
import {ISandboxService} from "./sandbox/sandbox.interface";
import {IProductionService} from "./production/production.interface";

'use strict';

/* @ngInject */
export function moduleConfig($stateProvider:angular.ui.IStateProvider, $locationProvider: angular.ILocationProvider) {
  $stateProvider
    .state('base.default.integration', {
        abstract: true,
        views: {
            '': {
                templateUrl: 'app/integrationkey/integrationkey.tmpl.html'
            },
            'subHeader':{
                templateUrl: 'app/base/layoutComponent/subHeader/subHeader.tmpl.html',
                controller: 'SubHeaderController',
                controllerAs: 'vm',
                resolve:{
                    /* @ngInject */
                    SubHeaderFor: function(ACCESS_CONSTANT){
                        return ACCESS_CONSTANT().PERMISSIONS.INTEGRATION;
                    }
                }
            }
        }
    })
    .state('base.default.integration.production', {
        url: '/production',
        controller:'ProductionController',
        templateUrl: 'app/integrationkey/production/production.tmpl.html',
        controllerAs: 'vm',
        resolve:{
            productionData: function (ProductionService:IProductionService) {
              return ProductionService.getProductionKey();
            }
        }
    })
    .state('base.default.integration.sandbox', {
        url: '/sandbox',
        controller:'SandboxController',
        templateUrl: 'app/integrationkey/sandbox/sandbox.tmpl.html',
        controllerAs: 'vm',
        resolve:{
            sandboxData: function (SandboxService:ISandboxService) {
              return SandboxService.getSandboxKey();
            }
        }
    });

       // use the HTML5 History API
        /*   $locationProvider.html5Mode(true);
        $locationProvider.hashPrefix('!'); */
}
