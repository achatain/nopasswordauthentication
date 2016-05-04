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

import com.github.achatain.nopasswordauthentication.exceptions.UnsupportedContentTypeException;
import com.github.achatain.nopasswordauthentication.utils.FakeHttpServletRequest;
import com.github.achatain.nopasswordauthentication.utils.FakeHttpServletResponse;
import com.google.appengine.api.urlfetch.HTTPMethod;
import org.apache.http.HttpHeaders;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JsonFilterTest {

    private FakeHttpServletRequest req;
    private FakeHttpServletResponse resp;
    private JsonFilter jsonFilter;
    private FilterChain filterChain;

    @Before
    public void setUp() throws Exception {
        req = new FakeHttpServletRequest();
        resp = new FakeHttpServletResponse();
        jsonFilter = new JsonFilter();
        filterChain = mock(FilterChain.class);
    }

    @Test
    public void shouldAllowGetRequest() throws Exception {
        jsonFilter.doFilter(req, resp, filterChain);

        verify(filterChain).doFilter(req, resp);
    }

    @Test
    public void shouldFilterMediaTypeJson() throws Exception {
        req.setMethod(HTTPMethod.POST.name());
        req.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        jsonFilter.doFilter(req, resp, filterChain);

        verify(filterChain).doFilter(req, resp);
    }

    @Test
    public void shouldFilterMediaTypeJsonUTF8() throws Exception {
        req.setMethod(HTTPMethod.POST.name());
        req.setHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
        jsonFilter.doFilter(req, resp, filterChain);

        verify(filterChain).doFilter(req, resp);
    }

    @Test
    public void shouldFilterMediaTypeJsonCaseInsensitive() throws Exception {
        req.setMethod(HTTPMethod.POST.name());
        req.setHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        jsonFilter.doFilter(req, resp, filterChain);

        verify(filterChain).doFilter(req, resp);
    }

    @Test(expected = UnsupportedContentTypeException.class)
    public void shouldRejectNonJsonMediaType() throws Exception {
        req.setMethod(HTTPMethod.POST.name());
        req.setHeader(HttpHeaders.CONTENT_TYPE, "text/html");
        jsonFilter.doFilter(req, resp, filterChain);
    }

    @Test(expected = UnsupportedContentTypeException.class)
    public void shouldRejectEmptyMediaType() throws Exception {
        req.setMethod(HTTPMethod.POST.name());
        req.setHeader(HttpHeaders.CONTENT_TYPE, "");
        jsonFilter.doFilter(req, resp, filterChain);
    }

    @Test(expected = UnsupportedContentTypeException.class)
    public void shouldRejectNullMediaType() throws Exception {
        req.setMethod(HTTPMethod.POST.name());
        req.setHeader(HttpHeaders.CONTENT_TYPE, null);
        jsonFilter.doFilter(req, resp, filterChain);
    }

    @Test(expected = UnsupportedContentTypeException.class)
    public void shouldRejectNoMediaType() throws Exception {
        req.setMethod(HTTPMethod.POST.name());
        jsonFilter.doFilter(req, resp, filterChain);
    }
}
