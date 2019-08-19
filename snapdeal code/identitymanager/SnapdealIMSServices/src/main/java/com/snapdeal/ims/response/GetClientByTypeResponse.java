package com.snapdeal.ims.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GetClientByTypeResponse {
	private List<ClientDetails> clientList;  
}
