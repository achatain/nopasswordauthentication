package com.github.achatain.nopasswordauthentication.di;

import com.github.achatain.nopasswordauthentication.app.App;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.googlecode.objectify.ObjectifyService;

public class NoPasswordAuthenticationServletContextListener extends GuiceServletContextListener {

    static {
        ObjectifyService.register(App.class);
    }

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new NoPasswordAuthenticationBusinessModule(),
                new NoPasswordAuthenticationServletModule()
        );
    }
}
