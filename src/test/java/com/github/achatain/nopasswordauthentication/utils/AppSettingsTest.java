package com.github.achatain.nopasswordauthentication.utils;

import com.github.achatain.nopasswordauthentication.exception.InternalServerException;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppSettingsTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig(),
            new LocalMemcacheServiceTestConfig());

    @Before
    public void setUp() throws Exception {
        helper.setUp();
    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    private Key getSettingsKey() {
        return KeyFactory.createKey(AppSettings.KIND, AppSettings.ID);
    }

    @Test(expected = InternalServerException.class)
    public void shouldNotFindProperty() throws Exception {
        AppSettings.getEmailProvider();
    }

    @Test
    public void shouldGetPropertyFromCache() throws Exception {
        AppSettings.setEmailProvider("cache-test-email-provider");
        DatastoreServiceFactory.getDatastoreService().delete(getSettingsKey());
        assertEquals("cache-test-email-provider", AppSettings.getEmailProvider());
    }

    @Test
    public void shouldGetPropertyFromDatastore() throws Exception {
        AppSettings.setEmailProvider("ds-test-email-provider");
        MemcacheServiceFactory.getMemcacheService().clearAll();
        assertEquals("ds-test-email-provider", AppSettings.getEmailProvider());
    }

    @Test
    public void shouldResetPropertiesToDefault() throws Exception {
        AppSettings.reset();
        assertEquals("appengine", AppSettings.getEmailProvider());
        assertEquals("change_me", AppSettings.getEmailSender());
        assertEquals("change_me", AppSettings.getSendGridApiKey());
    }

    @Test
    public void shouldSetSendgridApiKey() throws Exception {
        AppSettings.setSendgridApiKey("test-sendgrid-api-key");
        assertEquals("test-sendgrid-api-key", AppSettings.getSendGridApiKey());
    }

    @Test
    public void shouldSetEmailProvider() throws Exception {
        AppSettings.setEmailProvider("test-email-provider");
        assertEquals("test-email-provider", AppSettings.getEmailProvider());
    }

    @Test
    public void shouldSetEmailSender() throws Exception {
        AppSettings.setEmailSender("test-email-sender");
        assertEquals("test-email-sender", AppSettings.getEmailSender());
    }
}
