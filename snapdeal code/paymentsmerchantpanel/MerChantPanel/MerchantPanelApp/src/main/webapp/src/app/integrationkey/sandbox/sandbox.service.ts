import {IHttpService} from "../../shared/services/http.interface";
import {HttpService} from "../../shared/services/http.service";
import {APP_INTEGRATION_URL} from "../integrationkey.url";
import {ISandboxService} from "./sandbox.interface";

'use strict';

/* @ngInject */
export class SandboxService implements ISandboxService {
    private HttpService:IHttpService;
    public REQ_METHOD;
    public REQ_URL;
    constructor(HttpService:IHttpService, APP_INTEGRATION_URL:any) {
        this.REQ_METHOD = APP_INTEGRATION_URL.getIntegrationKeyUrl.sandbox.method;
        this.REQ_URL = APP_INTEGRATION_URL.getIntegrationKeyUrl.sandbox.url;
        this.HttpService = HttpService;
    }
    public getSandboxKey():any {
        return this.HttpService.request(this.REQ_METHOD, this.REQ_URL);
    }
}
