package com.snapdeal.admin.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GetClientByNameResponse {
	private List<ClientDetails> clientList;
}