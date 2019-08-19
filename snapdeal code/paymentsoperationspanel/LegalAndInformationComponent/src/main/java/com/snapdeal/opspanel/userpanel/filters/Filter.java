package com.snapdeal.opspanel.userpanel.filters;

import java.util.List;

public interface Filter<T, U> {

   public List<T> filterData( List<T> objectsList, U criterion );

}
