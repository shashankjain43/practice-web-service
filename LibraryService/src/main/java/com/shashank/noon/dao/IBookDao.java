package com.shashank.noon.dao;

import com.shashank.noon.entity.BookDO;

public interface IBookDao extends CrudRepository<BookDO, Integer> {
	
	public BookDO getBookByBookId(int bookId);

}
