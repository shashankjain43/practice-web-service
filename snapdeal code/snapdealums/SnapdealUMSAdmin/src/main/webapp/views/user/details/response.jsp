<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<link href="${path.css('style.css')}" rel="stylesheet" type="text/css" />  

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

		
				<align="left">
					
							 <font color="purple" size="3"></font> <font color="blue">Details</font>
				</align>
<align="left">

<c:if test="${user != null}">

<table width="100%" border="1">
<tr>


<td><font color="purple"> Id : </font></td><td>${user.id} </td></tr>
<tr>


<td><font color="purple"> Email: </font></td><td>${user.email} </td></tr>

<tr>


<td><font color="purple"> EmailVerified: </font></td><td>${user.emailVerified} </td></tr>

<tr>


<td><font color="purple">FirstName: </font></td><td>${user.firstName} </td></tr>

<tr>


<td><font color="purple">LastName: </font></td><td>${user.lastName} </td></tr>

<tr>


<td><font color="purple"> Gender: </font></td><td>${user.gender} </td></tr>

<tr>


<td><font color="purple"> Birthday: </font></td><td>${user.birthday} </td></tr>

<tr>


<td><font color="purple"> Mobile: </font></td><td>${user.mobile} </td></tr>

<tr>


<td><font color="purple"> Created On: </font></td><td>${user.created} </td></tr>

<tr>


<td><font color="purple"> Modified On: </font></td><td>${user.modified} </td></tr>

<tr>
<td><font color="purple"> Roles: </font></td>
<td>
<table width="100%" border="1" >

<c:forEach var="item" items="${user.userRoles}">

<tr><td>${item.role} </td></tr>
</c:forEach>
</table>
</td>
</tr>
</table>
</c:if>

<br><br>


<c:if test="${userAddress != null|| not empty userAddress||userAddress.size()>0}">



<align="left"><font color="purple" size="3">Addresses: </font>></align>

<table width="100%" border="1">

<tr>
<td>Id  </td>
<td>Name </td>
<td>Address </td>
<td>City </td>
<td>State </td>
<td>Country </td>
<td>PostalCode </td>
<td>Mobile </td>
<td>LandlineNo. </td>
<td>Created </td>
<td>AddressTag </td>
<td>IsDefault </td>
</tr>
<c:forEach var="item" items="${userAddress}">
<tr>
 <td>${item.id}</td> 
<td>${item.name}</td>
<td>${item.address1} , ${item.address2}</td>
<td>${item.city} </td>
<td>${item.state}</td>
<td>${item.country}</td>
<td>${item.postalCode}</td>
<td>${item.mobile} </td>
<td>${item.landline}</td>
<td>${item.created}</td>
<td>${item.addressTag} </td>
<td>${item.isDefault} </td>
</tr>
</c:forEach>
</table>
</c:if>

<br><br>

<c:if test="${userLoyaltyStatus != null}">

<br>
<table width="100%" border="1" >
<tr>


<td><font color="purple"> LoyaltyStatus: </font></td><td>${userLoyaltyStatus.value} </td></tr>



<tr>
<td><font color="purple"> LoyaltyProgram: </font></td><td>${userLoyaltyProgram.value} </td></tr>
</table>
</c:if>

<br><br>

<c:if test="${userAvailableSdCash != null}">

<br>


<align="left"><font color="purple" size="3">Available Balance in SDWallet: </font></align>>
<table width="100%" border="1" >
<tr>
<td>${userAvailableSdCash.availableAmount}</td>

</tr>


</table>
</c:if>







<br><br>

<c:if test="${userEmailSubscriber != null}">

<br>


<align="left"><font color="purple" size="3"> EmailSubscriptionDetail: </font></align>
<table width="100%" border="1">

<tr><td><align="left">Id </align></td>
<td><align="left">NormalizedEmail </align></td>
<td><align="left">ZoneId </align></td>
<td><align="left">Subscribed</align> </td>
<td><align="left">Updated </align></td>
<td>Created </td>
<td>SubscriptipnPage </td>
<td>Customer </td>
<td>ReasonUnsubscription </td>
<td>SuggestionUnsubscription </td>
<td>ChannelCode </td>
<td>Active </td>
<td>Preference </td>
</tr><tr> <td>${userEmailSubscriber.id}</td> 
 <td>${userEmailSubscriber.normalizedEmail}</td> 
<td>${userEmailSubscriber.zoneId}</td> 
 <td>${userEmailSubscriber.subscribed}</td>
 <td>${userEmailSubscriber.updated}</td> 
 <td>${userEmailSubscriber.created}</td> 
<td>${userEmailSubscriber.subscriptionPage}</td>
 <td>${userEmailSubscriber.customer} </td> 
 <td>${userEmailSubscriber.reasonUnsubscription}</td> 
<td>${userEmailSubscriber.suggestionUnsubscription}</td>
<td>${userEmailSubscriber.channelCode}</td> 
<td>${userEmailSubscriber.active}</td> 
<td>${userEmailSubscriber.preference}</td> 
</tr>
</table>
</c:if>

</align>

</body>
</html>