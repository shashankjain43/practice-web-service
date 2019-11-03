<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>



	<title><tiles:getAsString name="title"/></title>
	<style>
	.sysMsg{
	background: url("/img/system_fail_bg.jpg?v=5") repeat-x scroll center top #FFC703; border-bottom: 1px solid #BF8A01;
    color: #0D0B09;
    overflow: hidden;
    padding: 5px 10px;
    }
    .sysInner{
    background: url("/img/tick.png?v=5") no-repeat scroll left center transparent;
    margin: 0 auto;
    padding: 2px 25px;
    position: relative;
    width: 900px;
    font-weight:bold;
    }
    .close {
    background: url("/img/home-sprite.png") no-repeat scroll -138px -228px transparent;
    color: #000000;
    cursor: pointer;
    float: right;
    height: 12px;
    overflow: hidden;
    text-decoration: none;
    width: 12px;
	}
	</style>
	<meta name="google-site-verification" content="8v8zAJic2w2XirLf5cTcaMB3A6sWQHQ_pv2EubyTtZc" />

	<link href="${path.css('style.css')}" rel="stylesheet" type="text/css" />
	<link href="${path.css('admin.css')}" rel="stylesheet" type="text/css" />
	<link href="${path.css('adminv1.css')}" rel="stylesheet" type="text/css" />
	 <%-- <c:if test="${affiliate == null}">
		<c:choose>
	 		<c:when test="${!getaways && not empty dealModel}">
	 			<link href="${path.css('festival/headerv1.css')}" rel="stylesheet" type="text/css" />
	 		</c:when>
	 		<c:otherwise>
	 			<link href="${path.css('festival/getaways.css')}" rel="stylesheet" type="text/css" />
	 		</c:otherwise>
	 	</c:choose>
	</c:if>
	<c:if test="${affiliate != null && not empty affiliate.css}">
		<link href="${path.css(affiliate.css)}" rel="stylesheet" type="text/css" />
	</c:if> --%>
	<link rel="shortcut icon" href="/img/icons/favicon.ico" type="image/x-icon" />
	<tiles:insertAttribute name="pathResolver" defaultValue="/views/layout/pathResolver.jsp" />
	<tiles:insertAttribute name="head" defaultValue=""/>
	
</head>
<body style="width: 100%; height: 100%">
<div>

		<div><tiles:insertAttribute name="header" defaultValue="/views/layout/header_old.jsp"/></div>
		<div><tiles:insertAttribute name="navigation" defaultValue="/views/layout/navigation_old.jsp"/> </div>
		<div id="system_message">
		<c:choose>
			<c:when test="${systemMessage != null}">
				<div class="system_${systemMessage.status}"><div class="message_inner"><c:out value="${systemMessage.message}"></c:out><div class="close"><!-- <img src="${path.resources('img/close.png')}" width="16" height="16" alt="Close" /> --></div></div></div>		
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${param['systemcode'] != null}">
						<c:set var="sysMessage" value="${cache.getCacheByName('systemMessage').getSystemMessageByCode(param['systemcode'])}"></c:set>
						<div class="system_${sysMessage.status}"><div class="message_inner"><c:out value="${sysMessage.message}"></c:out><div class="close"><!-- <img src="${path.resources('img/close.png')}" width="16" height="16" alt="Close" /> --></div></div></div>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${memcache.get('broadcast')!='' && memcache.get('broadcast')!=null}">
								<div class="sysMsg"><div class="sysInner"><c:out value="${memcache.get('broadcast')}"></c:out><div class="close"></div></div></div>
							</c:when>
							<c:otherwise>
								<sec:authorize ifAllGranted="unverified">
								<div class="system_fail"><div class="message_inner">Please verify your account to activate. Click <a href="${path.http}/sendVerificationEmail">here</a> to send verification email.<div class="close"><!-- <img src="${path.resources('img/close.png')}" width="16" height="16" alt="Close" /> --></div></div></div>
								</sec:authorize>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>	
		</div>
		<div><tiles:insertAttribute name="body" /></div>
		<div><tiles:insertAttribute name="footer" defaultValue="/views/layout/footer_old.jsp"/></div>
</div>

<script type="text/javascript" src="${path.js('bitly/bitly.js')}"></script>
<script type="text/javascript" src="${path.js('snapdeal/signupWidget.js')}"></script>
<script type="text/javascript">
$("#datePicker").date_input();
$("#expiryDate").date_input();
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-271722-9']);
  _gaq.push(['_trackPageview']);
 
  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
 
</script>



<!-- chartbeat -->
<script type="text/javascript">
var _sf_async_config={uid:3977,domain:"snapdeal.com"};
(function(){
function loadChartbeat() {
window._sf_endpt=(new Date()).getTime();
var e = document.createElement('script');
e.setAttribute('language', 'javascript');
e.setAttribute('type', 'text/javascript');
e.setAttribute('src',
(("https:" == document.location.protocol) ? "https://s3.amazonaws.com/" : "http://") +
"static.chartbeat.com/js/chartbeat.js");
document.body.appendChild(e);
}
var oldonload = window.onload;
window.onload = (typeof window.onload != 'function') ?
loadChartbeat : function() { oldonload(); loadChartbeat(); };
})();
</script>

</body>
</html>