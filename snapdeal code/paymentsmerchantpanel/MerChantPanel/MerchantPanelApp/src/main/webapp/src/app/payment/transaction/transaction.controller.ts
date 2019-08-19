import {IInitiateGenerateReportVM} from "./IInitiateGenerateReportVM";
import {ICssStyleService} from "../../shared/services/cssStyle.service";
import {IGlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {ITransactionService} from "./ITransactionService";
import {DateFilter} from "./IInitiateGenerateReportVM";

'use strict';
/* @ngInject */
export class TransactionController{

  private $sce: any;
  private $q: ng.IQService;
  private $scope: ng.IScope;
  private $state: ng.ui.IStateService;
  private $filter: ng.IFilterService;
  private TRANSACTION_CONSTANT: any;
  private GlobalOmnitureService: any;
  private TransactionService: ITransactionService;
  private CssStyleService: any;

  isLoading: boolean;
  transactionTypeFiltersApplied: any;
  transactions: Array;
  transactionHeaders: Array;
  selectedDrodownItem: Array;
  chooseFieldHeaders: Array;
  filterError: string;
  initiateGenerateReport: IInitiateGenerateReportVM;
  filtersVM: any;
  allTransactions: any;

  searchParam: any;
  dateMessage: string;
  errorMessage: string;
  amountError: string;

  private validDate: boolean;
  private searchData: any;
  private appliedFilters: any;
  private stopWatchDropdownAsAppliedFilters: boolean;
  private APP_MESSAGE: any;

  constructor($filter: ng.IFilterService,
              $state: ng.ui.IStateService,
              $scope: ng.IScope,
              $q: ng.IQService,
              CssStyleService: ICssStyleService,
              TRANSACTION_CONSTANT: any,
              GlobalOmnitureService: IGlobalOmnitureService,
              APP_MESSAGE: any,
              TransactionService: ITransactionService,
              allTransactions: any,
              $sce: any,
              searchParam: any){

    this.isLoading = false;
    this.$sce = $sce;
    this.$q = $q;
    this.$scope = $scope;
    this.$filter = $filter;
    this.$state = $state;
    this.TRANSACTION_CONSTANT = TRANSACTION_CONSTANT().TRANSACTION_CONSTANT;
    this.GlobalOmnitureService = GlobalOmnitureService;
    this.TransactionService = TransactionService;
    this.CssStyleService = CssStyleService;

    this.transactionTypeFiltersApplied = null;

    this.APP_MESSAGE = APP_MESSAGE.getMessageConstants;
    this.transactions = [];
    this.transactionHeaders = [];
    this.chooseFieldHeaders = [];
    this.filterError = '';
    this.searchParam = searchParam;
    this.allTransactions = allTransactions;
    this.initiateGenerateReport = {};
    this.initiateGenerateReport.dateFilter = {};
    this.initiateGenerateReport.dateFilter.startDate = '';
    this.initiateGenerateReport.dateFilter.endDate = '';
    this.initiateGenerateReport.status = false;
    this.initiateGenerateReport.message = '';
    this.initiateGenerateReport.forbiddenMessage = '';
    this.initiateGenerateReport.forbiddenStatus = false;

    this.validDate = true;
    this.searchData;
    this.appliedFilters = {};
    this.stopWatchDropdownAsAppliedFilters = false;

    this.dateMessage = '';
    this.errorMessage = '';
    this.amountError = '';
    this.init();
  }


    gotoDownloadHistory() {
        this.$state.go('base.default.download-history');
    }

     init() {
       this.initializeFiltersVM();
       if (this.allTransactions.error !== null) {
            this.transactions = [];
            this.errorMessage = this.allTransactions.error.errorMessage;
            this.isLoading = false;
            this.setOmnitureForSearch();
            return;
        }
        if (this.allTransactions.data.mpTransactions.length !== 0) {
            this.transactions = this.allTransactions.data.mpTransactions;
            if (this.transactions[0].txnDate) {

                this.filtersVM.toDateFilter.value = this.$filter('date')(this.transactions[0].txnDate, 'dd/MM/yyyy');
                this.initiateGenerateReport.dateFilter.endDate = this.transactions[0].txnDate;
            }
            if (this.transactions[this.transactions.length - 1].txnDate) {
                this.filtersVM.fromDateFilter.value = this.$filter('date')(this.transactions[this.transactions.length - 1].txnDate, 'dd/MM/yyyy');
                this.initiateGenerateReport.dateFilter.startDate = this.transactions[this.transactions.length - 1].txnDate;
            }
        } else {
            this.transactions = [];
            this.errorMessage = this.APP_MESSAGE.EMPTY_TRANSACTION;
        }

       this.initializeVM();
       this.watchersInit();


     }

    initializeVM(){
      this.isLoading = false;
      this.transactionHeaders = this.createHeader();
      this.chooseFieldHeaders = this.generateFieldHeaders();
      this.setOmnitureForSearch();



      this.searchData = this.searchParam != undefined ? this.searchParam : undefined;
      this.dateMessage = '';
      this.dateMessage = '';
      }

    initializeFiltersVM(){
      this.filtersVM = {};
      this.filtersVM.transactionStatus = angular.copy(this.TRANSACTION_CONSTANT.TRANSACTION_STATUS_FILTER);
      this.filtersVM.transactionType = angular.copy(this.TRANSACTION_CONSTANT.TRANSACTION_TYPE_FILTER);
      this.filtersVM.amount = angular.copy(this.TRANSACTION_CONSTANT.AMOUNT_FILTER);
      this.filtersVM.dropdown = angular.copy(this.TRANSACTION_CONSTANT.DROPDOWN_FILTER);
      this.filtersVM.fromDateFilter = angular.copy(this.TRANSACTION_CONSTANT.DATE_FILTER)[0];
      this.filtersVM.toDateFilter = angular.copy(this.TRANSACTION_CONSTANT.DATE_FILTER)[1];
      this.filtersVM.selectedDrodownItem = angular.copy(this.TRANSACTION_CONSTANT.DROPDOWN_FILTER[0]);
      this.filtersVM.page = angular.copy(this.TRANSACTION_CONSTANT.PAGINATION[0]);
      this.filtersVM.limit = angular.copy(this.TRANSACTION_CONSTANT.PAGINATION[1]);
      this.selectedDrodownItem = angular.toJson(this.filtersVM.dropdown[0]);
    }
     checkDateValidation(isFromDateLower, isToDateGreater) {
        this.dateMessage = '';
        if (isFromDateLower) {
            this.dateMessage = this.APP_MESSAGE.INVALID_FROM_DATE;
            this.validDate = false;
        }
        else if (isToDateGreater) {
            this.dateMessage = this.APP_MESSAGE.INVALID_TO_DATE;
            this.validDate = false;
        } else {
            this.validDate = true;
        }
        return true;
    }

     setOmnitureForSearch() {
        if (this.searchData != undefined)
          this.GlobalOmnitureService.searchTransaction(this.searchData.displayValue, this.searchParam.value, this.filtersVM.toDateFilter.value + '-' + this.filtersVM.fromDateFilter.value);
        /*else
          this.setOmniture('', '', 0, 0);*/
    }

     updateFieldHeader(fieldHeaderObject) {
        if (fieldHeaderObject.isShown) {
            fieldHeaderObject.isShown = false;
            var objectToRemoveIndex = this.getIndexFromTransactionHeaders(fieldHeaderObject);
            this.transactionHeaders.splice(objectToRemoveIndex, 1);
            return;
        }
        fieldHeaderObject.isShown = true;
        this.transactionHeaders.push(fieldHeaderObject);
    }

     private getIndexFromTransactionHeaders(fieldHeaderObject) {
        var transactionHeaders = this.transactionHeaders;
        var fieldIndex = null;
        angular.forEach(transactionHeaders, function(object, index) {
            if (object.key === fieldHeaderObject.key) {
                fieldIndex = index;
                return;
            }
        });
        return fieldIndex;
    }

     generateFieldHeaders() {
        var transactionHeadersVM = this.TransactionService.transactionHeadersVM;

       function  showHeadersWithIsInChooseFieldTrue(headerVM) {
         return headerVM.isInChooseField == true;
       }
        var transactionHeaders = this.$filter('filter')(transactionHeadersVM, showHeadersWithIsInChooseFieldTrue);
        transactionHeaders = this.$filter('orderBy')(transactionHeaders, 'priority');
        return transactionHeaders;
    }


     createHeader() {
        var transactionHeadersVM = this.createHeadersVM();
        this.TransactionService.transactionHeadersVM = angular.copy(transactionHeadersVM);
        return this.displayHeadersInit(transactionHeadersVM);
    }

     displayHeadersInit(transactionHeadersVM) {
         function  showHeadersWithShowTrue(headerVM) {
            return headerVM.isShown == true;
        }

        var transactionHeaders = this.$filter('filter')(transactionHeadersVM, showHeadersWithShowTrue);
        transactionHeaders = this.$filter('orderBy')(transactionHeaders, 'priority');
        return transactionHeaders;
    }

     createHeadersVM() {
        var transactionHeadersVM = [];
        var constantHeader = this.TRANSACTION_CONSTANT.TRANSACTION_HEADER;

        angular.forEach(this.transactions[0], function(headerValue, key) {

            for (var i = 0; i < constantHeader.length; i++) {

                if (constantHeader[i].key == key) {
                    transactionHeadersVM.push(constantHeader[i]);
                    return;
                }
            }
        });
        return transactionHeadersVM;
    }

     applyFilters() {
        if (this.amountError) {
            return;
        }
        var transactionTypeFiltersApplied = this.$filter('filter')(this.filtersVM.transactionType, {selected: true});
        var transactionStatusFiltersApplied = this.$filter('filter')(this.filtersVM.transactionStatus, {selected: true});
        var fromAmount = this.filtersVM.amount[0];
        var toAmount = this.filtersVM.amount[1];
       this.appliedFilters = {
            'transactionStatus': transactionStatusFiltersApplied,
            'transactionType': transactionTypeFiltersApplied,
            'fromAmount': fromAmount,
            'toAmount': toAmount
        };
        this.setOmniture(this.appliedFilters.transactionStatus, this.appliedFilters.transactionType, this.appliedFilters.fromAmount.value, this.appliedFilters.toAmount.value);
        this.filtersVM.page = angular.copy(this.TRANSACTION_CONSTANT.PAGINATION[0]);
        if (transactionTypeFiltersApplied.length === 1 && transactionStatusFiltersApplied.length === 0) {
            if (transactionTypeFiltersApplied[0].value === 'REFUND') {
                this.stopWatchDropdownAsAppliedFilters = true;
                this.selectedDrodownItem = transactionTypeFiltersApplied[0];
            }
        } else if (transactionTypeFiltersApplied.length === 0 && transactionStatusFiltersApplied.length === 1) {
            if (transactionStatusFiltersApplied[0].value === 'SETTLED') {
                this.stopWatchDropdownAsAppliedFilters = true;
                this.selectedDrodownItem = transactionStatusFiltersApplied[0];
            }
        }
        this.requestForFilterTransactions();
    }

     resetFilters() {

        this.filterError = '';
        this.filtersVM.transactionStatus = angular.copy(this.TRANSACTION_CONSTANT.TRANSACTION_STATUS_FILTER);
        this.filtersVM.transactionType = angular.copy(this.TRANSACTION_CONSTANT.TRANSACTION_TYPE_FILTER);
        this.filtersVM.amount = angular.copy(this.TRANSACTION_CONSTANT.AMOUNT_FILTER);
    }

  requestForFilterTransactions() {
    var vm = this;
    vm.isLoading = true;
    this.stopWatchDropdownAsAppliedFilters = false;
    var deferred = this.$q.defer();
    this.CssStyleService.removeDisplayElement('#filterTab');
    var actualFilters = this.actualFiltersSelectedAfterAppy(this.filtersVM);
    this.TransactionService.getFilteredTransactions(actualFilters, this.searchParam)
      .then(function (response) {
        if (response.data != null) {

          vm.transactions = response.data.mpTransactions;
          if (vm.transactions.length == 0) {
            vm.errorMessage = vm.APP_MESSAGE.EMPTY_TRANSACTION;
          }
          deferred.resolve(response);
          return;
        }

        if (response.error !== null) {
          vm.transactions = [];
          vm.errorMessage = vm.allTransactions.error.errorMessage;
          vm.isLoading = false;
          return;
        }
        deferred.resolve(response);
      })
      .finally(function () {
        vm.isLoading = false;
      });
    return deferred.promise;
  }

  watchersInit() {
    this.selectedDropDownItemWatcher();
    this.toDateFilterWatcher();
    this.fromDateFilterWatcher();
  }

  selectedDropDownItemWatcher(){
    var vm = this;
    this.$scope.$watch(() => this.selectedDrodownItem,
      (newValue, oldValue) =>
      {
        if (newValue == oldValue || vm.stopWatchDropdownAsAppliedFilters) {
          return;
        }
        vm.filtersVM.page = angular.copy(vm.TRANSACTION_CONSTANT.PAGINATION[0]);
        vm.filtersVM.selectedDrodownItem = angular.fromJson(newValue);
        vm.filtersVM.transactionStatus = angular.copy(vm.TRANSACTION_CONSTANT.TRANSACTION_STATUS_FILTER);
        vm.filtersVM.transactionType = angular.copy(vm.TRANSACTION_CONSTANT.TRANSACTION_TYPE_FILTER);
        if (vm.filtersVM.selectedDrodownItem.value == 'REFUND') {
          angular.forEach(vm.filtersVM.transactionType, function(transactionType)
            {
              if (transactionType.value == 'REFUND') {
                transactionType.selected = true;
              }
            }
          )
        }
        if (vm.filtersVM.selectedDrodownItem.value == 'SETTLED') {
          angular.forEach(vm.filtersVM.transactionStatus, function(transactionStatus)
            {
              if (transactionStatus.value == 'SETTLED') {
                transactionStatus.selected = true;
              }
            }
          )
        }
        vm.appliedFilters.transactionStatus = vm.filtersVM.transactionStatus;
        vm.appliedFilters.transactionType = vm.filtersVM.transactionType;
        vm.requestForFilterTransactions();
      }
    );
  }

  toDateFilterWatcher(){
    var _this = this;
    this.$scope.$watch(() => this.filtersVM.toDateFilter.value,
      (newValue, oldValue) =>
      {
        if (newValue !== oldValue && _this.checkValidDate()) {
          if (_this.filtersVM.toDateFilter.value != _this.filtersVM.toDateFilter.displayValue) {
            _this.filtersVM.page = angular.copy(_this.TRANSACTION_CONSTANT.PAGINATION[0]);
            _this.requestForFilterTransactions();
        }
      }
    });
  }

  fromDateFilterWatcher(){
    var _this = this;
    this.$scope.$watch(() => this.filtersVM.fromDateFilter.value,
      (newValue, oldValue) =>
      {
        if (newValue !== oldValue && _this.checkValidDate()) {
          _this.filtersVM.page = angular.copy(_this.TRANSACTION_CONSTANT.PAGINATION[0]);
          _this.requestForFilterTransactions();
        }
      });
  }

  actualFiltersSelectedAfterAppy(filters) {
    var actualFilters = angular.copy(filters);
    actualFilters.transactionStatus = this.appliedFilters.transactionStatus;
    actualFilters.transactionType = this.appliedFilters.transactionType;
    actualFilters.fromAmount = this.appliedFilters.fromAmount;
    actualFilters.toAmount = this.appliedFilters.toAmount;
    return actualFilters;
  }

   generateReport() {
      var _this = this;
      if (this.transactions.length < 1) {
          this.initiateGenerateReport.forbiddenStatus = true;
          this.initiateGenerateReport.forbiddenMessage = this.APP_MESSAGE.GENERATE_REPORT_FORBIDDEN;
          return;
      }
      this.isLoading = true;
      this.TransactionService.initiateGenerateReport(this.initiateGenerateReport.dateFilter)
          .then(function (response) {
              if (response.error == null) {
                  _this.initiateGenerateReport.status = true;
                  _this.initiateGenerateReport.message = _this.$sce.trustAsHtml(response.data.message +
                      '<a   ng-click="_this.gotoDownloadHistory()">  <span style="font-weight: bold;margin-left: 20px">View Download History</span></a>');
                  return;
              }
              _this.initiateGenerateReport.status = false;
              _this.initiateGenerateReport.message = response.error.errorMessage;
              _this.GlobalOmnitureService.exportToExcel(_this.initiateGenerateReport.message);
          })
          .finally(function () {
              _this.isLoading = false;
              _this.CssStyleService.fadeInElement('.export-xl-popup , .lightbox-bg');
          });
    }

     compareRange(toVal, fromVal) {
        if (angular.isNumber(toVal) && angular.isNumber(fromVal)) {
            this.amountError = (toVal <= fromVal);
        }
    }

     setOmniture(transactionStatusFiltersApplied, transactionTypeFiltersApplied, fromAmount, toAmount) {
        var filter = '';
        var header = '';

        angular.forEach(this.transactionHeaders,  function(object) {
            header += object.displayValue + ',';
        });
        angular.forEach(transactionStatusFiltersApplied,  function(object) {
            filter += object.displayValue + ',';
        });
        angular.forEach(transactionTypeFiltersApplied, function (object) {
            if (object.displayValue != '')
                filter += object.displayValue + ',';
        });

        if (fromAmount != '')
            filter += 'fromAmount:' + fromAmount + '-';
        if (toAmount != '') {

            filter += 'toAmount:' + toAmount + ',';
        }
        angular.forEach(this.chooseFieldHeaders,  function(object) {
            if (object.displayValue != '')
                header += object.displayValue + ',';
        });
        this.GlobalOmnitureService.viewTransaction(filter.trim().slice(0, -1), header, this.filtersVM.fromDateFilter.value + '-' + this.filtersVM.toDateFilter.value);
    }

     checkValidDate() {
        var from = this.getShortDateFromString(this.filtersVM.fromDateFilter.value);
        var to = this.getShortDateFromString(this.filtersVM.toDateFilter.value);
        var currentDate = this.$filter('date')(new Date(), 'MM/dd/yyyy');
        if (to - from < 0) {
            return false;
        }
        return true;
    }

     getShortDateFromString(inputString) {
        var dateArray = inputString.split('/');
        return new Date(dateArray[2], dateArray[1] - 1, dateArray[0]).getTime();
    }

     getTransactionsOfPage(page) {
        if (page < 1) {
            return;
        }
        var vm = this;
        this.filtersVM.page.value = page;
        this.requestForFilterTransactions()
            .finally(function () {
                vm.filtersVM.page.displayValue = page;
            });
    }

     requestForFilterTransactions() {
        var vm = this;
        this.isLoading = true;
        this.stopWatchDropdownAsAppliedFilters = false;
        var deferred = this.$q.defer();
        this.CssStyleService.removeDisplayElement('#filterTab');
        var actualFilters = this.actualFiltersSelectedAfterAppy(this.filtersVM);
        this.TransactionService.getFilteredTransactions(actualFilters, this.searchParam)
            .then(function (response) {
                if (response.data != null) {
                    vm.transactions = response.data.mpTransactions;
                    if (vm.transactions.length == 0) {
                      vm.errorMessage = vm.APP_MESSAGE.EMPTY_TRANSACTION;
                    }
                } else {
                    vm.transactions = [];
                    vm.errorMessage = vm.allTransactions.error.errorMessage;
                    vm.isLoading = false;
                }
                deferred.resolve(response);
            })
            .finally(function () {
                vm.isLoading = false;
            });
        return deferred.promise;
    }

     generateReport() {
       var vm = this;
        if (this.transactions.length < 1) {
            this.initiateGenerateReport.forbiddenStatus = true;
            this.initiateGenerateReport.forbiddenMessage = this.APP_MESSAGE.GENERATE_REPORT_FORBIDDEN;
            return;
        }
       this.isLoading = true;
       this.TransactionService.initiateGenerateReport(this.initiateGenerateReport.dateFilter)
            .then(function (response) {
                vm.initiateGenerateReport.status = true;
                vm.initiateGenerateReport.message = vm.$sce.trustAsHtml(response.message +
                    '<a   ng-click="vm.gotoDownloadHistory()"><span style="font-weight: bold;margin-left: 20px">View Download History</span></a>');
            })
            .catch(function (response) {
              vm.initiateGenerateReport.status = false;
              vm.initiateGenerateReport.message = response.errorMessage;
              vm.GlobalOmnitureService.exportToExcel(vm.initiateGenerateReport.message);
            })
            .finally(function () {
                vm.isLoading = false;
                vm.CssStyleService.fadeInElement('.export-xl-popup , .lightbox-bg');
            });
    }

     compareRange(toVal, fromVal) {
        if (angular.isNumber(toVal) && angular.isNumber(fromVal)) {
            this.amountError = (toVal <= fromVal);
        }
    }

     setOmniture(transactionStatusFiltersApplied, transactionTypeFiltersApplied, fromAmount, toAmount) {
        var filter = '';
        var header = '';

        angular.forEach(this.transactionHeaders,  function(object) {
            header += object.displayValue + ',';
        });
        angular.forEach(transactionStatusFiltersApplied, function (object) {
            filter += object.displayValue + ',';
        });
        angular.forEach(transactionTypeFiltersApplied, function (object) {
            if (object.displayValue != '')
                filter += object.displayValue + ',';
        });

        if (fromAmount != '')
            filter += 'fromAmount:' + fromAmount + '-';
        if (toAmount != '') {

            filter += 'toAmount:' + toAmount + ',';
        }
        angular.forEach(this.chooseFieldHeaders, function (object) {
            if (object.displayValue != '')
                header += object.displayValue + ',';
        });
       this.GlobalOmnitureService.viewTransaction(filter.trim().slice(0, -1), header, this.filtersVM.fromDateFilter.value + '-' + this.filtersVM.toDateFilter.value);
    }
}
