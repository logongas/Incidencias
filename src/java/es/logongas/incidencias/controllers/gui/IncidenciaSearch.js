function searchEvent_postConfigueControls() {
    fieldCodEstado.setValue("A");
    var defColumn=fieldIdProyecto.getDefColumn();
    var parameters=new Parameters();
    for(var i=0;i<defColumn.getDependColumns().size();i++) {
        var controlField=WLSession.getPageFields().getFieldByColumnName(defColumn.getDependColumns().get(i));
        parameters.addParameter(controlField.getValue(),controlField.getDefColumn().getDataType());
    }
    DLSession.getConnection().executeQuery(defColumn.getSQLSearch(),parameters,callBack_executeQuerySQLSearch,function(status,statusText,userDefinedParam) { alert("Ha fallado la petición de obtener la lista de valores\n" + statusText) });

}


function callBack_executeQuerySQLSearch(rst) {
    if (rst.next()) {
        var value=rst.getObject(0);
        if (rst.next()==false) {
            //Si NO hay mas valores entonces ponemos el primero
            fieldIdProyecto.setValue(value);
        }
    }

    buscar();
}