package com.snapdeal.merchant.dao;

import com.snapdeal.merchant.dao.entity.FilterEntity;

public interface IFilterDao {

	public int insertFilterInfo(FilterEntity entity);
	
	public FilterEntity getFilterInfo(FilterEntity entity);

}
