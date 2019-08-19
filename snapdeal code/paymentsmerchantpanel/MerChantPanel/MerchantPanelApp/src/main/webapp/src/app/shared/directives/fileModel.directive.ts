'use strict';

export interface IAttributesService extends angular.IAttributes {
    fileModel:string;
}

/* @ngInject */
export function fileModel($parse:ng.IParseService):angular.IDirective {
    return {
        restrict: 'A',
        link: function (scope:ng.IScope, element:angular.IAugmentedJQuery, attrs:IAttributesService) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    }
}
