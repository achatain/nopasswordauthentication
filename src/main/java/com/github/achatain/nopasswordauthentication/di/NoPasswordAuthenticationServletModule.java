package com.github.achatain.nopasswordauthentication.di;

import com.github.achatain.nopasswordauthentication.app.AppServlet;
import com.github.achatain.nopasswordauthentication.auth.AuthServlet;
import com.github.achatain.nopasswordauthentication.filters.ExceptionFilter;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

class NoPasswordAuthenticationServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        // Filters
        filter("/*").through(ObjectifyFilter.class);
        filter("/*").through(ExceptionFilter.class);

        // Servlets
        serve("/app").with(AppServlet.class);
        serve("/auth").with(AuthServlet.class);
    }
}
