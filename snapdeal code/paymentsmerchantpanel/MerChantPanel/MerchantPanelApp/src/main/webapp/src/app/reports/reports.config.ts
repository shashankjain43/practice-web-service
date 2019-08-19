import {ISettlementService} from "./settlement/settlement.interface";
import {SETTLEMENT_CONSTANT} from "./settlement/settlement.constant";
import {IInvoiceService} from "./invoice/invoice.interface";
import {INVOICE_CONSTANT} from "./invoice/invoice.constants";

'use strict';

/* @ngInject */
export function moduleConfig($stateProvider:angular.ui.IStateProvider , $locationProvider: angular.ILocationProvider) {
    $stateProvider
        .state('base.default.reports', {
            abstract: true,
            views: {
                '': {
                    templateUrl: 'app/integrationkey/integrationkey.tmpl.html'
                },
                'subHeader': {
                    templateUrl: 'app/base/layoutComponent/subHeader/subHeader.tmpl.html',
                    controller: 'SubHeaderController',
                    controllerAs: 'vm',
                    resolve: {
                        /* @ngInject */
                        SubHeaderFor: function (ACCESS_CONSTANT) {
                            return ACCESS_CONSTANT().PERMISSIONS.REPORT;
                        }
                    }
                }
            }
        })
        .state('base.default.reports.settlement', {
            url: '/settlement',
            controller: 'SettlementController',
            templateUrl: 'app/reports/settlement/settlement.tmpl.html',
            controllerAs: 'vm',
            resolve: {
                /* @ngInject */
                settlement: function (SettlementService:ISettlementService, SETTLEMENT_CONSTANT:SETTLEMENT_CONSTANT) {
                    var filters = {
                        'page': angular.copy(SETTLEMENT_CONSTANT.getSettlementConstant.PAGINATION[0]),
                        'limit': angular.copy(SETTLEMENT_CONSTANT.getSettlementConstant.PAGINATION[1])
                    };
                    return SettlementService.getSettlementReport(filters);
                }
            }
        })
        .state('base.default.reports.invoice', {
            url: '/invoice',
            controller: 'InvoiceController',
            templateUrl: 'app/reports/invoice/invoice.tmpl.html',
            controllerAs: 'vm',
            resolve: {
                /* @ngInject */
                invoice: function (InvoiceService:IInvoiceService, INVOICE_CONSTANT:INVOICE_CONSTANT) {
                    var filters = {
                        'page': angular.copy(INVOICE_CONSTANT.getInvoiceConstant.PAGINATION[0]),
                        'limit': angular.copy(INVOICE_CONSTANT.getInvoiceConstant.PAGINATION[1])
                    };

                    return InvoiceService.getInvoiceReport(filters);
                }
            }
        });
        // use the HTML5 History API
         /*   $locationProvider.html5Mode(true);
        $locationProvider.hashPrefix('!'); */
}
