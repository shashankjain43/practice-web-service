
<%@ include file="/tld_includes.jsp"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="${path.css('style.css')}" rel="stylesheet" type="text/css" />



<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>
	<%@ include file="/views/common/home_LogOut_header.jsp"%>
	
	<form:form action="processEmailTypeMapping" method="post"
						enctype="multipart/form-data">
						<br>
						<table>
							<tr>


								<td><p align="left">
										<font color="blue" size="2"> EmailType:</font>
									</p></td>
								<td><p align="left">
										<input type="text" name="emailType" id="txtBox" />
									</p></td>
									
									<td><input type="submit"
								onClick="this.form.submit(); this.disabled=true; this.value='Processing...';"
								value="Process" class="button" /> <br></td>
	</tr></table></form:form>
	
	
	<c:if
					test="${EmailTemplateNameList!=null}">
					<h2>
						
											<c:forEach items="${EmailTemplateNameList}" var="item">

                                   <font size=3 color="grey"> ${item}<br></font>
											

											</c:forEach>
					</h2>


				</c:if>
				</body>
</html>
	