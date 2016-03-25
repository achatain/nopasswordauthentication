package com.github.achatain.nopasswordauthentication.notification.email;

public interface EmailService {

    boolean sendEmail(Long appId, String from, String fromName, String to, String subject, String text);
}
