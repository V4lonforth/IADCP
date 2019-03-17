package com.iad.courseProject;

import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Named
public class MessageController {

    public MimeMessage getBaseMessage() throws MessagingException {
        MimeMessage msg = new MimeMessage(getSession());
        msg.setFrom(new InternetAddress("testpipaaccout@gmail.com"));
        msg.setSentDate(new Date());
        return msg;
    }
    private Session getSession() {
        final String username = "testpipaaccout@gmail.com";
        final String password = "rsv31526";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
