/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.cps.common.mao.GoogleSubaccountMao;
import com.snapdeal.cps.common.mao.SellerInfoMao;
import com.snapdeal.cps.common.mongo.GoogleSubaccountInfo;
import com.snapdeal.cps.common.mongo.SellerInfo;

/**
 *  @version   1.0, Jun 24, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Service("sellerService")
public class SellerService {
    
    @Autowired
    private SellerInfoMao sellerInfoMao;
    
    @Autowired
    private GoogleSubaccountMao googleSubaccountMao;
    
    public void createSeller(String sellerCode, String sellerName,String displayName){
        sellerInfoMao.insertSeller(new SellerInfo(sellerCode, sellerName, displayName));
    }
    
    public SellerInfo getSellerInfoByGoogleSubaccountId(String googleSubaccountId){
        GoogleSubaccountInfo gsInfo = googleSubaccountMao.findById(googleSubaccountId);
        return sellerInfoMao.findBySellerCode(gsInfo.getSellerCode());
    }
    
    public SellerInfo getSellerInfoByCode(String sellerCode){
        return sellerInfoMao.findBySellerCode(sellerCode);
    }
    
    public void updateDisplayName(String sellerCode, String displayName){
        sellerInfoMao.updateSellerDisplayName(sellerCode, displayName);
    }
    
    public void saveSellerInfo(SellerInfo sellerInfo){
        sellerInfoMao.saveSellerInfo(sellerInfo);
    }
    
}
