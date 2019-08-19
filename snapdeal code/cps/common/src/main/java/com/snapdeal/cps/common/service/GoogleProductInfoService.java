/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.cps.common.mao.GoogleCategoryMao;
import com.snapdeal.cps.common.mao.GoogleKeywordInfoMao;
import com.snapdeal.cps.common.mao.GoogleProductListingMao;
import com.snapdeal.cps.common.mao.GoogleSubaccountMao;
import com.snapdeal.cps.common.mongo.GoogleCategoryInfo;
import com.snapdeal.cps.common.mongo.GoogleKeywordInfo;
import com.snapdeal.cps.common.mongo.GoogleProductListing;
import com.snapdeal.cps.common.mongo.GoogleSubaccountInfo;

/**
 *  @version   1.0, Jul 11, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Service("googleProductService")
public class GoogleProductInfoService {

    @Autowired
    private GoogleCategoryMao googleCategoryMao;
    
    @Autowired
    private GoogleProductListingMao googlePLMao;
    
    @Autowired
    private GoogleKeywordInfoMao googleKeywordMao;

    @Autowired
    private GoogleSubaccountMao googleSubaccountMao;
    
    public void createGoogleSubaccount(String sellerCode, String gsId){
        googleSubaccountMao.insertGoogleSubaccountInfo(sellerCode, gsId);
    }
    
    public String getKeywordsByBrandAndCategoryId(String brand, int categoryId){
        GoogleKeywordInfo bsKeyInfo = googleKeywordMao.findByBrandAndSubCategoryId(brand, categoryId);
        GoogleKeywordInfo sKeyInfo = googleKeywordMao.findByNoBrandAndSubCategoryId(categoryId);
        
        String keywords = "";
        
        if(bsKeyInfo != null){
            keywords = keywords + bsKeyInfo.getKeywords();
        }
        
        if(sKeyInfo != null){
            keywords = keywords + ", " + sKeyInfo.getKeywords();
        }
        
        return keywords;
    }
    
    public String getGoogleCategory(int categoryId){
        GoogleCategoryInfo categInfo = googleCategoryMao.findByCategoryId(categoryId);
        if(categInfo == null){
            return null;
        }
        return categInfo.getName();
    }
    
    public GoogleCategoryInfo getGoogleCategoryInfo(int categoryId){
        return googleCategoryMao.findByCategoryId(categoryId);
    }
    
    public List<Integer> getBannedCategoryIds(){
        List<GoogleCategoryInfo> categories = googleCategoryMao.findByBannedStatus(true);
        if(categories == null || categories.isEmpty()){
            return null;
        }
        List<Integer> bannedCategories = new ArrayList<Integer>();
        for(GoogleCategoryInfo category: categories){
            bannedCategories.add(category.getCategoryId());
        }
        return bannedCategories;
    }
    
    public List<String> getGoogleSubaccountsByProductId(long pogId){
        GoogleProductListing googlePL = googlePLMao.findByPogId(pogId);
        if(googlePL == null){
            return null;
        }
        return googlePL.getGsIds();
    }
    
    public GoogleProductListing getGoogleProductListingByProductId(long pogId){
        return googlePLMao.findByPogId(pogId);
    }

    public String getGoogleSubaccountIdBySellerCode(String sellerCode){
        GoogleSubaccountInfo gsInfo = googleSubaccountMao.findBySellerCode(sellerCode);
        if(gsInfo == null){
            return null;
        }
        
        return gsInfo.getId();
    }

    public String getSellerCodeByGoogleSubaccountId(String gsId){
        GoogleSubaccountInfo gsInfo = googleSubaccountMao.findById(gsId);
        if(gsInfo == null){
            return null;
        }
        return gsInfo.getSellerCode();
    }
    
    public void setPrimarySeller(long pogId, String gsId) {
        GoogleProductListing googleListing = getGoogleProductListingByProductId(pogId);
        List<String> subaccounts = new ArrayList<String>();
        if(googleListing == null){
            subaccounts.add(0,gsId);
            googleListing = new GoogleProductListing(pogId, subaccounts);
        }else{
            subaccounts = googleListing.getGsIds();
            if(subaccounts != null && !subaccounts.isEmpty()){
                subaccounts.remove(gsId);
            }
            subaccounts.add(0,gsId);
            googleListing.setGsIds(subaccounts);
        }
        
        googlePLMao.saveProductListing(googleListing);
        
    }
    
    public void setCustomLabel(long pogId, String label, int index){
        if(index < 0 || index > 3){
            return;
        }
        GoogleProductListing googleListing = getGoogleProductListingByProductId(pogId);
        if(googleListing == null){
            return;
        }
        
        List<String> labels = googleListing.getLabels();
        if(labels == null || labels.isEmpty()){
            for(int i=0;i<index;i++){
                labels.add(i, "");
            }
        }else{
            labels.remove(index);
        }
        labels.add(index,label);
        
        googleListing.setLabels(labels);
        googlePLMao.saveProductListing(googleListing);
    }
    
    public void setBestsellerLabel(long pogId){
        GoogleProductListing googleListing = getGoogleProductListingByProductId(pogId);
        if(googleListing == null){
            return;
        }
        
        List<String> labels = googleListing.getLabels();
        if(labels != null && !labels.isEmpty()){
            labels.remove(0);
        }else{
            labels = new ArrayList<String>();
        }
        
        labels.add(0, "bestseller");
        googleListing.setLabels(labels);
        googlePLMao.saveProductListing(googleListing);
    }
    
    public void deleteSubaccountById(String subaccountId){
        // remove from subaccount info
        googleSubaccountMao.removeSubaccountById(subaccountId);
        
        // remove from google product listing
        googlePLMao.removeSubaccountById(subaccountId);
        
    }
    
    public void resetBestSellingLabelForAll(){
        googlePLMao.resetBestSellingLabelForAll();
    }
}
