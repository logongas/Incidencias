/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.incidencias.controllers;

import java.lang.management.*;
import java.util.*;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import org.elf.datalayer.DLSession;
import org.elf.weblayer.WLSession;

/**
 *
 * @author <a href="mailto:logongas@users.sourceforge.net">Lorenzo González</a>
 */
public final class Performance {

    String appName;
    String connectionName;
    private long time;
    private long processCPUTime;
    private int availableProcessors;
    private long heapMemoryUsageCommitted;
    private long heapMemoryUsageUsed;
    private long heapMemoryUsageMax;
    private int dataSourceMaxActive;
    private int dataSourceNumActive;
    private int dataSourceNumIdle;

    public Performance(String appName, String connectionName) {
        this.appName = appName;
        this.connectionName = connectionName;
        
        this.refreshData();
    }

    public void refreshData() {
        //Obtener los objetos JMX
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();


        ObjectName objectName;
        MBeanServer server = null;
        try {

            objectName = new ObjectName("Catalina:type=DataSource,path=" + WLSession.getPaths().getContextPath() + ",host=localhost,class=javax.sql.DataSource,name=\"" + DLSession.getConnection().getDefaultConnectionName() + "\"");

            List myList = MBeanServerFactory.findMBeanServer(null);
            if (myList.toArray().length == 0) {
                throw new RuntimeException("No hay MBeanServer");
            }
            server = (MBeanServer) myList.toArray()[0];
            if (server == null) {
                throw new RuntimeException("MBeanServer es null");
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        //Obtener la información
        time = System.nanoTime();
        if (operatingSystemMXBean instanceof com.sun.management.OperatingSystemMXBean) {
            processCPUTime = ((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getProcessCpuTime();
        } else {
            DLSession.getLogger().info("Sin información de CPU:" + operatingSystemMXBean.getClass().getName());
            processCPUTime = 0;
        }
        availableProcessors = operatingSystemMXBean.getAvailableProcessors();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        try {
            dataSourceMaxActive = (Integer) server.getAttribute(objectName, "maxActive");
            dataSourceNumActive = (Integer) server.getAttribute(objectName, "numActive");
            dataSourceNumIdle = (Integer) server.getAttribute(objectName, "numIdle");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        heapMemoryUsageCommitted = heapMemoryUsage.getCommitted();
        heapMemoryUsageUsed = heapMemoryUsage.getUsed();
        heapMemoryUsageMax = heapMemoryUsage.getMax();
        
    }

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @return the processCPUTime
     */
    public long getProcessCPUTime() {
        return processCPUTime;
    }

    /**
     * @return the availableProcessors
     */
    public int getAvailableProcessors() {
        return availableProcessors;
    }

    /**
     * @return the heapMemoryUsageCommitted
     */
    public long getHeapMemoryUsageCommitted() {
        return heapMemoryUsageCommitted;
    }

    /**
     * @return the heapMemoryUsageUsed
     */
    public long getHeapMemoryUsageUsed() {
        return heapMemoryUsageUsed;
    }

    /**
     * @return the heapMemoryUsageMax
     */
    public long getHeapMemoryUsageMax() {
        return heapMemoryUsageMax;
    }

    /**
     * @return the dataSourceMaxActive
     */
    public int getDataSourceMaxActive() {
        return dataSourceMaxActive;
    }

    /**
     * @return the dataSourceNumActive
     */
    public int getDataSourceNumActive() {
        return dataSourceNumActive;
    }

    /**
     * @return the dataSourceNumIdle
     */
    public int getDataSourceNumIdle() {
        return dataSourceNumIdle;
    }
}
