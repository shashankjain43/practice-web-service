<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="${path.css('snapdeal/style.css')}" rel="stylesheet" type="text/css" />

<div class="header">
	<sec:authorize ifAnyGranted="registered, unverified">
		<sec:authentication property="principal" var="user" />
		<div class="myaccount-drop-outer">
			<c:set var="name"
				value="${user.name == null || user.name == '' ? user.username : user.name }"></c:set>
			<c:set var="firstName"
				value="${user.user.firstName == null || user.user.firstName == '' ? user.username : user.user.firstName } ${user.user.firstName == null || user.user.firstName == '' ? '' : '!' }"></c:set>
		</div>
		<div class="sd-droplist-outer myaccount-list-outer">
			<ul class="sd-droplist myaccount-list">
				<sec:authorize ifAnyGranted="registered">
				</sec:authorize>
				<li><a href="${path.http }/logout">Sign Out</a>
				</li>
			</ul>
		</div>
	</sec:authorize>
	<sec:authorize ifAnyGranted="registered,unverified">
		<input type="hidden" id="uidField" name="trackingUid"
			value="${user.trackingUID}" />
		<input type="hidden" id="sharerEmail" name="sharerEmail"
			value="${user.trackingEmail}" />
	</sec:authorize>
	<sec:authorize ifNotGranted="registered,unverified">
		<div class="nav-sign-outer">
		</div>
		<input type="hidden" id="uidField" name="trackingUid" value="0" />
		<input type="hidden" id="sharerEmail" name="sharerEmail" value="0" />
	</sec:authorize>

</div>

<div class="clear"></div>
