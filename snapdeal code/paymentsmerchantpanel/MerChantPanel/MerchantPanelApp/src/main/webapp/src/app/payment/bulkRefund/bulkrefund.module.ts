import {BulkRefundService} from "./bulkrefund.service";
import {BulkRefundController} from "./bulkrefund.controller";
import {BULK_REFUND_CONSTANT} from "./bulfRefund.constant";

(function() {
    'use strict';
    angular.module('app.bulkrefund', [])
      .constant('BULK_REFUND_CONSTANT', BULK_REFUND_CONSTANT)
      .service('BulkRefundService', BulkRefundService)
      .controller('BulkRefundController', BulkRefundController)
})();
