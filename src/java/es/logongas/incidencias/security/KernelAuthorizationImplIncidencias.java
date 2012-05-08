/*
 * KernelAuthorizationImplIncidencias.java
 *
 * Created on 11 de marzo de 2008, 8:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package es.logongas.incidencias.security;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.*;
import org.elf.businesslayer.kernel.services.record.KernelFields;
import org.elf.businesslayer.kernel.services.record.businesslogic.BusinessLogicOperation;
import org.elf.common.*;
import org.elf.datalayer.*;
import org.elf.datalayer.kernel.services.security.*;
/**
 *
 * @author Administrador
 */
public class KernelAuthorizationImplIncidencias implements KernelAuthorization {
    

    public AccessType isAccessAuthorized(Object object,Object method) {
        AccessType accessType=null;
        
        if (object instanceof URI) {
                String url=((URI)object).toString();            

                List<String> urlPrefixGranted=new ArrayList<String>();
                Collections.addAll(urlPrefixGranted,
                    "/common/javascript",
                    "/controls/javascript",
                    "/controls/img",
                    "/controls/css",
                    "/img",
                    "/js",
                    "/css",
                    "/icons",
                    "/controller/DLSession/service/createSession");

                for(int i=0;i<urlPrefixGranted.size();i++) {
                    if (url.startsWith(urlPrefixGranted.get(i))) {
                        accessType=AccessType.GrantedIfAllOthersAbstain;
                    }
                }
                List<String> urlGranted=new ArrayList<String>();
                for(int i=0;i<urlGranted.size();i++) {
                    if (url.equals(urlGranted.get(i))) {
                        accessType=AccessType.GrantedIfAllOthersAbstain;
                    }
                }            

                if (accessType==null) {
                    if (DLSession.getSecurity().isPublic()==true) {
                        if (StringUtil.stringCount(url,"/")>1) {
                            accessType=AccessType.DenyIfAllOthersAbstain;
                        } else {
                            accessType=AccessType.GrantedIfAllOthersAbstain;
                        }                        
                    }  else {
                        accessType=AccessType.GrantedIfAllOthersAbstain;
                    }                    

                }             
        } else if (object instanceof KernelFields) {
            KernelFields kernelFields=((KernelFields)object);      
            Roles roles=DLSession.getSecurity().getUserIdentification().getRoles();
            String login=DLSession.getSecurity().getUserIdentification().getLogin();
            BusinessLogicOperation businessLogicOperation=(BusinessLogicOperation)method;
            boolean owened;
            
            if (kernelFields.getTableName().equals("Incidencia")) {
                owened=login.equals(kernelFields.getValue("Login"));
                if (roles.containsKey("ADMIN")==true) {
                    //Un Administrador puede hacer lo que quiera con una Incidencia
                    accessType=AccessType.GrantedIfAllOthersAbstain;
                } else if (roles.containsKey("TECNICO")==true) {
                    //Los tecnicos pueden hacer lo que quieran menos borrar una incidencia que NO crearon ellos.
                    //Siempre dentro de sus proyectos.
                    if (businessLogicOperation==BusinessLogicOperation.DELETE) {
                        if (owened==true) {
                            accessType=AccessType.GrantedIfAllOthersAbstain;
                        } else {
                            accessType=AccessType.DenyIfAllOthersAbstain;
                        }
                    } else {
                        accessType=AccessType.GrantedIfAllOthersAbstain;
                    }                    
                    
                } else if (roles.containsKey("USER")==true) {
                    //Los usuarios no pueden borrar Incidencias
                    if (businessLogicOperation==BusinessLogicOperation.DELETE) {
                        accessType=AccessType.DenyIfAllOthersAbstain;
                    } else {
                        accessType=AccessType.GrantedIfAllOthersAbstain;
                    }
                } else {
                    accessType=AccessType.DenyIfAllOthersAbstain;
                }
            }else if (kernelFields.getTableName().equals("IncidenciaTarea")) {
                owened=login.equals(kernelFields.getValue("Login"));
                if (roles.containsKey("ADMIN")==true) {
                    accessType=AccessType.GrantedIfAllOthersAbstain;
                } else if (roles.containsKey("TECNICO")==true) {
                    //Los tecnicos solo pueden borrar o modificar tareas que ellos han creado.
                    if ((businessLogicOperation==BusinessLogicOperation.DELETE) || (businessLogicOperation==BusinessLogicOperation.UPDATE)){
                        if (owened==true) {
                            accessType=AccessType.GrantedIfAllOthersAbstain;
                        } else {
                            accessType=AccessType.DenyIfAllOthersAbstain;
                        }
                    } else {
                        accessType=AccessType.GrantedIfAllOthersAbstain;
                    }
                } else if (roles.containsKey("USER")==true) {
                    //Los usuarios solo pueden ver las tareas.
                    if (
                            (businessLogicOperation==BusinessLogicOperation.DELETE)
                            || (businessLogicOperation==BusinessLogicOperation.UPDATE)
                            || (businessLogicOperation==BusinessLogicOperation.INSERT)
                        ){
                        accessType=AccessType.DenyIfAllOthersAbstain;
                    } else {
                        accessType=AccessType.GrantedIfAllOthersAbstain;
                    } 
                } else {
                    accessType=AccessType.DenyIfAllOthersAbstain;
                }
            } else {
                if (roles.containsKey("ADMIN")==true) {
                    accessType=AccessType.GrantedIfAllOthersAbstain;
                } else if (roles.containsKey("TECNICO")==true) {
                    accessType=AccessType.DenyIfAllOthersAbstain;
                } else if (roles.containsKey("USER")==true) {
                    accessType=AccessType.DenyIfAllOthersAbstain;
                } else {
                    accessType=AccessType.DenyIfAllOthersAbstain;
                }
            }
                
        } else {
            //Si lo que se piede NO es una URL se deniega todo a "PUBLIC"
            //Y al resto se le permite todo
            if (DLSession.getSecurity().isPublic()==true) {
                accessType=AccessType.DenyIfAllOthersAbstain;
            }  else {
                accessType=AccessType.GrantedIfAllOthersAbstain;
            }
        }
        

        
        return accessType;
    }
    
}
