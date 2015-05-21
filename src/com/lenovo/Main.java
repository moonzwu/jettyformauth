package com.lenovo;


import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Password;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
	    Server server = new Server(8999);
//        ServletContextHandler restContext = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS | ServletContextHandler.SECURITY);
//
//        restContext.addServlet(new ServletHolder(new DefaultServlet() {
//            @Override
//            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//                response.getWriter().append("hello " + request.getUserPrincipal().getName());
//            }
//        }), "/*");
//
//        restContext.addServlet(new ServletHolder(new DefaultServlet() {
//            @Override
//            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//                response.getWriter().append("<form method='POST' action='/j_security_check'>"
//                        + "<input type='text' name='j_username'/>"
//                        + "<input type='password' name='j_password'/>"
//                        + "<input type='submit' value='Login'/></form>");
//            }
//        }), "/login");
//
//        restContext.addServlet(new ServletHolder(new DefaultServlet() {
//            @Override
//            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//                response.getWriter().append("has been authed.");
//            }
//        }), "/user/print");
//
//        Constraint constraint = new Constraint();
//        constraint.setName(Constraint.__FORM_AUTH);;
//        constraint.setRoles(new String[]{"user","admin","moderator"});
//        constraint.setAuthenticate(true);
//
//        ConstraintMapping constraintMapping = new ConstraintMapping();
//        constraintMapping.setConstraint(constraint);
//        constraintMapping.setPathSpec("/*");
//
//        ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
//        securityHandler.addConstraintMapping(constraintMapping);
//        HashLoginService loginService = new HashLoginService();
//        loginService.putUser("username", new Password("password"), new String[] {"user"});
//        securityHandler.setLoginService(loginService);
//
//        FormAuthenticator authenticator = new FormAuthenticator("/login", "/login", false);
//        securityHandler.setAuthenticator(authenticator);
//
//        restContext.setSecurityHandler(securityHandler);


        Constraint constraint = new Constraint();
        constraint.setName(Constraint.__FORM_AUTH);
        constraint.setRoles(new String[]{"user","admin","moderator"});
        constraint.setAuthenticate(true);

        ConstraintMapping constraintMapping = new ConstraintMapping();
        constraintMapping.setConstraint(constraint);
        constraintMapping.setPathSpec("/user/*");

        ConstraintMapping constraintMapping2 = new ConstraintMapping();
        constraintMapping2.setConstraint(constraint);
        constraintMapping2.setPathSpec("/client/*");

        ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
        securityHandler.addConstraintMapping(constraintMapping);
        securityHandler.addConstraintMapping(constraintMapping2);

        HashLoginService loginService = new HashLoginService();
        loginService.putUser("username", new Password("password"), new String[] {"user"});
        securityHandler.setLoginService(loginService);

        FormAuthenticator authenticator = new FormAuthenticator("/login/login.html", "/login/login.html", false);
        securityHandler.setAuthenticator(authenticator);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        WebAppContext appContext = AppContextBuilder.buildWebAppContext();
        appContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        appContext.setSecurityHandler(securityHandler);

        contexts.setHandlers(new Handler[]{appContext});
        server.setHandler(contexts);

        server.start();
        server.join();
    }
}
