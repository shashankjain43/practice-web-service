(function() {
    'use strict';

    angular
        .module('app')
        .config(routeConfig);

    /* @ngInject */
    function routeConfig($stateProvider, $urlRouterProvider) {
        // Setup the apps routes

        // 404 & 500 pages
        $stateProvider
        .state('error', {
            url: '/handleError',
            templateUrl: 'handleError.tmpl.html',
            controllerAs: 'vm',
            controller: function($state) {
                var vm = this;
                vm.goHome = function() {
                    $state.go('authentication.login');
                };
            }
        })

        .state('403', {
            url: '/403',
            templateUrl: '403.tmpl.html',
            controllerAs: 'vm',
            controller: function($state) {
                var vm = this;
                vm.goHome = function() {
                    $state.go('authentication.login');
                };
            }
        });

        // set default routes when no path specified
        $urlRouterProvider.when('', '/login');
        $urlRouterProvider.when('/', '/login');

        // always goto error if route not found
        $urlRouterProvider.otherwise('/error');
    }
})();
