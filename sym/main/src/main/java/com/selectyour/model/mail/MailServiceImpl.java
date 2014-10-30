package com.selectyour.model.mail;//Dzmitry Maiseyenka: 27.05.2014 15:16

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {
    public static final String HOST = "mail.selectartforyourself.com";
    public static final String FROM_EMAIL = "info@selectartforyourself.com";
    public static final String PASS = "minfo1";
    public static final String TO_EMAIL = "for-dm@yandex.ru";

    public static void main(String[] args) throws Exception {
        MailService mailService = new MailServiceImpl();
        mailService.sendPictureOrder("Новый заказ");
    }

    public void sendPictureOrder(String content) throws IOException {
        sendMail(TO_EMAIL, FROM_EMAIL, "Новый заказ на картину", content);
    }

    public void sendVisualizationOrder(String content) throws IOException {
        sendMail(TO_EMAIL, FROM_EMAIL, "Новый заказ на визуализацию", content);
    }

    public void sendMail(String to, String from, String subject,
                         String content) throws IOException {
        try {
            sendMailInternal(to, from, subject, content);
        } catch (Exception e) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                sendMailWithLogException(to, from, subject, content);
            }
            sendMailWithLogException(to, from, subject, content);
        }
    }

    private void sendMailWithLogException(String to, String from, String subject, String content) throws IOException {
        try {
            sendMailInternal(to, from, subject, content);
        } catch (Exception e2) {
            throw new IOException(String.format("Error during sending mail from %s to %s subject %s content\n %s",
                    from, to, subject, content), e2);
        }
    }

    private void sendMailInternal(String to, String from, String subject,
                                  String content) throws Exception {


        String host = HOST;
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        SMTPAuthenticator auth = new SMTPAuthenticator();

        Session session = Session.getDefaultInstance(props, auth);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] address = {new InternetAddress(to)};
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(content);

        Transport.send(msg);
    }

    private static class SMTPAuthenticator extends javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(FROM_EMAIL, PASS); //TODO: move to properties
        }
    }
}