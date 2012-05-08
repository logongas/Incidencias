/*
 * KernelAuthorizationImplIncidencias.java
 *
 * Created on 11 de marzo de 2008, 8:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package es.logongas.incidencias.security;

import org.elf.businesslayer.BLSession;
import org.elf.businesslayer.dictionary.DefTable;
import org.elf.businesslayer.kernel.services.record.KernelFields;
import org.elf.businesslayer.kernel.services.record.businesslogic.BusinessLogicOperation;
import org.elf.datalayer.*;
import org.elf.datalayer.kernel.services.security.*;
import org.elf.weblayer.controllers.genericmant.GenericMant;

/**
 *
 * @author Administrador
 */
public class KernelAuthorizationImplDeveloper implements KernelAuthorization {

    public AccessType isAccessAuthorized(Object object, Object method) {
        AccessType accessType = null;
        Roles roles = DLSession.getSecurity().getUserIdentification().getRoles();
        String login = DLSession.getSecurity().getUserIdentification().getLogin();

        if (object instanceof GenericMant) {
            Object[] arrayMethod = (Object[]) method;
            String tablename = (String)arrayMethod[0];
            DefTable defTable = BLSession.getDictionary().getDefTables().getDefTable(tablename);

            if ("DICTIONARY".equalsIgnoreCase(defTable.getConnectionName())) {
                if (roles.containsKey("DEVELOPER") == true) {
                    accessType = AccessType.Granted;
                } else {
                    accessType = AccessType.Deny;
                }
            } else {
                accessType = AccessType.Abstain;
            }
        } else if (object instanceof KernelFields) {
            BusinessLogicOperation businessLogicOperation = (BusinessLogicOperation) method;
            KernelFields kernelFields = ((KernelFields) object);
            String tablename = kernelFields.getTableName();
            DefTable defTable = BLSession.getDictionary().getDefTables().getDefTable(tablename);



            if ("DICTIONARY".equalsIgnoreCase(defTable.getConnectionName())) {
                if (roles.containsKey("DEVELOPER") == true) {
                    accessType = AccessType.Granted;
                } else {
                    accessType = AccessType.Deny;
                }
            } else {
                accessType = AccessType.Abstain;
            }
        } else {
            accessType = AccessType.Abstain;
        }



        return accessType;
    }
}
