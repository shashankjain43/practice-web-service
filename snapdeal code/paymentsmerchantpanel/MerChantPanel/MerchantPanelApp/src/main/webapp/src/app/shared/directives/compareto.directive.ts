'use strict';

export interface ICompareTo extends ng.IScope {
    otherModelValue: '=compareTo'
}

export interface ICompareToValidation extends angular.INgModelController {
    $validators : {
        noMatch:any;
    }
}
/* @ngInject */
export function CompareTo():angular.IDirective {
    return {
        require: 'ngModel',
        restrict: 'A',
        scope: {
            otherModelValue: '=compareTo'
        },
        link: function (scope:ICompareTo, element:angular.IAugmentedJQuery, attrs:angular.IAttributes, ngModel:angular.INgModelController) {
            ngModel.$validators.noMatch = modelValue => (modelValue === scope.otherModelValue);
            scope.$watch('otherModelValue', function () {
                ngModel.$validate();
            });
        }
    };
}

