package com.snapdeal.cps.google.atom;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.Key;

/**
 * This class defines a basic entry object which makes up an ATOM feed
 * 
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 * 
 */
public class FeedEntry {

    @Key("sc:id")
    Long pogId;

    @Key("link")
    List<Link> links = new ArrayList<Link>();

    @Key
    String title;

    @Key
    Content content;
}
