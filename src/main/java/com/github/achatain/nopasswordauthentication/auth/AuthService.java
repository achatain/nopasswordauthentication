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

package com.github.achatain.nopasswordauthentication.auth;

import com.github.achatain.nopasswordauthentication.app.App;
import com.github.achatain.nopasswordauthentication.app.AppService;
import com.github.achatain.nopasswordauthentication.email.EmailService;
import com.github.achatain.nopasswordauthentication.utils.TokenUtils;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import javax.inject.Inject;
import java.util.Date;
import java.util.logging.Logger;

import static com.github.achatain.nopasswordauthentication.utils.MsgResources.invalidEmail;
import static com.github.achatain.nopasswordauthentication.utils.MsgResources.paramShouldNotBeBlank;

class AuthService {

    private static final Logger LOG = Logger.getLogger(AuthService.class.getName());

    private static final String CALLBACK_TEMPLATE = "https://%s?uid=%s&token=%s";

    private final AppService appService;
    private final EmailService emailService;
    private final AuthRepository authRepository;

    @Inject
    public AuthService(AppService appService, EmailService emailService, AuthRepository authRepository) {
        this.appService = appService;
        this.emailService = emailService;
        this.authRepository = authRepository;
    }

    void auth(AuthRequest authRequest) {
        Preconditions.checkArgument(StringUtils.isNotBlank(authRequest.getApiToken()), paramShouldNotBeBlank("apiToken"));
        Preconditions.checkArgument(EmailValidator.getInstance().isValid(authRequest.getUserEmail()), invalidEmail(authRequest.getUserEmail()));

        // 1. Hash the received api token and find the matching app
        App foundApp = appService.findByApiToken(TokenUtils.hash(authRequest.getApiToken()));
        Preconditions.checkState(foundApp != null, "No application matching this api token was found");

        // 2. Create an Auth entity with userEmail | authToken | appId | timestamp
        String authToken = TokenUtils.generate();

        Auth auth = Auth.create()
                .withAppId(foundApp.getId())
                .withUserId(authRequest.getUserEmail())
                .withTimestamp(new Date().getTime())
                .withToken(TokenUtils.hash(authToken))
                .build();
        authRepository.save(auth);

        // 3. Build the callbackUrl with query params
        String callbackUrl = String.format(CALLBACK_TEMPLATE, foundApp.getCallbackUrl(), auth.getUserId(), authToken);

        // 4. Send an email to userEmail containing the callbackUrl
        emailService.sendEmail(
                foundApp.getId(),
                foundApp.getOwnerEmail(),
                foundApp.getName(),
                authRequest.getUserEmail(),
                "No password authentication",
                callbackUrl);
    }
}
