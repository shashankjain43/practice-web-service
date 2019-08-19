package com.snapdeal.merchant.dto;

import java.util.List;

import lombok.Data;

@Data
public class MerchantRoleDTO {

   private Integer id;

   private String name;

   private List<MerchantPermissionDTO> permissions;
}
