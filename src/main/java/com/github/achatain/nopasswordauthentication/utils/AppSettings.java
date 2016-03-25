package com.github.achatain.nopasswordauthentication.utils;

import com.github.achatain.nopasswordauthentication.exception.InternalServerException;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class AppSettings {

    private static final Logger LOG = Logger.getLogger(AppSettings.class.getName());

    private static final Key KEY = KeyFactory.createKey("AppSettings", 1L);

    private AppSettings() {
    }

    public static String getSendGridApiKey() {
        return getProperty("sendGridApiKey");
    }

    public static String getEmailProvider() {
        return getProperty("emailProvider");
    }

    public static String getEmailSender() {
        return getProperty("emailSender");
    }

    private static String getProperty(String property) {
        String value = getCachedProperty(property);

        if (value == null) {
            value = getDatastoreProperty(property);
        }

        return value;
    }

    private static String getCachedProperty(String property) {
        String value = (String) MemcacheServiceFactory.getMemcacheService().get(property);
        LOG.info(String.format("Property [%s] %s found in the cache", property, value == null ? "not" : ""));
        return value;
    }

    private static void cacheProperty(String property, String value) {
        LOG.info(String.format("Property [%s] put in the cache", property));
        MemcacheServiceFactory.getMemcacheService().put(property, value);
    }

    private static String getDatastoreProperty(String property) {
        String value;

        try {
            value = (String) DatastoreServiceFactory.getDatastoreService().get(KEY).getProperty(property);
        } catch (EntityNotFoundException e) {
            LOG.log(Level.SEVERE, String.format("Property [%s] is missing in the app settings.", property), e);
            throw new InternalServerException("Bad app config");
        }

        cacheProperty(property, value);

        return value;
    }
}
