/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.logongas.incidencias.businesslogic.persistence;

import org.elf.businesslayer.kernel.impl.persistence.KernelPersistenceImplSingleJDBC;
import org.elf.businesslayer.kernel.services.record.KernelFields;
import org.elf.datalayer.DLSession;
import org.elf.datalayer.RecordSet;

/**
 *
 * @author Lorenzo
 */
public class Incidencia extends KernelPersistenceImplSingleJDBC {

    @Override
    public boolean read(KernelFields fields, String connexion) {
        boolean read= super.read(fields, connexion);

        if (read==true) {
            RecordSet rst = DLSession.getConnection().executeQuery("(" + connexion + ")"+"SELECT SUM(Duracion) FROM IncidenciaTarea WHERE IdIncidencia=?", fields.getValue("IdIncidencia"), fields.getDefColumn("IdIncidencia").getDataType());
            rst.next();
            fields.setValue("Duracion", rst.getInt(0));
        }

        return read;
    }




}
