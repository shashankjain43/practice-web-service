"use strict"

export interface IfileUpload {
  withDataUpload:(file:any, uploadUrl:string, formData:Object, fileKey:string)=>ng.IPromise<any>;
}
