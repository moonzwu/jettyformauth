package com.lenovo;

import org.eclipse.jetty.webapp.WebAppContext;

public class AppContextBuilder {

    static public WebAppContext buildWebAppContext(){
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setDescriptor(webAppContext + "/WEB-INF/web.xml");
        webAppContext.setResourceBase(".");
        webAppContext.setContextPath("/");

        return webAppContext;
    }
}