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

package com.github.achatain.nopasswordauthentication.auth;

import com.github.achatain.nopasswordauthentication.utils.FakeHttpServletRequest;
import com.github.achatain.nopasswordauthentication.utils.FakeHttpServletResponse;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AuthVerifyServletTest {

    @Mock
    private AuthService authService;

    @Captor
    private ArgumentCaptor<AuthVerify> authVerifyCaptor;

    private Gson gson = new Gson();

    private AuthVerifyServlet authVerifyServlet;

    private FakeHttpServletRequest req;
    private FakeHttpServletResponse resp;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        req = new FakeHttpServletRequest();
        resp = new FakeHttpServletResponse();
        authVerifyServlet = new AuthVerifyServlet(authService, gson);
    }

    @Test
    public void shouldVerify() throws Exception {
        req.setHeader(HttpHeaders.AUTHORIZATION, AuthRequestServlet.BEARER_PREFIX + " faketoken ");
        req.setBody(Resources.toString(Resources.getResource("authVerify.json"), Charsets.UTF_8));

        when(authService.verify(any(AuthVerify.class))).thenReturn(true);

        authVerifyServlet.doPost(req, resp);

        verify(authService).verify(authVerifyCaptor.capture());
        AuthVerify authVerify = authVerifyCaptor.getValue();
        assertEquals("faketoken", authVerify.getApiToken());
        assertEquals("test.user@github.com", authVerify.getUserId());
        assertEquals("auth_request_token", authVerify.getToken());

        assertEquals(200, resp.getStatus());
        assertEquals("{\"authorized\":true}", resp.getContentAsString());
    }

    @Test
    public void shouldFailVerification() throws Exception {
        req.setHeader(HttpHeaders.AUTHORIZATION, AuthRequestServlet.BEARER_PREFIX + " faketoken ");
        req.setBody(Resources.toString(Resources.getResource("authVerify.json"), Charsets.UTF_8));

        when(authService.verify(any(AuthVerify.class))).thenReturn(false);

        authVerifyServlet.doPost(req, resp);

        verify(authService).verify(authVerifyCaptor.capture());
        AuthVerify authVerify = authVerifyCaptor.getValue();
        assertEquals("faketoken", authVerify.getApiToken());
        assertEquals("test.user@github.com", authVerify.getUserId());
        assertEquals("auth_request_token", authVerify.getToken());

        assertEquals(403, resp.getStatus());
    }

}
