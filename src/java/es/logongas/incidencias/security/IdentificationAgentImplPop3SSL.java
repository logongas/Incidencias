/*
 *
 * IdentificationAgentImplPop3SSL 9-Agosto-2011
 *
 * Incidencias
 *
 * Copyright 2011 Lorenzo González Gascón
 * mailto: lorenzo.profesor@gmail.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA02111-1307USA
 *
 * Disclaimer:
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package es.logongas.incidencias.security;

import org.elf.common.StringUtil;
import org.elf.datalayer.*;
import org.elf.datalayer.UserIdentification;
import org.elf.datalayer.kernel.*;
import org.elf.datalayer.kernel.impl.connection.mail.Pop3Server;
import org.elf.datalayer.kernel.impl.security.*;
import org.elf.datalayer.kernel.services.connection.KernelRecordSet;
import org.elf.datalayer.kernel.services.security.*;

/**
 * Identifica los usuarios mediante su cuenta de correo Pop3 pero conectadonse mediante SSL.
 * @author lorenzo González
 */
public class IdentificationAgentImplPop3SSL implements IdentificationAgent {

    private String _connectionName = "";
    private String _userTableName = "DL_User";
    private String _userRolesTableName = "DL_UserRole";
    private String _roleTableName = "DL_Role";
    private String _publicUserName = "PUBLIC";
    private String _systemUserName = "SYSTEM";

    /* (non-Javadoc)
     * @see org.elf.datalayer.security.IdentificationAgent#init(java.util.Map)
     */
    public void init(KernelSession kernelSession) {
        if ((getConnectionName() == null) || (getConnectionName().trim().equals(""))) {
            setConnectionName(kernelSession.defaultConnectionName);
        }
    }

    /* (non-Javadoc)
     * @see org.elf.datalayer.security.IdentificationAgent#getUserIdentity(org.elf.datalayer.security.Authenticator)
     */
    public UserIdentification getUserIdentification(Authenticator authenticator, KernelSession kernelSession) {
        if (authenticator instanceof AuthenticatorImplUserPassword) {
            AuthenticatorImplUserPassword aup = (AuthenticatorImplUserPassword) authenticator;

            String eMailDomain="@"+getEmailDomain();
            String eMail=StringUtil.cnvString(aup.getLogin());
            String fullName;

            if (eMail.endsWith(eMailDomain)==false) {
                eMail=eMail+eMailDomain;
            }

            String login=eMail.substring(0,eMail.length()-eMailDomain.length());

            String sql="SELECT * FROM " + _userTableName + " WHERE Login=?";
            Parameters parameters=new Parameters();
            parameters.addParameter(login,DataType.DT_STRING);
            KernelRecordSet rst=kernelSession.kernelConnections.getKernelConnection(_connectionName).executeQuery(sql,parameters);
            if (rst.next()) {
                if ("H".equals(rst.getString("CodHabilitadoUsuario"))) {
                    fullName=(StringUtil.cnvString(rst.getString("Nombre")) + ' ' + StringUtil.cnvString(rst.getString("Ape1")) + ' ' + StringUtil.cnvString(rst.getString("Ape2"))).trim();
                    rst.close();
                } else {
                    rst.close();
                    DLSession.getLogger().info("El usuario:"+eMail +" no está habilitado");
                    //El usuario no está habilitado
                    return null;
                }
            } else {
                rst.close();
                DLSession.getLogger().info("No existe el usuario:"+eMail +"en el sistema");
                //El Login no existe
                return null;
            }
                
            try {
                Pop3Server pop3Server = new Pop3Server(getAuthenticationServer() , eMail, aup.getPassword());
                pop3Server.close();
            } catch (Exception ex) {
                DLSession.getLogger().info("Falló al acceder a validar la contraseña de "+eMail +"."+ex.getLocalizedMessage());
                //Si salta alguna excepción es que no se ha podido conectar o que el usuario/password es erroneo.
                return null;
            }
            
            Roles roles = getRoles(kernelSession, login);

            UserIdentification ui = new UserIdentification(
                    login,
                    roles,
                    fullName);
            return ui;


        } else {
            //El autentificador que nos han pasado no lo soportamos
            return null;
        }
    }

