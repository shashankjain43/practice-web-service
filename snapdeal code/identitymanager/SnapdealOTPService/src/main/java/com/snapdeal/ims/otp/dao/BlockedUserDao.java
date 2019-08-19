package com.snapdeal.ims.otp.dao;

import com.google.common.base.Optional;
import com.snapdeal.ims.otp.entity.FreezeAccountEntity;
import com.snapdeal.ims.otp.internal.request.DropUserFromFreezeRequest;
import com.snapdeal.ims.otp.internal.request.GetFrozenAccount;

/**
 * 
 * @author shagun
 * @usage To store and retrieve blocked user accounts
 *
 */
public interface BlockedUserDao {

	public void freezeUser(FreezeAccountEntity freezeDao);

	public void updateFreezeUser(FreezeAccountEntity freezeAccount);

	public Optional<FreezeAccountEntity> getFreezedAccount(
			GetFrozenAccount getFrozenAccount);

	public boolean dropFreezedUser(
			DropUserFromFreezeRequest dropUserFromFreezeRequest);

}
