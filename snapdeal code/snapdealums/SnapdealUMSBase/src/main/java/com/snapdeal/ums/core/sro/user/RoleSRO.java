/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 15, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.core.sro.user;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;

public class RoleSRO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -7985511427098493138L;

    public enum RoleDef {

        UNVERIFIED("unverified"), REGISTERED("registered"), ADMIN("admin"), BUSINESS_DEVELOPMENT("bd"), CONTENT_MANAGER("content"), SUPER("super"), VENDOR("vendor"), AFFILIATE(
                "affiliate"), CCMANAGER("ccmanager"), MARKETING("marketing"), GETAWAYS_BD("getaways-bd"), PRODUCT("product"), CAMPAIGN_MGR("campaignmgr"), CC_EXECUTIVE(
                "ccexecutive"), FINANCE("finance"), SHIPPROVIDER("shipprovider"), MIS("mis"), AUDIT("audit"), SEO("seo"), CONTENT_MGR("ContentMgr"), AM("am"), AM_CHEIF("amchief",
                AM), DESIGN("design"), DESIGN_CHEIF("designchief", DESIGN), CONTENT("content"), CONTENT_CHEIF("contentchief", CONTENT), QA("qa"), QA_CHEIF("qachief", QA), HR("hr"), CS_ADMIN(
                "csadmin"), LOGISTICS("logistics"), CANCELLATION_APPROVER_LOGISTICS("canAppLogistics", LOGISTICS), PRODUCT_SOURCING("productsourcing"), CATEGORY_ADMIN(
                "categoryAdmin"), STOREFRONTSUPERADMIN("storefrontSuperAdmin"), ORDERFULFILLMENT("orderfulfillment"), CCEXECUTIVE_OUTGOING("ccexecutiveoutgoing"), PCEXECUTIVE(
                "pcexecutive"), PCMANAGER("pcmanager"), CODVERIFICATION("codverification"), MARKETINGSFADMIN("marketingsfadmin"), CATEGORY("category");

        private String  code;
        private RoleDef subRoleDef = null;

        private RoleDef(String code) {
            this.code = code;
        }

        private RoleDef(String code, RoleDef subRole) {
            this.code = code;
            subRoleDef = subRole;
        }

        public String code() {
            return this.code;
        }

        public RoleDef getSubordinateRole() {
            if (subRoleDef != null)
                return subRoleDef;
            else
                return null;
        }

    }

    /**
    *
    */

    @Tag(1)
    private int               id;
    @Tag(2)
    private String            code;
    @Tag(3)
    private String            description;

    public RoleSRO() {
    }

    public RoleSRO(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
