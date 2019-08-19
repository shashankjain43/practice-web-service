"use strict";
'use strict';
var appDirective = (function () {
    function appDirective() {
    }
    appDirective.instance = function () {
        return new appDirective();
    };
    /* @ngInject */
    appDirective.prototype.KeyPress = function () {
        return {
            link: function (scope, $element, $attrs) {
                $element.bind("keydown keypress", function (event) {
                    if (event.which === 13) {
                        scope.$apply(function () {
                            scope.$eval($attrs.keyPress);
                        });
                        event.preventDefault();
                    }
                });
            }
        };
    };
    return appDirective;
}());
exports.appDirective = appDirective;
