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
<script type="text/javascript" charset="ISO-8859-1" src="acercade.js"></script>
</head>
<body style="background-color:#f0f0f0;margin:10;padding:0;">
    <table class="text" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr ><td >Incidencias.
        <tr ><td >&copy; 2008-<%=(new DLDate()).getDateParts().getYear() %> Lorenzo González Gascón</td></tr>
        <tr ><td >Licenciado bajo GPL 3.0</td></tr>           
        <tr >
            <td >Los iconos e im&aacute;genes usados son respectivamente propiedad de:
                <ul>
                    <li><a href="http://tango.freedesktop.org" target="_blank">Tango Desktop Project</a></li>
                    <li><a href="http://www.vistaico.com" target="_blank">VistaICO.com</a></li>
                    <li><a href="http://garcya.us/blog/90-free-vector-icons-set/"  target="_blank">90 Free Vector Icons Set</a></li>
                    <li><a href="http://www.customicondesign.com/"  target="_blank">Custom icon design</a></li>
                    <li><a href="http://www.oxygen-icons.org/"  target="_blank">Oxygen</a></li>
                    <li><a href="http://www.flickr.com/photos/cayetano/1808072106/"  target="_blank">Imagen CAC por cayetano</a></li>
                    <li><a href="http://www.flickr.com/photos/tessekkur/420937404/"  target="_blank">Imagen CAC por tessekkur</a></li>
                    <li><a href="http://www.flickr.com/photos/surfzone/4698912363/"  target="_blank">Imagen CAC por surfzone</a></li>

                </ul>
            </td>
        </tr>        
        <tr ><td align="right" height="30px" ><%=buttonCerrar.toHTML() %></td></tr>        
    </table>
</body>
</html>
