
  import {IHttpService} from "./http.interface";
  interface IResponseDataErrorFormat<T>{
    data: T;
    error: T;
  }
  'use strict';
  /** @ngInject */
  export class HttpService implements IHttpService {

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

    public requestFileDownload(method, url){
      var deferred = this.$q.defer();
      var _this = this;
      this.$http({
        method: method,
        url: _this.generateBaseUrl() +url,
        responseType:'arraybuffer'
      }).then(function(response) {
          if(response.data == null){
            deferred.reject(response.data);
          } else {
            deferred.resolve(response.data);
          }
        })
        .catch(function(){
          _this.$state.go('error');
        });
      return deferred.promise;
    }

    public requestErrorDataFormat(method:string, url:string, data?:Object): ng.IPromise<any> {
      var deferred = this.$q.defer();
      var _this = this;
      this.$http({
        method: method,
        url: _this.generateBaseUrl() + url,
        data: data
      }).then(function (response: any) {
          if(response.data.data == null){
            deferred.reject(response.data.error);
          } else {
            deferred.resolve(response.data.data);
          }
        })
        .catch(function (response: any) {
          _this.$state.go('error');
        });
      return deferred.promise;
    }

    public requestDataFormat(method:string, url:string, data?:Object): ng.IPromise<any> {
      var deferred = this.$q.defer();
      var _this = this;
      this.$http({
        method: method,
        url: _this.generateBaseUrl() + url,
        data: data
      }).then(function (response: any) {
            deferred.resolve(response.data);
        })
        .catch(function (response: any) {
          _this.$state.go('error');
        });
      return deferred.promise;
    }


    public request(method:string, url:string, data?:Object): ng.IPromise<any> {
      var deferred = this.$q.defer();
      var _this = this;

      this.$http({
        method: method,
        url: _this.generateBaseUrl() + url,
        data: data
      }).then(function (response) {
          deferred.resolve(response);
      })
      .catch(function () {
        _this.$state.go('error');
      });
      return deferred.promise;
    }

    private generateBaseUrl() {
      var baseUrl = this.API_CONFIG.BASE_URL;
      return baseUrl;
    }
  }

