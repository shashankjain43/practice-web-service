<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
function update(){
	document.myform.action='${path.http}/admin/addAccessControl/addRoles';
	document.myform.submit();
}

function fetch() {
	document.myform.action='${path.http}/admin/addAccessControl/fetchRoles';
	document.myform.submit();
}
</script>
<tiles:insertTemplate template="/views/layout/adminBase.jsp">
 	<%@ include file="/views/common/home_LogOut_header.jsp"%>

	<tiles:putAttribute name="title" value="Access Control" />
	<tiles:putAttribute name="head">
	</tiles:putAttribute>
	<tiles:putAttribute name="body">

		<div id="content_wrapper">
		<div id="internal-content">
		<div class="content-bdr">
		<div class="graybox" style="width: 400px; margin: 0 auto;">
		<div class="gray-cont">
		<FORM name="myform" METHOD=GET
			action='${path.http}/admin/addAccessControl/addRoles'>
		<div class="cus_info_wrap"><label class="labelTop">Pattern*</label>
		<INPUT TYPE=TEXT NAME=pattern class="input" value="${test}" style="float: left; width: auto;"> 
		<INPUT value="Fetch" class="button" onclick="fetch()" style="width:80px;"></div>
		<div class="cus_info_wrap"><label class="labelTop">Roles*</label>
		<div class="role_type">
		<ul>
			<input id="roles" type="hidden" value="${roles}" />
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=unverified> unverified</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=registered> registered</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=admin> admin</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=bd> bd</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=content> content</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=SUPER> SUPER</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=vendor> vendor</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=affiliate> affiliate</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=ccmanager> ccmanager</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=marketing> marketing</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=getaways-bd> getaways-bd</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=product> product</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=campaignmgr> campaignmgr</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=ccexecutive> ccexecutive</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=finance> finance</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=Audit> Audit</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=MIS> MIS</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=SEO> SEO</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=ContentMgr> Content Manager</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=mobile> mobile</li>	
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=logistics> logistics</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=canAppLogistics> Cancellation Approval Logistics</li>	
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=ccexecutiveOutgoing> CCExecutive Outgoing</li>			
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=storefrontSuperAdmin> StorefrontSuperAdmin</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=financemgr> Finance Manager</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=pcexecutive> PC executive</li>
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=pcmanager> PC Manager</li>	
			<li><input class="rolesCheck" TYPE=checkbox name="roles"
				VALUE=codverification> COD Verification</li>			
		</ul>
		</div>
		</div>
		<div align="center"><INPUT value="Update" class="button"
			onclick="update()"></div>

		</FORM>
		</div>
		</div>
		</div>
		</div>
		</div>
	</tiles:putAttribute>
		
</tiles:insertTemplate>
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
		$(document).ready(function () {
			$(".rolesCheck").each(function() {
				if ($("#roles").val().indexOf(($(this).val())) != -1) {
					$(this).attr('checked', 'checked');
				}
			});
		});
		</script>