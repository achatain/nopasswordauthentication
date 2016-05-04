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
import com.github.achatain.nopasswordauthentication.utils.ServletResponseUtils;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class AuthVerifyServlet extends AuthorizedServlet {

    private static final long serialVersionUID = -4354134743121914401L;

    private final transient Gson gson;
    private final transient AuthService authService;

    @Inject
    public AuthVerifyServlet(AuthService authService, Gson gson) {
        this.authService = authService;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String apiToken = extractApiToken(req);

        AuthVerify authVerify = gson.fromJson(req.getReader(), AuthVerify.class);
        Preconditions.checkArgument(authVerify != null, "Missing request body");

        authVerify.setApiToken(apiToken);

        if (authService.verify(authVerify)) {
            ServletResponseUtils.writeJsonResponse(resp, "authorized", true);
        } else {
            ServletResponseUtils.writeForbiddenResponse(resp);
        }
    }
}
