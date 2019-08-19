<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/base.jsp">

	<tiles:putAttribute name="title" value="Customer Survey - SnapDeal.com" />

	<tiles:putAttribute name="head">
		<meta name="description" content="Customer Survey - SnapDeal.com"></meta>
		<meta name="keywords" content="Customer Survey, SnapDeal.com"></meta>
		<script type="text/javascript"
			src="http://maps.google.com/maps/api/js?sensor=false"></script>
		<script type="text/javascript" src="${path.js('jquery/jquery.date_input.pack.js')}"></script>
		<style>
		
.calendar {
	background: #FFF url("/img/calendar.jpg") no-repeat center right;
}
.date_selector,.date_selector * {
width:auto;
height:auto;
border:none;
background:none;
text-align:left;
text-decoration:none;
margin:0;
padding:0;
}

.date_selector {
background:#F2F2F2;
border:1px solid #bbb;
position:absolute;
z-index:100000;
display:none;
margin:-1px 0 0;
padding:5px;
}

.date_selector_ieframe {
position:absolute;
z-index:99999;
display:none;
}

.date_selector .nav {
width:17.5em;
}

.date_selector .month_nav,.date_selector .year_nav {
display:block;
position:relative;
text-align:center;
margin:0 0 3px;
padding:0;
}

.date_selector .month_nav {
float:left;
width:55%;
}

.date_selector .year_nav {
float:right;
width:35%;
margin-right:-8px;
}

.date_selector .month_name,.date_selector .year_name {
font-weight:700;
line-height:20px;
color:#539dcf;
}

.date_selector .button {
display:block;
position:absolute;
top:0;
width:18px;
height:18px;
line-height:17px;
font-weight:700;
color:#003C78;
text-align:center;
font-size:120%;
overflow:hidden;
border:1px solid #F2F2F2;
}

.date_selector .button:hover,.date_selector .button.hover {
background:none;
color:#003C78;
cursor:pointer;
border-color:#ccc;
}

.date_selector .prev {
left:0;
}

.date_selector .next {
right:0;
}

.date_selector table {
border-spacing:0;
border-collapse:collapse;
clear:both;
}

.date_selector th,.date_selector td {
width:2.5em;
height:2em;
text-align:center;
color:#000;
padding:0;
}

.date_selector td {
border:1px solid #ccc;
line-height:2em;
text-align:center;
white-space:nowrap;
color:#003C78;
background:#FFF;
}

.date_selector td.today {
background:#FFFEB3;
}

.date_selector td.unselected_month {
color:#ccc;
}



.date_selector td.selectable_day:hover,.date_selector td.selectable_day.hover {
background:#003C78;
color:#FFF;
}


</style>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<c:set var="city"
			value="${cache.getCacheByName('zonesCache').getZoneByPageUrl(zonePageUrl).city}"></c:set>
		<div id="internal-content">
			<div class="content-bdr">
				<div class="emailfeedback_head_outer">
					<div class="emailfeedback_head">
						<span>Thank you for your time !</span><br />
					</div>
				</div>
				</div>
		</div>
	</tiles:putAttribute>
	
</tiles:insertTemplate>