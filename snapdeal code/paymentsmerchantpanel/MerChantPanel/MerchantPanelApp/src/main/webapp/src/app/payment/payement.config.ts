
'use strict';
  import {ITransactionService} from "./transaction/ITransactionService";

/* @ngInject */
export function paymentModuleConfig($stateProvider:angular.ui.IStateProvider , $locationProvider: angular.ILocationProvider) {
        $stateProvider
            .state('base.default.payment', {
                abstract: true,
                views: {
                    '': {
                        templateUrl: 'app/payment/payment.tmpl.html'
                    },
                    'subHeader':{
                        templateUrl: 'app/base/layoutComponent/subHeader/subHeader.tmpl.html',
                        controller: 'SubHeaderController',
                        controllerAs: 'vm',
                        resolve:{
                            SubHeaderFor: function(ACCESS_CONSTANT){
                                return ACCESS_CONSTANT().PERMISSIONS.PAYMENTS;
                            }
                        }
                    }
                }
            })
            .state('base.default.payment.transaction', {
                url: '/transaction',
                controller:'TransactionController',
                templateUrl: 'app/payment/transaction/transaction.tmpl.html',
                controllerAs: 'vm',
                params: {searchParam: null},
                resolve:{
                    searchParam:function(TransactionService: ITransactionService, $stateParams){
                        return $stateParams.searchParam;
                    },
                    allTransactions: function(TransactionService: ITransactionService, $stateParams){
                        if($stateParams.searchParam === null){
                            return TransactionService.getFilteredTransactions(null, null);
                        }
                        return TransactionService.getFilteredTransactions(null, $stateParams.searchParam);
                    }

                }
            })
            /*.state('base.default.payment.acceptPayments', {
                url: '/accept-payments',
                controller:'AcceptPaymentsController',
                templateUrl: 'app/payment/acceptPayments/acceptPayments.tmpl.html',
                controllerAs: 'vm',
                params: {searchParam: null},
                resolve:{
                    searchParam:function(TransactionService: ITransactionService, $stateParams){
                        return $stateParams.searchParam;
                    }
                }
            })*/
            .state('base.default.payment.base-refund', {
                abstract: true,
                templateUrl: 'app/payment/refund/base-refund.tmpl.html'
            })
            .state('base.default.payment.base-refund.refund', {
                url: '/refund',
                views: {
                    '':{
                        controller:'RefundController',
                        templateUrl: 'app/payment/refund/refund.tmpl.html',
                        controllerAs: 'vm',
                        resolve: {
                            allTransactions: function (TransactionService) {
                                return TransactionService.viewRefund(null, null);
                            }
                        }
                    },
                    'refund-action':{
                        templateUrl: 'app/payment/refund/refund-action/refund-action.tmpl.html',
                        controller:'RefundActionController',
                        controllerAs: 'vm'
                    }
                }
            })
            .state('base.default.payment.bulkRefund', {
                url: '/bulkRefund',
                controller:'BulkRefundController',
                templateUrl: 'app/payment/bulkRefund/bulkRefund.tmpl.html',
                controllerAs: 'vm'
            });

              // use the HTML5 History API
        /*   $locationProvider.html5Mode(true);
        $locationProvider.hashPrefix('!'); */
    }
