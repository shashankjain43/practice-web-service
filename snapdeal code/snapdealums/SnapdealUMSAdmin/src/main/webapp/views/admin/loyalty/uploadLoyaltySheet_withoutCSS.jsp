<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload SNAPBOX eligibility sheet</title>
</head>
<body>




	<h2>
		Upload <font color="red">SNAPBOX</font> <font color="blue">eligibility</font>
		sheet
	</h2>
	<br> Please NOTE:
	<br>
	<br> 1. The file should be a .CSV and contain no more than
	1,00,000 entries.
	<br> 2. Processing the file could take a few minutes. Please do
	not refresh the page.
	<br>
	<br>




	<form:form action="uploadLoyaltyFile" method="post"
		enctype="multipart/form-data">
		<br>
		<table>

			<tr>
				<td><input name="uploadedFile" type="file" /></td>
			</tr>
			<tr>
				<td><input type="submit"
					onClick="this.form.submit(); this.disabled=true; this.value='Processing...'; " /></td>
			</tr>
		</table>
		<br>


	</form:form>
	<br>
	<br>


	<c:if test="${loyaltyUploadRs != null}">
		<h3>Uploaded File: ${uploadedFile}</h3>

		<c:if test="${not empty loyaltyUploadRs.validationErrors}">
			<c:set var="errorCount" value="0" scope="page" />
			<h3>
				<font color="red">Errors</font>
			</h3>
			<ul>
				<c:forEach var="validationError"
					items="${loyaltyUploadRs.validationErrors}">
					<c:set var="errorCount" value="${errorCount + 1}" scope="page" />
					<%-- 					<c:out value="${existingLoyaltyEmailsCount}" /> --%>
					<font color="red">${errorCount}. ${validationError.message}</font>
					<br>
				</c:forEach>
			</ul>

		</c:if>


		<c:if test="${empty loyaltyUploadRs.validationErrors}">

			<table>
				<tr>
					<td>Total entries:</td>
					<td><font color="purple">${loyaltyUploadRs.totalEntriesCount}</font></td>
				</tr>
				<tr>
					<td>Email IDs made eligible:</td>
					<td><font color="green">${loyaltyUploadRs.madeEligibleCount}</font></td>
				</tr>
				<tr>
					<td>Existent email IDs:</td>
					<td><font color="blue">${loyaltyUploadRs.existentEmailCount}</font></td>
				<tr>
					<td>Invalid entries:</td>
					<td><font color="red	">${loyaltyUploadRs.invalidCount}</font></td>
				</tr>

			</table>

			<%-- 	<h3>Message : ${message}</h3> --%>
			<%-- 	<h3>Email IDs made eligible : ${loyaltyUploadRs.madeEligibleCount}</h3> --%>
			<%-- 	<h3>Existent email IDs : <font color="red">${loyaltyUploadRs.existentEmailCount}</font></h3> --%>
			<%-- 	<h3>Invalid entries : <font color="red">${loyaltyUploadRs.invalidCount}</font></h3> --%>
			<%-- 	<h3>Raw entries : <font color="blue">${loyaltyUploadRs.rawEntriesCount}</font></h3> --%>


			<c:if test="${not empty loyaltyUploadRs.existingLoyaltyEmails}">
				<c:set var="existingLoyaltyEmailsCount" value="0" scope="page" />
				<table>
					<h3>
						<font color="blue">Pre-existing entries:</font>
					</h3>
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
			</c:if>


			<c:if test="${not empty loyaltyUploadRs.invalidEmailIDs}">
				<c:set var="invalidEmailIDsCount" value="0" scope="page" />
				<table>
					<h3>
						<font color="red">Invalid entries:</font>
					</h3>
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
			</c:if>


		</c:if>
	</c:if>

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