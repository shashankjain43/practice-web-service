jQuery.fn.timer = function(currTime) {
	$(this).each(function(i, e) {
		var dealTime = $(e).find('input.[name="startTime"]').val();
		var timer = new Snapdeal.DealTimer();
		timer.init($(e), dealTime - currTime);
	});
};
  
String.prototype.trim = function () {  return this.replace(/^\s+|\s+$/g, ""); };
Snapdeal.HomePage = function() {
	var self = this;
	this.init = function() {
		window.cartService = new Snapdeal.CartService();
		window.cartService.init();
		var cartQty = window.cartService.cartQty();
		$('#cartItemQtyId').text(cartQty);
		if (cartQty > 0) {
			$('#cartItemQtyId').removeClass('empty-cart');
		}
		if ($('#initShowCart').html() == 'true') {
			window.cartService.showCart();
		}
		
		if( $("#firstHit").attr('u') != '' && $(".leftNavAjax").attr('u') != undefined) {
			var leftNavUrl = Snapdeal.getStaticPath("/" + $(".leftNavAjax").attr('u')) + "&cpu=" + Snapdeal.Cookie.get('cityPageUrl');
			$.ajax({
	            url: leftNavUrl,
	            type: "GET",
	            dataType: "html",
	            success: function(data) {
	            	$(".leftNavAjax").html(data);
	            	autoPositionForLeftNav();
	            	leftNaviBrandStore();
	            	if( $(".left-product-banners").size() > 0 ) {
	            		homePageTopOfferWidget();
            		    $('.personalizationWidget').each(function(){
            		    	getPersonalizationWidgetProducts($(this).attr('id').split('-')[1]);
	            		});
            		    $('.homePageCatWidget').each(function(){
            		    	homePageCategoryWidgets();
            		    });
            		    loadLeftNavImage();
	            	}
	            }
	        });
		}
				
		$('.sdShareWidget').each(
				function() {

					if ($(this).attr('uid') != null) {
						$('#uidField').val($(this).attr('uid'));
					}
					if ($(this).attr('utmcontent') != null) {
						$('#utmContent').val($(this).attr('utmcontent'));
					}

					if ($(this).attr('catalogID') != null) {
						$('#catalogID').val($(this).attr('catalogID'));
					}
					if ($(this).attr('dealDetail') != null) {
						$('#dealDetail').val($(this).attr('dealDetail'));
					}

					if (($('#uidField').val() == 0)) {
						shareDealNLoggedIn(this);
					} else {
						if ($(this).attr('customUrl') != null) {
							$('#url').val($(this).attr('customUrl'));
						}
						if ($(this).attr('customEmail') != null) {
							$('#sharerEmail').val($(this).attr('customEmail'));
						}
						shareDealLoggedIn(this, $('#uidField').val(), $(this)
								.attr('customUrl'), $('#utmContent').val(), $(
								'#dealDetail').val());
					}
				});
		$('div.[type="sd.ImageLoader"]').each(function(i, e) {
			self.loadImage(e);
		});
		$("#refermerchantsideForm").mouseup(function() {
			return false;
		});
		$("#feedbackForm").mouseup(function() {
			return false;
		});
		$(".feedback").click(function(e) {
			return self.feedbackClick(e);
		});
		// $(".csupport-drop").click(function(e){return
		// self.customersupportclick(e);});
		$(".refermerchantside").click(function(e) {
			return self.refermerchantsideClick(e);
		});
		$("#citydropdown").change(function(e) {
			return self.handleCityChange(e);
		});
		$("#system_message .close").click(function(e) {
			self.closeSystemMessage(e);
		});
		$("#system_message .firstsubscribe-skip").click(function(e) {
			self.closeSystemMessage(e);
		});
		$(".signin").click(function(e) {
			self.showLogin(e);
		});
		$(".signup").click(function(e) {
			self.showSignUp(e);
		});
		$(".joinsnapdeal").click(function(e) {
			self.showSignUp(e);
		});

		$('#emailSubsForm').ajaxForm({
			dataType : "json",
			success : function(data) {
				self.handleEmailSubs(data);
			}
		});

		$(".myaccount").click(function(e) {
			self.showMyAccount(e);
		});
		$(document).mouseup(function(e) {
			self.handleMouseUp(e);
		});
		$('#forgotPasswordNavigation').ajaxForm({
			dataType : "json",
			success : function(data) {
				self.handleForgotPassword(data)
			}
		});
		// Hide (Collapse) the toggle containers on load
		$(".toggle_container").hide();
		// Switch the "Open" and "Close" state per click then slide up/down
		// (depending on open/close state)
		$(".trigger").click(function() {
			$(this).toggleClass("").next().slideToggle("fast");
		});
		// Hide (Collapse) the toggle containers on load
		$(".toggle_container2").hide();
		// Switch the "Open" and "Close" state per click then slide up/down
		// (depending on open/close state)
		$(".trigger2").click(function() {
			$(this).toggleClass("").next().slideToggle("fast");
		});
		$(".rpx").click(function(e) {
			self.handleRPXClick(e)
		});
		
		leftNaviBrandStore();

		$(".back_city").click(function() {
			$("#outer, .firstsubscribe-city_wrap").hide();
			$("#firstselectcity").show();
		});

		
		$(".csupport-drop").click(function() {
			
			if( $(".cust-support-outer").css('display') == 'none' ) {
				$(".csupport-drop").addClass("active-drop-tab");
			} else {
				$(".csupport-drop").removeClass("active-drop-tab");
			}
			
			$(".cust-support-outer").toggle();
			
			$(".citylist-outer").hide();
			$(".city-drop").removeClass("active-drop-tab");
			return false;
		});
		
		$(".city-drop").click(function() {
			$(".citylist-outer").toggle();
			$(".city-drop").toggleClass("active-drop-tab");
			return false;
		});
		
		$(".myaccount-drop").click(function() {
			$(".myaccount-list-outer").toggle();
			$(".myaccount-drop").toggleClass("myacct-active-drop-tab");
			return false;
		});
		

		$('.orderStatus').click(function(){
   			var url = "/info/orderStatus";
			var win = window.open(url, "OrderStatus", "");
			win.focus();
   		});	


		$("#voucherResend").click(function() {
			$("#voucherResendOuter").show();
			return false;
		});

		$('.filter-toggle').click(function() {
			$(this).toggleClass("filter-toggle-arrow");
			$(this).next().slideToggle("fast");
		});


		$(".get-deal-alerts").click(function() {
			$(".get-daily-alerts-outer").show();
			$(".citylist-outer").hide();
			$(".city-drop").removeClass("active-drop-tab");
			return false;
		});
		
		$(".all-categories-drop-box").click(function() {
			$(this).toggleClass('all-categories-uparrow');
			$(".all-categories-drop-outer").toggle();
			return false;
		});
		

		$(".left-categories-head-down").mouseenter(function(){
			leftCategoriesHeadHover('left-categories-head-down');
		}).mouseleave(function(){
			leftCategoriesHeadOut('left-categories-head-down');
		});
		
		$(".left-categories-cont-inactive").mouseenter(function(){
			leftCategoriesContHover('left-categories-cont-inactive');
		}).mouseleave(function(){
			leftCategoriesContOut('left-categories-cont-inactive');
		});
		

		$("a.cityname").click(function() {
			var cityname = $(this).html();
			$("#mycity").text(cityname);
			window.location.href = $(this).attr('href');
			return false;
		});

		$("a.getcityname").click(function() {
			var zoneId = $(this).attr('defaultZoneId');
			$("#zoneSubscibeHidden").val(zoneId);
			var cityname = $(this).html();
			$("#getmycity").text(cityname);
			$(".get-alert-list-outer").hide();
			return false;
		});

		$(".menucard").click(function() {
			$(".menucard-overlay").show();
			return false;
		});

		$(".nav-mycart-outer").click(function() {
			window.cartService.showCart();
			window.cartService.fireOmnitureScript("showCart");
			return false;
		});
		
		$(".liveBuy").live('click', function() {			
			if ($(this).attr('catalog') == "undefined") {
				$('#attribute-error-message').show();
				return false;
			}
			var isPrebook = false;
			if ($(this).attr('prebook') == "true") {
				isPrebook = true;
			}
			$(".menucard-overlay").hide();
			window.cartService.addToCart($(this).attr('catalog'));
			window.cartService.fireOmnitureScript("addToCart", $(this)
					.attr('catalog'), true, isPrebook);
			return false;
		});

		$(".addToCart").click(
				function() {
					if ($(this).attr('catalog') == "undefined") {
						$('#attribute-error-message').show();
						return false;
					}
					var isPrebook = false;
					if ($(this).attr('prebook') == "true") {
						isPrebook = true;
					}
					$(".menucard-overlay").hide();
					window.cartService.addToCart($(this).attr('catalog'));
					window.cartService.fireOmnitureScript("addToCart", $(this)
							.attr('catalog'), true, isPrebook);
					return false;
				});
		
		$(".share-deal-know_more").click(function(){
			$(".know-more-outer").show();
			$(".share-deal-fields-outer").hide();
			return false;
			});
		
		$(".know-more-close").click(function(){
			$(".know-more-outer").hide();			
			return false;
			});

		$(".close").click(function() {
			$(".get-daily-alerts-outer").hide();
			return false;
		});
		
		if( $("#categoryId").val() != "" && $("#categoryId").val() != null ) {
			if( $('#changeBackToAll').val() != "true" ) {
				$('a[catId="'+$("#categoryId").val()+'"][vid="'+$("#vertical").val()+'"]').click();
			}
		}

		$(".share-close").click(
			function() {
				$(".share-deal-fields-outer").hide();
				$('input[type="text"]').each(function() {
					$(this).val("");
				});
				$("#createRefererResponse").html("");
				$('#emailShareStatus').html("");
				$("#addressbookmails").hide();
				$('.share-email-button').removeClass('inactive');
				$('#refererEmailField').attr('readonly', false);
				$('.error').remove();
				$("#shareNLoggedin").html('<div class="share-ico-outer share-ico-inactive"><div class="fshare-ico fshare-ico-inactive"></div><div class="ftweet-ico ftweet-ico-inactive"></div><div class="fmail-ico fmail-ico-inactive"></div></div>');
				return false;
		});

		$(".share-deal-head-bg").click(function() {
			$(".share-deal-fields-outer").show();
			return false;
		});

		$(".closeCustomerSupport").click(function() {
			$(".voucher-resend-outer").hide();
			$("#statusIdInput").val("Order ID");
			$("#messageVoucherResend").hide();
			$("#voucherResendInner").show();
			$("#statusMobileInput").val("Mobile Number");
			$("#errormsgOrder").html(" ");
			$("#errormsgVoucher").html(" ");
			$("#orderIdInput").val("Order ID");
			$("#voucherMobileInput").val("Mobile Number");
			return false;
		});

		$(".main-banner-close").click(function() {
			$(".main-banner").hide();
			return false;
		});

		$(".menucard-close").click(function() {
			$(".menucard-overlay").hide();
			return false;
		});


		$(".signin-skip").click(function() { $("fieldset#signin_menu").hide(); });

		$("#local-deals").click(function() {
			$("#local-deals-out").slideToggle("fast");
			$("#local-deals").toggleClass("active-category");

		});

		$("#product-deals").click(function() {
			$("#product-deals-out").slideToggle("fast");
			$("#product-deals").toggleClass("active-category");

		});
		
		var localSubscriptionUrl = '';
		$(".localDealsLeftNav li a").live("click", function(){
			if( Snapdeal.Cookie.get('sc') == 1 ) {
				$("#leftSubscriptionOverlay").show();
				var posTop = ($(this).parent().position().top) -$(window).scrollTop() -25 ;	                 
				if(posTop>530){					
					$('body,html').scrollTop(225);
					posTop = $(this).parent().position().top -$(window).scrollTop() -25 ;				
				}
				if(posTop<0){					
					$('body,html').scrollTop(615);
					posTop = $(this).parent().position().top -$(window).scrollTop() -25 ;				
				}
				localSubscriptionUrl = $(this).attr('catUrl');
				$('.subscription-left-cont').css('top',posTop).show();
				leftCategorySubs($(this).html(), "'"+$(this).attr('catUrl')+"'");
			}else{
				var leftNavUrl = $(this).attr('catUrl').replace(/\zonePage/g, Snapdeal.Cookie.get('cityPageUrl'));
				window.location.href = "/" + leftNavUrl;
			}
		});

		$(".localDealsError li a").live("click",
				function() {
					if (Snapdeal.Cookie.get('sc') == 1) {
						var posTop = ($(this).parent().position().top)
								- $(window).scrollTop() + 35;
						errorHeaderSubscription($(this).html(),"'"+$(this).attr('catUrl')+"'");
						$('.subscription-header-cont').css('top', posTop);
						$('.subscription-header-cont').css('left', '500px');
					} else {
						
						
						var pageUrl = Snapdeal.Cookie.get('cityPageUrl');
						
						if(pageUrl==null){
							var posTop = ($(this).parent().position().top)
							- $(window).scrollTop() + 35;
					errorHeaderSubscription($(this).html(),"'"+$(this).attr('catUrl')+"'");
					$('.subscription-header-cont').css('top', posTop);
					$('.subscription-header-cont').css('left', '500px');
							
						}else{
							var leftNavUrl = $(this).attr('catUrl').replace(
									/\zonePage/g,
									Snapdeal.Cookie.get('cityPageUrl'));
							window.location.href = "/" + leftNavUrl;
							
						}
					}
		});
		
		$(".localDealsLeftNavInner li a").live("click", function(){
			if( Snapdeal.Cookie.get('sc') == 1 ) {
				var posTop = ($(this).parent().position().top) -325 ;
				leftCategorySubs($(this).html(), "'"+$(this).attr('catUrl')+"'");
				$('.subscription-left-cont').css('top',posTop);
				$('.left-categories-cont-inactive, .left-categories-head-down').unbind('mouseleave');
			}else{
				var leftNavUrl = $(this).attr('catUrl').replace(/\zonePage/g, Snapdeal.Cookie.get('cityPageUrl'));
				window.location.href = "/" + leftNavUrl;
			}
		});
		

		$(".locations-details-skip").live("click", function() {
			$(".tab-content").hide();
			$(".all-location a").removeClass("current");
			return false;
		});

		$('#voucherResendButton').click(
				function() {
					var ajaxUrl = '/resendVoucherInHeader/'
							+ $("#orderIdInput").val() + '-'
							+ $("#voucherMobileInput").val();
					$.ajax({
						url : ajaxUrl,
						type : "GET",
						dataType : 'json',
						success : function(data) {
							displayMessage(data);
						}
					});
				});

		$("#orderIdInput").keypress(function(C) {
			var B = (C.keyCode ? C.keyCode : C.which);
			if (B == 13) {
				$("#voucherResendButton").click()
			}
		});

		$("#voucherMobileInput").keypress(function(C) {
			var B = (C.keyCode ? C.keyCode : C.which);
			if (B == 13) {
				$("#voucherResendButton").click()
			}
		});
		
		$('.navlink a').mouseover(function() {
			var id = $(this).attr('id');
			if($(this).hasClass('inactive')){
				$("#no-prod-" + id).show();	
			}
		});
		
		$('.navlink a').mouseleave(function() {
			$(".no-prod-outer").hide();	
		});
		
		$('.sub-cat-list a').mouseover(function() {
			var id = $(this).attr('id');
			if($(this).hasClass('sub-inactive')){
				$("#sub-no-prod-" + id).show();	
			}
		});
		
		$('.sub-cat-list a').mouseleave(function() {
			$(".sub-no-prod").hide();	
		});
		
		$('.no-prod-outer').mouseover(function() {
			var id = $(this).attr('id');
			$("#" + id).show();	
		});
		
		$('.no-prod-outer').mouseleave(function() {
			var id = $(this).attr('id');
			$("#" + id).hide();	
		});
		
		$('.sub-no-prod').mouseover(function() {
			var id = $(this).attr('id');
			$("#" + id).show();	
		});
		
		$('.sub-no-prod').mouseleave(function() {
			var id = $(this).attr('id');
			$("#" + id).hide();	
		});
		
		$(".leftSubcatOuter").each(function(){
			$(this).find("ul:last").css('border','none');
		});
		
		function displayMessage(data) {
			if (data != null) {
				$('#messageVoucherResend').html(data['message']);
				$("#voucherResendInner").hide();
				$('#messageVoucherResend').show();
			} else {
				$('#errormsgVoucher')
						.html(
								'Enter valid Order ID & Mobile Number you used at the time of Purchase');
			}
		}

		$('.collectDemographs').each(function() {
			var widget = new Snapdeal.collectDemographWidget();
			widget.render(this);
		});

		$(".signinToCheckout").click(function(e) {
			$.ajax({
				url : "/ajaxLogin",
				type : "POST",
				dataType : 'json',
				success : function(data) {
					window.widget = new Snapdeal.checkoutPageSignupWidget();
					window.widget.init(data);
					var html = window.widget.getHtml();
					$(".checkout-signbox-cont").html(html);
					$(".checkout-signbox-cont").show();
					$(".checkout-signbox").hide();
				}
			});
		});

		$("ul.cust-support li:last, ul.citylist li:last, ul.get-alert-drop li:last, ul.myaccount-list li:last").css('border', 'none');
		
		highlights = new Snapdeal.DealHighlights();
		highlights.init(this);

		$('#city-search-field').autocomplete1("/json/cities/get/search/city", {
			dataType : 'json',
			parse : function(data) {
				$("#city-search-field").addClass("city-search-drop");
				var rows = [];
				for ( var i = 0; i < data.length; i++) {
					rows[i] = {
						data : data[i].citySearchDTO,
						value : data[i].citySearchDTO.name,
						result : data[i].citySearchDTO.name
					};
				}
				return rows;
			},			
			formatItem : function(item) {
				var name = item.name;
				if (name == 'No result found') {
					name = '<span class="no-result">' + name + '</span>';
				}
				return name;
			},
			minChars : 2,
			max: 1000,
			matchContains: true,
			extraParams: {timestamp : 0}
		}).result(function(event, item) {
			if (item.name == 'No result found') {
			} else {
				window.location.href = "/deals-" + item.pageUrl;
			}
			$("#city-search-field").removeClass("city-search-drop");
			
			/*
			 * Setting icu cookie value for CityMigrationTask
			 *var cityList = $("#cityDivisionList").val().trim().split(',');
			 *$.each( cityList, function(item1) {
			 *if( item.pageUrl.toLowerCase() == cityList[item1].toLowerCase()) {
			 *if( Snapdeal.Cookie.get('icu') == null ) {
			 *Snapdeal.Cookie.set('icu', 1, 90);
			 *}
			 *}
			 *});
			*/
		});
		
		$('#getmycity').autocomplete1("/json/cities/get/search/city", {
			dataType : 'json',
			parse : function(data) {
				var rows = [];
				for ( var i = 0; i < data.length; i++) {
					var result = data[i].citySearchDTO.name;
					if (result == 'No result found') {
						result = '';
					}
					rows[i] = {
						data : data[i].citySearchDTO,
						value : data[i].citySearchDTO.name,
						result : result
					};
				}
				return rows;
			},
			formatItem : function(item) {
				var name = item.name;
				if (name == 'No result found') {
					name = '<span class="no-result">' + name + '</span>';
				}
				return name;
			},
			minChars : 2,
			max: 1000,
			matchContains: true,
			extraParams: {timestamp : 0}
		}).result(function(event, item) {
			$("#zoneSubscibeHidden").val(item.defaultZoneId);
			setTimeout('$("#value").focus()',10);
		});
		updateRecentSearchCookie();
	};
	
	
//	$('#auto-suggest-search').autocomplete("/json/search/autoSuggestion", {
//		dataType : 'json',
//		parse : function(data) {
//			var rows = [];
//			var c = 0;
//			for ( var i = 0; i < data.length; i++) {
//				rows[c] = {
//					data : data[i].ssd,
//					value : data[i].ssd.cn,
//					result : $('#auto-suggest-search').val()
//				};
//				c++;
//				var sgns = data[i].ssd.sgns;
//				if(typeof sgns.length === "undefined") {
//					sgns = [sgns];
//				}
//				for (var j = 0; j < sgns.length; j++) {
//					rows[c] = {
//						data : sgns[j],
//						value : sgns[j].sn,
//						result : sgns[j].sn
//					}
//					c++;
//				}
//			}
//			return rows;
//		},
//		formatItem : function(item) {
//			var name = item.sn;
//			if(typeof name === "undefined") {
//				name = '<b>' + item.cn + '</b>';
//			}
//			return name;
//		},	
//		minChars : 2,
//		max: 1000,
//		extraParams: {timestamp : 0}
//	}).result(function(event, item) {
//		var name = item.sn;
//		if(typeof name === "undefined") {
//			name = $('#auto-suggest-search').val();
//		}
//		return name;
//	});

	this.loadScript = function(url) {
		// alert('loadScript');
		var scriptNode = $('<script/>');
		scriptNode.attr('type', 'text/javascript');
		scriptNode.attr('src', url);
		scriptNode.appendTo($("head"));
	}

	this.showSignUp = function() {
		$.ajax({
			url : "/ajaxSignup",
			type : "POST",
			dataType : 'json',
			success : function(data) {
				self.handleSignUp(data);
			}
		});
	};
	
	this.handleSignUp = function(data) {
		window.widget = new Snapdeal.signupWidget();
		window.widget.init(data);
		$("#signin_box").html(window.widget.getHtml());
		$('#signin_box').css('height:100%','width:100%')
		$('.signupopupbox').show('fast');
		$(".signin").toggleClass("menu-open");
	};

	this.handleRPXClick = function(e) {
		var args = $(e).attr('data').split('-');
		loginWindow = window
				.open(
						args[0],
						"OAuthLogin",
						"menubar=yes,resizable=yes,scrollbars=yes,width=850,height=550,left=100,top=100");
	};

	this.handleForgotPassword = function(e) {
		$('#forgotPasswordNavigationResponse').html(data['message']);
	};

	this.handleMouseUp = function(e) {
		
		if( $(e.target).attr('id') == "signin_menu" ) return false;
		
		$("#subsResponse, #subsResponse2, .morecity, .RetypePassword, .servicesdropped, .msg_arrow, .morezone, .common-fine-prints-box, .know-more-outer, .us").hide();
		$("#emailShareStatus").hide();
		$(".get-alert-list-outer").hide();

		if ($(e.target).parents("#signin_menu").length == 0
				|| $(e.target).hasClass("signin-skip")
				|| $(e.target).hasClass("joinsnapdeal") || $(e.target).hasClass("alreadyRegistered")) {
			$(".signin").removeClass("menu-open");
//			$(".signup").removeClass("menu-open");
			$(".signininner2").removeClass("menu-open");
			$("fieldset#signin_menu").hide();
//			$('.signupopupbox').hide();
			$(".myaccount").removeClass("menu-open");
			$("fieldset#signout_menu").hide();
			if ($(e.target).hasClass("joinsnapdeal")) {
				self.showSignUp();
			}
			
			if ($(e.target).hasClass("alreadyRegistered")) {
				self.showLogin(e);
			}
		}

		if ($(e.target).parents(".feedback").length == 0
				&& $(e.target) != $('.feedback')) {
			$(".feedback").removeClass("off");
			$(".refermerchantside").removeClass("refer-feedback-hide");
			$("#feedbackForm").hide();
		}
		if ($(e.target).parents(".refermerchantside").length == 0
				&& $(e.target) != $('.refermerchantside')) {
			$(".refermerchantside").removeClass("referoff");
			$(".feedback").removeClass("refer-feedback-hide");
			$("#refermerchantsideForm").hide();
		}

	};

	this.showMyAccount = function(e) {
		e.preventDefault();
		$("fieldset#signout_menu").toggle();
		$(".myaccount").toggleClass("menu-open");
	};

	this.getMultiSubscriptionHtml = function(dto,email,zoneId){
		var subsDetail = "<div class='subscriptionCitiesBox fnt-tahoma'>";
        subsDetail+="<div id='subscriptionCitiesBoxClose' class='subscriptionCitiesBoxClose'></div>";
        subsDetail+="<div class='subscriptionCitiesCont'>";
        subsDetail+="<div style='font-size:16px'><strong>You seem to have subscribed to more than one city.</strong></div>";
        subsDetail+="<div style='font-size:14px'>Please select one city you would like to be subscribed to:</div><br/>";
        subsDetail+="<div>";
        for ( var i = 0; i < dto.length; i++) {
                if(i%4==0 && i!=0){
                        subsDetail+="</div><div>";
                }
                if(zoneId == dto[i].zoneId){
                        subsDetail+="<label><input name='city' type='radio' value='"+dto[i].zoneId+"' checked=checked/>"+ dto[i].cityName+"</label>";
                }else{
                        subsDetail+="<label><input name='city' type='radio' value='"+dto[i].zoneId+"'/>"+ dto[i].cityName+"</label>";
                }
                
        }
        subsDetail+="</div>";
        subsDetail+=" <button id='subscriptionSubmit' class='blueBtn margnCenter' ><span>Submit</span></button>";
        subsDetail+="<div class='error' id='responsesubs'></div>";
        subsDetail+="<div class='subscriptionText' style='text-align:left;background-color: white;margin-top: 10px;border-top: 2px solid lightgray;'>";
        subsDetail+="<div class='head'>What's in it for you?</div>";
        subsDetail+="<div>By selecting a city, you can enjoy regular deal updates in your city.  </div><br/>";
        subsDetail+=" <div>Click <strong class='handCursr' id='subscriptionCityAlerts' style='color: #1C8BD4;'>Subscribe</strong> if you want to subscribe to another city.</div>";
        subsDetail+="</div></div>";
        return subsDetail;
	}


	this.handleEmailSubs = function(data) {
		$('#value').val('email or mobile');
		$('#subsResponse').css("display", "block");
		$('.msg_arrow').css("display", "block");
		$('#subsResponse span').html(data['message']);
		if (data['message'].indexOf("Successfully") != -1) {
			$(".get-daily-alerts-outer").delay(3200).fadeOut(200);
		}
		if(data['items']['subscribed']){
			var zoneId=data['items']['zoneId'];
			var email = data['items']['email'];
			var ajaxUrl = "/getDto?zoneId="+zoneId+"&email="+email;
			$.ajax({
				url : ajaxUrl,
				type : "POST",
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				success : function(data) {
					$('#subscriptionOverlay').html(self.getMultiSubscriptionHtml(data,email,zoneId));
					$('.get-daily-alerts-outer').hide();
					$('#subscriptionOverlay').show();
					
					$('#subscriptionCityAlerts').click(function(){
						$('#subscriptionOverlay').hide();
						$('.get-daily-alerts-outer').show();
						
					});
					
					$('#subscriptionCitiesBoxClose').click(function(){
						var selectedZoneId = $('input:radio[name=city]:checked').val();
						var subsUrl = "/sigleCitySubscribe?zoneId="+selectedZoneId+"&email="+email;
						$.ajax({
							url : subsUrl,
							type : "POST",
							dataType : 'json',
							contentType : 'application/json; charset=utf-8',
							success : function(data) {
								$('#responsesubs').html(data['message']);
								if (data['message'].indexOf("Successfully") != -1) {
									$('#subscriptionOverlay').html('').hide();
								}
							}
							
						})
					});
					
					$('#subscriptionSubmit').click(function () {
						var selectedZoneId = $('input:radio[name=city]:checked').val();
						var subsUrl = "/sigleCitySubscribe?zoneId="+selectedZoneId+"&email="+email;
						$.ajax({
							url : subsUrl,
							type : "POST",
							dataType : 'json',
							contentType : 'application/json; charset=utf-8',
							success : function(data) {
								$('#responsesubs').html(data['message']);
								if (data['message'].indexOf("Successfully") != -1) {
									$("#subscriptionOverlay").delay(1000).fadeOut(200);
								}
							}
							
						})
						
					})
				}
				
			})
		}
		if (data['items']['newSubscriber']) {
			var iframe = '<iframe src="http://www.vizury.com/analyze/analyze.php?account_id=VIZ-VRM-1330&param=1300" scrolling="no" width="1" height="1" marginheight="0" marginwidth="0" frameborder="0"></iframe>';
			$('#newSubscriber').html(iframe);
		}
	};

	this.showLogin = function(e) {
		$.ajax({
			url : "/ajaxLogin",
			type : "POST",
			dataType : 'json',
			success : function(data) {
				self.handleLogin(data);
			}
		});
	};

	this.handleLogin = function(data) {
		window.widget = new Snapdeal.signupWidget();
		window.widget.init(data);
		$("#signin_box").html(window.widget.getHtml());
		$('#signin_box').css('height:100%','width:100%');
		$('#signin_box').show();
		$('.signupopupbox').show();
		$(".signin").toggleClass("menu-open");
		$("#j_username").focus(changeColor());
	};
	
	$('#resend_password_link, .alreadyRegistered').live('keydown', function(e) { 
		  var keyCode = e.keyCode || e.which; 
		  if (keyCode == 9 ) {
		      $("#j_username").focus();
		      return false;
		  } 
	});
	
	$('#close-pop').live('keydown', function(e) { 
		  var keyCode = e.keyCode || e.which; 
		  if (keyCode == 9 ) {
		      $("#j_username").focus();
		      $("#fp_email").focus();
		      return false;
		  } 
	});

	this.loadImage = function(e) {
		var element = $(e);
		var html = '<img src="' + element.attr('src') + '" width="'
				+ element.attr('width') + '" alt="' + element.attr('alt')
				+ '"/>';
		var parent = $(e.parentNode);
		element.remove();
		parent.append(html);
	};

	this.handleCityChange = function(e) {
		var pageUrl = $('#citydropdown').val();
		$('#citydropdown').val(pageUrl);
		window.location.href = "/deals-" + pageUrl;
		return false;
	};

	this.closeSystemMessage = function(e) {
		$('#system_message').slideToggle("fast");
	};

	this.feedbackClick = function(e) {
		e.preventDefault();
		if ($(e.target).parents('.feedback').length != 0
				&& $('.feedback').hasClass('off')) {
			$('.feedback').removeClass('off');
			$(".refermerchantside").removeClass("refer-feedback-hide");
			$("#feedbackForm").hide();
		} else {
			var src = Snapdeal.getStaticPath("/info/feedback");
			var iframe = '<iframe src="'
					+ src
					+ '" class="feedback_wrap" scrolling="no" frameborder="0" width="340" height="455" allowtransparency="true">';
			$('#feedbackForm').html(iframe);
			$("#feedbackForm").css('display', 'none');
			$("#feedbackForm").toggle();
			$(".feedback").toggleClass("off");
			$(".refermerchantside").addClass("refer-feedback-hide");
		}
	};

	this.customersupportclick = function(e) {
		e.preventDefault();
		if ($(e.target).parents('.csupport-drop').length == 0
				&& $('.csupport-drop').hasClass('active-drop-tab')) {
			$(".cust-support-outer").hide();
			$(".csupport-drop").removeClass("active-drop-tab");
		} else {
			$(".csupport-drop").addClass("active-drop-tab");
			$(".cust-support-outer").show();
		}
	}

	this.refermerchantsideClick = function(e) {
		e.preventDefault();
		if ($(e.target).parents('.refermerchantside').length != 0
				&& $('.refermerchantside').hasClass('referoff')) {
			$('.refermerchantside').removeClass('referoff');
			$(".feedback").removeClass("refer-feedback-hide");
			$("#refermerchantsideForm").hide();
		} else {
			var src = Snapdeal.getStaticPath("/info/refermerchant-salesforce-side");
			var iframe = '<iframe src="'
					+ src
					+ '" class="refermerchantside_wrap" scrolling="no" frameborder="0" width="340" height="500" allowtransparency="true">';
			$('#refermerchantsideForm').html(iframe);
			$("#refermerchantsideForm").css('display', 'none');
			$("#refermerchantsideForm").toggle();
			$(".refermerchantside").toggleClass("referoff");
			$(".feedback").addClass("refer-feedback-hide");
		}
	};


	this.showLoader = function(container) {
		var html = '<div type="sd.AjaxLoader" style="padding:0 20px 0 20px;text-align:center;"><img src="'
				+ Snapdeal.getResourcePath('img/ajax-loader.gif') + '"/></div>';
		container.append(html);
	};

	this.hideLoader = function(container) {
		container.find('div.[type="sd.AjaxLoader"]').each(function(i, e) {
			$(e).remove();
		});
	};
	
	
	// sending the search string with productId
	/*
	 $('.searchResult').bind('click', function() {
		$.ajax({
			url : '/json/ssLogger',
			dataType: 'json',
			data : {
				query : 'product deal',
				cid : 8801,
				v : 'p'
			},
			success : function(data) {
				var ssdto = data.ssdto.query + ';' + data.ssdto.category + ';' + data.ssdto.vertical;
				Snapdeal.Cookie.set('searchString', ssdto, 3);
			}
		});
	});
	*/
};


