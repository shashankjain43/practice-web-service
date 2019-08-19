"use strict"

export interface IHttpService {
  request:(method:string, url:string, data?:Object)=>ng.IPromise<any>;
  requestDataFormat:(method:string, url:string, data?:Object)=>ng.IPromise<any>;
  requestErrorDataFormat:(method:string, url:string, data?:Object)=>ng.IPromise<any>;
  requestFileDownload:(method:string, url:string)=>ng.IPromise<any>;
}
