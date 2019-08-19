<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/admin.jsp">

	<tiles:putAttribute name="title" value="Admin Dasboard"/>
	<tiles:putAttribute name="head">
		<meta name="description" content="${metaDescription}"></meta>
		<meta name="keywords" content="${metaKeywords}"></meta>
		<style>
		       #body-content {
				-moz-border-radius-bottomleft:15px;
				-moz-border-radius-bottomright:15px;
				-moz-border-radius-topleft:15px;
				-moz-border-radius-topright:15px;
				pading-left:10px;
				margin:-12px auto;
				overflow:hidden;
				width:309px;
			}
		      
		</style>	
	</tiles:putAttribute>
	
   <tiles:putAttribute name="body">
	<div class="logo" >
	
	</div>
	<div class="report-details">
		<div align="center">
			<div id="body-content">
			<div>
	 				<font style="color:#029ECB;font-size:20px;font-weight:bold;height:45px;padding-left:10px;">Welcome to UMS Admin Dasboard..</font>
	 		</div>
			</div>
		</div>
	</div>
		    
		
	</tiles:putAttribute>
	</tiles:insertTemplate>