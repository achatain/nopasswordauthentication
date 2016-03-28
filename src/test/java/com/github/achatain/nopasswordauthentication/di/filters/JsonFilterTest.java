package com.github.achatain.nopasswordauthentication.di.filters;

import com.github.achatain.nopasswordauthentication.exceptions.UnsupportedContentTypeException;
import com.github.achatain.nopasswordauthentication.utils.FakeHttpServletRequest;
import com.github.achatain.nopasswordauthentication.utils.FakeHttpServletResponse;
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
    public void shouldFilterMediaTypeJson() throws Exception {
        req.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        jsonFilter.doFilter(req, resp, filterChain);

        verify(filterChain).doFilter(req, resp);
    }

    @Test
    public void shouldFilterMediaTypeJsonUTF8() throws Exception {
        req.setHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
        jsonFilter.doFilter(req, resp, filterChain);

        verify(filterChain).doFilter(req, resp);
    }

    @Test
    public void shouldFilterMediaTypeJsonCasInsensitive() throws Exception {
        req.setHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        jsonFilter.doFilter(req, resp, filterChain);

        verify(filterChain).doFilter(req, resp);
    }

    @Test(expected = UnsupportedContentTypeException.class)
    public void shouldRejectNonJsonMediaType() throws Exception {
        req.setHeader(HttpHeaders.CONTENT_TYPE, "text/html");
        jsonFilter.doFilter(req, resp, filterChain);
    }

    @Test(expected = UnsupportedContentTypeException.class)
    public void shouldRejectEmptyMediaType() throws Exception {
        req.setHeader(HttpHeaders.CONTENT_TYPE, "");
        jsonFilter.doFilter(req, resp, filterChain);
    }

    @Test(expected = UnsupportedContentTypeException.class)
    public void shouldRejectNullMediaType() throws Exception {
        req.setHeader(HttpHeaders.CONTENT_TYPE, null);
        jsonFilter.doFilter(req, resp, filterChain);
    }

    @Test(expected = UnsupportedContentTypeException.class)
    public void shouldRejectNoMediaType() throws Exception {
        jsonFilter.doFilter(req, resp, filterChain);
    }
}
