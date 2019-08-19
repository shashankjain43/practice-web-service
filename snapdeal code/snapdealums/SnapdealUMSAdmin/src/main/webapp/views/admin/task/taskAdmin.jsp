<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/adminBase.jsp">

	<tiles:putAttribute name="title" value="Task Admin" />

	<tiles:putAttribute name="head">
	</tiles:putAttribute>
	<%@ include file="/views/common/home_LogOut_header.jsp"%>
	<tiles:putAttribute name="body">
	<div id="internal-content">
			<div class="content-bdr">
		<c:if test="${tasks!= null}">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="admin-tbl">
				<tr>
					<th>Name</th>
					<th>Cron_Expression</th>
					<th>Clustered</th>
					<th>Concurrent</th>
					<th>Impl_Class</th>
					<th>Last Run Time</th>
					<th>Run Result</th>
					<th>Notification Email</th>
					<th>Email Template</th>
					<th>Enabled</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>

				<c:forEach items="${tasks}" var="task">
					<tr>
						<td>${task.name}</td>					
						<td>${task.cronExpression}</td>
						<td>${task.clustered}</td>
						<td>${task.concurrent}</td>
						<td>${task.implClass}</td>
						
							<td>${task.lastExecTime}</td>
								<td>${task.lastExecResult}</td>
									<td>${task.notificationEmail}</td>
										<td>${task.emailTemplate}</td>
						
						<td>${task.enabled}</td>
						<td>
						<a href="${path.http}/admin/editTask/${task.id}">Edit</a>
						<c:if test= "${task.enabled}">	
						</td>
						<td width="2%">
						<a href="${path.http}/admin/runTask/${task.id}" onclick = "if (! confirm('Are you sure do you want run task?')) return false;">RunTask</a>
						</td>	
						 </c:if>					
					</tr>
					<tr>
					<td colspan="10">
					<c:if test="${task.taskParameters.size() > 0}">
					<table cellpadding="0" cellspacing="0" border="0" width="50%">					
					    <tr>
					    <th>Name</th>
					    <th>Value</th>
					    </tr>
						<c:forEach items="${task.taskParameters}" var="taskParameter">
					    <c:if test= "${taskParameter != null}">					    
					    <tr>
					    <td width="2%">${taskParameter.name}</td>
					    <td width="2%"> ${taskParameter.value}</td>	
					    </tr>					    				  
					    </c:if>					    
						</c:forEach>					
						</table>
					</c:if>
						</td>
					</tr>
					<tr>
					<td colspan="10" style="border-bottom: 1px #ccc solid;"></td>
					</tr>
				</c:forEach>			
			</table>
			
		</c:if>
</div></div>
	</tiles:putAttribute>	 
</tiles:insertTemplate>
