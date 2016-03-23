package com.github.achatain.nopasswordauthentication.auth;

import com.github.achatain.nopasswordauthentication.app.App;
import com.github.achatain.nopasswordauthentication.app.AppService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import javax.inject.Inject;

import static com.github.achatain.nopasswordauthentication.utils.Msg.invalidEmail;
import static com.github.achatain.nopasswordauthentication.utils.Msg.paramShouldNotBeBlank;

class AuthService {

    private AppService appService;

    @Inject
    public AuthService(AppService appService) {
        this.appService = appService;
    }

    void auth(AuthRequest authRequest) {
        Preconditions.checkArgument(StringUtils.isNotBlank(authRequest.getApiToken()), paramShouldNotBeBlank("apiToken"));
        Preconditions.checkArgument(EmailValidator.getInstance().isValid(authRequest.getUserEmail()), invalidEmail(authRequest.getUserEmail()));

        // 1. Hash the received api token and find the matching app
        App foundApp = appService.findByApiToken(authRequest.getApiToken());

        // 2. Create an Auth entity with userEmail | authToken | appId | timestamp
        // 3. Build the callbackUrl with query params
        // 4. Send an email to userEmail containing the callbackUrl
    }
}
