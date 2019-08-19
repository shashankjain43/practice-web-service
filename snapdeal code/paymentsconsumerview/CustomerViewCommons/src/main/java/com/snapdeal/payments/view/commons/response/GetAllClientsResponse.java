package com.snapdeal.payments.view.commons.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.payments.view.commons.request.Client;

@Getter
@Setter
public class GetAllClientsResponse extends AbstractResponse {

   private static final long serialVersionUID = -6566521663200257111L;
   
   public List<Client> clients;
   
}
