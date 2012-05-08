<%@page contentType="text/html"%><%@page pageEncoding="UTF-8"%>
<%@page import="org.elf.datalayer.*,org.elf.businesslayer.*,org.elf.weblayer.*,org.elf.weblayer.controls.*"   %>
<%

    Button buttonCerrar=WLSession.getControls().createButton("buttonCerrar","Cerrar");
    buttonCerrar.setDefaultButton(true);

%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">    
    <title><%=DLSession.getConfig().getValue("app.nombreCentro")%> - Incidencias</title>
<LINK href="<%=WLSession.getPaths().getAbsolutePath()%>/controls/css" rel="stylesheet" type="text/css">
<LINK href="<%=WLSession.getPaths().getAbsolutePath()%>/css/elf.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=WLSession.getPaths().getAbsolutePath()%>/common/javascript"></script>
<script type="text/javascript" src="<%=WLSession.getPaths().getAbsolutePath()%>/controls/javascript"></script>   
<script type="text/javascript" charset="ISO-8859-1" src="helpRoles.js"></script>
</head>
<body style="background-color:#f0f0f0;margin:10;padding:0;">
    <table class="text" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">        
        <tr >
            <td >Los permisos de cada rol son:
                <ul >
                    <li style="margin-top: 4px;"><span style="font-weight: bold;">Administrador:</span>Control total del programa.</li>
                    <li style="margin-top: 4px;"><span style="font-weight: bold;">T&eacute;cnico:</span>Actualiza o borra sus propias incidencias o tareas de sus proyectos.</li>
                    <li style="margin-top: 4px;"><span style="font-weight: bold;">Usuario:</span>Actualiza sus propias incidencias de sus proyectos pero no crea tareas.</li>
                </ul>
            </td>
        </tr>        
        <tr ><td align="right" height="30px" ><%=buttonCerrar.toHTML() %></td></tr>        
    </table>
</body>
</html>
