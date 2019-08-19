package com.snapdeal.ims.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class TransferTokenDTO implements Serializable {

   private static final long serialVersionUID = -7931093468261446597L;
   private String transferToken;
   private Date transferTokenExpiry;
}