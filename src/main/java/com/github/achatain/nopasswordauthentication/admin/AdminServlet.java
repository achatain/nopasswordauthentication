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

    private static final String ACTION = "action";
    private static final String ACTION_RESET = "reset";

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
