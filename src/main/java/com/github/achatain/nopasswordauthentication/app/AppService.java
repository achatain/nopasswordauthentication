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

package com.github.achatain.nopasswordauthentication.app;

import com.github.achatain.nopasswordauthentication.utils.TokenUtils;
import com.google.common.base.Preconditions;
import org.apache.commons.validator.routines.EmailValidator;

import javax.inject.Inject;

import static com.github.achatain.nopasswordauthentication.utils.MsgResources.invalidEmail;
import static com.github.achatain.nopasswordauthentication.utils.MsgResources.paramShouldNotBeBlank;
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
