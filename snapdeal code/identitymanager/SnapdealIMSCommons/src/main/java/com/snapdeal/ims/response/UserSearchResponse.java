package com.snapdeal.ims.response;

import java.util.List;

import com.snapdeal.ims.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSearchResponse extends AbstractResponse{

	List<UserEntity> users;
}
