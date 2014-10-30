package com.selectyour.pages.component;// MaiseyenkaDP gdfan 07.07.12 10:57

import com.selectyour.pages.BasePage;

/**
 * Page to bye component
 */
public class Bye extends BasePage {
    /**
     * price of component in USD
     */
    private final String price = "3150 российских рублей"; //should correlate with wmAccount
    /**
     * webmoney account for price
     */
    private final String wmAccount = "R418154769280"; //should correlate with price

    public String getPrice() {
        return price;
    }

    public String getWmAccount() {
        return wmAccount;
    }
}
