<%@ include file="/tld_includes.jsp"%>


<tiles:insertTemplate template="/views/layout/adminBasev3.jsp">

	<tiles:putAttribute name="title"
		value="Order Management Vendor Dashboard - Vendor Reshipment" />
	<tiles:putAttribute name="head">



		<style>
.customer-search-head {
	color: #000;
	font-size: 10px;
	padding-bottom: 5px;
	border-bottom: 1px solid #999999;
	margin: 5px 0 0px 0;
	overflow: hidden;
}

.customer-search-head .show {
	float: right;
}

.customer-search-head a,.customer-search-head span {
	color: #000;
	display: block;
	text-decoration: none;
	float: left
}

.text_filter,.select_filter {
	width: 78px;
	font-size: 11px !important;
}
</style>
	</tiles:putAttribute>

	<tiles:putAttribute name="deferredScript">
		<script type="text/javascript"
			src="${path.js('jquery/jquery.dataTables.js')}"></script>
		<script type="text/javascript"
			src="${path.js('jquery/jquery.dataTables.columnFilter.js')}"></script>
		<script type="text/javascript"
			src="${path.js('jquery/jquery.dataTables.columnFilter.js')}"></script>

		<link href="${path.css('datepickr.css')}" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="${path.js('datepickr.min.js')}"></script>

		<script>
        
        $(document).ready(function() {
        	
        	new datepickr('startDate', {
				'dateFormat': 'Y-m-d'
			});
        	new datepickr('endDate', {
				'dateFormat': 'Y-m-d'
			});
        	  $('#searchButton').click(function(){
           		 if(validateDates()){
           			searchVendor(); 
           		 }
        		  
           	  });
        	  
        	if("${suborders}" != ''){
        		$('#searchStringSuborderCode').val("${suborders}");
        		searchVendor();
        	}
        	
        	 
        	 function searchVendor(){
                 $('#back').hide();
                 $('#return').hide();
                 $('#packageDetails').hide();
                 $('#history').hide();
                 $('#orderItemDetails').hide();
               $('#packageDetail').hide();
               $('#shippingDetail').hide();
               
                 $('#shippingPackageTable').remove();
                 $('#packageDetailTable').remove();
                 $('#shippingPackageHistoryTable').remove();
                 $('#shippingOrderItemTable').remove();
                 $('#shippingDetailTable').remove();
                 
                 
                 var startDate = $('#startDate').val();
                 var endDate = $('#endDate').val();
                 startDate = startDate.replace("/","-");
                 startDate = startDate.replace("/","-");
                 startDate = startDate.replace("/","-");
                 endDate = endDate.replace("/","-");
                 endDate = endDate.replace("/","-");
                 endDate = endDate.replace("/","-"); 
                 
                 var ajaxUrl = "/xhr/get/suborderInfo/reship" +"/"+ startDate +"/"+endDate+"/"+$('input[@name="radio_vouchers"]:checked').val();
                
                 $('#ajax-loader-icon').show();
                 $('#availablilityMessage')
                         .html('');
                $ .ajax({url : ajaxUrl,type : "GET",dataType : 'json',success : function(data) {
                	if(data.length==0){
                		var aHtml="";
                		alert("No Data found .Kindly try after some time");
                	}else{

                		//aHtml = "<div  ";
                		
                		aHtml = "<table border='0'  cellspacing='0' cellpadding='0' class='tbl-blue dataTable' id='shippingPackageTable' style=''>";
                        aHtml += "<thead>";
                        aHtml += "<tr>";
                        aHtml += "<th></th>";
                        aHtml += "<th>Search</th>";
                        aHtml += "<th>Search</th>";
                        aHtml += "<th>Search</th>";
                        aHtml += "<th>Search</th>";
                        aHtml += "<th>Search</th>";
                        aHtml += "<th>Search</th>";
                        aHtml += "<th>Search</th>";
                        aHtml += "<th>Search</th>";
                        aHtml += "<th>Search</th>";
                        aHtml += "<th>Search</th>";
                        aHtml += "<th>Search</th>";
                        aHtml += "<th></th>";
                        aHtml += "<th></th>";
                        aHtml += "</tr>";
                        aHtml += "<tr>";
                        aHtml += "<th>#</th>";
                        aHtml += "<th>Suborder</th>";
                        aHtml += "<th>Provider</th>";
                        aHtml += "<th>Method</th>";
                        aHtml += "<th>AWB</th>";
                        aHtml += "<th>Status</th>";

                       // aHtml += "<th>Created</th>";
                        aHtml += "<th>City</th>";
                        aHtml += "<th>Pincode</th>";
                      //  aHtml += "<th>SKU Code</th>";
                        aHtml += "<th>Package Type</th>";
                        aHtml += "<th>Paid Amount</th>";
                        aHtml += "<th>Reference Code</th>";
                        aHtml += "<th>Vendors</th>";
                        aHtml += "<th>SLA breached</th>";
                        aHtml += "<th>&nbsp; Action &nbsp;</th>";
                        
                        aHtml += "</tr>";
                        aHtml += "</thead>";
                        aHtml += "<tbody>";
                        var count = 0;
                        var SuborderDetailVendorPanelDTO = /* Snapdeal.Utils.fixArray */(data[0]) ;
                        for ( var i = 0; i < data.length; i++) {
                        	count += 1;
                        	var suborderDetailVendorPanelDTO = data[i].suborderDetailVendorPanelDTO;
                      		aHtml += "<tr>";
                            aHtml += "<td> "+count+"</td>";
                            aHtml += "<td> "+toString(suborderDetailVendorPanelDTO.subOrderCode)+"</td>";
                            aHtml += "<td> "+toString(suborderDetailVendorPanelDTO.provider)+"</td>";
                            aHtml += "<td> "+toString(suborderDetailVendorPanelDTO.shippingMethod)+"</td>";
                            aHtml += "<td> "+toString(suborderDetailVendorPanelDTO.awb)+"</td>";
                            aHtml += "<td> "+toString(suborderDetailVendorPanelDTO.status)+"</td>";
                            aHtml += "<td> "+toString(suborderDetailVendorPanelDTO.city)+"</td>";
                            aHtml += "<td> "+toString(suborderDetailVendorPanelDTO.pincode)+"</td>";
                            aHtml += "<td> "+suborderDetailVendorPanelDTO.packageType+"</td>";
                            aHtml += "<td> "+toString(suborderDetailVendorPanelDTO.totalPaidAmount)+"</td>";
                            aHtml += "<td> "+toString(suborderDetailVendorPanelDTO.referenceCode)+"</td>";
                            aHtml += "<td> "+toString(suborderDetailVendorPanelDTO.vendor)+"</td>";
                            aHtml += "<td> "+(suborderDetailVendorPanelDTO.boolSlaBreached==false ? "false":"true")+"</td>";
                            if(suborderDetailVendorPanelDTO.boolCancelable == true){
                            	aHtml +='<td><a style="text-decoration:underline;color:#029ECB;cursor:pointer;cursor:hand;" onclick="changeVendor('+suborderDetailVendorPanelDTO.subOrderCode+','+suborderDetailVendorPanelDTO.orderCode+')">Change Vendor</a> &nbsp; &nbsp; <a style="text-decoration:underline;color:#029ECB;cursor:pointer;cursor:hand;" onclick="cancelVendor('+suborderDetailVendorPanelDTO.subOrderCode+','+suborderDetailVendorPanelDTO.orderCode+')">Cancel</a></td>';  
                            }else{
                            	aHtml += "<td> &nbsp; </td>";	
                            	/* aHtml +="<td> "+"<button type="+"button"+" name=" +"txtRow1"
                       		 +"id="+"txtRow1"+" >"+"change vendor"+"</button>"+" </td>"; */
                            	/* <a
								onclick="searchOnSubCat('${subCategories.id}', '${matchingCategories.vertical}')">${subCategories.name}<span
									class="cat-count">(${subCategories.noOfResults})</span> </a> */
                            	/* aHtml +='<td><button type="button" name="txtRow1" id=${suborderDetailVendorPanelDTO.subOrderCode} style="font-size:40%">Change Vendor</button></td>'; */
                            }
	                        
                            aHtml+="</tr>";
                         }
                         aHtml += "</tbody>";
                	}
                	$('#ajax-loader-icon').hide();
                	
                	$('#packageDetails').html(aHtml);
                	var spString = $('#shippingProviders').html().trim();
                    spString = spString.substring(0,spString.length -1);
                	 $('#shippingPackageTable').dataTable().columnFilter({
                		 sPlaceHolder : "head:before",aoColumns : [
                                         null,
                                         {
                                             type : "text"
                                         },
                                         {
                                             type : "text"
                                             
                                         },
                                         {
                                             type : "text"
                                         },
                                         {
                                             type : "text"
                                         },
                                         {
                                             type : "text"
                                         },
                                         {
                                             type : "text"
                                         },
                                         {
                                             type : "text"
                                         },
                                         {
                                             type : "text"
                                         },
                                         {
                                             type : "text"
                                         },
                                         {
                                             type : "text"
                                         },
                                         {
                                             type : "text"
                                         },
                                         null,null
                                         ]
                             });
                	 
                	 $('#shippingPackageTable').attr("style", {width: ""}); 
                     $('#packageDetails').show();
                	$('#back').show();
                	$('#ajax-loader-icon').hide();
                    $('#availablilityMessage').html('');	
                }});        		 
        	 }
        	 $('#save').click(function(){
        		 var suborderCode = $('#suborderCode').val();
        		 var vendorCode = $('#vendorCode').val();
        		 if( !$("input[@name='vendor']:checked").val() || suborderCode == "" || vendorCode == ""){
        			 alert("Vendor need to be selected or kindly reject this suborder entry");
           		 }else{
        			 var ajaxUrl = "/xhr/get/changevendor/reship/"+suborderCode+"/"+vendorCode;
            			$.ajax({
        				url:ajaxUrl, 
        				type: "GET", 
        				dataType: 'json', 
        				success: function(data) {
        					var vhtml = "";
       					if(data.sr.length == 0){
       						vhtml = ""; 
       						alert("No Data found .Kindly reject/cancel this suborder");
       					}else{
       						alert(data.sr.m);		
       					}    					
       					$("#lightboxVendor,#lightboxVendorTable").fadeOut(300);
       					searchVendor();
        			    }
        			    });
    			 
        		 }
           		 
        	 });
             $('#cancel').click(function(){
            	 $("#lightboxVendor,#lightboxVendorTable").fadeOut(300);
        	 });
        
        });
      
        
        function saveItem(itemCode) {
        	var vendorCode = $('#vendor'+itemCode).val();
        	
        	var ajaxUrl = "/admin/fulfillment/overrideVendorForItem";
        	var packagedata = {"soiCode" : itemCode, "vendorCode" : vendorCode};
			$.ajax({url:ajaxUrl, type: "POST", data: packagedata, dataType: 'json', success: function(data) {
				alert(data['message']);		
			}});
        }
        
        
        
        function backToPackageTable(){
    	 $('#return').hide();
	     $('#packageDetails').show();
         $('#packageDetail').hide();
         $('#shippingDetail').hide();
         $('#history').hide();
         $('#orderItemDetails').hide();
     
         $('#shippingPackageHistoryTable').remove();
         $('#shippingOrderItemTable').remove();
         $('#shippingDetailTable').remove();
         $('#packageDetailTable').remove();
        }
        
        
        function toString(data){
            if(data == undefined){
                data="";
            }
            return data;
            
        }
        function changeVendor(subOrderCode,orderCode) {
//        	$("#lightboxVendor").fadeIn(300);
			//alert('SubOrder:'+subOrderCode+' OrderCode:'+orderCode);
			var ajaxUrl = "/xhr/get/vendorInfo/"+subOrderCode;
        	var subOrderData = {"suborderCode" : subOrderCode, "orderCode" : orderCode};
        	
			$.ajax({
				url:ajaxUrl, 
				type: "GET", 
				dataType: 'json', 
				success: function(data) {
					var vhtml = "";
					if(data.length == 0){
						vhtml = ""; 
						alert("No Vendor found for this suborder.Kindly reject this entry.");
					}else{
						vhtml += "<table border='0'  cellspacing='1' cellpadding='0' class='tbl-blue dataTable' id='vendorInfoTable' width='100%'>";
						vhtml += "<thead>";
						vhtml += "<tr>";
						vhtml += "<th></th>";
						vhtml += "<th>Vendor</th>";
                        vhtml += "<th>Amount</th>";
                        vhtml += "<th>Difference</th>";
                        vhtml += "<th>Allowed</th>";
                        vhtml += "</tr>";
   						vhtml += "</thead>";
						vhtml += "<tbody>";
						for ( var i = 0; i < data.length; i++) {
                        	var vendorDetailPanelDTO  = data[i].vendorDetailPanelDTO;
                        	vhtml += "<tr>";
                        	if(vendorDetailPanelDTO.allowed == true){
                        		vhtml += "<td> "+'<input type="radio" name="vendor" onClick=saveVendor("'+vendorDetailPanelDTO.subOrderCode+'","'+vendorDetailPanelDTO.vendorCode +'")'+"></td>";	
                        	}else{
                        		vhtml += "<td> "+'<input type="radio" name="vendor"  disabled="true" onClick=saveVendor("'+vendorDetailPanelDTO.subOrderCode+'","'+vendorDetailPanelDTO.vendorCode +'")'+"></td>";
                        	}
                        	
                        	vhtml += "<td> "+vendorDetailPanelDTO.vendorName+"</td>";
                        	vhtml += "<td> "+vendorDetailPanelDTO.vendorSellingPrice+"</td>";
                        	vhtml += "<td> "+vendorDetailPanelDTO.priceDifference+"</td>";
                        	vhtml += "<td> "+(vendorDetailPanelDTO.allowed == true ? "Yes":"No")+"</td>";
                        	vhtml += "</tr>";
							
						}
						vhtml += "</tbody>";
						$('#lightboxVendorData').html(vhtml);
						$('#vendorInfoTable').attr("width", "100%");
						$("#lightboxVendor,#lightboxVendorTable").fadeIn(300);
					}
					
					
					
			    }
			});
		}
        function cancelVendor(subOrderCode,orderCode) {
        	$('#ajax-loader-icon').show();
   			var ajaxUrl = "/xhr/cancelvendor/reship/"+subOrderCode;
        	var subOrderData = {"suborderCode" : subOrderCode, "orderCode" : orderCode};
        	
			$.ajax({
				url:ajaxUrl, 
				type: "GET", 
				dataType: 'json', 
				success: function(data) {
					$('#ajax-loader-icon').hide();
					var vhtml = "";
					if(data.length == 0){
						vhtml = ""; 
						alert("No Data found .Kindly try after some time..");
					}else{
						alert(data.sr.m); 
					}
					searchVendor();
											
			    }
			});
		}
        
        function saveVendor(subOrderCode,vendorCode){
        	$('#suborderCode').val(subOrderCode);
        	$('#vendorCode').val(vendorCode);
        }
        function validateDates(){
        	var doSubmit =true;
        	if (!$("input[@name='radio_vouchers']:checked").val()) {
        	   alert('Kindly select Pending or Rejected options');
        	   return false;
        	}
        	
        	if($('#startDate').val()==''){
        	alert("Please select a valid start date.")
        	doSubmit =false;
        	} else {
        	if($('#endDate').val()==''){
        	alert("Please select a valid end date.")
        	doSubmit =false;
        	}
        	}
        	var startDate = new Date($('#startDate').val());
        	var endDate = new Date($('#endDate').val());
        	if(startDate.getTime()>endDate.getTime()){
        	alert("Start Date cannot be greater than End Date");
        	doSubmit =false;
        	}else if(startDate.getTime()==endDate.getTime()){
        	alert("Start Date cannot be equal to End Date");
        	doSubmit =false;
        	}
        	
        		return doSubmit;	
        	
        	
        	} 
        
        </script>

	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<div id="internal-content">
			<div class="content-bdr">

				<div id="comment"></div>
				<div class="head-lable">Replacement/Reshipment</div>

				<div class="cod-dash-menu">
					<ul style="float: none;">
						<li><a href="${path.http}/admin/vrp/vendorOverride">Vendor
								Override</a></li>
						<%-- <li><a href="${path.http}/admin/vrp/pendingFulfillment">Pending
								Fulfillment</a>
						</li>
						 --%>
						<li><a href="${path.http}/admin/vrp/updateSuccessful">Update
								as Successful</a></li>
						<li><a href="${path.http}/admin/vrp/reshipment"
							class="active">Reshipment/Replacement</a></li>
					</ul>
				</div>
				<div class="cod-outer">
					<div>${message}</div>
					<div>
						<table width="100%" cellspacing="0" cellpadding="0">
							<tbody>
								<tr>
									<td colspan="4"><Strong>Show</Strong>
									</td>
								</tr>
								<tr>
									<td width="10%" style="padding: 15px 15px 15px 0;">Pending</td>
									<td width="10%" style="padding-right: 35px;"><input
										id="radio_products" type="radio" value="open"
										name="radio_vouchers">
									</td>
									<td width="10%" style="padding: 15px 15px 15px 0;">Rejected</td>
									<td width="70%" style="padding-right: 15px;"><input
										id="radio_vouchers" type="radio" value="rej"
										name="radio_vouchers">
									</td>
								</tr>
								<tr>
									<td width="10%" style="padding: 15px 15px 15px 0;">Start
										Date</td>
									<td><input id="startDate"
										class="input-fld-small-calendar hasDatepicker" type="text">
									</td>
									<td width="10%" style="padding: 15px 15px 5px 49px;">End
										Date</td>
									<td><input id="endDate"
										class="input-fld-small-calendar hasDatepicker" type="text">
									</td>

								</tr>

								<tr>
								<tr>
							</tbody>
						</table>
						<div class="left" style='display: none;'>
							Order Id: <input name="orderCode" type="text"
								id="searchStringOrderCode" class="input-text"
								style="width: 120px" /> or Suborder Code: <input type="text"
								id="searchStringSuborderCode" class="input-text"
								style="width: 120px" />

						</div>
						<div id="ajax-loader-icon" align="center" style="display: none">
							<img src="${path.resources('img/ajax-loader.gif')}">
						</div>


						<div id="showSearchButton" style="padding: 10px 0">
							<input type="submit" value="fetch" class="button"
								id="searchButton" />
						</div>

						<div id='packageDetails' style='display: none;'
							class='customer-search-head'>Shipping Package Detail Table</div>
						<div id='packageDetail' style='display: none;'
							class='customer-search-head'>Shipping Package Details</div>
						<div id='orderItemDetails' style='display: none;'
							class='customer-search-head'>Shipping Order Item Details</div>
						<div id='shippingDetail' style='display: none;'
							class='customer-search-head'>Shipping Details</div>

						<div id='history' style='display: none;'
							class='customer-search-head'>History Details</div>
						<div style="display: none" id="shippingProviders">
							<c:forEach
								items="${cache.getCacheByName('shippingProvidersCache').shippingProviders}"
								var="provider" varStatus="ctr">${provider.name}<c:if
									test="${fn:length(provider.name) > 0}">,</c:if>
							</c:forEach>
						</div>
						<div id="return" style="padding: 5px 0; display: none;"
							class="backtotop">
							<a href='#' onclick='return backToPackageTable();'>Back To
								Shipping Package Detail Table</a>
						</div>
						<div id="back" style="padding: 5px 0; display: none;"
							class="backtotop">
							<a href="${path.http}/admin/vrp/reshipment" class="active">New
								Search</a>
						</div>


					</div>
				</div>
			</div>
		</div>
		<div id="suborderCode" value="" style="display: none"></div>
		<div id="vendorCode" value="" style="display: none"></div>
	</tiles:putAttribute>
</tiles:insertTemplate>
