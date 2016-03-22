package com.github.achatain.nopasswordauthentication.auth;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import org.apache.commons.validator.routines.EmailValidator;

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

    @Inject
    public AuthServlet(Gson gson) {
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthRequest authRequest = gson.fromJson(req.getReader(), AuthRequest.class);

        Preconditions.checkArgument(authRequest != null, "Missing request body");
        Preconditions.checkArgument(EmailValidator.getInstance().isValid(authRequest.getUserEmail()), String.format("Email address [%s] is invalid", authRequest.getUserEmail()));

        LOG.info(String.format("Received an AuthServlet POST request with body [%s]", authRequest));

        // TODO
        // 1. Hash the received api token and find the matching app
        // 2. Create an Auth entity with userEmail | authToken | appId | timestamp
        // 3. Build the callbackUrl with query params
        // 4. Send an email to userEmail containing the callbackUrl
    }
}
