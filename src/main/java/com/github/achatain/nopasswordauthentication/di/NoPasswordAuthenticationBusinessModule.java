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

package com.github.achatain.nopasswordauthentication.di;

import com.github.achatain.nopasswordauthentication.app.AppRepository;
import com.github.achatain.nopasswordauthentication.app.AppRepositoryImpl;
import com.github.achatain.nopasswordauthentication.auth.AuthRepository;
import com.github.achatain.nopasswordauthentication.auth.AuthRepositoryImpl;
import com.github.achatain.nopasswordauthentication.email.EmailService;
import com.github.achatain.nopasswordauthentication.email.EmailServiceFactory;
import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.googlecode.objectify.ObjectifyFilter;

import javax.inject.Singleton;

class NoPasswordAuthenticationBusinessModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ObjectifyFilter.class).in(Singleton.class);
        bind(AppRepository.class).to(AppRepositoryImpl.class).in(Singleton.class);
        bind(AuthRepository.class).to(AuthRepositoryImpl.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    private Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    private EmailService provideEmailService() {
        return EmailServiceFactory.getEmailService();
    }
}
