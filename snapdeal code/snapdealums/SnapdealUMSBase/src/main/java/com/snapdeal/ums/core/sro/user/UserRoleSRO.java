/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 13-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.dyuproject.protostuff.Tag;

public class UserRoleSRO implements Serializable {

    /**
	 * 
	 */
    private static final long       serialVersionUID = 9064337906542304746L;
    @Tag(1)
    private Integer                 id;
    @Tag(2)
    private Integer                 userId;
    @Tag(3)
    private String                  role;
    @Tag(4)
    private String                  email;
    


    public enum Role {
        UNVERIFIED("unverified"), REGISTERED("registered"), ADMIN("admin"), BUSINESS_DEVELOPMENT("bd"), CONTENT_MANAGER("content"), SUPER("super"), VENDOR("vendor"), AFFILIATE(
                "affiliate"), CCMANAGER("ccmanager"), MARKETING("marketing"), GETAWAYS_BD("getaways-bd"), PRODUCT("product"), CAMPAIGN_MGR("campaignmgr"), CC_EXECUTIVE(
                "ccexecutive"), FINANCE("finance"), SHIPPROVIDER("shipprovider"), MIS("mis"), AUDIT("audit"), SEO("seo"), CONTENT_MGR("ContentMgr"), AM("am"), AM_CHEIF("amchief",
                AM), DESIGN("design"), DESIGN_CHEIF("designchief", DESIGN), CONTENT("content"), CONTENT_CHEIF("contentchief", CONTENT), QA("qa"), QA_CHEIF("qachief", QA), HR("hr"), CS_ADMIN(
                "csadmin"), LOGISTICS("logistics"), CANCELLATION_APPROVER_LOGISTICS("canAppLogistics", LOGISTICS), PRODUCT_SOURCING("productsourcing"), CATEGORY_ADMIN(
                "categoryAdmin"), STOREFRONTSUPERADMIN("storefrontSuperAdmin"), ORDERFULFILLMENT("orderfulfillment"), CCEXECUTIVE_OUTGOING("ccexecutiveoutgoing"),
                PCEXECUTIVE("pcexecutive"),PCMANAGER("pcmanager"), CODVERIFICATION("codverification"), MARKETINGSFADMIN("marketingsfadmin"), CATEGORY("category"),ZENDESKUSER("zendeskuser"),CZENTRIXUSER("czentrixuser"),
                OMS_CFR_ADMIN("OMSCFRAdmin"),OMS_VRA_ADMIN("OMSVRAAdmin"),OMS_CASHBACK_ADMIN("OMSCashbackAdmin"),OMS_BULKPARTIALCANCELLATION_ADMIN("OMSBulkPartialCancellationAdmin"),
                OMS_CODVERIFICATION_ADMIN("OMSCODVerificationAdmin"),OMS_RESTRICTEDCANCELLATION_ADMIN("OMSRestrictedCancellationAdmin"),OMS_CC_ADMIN("OMSCCAdmin"),
                OMS_ALTERNATE_ADMIN("OMSAlternateAdmin"),OMS_TASK_ADMIN("OMSTaskAdmin"),OMS_RESTRICTEDPAYMENTMODE_ADMIN("OMSRestrictedPaymentModeAdmin"),
                OMS_NEFT_ADMIN("OMSNeftAdmin"),OMS_TEMPLATE_ADMIN("OMSTemplateAdmin"),OMS_ADMIN("OMSAdmin"),OMS_RETRIGGER_ADMIN("OMSRetriggerAdmin");
        
        private String                   uRole;
        private Role                     uSubRole = null;
        private static Map<String, Role> cache    = new HashMap<String, Role>();

        static {
            for (Role role : Role.values()) {
                cache.put(role.role(), role);
            }
        }
        private Role(String role, Role subRole) {
            uRole = role;
            uSubRole = subRole;
        }
        private Role(String role) {
            this(role, null);
        }

        public String role() {
            return uRole;
        }

        public String getSubordinateRole() {
            if (uSubRole != null)
                return uSubRole.role();
            else
                return "";
        }

        public static Role getRoleFromRoleString(String roleName) {
            return cache.get(roleName);
        }
    }
    public UserRoleSRO() {
       
    }
    
    public UserRoleSRO(UserSRO user, String role) {
        this.userId = user.getId();
        this.role = role.toLowerCase();
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer id) {
        this.userId = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserRoleSRO other = (UserRoleSRO) obj;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserRoleSRO [role=" + role + "]";
    }
    
    

}
