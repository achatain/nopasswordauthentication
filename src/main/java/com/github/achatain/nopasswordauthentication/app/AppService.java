package com.github.achatain.nopasswordauthentication.app;

import com.github.achatain.nopasswordauthentication.utils.TokenUtils;
import com.google.common.base.Preconditions;
import org.apache.commons.validator.routines.EmailValidator;

import javax.inject.Inject;

import static com.github.achatain.nopasswordauthentication.utils.Msg.invalidEmail;
import static com.github.achatain.nopasswordauthentication.utils.Msg.paramShouldNotBeBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class AppService {

    private AppRepository appRepository;

    @Inject
    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    String create(String ownerEmail, String name, String callbackUrl, String emailTemplate) {
        Preconditions.checkArgument(isNotBlank(ownerEmail), paramShouldNotBeBlank("ownerEmail"));
        Preconditions.checkArgument(isNotBlank(name), paramShouldNotBeBlank("name"));
        Preconditions.checkArgument(isNotBlank(callbackUrl), paramShouldNotBeBlank("callbackUrl"));
        Preconditions.checkArgument(EmailValidator.getInstance().isValid(ownerEmail), invalidEmail(ownerEmail));

        // TODO check that this apiToken is not already present in the Datastore (don't forget to hash it before checking)
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

    public App findByApiToken(String apiToken) {
        return appRepository.findByApiToken(apiToken);
    }
}
