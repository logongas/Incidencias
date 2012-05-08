//Gráficos
var jdbcChart;

//Datos
var data;

var minNumActiveUsed=undefined;
var maxNumActiveUsed=undefined;

var minNumIdle=undefined;
var maxNumIdle=undefined;

var initialTime=undefined;//Instante en el que se empieza a monitorizar
var chartInitTime=0;//Instante en el que empieza este gráfico

var maxTime=120;//Duracion del gráfico
var refreshTime=1000;//Velocidad de refresco del gráfico

var pressedStop=false; //Si se ha pulsado el botón de "Stop"."

addEvent(window,"load",function () {
    reset();
    fieldRefresco.setValue(refreshTime);
    fieldMaxTime.setValue(maxTime);
    setTimeout("getData()",1);
});

function reset() {
    jdbcChart=null;

    data=new Array();

    minHeapUsed=undefined;
    maxHeapUsed=undefined;
    minPorCPU=undefined;
    maxPorCPU=undefined;

    initialTime=undefined;
    chartInitTime=0;

}

/**
 * Configura el estilo del gráfico de lineas de la mamoria del heap usada.
 */
function configureCharts() {
    removeChildNodes(document.getElementById("chartJDBC"));

     jdbcChart=new Dygraph(document.getElementById("chartJDBC"),data,
      {
        labels: ["Tiempo (s)", "Nº Conexiones máximas", "Nº Conexiones Activas","Nº Conexiones esperando" ],
	dateWindow:[0,getMaxTime()+0.1],
	colors: ["#212190","#289428","#804000"],
        labelsSeparateLines:true,
        labelsDivStyles: {backgroundColor: 'transparent',fontSize:'12px'},
        labelsDivWidth: 260,
        strokeWidth: 2,
        axisLabelFontSize: 10,
        yAxisLabelWidth:25,
        includeZero:true
      }
    );

}

function getData() {

    var args=new List();
    WLSession.getControllers().getController("Monitor").executeService("getData",args,callBackHeapMemory);
    
}

function callBackHeapMemory(mapData) {
    var startTime=(new Date()).getTime();
    var numActive=mapData.get("NUM_ACTIVE");
    var maxActive=mapData.get("MAX_ACTIVE");
    var numIdle=mapData.get("NUM_IDLE");
    
    time=mapData.get("TIME");

    //Tiempo base desde el que se calcula todo
    if (initialTime==undefined) {
        initialTime=time;
    }

    //Instante de tiempo en el que estamos
    var chartTime=new Number(((time-initialTime)/1000000000).toFixed(1));

    data.push([chartTime,maxActive,numActive,numIdle]);

/****** BEGIN: Calcular Maximos y Mínimos ******/
    if ((numActive<minNumActiveUsed) || (minNumActiveUsed==undefined))  {
        minNumActiveUsed=numActive;
        fieldMinimoNumActive.setValue(minNumActiveUsed.toFixed(2));
    }
    
    if ((numActive>maxNumActiveUsed) || (maxNumActiveUsed==undefined))  {
        maxNumActiveUsed=numActive;
        fieldMaximoNumActive.setValue(maxNumActiveUsed.toFixed(2));
    }

    if ((numIdle!=undefined) && ((numIdle<minNumIdle) || (minNumIdle==undefined)))  {
        minNumIdle=numIdle;
        fieldMinimoNumIdle.setValue(minNumIdle.toFixed(2));
    }
    
    if ((numIdle!=undefined) && ((numIdle>maxNumIdle) || (maxNumIdle==undefined)))  {
        maxNumIdle=numIdle;
        fieldMaximoNumIdle.setValue(maxNumIdle.toFixed(2));
    }
/****** END: Calcular Maximos y Mínimos ******/
    
    if ( (pressedStop==false) && (data.length>=1) && (jdbcChart==null)) {
        configureCharts();
    } else if (data[data.length-1][0]>=getMaxTime()+chartInitTime) {
        var d1=data[data.length-1];
        var d2=data[data.length-2];
        data=new Array();
        data.push(d2);
        data.push(d1);


        var startX;
        var endX;

        startX=getMaxTime()+chartInitTime;
        endX=startX+getMaxTime();
        chartInitTime=startX;
        configureCharts();

        jdbcChart.updateOptions({dateWindow:[startX,endX+0.1]});
    } else if (jdbcChart!=null) {
        jdbcChart.updateOptions({})
    }
    
    fieldActualNumActive.setValue(numActive);
    fieldActualNumIdle.setValue(numIdle);


    if (pressedStop==false) {
        //Calculamos cuanto tiempos hemos gastado en procesar el datos
        var endTime=(new Date()).getTime();
        
        //El tiempo hasta el nuevo "tiemout" es el que había menos el "gastado";
        var waitTime=getRefreshTime()-(startTime-endTime);
        if (waitTime<=0) {
            waitTime=1;
        }
        setTimeout("getData()",waitTime);        
    }
              
}

function getMaxTime() {
    return maxTime;
}

function getRefreshTime() {
    return refreshTime;   
}

function buttonConfigurar_onClick() {
    var newRefreshTime=fieldRefresco.getValue()*1;
    if ((isNaN(newRefreshTime)==false) && (newRefreshTime>=3)) {
        refreshTime=newRefreshTime;
    }

    var newMaxTime=fieldMaxTime.getValue()*1;
    if ((isNaN(newMaxTime)==false) && (newMaxTime>=3)) {
        maxTime=newMaxTime;
    }
    reset();
}

function buttonStop_onClick() {
    pressedStop=!pressedStop;
    if (pressedStop==true) {
        buttonStop.setCaption("Start");
    } else {
        buttonStop.setCaption("Stop");
        setTimeout("getData()",getRefreshTime());        
    }
}

    