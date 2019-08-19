import {APP_MESSAGE_CONSTANT} from "../../shared/constants/message.constant";
import {ISettlementService} from "./settlement.interface";
import {SettlementService} from "./settlement.service";
import {SETTLEMENT_CONSTANT} from "./settlement.constant";
import {GlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {IGlobalOmnitureService} from "../../shared/services/globalomniture.service";

"use strict";

/* @ngInject */
export class SettlementController {
    private
    GlobalOmnitureService:IGlobalOmnitureService;
    SettlementService:ISettlementService;
    public APP_MESSAGES;
    public SETTLEMENT_CONSTANT:any;
    private $filter:ng.IFilterService;
    public errorMessage;
    public settlementPageData:any;
    public filters:any;
    public settlement:any;
    public settlementTableData:any;
    public allSettlementHeader:any;
    public totalCount:any;
    public pageCount:number;
    public maxCount:number;
    public pageArray:Array<Object>;
    public dateMessage:any;
    public $scope:ng.IScope;
    public  isLoading:Boolean = false;

    constructor(GlobalOmnitureService:IGlobalOmnitureService,
                APP_MESSAGE:APP_MESSAGE_CONSTANT,
                SettlementService:ISettlementService,
                $filter:ng.IFilterService,
                $scope:ng.IScope,
                settlement:any) {

        this.SETTLEMENT_CONSTANT = SETTLEMENT_CONSTANT.getSettlementConstant;
        this.GlobalOmnitureService = GlobalOmnitureService;
        this.APP_MESSAGES = APP_MESSAGE_CONSTANT.getMessageConstants;
        this.settlement = settlement;
        this.totalCount = 0;
        this.pageCount = 0;
        this.pageArray = [];
        this.$filter = $filter;
        this.settlementTableData = [];
        this.settlementPageData = [];
        this.dateMessage = '';
        this.$scope = $scope;
        this.SettlementService=SettlementService;
        var vm = this;
        this.init();

        this.$scope.$watch(() => this.filters.fromDateFilter.value,
            (oldValue:string, newValue:string) => {
                if (oldValue !== newValue) {
                    this.requestForFilterReports();
                }
            });
    }

    public init() {
        this.isLoading = true;
        this.filters = {
            'fromDateFilter': angular.copy(this.SETTLEMENT_CONSTANT.DATE_FILTER)[0],
            'toDateFilter': angular.copy(this.SETTLEMENT_CONSTANT.DATE_FILTER)[1],
            'page': angular.copy(this.SETTLEMENT_CONSTANT.PAGINATION[0]),
            'limit': angular.copy(this.SETTLEMENT_CONSTANT.PAGINATION[1])
        };

        this.errorMessage = this.APP_MESSAGES.NO_GENERATE_REPORT;
        this.getReportData(this.settlement);
    }


    public getReportData(settlement) {
        this.errorMessage = '';
        this.settlementPageData = [];
        this.isLoading = false;
        var settlementData = settlement.data;
        if (settlementData.data !== null && settlementData.data.merchantReportDetails != null) {
            this.totalCount = settlementData.data.merchantReportDetails.length;
            if (this.totalCount == 0) {
                this.errorMessage = this.APP_MESSAGES.NO_GENERATE_REPORT;
                return;
            }
            this.settlementTableData = settlementData.data.merchantReportDetails;
            this.createHeader();
            try {
                this.customePagination();
            } catch (exception) {
            }
        }
        if (settlementData.error !== null)
            this.errorMessage = settlementData.error.errorMessage;


    }

    public createHeader() {
        var settelementHeadersVM = [];
        var constantHeader = this.SETTLEMENT_CONSTANT.SETTLEMENT_HEADER;
        angular.forEach(this.settlementTableData[0], function (headerValue, key) {
            for (var i = 0; i < constantHeader.length; i++) {
                if (constantHeader[i].key == key) {
                    settelementHeadersVM.push(constantHeader[i]);
                    return;
                }
            }
        });
        var allSettlementHeader = this.$filter('orderBy')(settelementHeadersVM, 'priority');
        this.allSettlementHeader = this.$filter('filter')(allSettlementHeader, {isShown: true});
    }


    public customePagination() {
        var pages = this.totalCount > 10 ? Math.floor(this.totalCount / this.filters.limit.value) : 1;
        this.pageCount = (this.totalCount % this.filters.limit.value == 0) || pages == 1 ? pages : pages + 1;
        this.maxCount = this.pageCount;
        var i, j, chunk = this.filters.limit.value;
        for (i = 0, j = this.settlementTableData.length; i < j; i += chunk) {
            this.pageArray.push(this.settlementTableData.slice(i, i + chunk));
        }
        this.settlementPageData = this.pageArray[0];
        this.setDate(this.pageArray[0]);

        this.isLoading = false;
    }


    public setDate(pageArray) {
        if (this.filters.fromDateFilter.value == 'From Date') {
            this.filters.fromDateFilter.value = this.$filter('date')(pageArray[0].endTime, 'dd/MM/yyyy');
            this.filters.toDateFilter.value = this.$filter('date')(pageArray[pageArray.length - 1].endTime, 'dd/MM/yyyy');
        }
    }

    public getReportOfPage(page) {
        if (page < 1) {
            return;
        }
        this.filters.page.value = page;
        this.filters.page.displayValue = page;
        this.settlementPageData = this.pageArray[page - 1];
    }

    public checkDateValidation(isFromDateLower) {
        this.dateMessage = '';
        if (isFromDateLower)
            this.dateMessage = this.APP_MESSAGES.INVALID_FROM_DATE;
        return true;
    }

    public requestForFilterReports() {
        var vm=this;
        this.isLoading = true;
        var currentDate = new Date();
        var fromdate = angular.copy(this.filters.fromDateFilter.value);
        var from = this.getShortDateFromString(fromdate);
        vm.getTimeDifference(from, currentDate);
        var filters = angular.copy(this.filters);
        vm.SettlementService.getSettlementReport(filters)
            .then(function (response) {
                vm.getReportData(response);
            })
            .finally(function () {
                vm.isLoading = false
            });
    }

    public downloadFiles(settlemeData) {
        var myUrl = settlemeData.fileDownloadUrl;
        myUrl = myUrl.replace('https://', 'http://');
        var downloadLink = angular.element('#downloadFileTag');
        downloadLink.attr('href', myUrl);
        downloadLink.attr('download', settlemeData.reportName);
        downloadLink[0].click();
    }



    public getTimeDifference(fromDate, toDate) {
        var magicNumber = (1000 * 60 * 60 * 24);
        var dayDiff = Math.floor((toDate - fromDate) / magicNumber);
        if (angular.isNumber(dayDiff)) {
            dayDiff = dayDiff + 1;
        }
        if (dayDiff > 7)
            this.filters.toDateFilter.value = this.addDaysTofromDate();
        else
            this.filters.toDateFilter.value = this.$filter('date')(new Date(), 'dd/MM/yyyy');

    }

    public addDaysTofromDate() {
        var fromDate = angular.copy(this.filters.fromDateFilter.value);
        var dateArray = fromDate.split('/');
        var date = new Date(dateArray[2], dateArray[1] - 1, dateArray[0])
        var numberOfDaysToAdd = 10;
        var newDate = date.setDate(date.getDate() + numberOfDaysToAdd);
        return this.$filter('date')(newDate, 'dd/MM/yyyy');
    }

    public getShortDateFromString(inputString) {
        if (angular.isNumber(inputString))
            return;
        var dateArray = inputString.split('/');
        return new Date(dateArray[2], dateArray[1] - 1, dateArray[0]).getTime();
    }

}
