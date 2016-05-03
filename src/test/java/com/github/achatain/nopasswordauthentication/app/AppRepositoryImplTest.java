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

package com.github.achatain.nopasswordauthentication.app;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppRepositoryImplTest {

    private LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());

    private AppRepository appRepository;

    private Closeable ofy;

    @Before
    public void setUp() throws Exception {
        helper.setUp();
        appRepository = new AppRepositoryImpl();
        ofy = ObjectifyService.begin();
        ObjectifyService.register(App.class);
    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
        ofy.close();
    }

    @Test
    public void shouldSaveAndFindById() throws Exception {
        App app = App.builder()
                .withApiToken("hashed token")
                .withCallbackUrl("https://callback.com/")
                .withEmailTemplate("<html></html>")
                .withName("My App")
                .withOwnerEmail("owner@myapp.com")
                .build();

        appRepository.save(app);

        App found = appRepository.findById(app.getId());

        assertEquals("hashed token", found.getApiToken());
        assertEquals("https://callback.com/", found.getCallbackUrl());
        assertEquals("<html></html>", found.getEmailTemplate());
        assertEquals("My App", found.getName());
        assertEquals("owner@myapp.com", found.getOwnerEmail());
    }

    @Test
    public void shouldSaveAndFindByApiToken() throws Exception {
        App app = App.builder()
                .withApiToken("hashed token")
                .withCallbackUrl("https://callback.com/")
                .withEmailTemplate("<html></html>")
                .withName("My App")
                .withOwnerEmail("owner@myapp.com")
                .build();

        appRepository.save(app);

        App found = appRepository.findByApiToken("hashed token");

        assertEquals("hashed token", found.getApiToken());
        assertEquals("https://callback.com/", found.getCallbackUrl());
        assertEquals("<html></html>", found.getEmailTemplate());
        assertEquals("My App", found.getName());
        assertEquals("owner@myapp.com", found.getOwnerEmail());
    }

}