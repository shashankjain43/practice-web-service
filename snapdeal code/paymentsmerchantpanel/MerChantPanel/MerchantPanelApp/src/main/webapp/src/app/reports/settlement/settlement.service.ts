import {IHttpService} from "../../shared/services/http.interface";
import {HttpService} from "../../shared/services/http.service";
import {APP_REPORT_URL} from "../reports.url";
import {ISettlementService} from "./settlement.interface";

'use strict';

/* @ngInject */
export class SettlementService implements ISettlementService {

    private HttpService:IHttpService;
    public REQ_METHOD;
    public REQ_URL;
    constructor(HttpService:IHttpService, APP_REPORT_URL:any) {
        this.REQ_METHOD = APP_REPORT_URL.getReportsUrl.SETTLEMENT_REPORT.METHOD;
        this.REQ_URL = APP_REPORT_URL.getReportsUrl.SETTLEMENT_REPORT.URL;
        this.HttpService = HttpService;
    }
    private getTimeFromFilter(dateFilterObject):any {
            var dateArray = dateFilterObject.value.split('/');
            if(dateFilterObject.key === 'endDate'){
                dateFilterObject.value =(new Date(dateArray[2], dateArray[1]-1, dateArray[0], 23, 59, 59)).getTime();
            } else {
                dateFilterObject.value = (new Date(dateArray[2], dateArray[1]-1, dateArray[0])).getTime();
            }
            dateFilterObject = (dateFilterObject.value > 0) ? dateFilterObject.value:0;
            return dateFilterObject;
        }
    public getSettlementReport(filter):any {
        var params='';
            if(filter.limit!=undefined)
            params='?pageSize='+filter.limit.value;
            if(filter.fromDateFilter!=undefined)
                params+='&startTime='+this.getTimeFromFilter(filter.fromDateFilter);
            if(filter.toDateFilter!=undefined)
                params+='&endTime='+this.getTimeFromFilter(filter.toDateFilter);
        return this.HttpService.request(this.REQ_METHOD, this.REQ_URL+params);
    }
}
