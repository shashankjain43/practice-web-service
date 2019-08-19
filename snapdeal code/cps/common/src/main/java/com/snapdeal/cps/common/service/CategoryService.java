/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.cps.common.mao.CategoryInfoMao;
import com.snapdeal.cps.common.mongo.CategoryInfo;

/**
 *  @version   1.0, Jul 11, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Service("categoryService")
public class CategoryService {

    @Autowired
    private CategoryInfoMao categoryInfoMao;
    
    public CategoryInfo getCategoryInfoBySubCategoryId(int subCategoryId){
        return categoryInfoMao.findBySubCategoryId(subCategoryId);
    }
    
    public List<Integer> getSubCategoriesByCategoryId(int categoryId){
        List<CategoryInfo> categInfoList = categoryInfoMao.findbyParentCategoryId(categoryId);
        if(categInfoList == null || categInfoList.isEmpty()){
            return null;
        }
        List<Integer> subCategories = new ArrayList<Integer>();
        for(CategoryInfo categInfo: categInfoList){
            subCategories.add(categInfo.getSubCategoryId());
        }
        return subCategories;
    }
    
    public void saveCategoryInfo(CategoryInfo categoryInfo){
        categoryInfoMao.saveCategoryInfo(categoryInfo);
    }
    
    public List<Integer> getAllSubCategoriesExcept(List<Integer> excludedSubcategories){
        List<CategoryInfo> categInfoList = categoryInfoMao.findSubCategoryNotIn(excludedSubcategories);
        if(categInfoList == null || categInfoList.isEmpty()){
            return null;
        }
        List<Integer> subCategories = new ArrayList<Integer>();
        for(CategoryInfo categInfo: categInfoList){
            subCategories.add(categInfo.getSubCategoryId());
        }
        return subCategories;
    }
}
