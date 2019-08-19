
import {IPermissionService} from "../../shared/permission/permission.service";
import {IHttpService} from "../../shared/services/http.interface";
import {APP_PAYMENT_URL} from "../payment.url";

'use strict';
export interface IBulkRefundService {
    downloadSampleFile:()=>any;
    bulkRefundHistory:()=>any;
    downloadBulkRefundOutputFile:(string)=>any;
    fileUpload:(File)=>any;
}

/* @ngInject */
export class BulkRefundService implements IBulkRefundService {
    user:any;
    APP_PAYMENT_URL:any;
    $state:ng.ui.IStateService;
    HttpService:IHttpService;
    $q:ng.IQService;
    API_CONFIG:any;
    Upload:angular.angularFileUpload.IUploadService;

    constructor(API_CONFIG:any, $q:ng.IQService, HttpService:IHttpService, Upload:angular.angularFileUpload.IUploadService, PermissionService:IPermissionService, $state:ng.ui.IStateService) {
        this.HttpService = HttpService;
        this.user = PermissionService.user;
        this.APP_PAYMENT_URL = APP_PAYMENT_URL.getPaymentURL;
        this.$q = $q;
        this.$state = $state;
        this.API_CONFIG = API_CONFIG;
        this.Upload=Upload;
    }

    downloadSampleFile() {
        return this.HttpService.requestFileDownload(this.APP_PAYMENT_URL.DOWNLOAD_BULK_FILE.METHOD, this.APP_PAYMENT_URL.DOWNLOAD_BULK_FILE.URL);
    }

    fileUpload(file:File) {
        var requestObject = {
            merchantId: this.user.merchantId,
            token: this.user.token,
            file: file
        };
        file.upload = this.Upload.upload({
            url: this.API_CONFIG.BASE_URL + this.APP_PAYMENT_URL.FILE__UPLOAD.URL,
            method: 'POST',
            data: requestObject
        });
        var deferred = this.$q.defer();
        file.upload.then(function (response) {
                deferred.resolve(response);
            })
            .catch(function () {
                this.$state.go('error');
            });
        return deferred.promise;
    }

    bulkRefundHistory() {
        return this.HttpService.request(this.APP_PAYMENT_URL.BULK_REFUND_HISTORY.METHOD, this.APP_PAYMENT_URL.BULK_REFUND_HISTORY.URL);
    }

    downloadBulkRefundOutputFile(id) {
        return this.HttpService.request(this.APP_PAYMENT_URL.DOWNLOAD_BULK_REFUND_FILE.METHOD, this.APP_PAYMENT_URL.DOWNLOAD_BULK_REFUND_FILE.URL + '?id=' + id);
    }
}
