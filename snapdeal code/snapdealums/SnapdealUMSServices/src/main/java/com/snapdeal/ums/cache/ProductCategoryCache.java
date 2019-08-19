/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Dec 8, 2010
 *  @author rahul
 */
package com.snapdeal.ums.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.snapdeal.base.annotations.Cache;
import com.snapdeal.catalog.base.sro.ProductCategorySRO;
import com.snapdeal.core.entity.CategoryView;
import com.snapdeal.core.entity.CompareTechSpecs;
import com.snapdeal.core.entity.PersonalizationWidgetMapping;
import com.snapdeal.core.entity.ProductCategoryMetadata;
import com.snapdeal.core.entity.ProductCategoryPersonalizationWidgetMapping;
import com.snapdeal.core.entity.ProductType;
import com.snapdeal.core.sro.productoffer.ProductCategoryMetadataSRO;
import com.snapdeal.core.sro.productoffer.ProductCategoryPersonalizationWidgetMappingSRO;

@Cache(name = "umscategoryCache")
public class ProductCategoryCache {

    private static final int                                                   DEFAULT_GRID_SIZE                      = 4;

    private List<ProductCategorySRO>                                           categories                             = new ArrayList<ProductCategorySRO>();
    private Map<Integer, ProductCategorySRO>                                   idToCategoryMap                        = new HashMap<Integer, ProductCategorySRO>();
    private Map<String, ProductCategorySRO>                                    pageUrlToCategory                      = new HashMap<String, ProductCategorySRO>();
    private Map<String, Integer>                                               categoryNameToIdMap                    = new HashMap<String, Integer>();
    private List<ProductCategorySRO>                                           parentCategories                       = new ArrayList<ProductCategorySRO>();
    private Map<Integer, List<String>>                                         idToAffinityCategoryMap                = new HashMap<Integer, List<String>>();
    private Map<Integer, LinkedHashMap<String, String>>                        idToTechSpecsMap                       = new HashMap<Integer, LinkedHashMap<String, String>>();
    private Map<Integer, List<Integer>>                                        productTypeIdToCategoryListMap         = new HashMap<Integer, List<Integer>>();
    private Map<Integer, String>                                               idToProductTypeNameMap                 = new HashMap<Integer, String>();
    private Map<Integer, List<ProductCategoryPersonalizationWidgetMappingSRO>> categoryIdToPersonalizationWidgetMap   = new HashMap<Integer, List<ProductCategoryPersonalizationWidgetMappingSRO>>();
    private Map<Integer, Integer>                                              categoryIdToPDPTypeMap                 = new HashMap<Integer, Integer>();
    private Map<Integer, ProductCategoryMetadataSRO>                           categoryIdToproductCategoryMetadataMap = new HashMap<Integer, ProductCategoryMetadataSRO>();
    private Map<Integer, PersonalizationWidgetMapping>                         personalizationWidgetMappingMapById    = new HashMap<Integer, PersonalizationWidgetMapping>();
    private Map<String, Map<Integer, PersonalizationWidgetMapping>>            pwmByPageTypeAndPriority               = new HashMap<String, Map<Integer, PersonalizationWidgetMapping>>();
    private Map<CategorySubcategory, CategoryView>                             categoryGridView                       = new HashMap<CategorySubcategory, CategoryView>();
    private static final Logger                                                LOG                                    = LoggerFactory.getLogger(ProductCategoryCache.class);

    public void addProductType(List<ProductType> productTypeList) {
        for (ProductType productType : productTypeList) {
            idToProductTypeNameMap.put(productType.getId(), productType.getName());
        }
    }

    public Map<Integer, ProductCategoryMetadataSRO> getCategoryIdToproductCategoryMetadataMap() {
        return categoryIdToproductCategoryMetadataMap;
    }

    public void setCategoryIdToproductCategoryMetadataMap(Map<Integer, ProductCategoryMetadataSRO> categoryIdToproductCategoryMetadataMap) {
        this.categoryIdToproductCategoryMetadataMap = categoryIdToproductCategoryMetadataMap;
    }

    public void setPersonalizationWidgetMappingMap(List<PersonalizationWidgetMapping> personalizationWidgetMappingList) {
        for (PersonalizationWidgetMapping pwm : personalizationWidgetMappingList) {
            personalizationWidgetMappingMapById.put(pwm.getId(), pwm);
            if (pwmByPageTypeAndPriority.get(pwm.getPageType()) == null) {
                pwmByPageTypeAndPriority.put(pwm.getPageType(), new HashMap<Integer, PersonalizationWidgetMapping>());
            }
            pwmByPageTypeAndPriority.get(pwm.getPageType()).put(pwm.getPriority(), pwm);
        }
    }

