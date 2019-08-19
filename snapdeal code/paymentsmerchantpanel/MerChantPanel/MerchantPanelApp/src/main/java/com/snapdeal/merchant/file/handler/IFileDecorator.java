package com.snapdeal.merchant.file.handler;

import com.snapdeal.merchant.file.handler.exception.FileHandlingException;

public interface IFileDecorator<S,T> {
	
	public T decorate(S request) throws FileHandlingException; 
	
	public void save(T request,String fileName) throws FileHandlingException;
	
	public void delete(String filePath);

}
