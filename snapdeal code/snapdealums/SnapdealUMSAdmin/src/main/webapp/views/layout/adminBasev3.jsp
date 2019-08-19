<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title><tiles:getAsString name="title" /></title>
<link href="${path.css('snapdeal/jqueryUi.css')}" rel="stylesheet"
	type="text/css" />
<link href="${path.css('snapdeal/style.css')}" rel="stylesheet"
	type="text/css" />
<link rel="shortcut icon" href="/img/icons/favicon.ico"
	type="image/x-icon" />
<script>
	if (typeof Snapdeal == 'undefined') {
		Snapdeal = {};
	};
	Snapdeal.getStaticPath = function(path) {
		return '${path.http}' + path;
	}

	Snapdeal.getResourcePath = function(path) {
		return '${path.resources("")}' + path;
	}
</script>

<tiles:insertAttribute name="head" defaultValue="" />

</head>
<body style="width: 100%; height: 100%">
	<tiles:insertAttribute name="header"
		defaultValue="/views/layout/header.jsp" />
	<div id="system_message">
		<c:choose>
			<c:when test="${systemMessage != null}">
				<div class="system_${systemMessage.status}">
					<div class="message_inner">
						<c:out value="${systemMessage.message}"></c:out>
						<div class="close">
							<!-- <img src="${path.resources('img/close.png')}" width="16" height="16" alt="Close" /> -->
						</div>
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
								<c:out value="${sysMessage.message}"></c:out>
								<div class="close">
									<!-- <img src="${path.resources('img/close.png')}" width="16" height="16" alt="Close" /> -->
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when
								test="${memcache.get('broadcast')!='' && memcache.get('broadcast')!=null}">
								<div class="sysMsg">
									<div class="sysInner">
										<c:out value="${memcache.get('broadcast')}"></c:out>
										<div class="close"></div>
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
											<div class="close">
												<!-- <img src="${path.resources('img/close.png')}" width="16" height="16" alt="Close" /> -->
											</div>
										</div>
									</div>
								</sec:authorize>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</div>
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer"
		defaultValue="/views/layout/footer.jsp" />


	 
	<script type="text/javascript" src="${path.js('jquery/jquery.all.min.js')}"></script>
	<%-- <script type="text/javascript" src="${path.js('jquery/jquery.date_input.pack.js')}"></script> --%>

	<script type="text/javascript" src="${path.js('snapdeal/snapdeal.js')}"></script>
	<script type="text/javascript" src="${path.js('snapdeal/signupWidget.js')}"></script>
	<%-- <script type="text/javascript" src="${path.js('snapdeal/calender.js')}"></script> --%>

    <script type="text/javascript" src="${path.js('jquery/common.js')}"></script> 
	<tiles:insertAttribute name="deferredScript" defaultValue="" />
	<div id="lightboxVendorTable" style="display: none;">
		<div id="lightboxVendorLabel">
			<B style="padding: 10px 10px 5px 10px; display: block">Choose
				Vendor</B>
		</div>
		<div id="lightboxVendorData"></div>
		<div id="lightboxVendorButton">
			<button margin="0 auto" id="save">Save</button>
			&nbsp; &nbsp;
			<button margin="0 auto" id="cancel">Cancel</button>
		</div>
	</div>
	<div id="lightboxVendor" style="display: none;"></div>
	
</body>
</html>
<!-- style="padding:0px 10px 10px 250px" -->