Snapdeal.DealLocation = function() {
	var self = this;
	var map;
	var msgDiv;
	var sw_lng;
	var sw_lat;
	var ne_lng;
	var ne_lat;
	var m_count;

	this.init = function(data, block) {
		self.m_count = 0;
		var div = '<div class="nomap"><div class="black-shadow mapMsg">Map not available for this outlet.</div></div>'
		self.msgDiv = $(div);
		var html = '';
		if (data.v != null) {
			if (data.v.vo != null) {
				if ($.isArray(data.v.vo)) {
					$.each(data.v.vo, function(i, e) {
						self.addVendorOutlet(e, block);
					});
				} else {
					self.addVendorOutlet(data.v.vo, block);
				}
			}
		}
		if (self.map == undefined) {
			block.find('.main_map').remove();
		}
	};

	this.addVendorOutlet = function(data, block, zoom) {
		var html = '';
		var d = $('<div/>');
		d.attr('class', 'locations-details');
		d.appendTo(block.find('div.loc'));
		if (data.n != null && data.n != "") {
			html = html + '<strong>' + data.n + ' outlet</strong><br>';
		}
		if (data.a1 != null && data.a1 != "") {
			html = html + 'Address: <span>' + data.a1 + '</span><br>';
		}
		if (data.a2 != null && data.a2 != "") {
			html = html + '<span>' + data.a2 + '</span><br>';
		}
		if (data.p != null && data.p != "") {
			html = html + 'Pin: <span>' + data.p + '</span><br>';
		}
		
		if (data.o != null && data.o != "") {
			html = html + 'Phone: <span>' + data.o + '</span><br>';
		}
		if (data.f != null && data.f != "") {
			html = html + 'Fax: <span>' + data.f + '</span><br>';
		}
		if (data.t != null && data.t != "") {
			html = html + 'Timing: <span>' + data.t + '</span><br>';
		}
		d.append(html);
		if (data.l && data.l != "" && data.g && data.g != "") {
			self.m_count++;
			if (self.m_count == 2) {
				self.map.setZoom(10);
			}
			var latlng = new google.maps.LatLng(data.l, data.g);
			if (self.map == undefined) {
				block.find('div.nomap').remove();
				var myOptions = {
					zoom : 14,
					center : latlng,
					mapTypeControl : false,
					streetViewControl : false,
					zoomControl : true,
					zoomControlOptions : {
						position : google.maps.ControlPosition.LEFT_TOP
					},
					mapTypeId : google.maps.MapTypeId.ROADMAP
				};
				self.map = new google.maps.Map(block.find('div.map')[0],
						myOptions);
				var e_c = $('<img id="4" class="dock black-shadow expand_img" src="img/max.png"/>');
				var sd = '<div><div class="ovr top h1 o1"></div><div class="ovr top h2 o2"></div><div class="ovr top h3 o3"></div><div class="ovr top h4 o4"></div><div class="ovr top h5 o5"></div></div>';
				sd = sd
						+ '<div><div class="ovr left w1 o1"></div><div class="ovr left w2 o2"></div><div class="ovr left w3 o3"></div><div class="ovr left w4 o4"></div><div class="ovr left w5 o5"></div></div>';
				e_c.appendTo(block.find('div.main_map'));
				$(sd).appendTo(block.find('div.main_map'));
				e_c
						.click(function() {
							var d = block.find('div.scrollbar1');
							var m = block.find('div.main_map');
							var img = $(this);
							if (d.css('marginLeft') != '0px') {
								d.css('display', 'inline');
							}
							var l1 = block.find('.loc_c');
							var w = l1.width();
							var h = m.height();
							d
									.animate(
											{
												marginLeft : parseInt(d
														.css('marginLeft'), 10) == 0 ? -d
														.outerWidth()
														: 0
											},
											{
												duration : 600,
												easing : 'myEasing',
												step : function(now, fx) {
													m
															.width(w
																	- d.width()
																	- now);
													if (fx.end < fx.start) {
														m.height(h - now / 2);
													} else {
														m
																.height(h
																		+ (fx.start - now)
																		/ 2);
													}
												},
												complete : function() {
													if (d.css('marginLeft') == '0px') {
														img
																.attr(
																		'src',
																		Snapdeal
																				.getResourcePath('img/max.png'));
													} else {
														img
																.attr(
																		'src',
																		Snapdeal
																				.getResourcePath('img/min.png'));
														d
																.css('display',
																		'none');
													}
													google.maps.event.trigger(
															self.map, "resize");
												}
											});
						});
			}
			var marker = new google.maps.Marker({
				position : latlng,
				clickable : true
			});
			marker.setMap(self.map);
			d.mouseenter(function(e) {
				self.addressMouseOver(e, marker);
			});
			d.mouseleave(function(e) {
				self.addressMouseOut(e, marker);
			});
			d.click(function(e) {
				self.addressClicked(e, marker);
			});
		} else {
			d.mouseenter(function(e) {
				self.addressMouseOver(e);
			});
			d.mouseleave(function(e) {
				self.addressMouseOut(e);
			});
		}
	};

	this.addressMouseOver = function(e, marker) {
		if (marker != undefined) {
			marker.setAnimation(google.maps.Animation.BOUNCE);
		} else if (self.map != undefined) {
			self.msgDiv.appendTo($(self.map.getDiv()));
		}
		$(e.currentTarget).css('background-color', '#DDEEF6');
	};

	this.addressMouseOut = function(e, marker) {
		if (marker != undefined) {
			marker.setAnimation(null);
		} else if (self.map != undefined) {
			self.msgDiv.remove();
		}
		$(e.currentTarget).css('background-color', '#fff');
	};

	this.addressClicked = function(e, marker) {
		var ll = marker.getPosition();
		if (!self.map.getBounds().contains(marker.getPosition())) {
			self.map.setCenter(ll);
		}
	};
};

