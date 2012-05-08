/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.logongas.incidencias.util.notify;

/**
 * Factoria de clases Notify
 */
public class NotifyFactory {

    /**
     * Crea una nueva clase Notify
     * @return una nueva clase Notify
     */
    public static Notify createNotify() {
        return new NotifyImplEMail();
    }

}
