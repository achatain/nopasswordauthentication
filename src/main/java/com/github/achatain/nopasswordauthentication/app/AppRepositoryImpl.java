package com.github.achatain.nopasswordauthentication.app;

import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class AppRepositoryImpl implements AppRepository {

    private static final Logger LOG = Logger.getLogger(AppRepositoryImpl.class.getName());

    public App find(Long id) {
        return ofy().load().type(App.class).id(id).now();
    }

    public void save(App app) {
        ofy().save().entity(app).now();
        LOG.info(String.format("New client application created [%s]", app));
    }
}
