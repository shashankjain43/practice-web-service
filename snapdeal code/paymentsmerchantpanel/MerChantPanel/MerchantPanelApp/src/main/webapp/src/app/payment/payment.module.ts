import {TRANSACTION_CONSTANT} from "./transaction/transaction.constant";
import {APP_PAYMENT_URL} from "./payment.url";
import {paymentModuleConfig} from "./payement.config";
import {BulkRefundService} from "./bulkRefund/bulkrefund.service";
import {BULK_REFUND_CONSTANT} from "./bulkRefund/bulfRefund.constant";
import {BulkRefundController} from "./bulkRefund/bulkrefund.controller";
import paymentMockRun from "./payment.mockRun";
import {TransactionController} from "./transaction/transaction.controller";
import {TransactionService} from "./transaction/transaction.service";
import {RefundController} from "./refund/refund.controller";
import {REFUND_CONSTANT} from "./refund/refund.constant";
import {NgMax} from "../shared/directives/ngmax.directive";
import {REFUND_ACTION_CONSTANT} from "./refund/refund-action/refund-action.constant";
import {RefundService} from "./refund/refund.service";
import {RefundActionController} from "./refund/refund-action/refund-action.controller";

'use strict';
export default angular.module('app.payment', ['app.shared'])
  .constant('TRANSACTION_CONSTANT', TRANSACTION_CONSTANT)
  .service('TransactionService', TransactionService)
  .controller('TransactionController', TransactionController)

  .constant('BULK_REFUND_CONSTANT', BULK_REFUND_CONSTANT)
  .service('BulkRefundService', BulkRefundService)
  .controller('BulkRefundController', BulkRefundController)
  .directive(NgMax)
  .service('RefundService', RefundService)
  .constant('REFUND_CONSTANT', REFUND_CONSTANT)
  .controller('RefundController', RefundController)
  .constant('REFUND_ACTION_CONSTANT', REFUND_ACTION_CONSTANT)
  .controller('RefundActionController', RefundActionController)


.constant('APP_PAYMENT_URL', APP_PAYMENT_URL)
  .config(paymentModuleConfig)
  .run(paymentMockRun)
  .name;
