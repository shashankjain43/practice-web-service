import {ITransactionService} from "../transaction/ITransactionService";
import {ICssStyleService} from "../../shared/services/cssStyle.service";
import {IGlobalOmnitureService} from "../../shared/services/globalomniture.service";

'use strict';
/* @ngInject */
export class RefundController {

    private $q:ng.IQService;
    private $scope:ng.IScope;
    private $filter:ng.IFilterService;
    private $rootScope:ng.IRootScopeService;
    private $state:ng.ui.IStateService;

    private APP_MESSAGE:any;
    private APP_KEY:any;
    private REFUND_CONSTANT:any;
    private TRANSACTION_CONSTANT:any;


    private CssStyleService:any;
    private GlobalOmnitureService:any;
    private TransactionService:ITransactionService;
    allTransactions:any;
    allTransactionsValue:any;
    allTransactionHeader:Array;
    transactions:Array;
    transactionHeaders:Array;
    isLoading:boolean;
    transactionTypeFiltersApplied:any;
    filterError:string;
    filtersVM:any;
    searchParam:any;
    dateMessage:string;
    errorMessage:string;
    requiredMessage:string;
    amountError:string;
    successMessage:string;
    selctedTransaction:string;
    refundSearch:any;
    noField:string;


    //local
    private constantHeader:any;
    private isSearch:boolean;
    private selectedSearchId:string;
    private searchText:string;

    constructor($q:ng.IQService,
                $scope:ng.IScope,
                $filter:ng.IFilterService,
                $rootScope:ng.IRootScopeService,
                $state:ng.ui.IStateService,
                APP_MESSAGE:any,
                APP_KEY:any,
                REFUND_CONSTANT:any,
                TRANSACTION_CONSTANT:any,
                allTransactions:any,
                CssStyleService:ICssStyleService,
                GlobalOmnitureService:IGlobalOmnitureService,
                TransactionService:ITransactionService) {
        this.selctedTransaction=undefined;
        this.isLoading = false;
        this.$q = $q;
        this.$scope = $scope;
        this.$filter = $filter;
        this.$rootScope = $rootScope;
        this.$state = $state;
        this.APP_MESSAGE = APP_MESSAGE.getMessageConstants;
        this.APP_KEY = APP_KEY.getKeys;
        this.REFUND_CONSTANT = REFUND_CONSTANT();
        this.TRANSACTION_CONSTANT = TRANSACTION_CONSTANT().TRANSACTION_CONSTANT;

        this.CssStyleService = CssStyleService;
        this.GlobalOmnitureService = GlobalOmnitureService;
        this.TransactionService = TransactionService;

        this.transactionTypeFiltersApplied = null;
        this.filtersVM = [];

        this.allTransactions = allTransactions;
        this.allTransactionsValue = [];
        this.isSearch = false;
        this.searchParam = null;
        this.selectedSearchId = '';
        this.searchText = '';
        this.successMessage = '';
        this.selctedTransaction = '';
        this.allTransactionHeader = [];
        this.init();
    }

    init() {
        this.filtersInit();
        this.initialLoadTransactions();
        this.eventsInit();
    }

    filtersInit() {
        this.filtersVM = {
            'fromDateFilter': angular.copy(this.TRANSACTION_CONSTANT.DATE_FILTER)[0],
            'toDateFilter': angular.copy(this.TRANSACTION_CONSTANT.DATE_FILTER)[1],
            'page': angular.copy(this.TRANSACTION_CONSTANT.PAGINATION[0]),
            'limit': angular.copy(this.TRANSACTION_CONSTANT.PAGINATION[1])
        };

    }

    checkDateValidation(isFromDateLower, isToDateGreater) {
        this.dateMessage = '';
        if (isFromDateLower)
            this.dateMessage = this.APP_MESSAGE.invalidFromDate;
        else if (isToDateGreater)
            this.dateMessage = this.APP_MESSAGE.invalidToDate;
        return true;
    }

    getTransactionsOfPage(page) {
        var vm = this;
        if (page < 1) {
            return;
        }
        this.filtersVM.page.value = page;
        this.requestForFilterTransactions()
            .finally(function () {
                vm.filtersVM.page.displayValue = page;
            });
    }

