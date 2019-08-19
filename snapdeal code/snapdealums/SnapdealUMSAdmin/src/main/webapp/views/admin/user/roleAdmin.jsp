<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/adminBase.jsp">

	<tiles:putAttribute name="title" value="User Admin" />

	<tiles:putAttribute name="head">
	</tiles:putAttribute>
<%@ include file="/views/common/home_LogOut_header.jsp"%>
	<tiles:putAttribute name="deferredScript">
	</tiles:putAttribute>

	<tiles:putAttribute name="body">

		<div id="internal-content">

			<div class="content-bdr">
				<div class="graybox">
					<div class="gray-cont">


						<div class="form-head">Current Roles</div>

						<table cellpadding="0" cellspacing="0" border="0" width="100%"
							style="table-layout: fixed">
							<tr>
								<th class="hd" width="15%">Role Code</th>
								<th class="hd" width="15%">Description</th>
							</tr>
							<c:forEach items="${cache.getCacheByName('roleCache').getRoles()}"
								var="roleItem">
								<tr>
									<td>${roleItem.code}</td>
									<td>${roleItem.description}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
			<div class="addnew">
			<b>Add new Role here :</b>
			<br/>
			<form class="below" id="addNewRoleForm" name="addNewRoleForm"
				action="${path.http}/admin/roleAdmin/addRole" method="post">
				<div id="errorText" style="display: none;" class="error">"Enter all reqd. values"</div>
				Enter code:(lowercase string)* : <input id="roleCode" type="text" name="code">
				Enter description:* <input id="roleDesc" type="text" name="description"> 
				<input id="roleSubmit" onclick=validate(); type="button" value="Submit">
			</form>
		</div>
			
		</div>
		<script type="text/javascript"
			src="${path.js('jquery/jquery.all.min.js')}"></script>
		<script type="text/javascript"
			src="${path.js('jquery/new.jqGrid.min.js')}"></script>
		<script type="text/javascript"
			src="${path.js('snapdeal/snapdeal.js')}"></script>
		<script type="text/javascript"
			src="${path.js('timepicker/jquery-ui-calendar.min.js')}"></script>
	<script type="text/javascript"
		src="${path.js('snapdeal/validations.js')}"></script>
		<script type="text/javascript">	
	function validate() {
		 var valid = false;
		if( ( $('#roleCode').val().trim().length > 0)  && ( $('#roleDesc').val().trim().length > 0)) {
			$('#addNewRoleForm').submit();
		}else
			{
			$('#errorText').show();
		 } 
	}
	</script>
	</tiles:putAttribute>
</tiles:insertTemplate>