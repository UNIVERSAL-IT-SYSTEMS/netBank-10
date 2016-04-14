package services;

import enums.EmailType;
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
    

    private static final String FOOTER ="\n\n" + "Üdvözlettel, SZDT Bank!";
    
    
    private void send(String to, String subject, String content) {
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
    
    public void sendEmail(String toName, String toMail, EmailType type){
        
        StringBuilder sb = new StringBuilder();
        sb.append("Tisztelt ");
        sb.append(toName);
        sb.append(" ! \n");
        sb.append("\n");
        sb.append(type.getMessage());
        sb.append(FOOTER);
        String messageBody = sb.toString();
        
        send(toMail, type.getSubject(), messageBody);
    }
    
    public void sendRefuseMail(String toName, String toMail, EmailType type, String message){
        StringBuilder sb = new StringBuilder();
        sb.append("Tisztelt ");
        sb.append(toName);
        sb.append(" ! \n");
        sb.append("\n");
        sb.append(type.getMessage());
        sb.append(message);
        sb.append(FOOTER);
        String messageBody = sb.toString();
        
        send(toMail, type.getSubject(), messageBody);
    }
    
    
    
}
