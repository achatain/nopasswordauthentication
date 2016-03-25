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

import com.google.appengine.repackaged.com.google.common.base.Preconditions;
import com.google.appengine.repackaged.com.google.common.collect.Iterables;
import com.google.appengine.repackaged.com.google.common.collect.LinkedListMultimap;
import com.google.appengine.repackaged.com.google.common.collect.ListMultimap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class FakeHttpServletResponse implements HttpServletResponse {
    private final ByteArrayOutputStream actualBody = new ByteArrayOutputStream();
    private final ListMultimap<String, String> headers = LinkedListMultimap.create();
    private int status = 200;
    private boolean committed;
    private ServletOutputStream outputStream;
    private PrintWriter writer;

    public FakeHttpServletResponse() {
    }

    public synchronized void flushBuffer() throws IOException {
        if (this.outputStream != null) {
            this.outputStream.flush();
        }

        if (this.writer != null) {
            this.writer.flush();
        }

    }

    public int getBufferSize() {
        throw new UnsupportedOperationException();
    }

    public String getCharacterEncoding() {
        return StandardCharsets.UTF_8.name();
    }

    public String getContentType() {
        return null;
    }

    public Locale getLocale() {
        return Locale.US;
    }

    public synchronized ServletOutputStream getOutputStream() {
        Preconditions.checkState(this.writer == null, "getWriter() already called");
        if (this.outputStream == null) {
            final PrintWriter osWriter = new PrintWriter(this.actualBody);
            this.outputStream = new ServletOutputStream() {
                public void write(int c) throws IOException {
                    osWriter.write(c);
                    osWriter.flush();
                }
            };
        }

        return this.outputStream;
    }

    public synchronized PrintWriter getWriter() {
        Preconditions.checkState(this.outputStream == null, "getOutputStream() already called");
        if (this.writer == null) {
            this.writer = new PrintWriter(this.actualBody);
        }

        return this.writer;
    }

    public synchronized boolean isCommitted() {
        return this.committed;
    }

    public void reset() {
        throw new UnsupportedOperationException();
    }

    public void resetBuffer() {
        throw new UnsupportedOperationException();
    }

    public void setBufferSize(int sz) {
        throw new UnsupportedOperationException();
    }

    public void setCharacterEncoding(String name) {
        Preconditions.checkArgument(StandardCharsets.UTF_8.equals(Charset.forName(name)), "unsupported charset: %s", new Object[]{name});
    }

    public void setContentLength(int length) {
        this.headers.removeAll("Content-Length");
        this.headers.put("Content-Length", Integer.toString(length));
    }

    public void setContentType(String type) {
        this.headers.removeAll("Content-Type");
        this.headers.put("Content-Type", type);
    }

    public void setLocale(Locale locale) {
        throw new UnsupportedOperationException();
    }

    public void addCookie(Cookie cookie) {
        throw new UnsupportedOperationException();
    }

    public void addDateHeader(String name, long value) {
        throw new UnsupportedOperationException();
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public void addIntHeader(String name, int value) {
        this.headers.put(name, Integer.toString(value));
    }

    public boolean containsHeader(String name) {
        return !this.headers.get(name).isEmpty();
    }

    public String encodeRedirectURL(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * @deprecated
     */
    @Deprecated
    public String encodeRedirectUrl(String url) {
        throw new UnsupportedOperationException();
    }

    public String encodeURL(String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * @deprecated
     */
    @Deprecated
    public String encodeUrl(String url) {
        throw new UnsupportedOperationException();
    }

    public synchronized void sendError(int sc) {
        this.status = sc;
        this.committed = true;
    }

    public synchronized void sendError(int sc, String msg) {
        this.status = sc;
        this.committed = true;
    }

    public synchronized void sendRedirect(String loc) {
        this.status = 302;
        this.setHeader("Location", loc);
        this.committed = true;
    }

    public void setDateHeader(String name, long value) {
        this.setHeader(name, Long.toString(value));
    }

    public void setHeader(String name, String value) {
        this.headers.removeAll(name);
        this.addHeader(name, value);
    }

    public void setIntHeader(String name, int value) {
        this.headers.removeAll(name);
        this.addIntHeader(name, value);
    }

    public synchronized void setStatus(int sc) {
        this.status = sc;
        this.committed = true;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public synchronized void setStatus(int sc, String msg) {
        this.status = sc;
        this.committed = true;
    }

    public synchronized int getStatus() {
        return this.status;
    }

    public String getHeader(String name) {
        return (String) Iterables.getFirst(this.headers.get(Preconditions.checkNotNull(name)), (Object) null);
    }

    public String getContentAsString() {
        this.writer.flush();
        return actualBody.toString();
    }
}
