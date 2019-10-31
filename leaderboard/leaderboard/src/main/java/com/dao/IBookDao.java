package com.dao;

import com.entity.BookDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookDao extends CrudRepository<BookDO, Integer> {
	
	public BookDO getBookByBookId(int bookId);

}
