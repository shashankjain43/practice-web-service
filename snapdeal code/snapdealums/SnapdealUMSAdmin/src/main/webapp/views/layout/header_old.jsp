<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
	

<c:set var="visibleCityCount" value="10"/>



<div id="header">
 <div class="header_d_left">
  <div class="header_d_right">
  <div class="wrapper"> 
  <c:set var="zones" value="${cache.getCacheByName('zonesCache').zones }"></c:set>
  <c:set var="cities" value="${cache.getCacheByName('citiesCache').cities }"></c:set>
  		<c:choose>
	 		<c:when test="${getaways}">
	 			
    			<span class="logo-get">
    				<a href="${path.http}" title="India's largest daily deals site"><span></span></a>
    			</span>
    		</c:when>
  			 <c:otherwise> 
  			 	<c:choose>
					  <c:when test="${dateUtils.getCurrentDate().getDate() <= 26 && dateUtils.getCurrentDate().getMonth() == 9 }">
						  <span class="logo-diwali">
						  		<a href="${path.http}" title="India's largest daily deals site"><span></span></a>
						  </span>
					 </c:when>	  
					  <c:otherwise>
						   <span class="logo">
					  			<a href="${path.http}" title="India's largest daily deals site"><span></span></a>
					  	   </span>
				  	 </c:otherwise>
				 </c:choose>
   			 </c:otherwise>
    	</c:choose>
    
   
    <div class="header_center">

      <div class="header_content rounded-corners">
        <div class="header_deal">
    
      </div>
    </div>
  </div>

</div>
</div>
</div>