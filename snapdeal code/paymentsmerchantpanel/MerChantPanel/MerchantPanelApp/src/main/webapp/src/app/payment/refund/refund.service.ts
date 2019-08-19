
import {IHttpService} from "../../shared/services/http.interface";
import {IRefundService} from "./refund.interface";
'use strict';

/* @ngInject */
export class RefundService implements IRefundService {
  //injectors
  HttpService: IHttpService;
  APP_PAYMENT_URL: any;

  constructor(HttpService: IHttpService, APP_PAYMENT_URL: any) {
    this.APP_PAYMENT_URL = APP_PAYMENT_URL.getPaymentURL;
    this.HttpService = HttpService;
  }

   initiateRefund(data: any) {
    return this.HttpService.requestErrorDataFormat(this.APP_PAYMENT_URL.SUBMIT_AMOUNT.METHOD, this.APP_PAYMENT_URL.SUBMIT_AMOUNT.URL, data);
  }
}
