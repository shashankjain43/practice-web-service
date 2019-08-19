<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="report-left">

<!-- Here ALL ADMIN PAGE LEFT DATA IS BEING PICKED UP BY CACHE .   -->

<ul>
	<li><a href="${path.http}/admin">Home</a></li>
	<sec:authorize ifAnyGranted="registered">
		<sec:authentication property="principal" var="user" />
		<c:forEach items="${cache.getCacheByName('accessControlCache').accessControls}" var="accCtrl" varStatus="ctr">
			<c:if test="true">
				<c:if test="${accCtrl.feature!=null}" >
				<li><a href="${path.http}${accCtrl.link}">${accCtrl.feature}</a></li>
				</c:if>
			</c:if>
		</c:forEach>
	</sec:authorize>
</ul>
</div>