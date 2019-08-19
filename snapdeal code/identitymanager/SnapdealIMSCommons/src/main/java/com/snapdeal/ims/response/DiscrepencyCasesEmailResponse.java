package com.snapdeal.ims.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class DiscrepencyCasesEmailResponse  extends AbstractResponse{

   /**
	 * 
	 */
	private static final long serialVersionUID = 4005702242281248437L;
private List<String> emailIds;
   
}