import IAttributes = angular.IAttributes;
'use strict';

export  interface  ICustomAttributes extends ng.IAttributes {
  keyPress: string;
}


export class appDirective implements angular.IDirective {
  static instance():angular.IDirective   {
    return new appDirective();
  }

  /* @ngInject */
  public KeyPress() {
    return {
      link: function (scope:ng.IScope, $element:ng.IAugmentedJQuery, $attrs:ICustomAttributes) {
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
  }

}


