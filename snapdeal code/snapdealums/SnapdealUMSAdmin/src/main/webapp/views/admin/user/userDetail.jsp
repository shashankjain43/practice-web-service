<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<link href="${path.css('style.css')}" rel="stylesheet" type="text/css" />
<link href="${path.css('admin.css')}" rel="stylesheet" type="text/css" />
<link href="${path.css('adminv1.css')}" rel="stylesheet" type="text/css" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<h2>
	<title>Get User Details By Email</title>
</h2>
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
							<font color="blue">Get User Details</font> <font color="blue">By
								Email</font>

						</div>

						<br> <br>
						<form:form name="frm" method="post"
							action="getUserProfileByEmailOrId" enctype="multipart/form-data">
							<table>
								<tr>


									<td><font color="black"> Enter User Email or User
											Id </font> <input type="text" name="value"> <input
										type="submit"
										onClick="this.form.submit(); this.disabled=true; this.value='Processing...'; "
										class="button" value="Fetch" /></td>

								</tr>
							</table>

						</form:form>
					</div>
					<br> <br> <br>

					<c:if
						test="${userDetailResponse!=null && userDetailResponse.successful==false && userDetailResponse.validationErrors!=null}">
						<c:forEach items="${userDetailResponse.validationErrors}"
							var="errorCodeAndMessage">
							<h2>
								<font color="red">${errorCodeAndMessage.code}: </font> <font
									color="black">${errorCodeAndMessage.message}</font>

							</h2>
						</c:forEach>


						<font color="black">Processing failed. . User does not
							exist .</font>
						</h2>
					</c:if>

					<c:if test="${userDetailResponse!=null}">
						<br>
						<br>

						<c:if test="${user != null}">

							<p align="left">

								<font color="purple" size="3">Details</font>
							</p>
							<p align="left">
							<table width="100%" border="1">
								<tr>


									<td><p align="left">
											<font color="purple"> Id </font>
										</p></td>
									<td><p align="left">${user.id}</p></td>
								</tr>
								<tr>


									<td><p align="left">
											<font color="purple"> Email </font>
										</p></td>
									<td><p align="left">${user.email}</p></td>
								</tr>

								<tr>


									<td><p align="left">
											<font color="purple"> EmailVerified </font>
										</p></td>
									<td><p align="left">${user.emailVerified}</p></td>
								</tr>

								<tr>


									<td><p align="left">
											<font color="purple"> FirstName </font>
										</p></td>
									<td><p align="left">${user.firstName}</p></td>
								</tr>

								<tr>


									<td><p align="left">
											<font color="purple"> LastName </font>
										</p></td>
									<td><p align="left">${user.lastName}</p></td>
								</tr>

								<tr>


									<td><p align="left">
											<font color="purple"> Gender </font>
										</p></td>
									<td><p align="left">${user.gender}</p></td>
								</tr>

								<tr>


									<td><p align="left">
											<font color="purple"> Birthday </font>
										</p></td>
									<td><p align="left">${user.birthday}</p></td>
								</tr>

								<tr>


									<td><p align="left">
											<font color="purple"> Mobile </font>
										</p></td>
									<td><p align="left">${user.mobile}</p></td>
								</tr>

								<tr>


									<td><p align="left">
											<font color="purple"> Created On </font>
										</p></td>
									<td><p align="left">${user.created}</p></td>
								</tr>

								<tr>


									<td><p align="left">
											<font color="purple"> Modified On </font>
										</p></td>
									<td><p align="left">${user.modified}</p></td>
								</tr>

								<tr>
									<td><p align="left">
											<font color="purple"> Roles </font>
										</p></td>
									<td>
										<table width="100%" border="1">

											<c:forEach var="item" items="${user.userRoles}">

												<tr>
													<td><p align="left">${item.role}</p></td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</table>
						</c:if>


						<br>

						<c:if
							test="${userAddress != null && not empty userAddress && userAddress.size()>0}">
							<br>
							<br>


							<p align="left">
								<font color="purple" size="3">Addresses: </font>
							</p>

							<table width="100%" border="1">

								<tr>
									<td><p align="left">Id</p></td>
									<td><p align="left">Name</p></td>
									<td><p align="left">Address</p></td>
									<td><p align="left">City</p></td>
									<td><p align="left">State</p></td>
									<td><p align="left">Country</p></td>
									<td><p align="left">PostalCode</p></td>
									<td><p align="left">Mobile</p></td>
									<td><p align="left">LandlineNo.</p></td>
									<td><p align="left">Created</p></td>
									<td><p align="left">AddressTag</p></td>
									<td><p align="left">IsDefault</p></td>
								</tr>
								<c:forEach var="item" items="${userAddress}">
									<tr>
										<td><p align="left">${item.id}</p></td>
										<td><align="left">${item.name}
											</p></td>
										<td><align="left">${item.address1} ,
											${item.address2}
											</p></td>
										<td><align="left">${item.city}
											</p></td>
										<td><align="left">${item.state}
											</p></td>
										<td><align="left">${item.country}
											</p></td>
										<td><align="left">${item.postalCode}
											</p></td>
										<td><align="left">${item.mobile}
											</p></td>
										<td><align="left">${item.landline}
											</p></td>
										<td><align="left">${item.created}
											</p></td>
										<td><align="left">${item.addressTag}
											</p></td>
										<td><align="left">${item.isDefault}
											</p></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>

						<br>

						<c:if test="${userLoyaltyStatus != null}">

							<br>
							<br>
							<table width="100%" border="1">
								<tr>


									<td><p align="left">
											<font color="purple"> LoyaltyStatus: </font>
										</p></td>
									<td><p align="left">${userLoyaltyStatus.value}</p></td>
								</tr>



								<tr>
									<td><p align="left">
											<font color="purple"> LoyaltyProgram: </font>
										</p></td>
									<td><p align="left">${userLoyaltyProgram.value}</p></td>
								</tr>
							</table>
						</c:if>

						<br>

						<c:if test="${userAvailableSdCash != null}">

							<br>
							<br>


							<p align="left">
								<font color="purple" size="3">Available Balance in
									SDWallet: </font><font color="black" size="3">Rs.${userAvailableSdCash}</font>
							</p>



							</tr>


							</table>
						</c:if>

						<c:if test="${userSdWalletList != null}">

							<br>
							<br>


							<p align="left">
								<font color="purple" size="3">Last five transactions in
									sdWallet </font>
							</p>
							<table width="100%" border="1">
								<tr>

									<td><p align="left">Amount</p></td>
									<td><p align="left">Expiry</p></td>
									<td><p align="left">ActivityId</p></td>
									<td><p align="left">Created</p></td>
									<td><p align="left">Updated</p></td>
									<td><p align="left">OriginalAmount</p></td>
									<td><p align="left">ReferenceId</p></td>
								</tr>
								<c:forEach var="item" items="${userSdWalletList}">
									<tr>
										<td><p align="left">${item.amount}</p></td>
										<td><p align="left">${item.expiry}</p></td>
										<td><p align="left">${item.activityId}</p></td>
										<td><p align="left">${item.created}</p></td>
										<td><p align="left">${item.updated}</p></td>
										<td><p align="left">${item.originalAmount}</p></td>
										<td><p align="left">${item.referenceId}</p></td>

									</tr>

								</c:forEach>
							</table>
						</c:if>

						<c:if test="${userSdWalletHistoryList != null}">

							<br>
							<br>

							<p align="left">
								<font color="purple" size="3">Last five transactions in
									sdWalletHistory </font>
							</p>
							<table width="100%" border="1">
								<tr>

									<td><p align="left">Amount</p></td>
									<td><p align="left">Expiry</p></td>
									<td><p align="left">ActivityId</p></td>
									<td><p align="left">Created</p></td>
									<td><p align="left">Updated</p></td>
									<td><p align="left">TransactionId</p></td>
									<td><p align="left">ReferenceId</p></td>
								</tr>
								<c:forEach var="item" items="${userSdWalletHistoryList}">
									<tr>
										<td><p align="left">${item.amount}</p></td>
										<td><p align="left">${item.expiry}</p></td>
										<td><p align="left">${item.activityId}</p></td>
										<td><p align="left">${item.created}</p></td>
										<td><p align="left">${item.updated}</p></td>
										<td><p align="left">${item.transactionId}</p></td>
										<td><p align="left">${item.referenceId}</p></td>

									</tr>

								</c:forEach>

							</table>

						</c:if>

						<br>

						<c:if test="${userEmailSubscriber != null}">

							<br>
							<br>


							<p align="left">
								<font color="purple" size="3"> EmailSubscriptionDetail: </font>
							</p>
							<table width="100%" border="1">

								<tr>
									<td><p align="left">Id</p></td>
									<td><p align="left">NormalizedEmail</p></td>
									<td><p align="left">ZoneId</p></td>
									<td><p align="left">Subscribed</p></td>
									<td><p align="left">Updated</p></td>
									<td><p align="left">Created</p></td>
									<td><p align="left">SubscriptipnPage</p></td>
									<td><p align="left">Customer</p></td>
									<td><p align="left">ReasonUnsubscription</p></td>
									<td><p align="left">SuggestionUnsubscription</p></td>
									<td><p align="left">ChannelCode</p></td>
									<td><p align="left">Active</p></td>
									<td><p align="left">Preference</p></td>
								</tr>
								<tr>
									<td><p align="left">${userEmailSubscriber.id}</p></td>
									<td><p align="left">${userEmailSubscriber.normalizedEmail}</p></td>
									<td><p align="left">${userEmailSubscriber.zoneId}</p></td>
									<td><p align="left">${userEmailSubscriber.subscribed}</p></td>
									<td><p align="left">${userEmailSubscriber.updated}</p></td>
									<td><p align="left">${userEmailSubscriber.created}</p></td>
									<td><p align="left">${userEmailSubscriber.subscriptionPage}</p></td>
									<td><p align="left">${userEmailSubscriber.customer}</p></td>
									<td><p align="left">${userEmailSubscriber.reasonUnsubscription}</p></td>
									<td><p align="left">${userEmailSubscriber.suggestionUnsubscription}</p></td>
									<td><p align="left">${userEmailSubscriber.channelCode}</p></td>
									<td><p align="left">${userEmailSubscriber.active}</p></td>
									<td><p align="left">${userEmailSubscriber.preference}</p></td>
								</tr>
							</table>
						</c:if>

						</align>
					</c:if>
				</div>
			</div>
		</div>
	</div>





</body>
</html>
