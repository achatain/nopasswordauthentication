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

package com.github.achatain.nopasswordauthentication.di.filters;

import com.github.achatain.nopasswordauthentication.exceptions.InternalServerException;
import com.github.achatain.nopasswordauthentication.exceptions.UnsupportedContentTypeException;
import com.github.achatain.nopasswordauthentication.utils.FakeHttpServletRequest;
import com.github.achatain.nopasswordauthentication.utils.FakeHttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ExceptionFilterTest {

    @Mock
    private FilterConfig filterConfig;

    @Mock
    private FilterChain filterChain;

    private FakeHttpServletRequest req;
    private FakeHttpServletResponse resp;
    private ExceptionFilter exceptionFilter;

    @Before
    public void setUp() throws Exception {
        req = new FakeHttpServletRequest();
        resp = new FakeHttpServletResponse();
        exceptionFilter = new ExceptionFilter();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void init() throws Exception {
        exceptionFilter.init(filterConfig);
        verifyZeroInteractions(filterConfig);
    }

    @Test
    public void shouldFilterWithNoException() throws Exception {
        exceptionFilter.doFilter(req, resp, filterChain);
        verify(filterChain).doFilter(req, resp);
    }

    @Test
    public void shouldHandleInternalServerException() throws Exception {
        doThrow(new InternalServerException("test")).when(filterChain).doFilter(req, resp);
        exceptionFilter.doFilter(req, resp, filterChain);
        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, resp.getStatus());
    }

    @Test
    public void shouldHandleUnsupportedContentTypeException() throws Exception {
        doThrow(new UnsupportedContentTypeException("test")).when(filterChain).doFilter(req, resp);
        exceptionFilter.doFilter(req, resp, filterChain);
        assertEquals(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, resp.getStatus());
    }

    @Test
    public void shouldHandleOtherExceptions() throws Exception {
        doThrow(Exception.class).when(filterChain).doFilter(req, resp);
        exceptionFilter.doFilter(req, resp, filterChain);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, resp.getStatus());
    }

    @Test
    public void destroy() throws Exception {
        exceptionFilter.destroy();
    }

}
