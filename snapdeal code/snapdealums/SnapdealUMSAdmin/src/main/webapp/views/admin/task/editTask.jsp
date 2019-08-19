<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/adminBase.jsp">
	<tiles:putAttribute name="title" value="Edit Task" />
	<tiles:putAttribute name="head">
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div id="internal-content"><form:form commandName="task1"
			method="POST" action='${path.http}/admin/updateTask' name="editTask">
			<div class="content-bdr">
			<div class="graybox-admin">
			<div class="gray-cont-admin">
			<div class="wrapper2"><form:hidden path="id" value="${task1.id}" />
			<form:hidden path="lastExecResult" value="${task1.lastExecResult}" />
			<form:hidden path="created"
						value="${dateUtils.dateToString(task1.created, 'yyyy-MM-dd HH:mm:ss')}" />
			<form:hidden path="updated"
						value="${dateUtils.dateToString(task1.updated, 'yyyy-MM-dd HH:mm:ss')}" />			
			<form:hidden path="lastExecTime"
						value="${dateUtils.dateToString(task1.lastExecTime, 'yyyy-MM-dd HH:mm:ss')}" />
			<div class="form-head">Name * 
			<form:errors path="name" class="errorMessage" /></div>
			<div class="form-row">
			<form:input path="name" class="input-fld" value="${task1.name}" /></div>

			<div class="form-head">Cron Expression * <form:errors path="cronExpression"
				class="errorMessage" /></div>
			<div class="form-row"><form:input path="cronExpression"
				class="input-fld" value="${task1.cronExpression}" /></div>

			<div class="form-head">Clustered : <form:errors path="clustered *"
				class="errorMessage" /></div>
			<div class="form-row" style="height: auto;">
			  <form:checkbox path="clustered" /></div>


			<div class="form-head">Concurrent * <form:errors path="concurrent"
				class="errorMessage" /></div>
			<div class="form-row">  <form:checkbox path="concurrent" /></div>

			<div class="form-head">TaskClass * <form:errors
				path="implClass" class="errorMessage" /></div>
			<div class="form-row"><form:input path="implClass"
				class="input-fld" value="${task1.implClass}" /></div>

			<div class="form-head">NotificationEmail * <form:errors
				path="notificationEmail" class="errorMessage" /></div>
			<div class="form-row"><form:input path="notificationEmail"
				class="input-fld" value="${task1.notificationEmail}" /></div>

			<div class="form-head">emailTemplate * <form:errors
				path="emailTemplate" class="errorMessage" /></div>
			<div class="form-row"><form:input path="emailTemplate"
				class="input-fld" value="${task1.emailTemplate}" /></div>

			<div class="form-head">Enable* <form:errors path="enabled"
				class="errorMessage" /></div>
			<div class="form-row"><form:checkbox path="enabled" /></div>
			
			<c:if test="${task1.taskParameters.size() > 0}">
				
				<div class="head-lable">Enter Task Parameters</div>
				<c:forEach items="${task1.taskParameters}" var="taskParameter" varStatus="count">				
					<div class="form-head">Name * ${taskParameter.name}</div>
					<div class="form-head">Value * ${taskParameter.value}					 
					<form:errors path="taskParameters[${count.index}].value" class="errorMessage" /></div>
					<div class="form-row">
			 <form:hidden path="umsTaskParameterList[${count.index}].id" value="${taskParameter.id}"/>			 
			 <form:hidden path="umsTaskParameterList[${count.index}].name" value="${taskParameter.name}"/>
			 <form:input path="umsTaskParameterList[${count.index}].value" class="input-fld" value="${taskParameter.value}" /></div>
				</c:forEach>
			</c:if>

			<div align="center"><input type="submit" name="submit"
				value="Submit" class="submit" /></div>

			</div>
			</div>
			</div>

			</div>
		</form:form></div>



	</tiles:putAttribute>
</tiles:insertTemplate>
