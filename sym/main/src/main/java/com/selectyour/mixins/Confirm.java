package com.selectyour.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * A simple mixin for attaching a JavaScript confirmation box to the onclick
 * event of any component that implements ClientElement.
 *
 * @author <a href="mailto:chris@thegodcode.net">Chris Lewis</a> Apr 18, 2008
 */
@Import(library = "confirm.js")
public class Confirm {

    @Parameter(value = "Are you sure?", defaultPrefix = BindingConstants.LITERAL)
    private String message;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @InjectContainer
    private ClientElement element;

    @AfterRender
    public void afterRender() {
        javaScriptSupport.addScript("new Confirm('%s', '%s');", element.getClientId(), message);
    }

}