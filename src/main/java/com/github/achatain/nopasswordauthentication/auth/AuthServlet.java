package com.github.achatain.nopasswordauthentication.auth;

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

@Singleton
public class AuthServlet extends HttpServlet {

    private static final transient Logger LOG = Logger.getLogger(AuthServlet.class.getName());

    private final transient Gson gson;
    private final transient AuthService authService;

    @Inject
    public AuthServlet(Gson gson, AuthService authService) {
        this.gson = gson;
        this.authService = authService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthRequest authRequest = gson.fromJson(req.getReader(), AuthRequest.class);

        Preconditions.checkArgument(authRequest != null, "Missing request body");

        LOG.info(String.format("Received an AuthServlet POST request with body [%s]", authRequest));

        authService.auth(authRequest);
    }
}
