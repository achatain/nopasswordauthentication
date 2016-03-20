package com.github.achatain.nopasswordauthentication.app;

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
        System.out.println("App Service is:" + appService);
        this.appService = appService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("bonjour");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(AppProperties.ID);
        String owner = req.getParameter(AppProperties.OWNER);
        String name = req.getParameter(AppProperties.NAME);
        String callbackUrl = req.getParameter(AppProperties.CALLBACK_URL);
        String emailTemplate = req.getParameter(AppProperties.EMAIL_TEMPLATE);

        String apiToken = appService.create(id, owner, name, callbackUrl, emailTemplate);

        resp.getWriter().write(apiToken);
    }
}
