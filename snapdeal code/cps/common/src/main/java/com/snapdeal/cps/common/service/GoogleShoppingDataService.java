package com.snapdeal.cps.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.cps.common.SellerAvailability;
import com.snapdeal.cps.common.dto.GoogleProductDTO;
import com.snapdeal.cps.common.dto.GoogleSellerDTO;
import com.snapdeal.cps.common.mongo.CategoryInfo;
import com.snapdeal.cps.common.mongo.GoogleCategoryInfo;
import com.snapdeal.cps.common.mongo.GoogleProductListing;
import com.snapdeal.cps.common.mongo.ProductInfo;
import com.snapdeal.cps.common.mongo.SellerInfo;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */
@Service("googleShoppingDataService")
public class GoogleShoppingDataService {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private GoogleProductInfoService googleProductService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private SellerService sellerService;
    
    @Autowired
    private ProcessUtilityService processUtilityService;

    private void filterBannedCategories(List<Integer> subCategories){
        List<Integer> bannedCategories = googleProductService.getBannedCategoryIds();
        Iterator<Integer> it = subCategories.iterator();
        while(it.hasNext()){
            if(bannedCategories.contains(it.next())){
                it.remove();
            }
        }
    }
    
    private void removeBannedCategoryProducts(List<ProductInfo> productInfoList){
        List<Integer> bannedCategories = googleProductService.getBannedCategoryIds();
        Iterator<ProductInfo> it = productInfoList.iterator();
        while(it.hasNext()){
            if(bannedCategories.contains(it.next().getSubCategoryId())){
                it.remove();
            }
        }
    }
    
