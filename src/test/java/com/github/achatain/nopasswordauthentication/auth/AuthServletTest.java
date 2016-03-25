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

    private FakeHttpServletRequest req;
    private FakeHttpServletResponse resp;
    private AuthService authService;
    private AuthServlet authServlet;

    @Before
    public void setUp() throws Exception {
        req = new FakeHttpServletRequest();
        resp = new FakeHttpServletResponse();
        authService = mock(AuthService.class);
        authServlet = new AuthServlet(new Gson(), authService);
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
}
