package com.selectyour.session;

/**
 * default session
 */
public class Session {
    private Long clientId;

    public Session(Long clientId) {
        if (clientId != null) {
            this.clientId = clientId;
        } else {
            //TODO: add log
            throw new IllegalArgumentException("Session creating error: illegal clientId");
        }
    }

    public Long getClientId() {
        return clientId;
    }

    public boolean isRegisteredUser() {
        return (clientId != null);
    }
}
