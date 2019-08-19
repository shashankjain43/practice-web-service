<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/adminBase.jsp">

	<tiles:putAttribute name="title" value="User Admin" />

	<tiles:putAttribute name="head">
	</tiles:putAttribute>
<%@ include file="/views/common/home_LogOut_header.jsp" %>  
	<tiles:putAttribute name="deferredScript">
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		

		<div id="internal-content">

			<div class="content-bdr">
				<div class="graybox">
					<div class="gray-cont">
						<div class="wrapper2">

							<form:form commandName="userAdminForm" method="GET" action='${path.http}/admin/userAdmin/fetchUserRole' name="userAdminForm">

								<div class="head-lable">User Details</div>

								<div style="width: 40%"></div>

								<div class="form-head">
									User Email *
									<form:errors path="user.email" class="errorMessage" />
								</div>
								<div class="form-row">
									<form:input path="user.email" class="form-fld" name="userRoleEmail" />
								</div>
								<div class="form-row">
									<input type="submit" name="fetchUserRole" id="fetchUserRole" value="Fetch" class="button" />
								</div>
							</form:form>
							<div class="errorMessage">${message}</div>
							<div class="form-head">User Roles *</div>
							<c:forEach items="${userAdminForm.userRoles}" var="userRole" varStatus="ctr">
								<span style='font-size: 15px; font-weight: bold; color: black;'>Role ${ctr.index+1}: </span>
								<span style='font-size: 20px; font-weight: bold; color: red;'>${userRole.role.code}</span>
								<a href="${path.http}/admin/userAdmin/deleteUserRole?id=${userRole.id}&email=<c:out value="${userAdminForm.user.email}" />">(Remove This Role)</a>
								<br />
								
						
								<br />
								<br />
							</c:forEach>
						</div>
						<div class="form-head">Add New Role *</div>

						<form action="${path.http}/admin/userAdmin/addData/addRole" method="get">

							<input type="hidden" name="userRoleEmail" value="<c:out value="${param['user.email']}" />" path="user.email" class="input" />

							<div>
								<select id="drpdwnUserRole" name="drpdwnUserRole" class="form-fld">
							    <c:forEach items="${cache.getCacheByName('roleCache').getRoles()}" var="roleItem">
     							   <option value="${roleItem.code}">${roleItem.description}</option>
							    </c:forEach>
								</select>
							</div>
							<br />
							<div class="errorMessage" style="color:#F00">${param['message']}</div>
							<div class="form-row">
								<input type="submit" name="addRoleZoneMap" id="addRoleZoneMap" value="Add Role" class="button" />
							</div>
						</form>
					</div>
				</div>
			</div>

		</div>
	</tiles:putAttribute>
</tiles:insertTemplate>