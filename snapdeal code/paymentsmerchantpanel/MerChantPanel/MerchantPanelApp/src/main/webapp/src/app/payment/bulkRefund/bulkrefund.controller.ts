import {IGlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {IBulkRefundService} from "./bulkrefund.service";
import {ICssStyleService} from "../../shared/services/cssStyle.service";
import {APP_MESSAGE_CONSTANT} from "../../shared/constants/message.constant";
'use strict';
/* @ngInject */
export class BulkRefundController {

  public bulkRefunHistoryVM = {};
  private checkFile:string;
  private isFileLarge:boolean;
  public APP_MESSAGE;
  public fileToUpload = '';
  private GlobalOmnitureService: IGlobalOmnitureService;
  private CssStyleService: ICssStyleService;
  public isLoading: boolean;
  public BULK_REFUND_CONSTANT: any;
  public BulkRefundService: IBulkRefundService;
  private $filter: ng.IFilterService;
  public allDownloadHeader: any;
  public downloadHeaderVal: any;
  public APP_KEY:any;
  public $scope: ng.IScope;
  public downloadHistoryFileErrror = '';
  public noField:string;
  public listError: string;
  public  uploadErrorMessage:string;
  public uploadSuccessMessage:string;
  public isShowRefund:boolean;

  constructor(BulkRefundService:IBulkRefundService,APP_KEY:any ,GlobalOmnitureService: IGlobalOmnitureService, $scope: ng.IScope, CssStyleService: ICssStyleService, BULK_REFUND_CONSTANT: any,
              $filter: ng.IFilterService,APP_MESSAGE:APP_MESSAGE_CONSTANT) {
    this.isFileLarge = false;
    this.GlobalOmnitureService = GlobalOmnitureService;
    this.CssStyleService = CssStyleService;
    this.BulkRefundService = BulkRefundService;
    this.BULK_REFUND_CONSTANT = BULK_REFUND_CONSTANT.getHistoryConstant;
    this.APP_MESSAGE =APP_MESSAGE_CONSTANT.getMessageConstants;
    this.APP_KEY = APP_KEY.getKeys;
    this.$filter = $filter;
    this.$scope = $scope;
    this.init();
    var vm=this;
    this.$scope.$on('keydown', function (event, keyEvent) {
      if (keyEvent.keyCode == vm.APP_KEY.ESC) {
        vm.CssStyleService.removeDisplayElement('.upload-tab-cont');
        vm.CssStyleService.removeDisplayElement('.bulk-refund-cont');
        vm.CssStyleService.removeDisplayElement('.refund-bg');
      }
    });
  }

  init() {
    this.isShowRefund=true;
    this.GlobalOmnitureService.bulkRefundHome();
    this.uploadErrorMessage = this.APP_MESSAGE.CHOOSE_CSV_FILE;
    this.uploadFile();
    this.eventKeydownListener();
  }

  eventKeydownListener() {
    var vm=this;
    vm.$scope.$on('keydown', function(event, keyEvent) {
      if (keyEvent.keyCode == vm.APP_KEY.ESC) {
        vm.CssStyleService.removeDisplayElement('.upload-tab-cont');
        vm.CssStyleService.removeDisplayElement('.bulk-refund-cont');
        vm.CssStyleService.removeDisplayElement('.refund-bg');
      }
    });
  }

  uploadFile() {
    this.fileToUpload = '';
    this.isShowRefund=true;
    this.CssStyleService.addCssClass('#uploadButton', 'disabled');
    this.removeMessageDisplay();
  }

  cancel() {
    angular.element("#inputFile , #inputBrowse").val("");
    this.CssStyleService.removeDisplayElement('#uploadError');
    this.CssStyleService.removeDisplayElement('#uploadSuccess');
  }

  downloadFile() {
    var vm = this;
    vm.isLoading = true;
    this.BulkRefundService.downloadSampleFile()
      .then(function (response) {
        vm.isLoading = false;
        var blob = new Blob([response], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
        saveAs(blob, "Bulk Refund Template.xlsx");
      })
      .catch(function(response){
        vm.GlobalOmnitureService.bulkRefundDownload(response);
      })
      .finally(function(){
        vm.isLoading = false;
      })
  }

  file_changed(file) {
    var vm = this;
    vm.fileToUpload = file.files[0];
    vm.isFileLarge = file.files[0].size / 1024 > this.BULK_REFUND_CONSTANT.FILE_SIZE;
    vm.removeMessageDisplay();
    vm.checkFile = vm.checkFileType(file.files[0].name);
    if (!this.checkFile) {
      vm.uploadErrorMessage = this.APP_MESSAGE.chooseCsvfile;
      vm.CssStyleService.changeText('#uploadError', vm.uploadErrorMessage);
      vm.CssStyleService.addCssClass('#uploadButton', 'disabled');
      vm.CssStyleService.addDisplayElement('#uploadError');
    }
    else if (vm.isFileLarge) {
      vm.uploadErrorMessage = this.APP_MESSAGE.fileSize;
      vm.CssStyleService.changeText('#uploadError', vm.uploadErrorMessage);
      vm.CssStyleService.addCssClass('#uploadButton', 'disabled');
      vm.CssStyleService.addDisplayElement('#uploadError');
    }
    else {
      vm.CssStyleService.removeCssClass('#uploadButton', 'disabled');
      vm.CssStyleService.removeDisplayElement('#uploadError');
    }

  }

  removeMessageDisplay() {
    this.CssStyleService.removeDisplayElement('#uploadSuccess');
    this.CssStyleService.removeDisplayElement('#uploadError');
  }

  checkFileType(photofile) {
    var fileExtnsn = photofile.substring(photofile.lastIndexOf('.') + 1).toLowerCase();
    return fileExtnsn == 'xls' || fileExtnsn == 'xlsx';
  }



  bulkrefund() {
    var vm = this;
    vm.uploadErrorMessage = '';
    if (this.checkFile && !this.isFileLarge) {
      this.removeMessageDisplay();
      vm.isLoading = true;
      this.BulkRefundService.fileUpload(vm.fileToUpload)
        .then(function (response) {
          if (response.data.data == null) {
            vm.CssStyleService.addDisplayElement('#uploadError');
            vm.uploadErrorMessage = response.data.error.errorMessage;
            vm.GlobalOmnitureService.bulkRefundDownload(vm.uploadErrorMessage);
            vm.isLoading = false;
            return;
          }
          vm.CssStyleService.addDisplayElement('#uploadSuccess');
          vm.uploadSuccessMessage = response.data.data.message;
          vm.isLoading = false;
          vm.isShowRefund=false;
          vm.GlobalOmnitureService.bulkRefundUplaod(vm.uploadErrorMessage);
        });
    }
  }

  getFileDeatails() {
    var vm = this;
    vm.isLoading = true;
    vm.listError = '';
    this.BulkRefundService.bulkRefundHistory()
      .then(function (response) {
        var responses = response.data;
        if (responses.data == null)
          vm.listError = responses.error.errorMessage;

        else {
          vm.bulkRefunHistoryVM = responses.data.info;
          if (responses.data.info.length == 0)
            vm.listError = this.APP_MESSAGE.noDownloadHistory;
          vm.loadBulkRefundHistory();
        }
        vm.isLoading = false;
        vm.GlobalOmnitureService.bulkRefundView(vm.listError);
      });
  }

  downloadHistoryFile(downloadData) {
    var vm = this;
    vm.listError = '';
    vm.isLoading = true;
    vm.downloadHistoryFileErrror = '';
    this.BulkRefundService.downloadBulkRefundOutputFile(downloadData.id)
      .then(function (downloadResponse) {
        vm.isLoading = false;
        if (downloadResponse.data.data != null) {
          vm.downloadLink(downloadResponse.data.data.url, downloadData);
        } else {
          vm.downloadHistoryFileErrror = downloadResponse.data.error.errorMessage;
        }
      });
  }

  downloadLink(myUrl, downloadData) {
    myUrl = myUrl.replace('https://', 'http://');
    var downloadLink = angular.element('#downloadFileTag');
    downloadLink.attr('href', myUrl);
    downloadLink.attr('download', downloadData.fileName);
    downloadLink[0].click();
  }

  loadBulkRefundHistory() {
    var vm = this;
    vm.noField = this.APP_MESSAGE.NO_DOWNLOAD_HISTORY;
    this.createHeaders();
  }

  createHeaders() {
    var vm = this;
    var downloadHeaderVM = [];
    var constantHeader = this.BULK_REFUND_CONSTANT.DOWNLOAD_HEADER;
    angular.forEach(vm.bulkRefunHistoryVM[0], function (headerValue, key) {
      for (var i = 0; i < constantHeader.length; i++) {
        if (constantHeader[i].KEY == key) {
          downloadHeaderVM.push(constantHeader[i]);
          return;
        }
      }
    });
    var allDownloadHeader = this.$filter('orderBy')(downloadHeaderVM, 'PRIORITY');
    vm.allDownloadHeader = this.$filter('filter')(allDownloadHeader, {IS_SHOWN: true});
    vm.downloadHeaderVal = angular.copy(vm.allDownloadHeader);
    vm.allDownloadHeader.push(constantHeader[constantHeader.length - 1]);
  }
}
