package com.selectyour.gwtserver.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public abstract class BaseSpringGwtServlet extends RemoteServiceServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContextUtils.
                getRequiredWebApplicationContext(getServletContext()).
                getAutowireCapableBeanFactory().
                autowireBean(this);
    }
}
