'use strict';

export interface IAttributesService extends angular.IAttributes {
    dateGreaterThan:string;
    dateGreaterThanCurrent:string;
    dateLowerThan:string;
}

/* @ngInject */
export function DateLowerThan($filter:ng.IFilterService):angular.IDirective {
    return {
        require: 'ngModel',
        link: function (scope:ng.IScope,
                        element:angular.IAugmentedJQuery,
                        attrs:IAttributesService,
                        ctrl:ng.INgModelController) {

            var validateDateRange = (function (inputValue) {
              if(attrs.dateLowerThan.split('/').length != 3){
                return;
              }
                var toDate = getShortDateFromString(attrs.dateLowerThan, $filter);
                var fromDate = getShortDateFromString(inputValue, $filter);
                var isValid = isValidDateRange(fromDate, toDate);
                ctrl.$setValidity('dateLowerThan', isValid);
                return inputValue;
            });

            ctrl.$parsers.unshift(validateDateRange);
            ctrl.$formatters.push(validateDateRange);
            attrs.$observe('dateLowerThan', function () {
                validateDateRange(ctrl.$viewValue);
            });
        }
    }
}

/* @ngInject */
export function DateGreaterThan($filter:ng.IFilterService):angular.IDirective {
    return {
        require: 'ngModel',
        link: function (scope:ng.IScope,
                        element:angular.IAugmentedJQuery,
                        attrs:IAttributesService,
                        ctrl:ng.INgModelController) {

            var validateDateRange = (function (inputValue) {
                if(attrs.dateGreaterThan.split('/').length != 3){
                  return;
                }
                var fromDate = getShortDateFromString(attrs.dateGreaterThan, $filter);
                var toDate = getShortDateFromString(inputValue, $filter);
                var isValid = isValidDateRange(fromDate, toDate);
                ctrl.$setValidity('dateGreaterThan', isValid);
                return inputValue;
            });
            ctrl.$parsers.unshift(validateDateRange);
            ctrl.$formatters.push(validateDateRange);
            attrs.$observe('dateGreaterThan', function () {
                validateDateRange(ctrl.$viewValue);
            });
        }
    }
};

/* @ngInject */
export function DateGreaterThanCurrent($filter:ng.IFilterService):angular.IDirective {
    return {
        require: 'ngModel',
        link: function (scope:ng.IScope,
                        element:angular.IAugmentedJQuery,
                        attr:IAttributesService,
                        ctrl:ng.INgModelController) {

            var validateDateRange = (function (inputValue) {
                var fromDate = $filter('date')(attr.dateGreaterThanCurrent, 'short');
                var toDate = $filter('date')(new Date(), 'short');
                var isValid = isValidDateRange(fromDate, toDate);
                ctrl.$setValidity('dateGreaterThanCurrent', isValid);
                return inputValue;
            });

            ctrl.$parsers.unshift(validateDateRange);
            ctrl.$formatters.push(validateDateRange);
            attr.$observe('dateGreaterThanCurrent', function () {
                validateDateRange(ctrl.$viewValue);

            });
        }

    }
}

function getShortDateFromString(inputString, $filter) {
    var dateArray = inputString.split('/');
    return $filter('date')(new Date(dateArray[2], dateArray[1] - 1, dateArray[0]), 'short');
}

function isValidDateRange(fromDate, toDate) {
    if (fromDate == "" || toDate == "")
        return true;
    if (isValidDate(fromDate) == false) {
        return false;
    }
    if (isValidDate(toDate) == true) {
        var days = getDateDifference(fromDate, toDate);
        if (days < 0) {
            return false;
        }
    }
    return true;
}
function getDateDifference(fromDate, toDate) {
    return Date.parse(toDate) - Date.parse(fromDate);
};

function isValidDate(dateStr) {
    if (dateStr == undefined)
        return false;
    var dateTime = new Date(dateStr.replace(/(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3")).getTime();
    //var dateTime = Date.parse(dateStr);
    if (isNaN(dateTime)) {
        return false;
    }
    return true;
};
