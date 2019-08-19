/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 13-Dec-2012
*  @author naveen
*/
package com.snapdeal.ums.dao.common;

import java.util.List;

import com.snapdeal.ums.core.entity.EmailTemplate;
import com.snapdeal.ums.core.entity.Locality;
import com.snapdeal.ums.core.entity.SmsTemplate;
import com.snapdeal.ums.core.entity.UMSProperty;
import com.snapdeal.ums.core.entity.Role;

public interface IStartupDao {

    List<UMSProperty> getUMSProperties();

    List<EmailTemplate> getEmailTemplates();

    List<SmsTemplate> getSmsTemplates();

    List<Locality> getAllLocalities();

    List<Role> getAllRoles();

}
