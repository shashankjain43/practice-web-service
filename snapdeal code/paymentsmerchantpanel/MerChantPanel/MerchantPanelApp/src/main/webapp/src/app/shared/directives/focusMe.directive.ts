
export interface IAttributesService extends angular.IAttributes {
    focusMe:string;
}

/* @ngInject */
export function FocuseMe():angular.IDirective {
    return {
        link: function (scope:ng.IScope, element:angular.IAugmentedJQuery, attrs:IAttributesService, $parse:ng.IParseService,
                        $timeout:ng.ITimeoutService) {

            var model = $parse(attrs.focusMe);
            scope.$watch(model, function (value) {
                if (value === true) {
                    $timeout(function () {
                        element[0].focus();
                    });
                }
            });
            element.bind('blur', function () {
                scope.$apply(model.assign(scope, false));
            });
        }
    }
}
