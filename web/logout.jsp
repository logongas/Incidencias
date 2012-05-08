<%@page contentType="text/html"%><%@page pageEncoding="UTF-8"%>
<%@page import="org.elf.datalayer.*,org.elf.businesslayer.*,org.elf.weblayer.*,org.elf.weblayer.controls.*"   %>
<%@page import="java.util.*"   %>
<%
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Cache-Control" content="no-cache">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="-1">
        <title><%=DLSession.getConfig().getValue("app.nombreCentro")%> - Incidencias</title>
        <LINK href="<%=WLSession.getPaths().getAbsolutePath()%>/controls/css" rel="stylesheet" type="text/css">
        <LINK href="<%=WLSession.getPaths().getAbsolutePath()%>/css/elf.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="<%=WLSession.getPaths().getAbsolutePath()%>/common/javascript"></script>
        <script type="text/javascript" src="<%=WLSession.getPaths().getAbsolutePath()%>/controls/javascript"></script>
        <script type="text/javascript">
            addEvent(window,"load",function () {
                DLSession.getDLSystem().destroySession();
                setTimeout("gotoIndexPage()",1500);
            });
            
            function gotoIndexPage() {
                newPage="/index.jsp";
                window.location.href=WLSession.getPaths().getAbsolutePath() + newPage;                
            }
            
        </script>
    </head>
    <body  style="background-color:#BFDBFF;margin:3;padding:0;">
        <table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
            <tr><td ><jsp:include page="/templates/top.jsp" /> </td></tr>
            <tr ><td style="background-color:#f0f0f0;width:100%;height:100%;border:1px solid #6C92C1" >
                    <!-- Inicio -->

                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                        <tr align="center"><td width="100%">&nbsp;&nbsp;</td></tr>
                        <tr align="center"><td ><img   src="<%=WLSession.getPaths().getAbsolutePath()%>/img/logout.gif"/></td></tr>
                        <tr align="center"><td class="text" style="font-size: 34px">Cerrando sesión</td></tr>
                        <tr align="center"><td class="text" style="font-size: 24px">......</td></tr>
                    </table>

                    <!-- Fin-->
            </td>
        </tr>
        <tr><td ><jsp:include page="/templates/bottom.jsp" /> </td></tr>
    </table>
</body>
</html>
