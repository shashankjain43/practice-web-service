import {IHttpService} from "../../shared/services/http.interface";
import {APP_INTEGRATION_URL} from "../integrationkey.url";
import {IProductionService} from "./production.interface";

'use strict';

/* @ngInject */
export class ProductionService implements IProductionService {
    private HttpService:IHttpService;
    public REQ_METHOD;
    public REQ_URL;
    constructor(HttpService:IHttpService, APP_INTEGRATION_URL:any) {
        this.REQ_METHOD = APP_INTEGRATION_URL.getIntegrationKeyUrl.production.method;
        this.REQ_URL = APP_INTEGRATION_URL.getIntegrationKeyUrl.production.url;
        this.HttpService = HttpService;
    }
    public getProductionKey():any {
        return this.HttpService.request(this.REQ_METHOD, this.REQ_URL);
    }
}
