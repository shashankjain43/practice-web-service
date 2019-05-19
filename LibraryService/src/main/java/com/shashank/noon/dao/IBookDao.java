package com.shashank.noon.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shashank.noon.entity.BookDO;

@Repository
public interface IBookDao extends CrudRepository<BookDO, Integer> {
	
	public BookDO getBookByBookId(int bookId);

}
