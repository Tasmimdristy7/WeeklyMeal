package com.dristy.WeeklyMealSpring.filter;

import com.dristy.WeeklyMealSpring.util.Util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(urlPatterns = "/weekly-meal/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // Do nothing.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        if (Util.isValidSession(servletRequest.getSession(false))) {
            // Disables caching of secured pages
            servletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            servletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            servletResponse.setDateHeader("Expires", 0); // Proxies.

            chain.doFilter(request, response);
        } else {
            servletResponse.sendRedirect(String.format("%s/", servletRequest.getContextPath()));
        }
    }

    @Override
    public void destroy() {
        // Do nothing.
    }
}
