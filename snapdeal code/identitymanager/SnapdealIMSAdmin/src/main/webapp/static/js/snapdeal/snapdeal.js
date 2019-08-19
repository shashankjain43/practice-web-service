	function onRPXSuccess() {
		var s = window.location.href;
		if (s.indexOf('?') != -1) {
			window.location.href = s.substring(0, s.indexOf('?'));
		} else {
			window.location.reload();
		}
	}
	
	function onRPXFail() {
		var s = window.location.href;
		if (s.indexOf('?') != -1) {
			window.location.href = s.substring(0, s.indexOf('?'))+'?systemcode=503';
		} else {
			window.location.href = window.location.href + '?systemcode=503';
		}
	}

	function calcage(msecs, num1, num2) {
		s = ((Math.floor(msecs/num1))%num2).toString();
		if (s.length < 2)
		s = "0" + s;
		return "" + s + "";
	}
	
	function CountBack(msecs) {
		if (msecs < 0) {
			$("#timer").html(" --  :  --  : --");
			return;
		}	
		Displayhour =calcage(msecs,3600000,10000);
		Displaymin =calcage(msecs,60000,60);
		Displaysec =calcage(msecs,1000,60);
		
		$("#timer").html(Displayhour + "&nbsp;:&nbsp;" + Displaymin + "&nbsp;:&nbsp;" + Displaysec);
	
		setTimeout("CountBack(" + (msecs-1000) + ")", 1000);
	}

	function startTimer() {
		var timeLeft = $("#timeLeft").html();
		CountBack(parseInt(timeLeft, 10));
	}

	function openChatPopup(url)
	{
		window.open(url,'_blank', 'toolbar=0,scrollbars=1,location=0,toolbar=0,status=0,menubar=0,resizable=0,width=750,height=430')
	}

	function openMenuPopup(url)
	{
		window.open(url,'_blank', 'toolbar=0,scrollbars=1,location=0,toolbar=0,status=0,menubar=0,resizable=1,width=750,height=430')
	}

	function dummy() {
		var xyz = "test";
	}

	var TabbedContent = {
		init: function() {	
			$(".tab_item").mouseover(function() {			
				TabbedContent.slideContent($(this));
				
			});
		},
		
		slideContent: function(obj) {
			
			var margin = $(obj).parent().parent().find(".slide_content").width();
			margin = margin * ($(obj).prevAll().size() - 0);
			margin = margin * -1;
			
			$(obj).parent().parent().find(".tabslider").stop().animate({
				marginLeft: margin + "px"
			}, {
				duration: 300
			});
			
		}
	}

	function feedbackClick(e){
		e.preventDefault();		
		if($(e.target).parents('.feedback').length!=0&&$('.feedback').hasClass('off')){
			$('.feedback').removeClass('off');
        	$(".refermerchantside").removeClass("refer-feedback-hide");
            $("#feedbackForm").hide();
		}
		else{
			var src=Snapdeal.getStaticPath("/info/feedback");
			var iframe = '<iframe src="'+src+'" class="feedback_wrap" scrolling="no" frameborder="0" width="340" height="455" allowtransparency="true">';
			$('#feedbackForm').html(iframe);
			$("#feedbackForm").css('display','none');
			$("#feedbackForm").toggle();
			$(".feedback").toggleClass("off");
	   	 	$(".refermerchantside").addClass("refer-feedback-hide");	
		}
	};
	
	function refermerchantsideClick(e){
		e.preventDefault();
		if($(e.target).parents('.refermerchantside').length!=0&&$('.refermerchantside').hasClass('referoff')){
			$('.refermerchantside').removeClass('referoff');
        	$(".feedback").removeClass("refer-feedback-hide");
            $("#refermerchantsideForm").hide();
		}
		else{
			var src=Snapdeal.getStaticPath("/info/refermerchant-side");
			var iframe = '<iframe src="'+src+'" class="refermerchantside_wrap" scrolling="no" frameborder="0" width="340" height="550">';
			$('#refermerchantsideForm').html(iframe);
			$("#refermerchantsideForm").css('display','none');
			$("#refermerchantsideForm").toggle();		 
			$(".refermerchantside").toggleClass("referoff");
			$(".feedback").addClass("refer-feedback-hide");
		}	};

	jQuery.fn.moreLess = function(options) {
	    var textID = this;
	    var opts = $.extend({}, $.fn.moreLess.defaults, options);
	    if ($(textID).text().length < opts.minimumTextLength)
	        return $(this);

	    if (!opts.startExpanded)
	        InsertSpan(true, $(textID));
	    InsertControls();

	    $("span.moreLessControls").click(ControlClick);
	    return $(this);
	    function ControlClick() {
	        var content = $(this).prev('.moreLessContent');
	        var collapsed = content.length > 0;
	        if (collapsed && content[0].style.display == 'none') {
	            content.slideDown(opts.speed, function() {
	                $(this).before(content.html()).remove();
	                if (typeof opts.callback == 'function') {
	                    opts.callback.call();
	                }
	            });
	            ToggleControls(collapsed, $(this));
	        }
	        else {
	            var parent = $(this).parent();
	            $(this).remove();
	            InsertSpan(false, parent);
	            parent.append($(this));
	            ToggleControls(collapsed, $(this));
	            $(this).prev('.moreLessContent').slideUp(opts.speed, function() {
	                if (typeof opts.callback == 'function') {
	                    opts.callback.call();
	                }
	            });
	            $(this).click(ControlClick);
	        }
	    }
	    function ToggleControls(collapsed, control) {
	        control.html(collapsed ? opts.expandedText : opts.collapsedText);
	    }
	    function InsertSpan(collapsed, control) {
	        var index = opts.truncateIndex;
	        var text = control.text();
	        var html = control.html();
	        if (opts.truncateChar != null) {
	            index = control.text().indexOf(opts.truncateChar, index);
	            if (index < 0 || index > opts.maximumTruncateIndex)
	                index = opts.truncateIndex;

	        }
	        control.html(html.substring(0, index) + "<span class='moreLessContent' " + (collapsed ? "style='display:none;'" : "") + ">" + html.substring(index, html.length) + "</span>");

	    };
	    function InsertControls(expanded) {
	        $(textID).append("<span class='moreLessControls'>" + (opts.startExpanded ? opts.expandedText : opts.collapsedText) + "</span>");
	    }
	};

	// Default Options
	$.fn.moreLess.defaults = {
	startExpanded: false,
	collapsedText: '... <span class="moreLessControl">More &raquo;</span>',
	expandedText: ' <span class="moreLessControl"> &laquo; Less</span>',
	truncateIndex: 600,
	maximumTruncateIndex: 700,
	truncateChar: ' ',
	minimumTextLength: 800,
	speed: 'fast',
	callback: null
	};
 $(document).ready(function() {
	 /*$("#downservices").click(function(){
			$(".servicesdropped").toggle("fast");		
			return false;
			});	

		$("a.cityname").click(function(){
			var cityname = $(this).html();		
			$("#mycity").text(cityname);
			window.location.href = $(this).attr('href');
			return false;
		});*/
	 $("#citydropdown").change(function() {
			var pageUrl=$("#citydropdown").val($(this).attr("pageUrl"));
			window.location.href="/deals-"+pageUrl;
			return false;
		 });

		$(".india").click(function(){
			$('.us').show();
			
		});	
		
		$("#system_message .close").click(function(){
			$('#system_message').slideToggle("slow");
		});	
		  
		$("#system_message .firstsubscribe-skip").click(function(){
			$('#system_message').slideToggle("slow");
		});	
		
		$("#morecity").click(function(){
			$(".morecity").slideToggle("fast");		
			return false;
		});	
		
		$(".myaccount-drop").click(function(){
			$(".myaccount-drop").addClass("active-drop-tab");
			$(".myaccount-list-outer").show();
			return false;
		 });
		   		 
		  
		  $(".morezone-head").mouseover(function(){	
			  $('.morezone').hide();
			  $(this).parents('.moreZoneParent').find(".morezone").slideDown("fast");				
				});	
		  
		  $(".morezone_nul").mouseover(function(){
			  $(".morezone").slideUp("fast");	
		  });
		
		  $(".morezone").mouseleave(function(){
			  $(".morezone").slideUp("fast");	
		  });
		  
		  		
		  TabbedContent.init();
			 
		  startTimer();
 
            $(".signin").click(function(e) {  
        		$.ajax({url:"/ajaxLogin", type: "POST", dataType: 'json', success: function(data) {
        			window.widget = new Snapdeal.signupWidget();
        			window.widget.init(data);
        			$("#signin_box").html(window.widget.getHtml());
                    $("fieldset#signin_menu").toggle('fast');
					$(".signin").toggleClass("menu-open");
					$(".signup").removeClass("menu-open");
        		}});            	
            });
            
            $(".signup").click(function(e) {  
        		$.ajax({url:"/ajaxSignup", type: "POST", dataType: 'json', success: function(data) {
        			window.widget = new Snapdeal.signupWidget();
        			window.widget.init(data);
        			$("#signin_box").html(window.widget.getHtml());
                    $("fieldset#signin_menu").toggle('fast');
                    $(".signin").removeClass("menu-open");
					$(".signup").toggleClass("menu-open");
        		}});            	
            });
            
            $(".signinToCheckout").click(function(e) {  
        		$.ajax({url:"/ajaxLogin", type: "POST", dataType: 'json', success: function(data) {
        			window.widget = new Snapdeal.checkoutPageSignupWidget();
        			window.widget.init(data);
        			var html = window.widget.getHtml();
        			$(".checkout-signbox-cont").html(html);
        			$(".checkout-signbox-cont").show();
    				$(".checkout-signbox").hide();
        		}});            	
            });
            
        
        $(".myaccount").click(function(e) {    
			e.preventDefault();
            $("fieldset#signout_menu").toggle();
			$(".myaccount").toggleClass("menu-open");
        });
          
            $("fieldset#signin_menu").mouseup(function() {
				return false
			});
		
			$(document).mouseup(function(e) {
				if($(e.target).parents(".sign_right").length==0) {
					$(".signin").removeClass("menu-open");
					$(".signup").removeClass("menu-open");
					$("fieldset#signin_menu").hide();
					$(".myaccount").removeClass("menu-open");
					$("fieldset#signout_menu").hide();
				}
			});	
	
            $('#emailSubsForm').ajaxForm({dataType: "json", success: function(data) { 
            	$('#emailSubsForm').clearForm();  
        		$('#subsResponse').css("display","block");
        		$('.msg_arrow').css("display","block");
        		$('#subsResponse span').html(data['message'])
        		if(data['items']['newSubscriber']) {
            		var iframe = '<iframe src="http://www.vizury.com/analyze/analyze.php?account_id=VIZ-VRM-1330&param=1300" scrolling="no" width="1" height="1" marginheight="0" marginwidth="0" frameborder="0"></iframe>';
        			$('#newSubscriber').html(iframe);
            	}
        	}});
                    

            $('#forgotPasswordNavigation').ajaxForm({dataType: "json", success: function(data) {   
        		$('#forgotPasswordNavigationResponse').html(data['message']);
        	}});           
                        
	// Hide (Collapse) the toggle containers on load
	$(".toggle_container").hide(); 
	// Switch the "Open" and "Close" state per click then slide up/down
	// (depending on open/close state)
	$(".trigger").click(function(){
	$(this).toggleClass("").next().slideToggle("fast");
	});
	// Hide (Collapse) the toggle containers on load
	$(".toggle_container2").hide(); 
	// Switch the "Open" and "Close" state per click then slide up/down
	// (depending on open/close state)
	$(".trigger2").click(function(){
	$(this).toggleClass("").next().slideToggle("fast");
	});
	
	// Hide (Collapse) the toggle containers on load
	$(".toggle_container3").hide(); 
	// Switch the "Open" and "Close" state per click then slide up/down
	// (depending on open/close state)
	$(".trigger3").click(function(){
	$(".toggle_container3").slideToggle("fast");
	});

	// Hide (Collapse) the toggle containers on load
	$(".merchant_more_content").hide(); 
	// Switch the "Open" and "Close" state per click then slide up/down
	// (depending on open/close state)
	$("div.merchant_more").click(function(){
	$('.merchant_more').hide();
	$('.merchant_less').addClass("merchant_active").show();
	$('.merchant_more_content').slideDown("slow");
	});
	
	$("div.merchant_less").click(function(){
		$('.merchant_less').hide();
		$('.merchant_more').show();
		$('.merchant_more_content').slideUp("slow");
		});
	

/*	$.featureList(
			$("#tabs li a"),
			$("#output li.tab-content"), {
				start_item	:0
					}
				);*/

	
	$(".rpx").click(function() {
		var args = $(this).attr('data').split('-');
		loginWindow = window.open(args[0], "OAuthLogin", "menubar=yes,resizable=yes,scrollbars=yes,width=850,height=550,left=100,top=100");
	});
	  
	  
	$("#main").accordion({
	      objID: "#acc2", 
	      obj: "div", 
	      wrapper: "div", 
	      el: ".h", 
	      head: "h4, h5", 
	      next: "div" 
	      // initShow : "h4 + div.outer:eq(1)"
	   });
	
	  $("#main").accordion({
	      objID: "#acc1", 
	      obj: "div", 
	      wrapper: "div", 
	      el: ".h", 
	      head: "h4, h5", 
	      next: "div"
	      // initShow : "h4 + div.outer:eq(1)"
	    });
	
		 $('#slides1').bxSlider({
				wrapper_class: 'slides1_wrap',		
				controls: false,
				auto: true,
				pager: true,
				auto_controls: false
			});
	
 $(".back_city").click(function(){
	 $("#outer, .firstsubscribe-city_wrap").hide(); 
		$("#firstselectcity").show(); 
		
		
	 });
		$(".feedback").click(feedbackClick);
		$(".refermerchantside").click(refermerchantsideClick);
		   
		
	    $(document).mouseup(function(e) {
            $("#subsResponse").hide();
            $("#subsResponse2").hide();
            $('.morecity').hide();
            $('.RetypePassword').hide();
			$('.servicesdropped').hide();
            $('.msg_arrow').hide();
            $('.morezone').hide();
            $(".us").hide();
            $(".myaccount-drop").removeClass("active-drop-tab");
    		$(".myaccount-list-outer").hide();
            if($(e.target).parents(".feedback").length==0&&$(e.target)!=$('.feedback')) {
            	$(".feedback").removeClass("off");
            	$(".refermerchantside").removeClass("refer-feedback-hide");
            	$("#feedbackForm").hide();
            }
            if($(e.target).parents(".refermerchantside").length==0&&$(e.target)!=$('.refermerchantside')) {
            	$(".refermerchantside").removeClass("referoff");
            	$(".feedback").removeClass("refer-feedback-hide");
            	$("#refermerchantsideForm").hide();
            }

        });  

 });
