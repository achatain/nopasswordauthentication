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

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Singleton
public class AuthVerifyServlet extends HttpServlet {

    private static final transient Logger LOG = Logger.getLogger(AuthVerifyServlet.class.getName());

    static final String BEARER_PREFIX = "Bearer ";

    private final transient Gson gson;
    private final transient AuthService authService;

    @Inject
    public AuthVerifyServlet(AuthService authService, Gson gson) {
        this.authService = authService;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
        Preconditions.checkArgument(authorization != null, "Missing authorization header");

        String apiToken = StringUtils.removeStartIgnoreCase(authorization, BEARER_PREFIX);
        Preconditions.checkArgument(StringUtils.isNotBlank(apiToken), "Missing api token");

        AuthVerify authVerify = gson.fromJson(req.getReader(), AuthVerify.class);
        Preconditions.checkArgument(authVerify != null, "Missing request body");

        boolean authOk = authService.verify(authVerify);

        // Write response with appropriate HTTP CODE
    }
}
