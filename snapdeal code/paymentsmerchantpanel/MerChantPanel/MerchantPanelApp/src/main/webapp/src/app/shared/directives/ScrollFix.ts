'use strict';
/* @ngInject */
export interface IAttributesService extends angular.IAttributes {
    fixWhen:string;
}
export function ScrollFix():angular.IDirective {
    return function(scope:ng.IScope, element:angular.IAugmentedJQuery, attrs:IAttributesService,$window:ng.IWindowService) {
        angular.element($window).bind("scroll", function() {
            if(window.pageYOffset > attrs.fixWhen)
            {
                angular.element(element).addClass('fixed');
            }
            else
            {
                angular.element(element).removeClass('fixed');
            }
        });
    }
}
