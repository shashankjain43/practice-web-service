<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/tld_includes.jsp"%>
<%@ page session="true"%>

<tiles:insertTemplate template="/views/layout/base.jsp">

	<tiles:putAttribute name="title" value="IMS Admin Login" />

	<tiles:putAttribute name="body">
		<c:set var="loginPath" value="${context}/login_security_check?" />
		<c:if test="${not empty signupForm.targetUrl}">
			<c:set var="loginPath"
				value="${context}/login_security_check?spring-security-redirect=${signupForm.targetUrl}&" />
		</c:if>
		<%-- <c:set var="encodedLoginPath" value="${path.getEncodedUrl(loginPath)}" /> --%>
		<div class="signupBoxDiv">
			<div class="register_head">IMS Admin Login</div>
			<form id="loginform" method="post" action="${loginPath}">
				<div class="loginformCont">
					<div class="cus_info_wrap">
						<label class="labelTop" for="email">E-mail *</label> <input
							class="input" type="text" name="j_username" id="j_username">
					</div>
					<div class="cus_info_wrap">
						<label class="labelTop" for="password">Password *</label> <input
							class="input" type="password" name="j_password">
					</div>
					<div class="rememberme">
						<input class="checkBox" id="rememberMe"
							name="_spring_security_remember_me" type="checkbox"> <span>Remember
							me on this computer</span>
					</div>
					<div align="center">
						<input value="Sign in" class="button" type="submit" />
					</div>
				</div>

			</form>
		</div>
	</tiles:putAttribute>
</tiles:insertTemplate>