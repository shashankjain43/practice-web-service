
import {IHttpService} from "../../shared/services/http.interface";
import {ITransactionService} from "./ITransactionService";
import {IFilterService} from "../../shared/filter/filter.interface";
'use strict';

/* @ngInject */
export class TransactionService implements ITransactionService{
    //inject
    private HttpService: IHttpService;
    private $filter: ng.IFilterService;
    private APP_PAYMENT_URL: any;

    //local
    private requestFilterObject: Object;
    public transactionHeadersVM: Array;

  constructor($filter: ng.IFilterService, HttpService: IHttpService, APP_PAYMENT_URL: any) {
      // this.viewTransaction = viewTransaction;
      this.$filter = $filter;
      this.HttpService = HttpService;
      this.APP_PAYMENT_URL= APP_PAYMENT_URL.getPaymentURL;

      this.transactionHeadersVM = [];
      this.requestFilterObject = {};
      this.HttpService = HttpService;
    }

    initiateGenerateReport(dateFilter){
       var requestObject = angular.copy(this.requestFilterObject);
        if(Object.keys(requestObject).length === 0){
            requestObject = dateFilter;
        } else{
            if(!requestObject.hasOwnProperty('startDate')){
                requestObject['startDate'] = dateFilter.startDate;
                requestObject['endDate'] = dateFilter.endDate;
            }
            delete requestObject['page'];
            delete requestObject['limit'];
        }
        return this.HttpService.requestErrorDataFormat(this.APP_PAYMENT_URL.INITIATE_GENERATE_REPORT.METHOD, this.APP_PAYMENT_URL.INITIATE_GENERATE_REPORT.URL+'?fileType=XLS&'+this.serialize(requestObject));
    }

     viewRefund(filters, searchParam)
    {
        var url=  this.APP_PAYMENT_URL.TRANSACTION_VIEW.URL+'?txnTypeList=PAYMENT&txnStatusList=SUCCESS,SETTLED';
        if(filters === null){
          return this.HttpService.requestDataFormat(this.APP_PAYMENT_URL.TRANSACTION_VIEW.METHOD, url);
        }
        var validFilters = this.getValidFilters(filters, searchParam);
        return this.HttpService.requestDataFormat(this.APP_PAYMENT_URL.TRANSACTION_VIEW.METHOD, url+'&'+this.generateRequestParams(validFilters));
    }

     showRefundedAmount(selectedTxn)
    {
        var refID =selectedTxn.txnRefId , refType =selectedTxn.txnRefType , orderId =selectedTxn.orderId;
        var url =  this.APP_PAYMENT_URL.SHOW_REFUND_AMOUNT.URL+'?txnRefId='+refID+'&txnRefType='+refType+'&orderId='+orderId;
        return this.HttpService.requestErrorDataFormat(this.APP_PAYMENT_URL.SHOW_REFUND_AMOUNT.METHOD, url);
    }

     getFilteredTransactions(filters, searchParam){
        if(filters === null && searchParam === null){
          return this.HttpService.requestDataFormat(this.APP_PAYMENT_URL.TRANSACTION_VIEW.METHOD,  this.APP_PAYMENT_URL.TRANSACTION_VIEW.URL);
        }
        var validFilters = this.getValidFilters(filters, searchParam);
        return this.HttpService.requestDataFormat(this.APP_PAYMENT_URL.TRANSACTION_VIEW.METHOD,  this.APP_PAYMENT_URL.TRANSACTION_VIEW.URL+'?'+this.generateRequestParams(validFilters));
    }

