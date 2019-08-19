<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<script type="text/javascript"
	src="${context}/static/js/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${context}/static/js/jquery/jquery-ui-1.10.4.min.js"></script>

<link href="${context}/static/css/jquery-ui-1.10.4.min.css"
	rel="stylesheet" type="text/css" />
<!-- Include one of jTable styles. -->
<link href="${context}/static/jtable/themes/metro/blue/jtable.min.css"
	rel="stylesheet" type="text/css" />
 
<!-- Include jTable script file. -->
<script src="${context}/static/jtable/jquery.jtable.min.js"
	type="text/javascript"></script>


<link href="${context}/static/css/style.css" rel="stylesheet"
	type="text/css" />
<link href="${context}/static/css/admin.css" rel="stylesheet"
	type="text/css" />
<link rel="shortcut icon" href="${context}/img/icons/favicon.ico"
	type="image/x-icon" />


<style>
.ui-tooltip {
	max-width: 100%;
}
</style>



<title><tiles:getAsString name="title" /></title>

<tiles:insertAttribute name="head" defaultValue="" />

</head>

<div>
	<div>
		<tiles:insertAttribute name="header"
			defaultValue="/views/layout/header_old.jsp" />
	</div>
	<div>
		<tiles:insertAttribute name="navigation"
			defaultValue="/views/layout/navigation_old.jsp" />
	</div>
	<div id="system_message">
		<div class="system_fail"></div>
	</div>
</div>

	<tiles:insertAttribute name="filter" defaultValue="" />


<div>
	<div id="Container"></div>
</div>



<!-- 
<div style="height: 30px;">
	<div style="float: right;">
		<span>Change theme:</span> <select id="ThemeCombobox"
			style="margin: 0px; margin: 5px 0px 0px 0px;">
			<option value="jqueryui-flick">jQueryUI theme (flick)</option>
			<option value="jqueryui-redmond">jQueryUI theme (redmod)</option>
			<option value="jqueryui-sunny">jQueryUI theme (sunny)</option>
			<option value="jqueryui-trontastic">jQueryUI theme
				(trontastic)</option>
			<option value="jqueryui-ui-darkness">jQueryUI theme
				(ui-darkness)</option>
			<option value="lightcolor/blue/jtable.css">Light - Blue</option>
			<option value="lightcolor/gray/jtable.css">Light - Gray</option>
			<option value="lightcolor/green/jtable.css">Light - Green</option>
			<option value="lightcolor/orange/jtable.css">Light - Orange</option>
			<option value="lightcolor/red/jtable.css">Light - Red</option>
			<option value="/static//jtable/themes/metro/blue/jtable.css">Metro Style - Blue</option>
			<option value="/static//jtable/themes/metro/brown/jtable.css">Metro Style - Brown</option>
			<option value="metro/crimson/jtable.css">Metro Style -
				Crimson</option>
			<option value="metro/darkgray/jtable.css">Metro Style - Dark
				Gray</option>
			<option value="metro/darkorange/jtable.css">Metro Style -
				Dark Orange</option>
			<option value="metro/green/jtable.css">Metro Style - Green</option>
			<option value="metro/lightgray/jtable.css">Metro Style -
				Light Gray</option>
			<option value="metro/pink/jtable.css">Metro Style - Pink</option>
			<option value="metro/purple/jtable.css">Metro Style - Purple</option>
			<option value="metro/red/jtable.css">Metro Style - Red</option>
		</select>
	</div>
	<div
		style="float: left; text-align: left; font-style: italic; margin: 5px 0px 0px 0px;">
		A quick demonstration of jTable.</div>
</div>
 -->

<body>

	<tiles:insertAttribute name="body" defaultValue="" />


</body>
</html>
