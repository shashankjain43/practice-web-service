package com.snapdeal.payments.view.mapper;

import com.snapdeal.payments.view.entity.TransactionStateDetailsEntity;

public interface ITransactionStateDetailsMapper {

   public void saveTransactionStateDetails(TransactionStateDetailsEntity entity);

   public TransactionStateDetailsEntity verifyForTxnStatusValid(TransactionStateDetailsEntity entity);

   public void updateForAlreadyExistState(TransactionStateDetailsEntity entity);

}
