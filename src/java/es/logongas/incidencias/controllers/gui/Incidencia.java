/*
 * Incidencia 14-sep-2009
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

package es.logongas.incidencias.controllers.gui;

import java.util.ArrayList;
import java.util.List;
import org.elf.datalayer.*;
import org.elf.weblayer.controllers.genericmant.GenericMant;
import org.elf.weblayer.controls.*;
/**
 * GUI para el mantenimiento de la tabla de Incidencias
 * @author  <a href="mailto:logongas@users.sourceforge.net">Lorenzo González</a>
 */
public class Incidencia extends GenericMant {
    @Override
    protected String getTableName() {
        return "Incidencia";
    }
    
    @Override
    public String getSearchSQL(String tableName,Parameters parameters) {
        Parameters newParameters=new Parameters();
        
        StringBuffer sb=new StringBuffer("SELECT I.IdIncidencia,I.IdIncidencia,I.Descripcion,FechaApertura,E.Descripcion,P.Descripcion" +
                " FROM Incidencia I,Estado E,Proyecto P,ProyectoUser PU " +
                " WHERE I.CodEstado=E.CodEstado AND P.IdProyecto=I.IdProyecto AND PU.IdProyecto=P.IdProyecto ");
        
        if (parameters.getParameter(0).isEmpty()==false) {
                sb.append(" AND I.IdIncidencia=? ");
                newParameters.addParameter(parameters.getParameter(0));
        }
        if (parameters.getParameter(1).isEmpty()==false) {
                sb.append(" AND I.IdProyecto=? ");
                newParameters.addParameter(parameters.getParameter(1));
        }
        if (parameters.getParameter(2).isEmpty()==false) {
                sb.append(" AND I.CodAula=? ");
                newParameters.addParameter(parameters.getParameter(2));
        }        
        if (parameters.getParameter(3).isEmpty()==false) {
                sb.append(" AND I.CodEstado=? ");
                newParameters.addParameter(parameters.getParameter(3));
        }        
        
         sb.append(" AND PU.Login=${Login} ");
        
        sb.append(" Order by FechaApertura ");
        parameters.clear();
        parameters.addAll(newParameters);
        return sb.toString();
        
        
    }
    
    @Override
    protected ColumnDefinitions getSearchGridColumnDefinitions() {
        ColumnDefinitions columnDefinitions = new ColumnDefinitions();
        columnDefinitions.addColumnDefinition("IdIncidencia", "Nº", 45);
        columnDefinitions.addColumnDefinition("Descripcion", "Descripcion", 350);
        columnDefinitions.addColumnDefinition("FechaApertura", "F. Apertura", 150);
        columnDefinitions.addColumnDefinition("Estado", "Estado", 75);
        columnDefinitions.addColumnDefinition("Proyecto", "Proyecto", 180);      
        
        return columnDefinitions;
    }    
    
    @Override
    protected String getSearchFieldsLayout() {    
        return 
                "   {F 'Búsqueda de incidencias' " +
                "       [   " +
                "           #IdIncidencia,IdIncidencia|" +
                "           #IdProyecto,IdProyecto 0,200|" +
                "           'Aula (Debes poner el proyecto)',CodAula 0,200|" +                
                "           #CodEstado,CodEstado" +
                "       ]" +
                "   }" ;

    }
    
    
    @Override
    protected String getMantFieldsLayout() {
        return "{T 'Incidencia','Tareas' " +
                "[" +
                "   [#IdIncidencia,IdIncidencia,#Login,Login]" +
                "   [    " +
                "       #IdProyecto,IdProyecto|" +
                "       #Descripcion,Descripcion" +
                "   ]" +
                "   |" +                
                "   [" +
                "       #CodAula,CodAula,#CodEstado,CodEstado|" +
                "       #FechaApertura,FechaApertura,#FechaCierre,FechaCierre" +
                "   ]" +
                "   |" +
                "   [#Explicacion|Explicacion 600,100]" +
                "   |" +
                "   [ >> #Duracion,Duracion disabled]" +
                "]|" +
                "{DETAIL}" +
                "}";
    }
    
    @Override
    protected List<String> getDetailTables() {
        List<String> detailTables=new ArrayList<String>();
        detailTables.add("IncidenciaTarea");        
        return detailTables; 
    }
    
    @Override
    protected ColumnDefinitions getDetailGridColumnDefinitions(String detailName) {
        if (detailName.equals("IncidenciaTarea")) {          
            ColumnDefinitions columnDefinitions = new ColumnDefinitions();
            columnDefinitions.addColumnDefinition("Login", "Login", 90);
            columnDefinitions.addColumnDefinition("Fecha", "Fecha", 150);
            columnDefinitions.addColumnDefinition("Duracion", "Durac.", 60);
            columnDefinitions.addColumnDefinition("Descripción", "Descripción", 380);

            return columnDefinitions;
        } else {
            throw new RuntimeException("El parámetro detailName no es válido:"+detailName);
        }        
    }   

    public String getDetailSQL(String tableName,String detailName,Parameters parameters) {
        if (detailName.equals("IncidenciaTarea")) {        
            return "SELECT IdIncidencia,IdTarea,Login,Fecha,Duracion,Left(Descripcion,100) FROM IncidenciaTarea WHERE IdIncidencia=? ORDER BY Fecha";
        } else {
            throw new RuntimeException("El parámetro detailName no es válido:"+detailName);
        }
    }    
    
    @Override
    protected String getDetailMantPage(String detailName) {
        if (detailName.equals("IncidenciaTarea")) {
            return "/controller/gui.IncidenciaTarea/action/mant";
        } else {
            throw new RuntimeException("El parámetro detailName no es válido:"+detailName);
        }
    }

    @Override
    protected int getDetailGridHeight(String detailName) {
        if (detailName.equals("IncidenciaTarea")) {
            return 275;
        } else {
            throw new RuntimeException("El parámetro detailName no es válido:"+detailName);
        }
    }


}
