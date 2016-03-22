package com.github.achatain.nopasswordauthentication.app;

import com.google.common.net.MediaType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class AppServlet extends HttpServlet {

    private final transient AppService appService;

    @Inject
    public AppServlet(AppService appService) {
        this.appService = appService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("bonjour");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ownerEmail = req.getParameter(AppProperties.OWNER_EMAIL);
        String name = req.getParameter(AppProperties.NAME);
        String callbackUrl = req.getParameter(AppProperties.CALLBACK_URL);
        String emailTemplate = req.getParameter(AppProperties.EMAIL_TEMPLATE);

        String apiToken = appService.create(ownerEmail, name, callbackUrl, emailTemplate);

        resp.getWriter().write(apiToken);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        App app = gson.fromJson(req.getReader(), App.class);

        String apiToken = appService.create(app.getOwnerEmail(), app.getName(), app.getCallbackUrl(), app.getEmailTemplate());

        JsonObject response = new JsonObject();
        response.addProperty("apiToken", apiToken);

        resp.setContentType(MediaType.JSON_UTF_8.toString());
        resp.getWriter().write(gson.toJson(response));
    }
}