    public PersonalizationWidgetMapping getPersonalizationWidgetMappingById(Integer id) {
        return personalizationWidgetMappingMapById.get(id);
    }

    public PersonalizationWidgetMapping getPersonalizationWidgetMappingByPageTypeAndPriority(String pageType, Integer priority) {
        return pwmByPageTypeAndPriority.get(pageType).get(priority);
    }

    public void addCategoryIdToPersonalizationWidgetMap(List<ProductCategoryPersonalizationWidgetMapping> idWidgetList) {
        for (ProductCategoryPersonalizationWidgetMapping catWidget : idWidgetList) {
            ProductCategoryPersonalizationWidgetMappingSRO catWidgetSRO = getCategoryWidgetDTO(catWidget);
            Integer catId = catWidgetSRO.getCategoryId();
            if (categoryIdToPersonalizationWidgetMap.get(catId) == null) {
                categoryIdToPersonalizationWidgetMap.put(catId, new ArrayList<ProductCategoryPersonalizationWidgetMappingSRO>());
            }
            categoryIdToPersonalizationWidgetMap.get(catId).add(catWidgetSRO);
        }
    }

    public void addProductCategory(ProductCategorySRO categorySRO) {
        categories.add(categorySRO);
        if (categorySRO.getParentCategoryId() == null) {
            parentCategories.add(categorySRO);
        }
        idToCategoryMap.put(categorySRO.getId(), categorySRO);
        pageUrlToCategory.put(categorySRO.getPageUrl().toLowerCase(), categorySRO);
        categoryNameToIdMap.put(categorySRO.getName().trim().toLowerCase(), categorySRO.getId());

        // Populating productTypeIdToCategoryListMap instead of hitting another catalog call
        List<Integer> categoryListByProductTypeId = productTypeIdToCategoryListMap.get(categorySRO.getProductTypeId());
        if (categoryListByProductTypeId == null) {
            categoryListByProductTypeId = new ArrayList<Integer>();
            productTypeIdToCategoryListMap.put(categorySRO.getProductTypeId(), categoryListByProductTypeId);
        }
        categoryListByProductTypeId.add(categorySRO.getId());
    }


    public void addProductCategoryMetadata(List<ProductCategoryMetadata> metadataList) {
        for (ProductCategoryMetadata metadata : metadataList) {
            ProductCategoryMetadataSRO metaSRO = getProductCategoryMetadataSROByEntity(metadata);
            categoryIdToproductCategoryMetadataMap.put(metadata.getCategporyId(), metaSRO);
        }
    }

    private ProductCategoryMetadataSRO getProductCategoryMetadataSROByEntity(ProductCategoryMetadata metadataEntity) {
        ProductCategoryMetadataSRO metadataSRO = new ProductCategoryMetadataSRO();
        metadataSRO.setId(metadataEntity.getId());
        metadataSRO.setProductCategoryId(metadataEntity.getCategporyId());
        metadataSRO.setPdpType(metadataEntity.getPdpType());
        metadataSRO.setDisplayInventory(metadataEntity.isShowInventory());
        metadataSRO.setMinInventoryThreshold(metadataEntity.getMinInventory());
        metadataSRO.setMaxInventoryThreshold(metadataEntity.getMaxInventory());
        metadataSRO.setAttributeSortOrderList(metadataEntity.getAttributeSortOrderList());
        metadataSRO.setShowPincode(metadataEntity.isShowPincode());
        metadataSRO.setShowGoogleAds(metadataEntity.isShowGoogleAds());
        metadataSRO.setGoogleAdsQuery(metadataEntity.getGoogleAdsQuery());
        metadataSRO.setActivateMultivendor(metadataEntity.isActivateMultivendor());
        return metadataSRO;
    }

    public ProductCategoryMetadataSRO getProductCategoryMetadataSROById(Integer id) {
        return categoryIdToproductCategoryMetadataMap.get(id);
    }

    /**************/

    public void addCompTechSpecs(Integer id, List<CompareTechSpecs> comprareTechSpecs) {
        if (!comprareTechSpecs.isEmpty()) {
            LinkedHashMap<String, String> techSpecs = new LinkedHashMap<String, String>();
            for (CompareTechSpecs compareTechSpec : comprareTechSpecs) {
                techSpecs.put(compareTechSpec.getHeading(), compareTechSpec.getSubheading());
            }
            idToTechSpecsMap.put(id, techSpecs);
        }
    }

    public void addCategoryListByProductTypeId(Integer productTypeId, List<Integer> categoryList) {
        productTypeIdToCategoryListMap.put(productTypeId, categoryList);
    }

