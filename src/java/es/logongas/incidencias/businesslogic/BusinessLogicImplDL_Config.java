/*
 * BusinessLogicImplDL_Config.java
 *
 * Creado el 21 de mayo de 2007, 23:27
 *
 * Easy Layered Framework (ELF)
 *
 * Copyright 2005-2007 Lorenzo González Gascón
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

package es.logongas.incidencias.businesslogic;

import org.elf.businesslayer.kernel.services.record.businesslogic.*;
import org.elf.datalayer.*;

/**
 *
 * @author  <a href="mailto:logongas@users.sourceforge.net">Lorenzo González</a>
 */
public class BusinessLogicImplDL_Config extends BusinessLogic  {

	/**
	 * Método que se llama antes de hacer una acción sobre el Record
	 * y que debe comprobar si los datos que hay en el Record
	 * con correctos.
	 * @param blv Contexto desde el que se llama a la lógica de negocio
	 */
	public void validate(BusinessLogicContextValidate blv) {
            if ((blv.getOperation()==BusinessLogicOperation.INSERT) || (blv.getOperation()==BusinessLogicOperation.UPDATE)) {
                try {
                    String isoString=(String)blv.getFields().getValue("DataValue");
                    DataType dataType=DataType.toDataTypeFromInt(((Integer)blv.getFields().getValue("DataType")).intValue());
                    Object value=DLConvert.getObjectFromISOString(isoString,dataType);
                } catch (Exception ex) {
                    blv.getMessages().addMessage("El formato del valor no es adecuado según su tipo");
                }
            } 

        };
}
