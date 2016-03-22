package com.github.achatain.nopasswordauthentication.di;

import com.github.achatain.nopasswordauthentication.app.AppRepository;
import com.github.achatain.nopasswordauthentication.app.AppRepositoryImpl;
import com.google.inject.AbstractModule;
import com.googlecode.objectify.ObjectifyFilter;

import javax.inject.Singleton;

class NoPasswordAuthenticationBusinessModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ObjectifyFilter.class).in(Singleton.class);
        bind(AppRepository.class).to(AppRepositoryImpl.class);
    }
}
