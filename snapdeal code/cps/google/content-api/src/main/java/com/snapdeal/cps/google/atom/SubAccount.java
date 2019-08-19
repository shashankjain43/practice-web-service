package com.snapdeal.cps.google.atom;

import com.google.api.client.util.Key;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 * 
 */
public class SubAccount {

    @Key("sc:adult_content")
    public String isAdult = "no";

    @Key
    SubaccountTitle title;

    @Key
    Link link;

    public SubAccount(String name) {
        this.title = new SubaccountTitle(name);
        this.link = new Link("text/html", "alternate", "http://www.snapdeal.com");
    }
}
