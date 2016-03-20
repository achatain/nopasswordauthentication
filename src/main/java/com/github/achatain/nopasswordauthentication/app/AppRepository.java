package com.github.achatain.nopasswordauthentication.app;

import com.github.achatain.nopasswordauthentication.utils.TokenUtils;

import static com.googlecode.objectify.ObjectifyService.ofy;

class AppRepository {

    App find(String id) {
        return ofy().load().type(App.class).id(id).now();
    }

    String create(String id, String owner, String name, String apiToken, String callbackUrl, String emailTemplate) {
        App app = new App(id, owner, name, TokenUtils.hash(apiToken), callbackUrl, emailTemplate);
        ofy().save().entity(app).now();
        return apiToken;
    }
}
