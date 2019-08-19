import {ISandboxService} from "../integrationkey/sandbox/sandbox.interface";
import {IAccountService} from "../account/account.interface";

/* @ngInject */
export function moduleConfig($stateProvider:angular.ui.IStateProvider, $locationProvider: angular.ILocationProvider) {
  $stateProvider
    .state('base.default.merchant-onboard', {
      url: '/onboard',
      views: {
        '': {
          templateUrl: 'app/merchantOnboard/mob.tmpl.html',
          controller: 'mobController',
          controllerAs: 'vm',
            resolve:{
                /* @ngInject */
                sandboxData: function (SandboxService:ISandboxService) {
                  return SandboxService.getSandboxKey();
                },
                profileInfo: function (AccountService:IAccountService) {
                return AccountService.viewProfile();
                }
            }
        }
      }
    });
       // use the HTML5 History API
        /*   $locationProvider.html5Mode(true);
        $locationProvider.hashPrefix('!'); */
}
