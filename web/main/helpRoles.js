addEvent(window,"load",function() {
    WLSession.getModalWindow().setWindowTitle("Ayuda de Roles");
    WLSession.getModalWindow().setWindowSize(600,150);
});

function buttonCerrar_onClick() {
    WLSession.getModalWindow().closeWindow(false);   
}