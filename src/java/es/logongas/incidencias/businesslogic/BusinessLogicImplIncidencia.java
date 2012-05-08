/*
 * BusinessLogicImplIncidencia 14-sep-2009
 * 
 * Easy Layered Framework (ELF)
 * 
 * Copyright 2004-2008 Lorenzo González Gascón
 * http://elfframework.sourceforge.net
 * mailto: logongas@users.sourceforge.net
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
package es.logongas.incidencias.businesslogic;

import java.util.ArrayList;
import java.util.List;
import es.logongas.incidencias.util.notify.Notify;
import es.logongas.incidencias.util.notify.NotifyFactory;
import org.elf.businesslayer.dictionary.DefColumn;
import org.elf.businesslayer.kernel.services.record.businesslogic.BusinessLogic;
import org.elf.businesslayer.kernel.services.record.businesslogic.BusinessLogicContext;
import org.elf.datalayer.*;

/**
 *
 * @author  <a href="mailto:logongas@users.sourceforge.net">Lorenzo González</a>
 */
public class BusinessLogicImplIncidencia extends BusinessLogic {

    @Override
    public void postRead(BusinessLogicContext bl) {

    }

    @Override
    public void postNew(BusinessLogicContext bl) {
        String login = DLSession.getSecurity().getUserIdentification().getLogin();
        bl.getFields().setValue("Login", login);
        bl.getFields().setValue("FechaApertura", new DLDateTime());

        Parameters parameters = new Parameters();
        DefColumn defColumn = bl.getFields().getDefColumn("IdProyecto");
        for (int i = 0; i < defColumn.getDependColumns().size(); i++) {
            String dependColumName = defColumn.getDependColumns().get(i);
            parameters.addParameter(bl.getFields().getValue(dependColumName), bl.getFields().getDefColumn(dependColumName).getDataType());
        }
        RecordSet rst = DLSession.getConnection().executeQuery(defColumn.getSqlSearch(), parameters);
        if (rst.next()) {
            int idProyecto;
            idProyecto = rst.getInt(0);
            if (rst.next() == false) {
                bl.getFields().setValue("IdProyecto", idProyecto);
            }
        }
    }

    @Override
    public void preInsert(BusinessLogicContext bl) {
        String codEstado = (String) bl.getFields().getValue("CodEstado");
        if (codEstado.equals(bl.getFields().getOriginalValue("CodEstado")) == false) {
            if (codEstado.equals("C")) {
                bl.getFields().setValue("FechaCierre", new DLDateTime());
            }
        }
    }

    @Override
    public void postInsert(BusinessLogicContext bl) {
        /****** Regla: Notificar la creación de la incidencia a todos los usuarios del proyecto ******/
        RecordSet rst=getUsuariosIncidencia((Integer)bl.getFields().getValue("IdIncidencia"));
        List<String> logins=getListFromRecordSet(String.class, rst);

        notifyCreacionIncidencia(bl, logins);

    }

    @Override
    public void preUpdate(BusinessLogicContext bl) {
        String codEstado = (String) bl.getFields().getValue("CodEstado");
        
        /****** Regla: Ponber la fecha de Cierre ******/
        if (codEstado.equals(bl.getFields().getOriginalValue("CodEstado")) == false) {
            if (codEstado.equals("C")) {
                bl.getFields().setValue("FechaCierre", new DLDateTime());
            }
        }

        
        /****** Regla: Enviar una correo si cambia el estado de la incidencia ******/
        if (codEstado.equals(bl.getFields().getOriginalValue("CodEstado")) == false) {
            RecordSet rst=getUsuariosIncidencia((Integer)bl.getFields().getValue("IdIncidencia"));
            List<String> logins=getListFromRecordSet(String.class, rst);
            
            notifyModificacionEstadoIncidencia(bl, logins);
        }
        
    }

