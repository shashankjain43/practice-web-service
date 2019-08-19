'use strict';
import {StorageEnum} from "./shared/storage/storage.enum";
/* @ngInject */
export function stateRunBlock($rootScope, $state, $filter, StorageService) {
  $rootScope.$on('$stateChangeStart',
    function (event, toState, toParams, fromState, fromParams) {
      var publicStates = ['qrcode', 'authentication.login', 'base.default.account.content.password',
        'public', 'public.helpsupport',
        'public.contactus','public.merchantwelcome', 'authentication.forgotPassword', '403', 'error',
        'authentication.offlineproducts','authentication.onlineproducts', 'authentication.pricing',
        'authentication.setPassword'];

      var onboardState = 'base.default.merchant-onboard';
      var user = StorageService.getKey(StorageEnum.user);
      var userStatus = StorageService.getKey(StorageEnum.userStatus);
      
      window.scrollTo(0,0);
      var overallUserStatus = true;
      if(!userStatus || userStatus == null) {
        overallUserStatus = true;
      }
      else {
        overallUserStatus = userStatus.KYC && userStatus.BASIC_INFO && userStatus.BANK_INFO;
      }

      if(toState.name == onboardState) {
        if(overallUserStatus){
          event.preventDefault();
          $state.go(fromState.name);
        }
      } 
      else if(!overallUserStatus && publicStates.indexOf(toState.name) == -1){
        event.preventDefault();
      }
      else if(publicStates.indexOf(toState.name) === -1 && (user === null || Object.keys(user).length === 0)) {
        event.preventDefault();
        $state.go('authentication.login');
      } else if(publicStates.indexOf(toState.name) === -1 && (user !== null && Object.keys(user).length !== 0)){
        var stateAvailable = $filter('filter')(StorageService.getKey(StorageEnum.accessibleStates), {'state':toState.name});
        if(stateAvailable.length === 0){
          event.preventDefault();
          $state.go('403');
        }
      }
    });
}