    private ProductCategoryPersonalizationWidgetMappingSRO getCategoryWidgetDTO(ProductCategoryPersonalizationWidgetMapping categoryWidget) {
        ProductCategoryPersonalizationWidgetMappingSRO categoryWidgetDTO = new ProductCategoryPersonalizationWidgetMappingSRO();
        categoryWidgetDTO.setId(categoryWidget.getId());
        categoryWidgetDTO.setCategoryId(categoryWidget.getCategoryId());
        categoryWidgetDTO.setPersonalizationWidgetId(categoryWidget.getPersonalizationWidgetId());
        categoryWidgetDTO.setWidgetPosition(categoryWidget.getWidgetPosition());
        return categoryWidgetDTO;
    }

    public List<ProductCategoryPersonalizationWidgetMappingSRO> getPersonalizationWidgetSROByCatId(Integer id) {
        return categoryIdToPersonalizationWidgetMap.get(id);
    }

    public List<Integer> getCategoryIdByProductTypeId(Integer id) {
        return productTypeIdToCategoryListMap.get(id);
    }

    public Integer getCatgoryIdByName(String categoryName) {
        return categoryNameToIdMap.get(categoryName.trim().toLowerCase());
    }

    public ProductCategorySRO getCategoryById(Integer categoryId) {
        return idToCategoryMap.get(categoryId);
    }

    public List<ProductCategorySRO> getCategories() {
        return categories;
    }

    public ProductCategorySRO getCategoryByPageUrl(String pageUrl) {
        return pageUrlToCategory.get(pageUrl.toLowerCase());
    }

    public List<ProductCategorySRO> getParentCategories() {
        return parentCategories;
    }

    public List<String> getAffinityCategoryIdList(Integer categoryId) {
        return idToAffinityCategoryMap.get(categoryId);
    }

    public void setIdToAffinityCategoryMap(Map<Integer, List<String>> idToAffinityCategory) {
        this.idToAffinityCategoryMap = idToAffinityCategory;
    }

    public String getProductTypeNamefromId(Integer id) {
        return idToProductTypeNameMap.get(id);
    }

    public void addPDPTypeByCategoryId(Integer categoryId, Integer pdpType) {
        categoryIdToPDPTypeMap.put(categoryId, pdpType);
    }

    public Integer getPDPTypeByCategoryId(Integer categoryId) {
        return categoryIdToPDPTypeMap.get(categoryId);
    }

    public LinkedHashMap<String, String> getIdToTechSpecsMap(Integer id) {
        return idToTechSpecsMap.get(id);
    }

    public void freeze() {

        Collections.sort(categories, new Comparator<ProductCategorySRO>() {
            @Override
            public int compare(ProductCategorySRO arg0, ProductCategorySRO arg1) {
                return arg0.getPriority() > arg1.getPriority() ? 1 : 0;
            }

        });

        // Set the child categories.
        for (ProductCategorySRO category : categories) {
            if (category.getParentCategoryId() != null) {
                ProductCategorySRO parentCategory = idToCategoryMap.get(category.getParentCategoryId());
                if (parentCategory != null) {
                    parentCategory.getChildCategoryIds().add(category.getId());
                } else {
                    LOG.error("Category object found null in cache for id {}", category.getParentCategoryId());
                }
            }
        }

    }

    public CategoryView getCategoryView(int categoryId, int subcategoryId) {
        return categoryGridView.get(new CategorySubcategory(categoryId, subcategoryId));
    }

    public CategoryView getCategoryView(int categoryId) {
        CategoryView categoryView = null;
        for (CategoryView catView : categoryGridView.values()) {
            if (catView.getCategoryId() == categoryId && ((catView.getGridSize().ordinal() + 3) != DEFAULT_GRID_SIZE)) {
                categoryView = catView;
                break;
            }
        }
        return categoryView;
    }

    public void addCategoryView(List<CategoryView> catViews) {
        for (CategoryView categoryView : catViews) {
            addCategoryView(categoryView);
        }
    }

    public void addCategoryView(CategoryView catView) {
        categoryGridView.put(new CategorySubcategory(catView.getCategoryId(), catView.getSubcategoryId()), catView);
    }

    private class CategorySubcategory {
        private int categoryId;
        private int subcategoryId;

        public CategorySubcategory(int categoryId, int subcategoryId) {
            this.categoryId = categoryId;
            this.subcategoryId = subcategoryId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + categoryId;
            result = prime * result + subcategoryId;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof CategorySubcategory)) {
                return false;
            }
            CategorySubcategory other = (CategorySubcategory) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (categoryId != other.categoryId) {
                return false;
            }
            if (subcategoryId != other.subcategoryId) {
                return false;
            }
            return true;
        }

        private ProductCategoryCache getOuterType() {
            return ProductCategoryCache.this;
        }
    }
}