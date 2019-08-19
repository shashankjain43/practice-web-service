import {IPermissionService} from "./permission.service";
import IDirectiveLinkFn = angular.IDirectiveLinkFn;
import {PermissionService} from "./permission.service";

interface IHasPermissionAttributes extends ng.IAttributes{
  hasPermission: string;
  isCategory: boolean;
}

/* @ngInject */
export function HasPermission(PermissionService: IPermissionService): angular.IDirective {
  return {
    restrict: 'A',
    link: function(scope:ng.IScope, element:ng.IAugmentedJQuery, attrs:IHasPermissionAttributes) {
    var value = attrs.hasPermission.trim();
    var notPermissionFlag = value[0] === '!';
    if (notPermissionFlag) {
      value = value.slice(1).trim();
    }
    toggleVisibilityBasedOnPermission();
    function toggleVisibilityBasedOnPermission() {
      var isAllowed  = PermissionService.isAllowed(value, attrs.isCategory);
      if(isAllowed && !notPermissionFlag || !isAllowed && notPermissionFlag) {
        element.show();
      }
      else {
        element.hide();
      }
    }
  },
  }
}
