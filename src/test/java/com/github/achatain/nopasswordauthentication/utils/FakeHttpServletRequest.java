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

package com.github.achatain.nopasswordauthentication.utils;

import com.google.appengine.repackaged.com.google.common.base.Function;
import com.google.appengine.repackaged.com.google.common.base.Splitter;
import com.google.appengine.repackaged.com.google.common.collect.*;
import org.apache.http.HttpHeaders;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.*;
import java.util.Map.Entry;

public class FakeHttpServletRequest implements HttpServletRequest {
    private final Map<String, Object> attributes = Maps.newConcurrentMap();
    private final ListMultimap<String, String> headers = LinkedListMultimap.create();
    private final ListMultimap<String, String> parameters = LinkedListMultimap.create();
    private String hostName = "localhost";
    private int port = 443;
    private String contextPath = "";
    private String servletPath = "";
    private String pathInfo;
    private String method;
    private String body;
    private static final Function<Collection<String>, String[]> STRING_COLLECTION_TO_ARRAY = new Function() {
        @Override
        public Object apply(Object o) {
            Collection<String> values = (Collection) o;
            return (String[]) values.toArray(new String[0]);
        }
    };

    public FakeHttpServletRequest() {
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(this.attributes.keySet());
    }

    public String getCharacterEncoding() {
        return StandardCharsets.UTF_8.name();
    }

    public int getContentLength() {
        return -1;
    }

    public String getContentType() {
        String value;
        List<String> listContentType = headers.get(HttpHeaders.CONTENT_TYPE);
        if (listContentType == null || listContentType.size() == 0) {
            value = null;
        } else {
            value = listContentType.get(0);
        }
        return value;
    }

    public ServletInputStream getInputStream() {
        throw new UnsupportedOperationException();
    }

    public String getLocalAddr() {
        return "1.2.3.4";
    }

    public String getLocalName() {
        return this.hostName;
    }

    public int getLocalPort() {
        return this.port;
    }

    public Locale getLocale() {
        return Locale.US;
    }

    public Enumeration<Locale> getLocales() {
        return Collections.enumeration(Collections.singleton(Locale.US));
    }

    public String getParameter(String name) {
        return (String) Iterables.getFirst(this.parameters.get(name), (Object) null);
    }

    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(Maps.transformValues(this.parameters.asMap(), STRING_COLLECTION_TO_ARRAY));
    }

    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.parameters.keySet());
    }

    public String[] getParameterValues(String name) {
        return (String[]) STRING_COLLECTION_TO_ARRAY.apply(this.parameters.get(name));
    }

    public String getProtocol() {
        return "HTTP/1.1";
    }

    public BufferedReader getReader() {
        return new BufferedReader(new StringReader(body));
    }

    /**
     * @deprecated
     */
    @Deprecated
    public String getRealPath(String path) {
        throw new UnsupportedOperationException();
    }

    public String getRemoteAddr() {
        return "5.6.7.8";
    }

    public String getRemoteHost() {
        return "remotehost";
    }

    public int getRemotePort() {
        return 1234;
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    public String getScheme() {
        return this.port == 443 ? "https" : "http";
    }

    public String getServerName() {
        return this.hostName;
    }

    public int getServerPort() {
        return this.port;
    }

    public boolean isSecure() {
        return this.port == 443;
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public void setCharacterEncoding(String env) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public String getAuthType() {
        return null;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    public long getDateHeader(String name) {
        throw new UnsupportedOperationException();
    }

    public String getHeader(String name) {
        return (String) Iterables.getFirst(this.headers.get(name), (Object) null);
    }

    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(this.headers.keySet());
    }

    public Enumeration<String> getHeaders(String name) {
        return Collections.enumeration(this.headers.get(name));
    }

    public int getIntHeader(String name) {
        return Integer.parseInt(this.getHeader(name));
    }

    public String getMethod() {
        return this.method == null ? "GET" : this.method;
    }

    public String getPathInfo() {
        return this.pathInfo;
    }

    public String getPathTranslated() {
        return this.pathInfo;
    }

    public String getQueryString() {
        return !this.parameters.isEmpty() && this.getMethod().equals("GET") ? paramsToString(this.parameters) : null;
    }

    public String getRemoteUser() {
        return null;
    }

    public String getRequestURI() {
        String var1 = this.contextPath;
        String var2 = this.servletPath;
        String var3 = this.pathInfo == null ? "" : this.pathInfo;
        return (new StringBuilder(0 + String.valueOf(var1).length() + String.valueOf(var2).length() + String.valueOf(var3).length())).append(var1).append(var2).append(var3).toString();
    }

    public StringBuffer getRequestURL() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getScheme());
        sb.append("://");
        sb.append(this.getServerName());
        sb.append(":");
        sb.append(this.getServerPort());
        sb.append(this.contextPath);
        sb.append(this.servletPath);
        if (this.pathInfo != null) {
            sb.append(this.pathInfo);
        }

        return sb;
    }

    public String getRequestedSessionId() {
        return null;
    }

    public String getServletPath() {
        return this.servletPath;
    }

    public HttpSession getSession() {
        throw new UnsupportedOperationException();
    }

    public HttpSession getSession(boolean create) {
        throw new UnsupportedOperationException();
    }

    public Principal getUserPrincipal() {
        throw new UnsupportedOperationException();
    }

    public boolean isRequestedSessionIdFromCookie() {
        throw new UnsupportedOperationException();
    }

    public boolean isRequestedSessionIdFromURL() {
        throw new UnsupportedOperationException();
    }

    /**
     * @deprecated
     */
    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        throw new UnsupportedOperationException();
    }

    public boolean isRequestedSessionIdValid() {
        throw new UnsupportedOperationException();
    }

    public boolean isUserInRole(String role) {
        throw new UnsupportedOperationException();
    }

    private static String paramsToString(ListMultimap<String, String> params) {
        try {
            StringBuilder e = new StringBuilder();
            boolean first = true;
            Iterator var3 = params.entries().iterator();

            while (var3.hasNext()) {
                Entry e1 = (Entry) var3.next();
                if (!first) {
                    e.append('&');
                } else {
                    first = false;
                }

                e.append(URLEncoder.encode((String) e1.getKey(), StandardCharsets.UTF_8.name()));
                if (!"".equals(e1.getValue())) {
                    e.append('=').append(URLEncoder.encode((String) e1.getValue(), StandardCharsets.UTF_8.name()));
                }
            }

            return e.toString();
        } catch (UnsupportedEncodingException var5) {
            throw new IllegalStateException(var5);
        }
    }

    public void setParametersFromQueryString(String qs) {
        this.parameters.clear();
        if (qs != null) {
            Iterator var2 = Splitter.on('&').split(qs).iterator();

            while (var2.hasNext()) {
                String entry = (String) var2.next();
                ImmutableList kv = ImmutableList.copyOf(Splitter.on('=').limit(2).split(entry));

                try {
                    this.parameters.put(URLDecoder.decode((String) kv.get(0), StandardCharsets.UTF_8.name()), kv.size() == 2 ? URLDecoder.decode((String) kv.get(1), StandardCharsets.UTF_8.name()) : "");
                } catch (UnsupportedEncodingException var6) {
                    throw new IllegalArgumentException(var6);
                }
            }
        }

    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void addParameter(String key, String value) {
        this.parameters.put(key, value);
    }

    public void setMethod(String name) {
        this.method = name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    void setSerletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    void setPathInfo(String pathInfo) {
        if ("".equals(pathInfo)) {
            this.pathInfo = null;
        } else {
            this.pathInfo = pathInfo;
        }
    }
}
