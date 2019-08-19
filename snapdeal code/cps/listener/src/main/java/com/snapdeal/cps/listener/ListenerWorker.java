/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.catalog.base.enums.CatalogStatusEnum;
import com.snapdeal.catalog.base.enums.ProductOfferState;
import com.snapdeal.catalog.base.model.GetPOGDetailByIdRequest;
import com.snapdeal.catalog.base.model.GetPOGDetailListBySUPCListRequest;
import com.snapdeal.catalog.base.model.GetPOGDetailListResponse;
import com.snapdeal.catalog.base.model.GetPOGDetailResponse;
import com.snapdeal.catalog.base.sro.POGDetailSRO;
import com.snapdeal.catalog.base.sro.POGDetailSRO.ProductCategoryDetailSRO;
import com.snapdeal.catalog.base.sro.POGDetailSRO.ProductOfferDetailSRO;
import com.snapdeal.cps.common.CPSProperties;
import com.snapdeal.cps.common.SellerAvailability;
import com.snapdeal.cps.common.dto.ProductDTO;
import com.snapdeal.cps.common.dto.SellerDTO;
import com.snapdeal.cps.common.mongo.CategoryInfo;
import com.snapdeal.cps.common.mongo.ProductInfo;
import com.snapdeal.cps.common.mongo.SellerInfo;
import com.snapdeal.cps.common.service.CategoryService;
import com.snapdeal.cps.common.service.ProductService;
import com.snapdeal.cps.common.service.SellerService;
import com.snapdeal.cps.listener.utils.ProductUtils;
import com.snapdeal.ipms.base.api.getVendorsInventoryPricingBySUPC.v0.GetVendorInventoryPricingBySUPCRequest;
import com.snapdeal.ipms.base.api.getVendorsInventoryPricingBySUPC.v0.GetVendorInventoryPricingBySUPCResponse;
import com.snapdeal.ipms.base.api.getVendorsInventoryPricingBySUPC.v0.SUPCAllVendorInventorySRO;
import com.snapdeal.ipms.base.api.getVendorsInventoryPricingBySUPC.v0.SUPCAvailableVendorsSRO;
import com.snapdeal.ipms.base.api.getVendorsInventoryPricingBySUPC.v0.VendorDetailInventoryPricingSRO;
import com.snapdeal.ipms.base.common.InventoryPricingStatus;
import com.snapdeal.ipms.base.exception.IPMSServiceException.IPMSServiceErrorCode;
import com.snapdeal.ipms.client.service.IIPMSClientService;
import com.snapdeal.product.client.service.IProductClientService;

