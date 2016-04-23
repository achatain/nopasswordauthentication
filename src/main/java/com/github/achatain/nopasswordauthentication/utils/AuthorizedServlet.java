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

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class AuthorizedServlet extends HttpServlet {

    public static final String BEARER_PREFIX = "Bearer ";

    protected String verifyApiToken(HttpServletRequest req) {
        String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
        Preconditions.checkArgument(authorization != null, "Missing authorization header");

        String apiToken = StringUtils.removeStartIgnoreCase(authorization, BEARER_PREFIX);
        Preconditions.checkArgument(StringUtils.isNotBlank(apiToken), "Missing api token");

        return apiToken;
    }
}