Snapdeal.DealHighlights = function() {
	var page;
	var self = this;
	var block;
	var a;
	var dealUrl;
	var zoneId;
	var scriptLoaded = false;

	this.init = function(page) {
		self.page = page;
		$("#see-all-locations").click(function(e) {
			self.tabsClicked(e);
			return false;
		});
	};

/*	this.closeDealDetail = function(e) {
		$('.tab-content').hide();
		$('a.current').removeClass('current');
	};
*/
	this.googleScriptLoaded = function() {
		$.ajax({
			url : "/json/dealbypageurl/vendoroutlets/" + self.dealUrl + "/"
					+ self.zoneId,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				self.ajaxResponse([ data, self.block ], self.a,
						self.updateLocationTab);
			}
		});
	};

	this.tabsClicked = function(e) {
		self.block = $('#see-all-locations-out');
		self.block.toggle();
		self.dealUrl = $('input.[name="dealPageUrl"]').val();
		self.zoneId = $('input.[name="dealZoneId"]').val();
		self.a = $(e.currentTarget).find('a');
		self.a.toggleClass('current');
		if (!self.a.hasClass('loaded')) {
			self.page.showLoader(self.block);
			if (!self.scriptLoaded) {
				self.page
						.loadScript('http://maps.google.com/maps/api/js?sensor=false&callback=googleScriptLoaded');
			} else {
				self.googleScriptLoaded();
			}
		}
	};

	this.ajaxResponse = function(params, a, callback) {
		if (!a.hasClass('loaded')) {
			a.addClass('loaded');
			self.page.hideLoader(params[1]);
			callback.apply(self, params);
		}
	}

	this.updateLocationTab = function(data, block) {
		// alert(data);
		var location = new Snapdeal.DealLocation();
		location.init(data, block);
		$('.scrollbar1').tinyscrollbar();
	};

};

