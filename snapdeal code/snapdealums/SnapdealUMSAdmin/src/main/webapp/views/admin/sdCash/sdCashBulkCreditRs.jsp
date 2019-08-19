<%@ include file="/tld_includes.jsp"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>



<!-- <link rel="stylesheet" type="text/css" -->
<%-- 	href="${path.css('jquery-ui-1.8.17.custom.css')}" /> --%>


<%-- <tiles:insertTemplate template="/views/layout/loyaltyadminBase.jsp"> --%>
<%-- 	<tiles:putAttribute name="title" --%>
<%-- 		value="Upload SNAPBOX eligibility sheet"></tiles:putAttribute> --%>
<%-- 	<tiles:putAttribute name="body"> --%>
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
						<div>2. The Processing of file will start from 1st row and
							1st column.</div>
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
function validateUploadedFile() {
    var x = document.forms["input"]["uploadedFile"].value;
    var y = document.forms["input"]["pwd"].value;

    if (x == null || x == "") {
     alert("File not Uploaded..Upload an excel file.");

        return false;
    }
    
    if (x != *.xsl) {
        alert("Wrong file extension... Please upload an excel-.xls file.");

           return false;
       }
}
</script>





					<form:form action="processSDCashBulkCreditUpload" method="post"
						enctype="multipart/form-data">
						<br>
						<table>
							<tr>

								
								<td><p align="left"><font color="blue" size="2"> Credit Reason:</font></p></td>
								<td><p align="left"><input type="text" name="reason" id="txtBox"></p></td>
								
							</tr>
							<br>
							<tr>
								
								<td><p align="left"><font color="blue" size="2"> Activity:</font></p></td>
								<td><p align="left"><select name="SDWalletActivityTypeId">
									
										<c:forEach items="${ActivityTypeList}" var="element">


											<option value="${element.id}">${element.name}</option>

										</c:forEach>


								</select></p></td>
							</tr>
						</table>
						<br>
						<br>
						<tr>
							<td><input name="uploadedFile" type="file" /> <br> <br>
								<input type="submit"
								onClick="return Empty(document.getElementById('txtBox'), 'Credit Reason is empty, Please Enter a Reason'); return validateUploadedFile(); this.form.submit(); this.disabled=true; this.value='Processing...';"
								value="Process" class="button" /> <br></td>
						</tr>
					</form:form>

				</div>
			</div>
	</div>
	</div>
<c:if test="${sdCashUploadResponse!=null}">

<div id="internal-content">

		<div class="content-bdr">
<div class="graybox">
				<div class="gray-cont">
					<div class="wrapper2">
						<div class="head-lable">
							<font color="red">SDCash Bulk Credit</font> <font color="blue">Response</font>
						</div>
						</div>
						</div>

	</div>
</div>
	</div>					
							<c:choose>
								<c:when test="${sdCashUploadResponse.activityId==false}">
									<tr>
										<td><center>
												<h2>
													<font color="purple" size="4">Please select Activity
														Type</font>
												</h2>
											</center></td>
									</tr>
								</c:when>

								<c:when test="${sdCashUploadResponse.emptyFileContent==true}">
									<tr>
										<td><center>
												<h2>
													<font color="purple" size="4">No File Selected..
														Please Upload the excel file</font>
												</h2>
											</center></td>
									</tr>
								</c:when>
								<c:when test="${sdCashUploadResponse.falseExcelExtension==true}">
									<tr>
										<td>
											<center>
												<h2>
													<font color="purple" size="4"> Incorrect
														Extension:Upload only excel file.</font>
												</h2>
											</center>
										</td>
									</tr>
								</c:when>
								<c:when test="${sdCashUploadResponse.nullRawExcelFile==true}">
									<tr>
										<td><center>
												<h2>
													<font color="purple" size="4"> The uploaded file is empty.</font>
												</h2>
											</center></td>
									</tr>
								</c:when>
								<c:when
									test="${sdCashUploadResponse.nullSDCashUploadRequest==true }">
									<tr>
										<td>
											<center>
												<h2>
													<font color="purple" size="4"> Could not process
														your request.Please fill up the form again</font>
												</h2>
											</center>
										</td>
									</tr>
								</c:when>
								<c:when test="${sdCashUploadResponse.rowCount==true}">
									<tr>
										<td><center>
												<h2>
													<font color="purple" size="4">Uploaded File contains more than 10000 entries.. Credited SDCash for top 10000 entries... Maximum allowed entries in a File: 10,000
													</font>
												</h2>
											</center></td>
									</tr>
								</c:when>
                             	
								<c:otherwise>
								<table border="0" cellpadding="0" cellspacing="0">
                       
									<tr>
										<td width="120%" colspan="2"><font color="black" size="4">Uploaded File: </font><font color="purple" size="4">
											${uploadedFile}</font> <br></td>
									</tr>
									
									
								
<%-- 									<c:if test="${fn:length(sdCashUploadResponse.unProcessedSDUserEmailMap) not gt 0}"> --%>
									
<!-- 									<h2> -->
<!-- 									<font color="purple">All the emailIds processed Successfully..</font> -->
<!-- 									</h2> -->
<%-- 									</c:if> --%>
									<c:if test="${fn:length(sdCashUploadResponse.unProcessedSDUserEmailIDMap) gt 0}">
							<tr>
							<td width="120%" colspan="2">
										<h2>
										 	<font color="black">Errors corresponding to
												unprocessed email Ids</font>
										</h2>
								</td>
								</tr>
								
								
								
										<c:forEach
											items="${sdCashUploadResponse.unProcessedSDUserEmailIDMap}"
											var="errorAndCorrespondingEmailsEntry" varStatus="outer">
											<c:set var="count" value="${inner.count}" scope="page"/>
										
										
											<tr>
												<td width="120%" colspan="2">
												<div class="gray-cont">
					<div class="wrapper2"><font color="blue">${errorAndCorrespondingEmailsEntry.key.msg}</font></div></div></td>
											</tr>
											 
											<c:forEach items="${errorAndCorrespondingEmailsEntry.value}" var="unprocessedEmail" varStatus="inner">
											
											<tr>
												<td>${outer.index+count+1}). <font color="black">${unprocessedEmail}</font></td>
											</tr>
								
											</c:forEach>
											
										
											<br>
										</c:forEach>
				
									</c:if>
									</table>
								</c:otherwise>
							</c:choose>
							
						</c:if>
               




	

</body>
</html>
