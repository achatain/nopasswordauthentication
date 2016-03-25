package com.github.achatain.nopasswordauthentication.notification.email;

import com.github.achatain.nopasswordauthentication.exception.InternalServerException;
import com.github.achatain.nopasswordauthentication.utils.AppSettings;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SendGridEmailServiceImpl implements EmailService {

    private static final Logger LOG = Logger.getLogger(SendGridEmailServiceImpl.class.getName());

    @Override
    public boolean sendEmail(Long appId, String from, String fromName, String to, String subject, String text) {
        SendGrid sendGrid = new SendGrid(AppSettings.getSendGridApiKey());

        SendGrid.Email email = new SendGrid.Email();
        email.setFrom(from);
        email.setFromName(fromName);
        email.addTo(to);
        email.setSubject(subject);
        email.setText(text);
        email.addCategory(appId.toString());

        SendGrid.Response response;

        try {
            response = sendGrid.send(email);
        } catch (SendGridException e) {
            LOG.log(Level.SEVERE, "Failed to send email via SendGrid.", e);
            throw new InternalServerException(e.getMessage(), e);
        }

        LOG.info(String.format("Email response: code is [%s], message is [%s]", response.getCode(), response.getMessage()));

        return response.getStatus();
    }
}