Snapdeal.CountdownTimer = function() {
	var self = this;
	var timerCallback;
	var timeout;
	var callbackScope;

	this.init = function(startTime, timeout, callback, callbackScope) {
		self.timeout = timeout;
		self.timerCallback = callback;
		self.callbackScope = callbackScope;
		self.countBack(startTime);
	};

	this.calcage = function(msecs, num1, num2) {
		var s = ((Math.floor(msecs / num1)) % num2).toString();
		if (s.length < 2)
			s = "0" + s;
		return "" + s + "";
	};

	this.getHours = function(milliSec) {
		return self.calcage(milliSec, 3600000, 10000);
	};

	this.getMinutes = function(milliSec) {
		return self.calcage(milliSec, 60000, 60);
	};

	this.getSeconds = function(milliSec) {
		return self.calcage(milliSec, 1000, 60);
	};

	this.countBack = function(milliSec) {
		if (milliSec < 0) {
			return;
		}
		if (self.callbackScope == null) {
			self.timerCallback(milliSec);
		} else {
			self.timerCallback.call(self.callbackScope, milliSec);
		}
		setTimeout(function() {
			self.countBack((milliSec - self.timeout));
		}, self.timeout);
	};
};

Snapdeal.DealTimer = function() {
	var self = this;
	var clock;
	var timer;

	this.init = function(clock, startTime) {
		self.clock = clock;
		self.timer = new Snapdeal.CountdownTimer();
		self.timer.init(startTime, 1000, self.updateDealTimer, self);
	};

	this.updateDealTimer = function(milliSec) {
		if (milliSec < 0) {
			self.clock.html(" --  :  --  : --");
		}
		self.clock.html(self.timer.getHours(milliSec) + "&nbsp;:&nbsp;"
				+ self.timer.getMinutes(milliSec) + "&nbsp;:&nbsp;"
				+ self.timer.getSeconds(milliSec));
	};
};

function onRPXSuccess() {
	var s = window.location.href;
	if (s.indexOf('checkout') != -1) {
		window.location.href = s;
		return;
	}
	if (s.indexOf('?') != -1) {
		window.location.href = s.substring(0, s.indexOf('?'));
	} else {
		window.location.reload();
	}
}

function onRPXFail() {
	var s = window.location.href;
	if (s.indexOf('?') != -1) {
		window.location.href = s.substring(0, s.indexOf('?'))
				+ '?systemcode=503';
	} else {
		window.location.href = window.location.href + '?systemcode=503';
	}
}

jQuery.easing.myEasing = function(x, t, b, c, d) {
	return c * Math.sqrt(1 - (t = t / d - 1) * t) + b;
};

var page;
var highlights;
function googleScriptLoaded() {
	highlights.scriptLoaded = true;
	highlights.googleScriptLoaded();
}

$('.home-image-hover').mouseover(function() {
	onMouseOver(this);
});

$('.home-image-hover').mouseleave(function() {
	onMouseLeave(this);
});

function onMouseOver(context) {
	$(context).find('.home-image-hover').show();
}

function onMouseLeave(context) {
	$(context).find('.home-image-hover').hide();
}

function shareDealLoggedIn(context, trackingID, customUrl, utmContent,
		dealDetail) {
	var link = "", twitterText = "";

	if (customUrl != null) {
		link = customUrl;
	} else {
		link = window.location.href;
	}

	var emailShareLink = $("#homePath").html() + '/refer/redirect/'
			+ trackingID + '?redirectUrl=' + link
			+ '&utm_medium=email&utm_content=' + utmContent;
	var fcbShareLink = $("#homePath").html() + '/refer/redirect/' + trackingID
			+ '?redirectUrl=' + link + '&utm_medium=facebook&utm_content='
			+ utmContent;
	var twtShareLink = $("#homePath").html() + '/refer/redirect/' + trackingID
			+ '?redirectUrl=' + link + '&utm_medium=twitter&utm_content='
			+ utmContent;
	$('#url').val(encodeURIComponent(emailShareLink));
	if (utmContent == "dealShare") {
		twitterText = "Go Snap this awesome deal of " + dealDetail;
	} else {
		twitterText = "I just bought this awesome deal of " + dealDetail;
	}
	var html = '';
	html += '<div class="loggedin-share-ico-outer">';
	html += '<div class="fshare-ico"><a href="http://www.facebook.com/share.php?u='
			+ encodeURIComponent(fcbShareLink) + '" target="_blank"></a></div>';
	html += '<div class="ftweet-ico loggedin-ftweet-ico"><a href="https://twitter.com/share?text='
			+ encodeURIComponent(twitterText)
			+ '&url='
			+ shortenUrl(encodeURIComponent(twtShareLink))
			+ '"'
			+ 'class="twitter-share-button" data-count="none" target="_blank"></a><script type="text/javascript" src="//platform.twitter.com/widget.js;"></script></div>';
	html += '<div class="fmail-ico" id="emailShareText" onclick="showaddressbook()"></div>';
	html += '</div>';
	html += '<div id="emailShareStatus"></div>';
	html += '<div class="share-deal-fields-outer loggedin-share-deal-fields-outer" id="addressbookmails"><div class="share-deal-arrow"></div><div class="share-deal-fields-cont"><div class="share-deal-fields-main">';
	html += '<div class="share-close"></div>';
	html += '<div class="share-deal-head-text">Share this deal with friends :-</div>';
	html += '<div class="loggedin-share-deal-mail-fields-outer">';
	html += '<form>';
	html += '<div>Your Name</div>'
			+ '<div class="recip-name-field"><input type="text" id="referrerName" class="share-email-box required" onblur = "validateReferrerName(true)"/></div>';
	html += '<div> Your friend\'s email addresses</div>'
			+ '<div class="share-deal-form-fld" id="inputEmails">';
	for ( var i = 0; i < 3; i++) {
		if(i==0){
		html += '<div style="overflow:hidden; clear:both">';	
		html += '<span class="share-deal-recip-box-left"><input type="text" name="recepientemail" class="share-email-box required validateEmail" onblur="validateRefferalWidget()"/></span>';
		}else{
			html += '<div style="overflow:hidden; clear:both">';	
			html += '<span class="share-deal-recip-box-left"><input type="text" name="recepientemail" class="share-email-box  validateEmail" onblur="validateRefferalWidget()"/></span>';
		}
		html += '<span class="share-deal-recip-box-right"><input type="text" name="recepientemail" class="share-email-box  validateEmail" onblur="validateRefferalWidget()"/></span>';
		html +='</div>';
	}
	html += '</div><div><input id="emailShareSubmit" type="button" value="Submit" onclick="emailShareSubmitAction()" class="share-email-button" /></div>';
	html += '</form>';
	html += '</div></div>';
	$(context).html(html);
}

function shareDealNLoggedIn(context) {
	var html = '';
	html += '<div class="share-deal-head-bg">Share & Win Cash</div>';
	html += '<div class="share-deal-fields-outer"><div class="share-deal-arrow"></div><div class="share-deal-fields-cont"><div class="share-deal-fields-main">';
	html += '<div class="share-close"></div>';
	html += '<div class="share-deal-head-text">Share this deal with friends :-</div>';
	html += '<div class="share-deal-mail-fields-outer">';
	html += '<div>Enter Your Email Address :</div>';
	html += '<div class="share-deal-mail-fields-cont">';
	html += '<div id="createRefererResponse" class="error" ></div>';
	html += '<div class="share-deal-mail-field"><input type="text" id="refererEmailField" class="share-email-box" value="Enter your Email Address"'
			+ 'onblur="if(this.value==\'\') { this.value=\'Enter your Email Address\'; }"'
			+ 'onfocus="if(this.value==\'Enter your Email Address\') { this.value=\'\'; }"></div>';
	html += '<div class="share-deal-button-field"><input type="submit" value="Submit" id="refererEmail" onclick="refererEmailButtonClick()" class="share-email-button"/><input type="hidden" id="subscriber-referer-email"/></div>';
	html += '</div></div>';
	html += '<div id="shareNLoggedin">';
	html += '<div class="share-ico-outer share-ico-inactive">';
	html += '<div class="fshare-ico fshare-ico-inactive"></div><div class="ftweet-ico ftweet-ico-inactive"></div><div class="fmail-ico fmail-ico-inactive"></div>';
	html += '</div></div>';
	html += '</div></div></div>';
	html += '<div class="share-deal-know_more" align="right">Know More</div>';
	html += '<div class="know-more-outer logout-know-more"><div class="know-more-cont"><div class="know-more-close"></div>';
	html += '<div class="know-more-main"><div class="know-more-text"><b>Share this deal</b> with your friends on Facebook, Twitter & Email. <br>When your referred friend makes his first purchase, we will credit snapdeal cash equal to their paid amount (upto a max of Rs 100) in your account</div></div>';
	html += '</div></div></div>'; 
	$(context).html(html);
}

function shareDealAfterSubmit(trackingID, customUrl, utmContent, dealDetail) {
	var link = "", twitterText = "";
	if (customUrl != 0) {
		link = customUrl;
	} else {
		link = window.location.href;
	}

	var emailShareLink = $("#homePath").html() + '/refer/redirect/'
			+ trackingID + '?redirectUrl=' + link
			+ '&utm_medium=email&utm_content=' + utmContent;
	var fcbShareLink = $("#homePath").html() + '/refer/redirect/' + trackingID
			+ '?redirectUrl=' + link + '&utm_medium=facebook&utm_content='
			+ utmContent;
	var twtShareLink = $("#homePath").html() + '/refer/redirect/' + trackingID
			+ '?redirectUrl=' + link + '&utm_medium=twitter&utm_content='
			+ utmContent;
	$('#url').val(encodeURIComponent(emailShareLink));
	if (utmContent == "dealShare") {
		twitterText = "Go Snap this awesome deal of " + dealDetail;
	} else {
		twitterText = "I just bought this awesome deal of " + dealDetail;
	}

	var html = '';
	html += '<div class="share-ico-outer share-ico-active">';
	html += '<div class="fshare-ico"><a href="http://www.facebook.com/share.php?u='
			+ encodeURIComponent(fcbShareLink) + '" target="_blank"></a></div>';
	html += '<div class="ftweet-ico"><a href="https://twitter.com/share?text='
			+ encodeURIComponent(twitterText)
			+ '&url='
			+ shortenUrl(encodeURIComponent(twtShareLink))
			+ '"'
			+ 'class="twitter-share-button" data-count="none" target="_blank"></a><script type="text/javascript" src="//platform.twitter.com/widget.js;"></script></div>';
	html += '<div class="fmail-ico" id="emailShareText" onclick="showaddressbook()"></div>';
	html += '</div>';
	html += '<div id="emailShareStatus"></div>';
	html += '<div id="addressbookmails" style="display: none">';

	html += '<div class="share-deal-recip-top"></div><div class="share-recip-fields-outer"><div class="share-recip-fields">';
	html += '<form>';
	html += '<div>Your Name</div>'
			+ '<div class="recip-name-field"><input type="text" id="referrerName" class="share-email-box required" onblur = "validateReferrerName(true)"/></div><div id="errorRefferalname" class="error"></div>';
	html += '<div> Your friend\'s email addresses</div>'
			+ '<div class="share-deal-form-fld" id="inputEmails">';
	for ( var i = 0; i < 3; i++) {
		if(i==0){
			html += '<div style="overflow:hidden; clear:both">';	
			html += '<span class="share-deal-recip-box-left"><input type="text" name="recepientemail" class="share-email-box  required validateEmail" onblur="validateRefferalWidget()"/> <div id="errorRefferalEmail" class="error"></div></span>';	
		}else{
			html += '<div style="overflow:hidden; clear:both">';	
		html += '<span class="share-deal-recip-box-left"><input type="text" name="recepientemail" class="share-email-box validateEmail" onblur="validateRefferalWidget()"/> <div id="errorRefferalEmail" class="error"></div></span>';
		}
		
		html += '<span class="share-deal-recip-box-right"><input type="text" name="recepientemail" class="share-email-box validateEmail" onblur="validateRefferalWidget()"/><div id="errorRefferalEmail" class="error"></div></span>';
		html += '</div>';
	}
	html += '</div><div><input id="emailShareSubmit" type="button" value="Submit" onclick="emailShareSubmitAction()" class="share-email-button" onblur="validateRefferalWidget()"/></div>';
	html += '</form>';
	html += '</div></div>';
	$('#shareNLoggedin').html(html);

}

function loadLeftNavImage() {
	$(".ll").each(function(index) {
		$(this).attr("src", $(this).attr("imgPath"));
	});
}

function shortenUrl(url) {
	var ajaxUrl = "/ext/util/generatesdsu/?lu=" + url;
	var shortendUrl;
	$.ajax({
		url : ajaxUrl,
		type : "POST",
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data['status'] == 'success') {
				shortendUrl = data['items'].su;
			} else {
				shortendUrl = url;
			}
		}
	});
	return shortendUrl;
}
function showaddressbook() {
	$('#emailShareStatus').html("");
	$("#addressbookmails").toggle();
}

function emailShareSubmitAction() {
	var validationValue = validateRefferalWidget();
		if(validationValue == false){
			return false;
		}
	var recepientList = "";
	$('input[name="recepientemail"]').each(function() {
		recepientList += $(this).val() + ',';
	});

	var ajaxUrl = "/sendInviteForShare?uid=" + $('#uidField').val() + '&email='
			+ $('#sharerEmail').val() + '&name=' + $('#referrerName').val()
			+ '&recipientList=' + recepientList + '&url=' + $('#url').val()
			+ '&utmcontent=' + $('#utmContent').val() + '&catalogID='
			+ $('#catalogID').val();
	$.ajax({
		url : ajaxUrl,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			if (data['status'] == 'success') {
				$('#emailShareStatus').show();
				$('#emailShareStatus').html(data['message']);
				$('#addressbookmails').hide();
				$('#referrerName').val("");
				$('input[name="recepientemail"]').each(function() {
					$(this).val("");
				});

			} else {
				$('#emailShareStatus').show();
				$('#emailShareStatus').html(data['message']);
				$('#addressbookmails').hide();
				$('#referrerName').val("");
				$('input[name="recepientemail"]').each(function() {
					$(this).val("");
				});
			}
		},
		error : function(data) {
			$('#emailShareStatus').html("Enter Valid Email");
		}
	});
}

