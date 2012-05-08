package es.logongas.incidencias.util.notify;

import java.util.List;

/**
 * Interfaz que abstrae del método de notificación.
 */
public interface Notify {

    /**
     * Notitica a una serie de usuarios
     * @param logins Lista de usuarios
     * @param subject Asunto de la notificación
     * @param message Mensaje con el texto de la notificación.
     */
    void notify(List<String> logins,String subject,String message);
    
}
