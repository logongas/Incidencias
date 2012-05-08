/*
 * 
 * DictionaryLoadersMatricula 31-may-2009
 * 
 * Easy Layered Framework (ELF)
 * 
 * Copyright 2005-2009 Lorenzo González Gascón
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

package es.logongas.incidencias;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import es.logongas.incidencias.security.IdentificationAgentImplPop3SSL;
import java.net.URL;
import org.elf.datalayer.dictionary.DataDictionary;
import org.elf.datalayer.dictionary.DefIdentificationAgent;
import org.elf.datalayer.dictionary.DefKernelConnectionFactory;
import org.elf.datalayer.kernel.impl.connection.xml.KernelConnectionFactoryImplXML;
import org.elf.weblayer.dictionary.DictionaryLoaders;

/**
 *
 * @author <a href="mailto:logongas@users.sourceforge.net">Lorenzo González</a>
 */
public class DictionaryLoadersMatricula extends DictionaryLoaders {

    @Override
    public DataDictionary loadDataDictionary(Map<String,String> parameters) {
        DataDictionary dd=super.loadDataDictionary(parameters);

        URL urlDictionary;
        if ((parameters.get("elf.startup.dictionaryUri")!=null) && (parameters.get("elf.startup.dictionaryUri").trim().equals("")==false)) {
            try {
                urlDictionary = new URL(parameters.get("elf.startup.dictionaryUri"));
            } catch (MalformedURLException ex) {
                throw new RuntimeException("La URL del diccionario que hay en el parámetro 'elf.startup.dictionaryUri' del web.xml no es válida:"+parameters.get("elf.startup.dictionaryUri"),ex);
            }
        } else {
            String path="/dictionary.xml";

            ClassLoader cl = this.getClass().getClassLoader();
            if (cl==null) {
                urlDictionary=ClassLoader.getSystemResource(path);
            }
            urlDictionary=cl.getResource(path);
        }
        
        Map<String, Object> xmlParameters = new HashMap<String, Object>();
        xmlParameters.put("source",urlDictionary.toExternalForm());
        DefKernelConnectionFactory defKernelConnectionFactory = new DefKernelConnectionFactory("DICTIONARY", KernelConnectionFactoryImplXML.class.getName(), xmlParameters);
        dd.getDefConnection().getDefKernelConnectionFactories().put("DICTIONARY",defKernelConnectionFactory);


        dd.getDefSecurity().getDefIdentificationAgents().clear();
        Map params=new HashMap();
        params.put("userTableName", "Usuario");
        params.put("userRolesTableName", "UsuarioRole");
        params.put("roleTableName", "Role");
        DefIdentificationAgent defIdentificationAgent=new DefIdentificationAgent(IdentificationAgentImplPop3SSL.class.getName(), params);
        dd.getDefSecurity().getDefIdentificationAgents().addDefIdentificationAgent(defIdentificationAgent);

        return dd;
        
    }

}
