package com.github.achatain.nopasswordauthentication.exception;

import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class ExceptionFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(ExceptionFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no-op
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (InternalServerException e) {
            LOG.log(Level.SEVERE, "Internal server error", e);
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage() != null ? e.getMessage() : "Internal server error");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception caught", e);
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage() != null ? e.getMessage() : "Unexpected error");
        }
    }

    @Override
    public void destroy() {
        // no-op
    }
}
