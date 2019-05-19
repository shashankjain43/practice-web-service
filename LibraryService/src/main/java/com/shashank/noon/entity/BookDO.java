package com.shashank.noon.entity;


@Entity
@Table("Book")
public class BookDO {
	
	@Column("Id")
	@Id
	int bookId;
	
	@Column
	String name;
	
	@Column
	String status;
	
	@Column
	String author;
}
