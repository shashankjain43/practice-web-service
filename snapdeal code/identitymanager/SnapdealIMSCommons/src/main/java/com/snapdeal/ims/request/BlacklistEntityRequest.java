package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.EntityType;
import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,BlacklistEntityRequest.class})
public class BlacklistEntityRequest extends AbstractRequest{
    
   private static final long serialVersionUID = 6915543703202727987L;
   
   @NotBlank(groups=First.class,message=IMSRequestExceptionConstants.BLACKLIST_ENTITY_IS_BLANK)
   private String entity;
   
   @NotNull(message=IMSRequestExceptionConstants.BLACKLIST_ENTITY_TYPE_IS_BLANK)
   private EntityType blackListType;

}
