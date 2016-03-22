package com.github.achatain.nopasswordauthentication.app;

import com.github.achatain.nopasswordauthentication.utils.TokenUtils;
import com.google.common.base.Preconditions;
import org.apache.commons.validator.routines.EmailValidator;

import javax.inject.Inject;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

class AppService {

    private AppRepository appRepository;

    @Inject
    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    String create(String ownerEmail, String name, String callbackUrl, String emailTemplate) {
        Preconditions.checkArgument(isNotBlank(ownerEmail), "Parameter [ownerEmail] should not be blank");
        Preconditions.checkArgument(isNotBlank(name), "Parameter [name] should not be blank");
        Preconditions.checkArgument(isNotBlank(callbackUrl), "Parameter [callbackUrl] should not be blank");
        Preconditions.checkArgument(EmailValidator.getInstance().isValid(ownerEmail), String.format("Email address [%s] is invalid", ownerEmail));

        String apiToken = TokenUtils.generate();

        App app = App.create()
                .withOwnerEmail(ownerEmail)
                .withName(name)
                .withCallbackUrl(callbackUrl)
                .withEmailTemplate(emailTemplate)
                .withApiToken(TokenUtils.hash(apiToken))
                .build();

        appRepository.save(app);

        return apiToken;
    }
}
