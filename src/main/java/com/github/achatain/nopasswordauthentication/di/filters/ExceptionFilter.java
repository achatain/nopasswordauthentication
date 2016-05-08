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

import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class ExceptionFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(ExceptionFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no-op
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (InternalServerException e) {
            LOG.log(Level.SEVERE, "Internal server error", e);
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal server error");
        } catch (UnsupportedContentTypeException e) {
            LOG.log(Level.SEVERE, "Unsupported content type error", e);
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            response.getWriter().write("Unsupported media type");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception caught", e);
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Unexpected error");
        }
    }

    @Override
    public void destroy() {
        // no-op
    }
}
