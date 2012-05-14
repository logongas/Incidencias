/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.incidencias.util.notify;

import java.util.List;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.elf.datalayer.DLSession;

/**
 *
 * @author Lorenzo
 */
public class NotifyImplEMail implements Notify {

    public void notify(List<String> logins, String subject, String cuerpo) {
        try {

            boolean sendMails = (Boolean) DLSession.getConfig().getValue("notify.sendMail", false);
            if ((sendMails == true) && (logins.size() > 0)) {



                InternetAddress[] addressTo = new InternetAddress[logins.size()];

                for (int i = 0; i < logins.size(); i++) {
                    String correo = getCorreoFromUsuario(logins.get(i));

                    addressTo[i] = new InternetAddress(correo);
                }



                Session session = null;

                Context initCtx = new InitialContext();
                Context envCtx = (Context) initCtx.lookup("java:comp/env");
                session = (Session) envCtx.lookup("mail/MailSession");



                Message message = new MimeMessage(session);
                message.setRecipients(Message.RecipientType.TO, addressTo);
                message.setSubject(subject);
                message.setContent(cuerpo, "text/plain");
                Transport.send(message);
            }
        } catch (RuntimeException rex) {
            throw rex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


    }

    private String getCorreoFromUsuario(String login) {
        if ((login == null) || (login.trim().equals("") == true)) {
            throw new RuntimeException("El Login para enviar un correo no puede estar vacio");
        }

        String correo = login + "@" + DLSession.getConfig().getValue("login.emailDomain");

        return correo;
    }
}
