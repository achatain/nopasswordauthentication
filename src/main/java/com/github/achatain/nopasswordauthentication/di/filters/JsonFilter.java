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
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.common.collect.Sets;
import com.google.common.net.MediaType;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@Singleton
public class JsonFilter implements Filter {

    private static final Set<String> JSON_TYPES = Sets.newHashSet(
            MediaType.JSON_UTF_8.withoutParameters().toString(),
            MediaType.JSON_UTF_8.toString());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no-op
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (HTTPMethod.GET.name().equals(((HttpServletRequest) request).getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String contentType = request.getContentType();

        if (JSON_TYPES.contains(StringUtils.lowerCase(contentType))) {
            chain.doFilter(request, response);
        } else {
            throw new UnsupportedContentTypeException(String.format("Expected a JSON content type but got [%s]", contentType));
        }
    }

    @Override
    public void destroy() {
        // no-op
    }
}
