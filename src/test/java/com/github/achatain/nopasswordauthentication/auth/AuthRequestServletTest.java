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

public class AuthRequestServletTest {

    private Gson gson = new Gson();
    private FakeHttpServletRequest req;
    private FakeHttpServletResponse resp;
    private AuthService authService;
    private AuthRequestServlet authRequestServlet;

    @Before
    public void setUp() throws Exception {
        req = new FakeHttpServletRequest();
        resp = new FakeHttpServletResponse();
        authService = mock(AuthService.class);
        authRequestServlet = new AuthRequestServlet(gson, authService);
    }

    @Test
    public void shouldAuthThroughAuthService() throws Exception {
        req.setHeader(HttpHeaders.AUTHORIZATION, AuthRequestServlet.BEARER_PREFIX + " faketoken ");
        req.setBody("{\"userId\":\"user@test.com\"}");

        authRequestServlet.doPost(req, resp);

        ArgumentCaptor<AuthRequest> captor = ArgumentCaptor.forClass(AuthRequest.class);
        verify(authService).request(captor.capture());

        assertEquals("faketoken", captor.getValue().getApiToken());
        assertEquals("user@test.com", captor.getValue().getUserId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAuthWithoutAuthorizationHeader() throws Exception {
        authRequestServlet.doPost(req, resp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAuthWithEmptyAuthorizationHeader() throws Exception {
        req.setHeader(HttpHeaders.AUTHORIZATION, "");
        authRequestServlet.doPost(req, resp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAuthWithoutAuthorizationToken() throws Exception {
        req.setHeader(HttpHeaders.AUTHORIZATION, AuthRequestServlet.BEARER_PREFIX);
        authRequestServlet.doPost(req, resp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAuthWithoutRequestBody() throws Exception {
        req.setHeader(HttpHeaders.AUTHORIZATION, AuthRequestServlet.BEARER_PREFIX + " faketoken ");
        req.setBody("");
        authRequestServlet.doPost(req, resp);
    }
}
