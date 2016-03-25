package com.github.achatain.nopasswordauthentication.di;

import com.github.achatain.nopasswordauthentication.app.AppRepository;
import com.github.achatain.nopasswordauthentication.app.AppRepositoryImpl;
import com.github.achatain.nopasswordauthentication.notification.email.EmailService;
import com.github.achatain.nopasswordauthentication.notification.email.EmailServiceFactory;
import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.googlecode.objectify.ObjectifyFilter;

import javax.inject.Singleton;

class NoPasswordAuthenticationBusinessModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ObjectifyFilter.class).in(Singleton.class);
        bind(AppRepository.class).to(AppRepositoryImpl.class);
    }

    @Provides
    private Gson provideGson() {
        return new Gson();
    }

    @Provides
    private EmailService provideEmailService() {
        return EmailServiceFactory.getEmailService();
    }
}
