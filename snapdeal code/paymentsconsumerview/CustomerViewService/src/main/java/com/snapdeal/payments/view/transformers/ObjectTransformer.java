package com.snapdeal.payments.view.transformers;

/**
 * Transform one type of object to other type accoding to need
 * 
 * @author shubham.bansal
 * 
 *
 */
public interface ObjectTransformer<T1, T2> {

	public T2 transforme(T1 obj);

}
