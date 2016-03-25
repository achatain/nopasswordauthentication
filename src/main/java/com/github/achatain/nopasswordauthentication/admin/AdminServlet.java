package com.github.achatain.nopasswordauthentication.admin;

import com.github.achatain.nopasswordauthentication.utils.ServletResponseUtils;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class AdminServlet extends HttpServlet {

    private static final Key SETTINGS_KEY = KeyFactory.createKey("AppSettings", 1L);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Entity settings = new Entity(SETTINGS_KEY);

        settings.setUnindexedProperty("sendGridApiKey", "dummy");
        settings.setUnindexedProperty("emailProvider", "appengine");

        DatastoreServiceFactory.getDatastoreService().put(settings);

        ServletResponseUtils.writeJsonResponse(resp, "status", "success");
    }
}
