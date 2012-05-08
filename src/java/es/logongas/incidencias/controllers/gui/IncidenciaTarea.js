var stop;
var timer;

function mantEvent_postConfigueControls(mantType) {
    if ((mantType=="VIEW") || (mantType=="DELETE")) {
        buttonStart.setEnabled(false);
        buttonStop.setEnabled(false);      
    }
}

function buttonStart_onClick() {
    buttonStart.setEnabled(false);
    buttonStop.setEnabled(true);

    stop=false;
    timer=setTimeout("addTime();",60000);
}


function buttonStop_onClick() {
    buttonStart.setEnabled(true);
    buttonStop.setEnabled(false);
    stop=true;
    clearTimeout(timer);
}

function addTime() {
    fieldDuracion.setValue((fieldDuracion.getValue()*1)+1);
    callEcho();
    if (stop==false) {
        timer=setTimeout("addTime();",60000);

    }
}

/**
 * Esta función se está llamando continuamente y hace una llamada AJAX para que
 * nunca caduque la sesión.
 */
function callEcho() {
    var args=new List();
    args.add("ABCD");
    WLSession.getControllers().getController("Echo").executeService("echo",args,function () {});
}