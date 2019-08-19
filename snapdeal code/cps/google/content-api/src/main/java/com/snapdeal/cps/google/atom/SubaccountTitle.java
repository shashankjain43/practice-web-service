package com.snapdeal.cps.google.atom;

import com.google.api.client.util.Key;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 * 
 */
public class SubaccountTitle {

    @Key("@type")
    String type = "text";

    @Key("text()")
    String name;

    public SubaccountTitle(String name) {
        this.name = name;
    }
}
