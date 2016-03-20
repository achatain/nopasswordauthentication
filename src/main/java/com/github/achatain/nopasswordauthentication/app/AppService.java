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

    String create(String id, String owner, String name, String callbackUrl, String emailTemplate) {
        Preconditions.checkArgument(isNotBlank(id));
        Preconditions.checkArgument(isNotBlank(owner));
        Preconditions.checkArgument(isNotBlank(name));
        Preconditions.checkArgument(isNotBlank(callbackUrl));

        Preconditions.checkArgument(EmailValidator.getInstance().isValid(owner), String.format("Email address [%s] is invalid", owner));
        Preconditions.checkArgument(appRepository.find(id) == null, String.format("id [%s] already exists", id));

        return appRepository.create(id, owner, name, TokenUtils.generate(), callbackUrl, emailTemplate);
    }
}
