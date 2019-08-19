import {IHttpService} from "../../shared/services/http.interface";
import {HttpService} from "../../shared/services/http.service";
import {APP_ACCOUNT_URL} from "../account.url";
import {IManageService} from "./manage.interface";

'use strict';

/* @ngInject */
export class ManageService implements IManageService {
    private HttpService:IHttpService;
    public APP_ACCOUNT_URL;
    constructor(HttpService:IHttpService, APP_ACCOUNT_URL:any) {
        this.APP_ACCOUNT_URL = APP_ACCOUNT_URL.getAccountURL;
        this.HttpService = HttpService;
    }
    public getUsersOfMerchant() {
            return this.HttpService.request(this.APP_ACCOUNT_URL.GET_USERS.METHOD, this.APP_ACCOUNT_URL.GET_USERS.URL);
        }
    public addUsers(requestData) {
        return this.HttpService.request(this.APP_ACCOUNT_URL.ADD_USERS.METHOD, this.APP_ACCOUNT_URL.ADD_USERS.URL,requestData);
    }

    public editUsers(requestData) {
        return this.HttpService.request(this.APP_ACCOUNT_URL.EDIT_USERS.METHOD, this.APP_ACCOUNT_URL.EDIT_USERS.URL,requestData);
    }

    public verifyUser(requestData) {
        return this.HttpService.request(this.APP_ACCOUNT_URL.VERIFY_USER.METHOD, this.APP_ACCOUNT_URL.VERIFY_USER.URL+requestData,requestData);
    }

}
