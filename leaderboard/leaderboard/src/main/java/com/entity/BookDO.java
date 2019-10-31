package com.entity;

import javax.persistence.*;

@Entity
@Table(name = "Book")
public class BookDO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int bookId;
	
	String name;
	
	String status;
	
	String author;
}