     private getValidFilters(filters, searchParam){
        var validFilters = [];
        if(filters !== null){
            var transactionTypeFiltersApplied = [];
            var transactionStatusFiltersApplied = [];
            if(angular.isDefined(filters.transactionType)){
                transactionTypeFiltersApplied = this.$filter('filter')(filters.transactionType, {selected: true});
            }
            if(angular.isDefined(filters.transactionStatus)){
                transactionStatusFiltersApplied = this.$filter('filter')(filters.transactionStatus, {selected: true});
            }
            if(angular.isDefined(filters.selectedDrodownItem)){
                if(filters.selectedDrodownItem.key === 'txnStatusList'){
                    if(this.$filter('filter')(transactionStatusFiltersApplied, {value:'SETTLED'}).length < 1)
                        transactionStatusFiltersApplied.push(filters.selectedDrodownItem);
                } else if(filters.selectedDrodownItem.key === 'txnTypeList'){
                    if(this.$filter('filter')(transactionTypeFiltersApplied, {value:'REFUND'}).length < 1)
                        transactionTypeFiltersApplied.push(filters.selectedDrodownItem);
                }
            }
            if(transactionTypeFiltersApplied.length > 0){   validFilters.push(transactionTypeFiltersApplied);  }
            if(transactionStatusFiltersApplied.length > 0){ validFilters.push(transactionStatusFiltersApplied); }
            if(angular.isDefined(filters.amount)){
                if(filters.amount[0].value !== ''){validFilters.push(filters.amount[0]); }
                if(filters.amount[1].value !== ''){validFilters.push(filters.amount[1]); }
            }
            if(angular.isDefined(filters.toDateFilter)){
                if(filters.toDateFilter.value !== filters.toDateFilter.displayValue){
                    validFilters.push(this.parseDateFilterObject(angular.copy(filters.toDateFilter)));
                }
            }
            if(angular.isDefined(filters.fromDateFilter)){
                if(filters.fromDateFilter.value !== filters.fromDateFilter.displayValue){
                    validFilters.push(this.parseDateFilterObject(angular.copy(filters.fromDateFilter)));
                }
            }
            validFilters.push(filters.page);
            validFilters.push(filters.limit);
        }
        if(searchParam != null){
            validFilters.push(searchParam);
            if(filters === null){
                var page = {'key':'page', 'value':1};
                var limit = {'key':'limit', 'value':10};
                validFilters.push(page);
                validFilters.push(limit);
            }
        }
        return validFilters;
    }

     private parseDateFilterObject(dateFilterObject){
        var dateArray = dateFilterObject.value.split('/');
        if(dateFilterObject.key === 'endDate'){
            dateFilterObject.value =(new Date(dateArray[2], dateArray[1]-1, dateArray[0], 23, 59, 59)).getTime();
        } else {
            dateFilterObject.value = (new Date(dateArray[2], dateArray[1]-1, dateArray[0])).getTime();
        }
        dateFilterObject.value = (dateFilterObject.value > 0) ? dateFilterObject.value:0;
        return dateFilterObject;
    }

     private serialize(obj) {
        var str = [];
        for(var p in obj)
            if (obj.hasOwnProperty(p)) {
                str.push(p + "=" +obj[p]);
            }
        return str.join("&");
    };

     generateRequestParams(validFilters){
        var requestFilter = this.generateRequestFilterObject(validFilters);
        return this.serialize(requestFilter);
    }

     private generateRequestFilterObject(validFilters){
        var requestFilter = {};
        var requestFilterArrayForInitiateReport = {};
        for(var i=0; i< validFilters.length; i++){
            if(angular.isArray(validFilters[i])){
                requestFilter[validFilters[i][0].key] = this.setValueForFilterKey(validFilters[i]);
                requestFilterArrayForInitiateReport[validFilters[i][0].key] = this.setValueForFilterArray(validFilters[i]);
            } else {
                requestFilter[validFilters[i].key] = validFilters[i].value;
                requestFilterArrayForInitiateReport[validFilters[i].key] = validFilters[i].value;
            }
        }
        this.requestFilterObject = angular.copy(requestFilterArrayForInitiateReport);
        return requestFilter;
    }

     private setValueForFilterKey(filterVM) {
        var filterVMvalue = '';
        angular.forEach(filterVM, function (value) {
            filterVMvalue += value.value + ',';
        });
        return filterVMvalue.slice(0, -1);
    }

     setValueForFilterArray(filterVM) {
        var filterVMvalue = [];
        angular.forEach(filterVM, function (value) {
            filterVMvalue.push(value.value);
        });
        return filterVMvalue;
    }
}

