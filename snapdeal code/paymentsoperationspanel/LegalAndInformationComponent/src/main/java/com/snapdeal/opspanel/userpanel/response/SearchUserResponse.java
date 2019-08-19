package com.snapdeal.opspanel.userpanel.response;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.snapdeal.payments.sdmoney.service.model.BankAccountDetails;
import com.snapdeal.payments.sdmoney.service.model.VoucherBalanceDetails;

import lombok.Data;

@Data
public class SearchUserResponse {

	private String name;
	private String email;
	private String mobileNumber;
	private String userId;
	private Timestamp accountCreationDate;
	private String blacklistingStatus; // TODO ask for enums
	private String walletAccountStatus; // TODO ask for enums
	private String imsAccountStatus; // TODO ask for enums
	private String migrationStatus; // TODO ask for enums
	private String accountOwner;
	private Boolean isUserEnabled;
	private BigDecimal generalAccountBalance;
	private BigDecimal generalVoucherBalance;
	private String transactionHistoryDownlaodUrl;
	private long voucherCount;
	private String userCreatedFrom;
	private List<VoucherBalanceDetails> vouchersList;
	private List<BankAccountDetails> userAccounts;
	private boolean mobileVerified;
	private boolean emailVerified;
	private String fbSocialId;
	private String googleSocialId;
	private String remainingGeneralBalanceLimit;

}
