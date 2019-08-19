package com.snapdeal.cps.common.mao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.snapdeal.cps.common.mongo.ProductInfo;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */

@Repository("productInfoMao")
public class ProductInfoMao {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public ProductInfo findByPogId(long pogId){
        return mongoTemplate.findOne(new Query(new Criteria("pogId").is(pogId)),ProductInfo.class);
    }

    public List<ProductInfo> findByUpdateTsGreaterThan(Date timestamp, int limit){
        Query query = new Query(new Criteria("updateTs").gt(timestamp));
        
        query.with(new Sort(Sort.Direction.ASC, "updateTs"));
        
        if(limit > 0){
            query.limit(limit);
        }
        
        return mongoTemplate.find(query, ProductInfo.class);
    }
    
    public List<ProductInfo> findByUpdateTsAndSubCategoryList(Date timestamp, List<Integer> subCategoryList, int limit) {
        
        Query query = new Query(new Criteria("updateTs").gt(timestamp));
        query.addCriteria(new Criteria("subCategoryId").in(subCategoryList));
        
        query.with(new Sort(Sort.Direction.ASC, "updateTs"));
        
        if(limit > 0){
            query.limit(limit);
        }
        
        return mongoTemplate.find(query, ProductInfo.class);
        
    }
    
    public List<ProductInfo> findBySubCategoryIdList(List<Integer> subCategoryList){
        return mongoTemplate.find(new Query(new Criteria("subCategoryID").in(subCategoryList)), ProductInfo.class);
    }
    
    public List<ProductInfo> findBySubCategoryIdListSortByMonthlyRank(List<Integer> subCategoryList, int limit){
        Query query = new Query();
        if(subCategoryList != null && !subCategoryList.isEmpty()){
            query.addCriteria(new Criteria("subCategoryId").in(subCategoryList));
        }
        query.addCriteria(new Criteria("mrank").gte(1));
        query.with(new Sort(Sort.Direction.ASC, "mrank"));
        if(limit > 0){
            query.limit(limit);
        }
        return mongoTemplate.find(query, ProductInfo.class);
    }
    
    public List<ProductInfo> findByMonthlyRankGreaterThanEqualTo(int rank){
        Query query = new Query(new Criteria("mrank").gte(rank));
        query.with(new Sort(Sort.Direction.ASC, "mrank"));
        return mongoTemplate.find(query, ProductInfo.class);
        
    }
    
    public void saveProductInfo(ProductInfo productInfo){
        mongoTemplate.save(productInfo);
    }
    
    public void resetMonthlyRankForAll(){
        Query query = new Query(new Criteria("mrank").gte(1));
        Update update = new Update();
        update.set("mrank", -1);
        mongoTemplate.updateMulti(query, update, ProductInfo.class);
    }
    
    public void findAndModifyMonthlyRank(long pogId, int rank){
        Query query = new Query(new Criteria("pogId").is(pogId));
        Update update = new Update();
        update.set("mrank", rank);
        mongoTemplate.findAndModify(query, update, ProductInfo.class);
    }
    
    public void updateProductTs(long pogId, Date updateTs){
        Query query = new Query(new Criteria("pogId").is(pogId));
        Update update = new Update();
        update.set("updateTs", updateTs);
        
        mongoTemplate.updateFirst(query, update, ProductInfo.class);
    }

       
}
