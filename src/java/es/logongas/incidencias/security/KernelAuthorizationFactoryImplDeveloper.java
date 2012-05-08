/*
 * KernelAuthorizationFactoryImplIncidencias.java
 *
 * Created on 11 de marzo de 2008, 8:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package es.logongas.incidencias.security;

import org.elf.datalayer.kernel.KernelSession;
import org.elf.datalayer.kernel.services.security.*;

/**
 *
 * @author Administrador
 */
public class KernelAuthorizationFactoryImplDeveloper implements KernelAuthorizationFactory {
    
    public void init(KernelSession kernelSession) {
    }

    public KernelAuthorization createKernelAuthorization() {
        return new KernelAuthorizationImplDeveloper();
    }
    
}
