package com.snapdeal.cps.common.mao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.snapdeal.cps.common.mongo.CategoryInfo;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */

@Repository("categoryInfoMao")
public class CategoryInfoMao {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public CategoryInfo findBySubCategoryId(int subCategoryId){
        return mongoTemplate.findOne(new Query(new Criteria("subCategoryId").is(subCategoryId)), CategoryInfo.class);
    }
    
    public List<CategoryInfo> findbyParentCategoryId(int parentCategoryId){
        return mongoTemplate.find(new Query(new Criteria("parentCategoryId").is(parentCategoryId)), CategoryInfo.class);
    }
    
    public void saveCategoryInfo(CategoryInfo categoryInfo){
        mongoTemplate.save(categoryInfo);
    }

    
    public List<CategoryInfo> findSubCategoryNotIn(List<Integer> excludedSubcategories) {
        return mongoTemplate.find(new Query(new Criteria("subCategoryId").nin(excludedSubcategories)), CategoryInfo.class);
    }
    
}
