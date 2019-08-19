package com.snapdeal.ims.cache;

import com.snapdeal.ims.client.dbmapper.entity.Client;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Cache(name = "ClientCache")
public class ClientCache extends AbstractCache<String, Client>{

   @Setter
   @Getter
   private Set<String> alliasNames;
}
