'use strict';

export interface IAttributesService extends angular.IAttributes {
    ngMax:string;
}

/* @ngInject */
export function NgMax():angular.IDirective {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function (scope:ng.IScope,
                        element:angular.IAugmentedJQuery,
                        attr:IAttributesService,
                        ngModel:angular.INgModelController) {

            var maxValidator = (function (value) {
                var max = scope.$eval(attr.ngMax);
                if (!isEmpty(value) && parseFloat(value) > parseFloat(max)) {
                    ngModel.$setValidity('ngMax', false);
                    return undefined;
                } else {
                    ngModel.$setValidity('ngMax', true);
                    return value;
                }

                function isEmpty(value) {
                  return angular.isUndefined(value) || value === '' || value === null || value !== value;
                }
            });

            ngModel.$parsers.push(maxValidator);
            ngModel.$formatters.push(maxValidator);

            scope.$watch(attr.ngMax, function () {
                ngModel.$setViewValue(ngModel.$viewValue);
            });
        }
    };
}
