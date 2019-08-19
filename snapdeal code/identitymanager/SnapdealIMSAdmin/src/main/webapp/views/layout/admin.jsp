<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:set var="context" value="${pageContext.request.contextPath}" />


<title><tiles:getAsString name="title" /></title>
<link href="${context}/static/css/style.css" rel="stylesheet" type="text/css" />
<link href="${context}/static/css/admin.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${context}/img/icons/favicon.ico"
	type="image/x-icon" />
<!-- <script>
	if (typeof Snapdeal == 'undefined') {
		Snapdeal = {};
	};
	Snapdeal.getStaticPath = function(path) {
	return '${path.http}' + path; 
	}
</script>
 -->
<tiles:insertAttribute name="head" defaultValue="" />

</head>
<body style="width: 100%; height: 100%">
	<div>
		<p align="right">
			<a href="${context}/logout">Log Out</a>
		</p>
		<div>
			<tiles:insertAttribute name="header"
				defaultValue="/views/layout/header_old.jsp" />
		</div>
		<div>
			<tiles:insertAttribute name="navigation"
				defaultValue="/views/layout/navigation_old.jsp" />
		</div>
		<div id="system_message">
			<div class="system_fail"></div>
		</div>


		<div id="internal-content">
			<div class="content-bdr">
				<div class="head-lable">Admin Dashboard</div>
				<div>
					<tiles:insertAttribute name="adminLeft"
						defaultValue="/views/layout/adminLeft.jsp" />
				</div>
				<div>
					<tiles:insertAttribute name="body" />
				</div>
			</div>
		</div>
		<div>
			<tiles:insertAttribute name="footer"
				defaultValue="/views/layout/footer_old.jsp" />
		</div>
	</div>

</body>
</html>
