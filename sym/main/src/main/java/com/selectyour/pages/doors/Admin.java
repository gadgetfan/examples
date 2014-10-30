package com.selectyour.pages.doors;

public class Admin {
    private boolean logged = false;

    Object onActivate() {
        if (logged) {
            return null;
        } else {
            return Index.class;
        }
    }

    Object onActivate(String pin) {
        if (pin.equals("1024")) {
            logged = true;
        }

        return onActivate();
    }

}
