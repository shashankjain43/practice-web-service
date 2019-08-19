/** @ngInject */
export function routerConfig($stateProvider: angular.ui.IStateProvider, $urlRouterProvider: angular.ui.IUrlRouterProvider, $locationProvider: angular.ILocationProvider) {
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
  $urlRouterProvider.when('', '/');
  $urlRouterProvider.when('/login', '/');

  // always goto error if route not found
  $urlRouterProvider.otherwise('/error');


  // use the HTML5 History API
       /* $locationProvider.html5Mode(true);
        $locationProvider.hashPrefix('!'); */
}
