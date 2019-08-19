(function () {
    'use strict';

    angular
        .module('app')
        .run(mockRun);

    /* @ngInject */
    function mockRun($rootScope: ng.IRootScopeService, $httpBackend: angular.IHttpBackendService, API_CONFIG) {
        if(API_CONFIG.mockRun) {
            // By Pass all the assets
            $httpBackend.whenGET(/assets/).passThrough();
            // By Pass all the files in app  folder
            $httpBackend.whenGET(/app/).passThrough();
            $httpBackend.whenGET(/.tmpl.html/).passThrough();
        }
    }

})();
