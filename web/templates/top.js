
function  toolBarInicio_onClick(menuItem) {
    mainMenuBar_onClick(menuItem);
}
function  toolBarMatricula_onClick(menuItem) {
    mainMenuBar_onClick(menuItem);
}
function  toolBarMantenimiento_onClick(menuItem) {
    mainMenuBar_onClick(menuItem);
}



var menuItemDepurarAjax=mainMenuBar.getMenuItem("DepurarAjax");
if (_debugAjax==true) {
    menuItemDepurarAjax.setUserData("1");
    menuItemDepurarAjax.setImage(WLSession.getPaths().getAbsolutePath() + "/icons/27x22/check.gif");
} else {
    menuItemDepurarAjax.setUserData("0");
}
var menuItemPreviewModalWindow=mainMenuBar.getMenuItem("PreviewModalWindow");
if (_previewModalWindow==true) {
    menuItemPreviewModalWindow.setUserData("1");
    menuItemPreviewModalWindow.setImage(WLSession.getPaths().getAbsolutePath() + "/icons/27x22/check.gif");
} else {
    menuItemPreviewModalWindow.setUserData("0");
}

function mainMenuBar_onClick(menuItem) {
    var newPage=null;
    {//Menu Archivo
        if (menuItem=="Inicio") { newPage="/main/main.jsp"; }  
        if (menuItem=="CerrarSesion") {
            newPage="/logout.jsp";
        }  
    }
    
    {//Menu Matriculacion
        if (menuItem=="Incidencias") { newPage="/main/genericMant.jsp?controller=gui.Incidencia"; }  
    }
    
    
    {//Menus Mantenimientos
        if (menuItem=="Proyectos") { newPage="/main/genericMant.jsp?controller=gui.Proyecto"; }    
        if (menuItem=="Aulas") { newPage="/main/genericMant.jsp?tableName=Aula"; }             
    }
    
    {//Menus Administración
        if (menuItem=="Usuarios") { newPage="/main/genericMant.jsp?controller=gui.Usuario"; }
        if (menuItem=="Configuracion") { newPage="/main/genericMant.jsp?controller=DL_Config"; }
    }

    {//Menu desarrollo
        if (menuItem=="tablas") { newPage="/main/genericMant.jsp?controller=BL_DefTable"; }
        if (menuItem=="secuencias") { newPage="/main/genericMant.jsp?controller=BL_Sequence"; }
        if (menuItem=="MonitorJVM") { newPage="/main/genericMant.jsp?url=" + WLSession.getPaths().getAbsolutePath() + "/elf/monitor/monitor_jvm.jsp"; }
        if (menuItem=="MonitorJDBC") { newPage="/main/genericMant.jsp?url=" + WLSession.getPaths().getAbsolutePath() + "/elf/monitor/monitor_jdbc.jsp"; }
        if (menuItem=="usuarioactivo") { newPage="/main/genericMant.jsp?url=" + WLSession.getPaths().getAbsolutePath() + "/elf/framework/usuarioactivo.jsp"; }


        if (menuItem=="DepurarAjax") {
            var menuItem=mainMenuBar.getMenuItem("DepurarAjax");
            if (menuItem.getUserData()=="0") {
                menuItem.setUserData("1");
                menuItem.setImage(WLSession.getPaths().getAbsolutePath() + "/icons/27x22/check.gif");
                _debugAjax=true;
            } else {
                menuItem.setUserData("0");
                menuItem.setImage(WLSession.getPaths().getAbsolutePath() + "/icons/void.gif");
                _debugAjax=false;
            }
        }
        if (menuItem=="PreviewModalWindow") {
            var menuItem=mainMenuBar.getMenuItem("PreviewModalWindow");
            if (menuItem.getUserData()=="0") {
                menuItem.setUserData("1");
                menuItem.setImage(WLSession.getPaths().getAbsolutePath() + "/icons/27x22/check.gif");
                _previewModalWindow=true;
                DLSession.getConfig().setValue("WL.PreviewModalWindow",true);
            } else {
                menuItem.setUserData("0");
                menuItem.setImage(WLSession.getPaths().getAbsolutePath() + "/icons/void.gif");
                _previewModalWindow=false;
                DLSession.getConfig().setValue("WL.PreviewModalWindow",false);
            }
        }
    }
    
    {//Ayuda
        if (menuItem=="AcercaDe") {
            WLSession.getModalWindow().showWindow(WLSession.getPaths().getAbsolutePath() + "/main/acercade.jsp",null,function(){});
        }
    }
    
    if (newPage!=null) {
        window.location.href=WLSession.getPaths().getAbsolutePath() + newPage;   
    }
}