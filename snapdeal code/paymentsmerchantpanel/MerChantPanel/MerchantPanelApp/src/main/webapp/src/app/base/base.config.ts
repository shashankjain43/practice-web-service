/**
 * Created by chitra.parihar on 14-03-2016.
 */

/* @ngInject */
export function baseConfig($stateProvider:angular.ui.IStateProvider, $locationProvider: angular.ILocationProvider) {
  $stateProvider
    .state('base', {
      abstract: true,
      templateUrl: 'app/base/layouts/layout.tmpl.html',
      controller: 'LayoutController',
      controllerAs: 'vm'
    })
    .state('base.default', {
      abstract: true,
      views: {
        'sidebarLeft': {
          templateUrl: 'app/base/layoutComponent/sidebarLeft/sidebarLeft.tmpl.html',
          controller: 'MenuController',
          controllerAs: 'vm'
        },
        'subHeader': {
          template: '<div ui-view="subHeader"></div>'
        },
        'sidebarRight': {
          template: '<div ui-view="sidebarRight"></div>'
        },
        'header': {
          templateUrl: 'app/base/layoutComponent/header/header.tmpl.html',
          controller: 'HeaderController',
          controllerAs: 'vm'
        },
        'content': {
          template: '<div ui-view></div>'
        }
        ,
        'footer': {
          templateUrl: 'app/base/layoutComponent/footer/footer.tmpl.html'
        }
      }
    });
    // use the HTML5 History API
         /*   $locationProvider.html5Mode(true);
        $locationProvider.hashPrefix('!'); */
}
