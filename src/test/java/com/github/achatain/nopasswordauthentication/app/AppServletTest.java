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

import com.github.achatain.nopasswordauthentication.utils.FakeHttpServletRequest;
import com.github.achatain.nopasswordauthentication.utils.FakeHttpServletResponse;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AppServletTest {

    private FakeHttpServletRequest req;
    private FakeHttpServletResponse resp;

    private AppServlet appServlet;

    @Mock
    private AppService appService;

    @Captor
    private ArgumentCaptor<AppDto> appDtoCaptor;

    private Gson gson = new Gson();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        req = new FakeHttpServletRequest();
        resp = new FakeHttpServletResponse();
        appServlet = new AppServlet(gson, appService);
    }

    @Test
    public void shouldDoPost() throws Exception {
        req.setBody(Resources.toString(Resources.getResource("appDto.json"), Charsets.UTF_8));

        String testApiToken = "123456789";
        when(appService.create(any(AppDto.class))).thenReturn(testApiToken);

        appServlet.doPost(req, resp);

        verify(appService).create(appDtoCaptor.capture());
        AppDto appDto = appDtoCaptor.getValue();
        assertEquals("My Test App", appDto.getName());
        assertEquals("achatain@github.com", appDto.getOwnerEmail());
        assertEquals("https://testapp.github.com/", appDto.getCallbackUrl());
        assertEquals("<html><p>My email template</p></html>", appDto.getEmailTemplate());

        assertEquals(200, resp.getStatus());
        assertEquals("{\"apiToken\":\"123456789\"}", resp.getContentAsString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectEmptyRequestBody() throws Exception {
        appServlet.doPost(req, resp);
    }
}