    private GoogleProductDTO getGoogleProduct(ProductInfo product, Logger log){
        
        long pogId = product.getPogId();
        int subCategoryId = product.getSubCategoryId();
        
        GoogleProductDTO googleProduct = new GoogleProductDTO(pogId);
        
        googleProduct.setTitle(product.getTitle());
        googleProduct.setDescription(product.getDescription());
        googleProduct.setUrl(product.getUrl());
        googleProduct.setMobileUrl(product.getUrl().replaceFirst("www", "m"));
        googleProduct.setImageUrl(product.getImageLink());
        googleProduct.setBrand(product.getBrand());
        googleProduct.setUpdateTs(product.getUpdateTs());
        
        // Get category info
        CategoryInfo categoryInfo = categoryService.getCategoryInfoBySubCategoryId(subCategoryId);
        if(categoryInfo == null){
            log.error("Category info unavailable for sub category ID: " + subCategoryId + ". POG: " + pogId);
            processUtilityService.logError("category", ""+subCategoryId, "Category info missing");
            
            googleProduct.setValid(false);
            return googleProduct;
        }
        
        // Set category info
        googleProduct.setSubCategoryId(subCategoryId);
        googleProduct.setParentCategoryId(categoryInfo.getParentCategoryId());
        if(categoryInfo.getParentCategoryId()==categoryInfo.getSubCategoryId()){
            googleProduct.setProductType(categoryInfo.getSubCategoryName());
        }else{
            googleProduct.setProductType(categoryInfo.getParentCategoryName() + " > " + categoryInfo.getSubCategoryName());
        }

        // Get google product category info
        GoogleCategoryInfo googleCategoryInfo = googleProductService.getGoogleCategoryInfo(subCategoryId);
        if(googleCategoryInfo == null){
            log.error("Google Category for the subcategory does not exist for POG: " + pogId);
            processUtilityService.logError("category", ""+subCategoryId, "Google category mapping missing");
            
            googleProduct.setValid(false);
            return googleProduct;
        }
        
        // Set google product category info
        googleProduct.setGoogleProductCategory(googleCategoryInfo.getName());
        googleProduct.setAgeGroup(googleCategoryInfo.getAgeGroup());
        googleProduct.setGender(googleCategoryInfo.getGender());
        
        // Get and set listing details
        String primarySellerCode = product.getSellerCode();
        String listedGsId = null;
        String keywords="";
        String productKeywords = "";
        
        GoogleProductListing googleListing = googleProductService.getGoogleProductListingByProductId(pogId);
        if(googleListing != null){
            listedGsId = googleListing.getListedPrimarySubaccountId();
            
            googleProduct.setAdwordsGroup(googleListing.getAdGroup());
            googleProduct.setCustomLabels(googleListing.getLabels());
            
            productKeywords = googleListing.getKeywords();
        }
        
        // Get keywords at brand and subcategory level
        String brandAndCategorykeywords = googleProductService.getKeywordsByBrandAndCategoryId(product.getBrand(), product.getSubCategoryId());
        if(brandAndCategorykeywords!=null && !brandAndCategorykeywords.isEmpty()){
            keywords = keywords + brandAndCategorykeywords;
            if(productKeywords!=null && !productKeywords.isEmpty()){
                keywords = keywords + " ," + productKeywords;
            }
        }else{
            if(productKeywords!=null && !productKeywords.isEmpty()){
                keywords = keywords + productKeywords;
            }
        }
        googleProduct.setKeywords(keywords);
        
        // If seller mapping is disabled
        if(primarySellerCode == null || primarySellerCode.isEmpty()){
            // Set price to minimum acceptable value by Google
            googleProduct.setPrice(1);
            
            if(listedGsId == null){
                googleProduct.setValid(false);
                return googleProduct;
            }
            
            // Product to be marked as out of stock in listed account
            String listedSellerCode = googleProductService.getSellerCodeByGoogleSubaccountId(listedGsId);
            if(listedSellerCode == null || listedSellerCode.isEmpty()){
                googleProduct.setValid(false);
                return googleProduct;
            }
            
            GoogleSellerDTO seller = new GoogleSellerDTO();
            seller.setSellerCode(listedSellerCode);
            seller.setSubaccountId(listedGsId);
            seller.setAvailability(SellerAvailability.OUT_OF_STOCK.getStatus());
            seller.setPrimary(true);
            
            googleProduct.addSeller(seller);
            
        }else{
            // Set price
            if(product.getPrice() > 0){
                googleProduct.setPrice(product.getPrice());
            }else{
                // Set price to minimum acceptable value by Google
                googleProduct.setPrice(1);
            }
            
            String primaryGsId = googleProductService.getGoogleSubaccountIdBySellerCode(primarySellerCode);
            SellerInfo sellerInfo = sellerService.getSellerInfoByCode(primarySellerCode);
            if(sellerInfo == null){
                log.error("Seller info could not be obtained for : " + primarySellerCode + ".POG: " + pogId);
                processUtilityService.logError("seller", primarySellerCode, "Seller info missing");
                
                googleProduct.setValid(false);
                return googleProduct;
            }
            
            // Set primary seller details
            GoogleSellerDTO primarySeller = new GoogleSellerDTO();
            primarySeller.setSellerCode(primarySellerCode);
            primarySeller.setSellerName(sellerInfo.getName());
            primarySeller.setSubaccountId(primaryGsId);
            primarySeller.setAvailability(product.getAvailability());
            primarySeller.setPrimary(true);
            
            googleProduct.addSeller(primarySeller);
            
            // If there is a seller change, currently listed will be marked as out of stock
            if(listedGsId != null && !listedGsId.equals(primaryGsId)){
                String listedSellerCode = googleProductService.getSellerCodeByGoogleSubaccountId(listedGsId);

                GoogleSellerDTO secondSeller = new GoogleSellerDTO();
                secondSeller.setSellerCode(listedSellerCode);
                secondSeller.setSubaccountId(listedGsId);
                secondSeller.setAvailability(SellerAvailability.OUT_OF_STOCK.getStatus());
                secondSeller.setPrimary(false);

                googleProduct.addSeller(secondSeller);
            }
        }
        googleProduct.setValid(true);
        return googleProduct;
    }
    
    public void createSubaccount(String sellerCode, String subaccountId){
        googleProductService.createGoogleSubaccount(sellerCode, subaccountId);
    }
    
    public String getGoogleSubaccountIdBySellerCode(String sellerCode){
        return googleProductService.getGoogleSubaccountIdBySellerCode(sellerCode);
    }
    
    public void updateProductListing(long pogId, String gsId){
        googleProductService.setPrimarySeller(pogId,gsId);
    }
    
