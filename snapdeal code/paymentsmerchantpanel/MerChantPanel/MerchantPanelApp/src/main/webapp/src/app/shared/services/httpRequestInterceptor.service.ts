
import {IPermissionService} from "../permission/permission.service";
import {PermissionService} from "../permission/permission.service";
import IHttpInterceptor = angular.IHttpInterceptor;
'use strict';
/* @ngInject */
export class HttpRequestInterceptor implements IHttpInterceptor{
      public PermissionService: IPermissionService;
      constructor(PermissionService: IPermissionService){
        this.PermissionService = PermissionService;
      }
      public static Factory(PermissionService: IPermissionService){
        return new HttpRequestInterceptor(PermissionService);
      }
      public request = (config) => {
        var user =  this.PermissionService.user;
        if(user != null){
          config.headers['merchantId'] = user.merchantId;
          config.headers['token'] = user.token;
        }
        return config;
      };
}
