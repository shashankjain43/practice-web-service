package com.snapdeal.cps.common.dto;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */
public class PublisherProductDTO {

    private long pogId;
    private String title;
    private String url;
    private String description;
    private String imageLink;
    private String brand;
    private int subCategoryID;
    private String subCategoryName;
    private int parentCategoryID;
    private String parentCategoryName;
    private int price;
    private String mrp;
    private String discount;
    private String availability;
    
    public long getPogId() {
        return pogId;
    }
    public void setPogId(long pogId) {
        this.pogId = pogId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImageLink() {
        return imageLink;
    }
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public int getSubCategoryID() {
        return subCategoryID;
    }
    public void setSubCategoryID(int subCategoryID) {
        this.subCategoryID = subCategoryID;
    }
    public String getSubCategoryName() {
        return subCategoryName;
    }
    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
    public int getParentCategoryID() {
        return parentCategoryID;
    }
    public void setParentCategoryID(int parentCategoryID) {
        this.parentCategoryID = parentCategoryID;
    }
    public String getParentCategoryName() {
        return parentCategoryName;
    }
    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getAvailability() {
        return availability;
    }
    public void setAvailability(String availability) {
        this.availability = availability;
    }
    public String getMrp() {
        return mrp;
    }
    public void setMrp(String mrp) {
        this.mrp = mrp;
    }
    public String getDiscount() {
        return discount;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