    search() {
        var vm = this;
        var vm = this;
        this.isSearch = true;
        vm.isLoading = true;
        vm.searchParam = {
            key: angular.fromJson(vm.selectedSearchId).key,
            value: vm.searchText
        };
        vm.filtersVM.page.displayValue = 1;
        vm.filtersVM.page.value = 1;
        this.TransactionService.viewRefund(vm.filtersVM, vm.searchParam)
            .then(function (response) {
                try {
                    this.GlobalOmnitureService.searchRefundTransaction(angular.fromJson(vm.selectedSearchId).displayValue, vm.searchText, vm.filters.toDateFilter.value + '-' + vm.filters.fromDateFilter.value);
                } catch (exception) {
                    exception.toString();
                }
                vm.setResponseInTable(response);
                vm.isLoading = false;
            });
    }

    private setResponseInTable(response) {
        var vm = this;
        var refundResponse = response;
        if (refundResponse.data != null) {
          vm.allTransactionsValue = [];
          if(refundResponse.data.mpTransactions.length == 0){
              vm.errorMessage = this.APP_MESSAGE.NO_INITIATE;
              return;
            }
            vm.allTransactionsValue = refundResponse.data.mpTransactions;
        } else {
            vm.errorMessage = refundResponse.error.errorMessage;
        }
    }

    requestForFilterTransactions() {
        var vm = this;
        vm.isLoading = true;
        var deferred = this.$q.defer();
        this.TransactionService.viewRefund(vm.filtersVM, vm.searchParam)
            .then(function (response) {
                vm.setResponseInTable(response);
                deferred.resolve(response);
            })
            .finally(function () {
                vm.isLoading = false;
            });
        return deferred.promise;
    }

    initiateRefund() {
       var vm = this;
        if (vm.selctedTransaction=='') {
            return;
        }
        vm.isLoading = true;
        var refundData = angular.fromJson(vm.selctedTransaction);

      vm.TransactionService.showRefundedAmount(refundData)
            .then(function (response) {
                vm.CssStyleService.addDisplayElement('.adduser-bg');
                if (vm.selctedTransaction != "") {
                    vm.CssStyleService.addCssClass('#initiateButton', 'edituser');
                    vm.CssStyleService.addCssClass('#refundForm div', 'show');
                    vm.$rootScope.$broadcast('transaction-action', {
                        data: {
                            'transactionInfo': angular.fromJson(vm.selctedTransaction),
                            'transactionCount': vm.allTransactionsValue.length,
                            'refundedAmount': response.totalRefundAmount
                        }
                    });
                } else {
                    vm.CssStyleService.removeCssClass('#initiateButton', 'edituser');
                    vm.CssStyleService.removeDisplayElement('.adduser-bg');
                }

            }).finally(function () {
            vm.isLoading = false;
        });


    }

    selectTransactionWatcher() {
        var vm = this;
        vm.$scope.$watch(() => vm.selctedTransaction,
           (newValue, oldValue)  =>
           {
            if (vm.selctedTransaction == "") {
                vm.CssStyleService.addCssClass('#initiateButton', 'disabled');
            } else
                vm.CssStyleService.removeCssClass('#initiateButton', 'disabled');
        });
    }

    initialLoadTransactions() {
        var vm = this;
        try {
            vm.CssStyleService.addCssClass('#initiateButton', 'disabled');
            vm.dateMessage = '';
            vm.errorMessage = vm.APP_MESSAGE.noInitiate;
            vm.requiredMessage = vm.APP_MESSAGE.requiredField;
            vm.refundSearch = vm.REFUND_CONSTANT.REFUND_SEARCH;
            vm.selectedSearchId = angular.toJson(vm.refundSearch[0]);
            vm.allTransactionsValue = vm.allTransactions.data.mpTransactions;
            if (vm.allTransactionsValue[0].txnDate != null) {
                vm.filtersVM.toDateFilter.value = vm.$filter('date')(vm.allTransactionsValue[0].txnDate, 'dd/MM/yyyy');
            }
            if (vm.allTransactionsValue[vm.allTransactionsValue.length - 1].txnDate != null) {
                vm.filtersVM.fromDateFilter.value = vm.$filter('date')(vm.allTransactionsValue[vm.allTransactionsValue.length - 1].txnDate, 'dd/MM/yyyy');
            }
            this.GlobalOmnitureService.refundHome(vm.noField);
        }
        catch (ex) {
            ex.toString();
        }
        if (vm.allTransactions.data != null) {
            vm.createHeader();
        }
        else {
            vm.errorMessage = vm.allTransactions.error.errorMessage;
        }
    }

