import {IGlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {IInvoiceService} from "./invoice.interface";
'use strict';

  /* @ngInject */
  export class InvoiceController {
    public $scope:ng.IScope;
    public  isLoading:Boolean = false;
    public INVOICE_CONSTANT: any;
    public APP_MESSAGE: any;
    public GlobalOmnitureService: IGlobalOmnitureService;
    public errorMessage: string;
    public filters:any;
    private $filter: angular.IFilterService;
    private invoice: any;
    private invoicePageData: Array;
    private totalCount: number;
    private invoiceTableData: Array;
    private dateMessage: string;
    private pageCount:number;
    private constantHeader: any;
    private allinvoiceHeader: any;
    private maxCount: number;
    public pageArray: Array;
    public InvoiceService: IInvoiceService;

    constructor(invoice,
                $scope: ng.IScope,
                APP_MESSAGE: any,
                GlobalOmnitureService: IGlobalOmnitureService,
                InvoiceService: IInvoiceService,
                $filter: angular.IFilterService,
                INVOICE_CONSTANT: any) {
    var vm = this;
    this.$scope = $scope;
    this.INVOICE_CONSTANT = INVOICE_CONSTANT.getInvoiceConstant;
    this.GlobalOmnitureService = GlobalOmnitureService;
    this.APP_MESSAGE = APP_MESSAGE.getMessageConstants;
    this.$filter = $filter;
    this.InvoiceService = InvoiceService;
      this.invoice = invoice;
    var constantHeader;
    var totalCount;
    var pageCount = 0;
    this.pageArray = [];
    var invoiceTableData = [];
    this.init();
  }

     init() {
     var vm = this;
      vm.isLoading = true;
      vm.filters = {
        'fromDateFilter': angular.copy(this.INVOICE_CONSTANT.DATE_FILTER)[0],
        'toDateFilter': angular.copy(this.INVOICE_CONSTANT.DATE_FILTER)[1],
        'page': angular.copy(this.INVOICE_CONSTANT.PAGINATION[0]),
        'limit': angular.copy(this.INVOICE_CONSTANT.PAGINATION[1]),
      };

      vm.errorMessage = vm.APP_MESSAGE.NO_GENERATE_REPORT ;
      this.getReportData(this.invoice);
      this.addWatchers();
    }

    addWatchers(){
      var vm = this;
      this.$scope.$watch(() => vm.filters.fromDateFilter.VALUE,
         (newValue: string, oldValue: string) => {
        if (newValue !== oldValue && !angular.isNumber(newValue)) {
          vm.requestForFilterReports();
        }
      });
      this.$scope.$watch(() => vm.filters.toDateFilter.VALUE,
        (newValue: string, oldValue: string) =>  {
        if (newValue !== oldValue && !angular.isNumber(newValue)) {
          vm.requestForFilterReports();
        }
      });
    }

     getReportData(invoice) {
      var vm = this;
       vm.errorMessage = '';
      vm.invoicePageData = [];
      vm.isLoading = false;
      var invoiceData = invoice.data;
      if (invoiceData.data !== null && invoiceData.data.merchantInvoiceDetails != null) {
        vm.totalCount = invoiceData.data.merchantInvoiceDetails.length;
        if (vm.totalCount == 0) {
          vm.errorMessage = this.APP_MESSAGE.NO_GENERATE_REPORT;
          return;
        }
        vm.invoiceTableData = invoiceData.data.merchantInvoiceDetails;
        vm.createHeader();
        try {
          vm.customePagination();
        } catch (exception) {
        }
      }
      if (invoiceData.error !== null)
      vm.errorMessage = invoiceData.error.errorMessage;
      vm.GlobalOmnitureService.invoiceReport(vm.filters.toDateFilter.VALUE + '-' + vm.filters.fromDateFilter.VALUE);

    }

     createHeader() {
       var vm = this;
      var invoiceHeadersVM = [];
      this.constantHeader = this.INVOICE_CONSTANT.INVOICE_HEADER;
      angular.forEach(this.invoiceTableData[0], function (headerValue, key) {
        for (var i = 0; i < vm.constantHeader.length; i++) {
          if (vm.constantHeader[i].KEY == key) {
            invoiceHeadersVM.push(vm.constantHeader[i]);
            return;
          }
        }
      });
      var allinvoiceHeader = this.$filter('orderBy')(invoiceHeadersVM, 'PRIORITY');
      this.allinvoiceHeader = this.$filter('filter')(allinvoiceHeader, {IS_SHOWN: true});
    }


     customePagination() {
      var pages = this.totalCount > 10 ? Math.floor(this.totalCount / this.filters.limit.VALUE) : 1;
      this.pageCount = (this.totalCount % this.filters.limit.VALUE == 0) || pages == 1 ? pages : pages + 1;
      this.maxCount = this.pageCount;
      var i, j, chunk = this.filters.limit.VALUE;
      for (i = 0, j = this.invoiceTableData.length; i < j; i += chunk) {
        this.pageArray.push(this.invoiceTableData.slice(i, i + chunk));
      }
      this.invoicePageData = this.pageArray[0];
      this.setDate(this.pageArray[0]);
      this.isLoading = false;
    }


     setDate(pageArray) {
       var vm = this;
      if (vm.filters.fromDateFilter.VALUE == 'From Date') {
        vm.filters.fromDateFilter.VALUE = this.$filter('date')(pageArray[pageArray.length -1].startTime, 'MM/yyyy');
        vm.filters.toDateFilter.VALUE = this.$filter('date')(pageArray[0].endTime, 'MM/yyyy');
      }
    }

     getReportOfPage(page) {
       var vm = this;
        if (page < 1) {
          return;
        }
        vm.filters.page.value = page;
        vm.filters.page.displayValue = page;
        vm.invoicePageData = this.pageArray[page - 1];
      }

     checkDateValidation(isFromDateLower) {
       var vm = this;
      vm.dateMessage = '';
      if (isFromDateLower)
        vm.dateMessage = this.APP_MESSAGE.INVALID_FROM_DATE;
      return true;
    }

     requestForFilterReports() {
       var vm = this;
      vm.isLoading = true;
      var currentDate = new Date();
      var fromdate = angular.copy(vm.filters.fromDateFilter.VALUE);
      var from = this.getShortDateFromString(fromdate);
      var filters = angular.copy(vm.filters);

      this.InvoiceService.getInvoiceReport(filters)
        .then(function (response) {
          vm.getReportData(response);
        })
        .finally(function () {
          vm.isLoading = false
        });
    }

     downloadFiles(invoiceData) {
      var myUrl = invoiceData.fileDownloadUrl;
      myUrl = myUrl.replace('https://', 'http://');
      var downloadLink = angular.element('#downloadFileTag');
      downloadLink.attr('href', myUrl);
      downloadLink.attr('download', invoiceData.reportName);
      downloadLink[0].click();
    }

     getShortDateFromString(inputString) {
      try {
        if (angular.isNumber(inputString))
          return;
        var dateArray = inputString.split('/');
        return new Date(dateArray[2], dateArray[1] - 1, 1).getTime();
      }catch(ex) {}
    }
  }
