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
import com.google.common.net.MediaType;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletResponseUtils {

    private ServletResponseUtils() {
    }

    public static void writeJsonResponse(HttpServletResponse resp, String property, String value) throws IOException {
        writeJsonResponse(resp, createSinglePropertyJson(property, value));
    }

    public static void writeJsonResponse(HttpServletResponse resp, JsonObject json) throws IOException {
        Preconditions.checkArgument(json != null, "Json argument should not be null");
        resp.setContentType(MediaType.JSON_UTF_8.toString());
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json.toString());
    }

    private static JsonObject createSinglePropertyJson(String property, String value) {
        JsonObject json = new JsonObject();
        json.addProperty(property, value);
        return json;
    }
}
