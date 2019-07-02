package com.sb.solutions.core.utils.email;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    public void sendMailWithAttachmentBcc(Email email) throws MessagingException, IOException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // check the authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        MimeMessage message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(username));
        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));

        List<String> items = email.getBcc() == null ? new ArrayList<>() : email.getBcc();
        if (!items.isEmpty()) {

            for (String recipient : items) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(
                    recipient));
            }
        }

        // Set Subject: header field
        message.setSubject(email.getSubject());

        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setContent(email.getBody(), "text/html");

        // Create a multipart message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();

        List<String> item =
            email.getAttachment() == null ? new ArrayList<>() : email.getAttachment();

        for (String attached : item) {
            if (attached != null) {
                Part attachment = new MimeBodyPart();
                URL url = new URL(attached);

                attachment.setDataHandler(new DataHandler(url));
                attachment.setDisposition(Part.ATTACHMENT);

                /*List<String> files = Arrays.asList(attached.split("/"));
                String fileName = "";
                for (String a : files) {
                    fileName = a;
                }*/

                attachment.setFileName("test");
                multipart.addBodyPart((BodyPart) attachment);
            }
        }
        // Send the complete message parts
        message.setContent(multipart);

        // Send message
        Transport.send(message);
    }
}
