addEvent(window,"load",function() {
    WLSession.getModalWindow().setWindowTitle("Acerca de \"Incidencias\"");
    WLSession.getModalWindow().setWindowSize(380,280);
});

function buttonCerrar_onClick() {
    WLSession.getModalWindow().closeWindow(false);   
}