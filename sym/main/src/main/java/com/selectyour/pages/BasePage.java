package com.selectyour.pages;

import com.selectyour.session.Session;
import org.apache.tapestry5.annotations.SessionState;

/**
 * ancestor of all page classes
 */
public abstract class BasePage {
    @SessionState(create = false)
    private Session session;

    public boolean isRegisteredClient() {
        return (session != null);
    }

    protected Session getSession() {
        return session;
    }

    protected void setSession(Session session) {
        assert (session == null);
        this.session = session;
    }
}
