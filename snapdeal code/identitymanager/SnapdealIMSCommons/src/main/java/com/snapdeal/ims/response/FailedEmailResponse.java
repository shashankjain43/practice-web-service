package com.snapdeal.ims.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FailedEmailResponse extends AbstractResponse{

	List<String> failedEmailList;
}
