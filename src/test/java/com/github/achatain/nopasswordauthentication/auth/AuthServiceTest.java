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

import com.github.achatain.nopasswordauthentication.app.App;
import com.github.achatain.nopasswordauthentication.app.AppService;
import com.github.achatain.nopasswordauthentication.email.EmailService;
import com.github.achatain.nopasswordauthentication.utils.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private AppService appService;

    @Mock
    private EmailService emailService;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private App app;

    @InjectMocks
    private AuthService authService;

    @Captor
    private ArgumentCaptor<Auth> authCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldRequest() throws Exception {
        AuthRequest authRequest = AuthRequest.builder()
                .withApiToken("api-token")
                .withUserId("test@github.com")
                .build();

        when(tokenService.hash("api-token")).thenReturn("hashed-api-token");
        when(appService.findByApiToken("hashed-api-token")).thenReturn(app);
        when(tokenService.generate()).thenReturn("auth-token");
        when(app.getId()).thenReturn(1L);
        when(tokenService.hash("auth-token")).thenReturn("hashed-auth-token");
        when(app.getCallbackUrl()).thenReturn("callback.com");
        when(app.getOwnerEmail()).thenReturn("owner@test.com");
        when(app.getName()).thenReturn("app-name");

        authService.request(authRequest);

        verify(authRepository).save(authCaptor.capture());
        Auth auth = authCaptor.getValue();
        assertEquals(Long.valueOf(1L), auth.getAppId());
        assertEquals("test@github.com", auth.getUserId());
        assertTrue(new Date().getTime() - auth.getTimestamp() < 1000);
        assertEquals("hashed-auth-token", auth.getToken());

        verify(emailService).sendEmail(1L, "owner@test.com", "app-name", "test@github.com", "No password authentication", "https://callback.com?uid=test@github.com&token=auth-token");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailRequestWhenBlankApiToken() throws Exception {
        AuthRequest authRequest = AuthRequest.builder()
                .withApiToken("")
                .withUserId("test@github.com")
                .build();

        authService.request(authRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailRequestWhenInvalidUserId() throws Exception {
        AuthRequest authRequest = AuthRequest.builder()
                .withApiToken("api-token")
                .withUserId("invalid_email")
                .build();

        authService.request(authRequest);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailRequestWhenNoAppMatchesApiToken() throws Exception {
        AuthRequest authRequest = AuthRequest.builder()
                .withApiToken("api-token")
                .withUserId("test@github.com")
                .build();

        when(tokenService.hash("api-token")).thenReturn("hashed-api-token");
        when(appService.findByApiToken("hashed-api-token")).thenReturn(null);

        authService.request(authRequest);
    }

    @Test
    public void shouldVerify() throws Exception {
        // TODO implement
    }

}
