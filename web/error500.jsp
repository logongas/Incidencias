<%@ page isErrorPage="true" %>
<%@page contentType="text/html"%><%@page pageEncoding="UTF-8"%>
<%@page import="org.elf.datalayer.*,org.elf.businesslayer.*,org.elf.weblayer.*,org.elf.weblayer.controls.*"   %>
<%@page import="java.util.*"   %>

<%
String _contextPath = request.getContextPath();
String _absolutePath = request.getRequestURL().substring(0, request.getRequestURL().indexOf(_contextPath)) + _contextPath;
String errorMessage=" ";

if (exception!=null) {
    errorMessage=exception.toString();
}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>Incidencias</title>
<LINK href="<%=_absolutePath %>/css/elf.css" rel="stylesheet" type="text/css">
</head>
<body class="Intro" >
    <table width="100%" height="100%" border="0px" cellpadding="0" cellspacing="0">
        <tr ><td  style="background-image: url(<%=_absolutePath %>/img/background_left_top.png)">
                <table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr >
                        <td width="100%" align="center"  ><img src="<%=_absolutePath %>/img/text_titulo.png" ></td>
                    </tr>
                </table>
        </td></tr>
        <tr  height="100%" >
            <td width="100%"  align="center"  >
                <table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr >
                        <td width="100%" align="center"  ><img src="<%=_absolutePath %>/img/forbidden.png" ></td>
                    </tr>
                    <tr >
                        <td width="100%" align="center" class="titulo1"  style="padding:15px"  >Lo sentimos pero el programa ha fallado.<img src="<%=_absolutePath %>/img/cara_triste.png" ></td>
                    </tr>
                    <tr >
                        <td width="100%" align="center" style="padding:10px"  ><%=org.elf.common.HTMLUtil.toHTML(errorMessage)%></td>
                    </tr>
                    <tr >
                        <td width="100%" align="center" style="padding:10px"  ><a class="mainLink" target="_top" href="<%=_absolutePath %>/index.jsp">Ir a la p&aacute;gina de inicio</a></td>
                    </tr>
                </table>

            </td>
        </tr>
        <tr><td>&nbsp;</td>
    </table>
</body>
</html>
