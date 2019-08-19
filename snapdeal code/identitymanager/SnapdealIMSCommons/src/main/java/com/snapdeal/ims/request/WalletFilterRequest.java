package com.snapdeal.ims.request;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import com.snapdeal.ims.request.AbstractRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString 
public class WalletFilterRequest extends AbstractRequest{

	
	private static final long serialVersionUID = 5383288374832366594L;
	private String merchant;
	private ArrayList<String> upgradeChannel;
	private ArrayList<String> upgradeSource;

}
