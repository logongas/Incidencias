<%@page contentType="text/html"%><%@page pageEncoding="UTF-8"%>
<%@page import="org.elf.datalayer.*,org.elf.businesslayer.*,org.elf.weblayer.*,org.elf.weblayer.controls.*"   %>
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
</head>
<body style="background-color:#BFDBFF;margin:3;padding:0;">
    <table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
        <tr><td ><jsp:include page="/templates/top.jsp" /> </td></tr>
        <tr ><td style="background-color:#f0f0f0;width:100%;height:100%;valign:middle;border:1px solid #6C92C1" >
                <div class="fondo_main">&nbsp;</div>    
            </td>
        </tr>
        <tr><td ><jsp:include page="/templates/bottom.jsp" /> </td></tr>        
    </table>
</body>
</html>
