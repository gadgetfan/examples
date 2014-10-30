package com.selectyour.pages.doors;

import java.net.MalformedURLException;
import java.net.URL;

public class WhereToBuy {
    URL onActivate() {
        try {
            return new URL("http://www.volhovec.ru/where-to-buy/");
        } catch (MalformedURLException e) {
            e.printStackTrace();  //TODO: log
            return null;
        }
    }
}
