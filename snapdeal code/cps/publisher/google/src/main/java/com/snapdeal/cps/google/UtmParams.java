package com.snapdeal.cps.google;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */
public class UtmParams {

    private static final String utmSource = "earth_feed";
    private String utmCampaign;
    private String utmMedium;
    
    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign;
    }
    public void setUtmMedium(String utmMedium) {
        this.utmMedium = utmMedium;
    }
    public String getUtmCampaign() {
        return utmCampaign;
    }
    public String getUtmMedium() {
        return utmMedium;
    }
    
    public String toString(){
        return  "utm_source=" + utmSource
                +"&utm_campaign=" + utmCampaign
                +"&utm_medium=" + utmMedium;
    }
    
}
