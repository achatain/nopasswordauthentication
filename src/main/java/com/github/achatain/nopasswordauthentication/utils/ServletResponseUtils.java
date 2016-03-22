package com.github.achatain.nopasswordauthentication.utils;

import com.google.common.base.Preconditions;
import com.google.common.net.MediaType;
import com.google.gson.JsonObject;

import javax.servlet.ServletResponse;
import java.io.IOException;

public class ServletResponseUtils {

    private ServletResponseUtils() {
    }

    public static void writeJsonResponse(ServletResponse resp, String property, String value) throws IOException {
        writeJsonResponse(resp, createSinglePropertyJson(property, value));
    }

    public static void writeJsonResponse(ServletResponse resp, JsonObject json) throws IOException {
        Preconditions.checkArgument(json != null, "Json argument should not be null");
        resp.setContentType(MediaType.JSON_UTF_8.toString());
        resp.getWriter().write(json.toString());
    }

    private static JsonObject createSinglePropertyJson(String property, String value) {
        JsonObject json = new JsonObject();
        json.addProperty(property, value);
        return json;
    }
}
