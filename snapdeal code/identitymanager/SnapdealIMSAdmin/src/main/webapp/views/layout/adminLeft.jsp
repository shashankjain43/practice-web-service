<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="report-left">
	<%-- 	cache ${cache} ${cache.getCacheByName('ConfigCache')}
	<c:set var="object" value="${cache.getCacheByName('ConfigCache')}"></c:set>
	cacheq ${object.get('admin-ui')}
 --%>

	<ul>
		<li><a href="${context}/admin">Home</a></li>
		<sec:authorize ifAnyGranted="registered">
			<sec:authentication property="principal" var="user" />
			<li><a href="${context}/admin/manage/configuration">Configuration Manager</a></li>
			<li><a href="${context}/admin/manage/client">Client Manager</a></li>
			<li><a href="${context}/admin/manage/ims_apis">IMS API Manager</a></li>
		</sec:authorize>
	</ul>
</div>