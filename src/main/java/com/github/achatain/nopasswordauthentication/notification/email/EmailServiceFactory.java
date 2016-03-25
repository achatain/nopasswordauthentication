package com.github.achatain.nopasswordauthentication.notification.email;

import com.github.achatain.nopasswordauthentication.utils.AppSettings;

public final class EmailServiceFactory {

    public static EmailService getEmailService() {
        switch (AppSettings.getEmailProvider()) {
            case AppSettings.EMAIL_PROVIDER_SENDGRID:
                return new SendGridEmailServiceImpl();
            case AppSettings.EMAIL_PROVIDER_APPENGINE:
            default:
                return new AppEngineEmailServiceImpl();
        }
    }
}
