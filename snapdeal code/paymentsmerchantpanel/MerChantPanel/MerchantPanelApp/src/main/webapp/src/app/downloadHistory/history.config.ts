import {IHistoryService} from "./history.interface";
/* @ngInject */
export function moduleConfig($stateProvider:angular.ui.IStateProvider, $locationProvider: angular.ILocationProvider) {
  $stateProvider
    .state('base.default.download-history', {
      url: '/download',
      views: {
        '': {
          templateUrl: 'app/downloadHistory/history.tmpl.html',
          controller: 'historyController',
          controllerAs: 'vm',
          resolve: {
            downloadHistoryData: function (HistoryService:IHistoryService) {
              return HistoryService.getDownloadHistory();
            }
          }
        },
        'subHeader': {
          templateUrl: 'app/base/layoutComponent/subHeader/subHeader.tmpl.html',
          controller: 'SubHeaderController',
          controllerAs: 'vm',
          resolve: {
            /* @ngInject */
            SubHeaderFor: function (ACCESS_CONSTANT) {
              return ACCESS_CONSTANT().PERMISSIONS.DOWNLOAD_HISTORY;
            }
          }
        }
      }
    });

  /*.state('download', {
   url: '/download',
   templateUrl: 'app/downloadHistory/history.tmpl.html',
   controller: 'HistoryController',
   controllerAs: 'vm',
   });*/

   // use the HTML5 History API
        /*   $locationProvider.html5Mode(true);
        $locationProvider.hashPrefix('!'); */
}
