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

import com.github.achatain.nopasswordauthentication.utils.TokenService;
import com.google.common.base.Preconditions;
import org.apache.commons.validator.routines.EmailValidator;

import javax.inject.Inject;

import static com.github.achatain.nopasswordauthentication.utils.MsgResources.invalidEmail;
import static com.github.achatain.nopasswordauthentication.utils.MsgResources.paramShouldNotBeBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class AppService {

    private final AppRepository appRepository;
    private final TokenService tokenService;

    @Inject
    public AppService(AppRepository appRepository, TokenService tokenService) {
        this.appRepository = appRepository;
        this.tokenService = tokenService;
    }

    String create(AppDto appDto) {
        Preconditions.checkArgument(isNotBlank(appDto.getOwnerEmail()), paramShouldNotBeBlank("ownerEmail"));
        Preconditions.checkArgument(isNotBlank(appDto.getName()), paramShouldNotBeBlank("name"));
        Preconditions.checkArgument(isNotBlank(appDto.getCallbackUrl()), paramShouldNotBeBlank("callbackUrl"));
        Preconditions.checkArgument(EmailValidator.getInstance().isValid(appDto.getOwnerEmail()), invalidEmail(appDto.getOwnerEmail()));

        // TODO check that this apiToken is not already present in the Datastore (don't forget to hash it before checking)
        String apiToken = tokenService.generate();

        App app = App.builder()
                .withOwnerEmail(appDto.getOwnerEmail())
                .withName(appDto.getName())
                .withCallbackUrl(appDto.getCallbackUrl())
                .withEmailTemplate(appDto.getEmailTemplate())
                .withApiToken(tokenService.hash(apiToken))
                .build();

        appRepository.save(app);

        return apiToken;
    }

    public App findByApiToken(String apiToken) {
        return appRepository.findByApiToken(apiToken);
    }
}
