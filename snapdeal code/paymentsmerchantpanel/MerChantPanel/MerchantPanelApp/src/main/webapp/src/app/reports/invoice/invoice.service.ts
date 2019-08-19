import {IInvoiceService} from "./invoice.interface";
import {IHttpService} from "../../shared/services/http.interface";
/**
 * Created by chitra.parihar on 18-04-2016.
 */
'use strict';
/* @ngInject */
export class InvoiceService implements IInvoiceService {
    private HttpService:IHttpService;
    public REQ_METHOD;
    public REQ_URL;

    constructor(HttpService:IHttpService, APP_REPORT_URL:any) {
        this.REQ_METHOD = APP_REPORT_URL.getReportsUrl.INVOICE_REPORT.METHOD;
        this.REQ_URL = APP_REPORT_URL.getReportsUrl.INVOICE_REPORT.URL;
        this.HttpService = HttpService;
    }

    private getTimeFromFilter(dateFilterObject):any {
      var dateArray = dateFilterObject.VALUE.split('/');
      if(dateFilterObject.KEY === 'endDate'){
        var noOfEndDays= new Date(dateArray[1],dateArray[0],0).getDate();

        dateFilterObject.VALUE =(new Date(dateArray[1], dateArray[0]-1, noOfEndDays, 23, 59, 59)).getTime();
      } else {
        dateFilterObject.VALUE = (new Date(dateArray[1], dateArray[0]-1, 1)).getTime();
      }
      dateFilterObject = (dateFilterObject.VALUE > 0) ? dateFilterObject.VALUE:0;
      return dateFilterObject;
    }

    public getInvoiceReport(filter):any {
        var params = '';
        if (filter.limit != undefined)
            params = '?pageSize=' + filter.limit.VALUE;
        if (filter.fromDateFilter != undefined)
            params += '&startTime=' + this.getTimeFromFilter(filter.fromDateFilter);
        if (filter.toDateFilter != undefined)
            params += '&endTime=' + this.getTimeFromFilter(filter.toDateFilter);
        return this.HttpService.request(this.REQ_METHOD, this.REQ_URL + params);
    }

}