    /**
     * This method returns product objects which have been updated
     * after the provided timestamp. If both timestamp and subcategories is null, no products are returned
     * 
     * @param timestamp
     *          : The time after which the records need to be selected, if null, will ignore timestamp
     * @param subCategories
     *          : List of subCategories, if null or empty, will fetch for all
     * @param limit 
     *          : The maximum number of records to be selected from DB, 0 for all
     * @param log
     *          : A logger object
     * @return List of GoogleProduct objects
     */
    public List<GoogleProductDTO> getUpdatedProductListBySubCategoryList(Date timestamp, List<Integer> subCategories, int limit, Logger log) {
        List<GoogleProductDTO> googleProducts = new ArrayList<GoogleProductDTO>();
        try{
            List<ProductInfo> updatedProducts;
            if(timestamp == null){
                log.error("No timestamp given");
                return null;
            }
            
            // Filter out banned categories
            filterBannedCategories(subCategories);
            
            log.info("Number of categories: " + subCategories.size());
            
            if(subCategories == null || subCategories.isEmpty()){
                updatedProducts = productService.getProductsUpdatedFromTs(timestamp, limit);
            }else{
                updatedProducts = productService.getProductsUpdatedFromTsFilterBySubCategoryList(timestamp, subCategories, limit);
            }
            
            if(updatedProducts == null || updatedProducts.isEmpty()){
                log.info("No products could be obtained from DB.");
                return null;
            }
            
            log.info("Number of products obtained from DB: " + updatedProducts.size());
            for(ProductInfo product: updatedProducts){
                GoogleProductDTO googleProduct = getGoogleProduct(product, log);
                googleProducts.add(googleProduct);
            }
            
        }catch(Exception ex){
            log.error("Unable to obtain complete data from DB");
            log.error(ExceptionUtils.getFullStackTrace(ex));
        }
        
        return googleProducts;
    }
    
    
    public List<GoogleProductDTO> getProductListBySubCategoryList(List<Integer> subCategories, Logger log){
        List<GoogleProductDTO> googleProducts = new ArrayList<GoogleProductDTO>();
        
        if(subCategories == null || subCategories.isEmpty()){
            log.error("No Subcategories provided");
            return null;
        }
        
        // Filter out banned categories
        filterBannedCategories(subCategories);
        
        log.info("Number of categories: " + subCategories.size());
        
        List<ProductInfo> products = productService.getProductsBySubCategoryList(subCategories);
        
        log.info("Number of products obtained from DB: " + products.size());
        for(ProductInfo product: products){
            GoogleProductDTO googleProduct = getGoogleProduct(product, log);
            googleProducts.add(googleProduct);
        }
        
        return googleProducts;
    }
    
    public GoogleProductDTO getProductById(long pogId, Logger log){
        ProductInfo product = productService.getProductById(pogId);
        
        // Get banned categories
        List<Integer> bannedCategories = googleProductService.getBannedCategoryIds();
        
        int subCategoryId = product.getSubCategoryId();
        
        // Check if category belongs to the list of banned categoriies
        if(bannedCategories.contains(subCategoryId)){
            log.warn("Subcategory belongs to banned categories. Ignoring POG: " + pogId);
            return null;
        }
        
        return getGoogleProduct(product, log);
    }
    
    /**
     * This method converts the given productInfo list into googleProduct list
     *  If productInfo list is null, no googleProducts are returned
     * 
     * @param productInfoList 
     *          : List of productInfo, if null or empty then return null
     * @param log
     *          : A logger object
     * @return List of GoogleProduct objects
     */
    public List<GoogleProductDTO> getGoogleProductListByProductInfoList(List<ProductInfo> productInfoList, Logger log) {
        
        // Remove products which belongs to a banned subcategories
        removeBannedCategoryProducts(productInfoList);
        
        if(productInfoList == null || productInfoList.isEmpty()){
            log.info("ProductInfo list is empty or null");
            return null;
        }
        
        log.info("Number of products remaining after banned filtering: " + productInfoList.size());
        
        List<GoogleProductDTO> googleProducts = new ArrayList<GoogleProductDTO>();
        for(ProductInfo product: productInfoList){
            GoogleProductDTO googleProduct = getGoogleProduct(product, log);
            googleProducts.add(googleProduct);
        }
        return googleProducts;
    }
}