<%@ include file="/tld_includes.jsp"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- <link rel="stylesheet" type="text/css" -->
<%-- 	href="${path.css('jquery-ui-1.8.17.custom.css')}" /> --%>
<%@ include file="/views/common/home_LogOut_header.jsp" %> 

<%-- <tiles:insertTemplate template="/views/layout/loyaltyadminBase.jsp"> --%>
<%-- 	<tiles:putAttribute name="title" --%>
<%-- 		value="Upload SNAPBOX eligibility sheet"></tiles:putAttribute> --%>
<%-- 	<tiles:putAttribute name="body"> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>



<link href="${path.css('style.css')}" rel="stylesheet" type="text/css" />
<link href="${path.css('admin.css')}" rel="stylesheet" type="text/css" />
<link href="${path.css('adminv1.css')}" rel="stylesheet" type="text/css" />


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload SNAPBOX eligibility sheet</title>
</head>
<body> 

	<div id="internal-content">
		<%-- 		<div class="error">${message}</div> --%>
		<div class="content-bdr">
			<div class="graybox">
				<div class="gray-cont">
					<div class="wrapper2">
						<div class="head-lable">
							Upload <font color="red">SNAPBOX</font> <font color="blue">eligibility</font>
							sheet
						</div>
						<div>Please NOTE:</div><br>
						<div>1. The file should be a .CSV and contain no more than
							1,00,000 entries.</div>
						<div>2. Processing the file could take a few minutes. Please
							do not refresh the page.</div>
					</div>

