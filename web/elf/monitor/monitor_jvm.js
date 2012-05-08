//Gráficos
var heapChart;
var cpuChart;

//Datos
var memoryChartData;
var cpuLoadChartData;

//Guardar los máximo y minimos
var minHeapUsed=undefined;
var maxHeapUsed=undefined;
var minPorCPU=undefined;
var maxPorCPU=undefined;


var initialTime=undefined;//Instante en el que se empieza a monitorizar
var chartInitTime=0;//Instante en el que empieza este gráfico

var lastTime=undefined; //Anterior ver que se ha leido el uso de CPU
var lastProcessCPUTime=undefined;//Anterior valor del uso de CPU

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
    heapChart=null;
    cpuChart=null;

    memoryChartData=new Array();
    cpuLoadChartData=new Array();

    minHeapUsed=undefined;
    maxHeapUsed=undefined;
    minPorCPU=undefined;
    maxPorCPU=undefined;

    initialTime=undefined;
    chartInitTime=0;
    
    lastTime=undefined;
    lastProcessCPUTime=undefined;



}

/**
 * Configura el estilo del gráfico de lineas de la mamoria del heap usada.
 */
function configureCharts() {

    
    removeChildNodes(document.getElementById("chartHeapMemory"));
    removeChildNodes(document.getElementById("chartCPULoad"));  
    
    heapChart=new Dygraph(document.getElementById("chartHeapMemory"),memoryChartData,
        {
            labels: ["Tiempo (s)", "Tamaño Heap", "Heap usada"],
            dateWindow:[0,getMaxTime()+0.1],
            labelsSeparateLines:true,
            labelsDivStyles: {
                backgroundColor: 'transparent',
                fontSize:'10px'
            },
            labelsDivWidth: 260,
            strokeWidth: 2,
            axisLabelFontSize: 10,
            yAxisLabelWidth:25,
            includeZero:true
        });
    cpuChart=new Dygraph(document.getElementById("chartCPULoad"),cpuLoadChartData,
        {
            labels: ["Tiempo (s)", "% uso CPU"],
            dateWindow:[0,getMaxTime()+0.1],
            labelsSeparateLines:true,
            labelsDivStyles: {
                backgroundColor: 'transparent',
                fontSize:'10px'
            },
            labelsDivWidth: 260,
            strokeWidth: 2,
            axisLabelFontSize: 10,
            yAxisLabelWidth:25,
            valueRange: [0,100.1],
            includeZero:true
        });

}

function getData() {

    var args=new List();
    WLSession.getControllers().getController("Monitor").executeService("getData",args,callBackHeapMemory);
    
}

function callBackHeapMemory(mapData) {
    var fechaHora=new Date();
    var startTime=fechaHora.getTime();
    
    //Datos del Heap
    var heapUsed=new Number((mapData.get("USED_HEAP")/1024/1024).toFixed(1));
    var heapSize=new Number((mapData.get("HEAP_SIZE")/1024/1024).toFixed(1));
    var maxHeapSize=new Number((mapData.get("MAX_HEAP_SIZE")/1024/1024).toFixed(1));

    //Datos de la CPU
    var availableProcessors=mapData.get("AVAILABLE_PROCESSORS");
    var processCPUTime=mapData.get("PROCESS_CPU_TIME");
    var time=mapData.get("TIME");

    //Tiempo base desde el que se calcula todo
    if (initialTime==undefined) {
        initialTime=time;
    }


    //Instante de tiempo en el que estamos
    var chartTime=new Number(((time-initialTime)/1000000000).toFixed(1));
    
    
    memoryChartData.push([chartTime,heapSize,heapUsed]);
   
    //Calcular el uso de CPU
    if ((lastProcessCPUTime!=undefined) && (processCPUTime!=undefined)) {
        var porCPU=new Number(((((processCPUTime-lastProcessCPUTime)/(time-lastTime))/availableProcessors)*100).toFixed(1));
        cpuLoadChartData.push([chartTime,porCPU]);
    }
    
    //Nos guardamos los anteriores valores para calcular el uso de CPU
    lastTime=time;
    lastProcessCPUTime=processCPUTime;

    /****** BEGIN: Calcular Maximos y Mínimos ******/
        if ((heapUsed<minHeapUsed) || (minHeapUsed==undefined))  {
            minHeapUsed=heapUsed;
            fieldMinimoHeap.setValue(minHeapUsed);
        }
        if ((heapUsed>maxHeapUsed) || (maxHeapUsed==undefined))  {
            maxHeapUsed=heapUsed;
            fieldMaximoHeap.setValue(maxHeapUsed);
        }
        if ((porCPU!=undefined) && ((porCPU<minPorCPU) || (minPorCPU==undefined)))  {
            minPorCPU=porCPU;
            fieldMinimoCPU.setValue(minPorCPU);
        }
        if ((porCPU!=undefined) && ((porCPU>maxPorCPU) || (maxPorCPU==undefined)))  {
            maxPorCPU=porCPU;
            fieldMaximoCPU.setValue(maxPorCPU);
        }
    /****** END: Calcular Maximos y Mínimos ******/
    
    if ( (pressedStop==false) && (memoryChartData.length>=1) && (cpuLoadChartData.length>=1) && (heapChart==null)) {
        configureCharts();
    } else if (memoryChartData[memoryChartData.length-1][0]>=getMaxTime()+chartInitTime) {
        var d1;
        var d2;

        d1=memoryChartData[memoryChartData.length-1];
        d2=memoryChartData[cpuLoadChartData.length-2];
        memoryChartData=new Array();
        memoryChartData.push(d2);
        memoryChartData.push(d1);

        d1=cpuLoadChartData[memoryChartData.length-1];
        d2=cpuLoadChartData[cpuLoadChartData.length-2];
        cpuLoadChartData=new Array();
        cpuLoadChartData.push(d2);
        cpuLoadChartData.push(d1);

        var startX;
        var endX;

        startX=getMaxTime()+chartInitTime;
        endX=startX+getMaxTime();
        chartInitTime=startX;

        configureCharts();
        heapChart.updateOptions({dateWindow:[startX,endX+0.1]});
        cpuChart.updateOptions({dateWindow:[startX,endX+0.1]});
    } else if (heapChart!=null) {
        heapChart.updateOptions({});
        cpuChart.updateOptions({});
    }




    fieldActualHeap.setValue(heapUsed);
    if (porCPU!=undefined) {
        fieldActualCPU.setValue(porCPU);
    }
    fieldActualHeapSize.setValue(heapSize);
    fieldActualMaxHeapSize.setValue(maxHeapSize);
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


    