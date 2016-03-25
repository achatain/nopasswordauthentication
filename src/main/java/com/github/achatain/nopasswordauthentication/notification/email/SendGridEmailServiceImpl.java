/*
 * https://github.com/achatain/nopasswordauthentication
 *
 * Copyright (C) 2016 Antoine R. "achatain" (achatain [at] outlook [dot] com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.achatain.nopasswordauthentication.notification.email;

import com.github.achatain.nopasswordauthentication.exception.InternalServerException;
import com.github.achatain.nopasswordauthentication.utils.AppSettings;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import java.util.logging.Level;
import java.util.logging.Logger;

class SendGridEmailServiceImpl implements EmailService {

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
