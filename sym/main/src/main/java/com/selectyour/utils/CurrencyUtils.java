package com.selectyour.utils;

public class CurrencyUtils {
    /**
     * 100 -> "100"
     * 100.1 -> "100.10"
     *
     * @param number
     * @return
     */
    static public String formatCurrency(double number) {
        //TODO: use long or BigDecimal for price!
        double epsilon = 0.004; // 4 tenths of a cent
        if (Math.abs(Math.round(number) - number) < epsilon) {
            return String.format("%10.0f", number).trim(); // sdb
        } else {
            return String.format("%10.2f", number).trim(); // dj_segfault
        }
    }
}
