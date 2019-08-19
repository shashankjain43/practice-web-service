<%@ include file="/tld_includes.jsp"%>
<tiles:insertTemplate template="/views/layout/adminBasev3.jsp">
	<tiles:putAttribute name="title" value="Vendor Dashboard -Vendor Pending" />
	<tiles:putAttribute name="head">
	<script type="text/javascript" src="${path.js('jquery/jquery.all.min.js')}"></script>
	<script type="text/javascript" src="${path.js('snapdeal/calender.js')}"></script>
		<style>
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
			
		<script>
			$(document).ready(function() {
				$("#radio_vouchers").attr('checked', false);
				$("#radio_products").attr('checked', false);
				$('#parent_categories_div').hide();
				
				function vendorFormatter(cellValue, options, rowObject){
					var html = "<select id='" +rowObject.id+ "'>";
					var length = cellValue.length;
					for(var i =0;i<length;i++){
						var selected = '';
						if(cellValue[i].code==rowObject.productVendorCode){
							selected = 'selected';
						}
						html += "<option value='" + cellValue[i].code + "' " + selected + ">" + cellValue[i].name +"</option>";
					}
					html += "</select>";
    		        return html;
				}
				
				function vendorUnFormatter(cellValue, options, rowObject){
					var id = options.rowId;
					var fld = $("#" + id + " option:selected").val();					
					return fld;
				}
				
				function dateOnlyFmatter(cellValue, options, rowObject){
					var now = new Date(cellValue);
					return $.fmatter.util.DateFormat(undefined, now, 'Y-m-d h:i:s A', $.jgrid.formatter.date);
				}
				
				var datePick = function(elem){
					   jQuery(elem).datepicker();
					}
				
				var updateGroupOpText = function ($form) {
			        //$('select.opsel option[value="AND"]', $form[0]).text('My ADD');
			        $('select.opsel option[value="OR"]', $form[0]).hide();
			        $('select.opsel', $form[0]).trigger('change');
			    }
				
				function buildQueryStringForSearch(postData){
					var searchString = "<b>Search is ON. Reload Grid to Reset Search.<br>";
					
					return searchString;
				}
				
				var ajaxUrl = '${path.http}/admin/fulfillment/vendorPending/fetch?shippingDispatchCategoryCode=-1&packageType=0';
				$('#pending_packages_list').jqGrid({
				   url : ajaxUrl,
				   datatype: 'json',
				   mtype: 'GET',
				      colNames:['ID','Vendor Code','Code','OrderCode','SubOrder Code','Product','Selling Price','Product Sold Date','Shipping City','Shipping State','Pincode','Shipping Method','Vendor'],
				      colModel:[
					   {name:'id',index:'id', width:50,editable:false,hidden:true},
					   {name:'productVendorCode',index:'productVendorCode', width:50,editable:false,hidden:true},
					   {name:'code',index:'code', width:50,editable:false},
					   {name:'orderCode',index:'orderCode', width:100,editable:false,searchoptions:{sopt:['eq','bw','cn']}},
					   {name:'suborderCode',index:'suborderCode', width:100,editable:false,searchoptions:{sopt:['eq','bw','cn']}},
				       {name:'productName',index:'productName', width:150,editable:false,searchoptions:{sopt:['eq','bw','cn']}},
				       {name:'itemPrice',index:'itemPrice', width:60,editable:false,sorttype:'number',formatter:'number',searchoptions:{sopt:['eq','lt','le','gt','ge']}},
				       {name:'created',index:'created', width:130,editable:false, sorttype:'date',formatter:dateOnlyFmatter,datefmt:'Y-m-d h:i:s A',searchoptions:{dataInit:datePick, attr:{title:'Select Date'},sopt:['eq','lt','le','gt','ge']},searchrules:{date:true}},
				       {name:'shippingDetail.city',index:'city', width:90,editable:false,searchoptions:{sopt:['eq','bw','cn'], multipleSearch : true}},
				       {name:'shippingDetail.state',index:'state', width:90,editable:false,searchoptions:{sopt:['eq','bw','cn']}},
				       {name:'shippingDetail.pincode',index:'pincode', width:80,editable:false,searchoptions:{sopt:['eq','bw','cn']}},
				       {name:'shippingMethodCode',index:'shippingMethodCode', width:100,editable:false,searchoptions:{sopt:['eq']}},
				       {name: 'vendors', index: 'vendors', width: 150, formoptions: { rowpos: 3, colpos: 3 }, editable: false, 
				    	   sortable:false, search:false, stype:'select',searchoptions:{sopt:['eq'],
			        		    dataInit: function (elem) {
			        		        $(elem).addClass('ui-state-highlight');
			        		    }} ,
			        		    formatter: vendorFormatter,unformat: vendorUnFormatter
				    	   }							           			       
				       ],
				      postData: {
				   },
				   rowNum:10,
				      rowList:[10,20,40,50,100,200,300,400,500,1000],
				      height: 'auto',
				      width: 'auto',
				      autowidth: true,
				   	  rownumbers: false,
				      pager: '#pending_packages_list_pager',
				      sortname: 'created',
				      viewrecords: true,
				      sortorder: "asc",
				      caption:"Logistic Pending Approval Package List",
				      emptyrecords: "Empty records",
				      loadonce: false,
				      gridview : true,
				      loadui : 'block',
				      altRows : true,
				      ajaxSelectOptions: { contentType: "application/json", dataType: 'json', type: "GET"},
				      multiselect: true,
				      shrinkToFit: false,
				      loadComplete: function() {
				    	  var grid = $("#pending_packages_list");
				    	  var isSearch = grid.jqGrid('getGridParam','search');
				    	  var postData = grid.jqGrid('getGridParam','postData');
				    	  if(isSearch){
				    		  $('#search_query').show();
				    		  $('#search_query').html(buildQueryStringForSearch(postData));
				    	  }else {
				    		  $('#search_query').hide();
				    	  }
				    	  
				   },
				   onSelectRow: function(id){
					   },
				      jsonReader : {
				          root: "rows",
				          page: "page",
				          total: "total",
				          records: "records",
				          repeatitems: false,
				          cell: "cell",
				          id: "id"
				      }
				  });
				$("#pending_packages_list").jqGrid('navGrid','#pending_packages_list_pager',
						{edit:false,add:false,del:false,search:true,refresh:true},
						{},{},{},
						{caption: "Search...",
						 closeAfterSearch: true,
						 closeAfterReset: true,
						 showQuery : true,
						 closeOnEscape : true,
						 multipleSearch : true,
						 onInitializeSearch: updateGroupOpText,
					     afterRedraw: updateGroupOpText
						 }
						);				
				
				
				//fetch packages for logistic approval
				$("#btnFetchProducts").click(function(){
					var shippingDispatchCategoryCode = $('#parentCategory').val();
					
					var packageType = '';
					if($("#radio_products").is(':checked')){
						packageType = $("#radio_products").val();
					}else if($("#radio_vouchers").is(':checked')){
						packageType = $("#radio_vouchers").val();
					}
					if(packageType=='' || shippingDispatchCategoryCode<0){
						alert("Both Category and Product Type are mandatory.");
					}else{
						var pending_packages_list_url = "${path.http}/admin/fulfillment/vendorPending/fetch?shippingDispatchCategoryCode="+shippingDispatchCategoryCode+"&packageType="+packageType;
						$("#pending_packages_list").jqGrid('setGridParam',{url:pending_packages_list_url,rows:10,page:1,sidx:'created',sord:'asc'}).trigger('reloadGrid'); 
					}					
				
				});
				
				//select Package Type : product/vouchers
				$("#radio_products").click(function(){
					$("#radio_vouchers").attr('checked', false);
					$('#parent_categories_div').show();
				});
				
				$("#radio_vouchers").click(function(){
					$("#radio_products").attr('checked', false);
					$('#parent_categories_div').hide();
				});		
				
							

		$('.saveAndApprove').click(function() {
							var orders='' ;
							var s = jQuery('#pending_packages_list').getGridParam('selarrrow');
							if(s){
								if(s.length>0){
									for(var i=0; i<s.length;i++){
										var packageId = $('#pending_packages_list').jqGrid ('getCell', s[i], 'id'); 
										var packProviderId = $('#pending_packages_list').jqGrid ('getCell', s[i], 'vendors');
										orders += packageId + ":" + packProviderId;
										if(i<s.length-1){
											orders += ";";
										}
									}
								}else{
									alert("Select atleast 1 Item.");
									return false;
								}
							}
							var orderdata = {
								"selectedOrders" : orders
							};
							var ajaxUrl = "/admin/fulfillment/allocateVendor";
							$.ajax({
										url : ajaxUrl,
										type : "POST",
										data : orderdata,
										dataType : 'json',
										success : function(
												data) {
											alert(data['message']);
											if (data['status'] == 'success') {												
												$('#statusChangeSuccess').html(data['message']).show();
												jQuery('#pending_packages_list').trigger("reloadGrid");
											} else {
												$('#statusChangeFail').html(data['message']).show();
											}
										}
									});
						});
								
				});
		</script>

	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<div id="internal-content">
			<div class="content-bdr">

				<div id="comment"></div>
				<div class="head-lable">Pending FulFillment</div>

				<div class="cod-dash-menu">
					<ul style="float: none;">
						<li><a href="${path.http}/admin/vrp/vendorOverride">Vendor Override</a></li>
						<li><a href="${path.http}/admin/vrp/pendingFulfillment" class="active">Pending Fulfillment</a></li>
						<li><a href="${path.http}/admin/vrp/updateSuccessful">Update as Successful</a></li>
						<li><a href="${path.http}/admin/vrp/reshipment">Reshipment/Replacement</a></li>
					</ul>
				</div>
				<div class="cod-outer">
					<div>${message}</div>
					<div id="verifyresp" class="cod-success" style="display: none"></div>
					<div id="cancelresp" class="cod-cancel" style="display: none"></div>
					<div>
						<c:choose>
							<c:when
								test="${showPending!= null && showPending || true}">
								<div>									 
										<table cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td colspan="4"><strong>Product Type</strong></td>
										</tr>
										<tr>
											<td width="1%" style="padding:5px 15px 5px 0; ">Products:</td><td width="1%" style="padding-right:35px;"> <input type="radio" name="radio_products" id="radio_products" value="product"/></td>
											<td width="1%" style="padding:5px 15px 5px 0; ">Vouchers:</td><td width="97%" style="padding-right:15px;"><input type="radio" name="radio_vouchers" id="radio_vouchers" value="deal"/></td>
										</tr>
										<tr>
											<td colspan="4" style="padding:5px 0">
											<div id="parent_categories_div" style="display: none">
											Select a Parent Category:
											<select id="parentCategory" name="parentCategory" style="width: 150px">
														<c:forEach items="${parentCategoriesDTO}" var="parentCategoriesDTO">
															<option value="${parentCategoriesDTO.value}">${parentCategoriesDTO.label}</option>
														</c:forEach>
											</select>
											</div>
											</td>
										</tr>
										<tr>
											<td colspan="4">
												<div id="sub_categories_div" style="display: none"></div>
											</td>
										</tr>
										<tr>
											<td colspan="4" style="padding-bottom:15px">
											<input type="submit" value="fetch" class="button" id="btnFetchProducts" />
											</td>
										</tr>
										</table>									
								</div>
								<div id="pending_packages">
								<div id="search_query" style="display: none"></div>
								<table id="pending_packages_list" width="100%"></table>
								<div id="pending_packages_list_pager"></div>
								</div>
							</c:when>
							<c:otherwise>
				Sorry. No pending shipments.	
			</c:otherwise>
						</c:choose>

						

						<div style="padding: 10px 0; text-align: center;">
							<input id="saveAndApprove" class="button saveAndApprove"
								type="button" Value="Save and Approve" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertTemplate>
