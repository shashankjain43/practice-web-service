package com.snapdeal.cps.google.atom;

import com.google.api.client.util.Key;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 * 
 */
public class Content {

    @Key("@type")
    String type = "text";

    @Key("text()")
    String description;

    public Content(String description) {
        this.description = description;
    }
}
