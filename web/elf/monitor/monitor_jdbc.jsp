<%@page contentType="text/html"%><%@page pageEncoding="UTF-8"%>
<%@page import="org.elf.datalayer.*,org.elf.businesslayer.*,org.elf.weblayer.*,org.elf.weblayer.controls.*"   %>
<%

            Frame frameNumActive = WLSession.getControls().createFrame("frameNumActive", "Nº Conexiones Activas", 0, 0);
            Caption captionMinimoNumActive = WLSession.getControls().createCaption("captionMinimoNumActive", "Mínimo:");
            Caption captionMaximoNumActive = WLSession.getControls().createCaption("captionMaximoNumActive", "Máximo:");
            Caption captionActualNumActive = WLSession.getControls().createCaption("captionActualNumActive", "Actual:");
            Field fieldMinimoNumActive = WLSession.getControls().createEmptyField("fieldMinimoNumActive", null);
            Field fieldMaximoNumActive = WLSession.getControls().createEmptyField("fieldMaximoNumActive", null);
            Field fieldActualNumActive = WLSession.getControls().createEmptyField("fieldActualNumActive", null);
            fieldMinimoNumActive.setEnabled(false);
            fieldMaximoNumActive.setEnabled(false);
            fieldActualNumActive.setEnabled(false);

            Frame frameNumIdle = WLSession.getControls().createFrame("frameNumIdle", "Nº Conexiones Esperando", 0, 0);
            Caption captionMinimoNumIdle = WLSession.getControls().createCaption("captionMinimoNumIdle", "Mínimo:");
            Caption captionMaximoNumIdle = WLSession.getControls().createCaption("captionMaximoNumIdle", "Máximo:");
            Caption captionActualNumIdle = WLSession.getControls().createCaption("captionActualNumIdle", "Actual:");
            Field fieldMinimoNumIdle = WLSession.getControls().createEmptyField("fieldMinimoNumIdle", null);
            Field fieldMaximoNumIdle = WLSession.getControls().createEmptyField("fieldMaximoNumIdle", null);
            Field fieldActualNumIdle = WLSession.getControls().createEmptyField("fieldActualNumIdle", null);
            fieldMinimoNumIdle.setEnabled(false);
            fieldMaximoNumIdle.setEnabled(false);
            fieldActualNumIdle.setEnabled(false);
            Button buttonStop = WLSession.getControls().createButton("buttonStop", "Stop");

            Frame frameConfigurar = WLSession.getControls().createFrame("frameConfigurar", "Configuracion", 0, 0);
            Caption captionRefresco = WLSession.getControls().createCaption("captionRefresco", "Refresco (ms):");
            Field fieldRefresco = WLSession.getControls().createEmptyField("fieldRefresco", null);
            Caption captionMaxTime = WLSession.getControls().createCaption("captionMaxTime", "Duracion gráfico (s):");
            Field fieldMaxTime = WLSession.getControls().createEmptyField("fieldMaxTime", null);
            Button buttonConfigurar = WLSession.getControls().createButton("buttonConfigurar", "Configurar");

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
        <script type="text/javascript" src="dygraph-combined.js"></script>
        <script type="text/javascript" src="monitor_jdbc.js"></script>

    </head>
    <body  >
        <table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0px">
            <tr>
                <td >
                    <table style="font-family: Verdana, Arial, Helvetica, sans-serif;color:#000000;font-size:10px;" cellpadding="0" cellspacing="0" border="0px">
                        <tr>
                            <td  >&nbsp;</td>
                            <td height="1px" align="center" valign="top" style="font-size:10px;font-weight:bold">Pool de Conexiones</td>
                        </tr>
                        <tr >
                            <td align="center"  width="20px" >&nbsp;N<br>&nbsp;º</td>
                            <td ><div id="chartJDBC" style="width:800px;height:500px">&nbsp;</div></td>
                        </tr>
                        <tr>
                            <td  width="20px" >&nbsp;</td>
                            <td height="1px" align="center">Tiempo (s)</td>
                        </tr>
                    </table>
                </td>
                <td width="100%" align="left"  >

                    <table border="0px" height="100%">
                        <tr  >
                            <td height="1%" valign="top" align="center">
                                <%=buttonStop.toHTML()%>
                            </td>
                        </tr>
                        <tr  >
                            <td height="15%" valign="middle" align="left">
                                <%=frameConfigurar.toHTML()%>
                                <%=captionRefresco.toHTML()%>
                                <%=fieldRefresco.toHTML()%>
                                <%=captionMaxTime.toHTML()%>
                                <%=fieldMaxTime.toHTML()%>
                                <%=buttonConfigurar.toHTML()%>
                                <%=frameConfigurar.toHTML()%>
                            </td>
                        </tr>
                        <tr>
                            <td height="37%">


                                <%=frameNumActive.toHTML()%>
                                <table>
                                    <tr>
                                        <td><%=captionActualNumActive.toHTML()%></td>
                                        <td><%=fieldActualNumActive.toHTML()%></td>
                                    </tr>
                                    <tr>
                                        <td><%=captionMinimoNumActive.toHTML()%></td>
                                        <td><%=fieldMinimoNumActive.toHTML()%></td>
                                    </tr>
                                    <tr>
                                        <td><%=captionMaximoNumActive.toHTML()%></td>
                                        <td><%=fieldMaximoNumActive.toHTML()%></td>
                                    </tr>
                                </table>
                                <%=frameNumActive.toHTML()%>


                            </td>
                        </tr>
                        <tr>
                            <td height="38%" valign="top">
                                <%=frameNumIdle.toHTML()%>
                                <table>
                                    <tr>
                                        <td><%=captionActualNumIdle.toHTML()%></td>
                                        <td><%=fieldActualNumIdle.toHTML()%></td>
                                    </tr>
                                    <tr>
                                        <td><%=captionMinimoNumIdle.toHTML()%></td>
                                        <td><%=fieldMinimoNumIdle.toHTML()%></td>
                                    </tr>
                                    <tr>
                                        <td><%=captionMaximoNumIdle.toHTML()%></td>
                                        <td><%=fieldMaximoNumIdle.toHTML()%></td>
                                    </tr>

                                </table>
                                <%=frameNumIdle.toHTML()%>

                            </td>
                        </tr>
                    </table>

                </td>
            </tr>
        </table>


    </body>
</html>