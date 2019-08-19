/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 20, 2010
 *  @author singla
 */
package com.snapdeal.ums.cache;

import java.util.HashMap;
import java.util.Map;

import com.snapdeal.base.annotations.Cache;
import com.snapdeal.base.velocity.Template;
import com.snapdeal.base.vo.SmsTemplateVO;
import com.snapdeal.ums.core.entity.SmsTemplate;

@Cache(name = "umsSmsTemplateCache")
public class SmsTemplateCache {

    private Map<String, SmsTemplateVO> nameToSmsTemplates = new HashMap<String, SmsTemplateVO>();

    public void addSmsTemplate(SmsTemplate template) {
        SmsTemplateVO templateVO = new SmsTemplateVO();
        templateVO.setName(template.getName());
        templateVO.setBodyTemplate(Template.compile(template.getBodyTemplate()));
        templateVO.setSmsChannelId(template.getSmsChannelId());
        templateVO.setDndScrubbingOn(template.isDndScrubbingOn());
        nameToSmsTemplates.put(template.getName(), templateVO);
    }

    public SmsTemplateVO getTemplateByName(String name) {
        return nameToSmsTemplates.get(name);
    }
}
