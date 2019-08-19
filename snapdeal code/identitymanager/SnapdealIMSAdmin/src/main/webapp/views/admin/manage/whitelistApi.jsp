<%@ include file="/tld_includes.jsp"%>  		
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/crudmanage.jsp">


	<tiles:putAttribute name="title" value="API Whitelisting Manager" />
	<tiles:putAttribute name="head">
		<meta name="description" content="${metaDescription}"></meta>
		<meta name="keywords" content="${metaKeywords}"></meta>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">

		<script type="text/javascript">
					var clientId = '${clientId}' ;
                    var cachedCityOptions = null;
                    console.log("Loading from custom function...");
                    $(document).ready(function() {
                        $('#Container').jtable({
                            title: 'API Whitelisting Manager',
                            paging: true,
                            pageSize: 10,
                            sorting: true,
                            multiSorting: true,
                            defaultSorting: 'apiUri ASC',

                            actions: {
                                listAction: function(postData, jtParams) {
                                    console.log("Loading from custom listAction function ...");
                                    return $.Deferred(function($dfd) {
                                        $.ajax({
                                            url: '/admin/whitelist_api/'+clientId+'/get_whitelist_apis_status?jtStartIndex=' + jtParams.jtStartIndex + '&jtPageSize=' + jtParams.jtPageSize + '&jtSorting=' + jtParams.jtSorting,
                                            type: 'GET',
                                            dataType: 'json',
                                            success: function(data) {
                                                $dfd.resolve(data);
                                                if (data['TotalRecordCount'] != 0 && clientId == '')
                                                {
                                                	location.reload();
                                                }
                                            },
                                            error: function() {
                                                $dfd.reject();
                                            }
                                        });
                                    });
                                }
                            },
                            fields: {
                            	clientId: {
                                    title: 'clientId',
                                    type: 'hidden',
                                },
                                apiId: {
                                    title: 'clientId',
                                    type: 'hidden',
                                    display:function(data){
                                    	return data.record.apiDetails.id;
                                    }
                                },
                                apiMethod: {
                                    title: 'API Method',
                                    width: '10%',
                                    display:function(data){
                                    	return data.record.apiDetails.apiMethod;
                                    }
                                },
                                apiUri: {
                                    title: 'API uri',
                                    width: '30%',
                                    display:function(data){
                                    	return data.record.apiDetails.apiURI;
                                    }
                                },
                                apiAlias: {
                                    width: '20%',
                                    title: 'API Alias',
                                    display:function(data){
                                    	return data.record.apiDetails.alias;
                                    }
                                },
                                isAllowed: {
                                    title: 'Is Allowed',
                                    width: '10%',
                                    display:function(data){
                                    	if(data.record.allowed){
                                    		return "Allowed";
                                    	}
                                    	return "Not Allowed";
                                    }
                                    
                                },
                                allowRestrict: {
                                    title: 'Toggle State',
                                    width: '20%',
                                    display: function(data) {
                                    	var $allowBut = $('<button title="Allow client to use this API" class="jtable-command-button jtable-allow-command-button" >Allow</button>');
                                    	var $restrictBut = $('<button title="Restrict client to use this API" class="jtable-command-button jtable-restrict-command-button" >Restrict</button>');
                                    	
                                    	var $but;
                                    	if (data.record.allowed) {
                                    		$but = $restrictBut;
                                    	}else{
                                    		$but = $allowBut;
                                    	}
                                    	
                                    	$but.click(function() {
                                            var url = null;
                                            var message = null
                                            if (data.record.allowed) {
                                                url = '/admin/whitelist_api/update/restrict';
                                                message = 'Are you sure you want to restrict client ' + data.record.clientId + ' to use this API ?';
                                            } else {
                                                url = '/admin/whitelist_api/update/allow';
                                                message = 'Are you sure you want to allow client ' + data.record.clientId + ' to use this API ?';
                                            }
                                            
                                            var whitelistApiDetails = {
                                            		clientId : data.record.clientId,
                                            		imsApiId : data.record.apiDetails.id
                                            }

                                            var $dfd = $.Deferred();
                                            if (confirm(message)) {

                                                $.ajax({
                                                    url: url,
                                                    type: 'POST',
                                                    dataType: 'json',
                                                    data: whitelistApiDetails,
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

                    });
                </script>
	</tiles:putAttribute>
</tiles:insertTemplate>