import {IHttpService} from "../../shared/services/http.interface";
import {HttpService} from "../../shared/services/http.service";
import {APP_ACCOUNT_URL} from "../account.url";
import {IPasswordService} from "./password.interface";

'use strict'
/* @ngInject */

export class PasswordService implements IPasswordService {
	private HttpService:IHttpService;
	public APP_ACCOUNT_URL;
	constructor(HttpService:IHttpService, APP_ACCOUNT_URL:any) {
        this.APP_ACCOUNT_URL = APP_ACCOUNT_URL.getAccountURL
        this.HttpService = HttpService;
    }
    public changePassword(requestObject){
        return this.HttpService.requestErrorDataFormat(this.APP_ACCOUNT_URL.CHANGE_PASSSWORD.METHOD, this.APP_ACCOUNT_URL.CHANGE_PASSSWORD.URL,requestObject);
    }
}
