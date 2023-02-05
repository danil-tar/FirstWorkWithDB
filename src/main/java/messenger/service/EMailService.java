package messenger.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.security.SecureRandom;
import java.util.Properties;

public class EMailService {

    private static EMailService instance = null;

    private EMailService() {
    }

    public static synchronized EMailService getInstance() {
        if (instance == null) {
            instance = new EMailService();
        }
        return instance;
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

//        props.put("mail.smtp.starttls.enable", false);
//        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.ssl.trust", "*");

//        props.put("mail.smtps.ssl.checkserveridentity", true);

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
