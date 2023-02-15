import com.sun.mail.pop3.POP3Store;
import com.sun.mail.util.MailSSLSocketFactory;
import jakarta.mail.*;
import messenger.menegment.InstanceFactory;
import messenger.service.EMailService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Properties;


public class TestMailService {
    private static String mailAddress = "testjavasendwrite@mail.ru";
  private   static String mailPassword = "MNgUpt6iPqe7UaRKff4N";
   private static String textForSubject = generateRandomString(8);


//    @Ignore
    @Test
    public void testingEMailSending() {
        EMailService eMailService = InstanceFactory.getInstance(EMailService.class);
        eMailService.sendEMail(mailAddress, textForSubject, "test");
        TestMailService.receivingEmail();
//        String host = "pop.mail.ru";//change accordingly
//        String mailStoreType = "pop3";
//
//        receiveEmail(host, mailStoreType, mailAddress, mailPassword);
    }


    public static void receivingEmail()  {
        Properties props = new Properties();

        String host = "imap.mail.ru";            // mail server host
        props.put("mail.imap.connectiontimeout", 1000);
        props.put("mail.imap.timeout", 1000);
        props.put("mail.imap.com", host );
        props.put("mail.imap.starttls.enable","true");
        props.put("mail.imap.auth", "true");  // If you need to authenticate
        props.put("mail.imap.ssl.trust", "*");

        EMailService eMailService = InstanceFactory.getInstance(EMailService.class);
        Session session = Session.getInstance(props, eMailService.getMyAuthenticator());

        boolean eMailIsReceived = false;
        try {
            Store store = session.getStore("imap");
            store.connect(host,mailAddress, mailPassword);

            Folder[] folders = store.getDefaultFolder().list("*");
            for (Folder folder : folders) {
                System.out.println(folder.getName() + " --- " + folder.getFullName());
            }

            Folder defaultFolder = store.getFolder("INBOX/ToMyself");
            defaultFolder.open(Folder.READ_WRITE);
            Message[] messages = defaultFolder.getMessages();
            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                if (message.getSubject().equals(textForSubject)) {
                    eMailIsReceived = true;
                    defaultFolder.setFlags(i+1, i+1, new Flags(Flags.Flag.DELETED), true);
                }
                System.out.println("From: " + message.getFrom()[0]);
            }
            defaultFolder.close();
            store.close();

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(eMailIsReceived);

    }

    public static void receiveEmail(String pop3Host, String storeType,
                                    String user, String password) {
        try {
            //1) get the session object
            Properties properties = new Properties();
            properties.put("mail.pop3.host", pop3Host);
            properties.put("mail.pop3.ssl.enable", "true" );

            Session emailSession = Session.getDefaultInstance(properties);

            //2) create the POP3 store object and connect with the pop server
            POP3Store emailStore = (POP3Store) emailSession.getStore(storeType);
            emailStore.connect(user, password);

            //3) create the folder object and open it
            Folder emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            //4) retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
            }

            //5) close the store and folder objects
            emailFolder.close(false);
            emailStore.close();

        } catch (NoSuchProviderException e) {e.printStackTrace();}
        catch (MessagingException e) {e.printStackTrace();}
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateRandomString(int length) {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
//            System.out.format("%dt:t%c%n", rndCharAt, rndChar);
            sb.append(rndChar);
        }
        return sb.toString();
    }

}
