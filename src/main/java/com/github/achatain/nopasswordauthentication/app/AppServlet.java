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
        App app = gson.fromJson(req.getReader(), App.class);

        Preconditions.checkArgument(app != null, "Missing request body");

        LOG.info(String.format("Received an AppServlet POST request with body [%s]", app));

        String apiToken = appService.create(app.getOwnerEmail(), app.getName(), app.getCallbackUrl(), app.getEmailTemplate());

        writeJsonResponse(resp, AppProperties.API_TOKEN, apiToken);
    }
}
