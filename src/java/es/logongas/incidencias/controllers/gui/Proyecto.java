/*
 * Proyecto 15-sep-2009
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

package es.logongas.incidencias.controllers.gui;

import org.elf.weblayer.controllers.genericmant.GenericMant;
/**
 * GUI para el mantenimiento de la tabla de Incidencias
 * @author  <a href="mailto:logongas@users.sourceforge.net">Lorenzo González</a>
 */
public class Proyecto extends GenericMant {
    @Override
    protected String getTableName() {
        return "Proyecto";
    }
    
    @Override
    protected String getMantFieldsLayout() {
        return "{F 'Datos del Proyecto' " +
                "[" +
                "       #IdProyecto,IdProyecto|" +
                "       #Descripcion,Descripcion|" +
                "       #Login,#CodAula|" +
                "       Login 0,200 ,CodAula 0,200" +
                "]" +
                "}";
    }
    

}

