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
