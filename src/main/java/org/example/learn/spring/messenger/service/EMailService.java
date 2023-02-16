package org.example.learn.spring.messenger.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EMailService {
    private EMailService() {
    }

    private final String addressEmailFrom = "testjavasendwrite@mail.ru";
    private final String userName = "testjavasendwrite@mail.ru";
    private final String userPassword = "MNgUpt6iPqe7UaRKff4N";
    private String mailServerHost = "smtp.mail.ru";
    private String port = "465";


    public void sendEMail(String addressEmailTo, String textForSubject, String textForTExt) {

        String to = addressEmailTo;
        String from = addressEmailFrom;

        Properties props = new Properties();

        String host = mailServerHost;            // mail server host
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.connectiontimeout", 5000);
        props.put("mail.smtp.timeout", 5000);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.ssl.trust", "*");

        Session session = Session.getInstance(props, getMyAuthenticator());

        MimeMessage mimeMessage = new MimeMessage(session);
        try {

            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setSubject(textForSubject);
            mimeMessage.setText(textForTExt);

            Transport.send(mimeMessage);
            System.out.println("Email Sent successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public Authenticator getMyAuthenticator() {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, userPassword);
            }
        };
    }


}
