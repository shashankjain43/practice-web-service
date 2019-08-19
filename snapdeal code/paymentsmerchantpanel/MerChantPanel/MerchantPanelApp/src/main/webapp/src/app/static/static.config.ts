'use strict';
/* @ngInject */
export function moduleConfig($stateProvider:angular.ui.IStateProvider , $locationProvider: angular.ILocationProvider) {
 $stateProvider
    .state('public', {
        abstract:true,
        views: {
                '': {
                    controller: 'StaticController',
                    templateUrl: 'app/static/static.tmpl.html',
                    controllerAs: 'vm'
                }
            }
    })
    .state('public.helpsupport', {
            url: '/helpsupport',
            views: {
                'content' : {
                    templateUrl: 'app/static/helpsupport.tmpl.html',
                    controller: 'StaticController',
                    controllerAs: 'vm'
                }
            }

    })
    .state('public.contactus', {
            url: '/contactus',
            views: {
                'content' : {
                    templateUrl: 'app/static/contactus.tmpl.html',
                    controller: 'StaticController',
                    controllerAs: 'vm'
                }
            }

    });

    // use the HTML5 History API
     /*   $locationProvider.html5Mode(true);
        $locationProvider.hashPrefix('!'); */
}
