/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.cps.common.mao.ProductInfoMao;
import com.snapdeal.cps.common.mongo.ProductInfo;

/**
 *  @version   1.0, Jul 8, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Service("productService")
public class ProductService {

    @Autowired
    private ProductInfoMao productInfoMao;
    
    public void saveProduct(ProductInfo productInfo){
        productInfo.setUpdateTs(Calendar.getInstance().getTime());
        productInfoMao.saveProductInfo(productInfo);
    }
    
    public ProductInfo getProductById(long pogId){
        return productInfoMao.findByPogId(pogId);
    }
    
    public List<ProductInfo> getProductsUpdatedFromTs(Date updateTs, int limit){
        return productInfoMao.findByUpdateTsGreaterThan(updateTs, limit);
    }
    
    public List<ProductInfo> getProductsUpdatedFromTsFilterBySubCategoryList(Date updateTs, List<Integer> subCategoryList, int limit){
        return productInfoMao.findByUpdateTsAndSubCategoryList(updateTs, subCategoryList, limit);
    }
    
    public List<ProductInfo> getProductsBySubCategoryList(List<Integer> subCategoryList){
        return productInfoMao.findBySubCategoryIdList(subCategoryList);
    }
    
    public List<ProductInfo> getProductsBySubCategoryListSortByMonthlySales(List<Integer> subCategoryList, int limit){
        return productInfoMao.findBySubCategoryIdListSortByMonthlyRank(subCategoryList, limit);
    }
    
    public List<ProductInfo> getProductsByMonthlyRankGreaterThanEqualTo(int rank){
        return productInfoMao.findByMonthlyRankGreaterThanEqualTo(rank);
    }
    
    public void updateProductTs(long pogId, Date updateTs){
        productInfoMao.updateProductTs(pogId, updateTs);
    }
    
    public void resetMonthlyRankForAll(){
        productInfoMao.resetMonthlyRankForAll();
    }
    
    public void updateMonthlyRank(long pogId, int rank){
        productInfoMao.findAndModifyMonthlyRank(pogId, rank);
    }
}
