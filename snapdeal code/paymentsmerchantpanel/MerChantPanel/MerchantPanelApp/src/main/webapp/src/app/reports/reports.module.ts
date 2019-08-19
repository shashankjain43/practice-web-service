import {moduleConfig} from "./reports.config";
import {SettlementService} from "./settlement/settlement.service";
import reportsMockRun from "./reports.mockrun";
import {SETTLEMENT_CONSTANT} from "./settlement/settlement.constant";
import {APP_REPORT_URL} from "./reports.url";
import {SettlementController} from "./settlement/settlement.controller";
import {InvoiceController} from "./invoice/invoice.controller";
import {IInvoiceService} from "./invoice/invoice.interface";
import {INVOICE_CONSTANT} from "./invoice/invoice.constants";
import {InvoiceService} from "./invoice/invoice.service";

'use strict';
export default angular.module('app.reports', [])
    .controller('SettlementController', SettlementController)
    .service('SettlementService', SettlementService)
    .controller('InvoiceController', InvoiceController)
    .service('InvoiceService', InvoiceService)
    .constant('INVOICE_CONSTANT', INVOICE_CONSTANT)
    .constant('APP_REPORT_URL', APP_REPORT_URL)
    .constant('SETTLEMENT_CONSTANT', SETTLEMENT_CONSTANT)
    .run(reportsMockRun)
    .config(moduleConfig)
    .name;

