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

package com.github.achatain.nopasswordauthentication.app;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.github.achatain.nopasswordauthentication.utils.ServletResponseUtils.writeJsonResponse;

@Singleton
public class AppServlet extends HttpServlet {

    private static final transient Logger LOG = Logger.getLogger(AppServlet.class.getName());

    private final transient AppService appService;
    private final transient Gson gson;

    @Inject
    public AppServlet(Gson gson, AppService appService) {
        this.gson = gson;
        this.appService = appService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppDto appDto = gson.fromJson(req.getReader(), AppDto.class);

        Preconditions.checkArgument(appDto != null, "Missing request body");

        LOG.info(String.format("Received an AppServlet POST request with body [%s]", appDto));

        String apiToken = appService.create(appDto);

        writeJsonResponse(resp, AppProperties.API_TOKEN, apiToken);
    }
}