    checkValidDate() {
        var vm = this;
        var from = vm.getShortDateFromString(vm.filtersVM.fromDateFilter.value);
        var to = vm.getShortDateFromString(vm.filtersVM.toDateFilter.value);
        var currentDate = vm.$filter('date')(new Date(), 'MM/dd/yyyy');
        if (to - from < 0) {
            return false;
        }
        return true;
    }

    getShortDateFromString(inputString) {
        var dateArray = inputString.split('/');
        return new Date(dateArray[2], dateArray[1] - 1, dateArray[0]).getTime();
    }


    eventsInit() {
        this.keyDownListerner();
        this.fromDateFilterWatcher();
        this.toDateFilterWatcher();
        this.searchTextWatcher();
        this.selectTransactionWatcher();
    }

    keyDownListerner() {
        var vm = this;
        this.$scope.$on('keydown', function (event, keyEvent) {
            if (keyEvent.keyCode == vm.APP_KEY.ESC) {
                vm.CssStyleService.removeCssClass('.initiate-refund-popup', 'show');
                vm.CssStyleService.removeDisplayElement('.adduser-bg');
            }
        });
    }

    createHeader() {
        var transactionHeadersVM = [];
        var vm = this;
        this.constantHeader = this.REFUND_CONSTANT.REFUND_HEADER;
        angular.forEach(this.allTransactionsValue[0], function (headerValue, key) {
            for (var i = 0; i < vm.constantHeader.length; i++) {
                if (vm.constantHeader[i].key == key) {
                    transactionHeadersVM.push(vm.constantHeader[i]);
                    return;
                }
            }
        });
        var allTransactionHeader = this.$filter('orderBy')(transactionHeadersVM, 'priority');
        this.allTransactionHeader = this.$filter('filter')(allTransactionHeader, {isShown: true});
    }


    fromDateFilterWatcher() {
        var vm = this;
        this.$scope.$watch(() => vm.filtersVM.fromDateFilter.value,
          (newValue, oldValue) => {
            if (newValue !== oldValue && this.checkValidDate()) {
                if (vm.filtersVM.toDateFilter.value != vm.filtersVM.toDateFilter.displayValue && !vm.isSearch) {
                    vm.requestForFilterTransactions();
                }
            }
        });
    }

    dateFilterClick() {
        this.isSearch = false;
    }

    toDateFilterWatcher() {
        var vm = this;
        this.$scope.$watch(() => vm.filtersVM.toDateFilter.value,
           (newValue, oldValue) => {
            if (newValue !== oldValue && this.checkValidDate()) {
                if (vm.filtersVM.toDateFilter.value != vm.filtersVM.toDateFilter.displayValue && !vm.isSearch) {
                    vm.requestForFilterTransactions();
                }
            }
        });
    }

    searchTextWatcher() {
        var vm = this;
        /*when user clear the search text,it invokes to get all initiate data*/
        this.$scope.$watch('vm.searchText', function (newValue, oldValue) {
            if (newValue != oldValue) {
                if (vm.searchText == undefined) {
                    vm.isLoading = true;
                    vm.filtersVM.page.value = 1;
                    vm.filtersVM.page.displayValue = 1;
                    vm.searchParam = null;
                    vm.isLoading = true;
                    vm.TransactionService.viewRefund(null, null)
                        .then(function (response) {
                            vm.setResponseInTable(response);
                            vm.$scope.transactionForm.$setPristine();
                        }).finally(function(){
                      vm.isLoading = false;
                    });
                }
            }
        });
    }
}
