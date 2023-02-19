package utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ConfirmEmail {

    private String SMTP_HOST = "smtp.gmail.com";
    private String FROM_ADDRESS = "bookingproiect@gmail.com";
    private String PASSWORD = "etmmtigkidcovffd";
    private String FROM_NAME = "Booking";

    public boolean sendMail(String recipient, String bccRecipient, String message) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "false");
            props.put("mail.smtp.ssl.enable", "true");

            Session session = Session.getInstance(props, new SocialAuth());
            Message msg = new MimeMessage(session);

            InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);
            msg.setFrom(from);

            InternetAddress toAddress = new InternetAddress();
            toAddress = new InternetAddress(recipient);
            msg.setRecipient(Message.RecipientType.TO, toAddress);


            InternetAddress bccAddress = new InternetAddress();
            bccAddress = new InternetAddress(bccRecipient);
            msg.setRecipient(Message.RecipientType.BCC, bccAddress);

            msg.setSubject("Confirmare email");
            msg.setContent(message, "text/plain");
            Transport.send(msg);
            return true;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConfirmEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } catch (MessagingException ex) {
            Logger.getLogger(ConfirmEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    class SocialAuth extends Authenticator {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {

            return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);

        }
    }
}
