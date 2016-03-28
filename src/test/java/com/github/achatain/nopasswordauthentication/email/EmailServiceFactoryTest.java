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

package com.github.achatain.nopasswordauthentication.email;

import com.github.achatain.nopasswordauthentication.utils.AppSettings;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EmailServiceFactoryTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
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
