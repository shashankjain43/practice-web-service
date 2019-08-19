package com.snapdeal.ims.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BlacklistEmailResponse extends AbstractResponse {

	private List<String> blacklistEmails;
}
