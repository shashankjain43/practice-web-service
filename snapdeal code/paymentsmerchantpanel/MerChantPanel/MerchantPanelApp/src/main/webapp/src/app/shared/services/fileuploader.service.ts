import {IfileUpload} from "./fileuploader.interface";

'use strict';
  /** @ngInject */
  export class fileUpload implements IfileUpload {

    $http: ng.IHttpService;
    $q: ng.IQService;
    $state: ng.ui.IStateService;
    API_CONFIG: any;

    constructor($http:ng.IHttpService, $q: ng.IQService, $state:ng.ui.IStateService,
                API_CONFIG:any) {
      this.$http = $http;
      this.$q = $q;
      this.$state = $state;
      this.API_CONFIG = API_CONFIG;
    }

    public withDataUpload(file, uploadUrl, formData, fileKey) {
        var fd = new FormData();
        var vm = this;
        var deferred = vm.$q.defer();
        angular.forEach(formData, function(value, key) {
          fd.append(key,value);
        });
        fd.append(fileKey, file);
        var url = vm.generateBaseUrl() + uploadUrl;
        vm.$http.post(url, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(response){
            deferred.resolve(response);
        })
        .error(function(response){
            deferred.resolve(response);
        });
        return deferred.promise;
    }

    private generateBaseUrl() {
      var baseUrl = this.API_CONFIG.BASE_URL;
      return baseUrl;
    }

}