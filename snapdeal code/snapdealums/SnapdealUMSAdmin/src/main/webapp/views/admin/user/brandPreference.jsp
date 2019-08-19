<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<link href="${path.css('style.css')}" rel="stylesheet" type="text/css" />
<link href="${path.css('admin.css')}" rel="stylesheet" type="text/css" />
<link href="${path.css('adminv1.css')}" rel="stylesheet" type="text/css" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<h2>
	<title>Get User Brand Details By Email Or Id</title>
</h2>
</head>
<body>
	<%@ include file="/views/common/home_LogOut_header.jsp"%>

	<div>
		<tiles:insertAttribute name="header"
			defaultValue="/views/layout/header_old.jsp" />
	</div>
	<div>
		<tiles:insertAttribute name="navigation"
			defaultValue="/views/layout/navigation_old.jsp" />
	</div>
	<div id="internal-content">
		<div class="content-bdr">
			<div class="graybox">
				<div class="gray-cont">
					<div class="wrapper2">
						<div class="head-lable">
							<font color="blue">User Brand Details</font> <font color="blue">By
								Email</font>

						</div>

						<br> <br>
						<form:form name="frm" method="post"
							action="getUserBrandByEmailOrId" enctype="multipart/form-data">
							<table>
								<tr>


									<td><font color="black"> Enter User Email or User
											Id </font> <input type="text" name="value"> <input
										type="submit"
										onClick="this.form.submit(); this.disabled=true; this.value='Processing...'; "
										class="button" value="Fetch" /></td>

								</tr>
							</table>

						</form:form>
					</div>
					<br> <br> <br>

					<c:if
						test="${userBrandResponse!=null && userBrandResponse.successful==false && userBrandResponse.validationErrors!=null}">
						<c:forEach items="${userBrandResponse.validationErrors}"
							var="errorCodeAndMessage">
							<h2>
								<font color="red">${errorCodeAndMessage.code}: </font> <font
									color="black">${errorCodeAndMessage.message}</font>

							</h2>
						</c:forEach>


						<font color="black">Processing failed. . User does not
							exist .</font>
						</h2>
					</c:if>


					<c:if test="${listOfBrands!=null}">
						<br>
						<c:forEach var="item" items="${listOfBrands}">

							<tr>
								<td><p align="left">${item}</p></td>

							</tr>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
