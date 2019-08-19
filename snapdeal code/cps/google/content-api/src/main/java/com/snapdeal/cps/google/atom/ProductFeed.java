package com.snapdeal.cps.google.atom;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.Key;

/**
 * This class defines a atom product feed as specified by Google guidelines for
 * publishing to GMC
 * 
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 * 
 */
public class ProductFeed {
    @Key("link")
    public List<Link> links = new ArrayList<Link>();

    @Key
    public String id;

    @Key
    public String updated;

    @Key("entry")
    public List<GoogleShoppingItem> entries = new ArrayList<GoogleShoppingItem>();

    public ProductFeed() {
        super();
    }

    public ProductFeed(List<GoogleShoppingItem> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        String feedString = "";
        for (GoogleShoppingItem entry : this.entries) {
            feedString = feedString + entry.batchID + " | ";
        }
        return feedString;
    }
}
