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

package com.github.achatain.nopasswordauthentication.utils;

import com.github.achatain.nopasswordauthentication.exceptions.InternalServerException;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AppSettings {

    private static final Logger LOG = Logger.getLogger(AppSettings.class.getName());

    static final String KIND = "AppSettings";
    static final Long ID = 1L;

    private static final Key KEY = KeyFactory.createKey(KIND, ID);

    // Settings keys
    private static final String SENDGRID_API_KEY = "sendGridApiKey";
    private static final String EMAIL_PROVIDER = "emailProvider";
    private static final String EMAIL_SENDER = "emailSender";
    private static final String AUTH_EXPIRY = "authExpiry";

    // Properties constants
    private static final String CHANGE_ME = "change_me";
    public static final String EMAIL_PROVIDER_APPENGINE = "appengine";
    public static final String EMAIL_PROVIDER_SENDGRID = "sendgrid";
    private static final Integer AUTH_DEFAULT_EXPIRY = 10*60*1000;

    private AppSettings() {
    }

    public static void reset() {
        Map<String, Object> defaultProperties = new HashMap<>();

        defaultProperties.put(SENDGRID_API_KEY, CHANGE_ME);
        defaultProperties.put(EMAIL_SENDER, CHANGE_ME);
        defaultProperties.put(EMAIL_PROVIDER, EMAIL_PROVIDER_APPENGINE);
        defaultProperties.put(AUTH_EXPIRY, AUTH_DEFAULT_EXPIRY);

        setProperties(defaultProperties);
    }

    public static String getSendGridApiKey() {
        return getProperty(SENDGRID_API_KEY);
    }

    static void setSendgridApiKey(String value) {
        setProperty(SENDGRID_API_KEY, value);
    }

    public static String getEmailProvider() {
        return getProperty(EMAIL_PROVIDER);
    }

    public static void setEmailProvider(String value) {
        setProperty(EMAIL_PROVIDER, value);
    }

    public static String getEmailSender() {
        return getProperty(EMAIL_SENDER);
    }

    static void setEmailSender(String value) {
        setProperty(EMAIL_SENDER, value);
    }

    public static Integer getAuthExpiry() {
        return getIntProperty(AUTH_EXPIRY);
    }

    static void setAuthExpiry(Integer value) {
        setProperty(AUTH_EXPIRY, value);
    }

    private static Entity getSettings() {
        Entity entity;

        try {
            entity = DatastoreServiceFactory.getDatastoreService().get(KEY);
        } catch (EntityNotFoundException e) {
            LOG.info("Creating app settings");
            entity = new Entity(KEY);
            DatastoreServiceFactory.getDatastoreService().put(entity);
            reset();
        }

        return entity;
    }

    private static void setProperties(Map<String, Object> properties) {
        Entity settings = getSettings();

        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            settings.setUnindexedProperty(entry.getKey(), entry.getValue());
            cacheProperty(entry.getKey(), entry.getValue());
        }

        DatastoreServiceFactory.getDatastoreService().put(settings);
    }

    private static void setProperty(String property, Object value) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(property, value);
        setProperties(properties);
    }

    private static String getProperty(String property) {
        String value = getCachedProperty(property);

        if (value == null) {
            value = getDatastoreProperty(property);
        }

        return value;
    }

    private static Integer getIntProperty(String property) {
        Integer value = getCachedIntProperty(property);

        if (value == null) {
            value = getDatastoreIntProperty(property);
        }

        return value;
    }

    private static String getCachedProperty(String property) {
        String value = (String) MemcacheServiceFactory.getMemcacheService().get(property);
        LOG.info(String.format("Property [%s] %s found in the cache", property, value == null ? "not" : ""));
        return value;
    }

    private static Integer getCachedIntProperty(String property) {
        Integer value = (Integer) MemcacheServiceFactory.getMemcacheService().get(property);
        LOG.info(String.format("Property [%s] %s found in the cache", property, value == null ? "not" : ""));
        return value;
    }

    private static void cacheProperty(String property, Object value) {
        LOG.info(String.format("Property [%s = %s] put in the cache", property, value));
        MemcacheServiceFactory.getMemcacheService().put(property, value);
    }

    private static String getDatastoreProperty(String property) {
        String value = (String) getSettings().getProperty(property);

        if (value == null) {
            LOG.log(Level.SEVERE, String.format("Property [%s] is missing in the app settings.", property));
            throw new InternalServerException("Bad app config");
        }

        cacheProperty(property, value);

        return value;
    }

    private static Integer getDatastoreIntProperty(String property) {
        Integer value = (Integer) getSettings().getProperty(property);

        if (value == null) {
            LOG.log(Level.SEVERE, String.format("Property [%s] is missing in the app settings.", property));
            throw new InternalServerException("Bad app config");
        }

        cacheProperty(property, value);

        return value;
    }
}
