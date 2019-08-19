import {GlobalOmnitureService} from "../shared/services/globalomniture.service";
import {HistoryService} from "./history.service";
import {IHistoryService} from "./history.interface";
import {APP_MESSAGE_CONSTANT} from "../shared/constants/message.constant";
import {HISTORY_CONSTANT} from "./history.constant";
import {IGlobalOmnitureService} from "../shared/services/globalomniture.service";

"use strict";
/* @ngInject */
export class HistoryController {
    private GlobalOmnitureService:IGlobalOmnitureService;
    private  HistoryService:IHistoryService;
    private $filter:ng.IFilterService;
    private downloadHistoryData:any;
    public HISTORY_CONSTANTS;
    public APP_MESSAGES;
    public errorMessage:string;
    public  allDownloadHeader:Array<Object>;
    public  downloadHeaderVal:Array<Object>;
    selectedFile;
    public  isLoading:Boolean = false;

    constructor(GlobalOmnitureService:IGlobalOmnitureService, HistoryService:IHistoryService,
                $filter:ng.IFilterService, downloadHistoryData:any) {

        this.GlobalOmnitureService = GlobalOmnitureService;
        this.HistoryService = HistoryService;
        this.APP_MESSAGES = APP_MESSAGE_CONSTANT.getMessageConstants;
        this.HISTORY_CONSTANTS = HISTORY_CONSTANT.getHistoryConstant;
        this.$filter = $filter;
        this.errorMessage = this.APP_MESSAGES.NO_DOWNLOAD_HISTORY;
        GlobalOmnitureService.downloadHistory(downloadHistoryData.data.error);
            if (downloadHistoryData.data.data != null) {
                this.downloadHistoryData = downloadHistoryData.data.data.info;
                this.createHeaders();
                return;
            }
      this.downloadHistoryData = [];
            this.errorMessage = downloadHistoryData.data.error.errorMessage;
    }

    createHeaders() {
        var vm = this;
        var downloadHeaderVM = new Array();
        var constantHeader = this.HISTORY_CONSTANTS.DOWNLOAD_HEADER;
        angular.forEach(this.downloadHistoryData[0], function (headerValue, key) {
            for (var i = 0; i < constantHeader.length; i++) {
                if (constantHeader[i].KEY == key) {
                    downloadHeaderVM.push(constantHeader[i]);
                    return;
                }
            }
        });
        vm.allDownloadHeader = this.$filter('orderBy')(downloadHeaderVM, 'PRIORITY');
        vm.allDownloadHeader = this.$filter('filter')(vm.allDownloadHeader, {IS_SHOWN: true});
        vm.downloadHeaderVal = angular.copy(vm.allDownloadHeader);
        vm.allDownloadHeader.push(constantHeader[constantHeader.length - 1]);
    }

    downloadFiles(downloadData) {
        var vm = this;
        this.errorMessage = undefined;
        this.selectedFile = angular.fromJson(downloadData);
        this.isLoading = true;
        this.HistoryService.downloadFiles(this.selectedFile).then(function (response) {
            vm.isLoading = false;
            var downloadResponse = response.data;
            if (downloadResponse.data != null) {
                vm.downloadLink(downloadResponse.data.url);
            } else {
                vm.errorMessage = downloadResponse.error.errorMessage;
            }
        });
    }


    downloadLink(myUrl) {
        myUrl = myUrl.replace('https://', 'http://');
        var downloadLink = angular.element('#downloadFileTag');
        downloadLink.attr('href', myUrl);
        downloadLink.attr('download', this.selectedFile.fileName);
        downloadLink[0].click();
    }
}
