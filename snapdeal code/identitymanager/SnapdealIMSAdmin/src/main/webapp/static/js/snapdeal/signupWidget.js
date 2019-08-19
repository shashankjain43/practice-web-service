Snapdeal.signupWidget = function() {
	var signupJson = null;
	var loginPath = Snapdeal.getStaticPath('/login_security_check?');
	var signupPath = Snapdeal.getStaticPath('/signupAjax');
	var termsPath = Snapdeal.getStaticPath('/info/terms');
	var encodedLoginPath = null;
	this.init = function(signupDTO) {
		signupJson = signupDTO;
		if (signupJson.targetUrl) {
			loginPath += 'spring-security-redirect=' + signupJson.targetUrl + "&";
		}
		encodedLoginPath = encodeURI(loginPath);
	};

	this.getHtml = function() {
		var html = '<fieldset id="signin_menu">'
				+ '<h2>Login with</h2>'
				+ '<ul class="network-login">'
				+ '<li><a onClick="return window.widget.fbLogin(\'https://www.facebook.com/dialog/oauth?client_id='
				+ signupJson.fbAppId
				+ '&redirect_uri='
				+ Snapdeal.getStaticPath('/')
				+ 'j_spring_facebook_security_check&scope=email,user_location,user_birthday,user_likes,publish_stream\');" href="javascript: void(0);"><div class="facebook"></div>Facebook</a></li>'
				+ '<li><a onClick="return window.widget.rpxLogin(\'https://snapdeal.rpxnow.com/openid/start?openid_identifier=https%3A%2F%2Fwww.google.com%2Faccounts%2Fo8%2Fid&#38;token_url='
				+ encodedLoginPath
				+ 'source=google\');" href="javascript: void(0);"><div class="google"></div>Gmail</a></li>'
				+ '<li><a onClick="return window.widget.rpxLogin(\'https://snapdeal.rpxnow.com/openid/start?openid_identifier=http%3A%2F%2Fme.yahoo.com%2F&#38;force_reauth=true&#38;display=popup&#38;token_url='
				+ encodedLoginPath + 'source=yahoo\');" href="javascript: void(0);"><div class="yahoo"></div>Yahoo Mail</a></li>' + '</ul>'
				+ '<div class="assured">Be assured, we do not store your passwords.</div>';

		if (signupJson.targetUrl) {
			html += '<div id="targetUrl" style="display:none">' + signupJson.targetUrl + '</div>';
		}

		if (signupJson.showLoginWidget) {
			html += this.getLoginHtml();
		} else {
			html += this.getSignupHtml();
		}
		this.getCSRFToken();
		html += '</fieldset>';
		return html;
	};

	this.getLoginHtml = function() {
		var html = '<div id="login">' + '<div class="or">Or</div>' + '<h2>Sign in</h2>' + '<div class="popup-form">' + '<div id="ajaxSigninResponse" class="error"></div>'
				+ '<div id="prevSource" class="error" style="display:none">You have signed up through <strong><span id="pSource"></span></strong>. Please click the same icon to login again. </div>'
				+ '<form method="post" id="ajaxSignin" action="">' + '<div class="popup-form-row">' + '<div class="leftside"><label for="username">E-mail *</label></div>'
				+ '<input id="j_username" name="j_username" value="" title="username" tabindex="4" type="text" class="popup-form-textbox" onmouseout="return window.widget.checkPrevSource();"/>'
				+ '</div>' + '<div class="popup-form-row">' + '<div class="leftside"><label for="password">Password *</label></div>'
				+ '<input id="j_password" name="j_password" value="" title="password" tabindex="5" type="password" class="popup-form-textbox" />' + '</div>' + '<div class="popup-form-row">'
				+ '<input id="remember" name="_spring_security_remember_me" value="1" tabindex="7" type="checkbox" checked="checked"/>' + '<input type="hidden" name="ajax" value="true"/>'
				+ '<input type="hidden" id="CSRFToken" name="CSRFToken" value=""/>'
				+ '<label for="remember" class="remember">Remember me on this computer.</label>' + '</div>'
				+ '<div class="popup-form-row" align="center"><input id="signin_submit" value="Sign In" tabindex="6" type="submit" onClick="return window.widget.loginFormValidate(); "></div>'
				+ '</form>' + '</div>' + '<p class="forgot trigger" align="right""> <a id="resend_password_link" onClick="return window.widget.loadForgotPass();">Forgot your password?</a> </p>'
				+ '<div class="toggle_container" style="display:none">' + '<div id="forgotPasswordNavigationResponse" class="error"></div>'
				+ '<form  id="forgotPasswordNavigation" name="forgotPasswordNavigation" method="post" action="">' + '<input type="text" value="Enter email to reset it" name="fp_email" id="fp_email"'
				+ 'onblur="if(this.value==\'\') { this.value=\'Enter email to reset it\'; }"' + 'onfocus="if(this.value==\'Enter email to reset it\') { this.value=\'\'; }" style="width: 65%;" />'
				+ '<input type="submit" value="Submit" class="button" style="width: 30%;" onClick="return window.widget.forgotPassword();"/>' + '</form>' + '</div>' + '</div>';

		return html;
	};

	this.getSignupHtml = function() {
		var html = '<div id="signup">' + '<div class="or">Or</div>' + '<h2>Sign up</h2>' + '<div class="popup-form">' + '<div id="ajaxSignupResponse" class="error"></div>'
				+ '<form method="post" id="ajaxSignup" action="">' + '<div class="popup-form-row">' + '<div class="leftside"><label for="username">E-mail *</label></div>'
				+ '<input id="j_username" name="j_username" value="" title="username" tabindex="4" type="text" class="popup-form-textbox"/>' + '</div>' + '<div class="popup-form-row">'
				+ '<div class="leftside"><label for="password">Password *</label></div>'
				+ '<input id="j_password" name="j_password" value="" title="password" tabindex="5" type="password" class="popup-form-textbox" />' + '</div>' + '<div class="popup-form-row">'
				+ '<div class="leftside"><label for="password">Confirm Password *</label></div>'
				+ '<input id="j_confpassword" name="j_confpassword" value="" title="password" tabindex="5" type="password" class="popup-form-textbox" />' + '</div>' + '<div class="popup-form-row">'
				+ '<input type="hidden" name="ajax" value="true"/> ' 
				+ '<input type="hidden" id="CSRFToken" name="CSRFToken" value=""/>'
				+ '<input  type="checkbox" class="checkbox" id="agree" name="agree" />';

		if (signupJson.source) {
			html += '<input type="hidden" id="j_source" name="j_source" value="' + signupJson.source + '"/>';
		}
		if (signupJson.targetUrl) {
			html += '<input type="hidden" id="j_targetUrl" name="j_targetUrl" value="' + signupJson.targetUrl + '"/>';
		}

		html += '<label for="agree" class="remember">I have read and I agree to the <a href="' + termsPath
				+ '" target="_blank">Terms of use.</a></label><br/><label for="agree" generated="true" class="error" style="display: none;">Please accept our policy</label>' + '</div>'
				+ '<div class="popup-form-row" align="center"><input id="signup_submit" value="Sign Up" tabindex="6" type="submit" onClick="return window.widget.signupFormValidate();"></div>'
				+ '</form>' + '</div>' + '</div>';

		return html;
	};

	this.loginFormValidate = function() {
		// validate signup form on keyup and submit
		$("#ajaxSignin").validate({
			rules : {
				j_username : {
					required : true,
					email : true
				},
				j_password : {
					required : true
				}
			},
			messages : {
				j_username : "Please enter a valid email address",
				j_password : {
					required : "Please provide a password"
				}
			},
			submitHandler : function(form) {
				$.ajax({
					type : "POST",
					dataType : "json",
					url : loginPath,
					data : $(form).serialize(),
					success : function(returnData) {
						return window.widget.loginCallback(returnData);
					}
				});
			}
		});
	}

	this.loginCallback = function(data) {
		if (data['status'] == 'success') {
			var s = window.location.href;
			if(data.items != undefined && data.items['targetUrl'] != null && data.items['targetUrl'] != ''){
				s = data.items['targetUrl'];
			}
			$('#ajaxSignin').clearForm();
			$('#signin_menu').hide();
			if (s.indexOf('?') == -1) {
				s += "?";
			} else {
				s += "&";
			}
			window.location.href = s + 'loginSuccess=success';
		} else {
			$('#ajaxSigninResponse').html(data['message']);
		}
	}

	this.signupFormValidate = function() {
		// validate signup form on keyup and submit
		$("#ajaxSignup").validate({
			rules : {
				j_username : {
					required : true,
					email : true
				},
				j_password : {
					required : true,
					minlength : 6
				},
				j_confpassword : {
					required : true,
					minlength : 6,
					equalTo : "#j_password"
				},
				agree : "required"
			},
			messages : {
				j_username : "Please enter a valid email address",
				j_password : {
					required : "Please provide a password",
					minlength : "Your password must be at least 6 characters long"
				},
				j_confpassword : {
					required : "Please provide a confirm password",
					minlength : "Your confirm password must be at least 6 characters long",
					equalTo : "Please enter the same password as above"
				},
				agree : "Please accept our policy"
			},
			submitHandler : function(form) {
				$.ajax({
					type : "POST",
					dataType : "json",
					data : $(form).serialize(),
					url : signupPath,
					success : function(returnData) {
						return window.widget.signupCallback(returnData);
					}
				});
			}
		});
	}

	this.signupCallback = function(data) {
		var s = window.location.href;
		if (data['status'] == 'success') {
			$('#signin_menu').hide();
			var email = $("#j_username").val();
			$('#ajaxSignup').clearForm();
			if (s.indexOf('?') == -1) {
				s += "?";
			} else {
				s += "&";
			}
			window.location.href = s + 'email=' + email + '&systemcode=103&msg=signup';
		} else {
			$('#ajaxSignupResponse').html(data['message']);
		}
	}

	this.rpxLogin = function(url) {
		loginWindow = window.open(url, "OAuthLogin", "menubar=yes,resizable=yes,scrollbars=yes,width=600,height=450,left=150,top=150");
		return false;
	}

	this.fbLogin = function(url) {
		loginWindow = window.open(url, "OAuthLogin", "menubar=yes,resizable=yes,scrollbars=yes,width=850,height=550,left=100,top=100");
		return false;
	}

	this.loadLogin = function() {
		var ajaxUrl = $("#targetUrl").html() ? "/ajaxLogin?targetUrl=" + $("#targetUrl").html() : "/ajaxLogin";
		$.ajax({
			url : ajaxUrl,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				window.widget.init(data);
				$("#signin_box").html(window.widget.getHtml());
				$("fieldset#signin_menu").show('fast');
				$(".signin").toggleClass("menu-open", true);
			}
		});
		return false;
	}

	this.loadSignup = function() {
		var ajaxUrl = $("#targetUrl").html() ? "/ajaxSignup?targetUrl=" + $("#targetUrl").html() : "/ajaxSignup";
		$.ajax({
			url : ajaxUrl,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				$("fieldset#signin_menu").hide('fast');
				window.widget.init(data);
				$("#signin_box").html(window.widget.getHtml());
				$("fieldset#signin_menu").show('fast');
				$(".signin").toggleClass("menu-open", true);
			}
		});
		return false;
	}

	this.loadForgotPass = function() {
		$(".toggle_container").show();
		return false;
	}

	this.forgotPassword = function() {
		var ajaxUrl = "/forgotPassword?";
		ajaxUrl += "email=" + $("#fp_email").val();
	$.ajax({
			url : ajaxUrl,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				$('#forgotPasswordNavigationResponse').html(data['message']);
			}
		});
		return false;
	}

	this.checkPrevSource = function() {
		var ajaxUrl = "/checkSource?";
		ajaxUrl += "email=" + $("#j_username").val()+ "&cs=" + $('#CSRFToken').val();

		$.ajax({
			url : ajaxUrl,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data['status'] == 'success') {
					$('#pSource').html(data['message']);
					$('#ajaxSigninResponse').hide();
					$('#prevSource').show();

				}
			}
		});
		return false;
	}
	
	this.getCSRFToken = function() {
		var ajaxUrl = "/checkCSRF";
		$.ajax({
			url : ajaxUrl,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data['status'] == 'success') {
					$('#CSRFToken').val(data['message']);
				}
			}
		});
		return false;
	}

};
