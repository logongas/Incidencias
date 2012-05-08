<%@page contentType="text/html"%><%@page pageEncoding="UTF-8"%>
<%@page import="org.elf.datalayer.*,org.elf.businesslayer.*,org.elf.weblayer.*,org.elf.weblayer.controls.*, org.elf.datalayer.kernel.impl.connection.*,java.util.*"   %>
<%

Caption captionLogin=WLSession.getControls().createCaption("captionLogin","Login:");
Caption captionValueLogin=WLSession.getControls().createCaption("captionValueLogin",DLSession.getUserIdentification().getLogin());

Caption captionName=WLSession.getControls().createCaption("captionName","Nombre:");
Caption captionValueName=WLSession.getControls().createCaption("captionValueName",DLSession.getUserIdentification().getDisplayName());

Caption captionRoles=WLSession.getControls().createCaption("captionRoles","Roles:");

KernelPageableRecordSetImplRawData kprrd=new KernelPageableRecordSetImplRawData();
kprrd.addColumn("role",DataType.DT_STRING);
kprrd.addColumn("name",DataType.DT_STRING);
Iterator it=DLSession.getUserIdentification().getRoles().values().iterator();

while (it.hasNext()) {
    Role role=(Role)it.next();
    List row=new ArrayList<Object>();
    row.add(role.getName());    
    row.add(role.getDisplayName());
    kprrd.addRow(row);    
}

RecordSet rst=DLSession.getConnection().executePageableQuery(kprrd);

Grid grid=WLSession.getControls().createGrid("grid",rst,1,true,150);
grid.addColumnDefinition("Nombre",150);
grid.addColumnDefinition("Descripción",250);

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK href="<%=WLSession.getPaths().getAbsolutePath()%>/controls/css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="<%=WLSession.getPaths().getAbsolutePath()%>/common/javascript"></script>
        <script type="text/javascript" src="<%=WLSession.getPaths().getAbsolutePath()%>/controls/javascript"></script>        
    </head>
    <body>
        <table cellpadding="0" cellspacing="0" border="0px" width="100%" style="padding: 10px">
            <tr>
                <td ><%=captionLogin.toHTML() %></td><td width="100%" ><%=captionValueLogin.toHTML() %></td>
            </tr>
            <tr>
                <td ><%=captionName.toHTML() %></td><td width="100%" ><%=captionValueName.toHTML() %></td>
            </tr> 
            <tr  >
                <td colspan="2" ><%=captionRoles.toHTML() %></td>
            </tr>             
            <tr  >
                <td colspan="2" valign="top"><%=grid.toHTML() %></td>
            </tr>             
        </table>
    </body>
</html>
