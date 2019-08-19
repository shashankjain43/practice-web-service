package com.snapdeal.ims.response;

import java.util.List;

import com.snapdeal.ims.entity.UserHistory;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class GetUserHistoryDetailsResponse  extends AbstractResponse
{
	private static final long serialVersionUID = -1998509830223248959L;
	private List<UserHistory> userHistoryDetails;
}

