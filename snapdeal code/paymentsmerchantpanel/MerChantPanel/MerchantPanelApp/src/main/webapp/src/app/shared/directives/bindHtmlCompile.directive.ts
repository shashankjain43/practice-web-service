'use strict';
export interface IAttributesService extends angular.IAttributes {
    bindHtmlScope:string;
    bindHtmlCompile:string;
}
/* @ngInject */
export function BindHtmlCompile($compile):angular.IDirective {
    return {
        restrict: 'A',
        link: function (scope:ng.IScope,
                        element:angular.IAugmentedJQuery,
                        attrs:IAttributesService) {
              scope.$watch(() => {
                  return scope.$eval(attrs.bindHtmlCompile);
              },
               (value) => {
                // Incase value is a TrustedValueHolderType, sometimes it
                // needs to be explicitly called into a string in order to
                // get the HTML string.
                element.html(value && value.toString());
                // If scope is provided use it, otherwise use parent scope
                var compileScope = scope;
                if (attrs.bindHtmlScope) {
                  compileScope = scope.$eval(attrs.bindHtmlScope);
                }
                $compile(element.contents())(compileScope);
              });
        }
    };
}