function refererEmailButtonClick() {

	$("#createRefererResponse").html("");

	var ajaxUrl = "/isfreshuser?email=" + $('#refererEmailField').val();

	$.ajax({
		url : ajaxUrl,
		type : "POST",
		dataType : 'json',
		success : function(data) {
			if (data['status'] == 'success') {
				$('#sharerEmail').val($('#refererEmailField').val());
				if (data['message'] == 'true') {
					$("#subscriber-referer-email").val(
							$('#refererEmailField').val());

					var ajaxUrl = "/shareDeal?email="
							+ $('#refererEmailField').val() + "&zoneid="
							+ $('#zoneid').val();
					$.ajax({
						url : ajaxUrl,
						type : "GET",
						dataType : 'json',
						success : function(data) {

							$('#uidField').val(data['message']);
							var trackingID = $('#uidField').val();
							var customUrl = $('#url').val();
							$('.share-email-button').addClass('submit-inactive');
							$('#refererEmailField').attr('readonly', true);
							shareDealAfterSubmit(trackingID, customUrl, $(
									'#utmContent').val(), $('#dealDetail')
									.val());
						},
						error : function() {
							shareDealNLoggedIn();
						}
					});

				} else {

					var ajaxUrl = "/shareDeal?email="
							+ $('#refererEmailField').val();
					$.ajax({
						url : ajaxUrl,
						type : "GET",
						dataType : 'json',
						success : function(data) {

							$('#uidField').val(data['message']);
							//  $('#msg').html('');
							var trackingID = $('#uidField').val();
							var customUrl = $('#url').val();
							$('.share-email-button').addClass('submit-inactive');
							$('#refererEmailField').attr('readonly', true);
							shareDealAfterSubmit(trackingID, customUrl, $(
									'#utmContent').val(), $('#dealDetail')
									.val());
						},
						error : function() {
							// $('#msg').html('');
							shareDealNLoggedIn();
						}
					});
				}
			} else {
				$("#createRefererResponse").html(
						"Please enter a valid email address");
			}
		}
	});
}

function validateReferrerName(removeError) {
		
	if(removeError) $('.error').remove();
	
	var submitForm = true;
	
	$("input[id=referrerName]").each( function() {
		if(myTrim($(this).val()).length == 0) {
			submitForm = false;
			$(this).after('<div class="error">This field is required.</div>');
		}
	});
	
	return submitForm;
}
		

			
function validateRefferalWidget() {

	$('.error').remove();
	var submitForm = true;
	
	$('.required').each(function() {
		if (myTrim($(this).val()).length == 0) {
			submitForm = false;

			$(this).after('<div class="error">This field is required.</div>');
		}
	});

	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	$('.validateEmail').each(function() {
		var address = $(this).val();
		if (myTrim(address).length > 0 && reg.test(address) == false) {
			$(this).after('<div class="error">Please enter a valid email address.</div>');
			submitForm = false;
			}
	});
	
	return submitForm;
}

function myTrim(str) {	return str.replace(/^\s+/,""); }
	
function showMyCart() {
	window.cartService.showCart(false);
	window.cartService.fireOmnitureScript("showCart");
	return false;
} 
	
$(document).ready(function() {
	page = new Snapdeal.HomePage();
	page.init();
});

$('html').click(function () { 
	$(".citylist-outer, .all-categories-drop-outer, .cust-support-outer, .myaccount-list-outer, .get-daily-alerts-outer").hide(); 
	$(".city-drop-outer").children().eq(0).addClass("city-drop").removeClass("active-drop-tab");
	$(".csupport-drop-outer").children().eq(0).addClass("csupport-drop").removeClass("active-drop-tab");
	$(".myaccount-drop").removeClass("myacct-active-drop-tab");
	$(".all-categories-drop-box").removeClass('all-categories-uparrow');
});


$('.citylist-outer, .city-drop, .city-drop-outer, .all-categories-drop-outer, .all-categories-drop-box, .cust-support-outer, .csupport-drop, .left-categories-head, .myaccount-list-outer, .myaccount-drop, .get-daily-alerts-outer, .get-deal-alerts, #subscriptionOverlay').click(function (event) {

	//.left-categories-cont
	if( $(this).attr('class') == "city-drop" ) { 
		$(".all-categories-drop-outer, .cust-support-outer, .get-daily-alerts-outer, .myaccount-list-outer").hide(); 
		$(".csupport-drop-outer").children().eq(0).removeClass("active-drop-tab"); 
		$(".myaccount-drop").removeClass("myacct-active-drop-tab");
		$(".all-categories-drop-box").removeClass('all-categories-uparrow');
	}
	
	if( $(this).attr('class') == "all-categories-drop-outer" || $(this).attr('class') == "all-categories-drop-box" || $(this).attr('class') == "left-categories-head" ) {
			$(".citylist-outer, .cust-support-outer, .get-daily-alerts-outer, .myaccount-list-outer").hide(); 
			$(".csupport-drop-outer").children().first().removeClass("active-drop-tab");
			$(".city-drop-outer").children().first().removeClass("active-drop-tab");
			$(".myaccount-drop").removeClass("myacct-active-drop-tab");
    }
	
	if( $(this).attr('class') == "csupport-drop" || $(this).attr('class') == "left-categories-head") { 
		$(".all-categories-drop-outer, .get-daily-alerts-outer, .myaccount-list-outer").hide();
		$(".myaccount-drop").removeClass("myacct-active-drop-tab");
		$(".all-categories-drop-box").removeClass('all-categories-uparrow');
	}
	
	if( $(this).attr('class') == "myaccount-drop") {
		$(".all-categories-drop-outer, .cust-support-outer, .citylist-outer, .get-daily-alerts-outer").hide(); 
		$(".csupport-drop-outer").children().eq(0).removeClass("active-drop-tab"); 
		$(".city-drop-outer").children().first().removeClass("active-drop-tab");
		$(".all-categories-drop-box").removeClass('all-categories-uparrow');
	}
	
	if( $(this).attr('class') == "selected-city get-deal-alerts" ) {
		$(".all-categories-drop-outer, .cust-support-outer, .myaccount-list-outer").hide(); 
		$(".csupport-drop-outer").children().eq(0).removeClass("active-drop-tab"); 
		$(".myaccount-drop").removeClass("myacct-active-drop-tab");
		$(".all-categories-drop-box").removeClass('all-categories-uparrow');
	}
	
	event.stopPropagation();
});

$('html').click(function(e){
	var asdsd = $(e.target).attr('class');
	if (! ($(e.target).attr('class') == 'subscription-left-cont') && !($(e.target).attr('id') == 'subscription-left-form') && !($(e.target).attr('class') == 'subscription-form-cont') && !($(e.target).attr('class') == 'subription-field subscriptionCityField ac_input') 
			&& !($(e.target).attr('class') == 'overhid') && !($(e.target).attr('class') == 'subscription-text') && !($(e.target).attr('id') == 'subriptionSubmit') && !($(e.target).attr('class') == 'subscription-left-arrow') && !($(e.target).attr('id') == 'subcriptionError') && !($(e.target).attr('class') == 'dnm-button fnt-tahoma')) {
		$(".lightBox-overlay").hide();
		$('body').removeClass('overhid');
    }
});

$("#orderStatus, #voucherResend").click(function () { 
	$(".cust-support-outer").hide();
	$(".csupport-drop-outer").children().eq(0).addClass("csupport-drop").removeClass("active-drop-tab");
});

function pickCategory(obj) {
	
	var tpStr = $(obj).html().toString();
	var tpStrRendered = (tpStr.length > 20) ? tpStr.substring(0, 18)+'...' : tpStr;	
	
	if( tpStr.indexOf('&') != -1 ) {
        tpStrRendered = (tpStr.length > 20) ? tpStr.substring(0, 20)+'...' : tpStr;
}
	
	$(".all-categories-drop-box").html( tpStrRendered ).attr('catId', $(obj).attr('catId') ).attr('vId', $(obj).attr('vId') ).removeClass('all-categories-uparrow');
	
	if( $(obj).attr('catId') != '0' && $(".all-categories-list").first().children().first().children().html() != "All Categories" ) {
		$(".all-categories-list").first().prepend("<li><a onclick='pickCategory(this)' catid='0' vId='all'>All Categories</a></li>");
	}
	
	if( $(obj).attr('catId') == '0') { $(".all-categories-list").first().children().eq(0).remove(); }
	
	$(".all-categories-drop-outer, .search-error-outer").hide();
	$("#searchBoxOuter").removeClass("search-boxes-error");
	
	if( $(obj).attr('vId') == "p" || $(obj).attr('vId') == "t" || $(obj).attr('vId') == "l" ) {
		$("#productResultsFound").show();
	}
	
	$("#vertical").val( $(obj).attr('vId') );
	
	cacheAS = {};
	cacheURL = {};
	moreCatIndex = false;
	showMoreCatgrs = false;
}

$(".minus-collaps, .plus-expand").click(function () {
	if( $(this).parent().parent().parent().children().eq(1).css('display') == "block" ) {
		$(this).parent().parent().parent().children().eq(1).hide();
		$(this).addClass("plus-expand").removeClass("minus-collaps");
	} else {
		$(this).parent().parent().parent().children().eq(1).show();
		$(this).addClass("minus-collaps").removeClass("plus-expand");
	}
});

if( $("#keyword").val() == "" ) {
	$("#keyword").val("Search for a brand, product or a specific item");
	$("#keyword").addClass('search-box-text-unactive');
}

if( $("#keyword").val() == "Search for a brand, product or a specific item" ) {
	$("#keyword").addClass('search-box-text-unactive');
}

$("#keyword").focus(function() {
	if ( $(this).val() == "Search for a brand, product or a specific item" ) {
	     $(this).val('').removeClass('search-box-text-unactive');
	     $(".search-error-outer").hide();
	     $("#searchBoxOuter").removeClass("search-boxes-error");	
	}
}).blur(function() {
	if ( $(this).val() == "" ) {
	     $(this).val('Search for a brand, product or a specific item').addClass('search-box-text-unactive');
	     $(".search-error-outer").hide();
	     $("#searchBoxOuter").removeClass("search-boxes-error");	
	}
});

function setFltrFxd() {
    
	if(window.pageYOffset >= 111 && $(".filters").is(":visible") && $(document).height() > 1000 ) {
        $(".filters, #tpHdng").css('top', '4px');
        $(".filters").css('float', 'none');
        $(".filters, #tpHdng").css('position', 'fixed');
        $("#tpHdng").css('background-color', 'white');
        $(".filter-prod-head-outer, .getawayHome-head-outer").css('width', 765);

        if( ( $(document).height() - $(window).scrollTop() ) < 1050 && parseInt( $(".filters").css('height') ) > 355 ) {
        	$(".filters").css('top', "-400px");
        } else {
        	$(".filters").css('top', '4px');
        }
        
    } else {
        //$("#newFltrBlk").css('height', 'auto');
        $(".filters, #tpHdng").css('top', 'auto');
        $(".filters").css('float', 'left');
        $(".filters, #tpHdng").css('position', 'relative');
        $(".filter-prod-head-outer, .getawayHome-head-outer").css('width', 'auto');
        $("#tpHdng").css('background-color', 'none');
    }
}
  
//if( !$.browser.msie ) { window.onscroll = setFltrFxd; }

$(".hit-ss-logger").live("click", function() {
	vwSrchdPrd($(this), $(this).attr("href") );
});


function vwSrchdPrd(obj, url) {
	// sending the search string with productId
	$.ajax({
		url : '/json/ssLogger',
		dataType: 'json',
		type :'GET',
		async: false,
		cache: false,
		data : {
			query : $("#lastKeyword").val(),
			cid : $(obj).attr("categoryId"),
			v : $(obj).attr("v"),
			pogId:$(obj).attr("pogId"),
			pos : $(obj).attr("pos")
		},
		success : function(data) {
			var ssdto = data.ssdto.query + ';' + data.ssdto.category + ';' + data.ssdto.vertical;
			Snapdeal.Cookie.set('searchString', ssdto, 3);
		}
	});
}

$(".hit-ss-logger-deal").live("click", function() {
	vwSrchdDeal($(this), $(this).attr("href") );
});

function vwSrchdDeal(obj, url) {
	// sending the search string with productId
	$.ajax({
		url : '/json/ssLogger-Deal',
		dataType: 'json',
		type :'GET',
		async: false,
		cache: false,
		data : {
			query : $("#lastKeyword").val(),
			catalogid : $(obj).attr("catalogId"),
			pos : $(obj).attr("pos")						
		},
		success : function(data) {
			var ssdto = data.ssdto.query + ';' + data.ssdto.category + ';' + data.ssdto.vertical;
			Snapdeal.Cookie.set('searchString', ssdto, 3);
			//window.location.href = url;
		}
	});
}

function clickGo(e) {
    if (e.keyCode == 13) {
    	$("#changeBackToAll").val(false);
    	submitSearchForm('go_header');
        return true;
    } else {
    	return false;
    }
}

function submitForViewAll(obj) {
	$(".all-categories-drop-box").attr( 'catId', $(obj).attr('catgrId') );
	$("#vertical").val( $(obj).attr('v') );
	$("#clickSrc").val("dontGo");
	$("#category").val($(obj).attr('catgrId'));
	$('#changeBackToAll').val(true);
	submitSearchForm('submitForViewAll');
}

function searchOnSubCat(catId, vertical) {
	$("#categoryId").val(catId);
	$("#vertical").val(vertical);
	$(".all-categories-drop-box").attr('catid', catId);
	$("#clickSrc").val("dontGo");
	//$("#submitDealsNearMeExplore").click();
	submitSearchForm('searchOnSubCat');
} 

var totItmsFound = 0; var ctlgStr = '';
var searchedHtmlStr = '';
var s4Str = '';
var modfdTxt = '';

