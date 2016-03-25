package com.github.achatain.nopasswordauthentication.notification.email;

import com.github.achatain.nopasswordauthentication.utils.AppSettings;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EmailServiceFactoryTest {

    private LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig(),
            new LocalMemcacheServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        AppSettings.reset();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void shouldGetSendGridEmailService() throws Exception {
        AppSettings.setEmailProvider(AppSettings.EMAIL_PROVIDER_SENDGRID);
        assertTrue(EmailServiceFactory.getEmailService() instanceof SendGridEmailServiceImpl);
    }

    @Test
    public void shouldGetAppEngineEmailService() throws Exception {
        assertTrue(EmailServiceFactory.getEmailService() instanceof AppEngineEmailServiceImpl);
    }

    @Test
    public void shouldFallBackToAppEngineEmailService() throws Exception {
        AppSettings.setEmailProvider("unknown");
        assertTrue(EmailServiceFactory.getEmailService() instanceof AppEngineEmailServiceImpl);
    }
}
