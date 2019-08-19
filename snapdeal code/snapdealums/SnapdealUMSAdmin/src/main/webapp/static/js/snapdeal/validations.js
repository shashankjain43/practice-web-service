Snapdeal.ValidateNotEmpty = function() {
	$('.validateNotEmpty').each(function(){
		if($(this).val().length == 0) {
			window.formValid = false;
			$(this).after(window.validationMessages['required']);
		}
	});
	
}

Snapdeal.ValidateLength = function() {

	$('.validateLength').each(function(){
		
		var elemValue = $(this).val();
		var elemName = $(this).attr('name');
		if(elemValue.length < $(this).attr('min')) {
			window.formValid = false;
			$(this).after("<div class='error'>" + elemName + " should not be less than " + $(this).attr('min') + ' characters </div>');
		} else if (elemValue.length > $(this).attr('max')) {
			window.formValid = false;
			$(this).after("<div class='error'>" + elemName + " should not be more than " + $(this).attr('max') + ' characters </div>');
		}
		
	});
	
}

Snapdeal.ValidateEmail = function() {

	$('.validateEmail').each(function(){
		
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		var address = $(this).val();
		if(reg.test(address) == false) {
			$(this).after(window.validationMessages['email']);
			window.formValid = false;
		}
				
	});
	
}

Snapdeal.ValidateNumber = function() {

	$('.validateNumber').each(function(){
		var n = $(this).val();
		if (isNaN(parseInt(n)) || !isFinite(n)) { 
			window.formValid = false;
			$(this).after(window.validationMessages['number']);
		}
		
	});
	
}

Snapdeal.validator = function() {
	
	$('.error').remove();
	
	window.formValid = true;
	
	window.validationMessages = {
		required: "<div class='error'>This field is required.</div>",
		remote: "Please fix this field.",
		email: "<div class='error'>Please enter a valid email address.</div>",
		url: "Please enter a valid URL.",
		date: "Please enter a valid date.",
		dateISO: "Please enter a valid date (ISO).",
		number: "<div class='error'>Please enter a valid number.</div>",
		digits: "Please enter only digits.",
		creditcard: "Please enter a valid credit card number.",
		equalTo: "Please enter the same value again.",
		accept: "Please enter a value with a valid extension.",
		maxlength: "Please enter no more than {0} characters.",
		minlength: "Please enter at least {0} characters.",
		rangelength: "Please enter a value between {0} and {1} characters long.",
		range: "Please enter a value between {0} and {1}.",
		max: "Please enter a value less than or equal to {0}.",
		min: "Please enter a value greater than or equal to {0}."
	}
	
	this.validate = function() {
		this.validateNotEmpty = new Snapdeal.ValidateNotEmpty();
		this.validateLength = new Snapdeal.ValidateLength();
		this.validateEmail = new Snapdeal.ValidateEmail();
		this.validateNumber = new Snapdeal.ValidateNumber();
		return window.formValid;
	}
	
};


Snapdeal.validator.format = function(source) {
	return function() {
		var args = $.makeArray(arguments);
		args.unshift(source);
		return $.validator.format.apply( this, args );
	};
};