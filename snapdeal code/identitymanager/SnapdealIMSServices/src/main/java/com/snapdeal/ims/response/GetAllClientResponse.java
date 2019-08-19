package com.snapdeal.ims.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GetAllClientResponse {
   private List<ClientDetails> clientList;
}
