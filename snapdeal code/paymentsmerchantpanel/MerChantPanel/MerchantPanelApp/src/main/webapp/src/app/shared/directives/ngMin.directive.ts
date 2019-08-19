'use strict';
/* @ngInject */

export interface IAttributesService extends angular.IAttributes {
    ngMin:string;
}

/* @ngInject */
export function NgMin():angular.IDirective {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function (scope:ng.IScope,
                        element:angular.IAugmentedJQuery,
                        attr:IAttributesService,
                        ngModel:angular.INgModelController) {


            var minValidator = (function (value) {
                var min = scope.$eval(attr.ngMin);
                if (!isEmpty(value) && value < min) {
                    ngModel.$setValidity('ngMin', false);
                    return undefined;
                } else {
                    ngModel.$setValidity('ngMin', true);
                    return value;
                }
              function isEmpty(value) {
                return angular.isUndefined(value) || value === '' || value === null || value !== value;
              }
            });


            ngModel.$parsers.push(minValidator);
            ngModel.$formatters.push(minValidator);

            scope.$watch(attr.ngMin, function () {
                ngModel.$setViewValue(ngModel.$viewValue);
            });
        }
    };
}
