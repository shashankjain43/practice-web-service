<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<title><tiles:getAsString name="title" /></title>

<style>
.sysMsg {
	background: url("/img/system_fail_bg.jpg?v=5") repeat-x scroll center
		top #FFC703;
	border-bottom: 1px solid #BF8A01;
	color: #0D0B09;
	overflow: hidden;
	padding: 5px 10px;
}

.sysInner {
	background: url("/img/tick.png?v=5") no-repeat scroll left center
		transparent;
	margin: 0 auto;
	padding: 2px 25px;
	position: relative;
	width: 900px;
	font-weight: bold;
}

.close {
	background: url("/img/home-sprite.png") no-repeat scroll -138px -228px
		transparent;
	color: #000000;
	cursor: pointer;
	float: right;
	height: 12px;
	overflow: hidden;
	text-decoration: none;
	width: 12px;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="google-site-verification"
	content="8v8zAJic2w2XirLf5cTcaMB3A6sWQHQ_pv2EubyTtZc" />
<link href="${path.css('style.css')}" rel="stylesheet" type="text/css" />
<link href="${path.css('admin.css')}" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="/img/icons/favicon.ico"
	type="image/x-icon" />
<script>
	if (typeof Snapdeal == 'undefined') {
		Snapdeal = {};
	};
	Snapdeal.getStaticPath = function(path) {
		return '${path.http}' + path;
	}
</script>

<%-- <tiles:insertAttribute name="head" defaultValue="" /> --%>

</head>


<body>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer" />
</body>
</html>