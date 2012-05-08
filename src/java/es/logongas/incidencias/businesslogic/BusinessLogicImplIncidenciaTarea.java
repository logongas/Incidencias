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
import org.elf.businesslayer.kernel.services.record.KernelRecord;
import org.elf.businesslayer.kernel.services.record.businesslogic.BusinessLogic;
import org.elf.businesslayer.kernel.services.record.businesslogic.BusinessLogicContext;
import org.elf.datalayer.*;


/**
 *
 * @author  <a href="mailto:logongas@users.sourceforge.net">Lorenzo González</a>
 */
public class BusinessLogicImplIncidenciaTarea extends BusinessLogic{



    
    
    @Override
    public void postNew(BusinessLogicContext bl) {
        bl.getFields().setValue("Fecha", new DLDateTime());
    }

    @Override
    public void preInsert(BusinessLogicContext bl) {
        String login=DLSession.getSecurity().getUserIdentification().getLogin();
        bl.getFields().setValue("Login",login);
    }

    @Override
    public void postInsert(BusinessLogicContext bl) {
        /****** Regla: Notificar que se ha realizado una tarea ******/
        RecordSet rst=getUsuariosIncidencia((Integer)bl.getFields().getValue("IdIncidencia"));
        List<String> logins=getListFromRecordSet(String.class, rst);
        notifyNuevaTarea(bl, logins);
    }


    /**
     * Notifica que se ha cambiado el estado de la incidencia
     * @param bl Datos de la incidencia
     * @param usuarios Lista de usuarios a notificar
     */
    private void notifyNuevaTarea(BusinessLogicContext bl,List<String> logins) {
        Notify notify=NotifyFactory.createNotify();
        StringBuilder messageBody = new StringBuilder();
        KernelRecord incidencia=getKernelSession().createReadKernelRecord("Incidencia", bl.getFields().getValue("IdIncidencia"));
        String nombreCentro=(String)DLSession.getConfig().getValue("app.nombreCentro");
        
        messageBody.append("Correo enviado automáticamente por la aplicación de Gestión de Incidencias del " + nombreCentro + ":\n\n");
        messageBody.append("El usuario " + incidencia.getKernelFields().getDescription("Login") + " ha realizado una nueva tarea en la incidencia:\n");
        messageBody.append("Nº:" + incidencia.getKernelFields().getValue("IdIncidencia") + "\n");
        messageBody.append("Proyecto:" + incidencia.getKernelFields().getDescription("IdProyecto") + "\n");
        messageBody.append("Descripcion:" + incidencia.getKernelFields().getValue("Descripcion") + "\n");
        messageBody.append("\n");
        messageBody.append("Tarea\n");
        messageBody.append("Fecha de realización:" + bl.getFields().getValue("Fecha") + "\n");
        messageBody.append("Duración:" +  bl.getFields().getValue("Duracion") + " Minutos\n");
        messageBody.append("Descripción de la tarea:\n" + bl.getFields().getValue("Descripcion") + ".\n");
        notify.notify(logins, "Gestión de Incidencias:Realizada nueva tarea en la incidencia Nº "+incidencia.getKernelFields().getValue("IdIncidencia"), messageBody.toString());

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
