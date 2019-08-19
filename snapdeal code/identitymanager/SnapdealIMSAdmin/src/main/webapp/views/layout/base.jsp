<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/tld_includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="context" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" type="text/css"
	href="${context}/static/css/jquery-ui-1.10.4.min.css" />

<%-- <tiles:insertAttribute name="headImmediate" defaultValue="" />
 --%>
<title><tiles:getAsString name="title" /></title>

<link href="${context}/static/css/snapdeal.css" rel="stylesheet" type="text/css" />

<link href="${context}/static/css/innerPages.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${context}/static/img/icons/favicon.ico"
	type="image/x-icon" />
<link rel=icon type=image/ico href="${context}/static/img/icons/favicon.ico" />

<tiles:insertAttribute name="head" defaultValue="" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--  for IE9 -->
<meta http-equiv="X-UA-Compatible" content="IE=9" />

<!-- source tracking params -->
<c:set var="visitTrackingParamsMap"
	value="${sessionScope.visitTrackingParams}" />
<c:forEach var="tarckingPrams" items="${visitTrackingParamsMap}">
	<input type="hidden" name="${tarckingPrams.key}"
		id="${tarckingPrams.key}" value="${tarckingPrams.value}" />
</c:forEach>
<!-- End -->

<body style="width: 100%; height: 100%; min-width: 962px;">
	<div id="redBox"></div>
	<div id="yellowStrip"></div>
	<div id="content_wrapper">
		<div class="page-main-cont">
			<tiles:insertAttribute name="body" />
		</div>
	</div>
	<div id="newSubscriber"></div>
	<script type="text/javascript"
		src="${context}/static/js/jquery/jquery.all.min.js"></script>
	<!--     <script type="text/javascript" src="/static/js/snapdeal/snapdealHome.js"></script>
 -->
	<script type="text/javascript" src="${context}/static/js/snapdeal/tracking.js"></script>
	<script type="text/javascript" src="${context}/static/js/omniture/sd_scode.js"></script>
	<c:if test="${param['loginSuccess']=='success'}">
		<c:set var="login" value="SnapdealLogin"></c:set>
		<sd:omnitureScript pageName="rpxLogin" />
	</c:if>

</body>
</html>
