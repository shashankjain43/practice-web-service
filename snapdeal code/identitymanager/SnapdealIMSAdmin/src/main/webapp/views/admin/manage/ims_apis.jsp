<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/crudmanage.jsp">

	<tiles:putAttribute name="title" value="IMS APIs Manager" />

	<tiles:putAttribute name="head">
		<meta name="description" content="${metaDescription}"></meta>
		<meta name="keywords" content="${metaKeywords}"></meta>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">

		<script type="text/javascript">
                    var appConfigType = '${appConfigType}';
                    var cachedCityOptions = null;
                    console.log("Loading from custom function...");

                    $(document).ready(function() {
                        $('#Container').jtable({
                            title: 'IMS APIs Manager',
                            paging: true,
                            pageSize: 10,
                            sorting: true,
                            multiSorting: true,
                            defaultSorting: 'configKey ASC',

                            formCreated: function(event, data) {
                                $('#Edit-customDelete').hide();

                                $('input').each(function() {
                                    $(this).css('width', '100%');
                                });
                            },

                            actions: {
                                listAction: function(postData, jtParams) {
                                    console.log("Loading from custom listAction function ...");
                                    return $.Deferred(function($dfd) {
                                        $.ajax({
                                            url: '/admin/ims_apis/list?jtStartIndex=' + jtParams.jtStartIndex + '&jtPageSize=' + jtParams.jtPageSize + '&jtSorting=' + jtParams.jtSorting,
                                            type: 'GET',
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

                                createAction: function(postData) {
                                    console.log("calling createaction function...");
                                    return $.Deferred(function($dfd) {
                                        $.ajax({
                                            url: '/admin/ims_apis/create',
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

                                updateAction: function(postData) {
                                    console.log("calling update function...");
                                    return $.Deferred(function($dfd) {
                                        $.ajax({
                                            url: '/admin/ims_apis/update',
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
                                }
                            },


                            fields: {
                            	id: {
                                    title: 'clientId',
                                    type: 'hidden',
                                },
                                apiMethod: {
                                    title: 'API Method',
                                    width: '10%',
                                },
                                apiURI: {
                                    title: 'API uri',
                                    width: '30%',
                                },
                                alias: {
                                    width: '20%',
                                    title: 'API Alias',
                                }
                            }
                        });
                        //Load student list from server
                        $('#Container').jtable('load');


                        setTimeout(function() {
                            $.each($('td'), function() {
                                //if($(this).text()==="admin-ui")
                                $(this).attr('title', $(this).text()).tooltip();
                                //$(this).attr('title',$(this).text());
                            });
                        }, 600);

                        $('#LoadRecordsButton').click(function(e) {
                            e.preventDefault();
                            $('#Container').jtable('load');
                        });

                    });
                </script>
	</tiles:putAttribute>
</tiles:insertTemplate>