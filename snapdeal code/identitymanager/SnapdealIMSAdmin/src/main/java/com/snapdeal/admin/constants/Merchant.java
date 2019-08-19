package com.snapdeal.admin.constants;

public enum Merchant {
	SNAPDEAL("SNAPDEAL"), FREECHARGE("FREECHARGE"), ONECHECK("ONECHECK");

	private String merchant;

	Merchant(String merchant) {
		this.merchant = merchant;
	}

	public String getValue() {
		return merchant;
	}

}
