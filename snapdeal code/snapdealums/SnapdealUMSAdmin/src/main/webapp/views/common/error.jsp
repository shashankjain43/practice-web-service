<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/base.jsp">
	<div>
		<ul>
			<li><a href="${path.http }/logout">Sign Out</a></li>
		</ul>
	</div>
	<c:choose>
		<c:when test="${param['code'] == '404'}">
			<tiles:putAttribute name="title"
				value="SnapDeal.com - 404 &ndash; Page not found" />
		</c:when>
		<c:when test="${param['code'] == '500'}">
			<tiles:putAttribute name="title"
				value="SnapDeal.com - 500 &ndash; Internal Server Error" />
		</c:when>
		<c:when test="${param['code'] == '412'}">
			<tiles:putAttribute name="title"
				value="SnapDeal.com - 412 &ndash; Precondition Failed" />
		</c:when>
		<c:when test="${param['code'] == '403'}">
			<tiles:putAttribute name="title"
				value="SnapDeal.com - 403 &ndash; Access Denied" />
		</c:when>
		<c:otherwise>
			<tiles:putAttribute name="title" value="SnapDeal.com - 401 &ndash;" />
		</c:otherwise>
	</c:choose>


	<tiles:putAttribute name="body">
		<div id="internal-content">
			<c:choose>
				<c:when test="${param['code'] == '404'}">

					<div class="big-text">
						<span class="big-error-text">404 Page Not Found;</span> <span>
							We Searched everywhere</span>
					</div>
					<div class="content-and-img">
						<div class="text-content">
							<div>
								<img alt=""
									src="${path.resources('img/error-404/arrow-icon-down.gif')}"
									width="30" height="15">
							</div>
							<div class="text-small" style="padding-top: 8px;">Under the
								bed, below the pillow, Friends' wallets, pockets of our foes,</div>
							<div class="text-small" style="padding-bottom: 20px;">River
								depths, mountain highs, In the tale of those old wives.</div>
							<div class="text-small">For you, our geeks looked
								everywhere.</div>
							<div class="text-small">Alas, that page has vanished into
								thin air</div>
							<div class="text-small">
								One <span style="color: #CD0000;">page lost</span>, many pages
								found, Yes, our reasoning is sound
							</div>
							<div class="text-small">There is a treasure you can strike,
								See if there is another page you like</div>
							<div style="padding-top: 8px;">
								<img alt=""
									src="${path.resources('img/error-404/arrow-icon-down.gif')}"
									width="30" height="15">
							</div>
						</div>
						<div class="img-content">
							<img alt="error-404"
								src="${path.resources('img/error-404/error-404.jpg')}"
								width="500" height="180">
						</div>
					</div>
					<div class="clear" style="padding-bottom: 40px;"></div>
					<div>
						<div class="clear"></div>
					</div>

				</c:when>
				
					<c:when test="${param['code'] == '500'}">
						<img src="${path.resources('img/error/error500.jpg')}" align="middle">
					</c:when>
					<c:when test="${param['code'] == '403'}">
						<img src="${path.resources('img/error/error403.jpg')}" align="middle">
					</c:when>
					<c:when test="${param['code'] == '412'}">
						<div class="big-text">
							<span class="big-error-text">412 Precondition has failed</span><br />
							<span> A specified precondition has failed for this
								request.</span>
						</div>
					</c:when>
					<c:otherwise>
						<img src="${path.resources('img/error/error401.jpg')}" align="middle">
					</c:otherwise>
			</c:choose>

		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="deferredScript">
		<sd:omnitureScript pageName="errorPage" />
	</tiles:putAttribute>
</tiles:insertTemplate>