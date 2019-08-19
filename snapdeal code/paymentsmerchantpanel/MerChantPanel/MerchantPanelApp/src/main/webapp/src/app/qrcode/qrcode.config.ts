
/* @ngInject */
export function qrcodeConfig($stateProvider:angular.ui.IStateProvider , $locationProvider: angular.ILocationProvider) {
  $stateProvider
    .state('qrcode', {
      url:'/authqrcode/:qr/:payTag/print',
      templateUrl:'app/qrcode/qrcode.tmpl.html',
      controller: 'QrcodeController',
      controllerAs: 'vm'
    });

      // use the HTML5 History API
        /*   $locationProvider.html5Mode(true);
        $locationProvider.hashPrefix('!'); */
}
