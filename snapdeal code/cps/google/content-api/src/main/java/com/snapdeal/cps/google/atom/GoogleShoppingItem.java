package com.snapdeal.cps.google.atom;

import com.google.api.client.util.Key;

/**
 * This class defines a atom feed entry for a Product as specified by Google.
 * 
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 * 
 */
public class GoogleShoppingItem extends BatchableFeedEntry {

    boolean isPrimary;
    String subaccountId;
    
    // XML elements
    // pogId, title, link and content elements are inherited from FeedEntry
    // class

    public GoogleShoppingItem(long pogId, String title, String description, String url, String operationType, boolean isBatchOperation){
        this.pogId = pogId;
        this.title = title;
        this.content = new Content(description);
        this.links.add(new Link("text/html", "alternate",url));
        
        if (isBatchOperation) {
            // Initialize batch properties
            this.batchOperation = new BatchableFeedEntry.BatchOperation();
            this.batchOperation.type = operationType;
            this.batchID = this.pogId.toString();
        }
    }
    
    @Key("sc:image_link")
    String imageLink;

    @Key("sc:content_language")
    final String lang = "en";

    @Key("sc:target_country")
    final String targetCountry = "IN";

    @Key("sc:channel")
    final String channel = "online";

    @Key("sc:adult")
    final boolean isAdult = false;

    @Key("scp:availability")
    String availability;

    @Key("scp:brand")
    String brand;

    @Key("scp:condition")
    final String condition = "new";

    @Key("scp:mobile_link")
    String mLink;
    
    @Key("scp:google_product_category")
    String googleProductCategory;

    @Key("scp:price")
    Price price;

    @Key("scp:product_type")
    String productType;
    
    @Key("scp:age_group")
    String ageGroup;
    
    @Key("scp:gender")
    String gender;

    @Key("scp:adwords_grouping")
    String adwordsGrouping;

    @Key("scp:custom_label_0")
    String customLabel0;
    
    @Key("scp:custom_label_1")
    String customLabel1;
    
    @Key("scp:custom_label_2")
    String customLabel2;
    
    @Key("scp:custom_label_3")
    String customLabel3;
    
    @Key("scp:custom_label_4")
    String customLabel4;
    
    public String getGoogleEntryID() {
        return this.channel + ":" + this.lang + ":" + this.targetCountry + ":" + this.pogId;
    }
    
    public long getId(){
        return this.pogId;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getContent(){
        return this.content.description;
    }
    
    public String getLink(){
        return this.links.get(0).href;
    }
    
    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLang() {
        return lang;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public String getGoogleProductCategory() {
        return googleProductCategory;
    }

    public void setGoogleProductCategory(String googleProductCategory) {
        this.googleProductCategory = googleProductCategory;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = new Price(price);
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdwordsGrouping() {
        return adwordsGrouping;
    }

    public void setAdwordsGrouping(String adGroup) {
        this.adwordsGrouping = adGroup;
    }

    public String getTargetCountry() {
        return targetCountry;
    }

    public String getChannel() {
        return channel;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public String getCondition() {
        return condition;
    }

    public String getCustomLabel0() {
        return customLabel0;
    }

    public void setCustomLabel0(String customLabel0) {
        this.customLabel0 = customLabel0;
    }

    public String getCustomLabel1() {
        return customLabel1;
    }

    public void setCustomLabel1(String customLabel1) {
        this.customLabel1 = customLabel1;
    }

    public String getCustomLabel2() {
        return customLabel2;
    }

    public void setCustomLabel2(String customLabel2) {
        this.customLabel2 = customLabel2;
    }

    public String getCustomLabel3() {
        return customLabel3;
    }

    public void setCustomLabel3(String customLabel3) {
        this.customLabel3 = customLabel3;
    }

    public String getCustomLabel4() {
        return customLabel4;
    }

    public void setCustomLabel4(String customLabel4) {
        this.customLabel4 = customLabel4;
    }

    public boolean isPrimary() {
        return isPrimary;
    }
    
    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    
    public String getSubaccountId() {
        return subaccountId;
    }
    

    public void setSubaccountId(String subaccountId) {
        this.subaccountId = subaccountId;
    }

    
}