    /**
     * Obtiene la identificación del usuario "PUBLICO"
     * Este usuario se utiliza para cuando la persona aun no se ha identificado
     * en el sistema pero queremos que tenga el mínimo de permisos.
     * @param kernelSession Objeto que da acceso al resto de servicios de
     * la capa de datos
     * @return Objeto que contiene datos asociados al usuario "Public"
     */
    public UserIdentification getPublicUserIdentification(KernelSession kernelSession) {
        String sql = "SELECT * FROM " + _userTableName + " WHERE Login=?";
        KernelRecordSet rst = kernelSession.kernelConnections.getKernelConnection(_connectionName).executeQuery(sql, new Parameters(_publicUserName, DataType.DT_STRING));
        if (rst.next()) {
            String login = rst.getString("login");
            Roles roles = getRoles(kernelSession, login);

            UserIdentification ui = new UserIdentification(
                    login,
                    roles,
                    (StringUtil.cnvString(rst.getString("firstname")) + ' ' + StringUtil.cnvString(rst.getString("surname1")) + ' ' + StringUtil.cnvString(rst.getString("surname2"))).trim());
            rst.close();
            return ui;

        } else {
            rst.close();
            //No han definido el usuario PUBLIC así que lo creamos nosotros
            //de esa forma no hace falta que lo den de alta.
            //Aunque no podrán asignale permisos
            return new UserIdentification("PUBLIC", null, "PUBLIC User");
        }
    }

    /**
     * Obtiene la identificación del usuario "SYSTEM"
     * Este usuario se utiliza para cuando la persona aun no se ha identificado
     * en el sistema pero queremos que tenga el máximo de permisos.
     * Se suele usar para cuando se lanza un trabajo BATH o cosas así.
     * @param kernelSession Objeto que da acceso al resto de servicios de
     * la capa de datos
     * @return Objeto que contiene datos asociados al usuario "Public"
     */
    public UserIdentification getSystemUserIdentification(KernelSession kernelSession) {
        String sql = "SELECT * FROM " + _userTableName + " WHERE Login=?";
        KernelRecordSet rst = kernelSession.kernelConnections.getKernelConnection(_connectionName).executeQuery(sql, new Parameters(_systemUserName, DataType.DT_STRING));
        if (rst.next()) {
            String login = rst.getString("login");
            Roles roles = getRoles(kernelSession, login);

            UserIdentification ui = new UserIdentification(
                    login,
                    roles,
                    (StringUtil.cnvString(rst.getString("firstname")) + ' ' + StringUtil.cnvString(rst.getString("surname1")) + ' ' + StringUtil.cnvString(rst.getString("surname2"))).trim());
            rst.close();
            return ui;

        } else {
            rst.close();
            //No han definido el usuario SYSTEM así que lo creamos nosotros
            //de esa forma no hace falta que lo den de alta.
            //Aunque no podrán asignale permisos
            return new UserIdentification("SYSTEM", null, "System User");
        }
    }

    /**
     * Obtiene un Map con los roles del usuario
     */
    private Roles getRoles(KernelSession kernelSession, String login) {
        String sql = "SELECT UR.IdRole,IFNULL(R.Description,UR.IdRole) As Description FROM " + _userRolesTableName + " UR LEFT JOIN " + _roleTableName + " R on UR.IdRole=R.IdRole WHERE UR.Login=?";
        
        
        Roles roles = new Roles();

        KernelRecordSet rst;
        try {
            rst = kernelSession.kernelConnections.getKernelConnection(_connectionName).executeQuery(sql, new Parameters(login, DataType.DT_STRING));
        } catch (Exception ex) {
            //Si da un error es que no existe la tabla de los Roles.
            //Así que suponemos que no está implementada esa funcionalidad
            return roles;
        }

        //Añadimos los roles del usuario
        while (rst.next()) {
            roles.addRole(new Role(rst.getString("IdRole"), rst.getString("Description")));
        }

        return roles;
    }

    /**
     * @return El dominio alque pertencen los correos de los usuarios.
     */
    public String getEmailDomain() {
        return (String)DLSession.getConfig().getValue("login.emailDomain");
    }   
    
    /**
     * @return El servidor Pop3 usuado para identificar al usuario
     */
    public String getAuthenticationServer() {
        return (String)DLSession.getConfig().getValue("login.authenticationServer");
    }    
    
    /**
     * Obtiene el nombre de la conexión se utilizará para obtener
     * los datos de la tabla
     * @return  Nombre de la conexión a utilizar
     */
    public String getConnectionName() {
        return _connectionName;
    }

    public void setConnectionName(String connectionName) {
        _connectionName = connectionName;
    }

    public String getUserTableName() {
        return _userTableName;
    }

    public void setUserTableName(String userTableName) {
        _userTableName = userTableName;
    }

    public String getUserRolesTableName() {
        return _userRolesTableName;
    }

    public void setUserRolesTableName(String userRolesTableName) {
        this._userRolesTableName = userRolesTableName;
    }

    public String getPublicUserName() {
        return _publicUserName;
    }

    public void setPublicUserName(String publicUserName) {
        this._publicUserName = publicUserName;
    }

    public String getSystemUserName() {
        return _systemUserName;
    }

    public void setSystemUserName(String systemUserName) {
        this._systemUserName = systemUserName;
    }

    public String getRoleTableName() {
        return _roleTableName;
    }

    public void setRoleTableName(String roleTableName) {
        this._roleTableName = roleTableName;
    }




}
