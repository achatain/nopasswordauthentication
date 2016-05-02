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

import com.github.achatain.nopasswordauthentication.utils.AuthorizedServlet;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Singleton
public class AuthRequestServlet extends AuthorizedServlet {

    private static final transient Logger LOG = Logger.getLogger(AuthRequestServlet.class.getName());

    private final transient Gson gson;
    private final transient AuthService authService;

    @Inject
    public AuthRequestServlet(Gson gson, AuthService authService) {
        this.gson = gson;
        this.authService = authService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String apiToken = extractApiToken(req);

        AuthRequest authRequest = gson.fromJson(req.getReader(), AuthRequest.class);
        Preconditions.checkArgument(authRequest != null, "Missing request body");

        authRequest.setApiToken(apiToken);

        LOG.info(String.format("Received an AuthRequestServlet POST request with body [%s]", authRequest));

        authService.request(authRequest);
    }
}
