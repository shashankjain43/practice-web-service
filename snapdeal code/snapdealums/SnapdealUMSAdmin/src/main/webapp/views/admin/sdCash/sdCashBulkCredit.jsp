


<%@ include file="/tld_includes.jsp"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>



<!-- <link rel="stylesheet" type="text/css" -->
<%-- href="${path.css('jquery-ui-1.8.17.custom.css')}" /> --%>


<%-- <tiles:insertTemplate template="/views/layout/loyaltyadminBase.jsp"> --%>
<%-- <tiles:putAttribute name="title" --%>
<%-- value="Upload SNAPBOX eligibility sheet"></tiles:putAttribute> --%>
<%-- <tiles:putAttribute name="body"> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="${path.css('style.css')}" rel="stylesheet" type="text/css" />



<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload SDCash Credit Excel Sheet</title>
</head>
<body>
	<%@ include file="/views/common/home_LogOut_header.jsp"%>

	<div>
		<tiles:insertAttribute name="header"
			defaultValue="/views/layout/header_old.jsp" />
	</div>
	<div>
		<tiles:insertAttribute name="navigation"
			defaultValue="/views/layout/navigation_old.jsp" />
	</div>
	<div id="internal-content">
		<div class="content-bdr">
			<div class="graybox">
				<div class="gray-cont">
					<div class="wrapper2">
						<div class="head-lable">
							Upload <font color="red">SDCash Credit</font> <font color="blue">Excel</font>
							Sheet
						</div>
						<div>Please NOTE:</div>
						<br>
						<div>1. The file should be a .XLS and contain no more than
							10,000 entries.</div>
						<div>2. First column corresponds to "emailID". Second column
							with SD cash. Third with expiry days(Optional. If present, has to
							be a number). Fourth column to order ID.</div>
						<div>3. Processing the file could take a few minutes. Please
							do not resubmit the file again, or else, credit would happen
							TWICE!</div>
                      <div>4. An email will be sent to you with the result.</div>
					</div>
					<script type='text/javascript'>
						function Empty(element, AlertMessage) {
							if (element.value.trim() == "") {
								alert(AlertMessage);
								element.focus();
								return false;
							}
							return true;
						}
					</script>

<script>
function myFunction() {
    document.getElementById("process").style.color = "grey";
}
</script>



					<form:form action="processSDCashBulkCreditUpload" method="post"
						enctype="multipart/form-data" name="form1">
						<br>
						<table>
							<tr>


								<td><p align="left">


										<font color="blue" size="2">Template Name:</font>
									</p></td>
								<td><p align="left">
										<select name="templateName" id="list">
										<c:forEach items="${emailTemplateNameList}" var="item">
										<option value="${item}">${item}</option>
										</c:forEach>

									</p></td>

							</tr>
							<br>
							<tr>

								<td><p align="left">
										<font color="blue" size="2"> Activity:</font>
									</p></td>
								<td><p align="left">



										<select name="sdWalletActivityTypeId">

											<c:forEach items="${activityTypeList}" var="element">


												<option value="${element.id}">${element.name}</option>

											</c:forEach>

										</select>
									</p></td>
							</tr>
						</table>
						<br>
						<br>
						<tr>


							<td><input name="uploadedFile" type="file" /></td>
							</tr>
							<tr><td>
							 <input type="submit" name="SubmitButton" id="process"
								onClick="this.form.submit(); this.value='Processing...'; form1.SubmitButton.disabled=true; myFunction();"

								value="Process" class="button" /></td>
						</tr>
					</form:form>

				</div>

				<c:if
					test="${sdCashUploadResponse!=null && sdCashUploadResponse.successful==false}">
					<h2>

						<font color="black">Processing failed. Any executed
							transactions were rolled back. SD cash has not been credited to
							any user.</font>
					</h2>
				</c:if>
				
				<c:if	
					test="${sdCashUploadResponse.validationErrors!=null}">
					
					<c:forEach
								items="${sdCashUploadResponse.validationErrors}"
								var="errorCodeAndMessage">
							<h2>	
								<font color="red">${errorCodeAndMessage.code}: </font>
								<font color="black">${errorCodeAndMessage.message}</font>
								
								</h2>
								</c:forEach>
				
					



				</c:if>



				<c:if
					test="${sdCashUploadResponse!=null && sdCashUploadResponse.successful==true}">
					<h2>
						<font color="black">Processing was successful.</font>
					</h2>
				</c:if>

				<c:if test="${sdCashUploadResponse!=null}">

					<c:if
						test="${fn:length(sdCashUploadResponse.unProcessedSDUserEmailIDMap) gt 0}">

						<h2>
							<font color="black">Unprocessed email IDs</font>
						</h2>

						<table width="100%" border="1" cellspacing="0" cellpadding="0">
							<c:forEach
								items="${sdCashUploadResponse.unProcessedSDUserEmailIDMap}"
								var="errorAndCorrespondingEmailsEntry">

								<table>
									<tr>
										<td><p align="left">

												<font color="blue" size=3>${errorAndCorrespondingEmailsEntry.key.msg}</font>
											</p></td>
									</tr>

									<c:forEach items="${errorAndCorrespondingEmailsEntry.value}"
										var="unprocessedEmail">
										<c:set var="newCount" value="${newCount+1}" scope="page" />

										<tr>
											<td>
												<p align="left">
													${newCount}. <font color="black">${unprocessedEmail}</font>
												</p>
											</td>
										</tr>

									</c:forEach>
								</table>
								<br>
							</c:forEach>
						</table>
					

					</c:if>
				</c:if>

			</div>
		</div>
	</div>





</body>
</html>

