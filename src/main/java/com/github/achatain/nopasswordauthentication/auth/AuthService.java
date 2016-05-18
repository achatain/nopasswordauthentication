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
import com.github.achatain.nopasswordauthentication.utils.AppSettings;
import com.github.achatain.nopasswordauthentication.utils.TokenService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import javax.inject.Inject;
import java.util.Date;
import java.util.logging.Logger;

import static com.github.achatain.nopasswordauthentication.utils.MsgResources.invalidEmail;
import static com.github.achatain.nopasswordauthentication.utils.MsgResources.paramShouldNotBeBlank;

public class AuthService {

    private static final Logger LOG = Logger.getLogger(AuthService.class.getName());

    private static final String CALLBACK_TEMPLATE = "https://%s?uid=%s&token=%s";

    private final AppService appService;
    private final EmailService emailService;
    private final AuthRepository authRepository;
    private final TokenService tokenService;

    @Inject
    public AuthService(AppService appService, EmailService emailService, AuthRepository authRepository, TokenService tokenService) {
        this.appService = appService;
        this.emailService = emailService;
        this.authRepository = authRepository;
        this.tokenService = tokenService;
    }

    void request(AuthRequest authRequest) {
        Preconditions.checkArgument(StringUtils.isNotBlank(authRequest.getApiToken()), paramShouldNotBeBlank("apiToken"));
        Preconditions.checkArgument(EmailValidator.getInstance().isValid(authRequest.getUserId()), invalidEmail(authRequest.getUserId()));

        // 1. Hash the received api token and find the matching app
        App foundApp = appService.findByApiToken(tokenService.hash(authRequest.getApiToken()));
        Preconditions.checkState(foundApp != null, "Unrecognized client application");

        // 2. Create an Auth entity with userEmail | authToken | appId | timestamp
        String authToken = tokenService.generate();

        Auth auth = Auth.create()
                .withAppId(foundApp.getId())
                .withUserId(authRequest.getUserId())
                .withTimestamp(new Date().getTime())
                .withToken(tokenService.hash(authToken))
                .build();
        authRepository.save(auth);

        // 3. Build the callbackUrl with query params
        String callbackUrl = String.format(CALLBACK_TEMPLATE, foundApp.getCallbackUrl(), auth.getUserId(), authToken);

        // 4. Send an email to userEmail containing the callbackUrl
        emailService.sendEmail(
                foundApp.getId(),
                foundApp.getOwnerEmail(),
                foundApp.getName(),
                authRequest.getUserId(),
                "No password authentication",
                callbackUrl);
    }

    boolean verify(AuthVerify authVerify) {
        // 1. Hash the received api token and find the matching app
        App foundApp = appService.findByApiToken(tokenService.hash(authVerify.getApiToken()));
        Preconditions.checkState(foundApp != null, "Unrecognized client application");

        // 2. Find the Auth entity matching appId and userId
        Auth auth = authRepository.findAndDelete(foundApp.getId(), authVerify.getUserId());
        Preconditions.checkState(auth != null, "No matching auth request");

        // 3. Ensure that the Auth entity is not expired
        Preconditions.checkState(new Date().getTime() < AppSettings.getAuthExpiry() + auth.getTimestamp(), "Auth request has expired");

        // 4. Hash the received token and check against the found Auth entity
        boolean verified = StringUtils.equals(tokenService.hash(authVerify.getToken()), auth.getToken());

        if (verified) {
            LOG.info(String.format("Successful authentication against app [%s] for user [%s]", foundApp.getId(), authVerify.getUserId()));
        } else {
            LOG.info(String.format("Failed authentication against app [%s] for user [%s]", foundApp.getId(), authVerify.getUserId()));
        }

        return verified;
    }
}
