function getTrackingParams() {
	var TrackingParams = "";
	var arrURLParams = "";
	var sURL = window.document.URL.toString();
	if (sURL.indexOf("?") > 0) {
		var arrParams = sURL.split("?");
		arrURLParams = arrParams[1].split("&");
	}

	var affPrefix = $('#aff').val();
	var arrParamNames = [ 'utm_source', 'utm_content', 'utm_medium',
			'utm_campaign', 'ref', 'utm_term' ];
	var arrParamValues = [ 'null', 'null', 'null', 'null', 'null', 'null' ];
	var size = arrParamNames.length;
	var i = 0;
	var refValue = "";
	var vtCheck = 1;
	for (i = 0; i < arrURLParams.length; i++) {
		var sParam = arrURLParams[i].split("=");
		var j = 0;
		for (j = 0; j < size; j++) {
			if (sParam[0] == arrParamNames[j]) {
				if (sParam[1] != "")
					arrParamValues[j] = sParam[1];
				break;
			}
		}
		if (sParam[0] == 'subid') {
			refValue = sParam[1];
		}

	}
	var referrer = document.referrer;
	for (i = 0; i < size; i++) {
		if (arrParamNames[i] == 'utm_source' && arrParamValues[i] == "null") {
			if (referrer != ""
					&& (referrer.toLowerCase().indexOf("google") > -1
							|| referrer.toLowerCase().indexOf("yahoo") > -1 || referrer
							.toLowerCase().indexOf("bing") > -1)) {
				arrParamValues[i] = "SEO";
				arrParamValues[1]= null;
				arrParamValues[2]= null;
				arrParamValues[3]= null;
				arrParamValues[4]= null;
				arrParamValues[5]= null;
			} else if (referrer != ""
					&& referrer.toLowerCase().indexOf("snapdeal") == -1) {
				arrParamValues[i] = "INLINK";
				arrParamValues[1]= null;
				arrParamValues[2]= null;
				arrParamValues[3]= null;
				arrParamValues[4]= null;
				arrParamValues[5]= null;
			} else {
				arrParamValues[i] = "DIRECT";
				vtCheck = 0;
			}

		}
		if (arrParamNames[i] == 'utm_content') {
			if (referrer != ""
					&& (referrer.toLowerCase().indexOf("google") > -1 || referrer
							.toLowerCase().indexOf("yahoo") > -1)) {
				var decodedStr = decodeURIComponent(document.referrer);
				var pattern = /&q=(.*?)&/gi;
				pattern = pattern.compile(pattern);
				var matcher = decodedStr.match(pattern);
				if (matcher[1]) {
					arrParamValues[i] = matcher[1];
				}
			}
		}

		if (affPrefix != "") {
			if (arrParamNames[i] == 'utm_source') {
				arrParamValues[i] = "CORPORATE";
			} else if (arrParamNames[i] == 'utm_campaign') {
				arrParamValues[i] = affPrefix;
			}
		}
		if (arrParamNames[i] == 'ref' && refValue != "") {
			arrParamValues[i] = refValue;
		}

		TrackingParams = TrackingParams + arrParamNames[i] + '='
				+ arrParamValues[i] + '|';
	}
	if (Snapdeal.Cookie.get('st') == null) {
		Snapdeal.Cookie.set('st', TrackingParams, 90, "/",
				".snapdeal.com");
		Snapdeal.Cookie.set('vt', TrackingParams, 1 / 48, "/",
				".snapdeal.com");
	} else if (vtCheck == 1 || Snapdeal.Cookie.get('vt') == null) {
		Snapdeal.Cookie.set('vt', TrackingParams, 1 / 48, "/",
				".snapdeal.com");
	} else {
		TrackingParams = Snapdeal.Cookie.get('vt');
		Snapdeal.Cookie.set('vt', TrackingParams, 1 / 48, "/",
		".snapdeal.com");
	}
}
