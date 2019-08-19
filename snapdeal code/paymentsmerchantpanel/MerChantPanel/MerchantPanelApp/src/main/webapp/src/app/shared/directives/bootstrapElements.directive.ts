export interface IAttributesService extends angular.IAttributes {
    tooltipShow:string;
}

/* @ngInject */
export function tooltipShow():angular.IDirective {
    return {
    	restrict: 'A',
        link: function (scope:ng.IScope, element:angular.IAugmentedJQuery, attrs:IAttributesService) {

            $("[data-toggle=popover]").popover({html:true})
        }
    }
}