$(".filter-prod-text").each(function(i, e) {
	var nav = 'href="#nav'+i+'"';
	if (i <= 3){
		s4Str += "<a "+nav+"><span class='searcResult-text-match'>"+$(e).text().split("(")[0]+"</span>";
		if(i == 3) { s4Str += "</a>"; } else {s4Str += "</a>, ";}
	}
	ctlgStr += "<a "+nav+"><span class='searcResult-text-match'>"+$(e).text().split("(")[0]+"</span>";
	
	if( $(".filter-prod-text").size() == (i+1)  ) { ctlgStr += "</a>"; } 
	else { 	ctlgStr += "</a>, ";  }
	
	totItmsFound += parseInt( $(e).text().split("(")[1] );
});

$("#fndInAll").html(totItmsFound);

if( $("#foundInAll").val() == true || $("#foundInAll").val() == "true" ) {
	$(".searchResult-text").hide();
} 

if (totItmsFound > 500 || $(".filter-prod-text").size() > 4 ) {
	
	if(totItmsFound > 500 ) {searchedHtmlStr = '500+'; } else {searchedHtmlStr = totItmsFound;}
	s4Str += " and <span style='cursor:pointer; color:#1954B6' id='catCnt'><strong>"+($(".filter-prod-text").size() - 4)+"</strong> other categories.</span>";
	
	if( $(".filter-prod-text").size() > 4 )  {	modfdTxt = s4Str; }
	else { modfdTxt = ctlgStr; }
	
} else {
	searchedHtmlStr = totItmsFound;
	modfdTxt = ctlgStr; 
}

$(".searchResult-text").html('<strong>'+searchedHtmlStr+' items</strong> found for <span class="searcResult-text-head"></span> in '+modfdTxt);
if($(".searchResult-text span.searcResult-text-head")[0]!=null){
	$(".searchResult-text span.searcResult-text-head")[0].textContent='"'+$("#keyword").val()+'"';
}

$('#resultsOnPage').val(totItmsFound);

$("#catCnt").click( function() {
	$(".searchResult-text").html('<strong>'+searchedHtmlStr+' items</strong> found for <span class="searcResult-text-head"></span> in '+ctlgStr);
	if($(".searchResult-text span.searcResult-text-head")[0]!=null){
		$(".searchResult-text span.searcResult-text-head")[0].textContent='"'+$("#keyword").val()+'"';
	}
});



function submitSearchForm(clickSrc) {
	
		var isCat            = false;
		var isKeyword        = false;
		var v                = $("#vertical").val();
		var isSuggested      = $("#suggested").val();
		var selectedLocality = $('#selectedLocality').val();
		
		$("#categoryId").val( $(".all-categories-drop-box").attr('catId') );
		$("#foundInAll").val(false);
		$("#clickSrc").val(clickSrc);
		
		isCat     = ( $(".all-categories-drop-box").attr('catId') != '0' ) ? true : false;
		isKeyword = ( $("#keyword").val() == "Search for a brand, product or a specific item" ||  $("#keyword").val() == "" ) ? false : true;
		
		if( isCat == false && isKeyword == false ) {
			$("#searchBoxOuter").addClass("search-boxes-error");	
			$(".search-error").html('Please choose a category or enter a keyword to search');
			$(".search-error-outer").show();
			return false;			
		}
		
		if(!isKeyword) {$("#keyword").val('');}
		
		var kwStr = $("#keyword").val();
		
		if( isKeyword && ( kwStr.length == 0 ) ) { //&& kwStr.length < 3 
			$("#searchBoxOuter").addClass("search-boxes-error");	
			$(".search-error").html('Please enter atleast a character');
			$(".search-error-outer").show();
			return false;
		}
		
		if (($('.all-categories-drop-box').attr('vid')=='l' || $('.all-categories-drop-box').attr('vid')=='t') && (Snapdeal.Cookie.get('sc') == 1)) {
			headerSubscription($('.all-categories-drop-box').html());
			return false;
		}
		
		if( clickSrc == 'go_header' ){
			$("#changeBackToAll").val(false);
		}		
		
		$('#formSearch').submit();            
            return true;
       
}

$('.sort-value').each( function() {
	if( $(this).html() == $('.sort-by-box span').html() ) { 
		//$(this).hide(); 
	}  
	else { 
		//$(this).show(); 
	}
});	

var cacheAS = {};
var cacheURL = {};
var moreCatIndex = false;
var showMoreCatgrs = false;

