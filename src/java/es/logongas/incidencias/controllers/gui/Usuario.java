/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.logongas.incidencias.controllers.gui;

import org.elf.datalayer.Parameters;
import org.elf.weblayer.controllers.genericmant.GenericMant;
import org.elf.weblayer.controls.ColumnDefinitions;

/**
 * GUI para el mantenimiento de la tabla Usuario
 * @author <a href="mailto:logongas@users.sourceforge.net">Lorenzo González</a>
 */
public class Usuario extends GenericMant {

    @Override
    protected String getTableName() {
        return "Usuario";
    }

    @Override
    protected String getMantFieldsLayout() {
        return " {F 'Datos del usuario' " +
                "   [" +
                "           #Login,<< Login 15|" +
                "           '',''|" +
                "           #Nombre,Nombre 20|" +
                "           #Ape1,Ape1 20 |" +
                "           #Ape2,Ape2 20 |" +
                "           #CodHabilitadoUsuario,CodHabilitadoUsuario |" +
                "           2 {T 'Permisos','Proyectos' " +
                "               [" +
                "                   IdRole 0,150|" +
                "                   {B 'buttonHelp','Ayuda Roles' }" +
                "               ]|" +
                "               IdProyecto 0,150}" +
                "   ]" +
                "}";
    }

    @Override
    protected String getSearchFieldsLayout() {
        return "{F 'Búsqueda de usuarios' " +
                "   [ " +
                "       #Login,Login 15,#Nombre,Nombre 15|" +
                "       #Ape1,Ape1 15,#Ape2,Ape2 15" +
                "   ]" +
                "}";
    }

    @Override
    protected ColumnDefinitions getSearchGridColumnDefinitions() {
        ColumnDefinitions columnDefinitions = new ColumnDefinitions();
        columnDefinitions.addColumnDefinition("Login", "Login", 150);
        columnDefinitions.addColumnDefinition("Nombre", "Nombre", 150);
        columnDefinitions.addColumnDefinition("Ape1", "1º Apellido", 150);
        columnDefinitions.addColumnDefinition("Ape2", "2º Apellido", 150);
        
        return columnDefinitions;
    }

    @Override
    public String getSearchSQL(String tableName, Parameters parameters) {
        Parameters newParameters=new Parameters();
        StringBuffer sb=new StringBuffer("SELECT Login,Login,Nombre,Ape1,Ape2 FROM Usuario WHERE 1=1");
        
        if (parameters.getParameter(0).isEmpty()==false) {
            sb.append(" AND Login=? ");
            newParameters.addParameter(parameters.getParameter(0));
        }
        if (parameters.getParameter(1).isEmpty()==false) {
            sb.append(" AND Nombre Like ? ");
            newParameters.addParameter(parameters.getParameter(1));
        }
        if (parameters.getParameter(2).isEmpty()==false) {
            sb.append(" AND Ape1 Like ? ");
            newParameters.addParameter(parameters.getParameter(2));
        }
        if (parameters.getParameter(3).isEmpty()==false) {
            sb.append(" AND Ape2 Like ? ");
            newParameters.addParameter(parameters.getParameter(3));
        }

        sb.append(" Order By Nombre,Ape1,Ape2");

        parameters.clear();
        parameters.addAll(newParameters);
        return sb.toString();
    }
}
