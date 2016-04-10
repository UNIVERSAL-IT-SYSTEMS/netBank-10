package services;

import java.util.Properties;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import logging.LoggingInterceptor;

/**
 *
 * @author Daniel Szabo
 */
@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class EmailSendingService {

    public void sendEmail(String to, String subject, String content) {



        final String username = "szdtbank@gmail.com";
        final String password = "abcde01248";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("szdtbank@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(content);
            Transport.send(msg);

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}
