<%@ include file="/tld_includes.jsp"%>  		
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/crudmanage.jsp">

	<tiles:putAttribute name="filter">

		<div id="filtering" class="filtering">
			<form>
				clientName : <select id="name" name="clientName">
					<option value="">--Select--</option>
					<c:forEach items="${clientNames}" var="name">
						<option value="${name}">${name}</option>
					</c:forEach>
				</select> clientType : <select id="type" name="clientType">
					<option value="">--Select--</option>
					<c:forEach items="${clientTypes}" var="type">
						<option value="${type}">${type}</option>
					</c:forEach>
				</select> merchant : <select id="merchants" name="merchant">
					<option value="">--Select--</option>
					<c:forEach items="${merchants}" var="merchant">
						<option value="${merchant}">${merchant}</option>
					</c:forEach>
				</select> clientStatus : <select id="status" name="clientStatus">
					<option value="">--Select--</option>
					<c:forEach items="${clientStatuses}" var="status">
						<option value="${status}">${status}</option>
					</c:forEach>
				</select> clientPlatform : <select id="platform" name="clientPlatform">
					<option value="">--Select--</option>
					<c:forEach items="${clientPlatforms}" var="platform">
						<option value="${platform}">${platform}</option>
					</c:forEach>
				</select>
				<button type="submit" id="LoadRecordsButton">Load records</button>
			</form>
		</div>
	</tiles:putAttribute>


	<tiles:putAttribute name="title" value="Client Manager" />
	<tiles:putAttribute name="head">
		<meta name="description" content="${metaDescription}"></meta>
		<meta name="keywords" content="${metaKeywords}"></meta>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">

		<script type="text/javascript">
					var clientNames = '${clientNames}' ;
                    var cachedCityOptions = null;
                    console.log("Loading from custom function...");
                    $(document).ready(function() {
                        $('#Container').jtable({
                            title: 'Client Manager',
                            paging: true,
                            pageSize: 10,
                            sorting: true,
                            multiSorting: true,
                            defaultSorting: 'Name ASC',

                            formCreated: function(event, data) {
                                $('#jtable-create-form #Edit-clientKey').closest('.jtable-input-field-container').hide();
                                $('#jtable-create-form #Edit-createdTime').closest('.jtable-input-field-container').hide();
                                $('#jtable-create-form #Edit-updatedTime').closest('.jtable-input-field-container').hide();
                                $('#jtable-create-form #Edit-clientStatus').closest('.jtable-input-field-container').hide();
                                $('#jtable-create-form #Edit-clientId').closest('.jtable-input-field-container').hide();
                                $("#Edit-activeInactive").closest('.jtable-input-field-container').hide();
                                $("#Edit-whitelistApis").closest('.jtable-input-field-container').hide();
                                $("#Edit-regenerateClientKey").closest('.jtable-input-field-container').hide();

                                $('input').each(function() {
                                    $(this).css('width', '100%');
                                });
                            },

                            actions: {
                                listAction: function(postData, jtParams) {
                                    console.log("Loading from custom listAction function ...");
                                    return $.Deferred(function($dfd) {
                                        $.ajax({
                                            url: '/admin/client/list?jtStartIndex=' + jtParams.jtStartIndex + '&jtPageSize=' + jtParams.jtPageSize + '&jtSorting=' + jtParams.jtSorting,
                                            type: 'GET',
                                            dataType: 'json',
                                            data: postData,
                                            success: function(data) {
                                                $dfd.resolve(data);
                                                if (data['TotalRecordCount'] != 0 && clientNames == '')
                                                {
                                                	location.reload();
                                                }
                                            },
                                            error: function() {
                                                $dfd.reject();
                                            }
                                        });
                                    });
                                },

                                createAction: function(postData) {
                                    console.log("calling createaction function...");
                                    return $.Deferred(function($dfd) {
                                        $.ajax({
                                            url: '/admin/client/create',
                                            type: 'POST',
                                            dataType: 'json',
                                            data: postData,
                                            success: function(data) {
                                                $dfd.resolve(data);
                                            },
                                            error: function() {
                                                $dfd.reject();
                                            }
                                        });
                                    });
                                },

                                /** deleteAction: function (postData) {
                                      console.log("deleting from custom function...");
                                      return $.Deferred(function ($dfd) {
                                          $.ajax({
                                              url: '/Demo/DeleteStudent',
                                              type: 'POST',
                                              dataType: 'json',
                                              data: postData,
                                              success: function (data) {
                                                  $dfd.resolve(data);
                                              },
                                              error: function () {
                                                  $dfd.reject();
                                              }
                                          });
                                      });
                                  }, **/

                                /**    updateAction: function(postData) {
                                        console.log("calling update function...");
                                        return $.Deferred(function ($dfd) {
                                            $.ajax({
                                                url: '/admin/configuration/update',
                                                type: 'POST',
                                                dataType: 'json',
                                                data: postData,
                                                success: function (data) {
                                                    $dfd.resolve(data);
                                                },
                                                error: function () {
                                                    $dfd.reject();
                                                }
                                            });
                                        });
                                    } **/
                            },
                            fields: {
                                clientId: {
                                    title: 'ClientId',
                                    width: '6%',
                                    //type: 'hidden',
                                },
                                clientName: {
                                    title: 'Client Name',
                                    width: '10%',
                                },
                                merchant: {
                                    title: 'Merchant',
                                    width: '6%',
                                    options: {
                                        'SNAPDEAL': 'SNAPDEAL',
                                        'FREECHARGE': 'FREECHARGE',
                                        'ONECHECK': 'ONECHECK'
                                    }
                                },
                                clientType: {
                                    title: 'Client Type',
                                    width: '10%',
                                    options: {
                                        'NON_USER_FACING': 'NON USER FACING',
                                        'USER_FACING': 'USER FACING'
                                    }
                                },
                                clientKey: {
                                    title: 'Client Key',
                                    width: '10%',
                                },
                                clientStatus: {
                                    title: 'Client Status',
                                    width: '10%',
                                    options: {
                                        'ACTIVE': 'ACTIVE',
                                        'INACTIVE': 'INACTIVE'
                                    }
                                },
                                clientPlatform: {
                                    title: 'Client Platform',
                                    width: '10%',
                                    options: {
                                        'WEB': 'WEB',
                                        'WAP': 'WAP',
                                        'APP': 'APP'
                                    }
                                },
                                createdTime: {
                                    title: 'Created',
                                    width: '10%',
                                },
                                updatedTime: {
                                    title: 'Updated',
                                    width: '10%',
                                },
                                imsInternalAlias: { 
                                    title: 'Internal Alias',
                                    width: '10%',
                                },
                                
                                activeInactive: {
                                    title: '',
                                    width: '0.3%',
                                    display: function(data) {
                                        var $but = $('<button title="disable/Enable Client" class="jtable-command-button jtable-enable-disable-command-button" >disable/Enable Client</button>');
                                        $but.click(function() {
                                            var url = null;
                                            var message = null
                                            if (data.record.clientStatus == 'ACTIVE') {
                                                url = '/admin/client/' + data.record.clientId + '/status/inactive';
                                                message = 'Are you sure you want to make client ' + data.record.clientId + ' inactive';
                                            } else if (data.record.clientStatus == 'INACTIVE') {
                                                url = '/admin/client/' + data.record.clientId + '/status/active';
                                                message = 'Are you sure you want to make client ' + data.record.clientId + ' active';
                                            }

                                            var $dfd = $.Deferred();
                                            if (confirm(message)) {

                                                $.ajax({
                                                    url: url,
                                                    type: 'GET',
                                                    dataType: 'json',
                                                    data: null,
                                                    success: function(data) {
                                                        $dfd.resolve(data);
                                                        $('#Container').jtable('load');
                                                    },
                                                    error: function() {
                                                        $dfd.reject();
                                                    }
                                                });
                                            }
                                        });
                                        return $but;
                                    }
                                },


                                regenerateClientKey: {
                                    title: '',
                                    width: '0.3%',
                                    display: function(data) {
                                        var $but = $('<button title="regenerate Client Key" class="jtable-command-button jtable-enable-disable-command-button" >regenerate Client Key</button>');
                                        $but.click(function() {
                                            var $dfd = $.Deferred();
                                            if (confirm('Are you sure you want to regenerate client key?')) {
                                                $.ajax({
                                                    url: '/admin/client/' + data.record.clientId + '/clientkey',
                                                    type: 'GET',
                                                    dataType: 'json',
                                                    data: null,
                                                    success: function(data) {
                                                        $dfd.resolve(data);
                                                        $('#Container').jtable('load');
                                                    },
                                                    error: function() {
                                                        $dfd.reject();
                                                    }
                                                });
                                            }
                                        });
                                        return $but;
                                    }
                                },
                                whitelistApis: { 
                                    title: 'API Whitelisting',
                                    width: '10%',
				    				display: function(data) {
                                        var $but = $('<button title="View/Update Client API Whitelisting" class="jtable-command-button jtable-enable-disable-command-button" >API Whitelisting</button>');
                                        $but.click(function() {
											var clientId = data.record.clientId;
                                            location = '/admin/manage/whitelist_api/'+clientId
                                        });
                                        return $but;
                                    }
                                }
                            }
                        });
                        //Load student list from server
                        $('#Container').jtable('load');


                        setTimeout(function() {
                            $.each($('td'), function() {
                                $(this).attr('title', $(this).text()).tooltip();
                                //$(this).attr('title',$(this).text());
                            });

                        }, 600);


                        $('#LoadRecordsButton').click(function(e) {
                            e.preventDefault();
                            $('#Container').jtable('load', {
                                clientName: $('#name').val(),
                                clientType: $('#type').val(),
                                merchant: $('#merchants').val(),
                                clientStatus: $('#status').val(),
                                clientPlatform: $('#platform').val(),
                            });
                        });

                    });
                </script>
	</tiles:putAttribute>
</tiles:insertTemplate>