<script>
function myFunction() {
    document.getElementById("process").style.color = "grey";
}
</script>
					<form:form action="uploadLoyaltyFile" method="post"
						enctype="multipart/form-data">
						<br>

						<input name="uploadedFile" type="file" />
						<br><br>
						<input type="submit" id="process"
							onClick="this.form.submit(); this.disabled=true; this.value='Processing...'; myFunction();"
							class="button" />
						<br>


					</form:form>

				</div>
				<c:if test="${loyaltyUploadRs != null}">
					<font color="blue">Uploaded File: </font> ${uploadedFile}
					<br>
					<c:if test="${not empty loyaltyUploadRs.validationErrors}">
						<font color="red">Errors:</font>
						<c:set var="errorCount" value="0" scope="page" />

						<ul>
							<c:forEach var="validationError"
								items="${loyaltyUploadRs.validationErrors}">
								<c:set var="errorCount" value="${errorCount + 1}" scope="page" />
								<%-- 					<c:out value="${existingLoyaltyEmailsCount}" /> --%>
								<font color="red">${errorCount}.
									${validationError.message}</font>
								<br><br>
							</c:forEach>
						</ul>

					</c:if>

					<br>
					<br>
					<c:if test="${empty loyaltyUploadRs.validationErrors}">
								Total unique entries: <font color="purple">${loyaltyUploadRs.totalEntriesCount}</font>
							<br>
							
								Email IDs made eligible: <font color="green">${loyaltyUploadRs.madeEligibleCount}</font>
							<br>
							
								Existent email IDs: <font color="blue">${loyaltyUploadRs.existentEmailCount}</font>
							<br>
								Invalid entries: 	<font color="red	">${loyaltyUploadRs.invalidCount}</font>

						<br><br>

						<%-- 	<h3>Message : ${message}</h3> --%>
						<%-- 	<h3>Email IDs made eligible : ${loyaltyUploadRs.madeEligibleCount}</h3> --%>
						<%-- 	<h3>Existent email IDs : <font color="red">${loyaltyUploadRs.existentEmailCount}</font></h3> --%>
						<%-- 	<h3>Invalid entries : <font color="red">${loyaltyUploadRs.invalidCount}</font></h3> --%>
						<%-- 	<h3>Raw entries : <font color="blue">${loyaltyUploadRs.rawEntriesCount}</font></h3> --%>


						<c:if test="${not empty loyaltyUploadRs.existingLoyaltyEmails}">
							<c:set var="existingLoyaltyEmailsCount" value="0" scope="page" />
							<font color="blue">Pre-existing entries:</font>
							<br>
							<table>



								<!-- 					<ul> -->

								<c:forEach var="existingLoyaltyEmail"
									items="${loyaltyUploadRs.existingLoyaltyEmails}">
									<c:set var="existingLoyaltyEmailsCount"
										value="${existingLoyaltyEmailsCount + 1}" scope="page" />
									<%-- 					<c:out value="${existingLoyaltyEmailsCount}" /> --%>
									<tr>
										<td>${existingLoyaltyEmailsCount}.</td>
										<td>${existingLoyaltyEmail}</td>
									</tr>
									<!-- 							<br> -->
								</c:forEach>


								<!-- 					</ul> -->
							</table>
							<br>
						</c:if>


						<c:if test="${not empty loyaltyUploadRs.invalidEmailIDs}">
							<c:set var="invalidEmailIDsCount" value="0" scope="page" />
							<font color="red">Invalid entries:</font>
							<br><br>	
							<table>

								<ul>
									<c:forEach var="invalidEmailID"
										items="${loyaltyUploadRs.invalidEmailIDs}">
										<c:set var="invalidEmailIDsCount"
											value="${invalidEmailIDsCount + 1}" scope="page" />
										<tr>
											<td>${invalidEmailIDsCount}.</td>
											<td>${invalidEmailID}</td>
										</tr>

										<%-- 									${invalidEmailIDsCount}.  ${invalidEmailID}<br> --%>
										<%-- 					${invalidEmailIDsCount}<li>${invalidEmailID}</li> --%>
									</c:forEach>
								</ul>
							</table>
							<br>
						</c:if>


					</c:if>
				</c:if>

			</div>
		</div>
	</div>


	<!-- 	<h2> -->
	<!-- 		Upload <font color="red">SNAPBOX</font> <font color="blue">eligibility</font> -->
	<!-- 		sheet -->
	<!-- 	</h2> -->
	<!-- 	<br> Please NOTE: -->
	<!-- 	<br> -->
	<!-- 	<br> 1. The file should be a .CSV and contain no more than -->
	<!-- 	1,00,000 entries. -->
	<!-- 	<br> 2. Processing the file could take a few minutes. Please do -->
	<!-- 	not refresh the page. -->
	<!-- 	<br> -->
	<!-- 	<br> -->




	<%-- 	<form:form action="uploadLoyaltyFile" method="post" --%>
	<%-- 		enctype="multipart/form-data"> --%>
	<!-- 		<br> -->
	<!-- 		<table> -->

	<!-- 			<tr> -->
	<!-- 				<td><input name="uploadedFile" type="file" /></td> -->
	<!-- 			</tr> -->
	<!-- 			<tr> -->
	<!-- 				<td><input type="submit" -->
	<!-- 					onClick="this.form.submit(); this.disabled=true; this.value='Processing...'; " -->
	<!-- 					class="button" /></td> -->
	<!-- 			</tr> -->
	<!-- 		</table> -->
	<!-- 		<br> -->


	<%-- 	</form:form> --%>
	<br>
	<br>



	<%-- 	<form:form method="post" action="uploadLoyaltyFile"> --%>

	<!-- 		<table> -->
	<!-- 			<tr> -->
	<%-- 				<td><form:label path="loyaltyProgram">Loyalty program</form:label></td> --%>
	<%-- 				<td><form:input path="loyaltyProgram" /></td> --%>
	<!-- 			</tr> -->
	<!-- 			<tr> -->
	<%-- 				<td><form:label path="loyaltyStatus">Loyalty status</form:label></td> --%>
	<%-- 				<td><form:input path="loyaltyStatus" /></td> --%>
	<!-- 			</tr> -->

	<!-- 			<tr> -->
	<!-- 				<td colspan="2"><input type="submit" value="UploadFile" /></td> -->
	<!-- 			</tr> -->
	<!-- 		</table> -->

	<%-- 	</form:form> --%>
</body>
</html>

<%-- 	</tiles:putAttribute> --%>