    private void notifyCreacionIncidencia(BusinessLogicContext bl,List<String> correos) {
        String nombreCentro=(String)DLSession.getConfig().getValue("app.nombreCentro");

        Notify notify=NotifyFactory.createNotify();
        StringBuilder messageBody = new StringBuilder();
        messageBody.append("Correo enviado automáticamente por la aplicación de Gestión de Incidencias del " + nombreCentro + ":\n\n");
        messageBody.append("El usuario " + bl.getFields().getDescription("Login") + " ha creado una nueva incidencia:\n");
        messageBody.append("Nº:" + bl.getFields().getValue("IdIncidencia") + "\n");
        messageBody.append("Proyecto:" + bl.getFields().getDescription("IdProyecto") + "\n");
        messageBody.append("Descripcion:" + bl.getFields().getValue("Descripcion") + "\n");
        messageBody.append("Explicación:\n" + bl.getFields().getValue("Explicacion") + "\n");
        notify.notify(correos, "Gestión de Incidencias:Creación de nueva Incidencia Nº " + bl.getFields().getValue("IdIncidencia"), messageBody.toString());
    }


    /**
     * Notifica que se ha cambiado el estado de la incidencia
     * @param bl Datos de la incidencia
     * @param usuarios Lista de usuarios a notificar
     */
    private void notifyModificacionEstadoIncidencia(BusinessLogicContext bl,List<String> logins) {
        String nombreCentro=(String)DLSession.getConfig().getValue("app.nombreCentro");
        
        Notify notify=NotifyFactory.createNotify();
        StringBuilder messageBody = new StringBuilder();
        messageBody.append("Correo enviado automáticamente por la aplicación de Gestión de Incidencias del " + nombreCentro + ":\n\n");
        messageBody.append("El usuario " + DLSession.getSecurity().getUserIdentification().getDisplayName() + " ha modificado el estado de la incidencia:\n");
        messageBody.append("Nº:" + bl.getFields().getValue("IdIncidencia") + "\n");
        messageBody.append("Proyecto:" + bl.getFields().getDescription("IdProyecto") + "\n");
        messageBody.append("Descripcion:" + bl.getFields().getValue("Descripcion") + "\n");
        messageBody.append("Nuevo Estado:" + bl.getFields().getDescription("CodEstado") + "\n");
        notify.notify(logins, "Gestión de Incidencias:Modificación del estado de la Incidencia Nº " + bl.getFields().getValue("IdIncidencia") +". Estado:"+bl.getFields().getDescription("CodEstado"), messageBody.toString());
    }


    
    private <T> List<T> getListFromRecordSet(Class T,RecordSet rst) {
        List<T> list=new ArrayList<T>();
        
        if (rst.getColumnCount()!=1) {
            throw new RuntimeException("El RecordSet solo puede tener una columna pero tiene:"+rst.getColumnCount());
        }

        while(rst.next()) {
            list.add((T)rst.getObject(0));
        }

        return list;
    }

    /**
     * Obtiene todos los usuarios que participan en una incidencia.
     * Es decir , tanto el que la creó como los que han realizado tareas en él y
     * los Administradores y Técnicos del proyectos.
     * @param idIncidencia Incidencia que la que queremos los usuarios.
     * @return RecordSet con los "Login" de los usuarios
     */
    private RecordSet getUsuariosIncidencia(int idIncidencia) {
        String sql=
            "SELECT Login FROM Incidencia WHERE IdIncidencia=? UNION " +
            "SELECT Login FROM IncidenciaTarea WHERE IdIncidencia=? UNION " +
            "SELECT UR.Login FROM Incidencia I,ProyectoUser PU,UsuarioRole UR WHERE " +
            "	I.IdIncidencia=? AND " +
            "	I.IdProyecto=PU.IdProyecto AND " +
            "	PU.Login=UR.Login AND " +
            "	(UR.IdRole=\"ADMIN\" OR UR.IdRole=\"TECNICO\") ";


        Parameters parameters=new Parameters();
        parameters.addParameter(idIncidencia, DataType.DT_INT);
        parameters.addParameter(idIncidencia, DataType.DT_INT);
        parameters.addParameter(idIncidencia, DataType.DT_INT);

        RecordSet rst=DLSession.getConnection().executeQuery(sql, parameters);
        
        return rst;

    }
 
}
