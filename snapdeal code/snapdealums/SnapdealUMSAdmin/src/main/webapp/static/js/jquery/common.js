/* Snapdeal.Cookie START */
Snapdeal.Cookie = {};
Snapdeal.Cookie.data = 'data';

Snapdeal.Cookie.set = function(a_name, a_value, expiredays, path, domain) {
	var expires = new Date();
	expires.setTime(expires.getTime() + (expiredays * 24 * 3600 * 1000));
	var cookieStr = a_name + "=" + escape(a_value) + ((expiredays) ? "; expires=" + expires.toGMTString() : "") + ((path) ? ";path=" + path : ";path=/")
			+ ((domain) ? ";domain=" + domain : ";domain=.snapdeal.com");
	document.cookie = cookieStr;
};

Snapdeal.Cookie.get = function(a_name) {
	if (document.cookie.length > 0) {
		begin = document.cookie.indexOf(" " + a_name + "=");
		if (begin != -1) {
			begin += a_name.length + 2;
			end = document.cookie.indexOf(";", begin);
			if (end == -1)
				end = document.cookie.length;
			return unescape(document.cookie.substring(begin, end));
		}else{
			begin = document.cookie.indexOf(a_name + "=");
			if(begin != -1){
				begin += a_name.length + 1;
				end = document.cookie.indexOf(";", begin);
				if (end == -1)
					end = document.cookie.length;
				return unescape(document.cookie.substring(begin, end));
			}
		}
	}
	return null;
};

Snapdeal.Cookie.remove = function(a_name) {
	var cookieStr = a_name + "=;expires=Thu, 01-Jan-1970 00:00:01 GMT";
	document.cookie = cookieStr;
};
/* Snapdeal.Cookie END */

/* get state list */

Snapdeal.StateList = {};
Snapdeal.StateList.get = function() {
	var states = [ "Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh",
			"Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Orissa", "Punjab", "Rajasthan",
			"Sikkim", "Tamil Nadu", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal" ];
	return states;
};

/*here we gonna write the common utilities, discussed with Rahul*/
Snapdeal.CommonUtils = {};
Snapdeal.CommonUtils.getQueryStringParameter = function(a) {a = a.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");var b = "[\\?&]" + a + "=([^&#]*)";var c = new RegExp(b);var d = c.exec(unescape(window.location.href));if (d == null) {return null;} else {return d[1];}};

/* get state list ends */

/* Form field handling START */
(function() {
	/**
	 * This jQuery Extension adds the ability to access form fields by the
	 * shortcut property .field() which gets and sets input field values by name
	 * using the .find() method.
	 * 
	 * @param string
	 *            inputName
	 * @param mixed
	 *            value (optional)
	 */
	$.fn.field = function(inputName, value) {

		if (typeof inputName != 'string') {
			return false;
		}

		var $inputElement = $(this).find('[name=' + inputName + ']');

		/*
		 * Get request, return the input fields value.
		 */
		if (typeof value === 'undefined') {
			if ($inputElement.length >= 1) {
				switch ($inputElement.attr('type')) {
				case 'checkbox':
					return $inputElement.is(':checked');
					break;
				case 'radio':
					var result;
					$inputElement.each(function(i, val) {
						if ($(this).is(':checked'))
							result = $(this).val();
					});
					return result;
					break;
				default:
					return $inputElement.val();
					break;
				}
			} else {
				return null;
			}
		}

		/*
		 * Set request, set the input field to the value provided and return a
		 * reference to the jQuery selector for the input
		 */
		else {
			switch ($inputElement.attr('type')) {
			case 'checkbox':
				$inputElement.attr({
					checked : value
				});
				break;
			case 'radio':
				$inputElement.each(function(i) {
					if ($(this).val() == value) {
						$(this).attr({
							checked : true
						});
					}
				});
				break;
			case undefined:
				$(this).append('<input type="hidden" name="' + inputName + '" value="' + value + '" />');
				break;
			default:
				$inputElement.val(value);
				break;
			}
			return $inputElement;
		}
	}
})();
/* Form field handling END */

Snapdeal.Utils = function() {
	return {
		/* ReatEasyFix for Arrays START */
		fixArray : function(array) {
			if ($.isArray(array)) {
				return array;
			} else {
				return [ array ];
			}
		}
	/* ReatEasyFix for Arrays END */
	};
}();