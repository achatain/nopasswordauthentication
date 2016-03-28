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
import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuthServletTest {

    private Gson gson = new Gson();
    private FakeHttpServletRequest req;
    private FakeHttpServletResponse resp;
    private AuthService authService;
    private AuthServlet authServlet;

    @Before
    public void setUp() throws Exception {
        req = new FakeHttpServletRequest();
        resp = new FakeHttpServletResponse();
        authService = mock(AuthService.class);
        authServlet = new AuthServlet(gson, authService);
    }

    @Test
    public void shouldAuthThroughAuthService() throws Exception {
        req.setHeader(HttpHeaders.AUTHORIZATION, AuthServlet.BEARER_PREFIX + " faketoken ");
        req.setBody("{\"userEmail\":\"user@test.com\"}");

        authServlet.doPost(req, resp);

        ArgumentCaptor<AuthRequest> captor = ArgumentCaptor.forClass(AuthRequest.class);
        verify(authService).auth(captor.capture());

        assertEquals("faketoken", captor.getValue().getApiToken());
        assertEquals("user@test.com", captor.getValue().getUserEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAuthWithoutAuthorizationHeader() throws Exception {
        authServlet.doPost(req, resp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAuthWithEmptyAuthorizationHeader() throws Exception {
        req.setHeader(HttpHeaders.AUTHORIZATION, "");
        authServlet.doPost(req, resp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAuthWithoutAuthorizationToken() throws Exception {
        req.setHeader(HttpHeaders.AUTHORIZATION, AuthServlet.BEARER_PREFIX);
        authServlet.doPost(req, resp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAuthWithoutRequestBody() throws Exception {
        req.setHeader(HttpHeaders.AUTHORIZATION, AuthServlet.BEARER_PREFIX + " faketoken ");
        req.setBody("");
        authServlet.doPost(req, resp);
    }
}