$.widget( "custom.catcomplete", $.ui.autocomplete, {	
	_renderMenu: function( ul, items ) {
				
		var self = this, currentCategory = "", afterOne = 0, orangeClass = '', cntrGrp = 0, showMorHtm = '';

		$.each( items, function( index, item ) {	
			orangeClass = '';
			
			if(moreCatIndex == true && currentCategory != "" && afterOne == 0 && item.category != currentCategory && $(".all-categories-drop-box").attr('catId') != "0"  ) {
				orangeClass = ' orangeClass';
				afterOne = 1;
			}
			
			
			if(  item.v == "showMore" ) { //showMoreCatgrs == true && 
				orangeClass = ' orangeClass';
				showMorHtm += "<li class='ui-autocomplete-category"+orangeClass+"' vid="+item.v+" cid="+item.cid+">" + item.category + "</li>";
			}
			
			if( item.category != "Match found in more categories" ) {
				orangeClass = '';
			}
			 
			if ( item.category != currentCategory && item.v != "showMore" ) {
				//console.log(cntrGrp + "--"+showMorHtm);
				if( cntrGrp == 1) { ul.append(showMorHtm);  }
				ul.append( "<li class='ui-autocomplete-category"+orangeClass+"' vid="+item.v+" cid="+item.cid+">" + item.category + "</li>" );
				currentCategory = item.category;
				cntrGrp++;
			}
			
			$(".ui-autocomplete li").last().attr('v', item.v);
			$(".ui-autocomplete li").last().attr('cid', item.cid);
			$(".ui-autocomplete li").last().children().attr('v', item.v);
			$(".ui-autocomplete li").last().children().attr('cid', item.cid);
			
			self._renderItem( ul, item );
			
		});
		
		
		var kwrdStr = $("#keyword").val().toString().trim();
		kwrdStr = kwrdStr.replace(/[^a-zA-Z0-9' ]/g, "");
		kwrdStr = kwrdStr.trim();
		var kwrdArr = kwrdStr.split(" ");
		
		$(".ui-autocomplete li").each(function() {
			if( $(this).hasClass("ui-autocomplete-category") == false ) 
			{
				var obj = $(this);
				$.each( kwrdArr, function(index, vaule) {
					if( obj.children().html() != null && vaule != "" && vaule != "b" ) {
						var tpmLTMH = obj.children().html().toString().trim();
						var regex = new RegExp("("+vaule+")","ig");
						tpmLTMH = tpmLTMH.replace( regex, "<b>$1</b>");
						obj.children().html(tpmLTMH);
					}

				});				
			}
			
		});
		
		if( $(".orangeClass").html() != "Match found in more categories" ) { $(".orangeClass").removeClass("orangeClass"); }
		
	}
});




$(function() {	
	
	$( "#keyword" ).catcomplete({
		source: function( request, response ) {
					var term = request.term;
					var termUrl = "q="+$("#keyword").val().toString().trim().replace(/[^a-zA-Z0-9' ]/g, "")+"&catId="+$(".all-categories-drop-box").attr('catId')+"&v="+$(".all-categories-drop-box").attr('vId');
					
					if ( ( term in cacheAS ) && ( termUrl in cacheURL )) {
						response( cacheAS[ term ] );
						if( cacheAS[ term ][0].label == "No items found" ) {
							$(".ui-autocomplete").hide();
						}
						
						return;
					}
					
					var serchWord = $("#keyword").val().toString().trim().replace(/[^a-zA-Z0-9' ]/g, "");
					serchWord = serchWord.trim();
					
					if( serchWord.length < 3 ) { return false; }
					
					$.ajax({
						url: "/json/search/autoSuggestion",
						dataType: "json",
						data: {
							q: serchWord,
							catId: $(".all-categories-drop-box").attr('catId'),
							v: $(".all-categories-drop-box").attr('vId')
						},
						success: function(data) {
														
							moreCatIndex = false;
							var getrespose = parseAutoSuggestData(data, term);
							cacheURL[termUrl] = termUrl;
							response(getrespose);
							
							if( getrespose[0].label == "No items found" ) {$(".ui-autocomplete").hide();}  
						}
					});
					
					 
				},
		minLength: 3,
		autoFocus: false,
		select: function( event, ui) {
			$("#suggested").val(true);
			$("#keyword").val(ui.item.value);
			$("#vertical").val( ui.item.v );
			if( ui.item.v != "l" ) {
				$("#strLocation, #localityAutocomplete").attr('disabled', true);
			} else {
				$("#strLocation").attr('disabled', false);
			}
			
			$('a[catId="'+ui.item.cid+'"][vid="'+ui.item.v+'"]').click();
			
			submitSearchForm('suggested');
		},
		change: function() {$("#strLocation").attr('disabled', false); $("#suggested").val(false);},
		delay: 0,
		disabled: false
	});
		
});


var refnSearchData = [];
function parseAutoSuggestData(data, term) {
	//data = [{"ssd":{"cid":17,"cn":"Men's Apparel","moreIndex":false,"sgns":{"sn":"reebok ipl tshirts"},"v":"p"}},{"ssd":{"cid":31,"cn":"Perfumes, Beauty & Health","moreIndex":false,"sgns":[{"sn":"reebok deodorants"},{"sn":"reebok reegame men deo 150ml"},{"sn":"reebok reeload men deo 150ml"},{"sn":"reebok reecharge men deo 150ml"},{"sn":"reebok reeplay men deo 150ml"},{"sn":"reebok reenergize men deo 150ml"},{"sn":"reebok reebounce deo women 75ml"},{"sn":"reebok reefresh deo women 75ml"},{"sn":"reebok reesport men deo 150ml"},{"sn":"reebok"}],"v":"p"}},{"ssd":{"cid":37,"cn":"Watches, Bags & Accessories","moreIndex":false,"sgns":[{"sn":"reebok black men's aviator sunglasses"},{"sn":"reebok aviator"},{"sn":"reebok aviators"},{"sn":"reebok black aviator sun glass"}],"v":"p"}}];
	
	refnSearchData = [];
	showMoreCatgrs = false;
	if( data != '[]' && data != null && data != '') {
		
		$.each(data, function(index) {
			var cn = this.ssd.cn;
			var vId = this.ssd.v;
			var subList = this.ssd.sgns;
			var cid = this.ssd.cid; 
			if(this.ssd.moreIndex == true && $(".all-categories-drop-box").attr('catId') != "0" ) {
				moreCatIndex = true;
				refnSearchData.push( { label: null, category: "No results in "+$('a[catId="'+$(".all-categories-drop-box").attr('catId')+'"][vid="'+$(".all-categories-drop-box").attr('vId')+'"]').html(), v: "" } );
				refnSearchData.push( { label: null, category: "Match found in more categories", v: "" } );
			}
			
			if( $.isArray(subList) ) {
				
				
				if( showMoreCatgrs == false && moreCatIndex == false && this.ssd.moreIndex == false && $(".all-categories-drop-box").attr('catId') != "0" ) {
					refnSearchData.push( { label: null, category: "Match found in more categories", v: "showMore", cid: "" } );
					showMoreCatgrs = true;
				}
				
				$.each(subList, function(indixi) {
					refnSearchData.push( { label: this.sn, category: cn, v: vId, cid: cid } );					
				});
				
			} else {
				
				if(subList!=null){
					refnSearchData.push( { label: subList.sn, category: cn, v: vId, cid: cid  } );
				}
				
				
				if( showMoreCatgrs == false && moreCatIndex == false && this.ssd.moreIndex == false && $(".all-categories-drop-box").attr('catId') != "0" ) {
					refnSearchData.push( { label: null, category: "Match found in more categories", v: "showMore", cid: "" } );
					showMoreCatgrs = true;
				}
				
			}
	    });
		
	} else {
		refnSearchData.push( { label: "No items found", category: "", v: "" } );
		//No suggestions found, please click on GO to search.
	}
	
	//if( moreCatIndex == false ) { cacheAS[ term ] = refnSearchData; }
	cacheAS[ term ] = refnSearchData;
	
	return refnSearchData;
}



$(function() {
	$("#back-top").hide();	    		
	$(function () {
		$(window).scroll(function () {
			if ($(this).scrollTop() > 1000) {
				$('#back-top').fadeIn();
			} else {
				$('#back-top').fadeOut();
			}
		});
		$('#back-top a').click(function () {
			$('body,html').animate({
				scrollTop: 0
			}, 800);
			return false;
		});
	});
});	

/* leftNavi BrandStore Links */

function leftNaviBrandStore(){
	var lstCtr = 0;
	$('.leftNaviBrandLetter').each(function(){
		lstCtr = lstCtr+1;
		$('#leftNaviCharsList-'+lstCtr).children().children().last().addClass('leftNaviBrandLetterLast');
	});
	
	$('.leftNaviBrandsBox').each(function(){
		$(this).children().last().css('padding-bottom','11px');
	});
}

/* leftNavi BrandStore Links */

/* left category hover */

function leftCategoriesHeadHover(leftCatId){
	$(".left-categories-cont-inactive").show();
	$('.'+leftCatId).addClass("left-categories-head-up");
	$('.'+leftCatId).parent().removeClass("left-categories-head-inner");
};

function leftCategoriesHeadOut(leftCatId){
	$(".left-categories-cont-inactive").hide();
	$('.'+leftCatId).removeClass("left-categories-head-up");
	$('.'+leftCatId).parent().addClass("left-categories-head-inner");
};

function leftCategoriesContHover(leftCatContId){
	$('.'+leftCatContId).show();
	$('.left-categories-head-down').addClass("left-categories-head-up");
	$('.left-categories-head-down').parent().removeClass("left-categories-head-inner");
};

function leftCategoriesContOut(leftCatContId){
	$('.'+leftCatContId).hide();
	$('.left-categories-head-down').removeClass("left-categories-head-up");
	$('.left-categories-head-down').parent().addClass("left-categories-head-inner");
};


/* escape key */
/*$(document).keyup(function(e) { 
    if (e.keyCode == 27) {
    	$(".lightBox-overlay").hide();
    	$('body').removeClass('overhid');
    }
});*/

/* new subscription */

function subcriptionFormCont(txt, subscriptionUrl) {
	var html = '';
	html += '<div class="subscription-form-cont">';
	html += 	'<div class="subscription-localDeal-hide">';
	html += 		'<div class="subscription-close" onclick="lightBoxClose();"></div>';
	html += 		'<div class="clear"></div>';
	html += 		'<div class="subscription-text">Please enter city to search for '+txt+' deals </div>';
	html += 	'</div>';
	html += 	'<div class="overhid">';
	html += 		'<div><input type="text" name="textfield" class="subription-field subscriptionCityField" value="Enter your city" /><input type="hidden" name="zoneId" value="" id="subscibeCityHidden"/></div>';
	html += 		'<div><input type="button" name="button" id="subriptionSubmit" class="subription-button" value="Continue" onclick="subcriptionFormsubmit('+subscriptionUrl+');" /></div>';
	html +=			'<div class="error" id="subcriptionError" style="display:none; clear:both">No City found</div>';
	html += 	'</div>';
	html += '</div>';
	return html;
};

function lightBoxClose(){
	$(".all-categories-drop-box").attr('catid','0').attr('vid','all').html('All Categories');
	$(".lightBox-overlay").hide();
	$('.left-categories-cont-inactive').bind('mouseleave', function() { leftCategoriesContOut('left-categories-cont-inactive')} );
	$('.left-categories-head-down').bind('mouseleave', function() {  leftCategoriesHeadOut('left-categories-cont-inactive') });
	$('body').removeClass('overhid');
};

function subscriptionAutocomplete(subscriptionUrl){
	$('.subscriptionCityField').autocomplete1("/json/cities/get/search/city", {
		dataType : 'json',
		parse : function(data) {
			var rows = [];
			for ( var i = 0; i < data.length; i++) {
				var result = data[i].citySearchDTO.name;
				if (result == 'No result found') {
					result = '';
				}
				rows[i] = {
					data : data[i].citySearchDTO,
					value : data[i].citySearchDTO.name,
					result : result
				};
			}
			return rows;
		},
		formatItem : function(item) {
			var name = item.name;
			if (name == 'No result found') {
				name = '<span class="no-result">' + name + '</span>';
			}
			return name;
		},
		minChars : 2,
		max: 1000,
		matchContains: true,
		extraParams: {timestamp : 0}
	}).result(function(event, item) {
		$("#subscibeCityHidden").val(item.pageUrl);
//		var dealUrl = $("#subriptionSubmit").attr('dealUrl'); 
		subcriptionFormsubmit(subscriptionUrl);
	});
};

function subcriptionFormsubmit(subscriptionUrl){
	subscriptionUrl = (subscriptionUrl==undefined ? '' : subscriptionUrl );
	if($("#subscibeCityHidden").val()!=''){
		Snapdeal.Cookie.set('sc', 1, -1);
		if(subscriptionUrl != ''){
			window.location.href = "/" + subscriptionUrl.replace(/\zonePage/g, $("#subscibeCityHidden").val());
		}else{
			if(($('.all-categories-drop-box').attr('vid')=='l' || $('.all-categories-drop-box').attr('vid')=='t')){
				Snapdeal.Cookie.set('zonePageUrl', $("#subscibeCityHidden").val(), 1);
				Snapdeal.Cookie.set('cityPageUrl', $("#subscibeCityHidden").val(), 1);
				$('#formSearch').submit();
			}else{
				window.location.href = "/deals-" + $("#subscibeCityHidden").val();
			}
		}
	}else{
		$('#subcriptionError').show();
	}
};

function updateOmnitureHomePage(eventType, source, category) {
	var ajaxUrl = "/omn/getOmnitureCodeForCityPopup?eventType=" + eventType + "&source=" + source + "&category=" + category;
	$.ajax({
		url : ajaxUrl,
		type : "POST",
		contentType : 'application/json; charset=utf-8',
		dataType : 'html',
		success : function(data) {
			$('body').append(data);
		},
		error : function() {
			$('#ajax-loader-icon').hide();
		}
	});
}

function leftCategorySubs(leftCatText, subscriptionUrl){
	updateOmnitureHomePage("dealCityPopup","left",leftCatText);
	$("#subscription-left-form").html(subcriptionFormCont(leftCatText, subscriptionUrl));
	$(".subscriptionCityField").click(function() { subscriptionAutocomplete(subscriptionUrl.replace(/\'/g,"")); });
	//subscriptionFocusBlur();
//	$('body,html').scrollTop(400);
	$('body').addClass('overhid');
};

function headerSubscription(headerCatText){
	updateOmnitureHomePage("dealCityPopup","search",headerCatText);
	$("#headerSubscriptionOverlay").show();
	$("#subscription-header-form").html(subcriptionFormCont(headerCatText,''));
	$(".subscriptionCityField").click(subscriptionAutocomplete(''));
	//subscriptionFocusBlur();
	$('body,html').scrollTop(0);
	$('body').addClass('overhid');
	$("#headerSubscriptionOverlay").show();
};

function errorHeaderSubscription(headerCatText,catUrl) {
	updateOmnitureHomePage("dealCityPopup", "search", headerCatText);
	$("#headerSubscriptionOverlay").show();
	$("#subscription-header-form").html(subcriptionFormCont(headerCatText, catUrl));
	$(".subscriptionCityField").click(subscriptionAutocomplete(catUrl.replace(/\'/g,"")));
	//subscriptionFocusBlur();
	// $('body,html').scrollTop(0);
	$('body').addClass('overhid');
};


$(".subscriptionCityField").live('focus', function() {
	if ( $(this).val() == "Enter your city" ) {
	     $(this).val('');
	}
});

$(".subscriptionCityField").live('blur', function() {
	if ( $(this).val() == "" ) {
	     $(this).val('Enter your city');
	}
});

$('.ac_results ul').live("click",function(){
	subcriptionFormsubmit(localSubscriptionUrl);
});

function autoPositionForLeftNav() {
	$(".leftSubcatOuter").each(function(a,b) {
		$(this).children(".submenulast").each(function(c,d){
			if( $(d).children().size() == 0 ) { $(d).remove(); }
		});
		$(this).children(".submenulast").last().css('border-right', '0px');
	});

	$(".subnavCont, .left-categories-cont-inactive").show();

	$(".leftSubcatOuter").each( function(a,b){
		var maxHeight = 0;
	    $(b).children(".subnav,.submenulast").each( function(c,d) {
	    	
	    	if( parseInt($(d).css("height")) > maxHeight ) {
	            maxHeight = parseInt($(d).css("height"));
	        }
	    	
	    	if( isNaN(parseInt( $(d).css("height") ) ) ) {
	    		if( parseInt( $(d).height() ) > maxHeight ) {
	    			maxHeight = parseInt($(d).height());
	    		}
	    	}
	        
	    });
	    
	    $(b).children(".submenulast").each( function(e,f){
	    	$(this).css('height',maxHeight);
	    });
	    
	});

	$(".navlink").each(function(a,b) {
		if(a > 0) {
			$(this).mouseover(function(){
				
				var tPos = 0;
				
				if( $("#leftNavNemu").hasClass("left-categories-cont-inactive") ) { var tPos = -200; }
				
				if( $.browser.version == "8.0" && $.browser.msie == true ) {
					var subnavContPos =  tPos - $(this).position().top + ( $(window).height() - parseInt( $(this).children('.subnavCont').height() ) ) - $(".navlink").eq(1).position().top + 40 + $('html').scrollTop();
				} else {
					var subnavContPos =  tPos - $(this).position().top + ( window.innerHeight - parseInt( $(this).children('.subnavCont').css('height')) ) - $(".navlink").eq(1).position().top + 40 + window.pageYOffset;
				}
				
				subnavContPos += -4;
				
				if( $(".header-left").position().top == 0 ) {	subnavContPos = subnavContPos + 104; } 
				else {	subnavContPos = subnavContPos + 208; }
				
				var catHoverPos = (subnavContPos+2) * (-1);
				
				if( subnavContPos > 0 ) { subnavContPos = -1; catHoverPos = -1; }
								
				if( $.browser.version == "8.0" && $.browser.msie == true ) {
					if( parseInt( $(this).children('.subnavCont').height() ) > ( $(window).height() - $(this).position().top ) ) {
						$(this).children('.subnavCont').css('position', 'absolute');
						$(this).children('.subnavCont').css('top', subnavContPos);
						$(this).children('.subnavCont').children('.catHover').css('top', catHoverPos);
					}
				} else {
					if( parseInt( $(this).children('.subnavCont').css('height') ) > ( window.innerHeight - $(this).position().top ) ) {
						$(this).children('.subnavCont').css('position', 'absolute');
						$(this).children('.subnavCont').css('top', subnavContPos);
						$(this).children('.subnavCont').children('.catHover').css('top', catHoverPos);
					}
				}
				
				//subnavContPos = subnavContPos - 4;
				if( $("#leftNavNemu").hasClass("left-categories-cont-inactive") ) {
					$(this).children('.subnavCont').css('position', 'absolute');
					$(this).children('.subnavCont').css('top', subnavContPos);
					$(this).children('.subnavCont').children('.catHover').css('top', catHoverPos);
				}
				
			});
		}
	});
		
	$(".subnavCont, .left-categories-cont-inactive").hide();

	if( $(".wrapper-z > a > .brandimgSize").size() > 0 ){
		$(".wrapper-z > a > .brandimgSize").mouseover(function(){
		    $(this).attr('src', Snapdeal.getResourcePath( $(this).attr('imgSrcCL') ) );
		}).mouseout(function(){
		    $(this).attr('src', Snapdeal.getResourcePath( $(this).attr('imgSrcBW') ) );
		});
	}

	if( $('.productSliderLeftNav').size() > 0 ) {
		if( !( $.browser.version == 8 && $.browser.msie == true ) ) {$(".left-categories-cont-inactive").show(); $(".subnavCont").show();}
		$('.productSliderLeftNav').each(function(a,b){
			$("#"+$(this).attr('id')).bxSlider({ auto:false,easing:'swing',prevText: '',nextText: '',infiniteLoop: false,hideControlOnEnd: true});
		});
		if( !( $.browser.version == 8 && $.browser.msie == true ) ) {$(".subnavCont").hide();$(".left-categories-cont-inactive").hide();}
		$(".productSliderLeftNav > .pager").css('width', 'auto');
	}

		
	$(".subnavCont").each(function(a,b){
		if(a > 0) {
			if( $(this).attr('style').indexOf("text-align") == -1 && $(this).attr('style').indexOf("TEXT-ALIGN") == -1 ) {
				var jug = $(this).attr('style').toString().split(';')[0];
				$(this).attr('style', jug );
			}
		} else {
			$(this).attr('style', 'left: 210px !important;width: 756px;');
		}

	});
	
	$(".subnavCont").eq(0).attr('style', 'left: 210px !important;width: 756px;');
}

/* personalizationWidget */
function getPersonalizationWidgetProducts(priority) {
	var ajaxUrl = Snapdeal.getStaticPath("/json/getPersonalizationWidgetData?priority="+priority+"&pageType=HOME_PAGE&pogId=1&categoryId=1");
	$.ajax({
		url : ajaxUrl,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			if(data != undefined && data != null && data.personalizationWidgetDTO != ""){
				if(priority==1){
					displayLeftPersonalizationWidgetProducts(data.personalizationWidgetDTO,priority);
				}else{
					displayPersonalizationWidgetProducts(data.personalizationWidgetDTO,priority);
				}
				
			}
		},
		error: function(){
	}
	});
}

function displayLeftPersonalizationWidgetProducts(data,priority){
 	var html ='';
      	if(data != undefined && data != null && data != ""){
          	var prodImage = '';
          	var flag=false;
          	html +='<h3 class="left-Category-title" style="margin-left: -4px;">'+(data.heading)+'</h3>';
          	html +='<div class="outer-box-left"><div class="slider-wrapper" id="slider-'+priority+'"><div>';
          	$.each(data.widgetData, function(index, val){
          		if ((index%5 == 0) && index!=0){
          			html +=	'</div><div>';
          			flag=true;
          		}
          		prodImage = (Snapdeal.getResourcePath('')+ val.image).replace('166x194', '113x132');
          		html +=	'<a href="'+Snapdeal.getStaticPath('/'+val.pageUrl)+'?HID='+data.rId+''+(index + 1)+'" title="'+val.name+'">';
          		html +=   '<div class="left-similar-deal-cont">';
          		html +=		'<div class="left-similar-deal-img-cont"><img src="'+prodImage+'" alt="'+val.name+'" width="64"/></div>';
          		html +=     '<div class="left-similar-deal-content">';
          		html += 	'<div class="left-similar-deal-title">'+val.name+'</div>';
          		if(val.price == null && val.voucherPrice == null){
       			html+='<div class="fnt12 prod-prebook">Pre Order at <strong class="blackText">Rs ' + val.displayPrice + '</strong></div>';
				}else{
					html += 	'<div class="similar-deal-price"><span class="'+(val.discount > 0 ? '' : 'no-display')+'">Rs '+val.price+'</span> Rs '+val.displayPrice+'</div>';
				}
          		if(val.freebies!=null && val.freebies.length>0){
       			html += '<div class="freebies_inside" align="left" style="margin-left:-2px;"><img src="'+Snapdeal.getResourcePath('img/freebie/freebie_inside.png?v=1')+'"></div>';
       		}
          		if(val.codValid == true){
          			html += '<div class="similar-deal-cod margnCenter"  align="left"  style="margin-left:-2px;">COD available</div>';
          		}
          		html +=  '</div>';
          		html +=  '</div>';
          		html += '</a>';
  			});
          	html +='</div></div></div>';
          	
          	if(flag==true){
	            html +='<div class="small-white-box">';
	        	html +='<a href="http://www.snapdeal.com/offers/best-discounts"><span style="font-size:12px;color:#0B0B61;font-weight:bold;"></span></a>';
	        	html +="</div>";			           		
          	}
          	
          	$('#personalizationWidget-'+priority).html(html);
          	$('#slider-'+priority).bxSlider({
               auto : false,
               pager : false,
               infiniteLoop: false,
               hideControlOnEnd: true
           });
          	
          	$("a.bx-prev").html('');
           $("a.bx-next").html('');
           
           
      	}
 }

function displayPersonalizationWidgetProducts(data,priority){
	var html ='';
 	if(data != undefined && data != null && data != ""){
      	var prodImage = '';
      	html +='<h3>'+(data.heading)+'</h3>';
      	html +='<div class="similar-deals-cont-outer fnt-tahoma"><div class="slider-wrapper" id="slider-'+priority+'"><div style="width:670px;">';
      	$.each(data.widgetData, function(index, val){
      		if ((index%4 == 0) && index!=0){
      			html +=	'</div><div>';
      		}
      		prodImage = (Snapdeal.getResourcePath('')+ val.image).replace('166x194', '113x132');
      		html +=	'<a href="'+Snapdeal.getStaticPath('/'+val.pageUrl)+'?HID='+data.rId+''+(index + 1)+'" title="'+val.name+'">';
      		html +=   '<div class="similar-deal-cont">';
      		html +=		'<div class="similar-deal-img-cont"><img src="'+prodImage+'" width="113" height="132"/></div>';
      		html += 	'<div class="similar-deal-title" align="center">'+val.name+'</div>';
      		if(val.price == null && val.voucherPrice == null){
  			html+='<div class="fnt12 prod-prebook">Pre Order at <strong class="blackText">Rs ' + val.displayPrice + '</strong></div>';
			}else{
				html += 	'<div class="similar-deal-price" align="center"><span class="'+(val.discount > 0 ? '' : 'no-display')+'">Rs '+val.price+'</span> Rs '+val.displayPrice+'</div>';
			}
      		if(val.freebies!=null && val.freebies.length>0){
   			html += '<div class="freebies_inside"><img src="'+Snapdeal.getResourcePath('img/freebie/freebie_inside.png?v=1')+'"></div>';
   		}
      		if(val.codValid == true){
      			html += '<div class="similar-deal-cod margnCenter">COD available</div>';
      		}
      		html +=  '</div>';
      		html += '</a>';
			});
      	html +='</div></div></div>';
      	$('#personalizationWidget-'+priority).html(html);
      	$('#slider-'+priority).bxSlider({
           auto : false,
           pager : false,
           infiniteLoop: false,
           hideControlOnEnd: true
       });
      	
      	$("a.bx-prev").html('');
       $("a.bx-next").html('');
 	}
	return html;
}

/* personalizationWidget ends */

/* HomePageTopOffer */
function homePageTopOfferWidget(){
	var ajaxUrl = Snapdeal.getStaticPath("/getTopOffers");
	$.ajax({
		url : ajaxUrl,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			if(data != null){
				displayHomePageTopOfferWidget(data);
			}
		},
		error: function(){
		}
	});
}			
		
function displayHomePageTopOfferWidget(data){
	var prodImage = '';
   	var html='';
   	var count=0;
   	$.each(data.topOffer, function(index, val){
   		if(val.offerHeading!="")
   			{
   			count++;
   			}
    });
   	if(data != undefined && data != null && data != "" && data.topOffer.length != 0 && count>0){
    html +='<h3  class="home-page-left-Category-title" style="'+'margin-left: -4px;'+'">Today'+"'s Top Offers"+"</h3>";
    html +="<div class='left-outer-box'>";
        	   
    $.each(data.topOffer, function(index, val){
    	if(val.offerLink!= "" && val.offerHeading!="" && val.imageIcon!= "" && val.offerText!="") {
    	html +='<div class="product-outer">';
    	if(val.offerLink!="null" && val.offerLink!=""){
	    	html +='<a href="'+val.offerLink+'">';
    	}
        html +='<div class="topOffer-img">';
        html +='<img src="'+val.imageIcon+'" alt="'+val.offerHeading+'" height="43" width="37">'; 
        html +="</div>";
        html +='<div class="topOffer-content">';
        html +='<div class="topOffer-content-text" style="color:#000000">'+val.offerHeading+"</div>";
        html +='<div class="topOffer-content-text" style="color:grey">'+val.offerText+"</div>";
        html +="</div>";
    	if(val.offerLink!="null" && val.offerLink!=""){
        html +="</a>";
    	}
        html +="</div>";
    	}
    });

     html +="</div>";
     html +='<div class="left-small-box">';
     html +='<a href="http://www.snapdeal.com/offers/best-discounts?HID=offers_viewall"><span style="font-size:12px;color:#004B91;font-weight:bold;">View All Offers >></span></a>';
     html +="</div>";
     html +="</div>";   	   
   	}
     $('#homePageTopOffer').html(html);
}

autoPositionForLeftNav();


/* for showing top selling products for a search */
function topSearchItems(){
	var kwStr = $("#keyword").val();	
	$.ajax({
		url : "json/topSimilarSearchProducts/get?keyword="+kwStr,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			//alert(data.minProductResponseDTO);
			displayTopSearchProducts(data.minProductResponseDTO);
		}
	});		
	
}

function displayTopSearchProducts(data){
	var kwStr = $("#keyword").val();
	    if(data.productDtos != null){
		   var html ='';
		   html += '<div class="srchwgt_heading"> Top Viewed Products for "'+kwStr+'"</div>';
		   html += '<div class="top_selling_products_con">';
		   html += '<div class="multiple overhid" ><div style="width:700px;">';  
		   $.each(Snapdeal.Utils.fixArray(data.productDtos), function(index, val){
			   if ((index%4 == 0) && index!=0){
          			html +=	'</div><div>';
          		}
			   index = index +1;
			   var prodImage = (Snapdeal.getResourcePath('')+ val.image).replace('166x194', 'small');			   
				html += '<div class="lfloat" style="width:175px"> <a href="'+Snapdeal.getStaticPath('/'+val.pageUrl+'?searchid=topviewed_'+index)+'">';
				html += '<div class="srchwgt_prdct">';
				html += '<div class="srchwgt_thumb"><img src="'+prodImage +'" alt="" /></div>';				
				html += '<div class="srchwgt_prdct_dtl">';
				html += '<div class="srchwgt_title">'+val.name+'<br /> <strong>Rs '+val.displayPrice+'</strong>';
				if(val.freebies != undefined){					
				    html += '<div class="srchwgt_freebie">Freebie inside</div>';					
				}
				html += '</div>';
				html += '</div> </div>';				
				html += '</a> </div>';	 
		   });
		   html += '</div></div></div>';
		   $('.topSearchItems').html(html);
		   $('.multiple').bxSlider({
			   auto : false,
               pager : false,
               infiniteLoop: false,
               hideControlOnEnd: true			      
		  				
		   });
	    }
}

function updateRecentSearchCookie() {
	var keyword = get('keyword');
	var vertical = get('vertical');
	var categoryId = get('categoryId');
	var clickSrc = get('clickSrc');

	if (keyword != undefined && keyword != '') {
		if (clickSrc == 'go_header' || clickSrc == 'suggested'
				|| clickSrc == 'top_searches') {
			var ajaxUrl = '/json/deals/updateRecentSearchCookie?keyword='
					+ keyword + '&vertical=' + vertical + '&categoryId='
					+ categoryId;
			$.ajax({
				url : ajaxUrl,
				dataType : 'json',
				type : "GET",
				cache : false,
				success : function(data) {
					// Snapdeal.Cookie.set("rs",data, "30");
				}
			});
		}
	}
}

function get(name) {
	if ( name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)')).exec(location.search) )
		return decodeURIComponent(name[1]);	    
}

if( $("#topSearchFlagg").val() == "true"){	topSearchItems(); }

// Required these function for callback during jsonp hits to logger server

function noLog() {
}

function logNotFoundImage() {
	setTimeout(logNotFoundImageAfterTimeout, 20000);
}

function logNotFoundImageAfterTimeout() {
	var imagePaths = "";
	$("img").each(function() { 
		var img = $(this); 
		if((img.attr('src').indexOf('png')!=-1 || img.attr('src').indexOf('gif')!=-1 || img.attr('src').indexOf('jpg')!=-1) && 
				(img.attr('src').indexOf('snapdeal.com') != -1)) {
			if(img.attr('naturalWidth') == "0") {
				imagePaths += img.attr('src') + "\t";
			}
		}
	});
	if(imagePaths != "") {
		$.ajax({
    		url : $('#logServiceUrl').val() + '/lg/inf',
    		dataType: 'jsonp',
    	    data: {
    			url: document.URL,
    			ips: imagePaths
    		}
    	});
	}
}

function callback() {
}

$(window).load(function() {
    var loadingTime = (new Date().getTime() - startTime) / 1000; // in seconds

    $.ajax({
    	url : '/json/deals/recentlySearched',
    	dataType : 'json',
    	type : 'GET',
    	cache : false,
    	success : function(data) {
    		var recViwItmLst = ""; var lastRemov = ""; var txtSpan = "";
    		if (data != "" && data != null && data != '[]') {
    			$("#recentSearchedDiv").show();
    			$.each( data, function(index) {
    				recViwItmLst = ""; lastRemov = ""; txtSpan = "";
    				
    				if( index == (data.length-1) ) { lastRemov = "lastRemov"; }

    				if(this.rscd.text != null && this.rscd.text.toString() != '') {
    					
    					if( this.rscd.text != null && this.rscd.text != undefined  ){ txtSpan = this.rscd.text; }
    					
    					recViwItmLst += '<div class="recent-searches-text '+lastRemov+'" onclick="javascript:;window.location.href=\''+Snapdeal.getStaticPath(this.rscd.url)+'\'"><span>'+txtSpan+'</span></div>';
    					$(".recent-searched-cont").append(recViwItmLst);
    					/*
    					if( $(".recent-searched-cont:first .recent-searches-text:last span")[0] != null ) {
    						$(".recent-searched-cont:first .recent-searches-text:last span")[0].textContent = this.rscd.text;
    					}*/
    				}
    			});
    		}
    	}
    });
    
    var logServiceUrl = $('#logServiceUrl').val();
    if(logServiceUrl != '' && logServiceUrl != undefined) {
    	$.ajax({
    		url : logServiceUrl + '/lg/all',
    		dataType: 'jsonp',
    		cache: false,
    	    data: {
    			u: Snapdeal.Cookie.get('u'), 
    			ref: document.referrer,
    			url: document.URL,
    			lt: loadingTime
    		}
    	});
    	
    	$.ajax({
    		url : logServiceUrl + '/lg/iuv',
    		dataType: 'jsonp',
    		cache: true,
    	    data: {
    			url: document.URL
    		},
    		jsonp : false,
    		jsonpCallback : 'callback'
    	});
    }
    if( $("#firstHit").attr('u') == '' ) {
    	loadLeftNavImage();
    } 
	var footerUrl = Snapdeal.getStaticPath("/" + $(".ajaxFooter").attr('u'));
	if(footerUrl != null && footerUrl != "") {
		$(".ajaxFooter").load(footerUrl);
	}
    
});

if( Snapdeal.Cookie.get("cityPageUrl") != null && Snapdeal.Cookie.get("cityPageUrl") != undefined && Snapdeal.Cookie.get("cityPageUrl") != "sdindia" ) {
	$("#mycity").html(Snapdeal.Cookie.get("cityPageUrl").toString().replace(/~/g, " ")).css('text-transform', 'capitalize');
} else {
	$("#mycity").html('Select City');
}

if( $.browser.msie ){
	$('map').remove();
}

function imageOffsetForRating(avgRating){
	if(avgRating < 0.2)
    	return 0;
	else if(avgRating < 0.8)
		return 1;
	else if(avgRating < 1.2)
		return 2;
	else if(avgRating < 1.8)
		return 3;
	else if(avgRating < 2.2)
		return 4;
	else if(avgRating < 2.8)
		return 5;
	else if(avgRating < 3.2)
		return 6;
	else if(avgRating < 3.8)
		return 7;
	else if(avgRating < 4.2)
		return 8;
	else if(avgRating < 4.8)
		return 9;
    return 10;
}

function textForRating(avgRating){
	if(avgRating < 1)
    	return 'Rate this product';
	else if(avgRating < 1.1)
		return 'Not Good';
	else if(avgRating < 2.1)
		return 'Needs That Special Something';
	else if(avgRating < 3.1)
		return 'Average, Ordinary';
	else if(avgRating < 4.1)
		return 'That\'s Good Stuff';
    return 'Perfect. It doesn\'t get any better';
}

if($('#logServiceUrl').val() != '' && $('#logServiceUrl').val() != undefined) {
    window.onerror = function(message, url, linenumber) {
        var log = "Line No.: " +linenumber+" >> "+message+" on "+url;
        log += " for this hit >> " + document.URL;
        $.ajax({
            url : $('#logServiceUrl').val() + '/lg/jse',
            dataType: 'jsonp',
            data: {log:log}
        });
        return false;
    }
}
