<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<tiles:insertTemplate template="/views/layout/crudmanage.jsp">

	<tiles:putAttribute name="title" value="Configuration Manager" />

	<tiles:putAttribute name="filter">
		<div id="filtering" class="filtering">
			<form>
				Type : <select id="type" name="configType">
					<option value="">--Select--</option>
					<c:forEach items="${appConfigType}" var="config">
						<option value="${config}">${config}</option>
					</c:forEach>
				</select> Key : <select id="key" name="configKey">
					<option value="">--Select--</option>
					<c:forEach items="${appConfigKeys}" var="config">
						<option value="${config}">${config}</option>
					</c:forEach>
				</select>
				<button type="submit" id="LoadRecordsButton">Load records</button>
			</form>
		</div>
	</tiles:putAttribute>

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
                            title: 'Configuration',
                            paging: true,
                            pageSize: 10,
                            sorting: true,
                            multiSorting: true,
                            defaultSorting: 'configKey ASC',

                            formCreated: function(event, data) {
                                //$('#jtable-edit-form .jtable-input #Edit-configKey').hide();
                                //$('#jtable-edit-form .jtable-input #Edit-configKey').hide();
                                //$('#jtable-edit-form #Edit-configKey').closest('.jtable-input-field-container').prop("disabled",true);
                                //$('#jtable-edit-form #Edit-configType').closest('.jtable-input-field-container').prop("disabled",true);
                                $('#jtable-edit-form #Edit-configType').prop("readonly", 'true');
                                $('#jtable-edit-form #Edit-configKey').prop("readonly", true);
                                $('#Edit-customDelete').hide();

                                $('input').each(function() {
                                    $(this).css('width', '100%');
                                });
                            },

                            deleteConfirmation: function(data) {    
                                data.deleteConfirmMessage = 'Are you sure to delete person ' + data.record.configType + '?';
                            },

                            actions: {
                                listAction: function(postData, jtParams) {
                                    console.log("Loading from custom listAction function ...");
                                    return $.Deferred(function($dfd) {
                                        $.ajax({
                                            url: '/admin/configuration/list?jtStartIndex=' + jtParams.jtStartIndex + '&jtPageSize=' + jtParams.jtPageSize + '&jtSorting=' + jtParams.jtSorting,
                                            type: 'GET',
                                            dataType: 'json',
                                            data: postData,
                                            success: function(data) {
                                                $dfd.resolve(data);
                                                if (data['TotalRecordCount'] != 0 && appConfigType == '') {
                                                    location.reload();
                                                }

                                            },
                                            error: function() {
                                                $dfd.reject();
                                            }
                                        });
                                    });
                                },

                                /**deleteAction: function (postData) {
                                     console.log("deleting from custom function..." + postData);
                                     return $.Deferred(function ($dfd) {
                                         $.ajax({
                                             url: '/admin/configuration/delete',
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
                                createAction: function(postData) {
                                    console.log("calling createaction function...");
                                    return $.Deferred(function($dfd) {
                                        $.ajax({
                                            url: '/admin/configuration/create',
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
                                            url: '/admin/configuration/update',
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
                                configType: {
                                    title: 'Type',
                                    width: '15%',

                                },
                                configKey: {
                                    title: 'Key',
                                    width: '20%',
                                    //type: 'hidden',
                                },
                                configValue: {
                                    title: 'Value',
                                    width: '22%',
                                },
                                description: {
                                    title: 'Description',
                                    width: '40%',
                                    type: 'textarea'
                                },

                                customDelete: {
                                    title: '',
                                    width: '0.3%',
                                    display: function(data) {
                                        var $but = $('<button title="delete" class="jtable-command-button jtable-delete-command-button" >delete</button>');
                                        $but.click(function() {
                                            var $dfd = $.Deferred();
                                            if (data.record.configType == 'global') {
                                                alert('Global Type Configuration are not allowed for deletion.')
                                                return false;
                                            }

                                            if (confirm('Are you sure you want to delete this?')) {
                                                $.ajax({
                                                    url: '/admin/configuration/delete',
                                                    type: 'POST',
                                                    dataType: 'json',
                                                    data: data.record,
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
                            $('#Container').jtable('load', {
                                configType: $('#type').val(),
                                configKey: $('#key').val(),
                            });
                        });

                        $(".jtable-title .jtable-toolbar").append('<span class="jtable-toolbar-item jtable-toolbar-item-add-record custom-jtable-toolbar-item-add-record"><span class="jtable-toolbar-item-icon"></span><span class="jtable-toolbar-item-text">Add Client Based record</span></span>');
                        //showCreateForm();


                        $(".custom-jtable-toolbar-item-add-record").click(function() {
                            $('body').append('<div id="custom-ui-widget-overlay" class="ui-widget-overlay ui-front"></div>');
                            $("body").append('<div id="custom-add-client-based-record-form" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-dialog-buttons ui-draggable ui-resizable" tabindex="-1" role="dialog" aria-describedby="ui-id-3" aria-labelledby="ui-id-4" style="position: absolute; height: auto; width: auto; top: 0px; left: 481.5px; display: block;">    <div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix"><span id="ui-id-4" class="ui-dialog-title">Add new record</span>        <button type="button" id="custom-close-button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close" role="button" aria-disabled="false" title="close"><span class="ui-button-icon-primary ui-icon ui-icon-closethick"></span><span class="ui-button-text">close</span></button>    </div>    <div id="ui-id-3" class="ui-dialog-content ui-widget-content" style="width: auto; min-height: 49px; max-height: none; height: auto;">        <form id="custom-jtable-create-form" class="jtable-dialog-form jtable-create-form">            <div class="jtable-input-field-container">                <div class="jtable-input-label">Merchant || ClientName</div>                <div class="jtable-input jtable-text-input">                       <select name="merchant_clientName" id="merchant_clientName"  style="width: 100%"><c:forEach items="${getAllClientResponse.clientList}" var="client"><option value="${client.merchant} && ${client.clientName}">${client.merchant} && ${client.clientName}</option></c:forEach></select>               </div>            </div>            <div class="jtable-input-field-container">                <div class="jtable-input-label">Key</div>                <div class="jtable-input jtable-text-input">                    <input class="" id="Edit-configKey" type="text" name="configKey" style="width: 100%;">                </div>            </div>            <div class="jtable-input-field-container">                <div class="jtable-input-label">Value</div>                <div class="jtable-input jtable-text-input">                    <input class="" id="Edit-configValue" type="text" name="configValue" style="width: 100%;">                </div>            </div>            <div class="jtable-input-field-container">                <div class="jtable-input-label">Description</div>                <div class="jtable-input jtable-textarea-input">                    <textarea class="" id="Edit-description" name="description"></textarea>                </div>            </div>        </form>    </div>    <div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">        <div class="ui-dialog-buttonset">            <button type="button" id="custom-cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"><span class="ui-button-text">Cancel</span></button>            <button type="button" id="custom-AddRecordDialogSaveButton" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"><span id="custom-save-button" class="ui-button-text">Save</span></button>        </div>    </div>    <div class="ui-resizable-handle ui-resizable-n" style="z-index: 90;"></div>    <div class="ui-resizable-handle ui-resizable-e" style="z-index: 90;"></div>    <div class="ui-resizable-handle ui-resizable-s" style="z-index: 90;"></div>    <div class="ui-resizable-handle ui-resizable-w" style="z-index: 90;"></div>    <div class="ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se" style="z-index: 90;"></div>    <div class="ui-resizable-handle ui-resizable-sw" style="z-index: 90;"></div>    <div class="ui-resizable-handle ui-resizable-ne" style="z-index: 90;"></div>    <div class="ui-resizable-handle ui-resizable-nw" style="z-index: 90;"></div></div>');
                            //$(".ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-dialog-buttons.ui-draggable.ui-resizable").css({"height": "auto", "width": "auto", "top":"0px", "left":"481.5px", "display":"block"});
                            buttonEventHandler();
                        });

                        	buttonEventHandler = function() {
                            $('#custom-close-button, #custom-cancel-button').click(function() {
                                $('#custom-ui-widget-overlay').remove();
                                $('#custom-add-client-based-record-form').remove();
                            });

                            $("#custom-AddRecordDialogSaveButton").click(function() {
                                var form = $('#custom-jtable-create-form');
                                $.ajax({
                                    url: '/admin/configuration/clientcreate',
                                    type: 'POST',
                                    dataType: 'json',
                                    data: form.serialize(),
                                    success: function(data) {
                                    $('#Container').jtable('load');
                                    alert("Client Configuration added Successfully.");
                                    $('#custom-ui-widget-overlay').remove();
                                	$('#custom-add-client-based-record-form').remove();
                                	} ,   
                                    error: function(data) {
                                    alert("There is Some issue in Adding client Configuration. Please Try Again.");
                                    },
                                    failure: function(data) {
                                    alert("There is Some issue in Adding client Configuration. Please Try Again.");
                                    }
                                });
                                $('#custom-ui-widget-overlay').remove();
                                $('#custom-add-client-based-record-form').remove();
                            });

                        }


                        $(".custom-jtable-toolbar-item-add-record").mouseover(function() {
                            $(this).addClass("jtable-toolbar-item-hover")
                        })
                        $(".custom-jtable-toolbar-item-add-record").mouseleave(function() {
                            $(this).removeClass("jtable-toolbar-item-hover")
                        })

                    });
                </script>
	</tiles:putAttribute>
</tiles:insertTemplate>