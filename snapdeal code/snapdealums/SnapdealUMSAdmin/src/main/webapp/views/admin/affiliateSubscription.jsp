<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/adminBase.jsp">

<%@ include file="/views/common/home_LogOut_header.jsp"%>
	<tiles:putAttribute name="title" value="Admin Landing Page" />
	<tiles:putAttribute name="head">
	
		<meta name="description" content="${metaDescription}"></meta>
		<meta name="keywords" content="${metaKeywords}"></meta>
		<style>
.system_success {
	display: block;
}

.promoError {
	background: url("/img/festival/adminv1/error.gif") no-repeat scroll 0 0
		transparent;
	margin-top: 5px;
	padding-left: 12px;
	color: #D40707 !important;
	font-weight: bold !important;
}

</style>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
	
		
		<script type="text/javascript"
			src="${path.js('timepicker/jquery-ui-timepicker.js')}"></script>
		<script type="text/javascript" src="${path.js('widget/jscolor.js')}"></script>
		<form:form commandName="subscriptionOffer" name="subscriptionOffer"
			id="subscriptionOffer" method="POST"
			action="${path.http}/admin/marketing/affiliateSubscription/addOffer"
			onsubmit="return validateForm(this);">
			<div style="margin: 0px 25px;">
				<div class="head-lable">
					<h4 class="head-lable-text">Affiliate Subscription Offers</h4>
				</div>
				<div id="system_message">
					<div class="system_fail">
						<div class="message_inner" id="result"></div>
					</div>
				</div>
				<div class="dealadmin">
					<div class="form-row-outer">
						<div class="form-head-left">Add/Edit Offer</div>
						<select id="offerNames" class="form-head-right"
							onchange="populateFields(this.value)">
							<option value="" class="parentcategories offer">Add new
								offer</option>
							<c:forEach items="${offers}" var="offer" varStatus="ctr">
								<option value="${offer.id }" class="parentcategories offer">${offer.offerName
									}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-row-outer" id="nameRow">
						<div class="form-head-left" id="offer">Offer Name</div>
						<div class="form-row-small">
							<form:input path="offerName" type="text" id="offerName"
								class="input-text validateNotEmpty" name="offerName"
								onchange="checkOfferNames();" />
						</div>
					</div>

					<div class="form-row-outer" id="nameRow">
						<div class="form-head-left" id="offer">Wap Offer T&C</div>
						<div class="form-row-small">
							<form:input path="offerText" type="text" id="offerText"
								class="input-text validateNotEmpty" name="offerText" />
						</div>
					</div>

					<div class="form-row-outer">
						<div class="form-head-left" id="Main Image">Select Desktop
							image</div>
						<div class="form-row-small">
							<input type="file" id="uploadedFile" class="input-text"
								name="imageFile" />
						</div>
					</div>
					<div class="form-row-outer">
						<div class="form-head-left"></div>
						<div class="form-row-small">
							<div class="upload-button" onclick="uploadimage('desktop')">
								<span class="form-head-right">Upload</span>
							</div>
						</div>
					</div>
					<div class="form-row-outer">
						<div class="form-head-left">Image Path after uploading</div>
						<div class="form-row-small">
							<form:input path="image" type="text" id="actualImageUrl"
								class="validateNotEmpty" readonly="true" />
						</div>
					</div>

					<div class="form-row-outer">
						<div class="form-head-left" id="Main Image">Select Mobile
							Image(106*90)</div>
						<div class="form-row-small">
							<input type="file" id="uploadedMobileFile" class="input-text"
								name="imageMobileFile" />
						</div>
					</div>
					<div class="form-row-outer">
						<div class="form-head-left"></div>
						<div class="form-row-small">
							<div class="upload-button" onclick="uploadimage('mobile')">
								<span class="form-head-right">Upload</span>
							</div>
						</div>
					</div>
					<div class="form-row-outer">
						<div class="form-head-left">Mobile Image Path after
							uploading</div>
						<div class="form-row-small">
							<form:input path="mobileImage" type="text"
								id="actualMobileImageUrl" class="validateNotEmpty"
								readonly="true" />
						</div>
					</div>

					<div class="form-row-outer">
						<div class="form-head-left">Promo Code</div>
						<div class="form-row-small">
							<form:input path="promoCode" type="text" id="promoCode"
								class="input-text validateNotEmpty" name="promoCode"
								onchange="validatePromoCode();" />
						</div>
					</div>

					<div class="form-row-outer">
						<div class="form-head-left">Desktop Enabled (for desktop
							site)</div>
						<div class="form-head-right">
							<form:checkbox path="enabled" id="enabled" value="true"
								checked="checked" />
						</div>
					</div>

					<div class="form-row-outer">
						<div class="form-head-left">Wap Enabled</div>
						<div class="form-head-right">
							<form:checkbox path="mobileEnabled" id="mobileEnabled"
								value="true" checked="checked" />
						</div>
					</div>



					<form:input path="id" type="hidden" id="offerId" />
					<input type="hidden" id="status" name="status" /> <img
						src="${path.resources('')}" />
					<div class="form-row-outer">
						<div class="form-head-left"></div>
						<div class="form-row-small">
							<input type="submit" name="submit" value="Submit"
								class="submit-button" style="padding: 0 12px 7px" />
						</div>
					</div>
				</div>
			</div>
		</form:form>


	</tiles:putAttribute>
	<div class="error"></div>
		
		<script type="text/javascript"
			src="${path.js('jquery/jquery.all.min.js')}"></script>
		<script type="text/javascript"
			src="${path.js('jquery/new.jqGrid.min.js')}"></script>
		<script type="text/javascript"
			src="${path.js('snapdeal/snapdeal.js')}"></script>
		<script type="text/javascript"
			src="${path.js('timepicker/jquery-ui-calendar.min.js')}"></script>
	<script type="text/javascript"
		src="${path.js('snapdeal/validations.js')}"></script>
	<script type="text/javascript"
		src="${path.js('jquery/ajaxfileupload.js')}"></script>
	<script>
		function uploadimage(type) {
			var file = 'uploadedFile';
			if (type == "desktop") {
				file = 'uploadedFile';
			} else {
				file = 'uploadedMobileFile';
			}
			$.ajaxFileUpload({
				url : Snapdeal.getStaticPath("")
						+ "/admin/marketing/affiliateSubscription/upload",
				fileElementId : file,
				dataType : 'json',
				success : function(data, status) {
					if (data.status == 'fail') {
						$('#result').html(data.items['result']);
						$('#status').val(data.status);
						return false;
					}
					$('#result').html(data.items['result']);
					$('#status').val(data.status);
					if (type == "desktop") {
						$('#actualImageUrl').val(data.items['contentPath']);
					} else {
						$('#actualMobileImageUrl').val(
								data.items['contentPath']);
					}
					return true;
				},
				error : function(data, status, e) {
					alert(e);
					return false;
				}
			});

		}
		function populateFields(value) {
			$('#offerId').val(value);
			if (value != "") {
				var ajaxUrl = "/json/get/subscriptionOffer/" + value;
				$
						.ajax({
							url : ajaxUrl,
							type : "GET",
							dataType : 'json',
							success : function(data) {
								$('#offerName')
										.val(
												data.affiliateSubscriptionOfferSRO.offerName);
								$('#offerText')
										.val(
												data.affiliateSubscriptionOfferSRO.offerText);
								$('#actualImageUrl')
										.val(
												data.affiliateSubscriptionOfferSRO.image);
								$('#actualMobileImageUrl')
										.val(
												data.affiliateSubscriptionOfferSRO.mobileImage);
								$('#promoCode')
										.val(
												data.affiliateSubscriptionOfferSRO.promoCode);
								$('#enabled')
										.attr(
												'checked',
												data.affiliateSubscriptionOfferSRO.enabled);
								$('#mobileEnabled')
										.attr(
												'checked',
												data.affiliateSubscriptionOfferSRO.mobileEnabled);
							}
						});
				//$('#offerName').val()
				$('#nameRow').hide();
			} else {
				$('#offerName').val("");
				$('#nameRow').show();
			}
		}

		function checkOfferNames() {
			var offerNames = document.getElementById('offerNames');
			var check = true;

			for ( var i = 0; i < offerNames.length; i++) {
				if ((offerNames.options[i].text.toLowerCase() == $('#offerName')
						.val().toLowerCase())) {
					check = false;
					break;
				} else {
					check = true;
					continue;
				}
			}

			return check;
		}

		function validatePromoCode() {
			$('.promoError').remove();
			if ($('#promoCode').val() != "") {
				var ajaxUrl = "/json/verify/promoByCode/"
						+ $('#promoCode').val();
				$.ajax({
					url : ajaxUrl,
					type : "GET",
					dataType : 'json',
					contentType : 'application/json; charset=utf-8',
					success : function(data) {
						if (data.sr.s == "fail") {
							$('#promoCode').after(
									'<div class="promoError">' + data.sr.m
											+ '</div>')
						}
					}
				});
			}
		}

		function validateForm(theForm) {
			$('.error').remove();
			//document.getElementById('errorCheck').style	.display = 'none';
			validator = new Snapdeal.validator();
			var isValid = validator.validate();

			if (!checkOfferNames() && $('#offerNames').val() == "") {
				isValid = false;
				$('#offerName')
						.after(
								'<div class="error">This Name already exists. Please provide a different name</div>');
			}
			return isValid;
		}
	</script>
</tiles:insertTemplate>