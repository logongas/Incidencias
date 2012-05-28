<%@page contentType="text/html"%><%@page pageEncoding="UTF-8"%>
<%@page import="org.elf.datalayer.*,org.elf.businesslayer.*,org.elf.weblayer.*,org.elf.weblayer.controls.*"   %>
<%

            Frame frameHeap = WLSession.getControls().createFrame("frameHeap", "Heap Usada", 0, 0);
            Caption captionMinimoHeap = WLSession.getControls().createCaption("captionMinimoHeap", "Mínimo:");
            Caption captionMaximoHeap = WLSession.getControls().createCaption("captionMaximoHeap", "Máximo:");
            Caption captionActualHeap = WLSession.getControls().createCaption("captionActualHeap", "Actual:");
            Field fieldMinimoHeap = WLSession.getControls().createEmptyField("fieldMinimoHeap", null);
            Field fieldMaximoHeap = WLSession.getControls().createEmptyField("fieldMaximoHeap", null);
            Field fieldActualHeap = WLSession.getControls().createEmptyField("fieldActualHeap", null);
            fieldMinimoHeap.setEnabled(false);
            fieldMaximoHeap.setEnabled(false);
            fieldActualHeap.setEnabled(false);

            Frame frameMaxHeapSize = WLSession.getControls().createFrame("frameMaxHeapSize", "Max. Heap", 0, 0);
            Frame frameHeapSize = WLSession.getControls().createFrame("frameHeapSize", "Tamaño Head", 0, 0);
            Caption captionActualHeapSize = WLSession.getControls().createCaption("captionHeapSize", "Actual:");
            Caption captionActualMaxHeapSize = WLSession.getControls().createCaption("captionMaxHeapSize", "Actual:");
            Field fieldActualHeapSize = WLSession.getControls().createEmptyField("fieldActualHeapSize", null);
            Field fieldActualMaxHeapSize = WLSession.getControls().createEmptyField("fieldActualMaxHeapSize", null);
            fieldActualHeapSize.setEnabled(false);
            fieldActualMaxHeapSize.setEnabled(false);




            Frame frameCPU = WLSession.getControls().createFrame("frameCPU", "% CPU", 0, 0);
            Caption captionMinimoCPU = WLSession.getControls().createCaption("captionMinimoCPU", "Mínimo:");
            Caption captionMaximoCPU = WLSession.getControls().createCaption("captionMaximoCPU", "Máximo:");
            Caption captionActualCPU = WLSession.getControls().createCaption("captionActualCPU", "Actual:");
            Field fieldMinimoCPU = WLSession.getControls().createEmptyField("fieldMinimoCPU", null);
            Field fieldMaximoCPU = WLSession.getControls().createEmptyField("fieldMaximoCPU", null);
            Field fieldActualCPU = WLSession.getControls().createEmptyField("fieldActualCPU", null);
            fieldMinimoCPU.setEnabled(false);
            fieldMaximoCPU.setEnabled(false);
            fieldActualCPU.setEnabled(false);

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
        <script type="text/javascript" src="monitor_jvm.js"></script>

    </head>
    <body  >



        <table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0px">
            <tr>
                <td >
                    <table style="font-family: Verdana, Arial, Helvetica, sans-serif;color:#000000;font-size:10px;" cellpadding="0" cellspacing="0" border="0px">
                        <tr>
                            <td  >&nbsp;</td>
                            <td height="1px" align="center" valign="top" style="font-size:10px;font-weight:bold">Memoria (Heap)</td>
                        </tr>
                        <tr >
                            <td align="center" width="20px" >M<br>i<br>B</td>
                            <td ><div id="chartHeapMemory" style="width:800px;height:240px">&nbsp;</div></td>
                        </tr>
                        <tr>
                            <td  width="20px" >&nbsp;</td>
                            <td height="1px" align="center">Tiempo (s)</td>
                        </tr>
                    </table>

                </td>
                <td width="100%" align="left" rowspan="2"  >

                    <table border="0px" height="100%">
                        <tr  >
                            <td height="1%" valign="top" align="center">
                                <%=buttonStop.toHTML()%>
                            </td>
                        </tr>
                        <tr height="15%" >
                            <td  valign="middle" align="left">
                                <%=frameConfigurar.toHTML()%>
                                <%=captionRefresco.toHTML()%>
                                <%=fieldRefresco.toHTML()%>
                                <%=captionMaxTime.toHTML()%>
                                <%=fieldMaxTime.toHTML()%>
                                <%=buttonConfigurar.toHTML()%>
                                <%=frameConfigurar.toHTML()%>
                            </td>
                        </tr>
                        <tr height="">
                            <td >


                                <%=frameMaxHeapSize.toHTML()%>
                                <table>
                                    <tr>
                                        <td><%=captionActualMaxHeapSize.toHTML()%></td>
                                        <td><%=fieldActualMaxHeapSize.toHTML()%></td>
                                    </tr>
                                </table>
                                <%=frameMaxHeapSize.toHTML()%>


                            </td>
                        </tr>
                        <tr height="">
                            <td >


                                <%=frameHeapSize.toHTML()%>
                                <table>
                                    <tr>
                                        <td><%=captionActualHeapSize.toHTML()%></td>
                                        <td><%=fieldActualHeapSize.toHTML()%></td>
                                    </tr>
                                </table>
                                <%=frameHeapSize.toHTML()%>


                            </td>
                        </tr>
                        <tr height="15%">
                            <td >


                                <%=frameHeap.toHTML()%>
                                <table>
                                    <tr>
                                        <td><%=captionActualHeap.toHTML()%></td>
                                        <td><%=fieldActualHeap.toHTML()%></td>
                                    </tr>
                                    <tr>
                                        <td><%=captionMinimoHeap.toHTML()%></td>
                                        <td><%=fieldMinimoHeap.toHTML()%></td>
                                    </tr>
                                    <tr>
                                        <td><%=captionMaximoHeap.toHTML()%></td>
                                        <td><%=fieldMaximoHeap.toHTML()%></td>
                                    </tr>
                                </table>
                                <%=frameHeap.toHTML()%>


                            </td>
                        </tr>
                        <tr height="">
                            <td>
                                <%=frameCPU.toHTML()%>
                                <table>
                                    <tr>
                                        <td><%=captionActualCPU.toHTML()%></td>
                                        <td><%=fieldActualCPU.toHTML()%></td>
                                    </tr>
                                    <tr>
                                        <td><%=captionMinimoCPU.toHTML()%></td>
                                        <td><%=fieldMinimoCPU.toHTML()%></td>
                                    </tr>
                                    <tr>
                                        <td><%=captionMaximoCPU.toHTML()%></td>
                                        <td><%=fieldMaximoCPU.toHTML()%></td>
                                    </tr>
                                </table>
                                <%=frameCPU.toHTML()%>

                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td >
                    <table style="font-family: Verdana, Arial, Helvetica, sans-serif;color:#000000;font-size:10px;" cellpadding="0" cellspacing="0" border="0px">
                        <tr>
                            <td  >&nbsp;</td>
                            <td height="1px" align="center" valign="top" style="font-size:10px;font-weight:bold">Uso de CPU</td>
                        </tr>
                        <tr >
                            <td align="right"  width="20px" >&nbsp;&nbsp;%</td>
                            <td ><div id="chartCPULoad" style="width:800px;height:240px">&nbsp;</div></td>
                        </tr>
                        <tr>
                            <td  width="20px" >&nbsp;</td>
                            <td height="1px" align="center">Tiempo (s)</td>
                        </tr>
                    </table>

                </td>
            </tr>
        </table>
    </body>
</html>