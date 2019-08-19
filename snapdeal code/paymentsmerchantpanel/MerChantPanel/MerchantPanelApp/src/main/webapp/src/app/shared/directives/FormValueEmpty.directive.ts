
export function FormValueEmpty():angular.IDirective {
    return {
        link: function (scope:ng.IScope, elm:angular.IAugmentedJQuery) {
            elm.bind('blur', function () {
                if (this.value == "" || this.value == undefined)
                    angular.element(this).addClass('empty');
                else
                    angular.element(this).removeClass('empty');
            });
        }
    }
}
