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

package com.github.achatain.nopasswordauthentication.admin;

import com.github.achatain.nopasswordauthentication.utils.AppSettings;
import com.github.achatain.nopasswordauthentication.utils.ServletResponseUtils;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class AdminServlet extends HttpServlet {

    static final String ACTION = "action";
    static final String ACTION_RESET = "reset";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = StringUtils.defaultString(req.getParameter(ACTION));

        switch (action) {
            case ACTION_RESET:
                AppSettings.reset();
                ServletResponseUtils.writeJsonResponse(resp, "action", "reset");
                break;
            default:
                ServletResponseUtils.writeJsonResponse(resp, "action", "unknown");
                break;
        }
    }
}
