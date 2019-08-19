package com.snapdeal.ims.token.service;

/**
 * Common api to generate id for different ussage.
 */
public interface IIDGenerator {

   /**
    * Global token id generation logic.
    */
   String generateGlobalTokenId();

   /**
    * User ID generation logic.
    */
   String generateUserId();
}