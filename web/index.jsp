<%@page contentType="text/html"%><%@page pageEncoding="UTF-8"%>
<%@page import="org.elf.datalayer.*,org.elf.businesslayer.*,org.elf.weblayer.*,org.elf.weblayer.controls.*"   %>
<%@page import="java.util.*"   %>

<%
Frame frame=WLSession.getControls().createFrame("frame","Usuario registrado",250,130);
Caption captionLogin=WLSession.getControls().createCaption("captionLogin","Correo:");
Caption captionPassword=WLSession.getControls().createCaption("captionPassword","Contraseña:");
Caption captionEMail=WLSession.getControls().createCaption("captionEMail","@"+(String)DLSession.getConfig().getValue("login.emailDomain"));
Field login=WLSession.getControls().createEmptyField("login",null,15);
Field password=WLSession.getControls().createEmptyField("password",null,15);
password .setPassword(true);
Button buttonEntrar=WLSession.getControls().createButton("buttonEntrar","Entrar");
buttonEntrar.setDefaultButton(true);

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
<script type="text/javascript" charset="utf-8" src="index.js"></script>
</head>
<body class="Intro" >
    <table width="100%" height="100%" border="0px" cellpadding="0" cellspacing="0">
        <tr ><td colspan="3" style="background-color:#3E6BA6;border-bottom: 1px solid #000000">
                <table width="100%" height="100px" cellpadding="0" cellspacing="0" border="0">
                    <tr height="100px" >
                        <td width="100%" height="100px"  align="center" ><img src="./img/text_titulo.png" ></td>
                    </tr>
                </table>
        </td></tr>
        <tr  height="100%"  >
            <td class="Left" >&nbsp;</td>
            <td valign="middle" align="center" class="Right">
                <%=frame.toHTML() %>
                <table  cellpadding="0" cellspacing="5px" border="0" >
                    <tr>
                        <td rowspan="2"><img src="<%=WLSession.getPaths().getAbsolutePath()%>/img/candado.gif" ></td>
                        <td><%=captionLogin.toHTML() %></td>
                        <td><%=login.toHTML() %></td>
                        <td><%=captionEMail.toHTML() %></td>
                    </tr>
                    <tr>
                        <td><%=captionPassword.toHTML() %></td>
                        <td colspan="2"><%=password.toHTML() %></td>
                    </tr>
                    <tr>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td  colspan="2">&nbsp;</td><td colspan="1" align="right"><%=buttonEntrar.toHTML() %></td><td  colspan="1">&nbsp;</td>
                    </tr>
                </table>
                <%=frame.toHTML() %>
            </td><td>&nbsp;</td>
        </tr>
        <tr><td colspan="3" class="text" style="text-align: center;font-size: 10px;padding-bottom: 4px;">&copy; 2009-<%=(new DLDateTime()).getDateParts().getYear() %> - Lorenzo González <script>document.write("(lorenzo.profesor"); document.write(" AT "); document.write("gmail.com) ");</script>- v.$$num_compilation$$</td></tr>
    </table>
</body>
</html>
