import {IHttpService} from "../shared/services/http.interface";
import {HttpService} from "../shared/services/http.service";
import {APP_ACCOUNT_URL} from "./account.url";
import {IAccountService} from "./account.interface";

'use strict';
/* @ngInject */

export class AccountService implements IAccountService {
    private HttpService:IHttpService;
    public REQ_METHOD_vp : any;
    public REQ_METHOD_gr : any;
    public REQ_URL_vp : any;
    public REQ_URL_gr : any;
    public APP_ACCOUNT_URL;
    constructor(HttpService:IHttpService, APP_ACCOUNT_URL:any) {
        this.APP_ACCOUNT_URL = APP_ACCOUNT_URL.getAccountURL;
        this.HttpService = HttpService;
    }
    public viewProfile(){
    	var REQ_METHOD_vp = this.APP_ACCOUNT_URL.VIEW_PROFILE.METHOD;
        var REQ_URL_vp = this.APP_ACCOUNT_URL.VIEW_PROFILE.URL;
        return this.HttpService.request(REQ_METHOD_vp, REQ_URL_vp);
    }
    public getRoles(){
    	var REQ_METHOD_gr = this.APP_ACCOUNT_URL.GET_ROLES.METHOD;
        var REQ_URL_gr = this.APP_ACCOUNT_URL.GET_ROLES.URL;
        return this.HttpService.request(REQ_METHOD_gr, REQ_URL_gr);
    }
}
