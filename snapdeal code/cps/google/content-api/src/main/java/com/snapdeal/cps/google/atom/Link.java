package com.snapdeal.cps.google.atom;

import com.google.api.client.util.Key;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 * 
 */
public class Link {

    @Key("@type")
    String type;

    @Key("@rel")
    String rel;

    @Key("@href")
    String href;

    public Link(String type, String rel, String href) {
        this.type = type;
        this.rel = rel;
        this.href = href;
    }
}
