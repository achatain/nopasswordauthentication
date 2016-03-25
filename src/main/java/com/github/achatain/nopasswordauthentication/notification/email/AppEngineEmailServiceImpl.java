package com.github.achatain.nopasswordauthentication.notification.email;

import com.github.achatain.nopasswordauthentication.utils.AppSettings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class AppEngineEmailServiceImpl implements EmailService {

    @Override
    public boolean sendEmail(Long appId, String from, String fromName, String to, String subject, String text) {
        Message msg = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
        try {
            msg.setFrom(new InternetAddress(AppSettings.getEmailSender(), fromName));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(text);
            Transport.send(msg);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return true;
    }
}