/**
 *  @version   1.0, Jul 11, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Component("listenerWorker")
public class ListenerWorker {

    @Autowired
    private IProductClientService catalogProductService;

    @Autowired
    private IIPMSClientService sipmsClient;
    
    @Autowired
    private CPSProperties propertyStore;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private SellerService sellerService;
    
    private boolean isProdEnv;

    @PostConstruct
    public void init(){
        catalogProductService.setWebServiceBaseURL(propertyStore.getProductServiceUrl());
        sipmsClient.setWebServiceBaseURL(propertyStore.getIpmsServiceUrl());
        
        // Set environment flag
        String environment = System.getProperty("env");
        if(environment != null && environment.equals("prod")){
            isProdEnv = true;
        }else {
            isProdEnv = false;
        }
        
    }
    
    private String convertToCamelCase(String title) {
        StringBuffer sb = new StringBuffer();
        String[] tokens = title.split(" ");
        for(int i=0;i<tokens.length;i++) {
            sb.append(Character.toUpperCase(tokens[i].charAt(0)));
            sb.append(tokens[i].substring(1).toLowerCase());
            if(i<tokens.length-1){
                sb.append(' ');
            }
        }
      return sb.toString();           
    }
    
    private void setPOGDetails(ProductDTO product, POGDetailSRO pogSRO){
        
        String title = convertToCamelCase(pogSRO.getName().replaceAll("[^a-zA-Z0-9!\"%&'()*+,./:;<=>?@\\_ `{|}-]", "").trim().replaceAll(" +", " "));
        // Set Name
        if(title.length() <= 150){
            product.setTitle(title);
        }else {
            String shortName = title.substring(0,149);
            product.setTitle(shortName.substring(0, shortName.lastIndexOf(' ')));
        }
        
        // Set Page Url
        product.setUrl(pogSRO.getFullPageUrl());
        
        // Set Brand
        product.setBrand(pogSRO.getBrand().getName().trim());
        
        // Set Description
        List<String> productHighlights = pogSRO.getHighlights();
        StringBuilder phBuilder = new StringBuilder();
        for (String ph : productHighlights) {
            phBuilder.append(ph.trim());
            phBuilder.append(' ');
        }
        
        product.setDescription(phBuilder.toString());
        
        // Set category details
        ProductCategoryDetailSRO categorySRO = pogSRO.getCategories().get(0);
        
        product.setSubCategoryId(categorySRO.getId());
        product.setSubCategoryName(categorySRO.getName());
        if(categorySRO.getParentCategoryId() == null){
            product.setParentCategoryId(categorySRO.getId());
            product.setParentCategoryName(categorySRO.getName());
        }else{
            product.setParentCategoryId(categorySRO.getParentCategoryId());
            product.setParentCategoryName(categorySRO.getParentCategoryName());
        }
        
        
    }
    
    private List<SellerDTO> getSellers(List<VendorDetailInventoryPricingSRO> inventoryPricingSROList, Logger log){
        
        List<SellerDTO> sellerDTOs = new ArrayList<SellerDTO>();
        for(VendorDetailInventoryPricingSRO inventoryPricingSRO: inventoryPricingSROList){
            if(inventoryPricingSRO.getStatus() == InventoryPricingStatus.DISABLED || inventoryPricingSRO.getStatus() == InventoryPricingStatus.NONEXISTENT){
                continue;
            }
            
            // Calculate selling price
            int sellingPrice = 0;
            if(inventoryPricingSRO.getSellingPrice() == null){
                log.warn("Selling price obtained as null for vendor: " + inventoryPricingSRO.getVendorCode());
                continue;
            }else{
                sellingPrice = inventoryPricingSRO.getSellingPrice();
            }
            
            if(inventoryPricingSRO.isInternalCashbackApplicable()){
                sellingPrice = Math.round((float) sellingPrice - inventoryPricingSRO.getTotalInternalCashbackAbsoluteValue());
            }
            
            // Create vendor detail SRO and set values
            SellerDTO seller = new SellerDTO();
            seller.setCode(inventoryPricingSRO.getVendorCode());
            seller.setName(inventoryPricingSRO.getVendorName());
            seller.setDisplayName(inventoryPricingSRO.getVendorDisplayName());
            
            seller.setPrice(sellingPrice);
            seller.setMrp(inventoryPricingSRO.getPrice());
            
            if(inventoryPricingSRO.getStatus() == InventoryPricingStatus.REJECTED){
                seller.setAvailability(SellerAvailability.OUT_OF_STOCK);
            }else if(inventoryPricingSRO.getBuyableInventory() == 0 ){
                seller.setAvailability(SellerAvailability.OUT_OF_STOCK);
            }else if((new Integer(0)).equals(inventoryPricingSRO.getBuyableInventory())){
                seller.setAvailability(SellerAvailability.OUT_OF_STOCK);
            }else{
                seller.setAvailability(SellerAvailability.IN_STOCK);
            }
            
            sellerDTOs.add(seller);
        }
        
        return sellerDTOs;
    }
    
    public List<POGDetailSRO> getPOGDetailListBySupcList(List<String> supcList, Logger log){
        List<POGDetailSRO> groupSROList = null;
        GetPOGDetailListBySUPCListRequest catalogRequest = new GetPOGDetailListBySUPCListRequest();
        GetPOGDetailListResponse catalogResponse = new GetPOGDetailListResponse();
        
        try {
            // Calling the catalog API using product client
            catalogRequest.setSupcs(supcList);
            catalogResponse = catalogProductService.getPOGDetailListBySUPCList(catalogRequest);
            
            if(!catalogResponse.isSuccessful()){
                log.warn("Call to catalog not successful");
                return null;
            }
            
            groupSROList = catalogResponse.getPogDetailSROs();
            if(groupSROList == null || groupSROList.isEmpty()){
                log.warn("Empty or null response obtained for the request");
                return null;
            }
            
            log.info("Catalog details obtained for " + groupSROList.size() + " POGs");
            
            /*if(isProdEnv){
                Iterator<POGDetailSRO> it = groupSROList.iterator();
                while(it.hasNext()){
                    POGDetailSRO pogSRO = it.next();
                    if (!pogSRO.isLive()) {
                        log.warn(pogSRO.getId() + " not live. Ignoring");
                        it.remove();
                    }
                }
            }*/
            
        }catch (TransportException e) {
            log.error("Error while making request to the catalog server");
            log.error(ExceptionUtils.getFullStackTrace(e)); 
        } catch (Exception e) {
            log.error("Error while fetching catalog details");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        
        return groupSROList;
    }
    
    public POGDetailSRO getPOGDetailById(long pogId, Logger log){
        POGDetailSRO pogDetails = null;
        
        GetPOGDetailByIdRequest productRequest = new GetPOGDetailByIdRequest();
        GetPOGDetailResponse productResponse = new GetPOGDetailResponse();
        
        try{
            productRequest.setProductOfferGroupId(pogId);
            productResponse = catalogProductService.getPOGDetailById(productRequest);
            
            if(!productResponse.isSuccessful()){
                log.warn("Call to catalog not successful");
                return null;
            }
            
            pogDetails = productResponse.getPogDetailSRO();
            
            if(isProdEnv){
                if(!pogDetails.isLive()){
                    return null;
                }
            }
            
        }catch (TransportException e) {
            log.error("Error while making request to the catalog server");
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch(Exception e) {
            log.error("Error while fetching catalog details");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        
        return pogDetails;
    }
    
    public List<ProductDTO> getProductListByPOGDetailList(Collection<POGDetailSRO> pogList, Logger log){
        List<ProductDTO> products = new ArrayList<ProductDTO>();
        for(POGDetailSRO pogSRO : pogList){
            try{

                // Create a new product
                long pogId = pogSRO.getId();
                ProductDTO product = new ProductDTO(pogId);
                
                // Find the most relevant offer
                ProductOfferDetailSRO mostRelevantOffer = Collections.max(pogSRO.getOffers(), new Comparator<ProductOfferDetailSRO>() {
                    @Override
                    public int compare(ProductOfferDetailSRO o1, ProductOfferDetailSRO o2) {
                        return computePriorityValue(o1) - computePriorityValue(o2);
                    }

                    private int computePriorityValue(ProductOfferDetailSRO offerDetailSRO) {
                        return ProductUtils.getProductStatusPriority(offerDetailSRO.getProductState()) + ProductUtils.getProductStatusPriority(offerDetailSRO.getProductStatus());
                    }

                });
                
                ProductOfferState productState = mostRelevantOffer.getProductState();
                CatalogStatusEnum productStatus = mostRelevantOffer.getProductStatus();
                
                // Setting product availability as per catalog states
                if(productState.equals(ProductOfferState.BUY_NOW)){
                    if(!productStatus.equals(CatalogStatusEnum.ACTIVE)){
                        product.setAvailability(SellerAvailability.OUT_OF_STOCK.getStatus());
                    }
                }else if(productState.equals(ProductOfferState.PREBOOK)){
                    if(productStatus.equals(CatalogStatusEnum.ACTIVE)){
                        product.setAvailability(SellerAvailability.PREORDER.getStatus());
                    }else{
                        product.setAvailability(SellerAvailability.OUT_OF_STOCK.getStatus());
                    }
                }else if(productState.equals(ProductOfferState.COMING_SOON)){
                    continue;
                }else{
                    product.setAvailability(SellerAvailability.OUT_OF_STOCK.getStatus());
                }
                
                // Get supcs for live offers
                List<String> liveSupcs = new ArrayList<String>();
                Map<String, ProductOfferDetailSRO> supcMap = new HashMap<String, ProductOfferDetailSRO>();
                
                // iterate over Product offers to get supcs
                for(ProductOfferDetailSRO po: pogSRO.getOffers()){
                    if(ProductOfferState.livePOStates.contains(po.getProductState())){
                        liveSupcs.add(po.getSupcs().get(0));
                    }
                    supcMap.put(po.getSupcs().get(0), po);
                }
                
                // Set POG level catalog details to the product
                setPOGDetails(product, pogSRO);
                
                // Set image link
                String primaryImageUrl = mostRelevantOffer.getImages().get(0);
                
                if (primaryImageUrl.startsWith("imgs/a/")) {
                    primaryImageUrl = propertyStore.getOldImageServerUrl() + primaryImageUrl;
                } else {
                    primaryImageUrl = propertyStore.getNewImageServerUrl() + primaryImageUrl;
                }
                
                product.setImageLink(primaryImageUrl);
                
                
                // Pass supcs to ipms to get relevant inventory and pricing data
                Map<String, SUPCAllVendorInventorySRO> supcInventoryMap = null;
                SUPCAvailableVendorsSRO availableVendorSRO = null;
                
                try{
                    GetVendorInventoryPricingBySUPCRequest ipmsRequest = new GetVendorInventoryPricingBySUPCRequest(liveSupcs);
                    GetVendorInventoryPricingBySUPCResponse ipmsResponse = sipmsClient.getVendorsInventoryPricingBySUPC(ipmsRequest);
    
                    /*if(ipmsResponse == null || !ipmsResponse.isSuccessful()){
                        log.warn("IPMS response not successful for POG ID: " + pogId);
                        continue;
                    }*/
                    if(ipmsResponse == null){
                        log.warn("IPMS response is null for POG ID: " + pogId);
                        continue;
                    }
                    if(ipmsResponse.getCode().equals(IPMSServiceErrorCode.INVALID_INVENTORY.getErrorCode())
                            || ipmsResponse.getCode().equals(IPMSServiceErrorCode.INVALID_PARAMETERS.getErrorCode())){
                        log.warn("IPMS Error msg for POG ID: " + pogId +" is: "+ ipmsResponse.getMessage());
                        continue;
                    }
                    
                    supcInventoryMap = ipmsResponse.getSupcToInventoryMap();
                    availableVendorSRO = ipmsResponse.getAvailableVendorsSRO();
                    
                }catch(TransportException tex){
                    log.error("Error while making request to the ipms server");
                    log.error(ExceptionUtils.getFullStackTrace(tex));
                }
                
                if(availableVendorSRO == null || supcInventoryMap == null || supcInventoryMap.isEmpty()){
                    log.warn("Inventory or Pricing information cannot be obtained for POG ID: " + pogId);
                    product.setAvailability(SellerAvailability.OUT_OF_STOCK.getStatus());
                    product.setSellers(null);
                }else{
                    List<SellerDTO> sellers = getSellers(availableVendorSRO.getDetailsSROList(), log);
                    if(sellers == null || sellers.isEmpty()){
                        product.setSellers(null);
                        product.setAvailability(SellerAvailability.OUT_OF_STOCK.getStatus());
                    }else{
                        product.setSellers(sellers);
                        if(product.getAvailability() == null || product.getAvailability().isEmpty()){
                            String availability = product.getPrimarySeller().getAvailability().getStatus();
                            product.setAvailability(availability);
                        }
                    }
                }
                
                products.add(product);
                
            }catch(Exception ex){
                log.error("Exception while creating product objects");
                log.error(ExceptionUtils.getFullStackTrace(ex));
            }
        }
        
        return products;
    }

    
    public void saveToMongo(List<ProductDTO> products, Logger log) {
        
        for(ProductDTO product : products){
            
            // Check category, insert in mongo if new
            CategoryInfo categInfo = categoryService.getCategoryInfoBySubCategoryId(product.getSubCategoryId());
            if(categInfo == null){
                categInfo = new CategoryInfo();
                categInfo.setSubCategoryId(product.getSubCategoryId());
                categInfo.setSubCategoryName(product.getSubCategoryName());
                categInfo.setParentCategoryId(product.getParentCategoryId());
                categInfo.setParentCategoryName(product.getParentCategoryName());
                categoryService.saveCategoryInfo(categInfo);
            }
            
            // Check if primary seller exists, insert in mongo if new, else update seller display name in DB
            SellerInfo sellerInfo;
            SellerDTO primarySeller = product.getPrimarySeller();
            if(primarySeller != null){
                sellerInfo = sellerService.getSellerInfoByCode(primarySeller.getCode());
                if(sellerInfo == null){
                    sellerInfo = new SellerInfo(primarySeller.getCode(), primarySeller.getName(), primarySeller.getDisplayName());
                    sellerService.createSeller(primarySeller.getCode(), primarySeller.getName(), primarySeller.getDisplayName());
                }else if(!sellerInfo.getDisplayName().equalsIgnoreCase(primarySeller.getDisplayName())){
                    sellerService.updateDisplayName(primarySeller.getCode(), primarySeller.getDisplayName());
                }
            }
            
            
            // Check product, insert in mongo if new else update pricing and seller information
            ProductInfo productInfo = productService.getProductById(product.getPogId());
            if(productInfo == null){
                // New product
                productInfo = new ProductInfo(product.getPogId());
                productInfo.setTitle(product.getTitle().replaceAll("[^\\x00-\\x7F]", " ").replaceAll("( )+", " "));
                productInfo.setUrl(propertyStore.getSnapdealSiteUrl() + product.getUrl());
                productInfo.setDescription(product.getDescription().replaceAll("[^\\x00-\\x7F]", " ").replaceAll("( )+", " "));
                productInfo.setImageLink(product.getImageLink());
                productInfo.setBrand(product.getBrand().replaceAll("[^\\x00-\\x7F]", " ").replaceAll("( )+", " "));
                productInfo.setSubCategoryId(product.getSubCategoryId());
                productInfo.setAvailability(product.getAvailability());
                productInfo.setMrank(-1); // since new product
            }else{
                productInfo.setSubCategoryId(product.getSubCategoryId());
                productInfo.setAvailability(product.getAvailability());
            }
            
            if(primarySeller != null){
                productInfo.setSellerCode(primarySeller.getCode());
                productInfo.setPrice(primarySeller.getPrice());
                productInfo.setMrp(primarySeller.getMrp());
            }else{
                productInfo.setSellerCode("");
                productInfo.setPrice(0);
                productInfo.setMrp(0);
            }
            
            if(!isProdEnv){
                log.info(productInfo.toString());
            }
            
            // Save product
            productService.saveProduct(productInfo);
        }
    }
}
