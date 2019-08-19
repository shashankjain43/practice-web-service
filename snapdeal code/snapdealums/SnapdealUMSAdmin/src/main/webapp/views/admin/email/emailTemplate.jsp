<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/adminBase.jsp">
<%@ include file="/views/common/home_LogOut_header.jsp"%>
	<tiles:putAttribute name="title" value="Email Template Admin"></tiles:putAttribute>
	<tiles:putAttribute name="body">
	
		<div id="internal-content">
		<div class="error">${message}</div>
		<div class="content-bdr">
		<div class="graybox">
		<div class="gray-cont">
		<div class="wrapper2">
		<div class="head-lable">Update Email Template Details</div>
		<div class="apiadmin"><form:form commandName="emailTemplateAdminForm"	method="POST" action="${path.http}/admin/emailTemplate/updateEmailTemplate" name="emailTemplateAdminForm">
			<div class="form-head">Email Template Name:<form:errors path="emailTemplate.name" class="errorMessage"/></div>
			<div class="form-row">
			<form:select path="emailTemplate.name" id="emailTemp">
				<c:forEach items="${cache.getCacheByName('emailTemplateCache').getEmailTemplates()}" var="emailTemp" >
				<form:option value="${emailTemp.name}">${emailTemp.name}</form:option>
				</c:forEach>
			</form:select>
			<input type="button" name="fetchData" id="fetchEmailData" value="Fetch" class="button" /></div>
			<c:if test="${emailTemplateAdminForm.emailTemplate.id != null}">
				<form:hidden path="emailTemplate.id" />
				<form:hidden path="emailTemplate.created" />
				<div class="form-head">Subject Template:<form:errors path="emailTemplate.subjectTemplate" class="errorMessage"/></div>
				<div class="form-row"><form:input path="emailTemplate.subjectTemplate" class="input-fld" /> </div>
				<div class="form-head">Body Template:<form:errors path="emailTemplate.bodyTemplate" class="errorMessage"/></div>
				<div class="form-row-area"><form:textarea path="emailTemplate.bodyTemplate" class="textarea-fld" /> </div>
				<div class="form-head">From:<form:errors path="emailTemplate.from" class="errorMessage"/></div>
				<div class="form-row"><form:input path="emailTemplate.from" class="input-fld" /> </div>
				<div class="form-head">To:<form:errors path="emailTemplate.to" class="errorMessage"/></div>
				<div class="form-row"><form:input path="emailTemplate.to" class="input-fld" /> </div>
				<div class="form-head">CC:<form:errors path="emailTemplate.cc" class="errorMessage"/></div>
				<div class="form-row"><form:input path="emailTemplate.cc" class="input-fld" /> </div>
				<div class="form-head">BCC:<form:errors path="emailTemplate.bcc" class="errorMessage"/></div>
				<div class="form-row"><form:input path="emailTemplate.bcc" class="input-fld" /> </div>
				<div class="form-head">Reply To:<form:errors path="emailTemplate.replyTo" class="errorMessage"/></div>
				<div class="form-row"><form:input path="emailTemplate.replyTo" class="input-fld" /> </div>
				
					<div class="form-head">TriggerID:<form:errors path="emailTemplate.replyTo" class="errorMessage"/></div>
				<div class="form-row"><form:input path="emailTemplate.triggerId" class="input-fld" /> </div>
				
				<div class="form-head">Email Channel:</div>
				<div class="form-row"> 
					<form:select path="emailTemplate.emailChannelId">
						<c:forEach items="${emailTemplateAdminForm.channels}" var="emailChan" >
							<form:option value="${emailChan.channelID}">${emailChan.channelName}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<div align="center"><input type="submit" name="submit" value="Submit" class="submit" /></div>
			</c:if>
		</form:form></div>
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
	$(document).ready(function() {
			$('#fetchEmailData').click(function(){
		 		window.location.href = "/admin/emailTemplate/fetchEmailData?name="+$('#emailTemp').val();
		 	});
		});
   	</script>