<%@page import="org.elf.datalayer.*,org.elf.businesslayer.*,org.elf.weblayer.*,org.elf.weblayer.controls.*"   %>
<%  
    String pathIcons=WLSession.getPaths().getAbsolutePath() + "/icons/16x16/";
    String pathToolBarIcons=WLSession.getPaths().getAbsolutePath() + "/icons/16x16/";
    Roles roles=DLSession.getSecurity().getUserIdentification().getRoles();
    
    MenuBar mainMenuBar=WLSession.getControls().createMenuBar("mainMenuBar");
        MenuBarItem archivo=mainMenuBar.addMenuItem("archivo","Archivo");
            archivo.addMenuItem("Inicio","Inicio",pathIcons + "go-home.gif");
            archivo.addSeparator("separator1");
            archivo.addMenuItem("CerrarSesion","Cerrar sesión",pathIcons + "system-log-out.gif");

        if (roles.containsKey("ADMIN") || roles.containsKey("TECNICO") || roles.containsKey("USER")) {
            MenuBarItem matricula=mainMenuBar.addMenuItem("matricula","Incidencias");
                matricula.addMenuItem("Incidencias","Incidencias",pathIcons + "contact-new.gif");
        }

        if (roles.containsKey("ADMIN") || roles.containsKey("TECNICO") ) {
            MenuBarItem mantenimientos=mainMenuBar.addMenuItem("mantenimientos","Mantenimientos");
                mantenimientos.addMenuItem("Proyectos","Proyectos",pathIcons + "applications-internet.gif");
                mantenimientos.addMenuItem("Aulas","Aulas",pathIcons + "internet-web-browser.gif");
        }
            
        if (roles.containsKey("ADMIN") ) {
        MenuBarItem administracion=mainMenuBar.addMenuItem("administracion","Administracion");
            administracion.addMenuItem("Usuarios","Usuarios",pathIcons + "system-users.gif");
            administracion.addMenuItem("Configuracion","Configuración",pathIcons + "applications-system.gif");
        }

        if (roles.containsKey("DEVELOPER") ) {
            MenuBarItem desarrollo=mainMenuBar.addMenuItem("desarrollo","Desarrollo");
                desarrollo.addMenuItem("tablas","Tablas",pathIcons + "kexi.gif");
                desarrollo.addMenuItem("secuencias","Secuencias",pathIcons + "enumList.gif");
                desarrollo.addSeparator("separatordesarrollo2");
                desarrollo.addMenuItem("MonitorJVM","Monitor CPU y Memoria",pathIcons + "utilities-system-monitor.gif");
                desarrollo.addMenuItem("MonitorJDBC","Monitor Base de datos",pathIcons + "utilities-system-monitor-db.gif");
                desarrollo.addSeparator("separatordesarrollo2");
                desarrollo.addMenuItem("PreviewModalWindow","Preview Ventana Modal");
                desarrollo.addMenuItem("DepurarAjax","Depurar Ajax");
                desarrollo.addMenuItem("usuarioactivo","Usuario Activo",pathIcons + "kuser.gif");
        }
                       
        MenuBarItem ayuda=mainMenuBar.addMenuItem("ayuda","Ayuda"); 
            ayuda.addMenuItem("AcercaDe","Acerca de...",pathIcons + "help-browser.gif");
        
            
            
        ToolBar toolBarInicio=WLSession.getControls().createToolBar("toolBarInicio");
            toolBarInicio.addToolBarItem("Inicio",pathIcons + "go-home.gif","Inicio");
            toolBarInicio.addSeparator("sep1");
            toolBarInicio.addToolBarItem("CerrarSesion",pathIcons + "system-log-out.gif","Cerrar sesión");

        
            ToolBar toolBarMatricula=WLSession.getControls().createToolBar("toolBarMatricula");
                toolBarMatricula.addToolBarItem("Incidencias",pathIcons + "contact-new.gif","Modificar incidencia");
        
            ToolBar toolBarMantenimiento=WLSession.getControls().createToolBar("toolBarMantenimiento");
                toolBarMantenimiento.addToolBarItem("Proyectos",pathIcons + "applications-internet.gif","Proyectos");
                toolBarMantenimiento.addToolBarItem("Aulas",pathIcons + "internet-web-browser.gif","Aulas");
%>
<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" oncontextmenu="return false;">
    <tr><td><img height="2px" src="<%=WLSession.getPaths().getAbsolutePath()%>/controls/img/void.gif" </td></tr>    
    <tr><td><%=mainMenuBar.toHTML() %></td></tr> 
    <tr><td><img height="3px" src="<%=WLSession.getPaths().getAbsolutePath()%>/controls/img/void.gif" </td></tr>    
    <tr>
        <td>
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td><%=toolBarInicio.toHTML()%></td>

<% if (roles.containsKey("ADMIN") || roles.containsKey("TECNICO") || roles.containsKey("USER")) {                    %>
                    <td>&nbsp;</td>
                    <td><%=toolBarMatricula.toHTML()%></td>

<% }                    %>
<% if (roles.containsKey("ADMIN") || roles.containsKey("TECNICO") ) {                    %>
                    <td>&nbsp;</td>
                    <td><%=toolBarMantenimiento.toHTML()%></td>
 <% }                    %>                   
                </tr>
            </table>
        </td>
    </tr>
    <tr><td><img height="3px" src="<%=WLSession.getPaths().getAbsolutePath()%>/controls/img/void.gif" </td></tr>    
</table>
<script type="text/javascript" charset="ISO-8859-1" src="<%=WLSession.getPaths().getAbsolutePath()%>/templates/top.js"></script>
