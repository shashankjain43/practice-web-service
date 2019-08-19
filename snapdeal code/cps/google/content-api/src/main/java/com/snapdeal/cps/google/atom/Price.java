package com.snapdeal.cps.google.atom;

import com.google.api.client.util.Key;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 * 
 */
public class Price {

    @Key("@unit")
    String unit = "INR";

    @Key("text()")
    int value;

    public Price(int value) {
        this.value = value;
    }
}
