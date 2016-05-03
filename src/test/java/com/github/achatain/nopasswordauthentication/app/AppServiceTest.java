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

import com.github.achatain.nopasswordauthentication.utils.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AppServiceTest {

    private AppService appService;

    @Mock
    private AppRepository appRepository;

    @Mock
    private TokenService tokenService;

    @Captor
    private ArgumentCaptor<App> appCaptor;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        appService = new AppService(appRepository, tokenService);
    }

    @Test
    public void shouldCreate() throws Exception {
        AppDto appDto = AppDto.builder()
                .withCallbackUrl("https://callback.com/")
                .withEmailTemplate("<html><p>Email Template</p></html>")
                .withName("Test App")
                .withOwnerEmail("owner@testapp.com")
                .build();

        when(tokenService.generate()).thenReturn("123456789");
        when(tokenService.hash("123456789")).thenReturn("hash");

        assertEquals("123456789", appService.create(appDto));

        verify(appRepository).save(appCaptor.capture());
        App app = appCaptor.getValue();
        assertEquals("hash", app.getApiToken());
        assertEquals("https://callback.com/", app.getCallbackUrl());
        assertEquals("<html><p>Email Template</p></html>", app.getEmailTemplate());
        assertEquals("Test App", app.getName());
        assertEquals("owner@testapp.com", app.getOwnerEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateWithoutOwnerEmail() throws Exception {
        AppDto appDto = AppDto.builder()
                .withCallbackUrl("https://callback.com/")
                .withEmailTemplate("<html><p>Email Template</p></html>")
                .withName("Test App")
                .withOwnerEmail("")
                .build();

        appService.create(appDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateWithoutName() throws Exception {
        AppDto appDto = AppDto.builder()
                .withCallbackUrl("https://callback.com/")
                .withEmailTemplate("<html><p>Email Template</p></html>")
                .withName("")
                .withOwnerEmail("")
                .build();

        appService.create(appDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateWithoutCallbackUrl() throws Exception {
        AppDto appDto = AppDto.builder()
                .withCallbackUrl("")
                .withEmailTemplate("<html><p>Email Template</p></html>")
                .withName("Test App")
                .withOwnerEmail("")
                .build();

        appService.create(appDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateWithInvalidOwnerEmail() throws Exception {
        AppDto appDto = AppDto.builder()
                .withCallbackUrl("https://callback.com/")
                .withEmailTemplate("<html><p>Email Template</p></html>")
                .withName("Test App")
                .withOwnerEmail("invalid_email")
                .build();

        appService.create(appDto);
    }

    @Test
    public void findByApiToken() throws Exception {
        appService.findByApiToken("token");
        verify(appRepository).findByApiToken("token");
    }
}
