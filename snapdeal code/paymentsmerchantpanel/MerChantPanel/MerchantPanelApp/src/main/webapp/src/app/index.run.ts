/** @ngInject */
export function runBlock($log: angular.ILogService, API_CONFIG, $httpBackend: angular.IHttpBackendService) {
  $log.debug('runBlock end');
  if(API_CONFIG.MOCK_RUN) {

    // By Pass all the assets
    $httpBackend.whenGET(/assets/).passThrough();
    // By Pass all the files in app  folder
    $httpBackend.whenGET(/app/).passThrough();
    $httpBackend.whenGET(/.tmpl.html/).passThrough();
  }
}
