package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
