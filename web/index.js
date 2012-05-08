function buttonEntrar_onClick() {
    DLSession.getDLSystem().createSession(login.getValue(),password.getValue(),sucessCreateSessionCallBack);
}



function sucessCreateSessionCallBack(ok,userDefinedParam) {
    if (ok==true) {
        window.location.href=WLSession.getPaths().getAbsolutePath() + "/main/genericMant.jsp?controller=gui.Incidencia";
    } else {
        alert("El nombre del usuario no es válido \no su contraseña es erronea \no no tiene permisos para acceder a esta aplicación");
    }
}
