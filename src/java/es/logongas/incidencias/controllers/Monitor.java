/*
 * Monitor 17-ago-2009
 * 
 * Easy Layered Framework (ELF)
 * 
 * Copyright 2004-2008 Lorenzo González Gascón
 * http://elfframework.sourceforge.net
 * mailto: logongas@users.sourceforge.net
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA02111-1307USA
 * 
 * Disclaimer:
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 * 
 */
package es.logongas.incidencias.controllers;

import java.util.*;
import org.elf.datalayer.DLSession;
import org.elf.weblayer.Controller;
import org.elf.weblayer.WLSession;

/**
 *
 * @author  <a href="mailto:logongas@users.sourceforge.net">Lorenzo González</a>
 */
public class Monitor extends Controller {

    /**
     * Map con los datos de uso del Heap
     * @return Un Map con los siguientes datos:
     * <ul>
     *  <li>TIME:Tiempo en el que se han calcula los datos.
     *  <li>PROCESS_CPU_TIME:Tiempo que se está ejecutando el proceso de JVM
     *  <li>AVAILABLE_PROCESSORS:Nº de procesadores disponibles.
     *  <li>HEAP_SIZE:Tamaño actual del heap
     *  <li>USED_HEAP:Memoria usada en el heap
     *  <li>MAX_HEAP_SIZE:Tamaño máximo que puede alcanzar el Heap
     *  <li>MAX_ACTIVE: Nº máximo de conexiones que puede haber activas en la conexión por defecto
     *  <li>NUM_ACTIVE: Nº de conexiones que hay activas en la conexión por defecto
     *  <li>NUM_IDLE:Nº de conexiones que hay esperando en la conexión por defecto
     * </ul>
     */
    public Map getData() {
        Performance performance = new Performance(WLSession.getPaths().getContextPath(), DLSession.getConnection().getDefaultConnectionName());
        HashMap data = new HashMap();

        data.put("TIME", performance.getTime());
        data.put("PROCESS_CPU_TIME", performance.getProcessCPUTime());
        data.put("AVAILABLE_PROCESSORS", performance.getAvailableProcessors());
        data.put("HEAP_SIZE", performance.getHeapMemoryUsageCommitted());
        data.put("USED_HEAP", performance.getHeapMemoryUsageUsed());
        data.put("MAX_HEAP_SIZE", performance.getHeapMemoryUsageMax());
        data.put("MAX_ACTIVE", performance.getDataSourceMaxActive());
        data.put("NUM_ACTIVE", performance.getDataSourceNumActive());
        data.put("NUM_IDLE", performance.getDataSourceNumIdle());

        return data;
    }
}


