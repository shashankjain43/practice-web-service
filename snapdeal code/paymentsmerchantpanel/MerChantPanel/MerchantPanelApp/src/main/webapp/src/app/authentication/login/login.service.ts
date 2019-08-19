export interface ILoginService {
  login: (loginName:string, password:string) => ng.IPromise<any>;
  createMerchant: (loginName:string, password:string, mode:string) => ng.IPromise<any>;
  createOfflineMerchant: (Object) => any;
}


import {IHttpService} from "../../shared/services/http.interface";


/** @ngInject */
export class LoginService implements ILoginService {
  private AuthURL:any;
  private HttpService:IHttpService;

  constructor(HttpService:IHttpService, Auth_URL:any) {
    this.AuthURL = Auth_URL;
    this.HttpService = HttpService;
  }

  public  login(loginName:string, password:string) {
    var requestObject = {
      "loginName": loginName,
      "password": password
    };
    return this.HttpService.requestErrorDataFormat(this.AuthURL.LOGIN.METHOD, this.AuthURL.LOGIN.URL, requestObject);
  }

   public  createMerchant(loginName:string, password:string, mode:string) {
    var requestObject = {
      "email": loginName,
      "password": password,
      "integrationMode":mode,
      "integrationModeSubtype":"Standard" 
    };
    return this.HttpService.requestErrorDataFormat(this.AuthURL.CREATE_MERCHANT.METHOD, this.AuthURL.CREATE_MERCHANT.URL, requestObject);
  }

  public createOfflineMerchant(offline) {
    var data = offline;
    return this.HttpService.requestErrorDataFormat(this.AuthURL.OFFLINE_MERCHANT.METHOD, this.AuthURL.OFFLINE_MERCHANT.URL, data);
  }
}

