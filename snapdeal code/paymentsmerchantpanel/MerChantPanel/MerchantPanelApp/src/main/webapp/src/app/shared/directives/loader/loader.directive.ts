import {StorageEnum} from "../../storage/storage.enum";

/* @ngInject */
export function routeLoadingIndicator($rootScope, StorageService): angular.IDirective{
  return {
    restrict:'E',
    templateUrl:'app/shared/directives/loader/loader.tmpl.html',
    link:function(scope: any, elem, attrs){
      scope.isStateLoading = false;
      $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){
        var onboardState = 'base.default.merchant-onboard';
        var userStatus = StorageService.getKey(StorageEnum.userStatus);
        var overallUserStatus = userStatus.KYC && userStatus.BASIC_INFO && userStatus.BANK_INFO;

        if(fromState.name == toState.name == onboardState){
          scope.isLoading = true;
          return;
        } else if(!overallUserStatus && toState.name != onboardState) {
          scope.isStateLoading = false;
            return;
        }
        scope.isStateLoading = true;
      });

      $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
        scope.isStateLoading = false;
      });
    }
  };
};
