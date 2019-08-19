<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/tld_includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link rel="stylesheet" type="text/css" href="${path.css('jquery-ui-1.8.17.custom.css')}" />
<tiles:insertAttribute name="headImmediate" defaultValue="" />
<title><tiles:getAsString name="title" /></title>
<link href="${path.css('snapdeal.css')}" rel="stylesheet"
    type="text/css" />
<c:if test="${affiliate != null && not empty affiliate.css}">
	<link href="${path.css(affiliate.css)}" rel="stylesheet" type="text/css" />
</c:if>
<link href="<tiles:insertAttribute name="pageSpecificCss" defaultValue="${path.css('innerPages.css')}"/>"	rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${path.resources('img/icons/favicon.ico')}" type="image/x-icon" />
<link rel=icon type=image/ico href="${path.resources('img/icons/favicon.ico')}" />

<script>
    if (typeof Snapdeal == 'undefined') {Snapdeal = {};};Snapdeal.getStaticPath = function(path) {return '${path.http}' + path;};
    Snapdeal.getResourcePath = function(path) {return '${path.resources("")}' + path;};
</script>
<tiles:insertAttribute name="head" defaultValue="" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--  for IE9 -->
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<!-- source tracking params -->
<c:set var="visitTrackingParamsMap" value="${sessionScope.visitTrackingParams}"/>
<c:forEach var="tarckingPrams" items="${visitTrackingParamsMap}">
    <input type="hidden" name ="${tarckingPrams.key}" id="${tarckingPrams.key}" value="${tarckingPrams.value}" />
</c:forEach>
<!-- End -->
</head>
<body style="width: 100%; height: 100%; min-width: 962px;">
<div id="redBox"></div>
	<div id="yellowStrip"></div>
	<div id="system_message">
		<div class="sytem-msj-cont">
			<c:choose>
				<c:when test="${systemMessage != null}">
					<div class="system_${systemMessage.status}">
						<div class="message_inner">
							<c:out value="${systemMessage.message}"></c:out>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${param['systemcode'] != null}">
							<c:set var="sysMessage"
								value="${cache.getCacheByName('systemMessage').getSystemMessageByCode(param['systemcode'])}"></c:set>
							<div class="system_${sysMessage.status}">
								<div class="message_inner">
									<sd:resolveMetaTemplates metaTemplate="${sysMessage.message}"></sd:resolveMetaTemplates>
									<div class="close">Close</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<sec:authorize ifAllGranted="unverified">
								<div class="system_fail">
									<div class="message_inner">
										Please verify your account to activate. Click <a
											href="${path.http}/sendVerificationEmail">here</a> to send
										verification email.
										<div class="close">Close</div>
									</div>
								</div>
							</sec:authorize>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<c:choose>
		<c:when test="${affiliate != null && not empty affiliate.headerPath}">
			<tiles:insertAttribute name="header" defaultValue="${affiliate.headerPath}" />
		</c:when>
		<c:otherwise>
			<tiles:insertAttribute name="header" defaultValue="/views/layout/header.jsp" />
		</c:otherwise>
	</c:choose>
	<div id="content_wrapper">
		<div class="page-main-cont">
			<tiles:insertAttribute name="body" />
		</div>
	</div>
	<c:choose>
		<c:when test="${fn:contains(header.cookie, 'u=')}">
			<div class="ajaxFooter" u="getFooter"></div>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${affiliate != null && not empty affiliate.footerPath}">
					<tiles:insertAttribute name="footer"
						defaultValue="${affiliate.footerPath}" />
				</c:when>
				<c:otherwise>
					<tiles:insertAttribute name="footer"
						defaultValue="/views/layout/footer.jsp" />
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>

	<div id="newSubscriber"></div>
    <script type="text/javascript" src="${path.js('jquery/jquery.all.min.js')}"></script>
    <script type="text/javascript" src="${path.js('snapdeal/snapdealHome.js')}"></script>
	<script type="text/javascript" src="${path.js('snapdeal/tracking.js')}"></script>
	<script type="text/javascript" src="${path.js('omniture/sd_scode.js')}"></script>
	<c:if test="${param['loginSuccess']=='success'}">
		<c:set var = "login" value = "SnapdealLogin"></c:set>
		<sd:omnitureScript pageName="rpxLogin" />
	</c:if>

	<input type="hidden" name="affiliate" id="aff" value="${affiliate!=null ? affiliate.urlPrefix : ''}"/>

	<tiles:insertAttribute name="deferredScript" defaultValue="" />
    <!-- These fields are declared for subscription flow -->
		<input id="emailRedboxStatus" type="hidden" value="${cache.getCacheByName('redboxConfigCache').getConfig('emailredbox').enabled}" />
		<input id="mobileRedboxStatus" type="hidden" value="${cache.getCacheByName('redboxConfigCache').getConfig('mobileredbox').enabled}" />
		<input type="hidden" name="zoneUrl" id="subZoneUrl" value="${cityPageUrl}" /> 
		<input type="hidden" name="cityName" id="subCityName" value="${cityName}" /> 
    <script defer="defer" type="text/javascript">
				var _gaq = _gaq || [];
				_gaq.push([ '_setAccount', 'UA-271722-9' ]);
				_gaq.push([ '_trackPageview' ]);
    </script>
    <script defer="defer" type="text/javascript">
		var _sf_async_config = {
			uid : 3977,
			domain : "snapdeal.com"
		};
		(function() {
            function loadFiles() {
                var deferedScript = $("#deferredScript").html().replace("<!--COMMENTSTARTED", "").replace("COMMENTENDED-->", "");
                deferedScript = decodeURIComponent(deferedScript.replace(/\+/g, '%20'));
                $("#deferredScript").html(deferedScript);
			}
			var oldonload = window.onload;
            window.onload = (typeof window.onload != 'function') ? loadFiles: function() {
						oldonload();
                loadFiles();
					};
		})();
	</script> 
	<c:if test="${!singleDealPage}">
		<iframe src="http://www.vizury.com/analyze/analyze.php?account_id=VIZ-VRM-1330&param=1000" scrolling="no" width="1" height="1" marginheight="0" marginwidth="0" frameborder="0"></iframe>
	</c:if> 
	<tiles:insertAttribute name="bodyEnd" defaultValue="" />
</body>
</html>
