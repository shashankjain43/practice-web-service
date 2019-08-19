package com.snapdeal.ims.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GetClientByMerchantResponse {
	private List<ClientDetails> clientList;
}
