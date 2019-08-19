import {APP_PAYMENT_URL} from "./payment.url";

/* @ngInject */
export default function paymentMockRun($httpBackend:angular.IHttpBackendService, API_CONFIG) {
    if (API_CONFIG.MOCK_RUN) {
            this.APP_PAYMENT_URL = APP_PAYMENT_URL.getPaymentURL;
            $httpBackend.whenPOST(new RegExp(this.APP_PAYMENT_URL.FILE__UPLOAD.URL)).respond(function () {
                var  fileUpload={
                    'error':null,
                    'data':{
                        'message':'Successfully uploaded'
                    }
                }
                return [200, fileUpload, {}];
            });

            $httpBackend.whenGET(new RegExp(this.APP_PAYMENT_URL.BULK_REFUND_HISTORY.URL)).respond(function () {
                var downloadHistoryResponse ={
                    'error': null,
                    'data': {
                        'info': [
                            {
                                'id': 1,
                                'viewed': false,
                                'fileName':'1nadasssssssasddddddddme',
                                'uploadStatus':'INPROGRSS',
                                'refundStatus': 'INPROGRESS',
                                'timestamp': 1452024323000
                            },
                            {
                                'id': 2,
                                'viewed': false,
                                'fileName':'2namjglllluguogdisadadssasdse',
                                'uploadStatus':'INPROGRSS',
                                'refundStatus': 'INPROGRESS',
                                'timestamp': 1452049695000
                            },
                            {
                                'id': 3,
                                'viewed': false,
                                'fileName':'3name',
                                'uploadStatus':'INPROGRSS',
                                'refundStatus': 'INPROGRESS',
                                'timestamp': 1452050347000
                            },
                            {
                                'id': 4,
                                'viewed': false,
                                'fileName':'4name',
                                'uploadStatus':'Failed',
                                'refundStatus': 'INPROGRESS',
                                'timestamp': 1452050658000
                            },
                            {
                                'id': 5,
                                'viewed': false,
                                'fileName':'5name',
                                'uploadStatus':'Failed',
                                'refundStatus': 'INPROGRESS',
                                'timestamp': 1452050872000
                            },
                            {
                                'id': 6,
                                'viewed': false,
                                'fileName':'6name',
                                'uploadStatus':'Failed',
                                'refundStatus': 'INPROGRESS',
                                'timestamp': 1452051241000
                            },
                            {
                                'id': 7,
                                'viewed': false,
                                'fileName':'7name',
                                'uploadStatus':'Failed',
                                'refundStatus': 'INPROGRESS',
                                'timestamp': 1452051657000
                            },
                            {
                                'id': 8,
                                'viewed': false,
                                'fileName':'18name',
                                'uploadStatus':'Failed',
                                'refundStatus': 'INPROGRESS',
                                'timestamp': 1452052101000
                            },
                            {
                                'id': 9,
                                'viewed': false,
                                'fileName':'9nasadddddddddddme',
                                'uploadStatus':'Failed',
                                'refundStatus': 'INPROGRESS',
                                'timestamp': 1452052511000
                            },
                            {
                                'id': 10,
                                'viewed': false,
                                'fileName':'10ndsaaaaaaaaaame',
                                'uploadStatus':'Failed',
                                'refundStatus': 'INPROGRESS',
                                'timestamp': 1452082511000
                            }
                        ]
                    }
                };
                return [200, downloadHistoryResponse, {}];
            });
            $httpBackend.whenGET(new RegExp(this.APP_PAYMENT_URL.TRANSACTION_VIEW.URL)).respond(function () {
                var accountResponse = {
                    'error': null,
                    'data': {
                        'mpTransactions': [
                            {
                                'txnDate': 1450170351341,
                                'fcTxnId': 'fcTxnId',
                                'merchantTxnId': 'merchantTxnId',
                                'orderId': 'orderId',
                                'txnType': 'DEBIT',
                                'txnStatus': 'PENDING',
                                'merchantFee': 1000,
                                'serviceTax': 5.2,
                                'swachBharatCess': 2.2,
                                'totalTxnAmount': 100000,
                                'netDeduction': 10,
                                'payableAmount': 100,
                                'merchantId': 'merchantId',
                                'merchantName': 'merchantName',
                                'storeId': 'storeId',
                                'storeName': 'storeName',
                                'terminalId': 'terminalId',
                                'custId': '1234',
                                'custName': 'custName',
                                'custIP': 'custIP',
                                'productId': 'productId',
                                'location': 'location',
                                'shippingCity': 'shippingCity',
                                'settlementId': 'settlementId'
                            },
                            {
                                'txnDate': 1450170351342,
                                'fcTxnId': 'fcTxnId',
                                'merchantTxnId': 'merchantTxnId',
                                'orderId': 'orderId',
                                'txnType': 'REFUND',
                                'txnStatus': 'FAILED',
                                'merchantFee': 1000,
                                'serviceTax': 5.2,
                                'swachBharatCess': 2.2,
                                'totalTxnAmount': 100000,
                                'netDeduction': 10,
                                'payableAmount': 100,
                                'merchantId': 'merchantId',
                                'merchantName': 'merchantName',
                                'storeId': 'storeId',
                                'storeName': 'storeName',
                                'terminalId': 'terminalId',
                                'custId': '1234',
                                'custName': 'custName',
                                'custIP': 'custIP',
                                'productId': 'productId',
                                'location': 'location',
                                'shippingCity': 'shippingCity',
                                'settlementId': 'settlementId'
                            }
                        ]
                    }

                };
              var accounterrorResponse = {
                'error': {'message':'Error in view transaction'},
                'data': null

              };
                return [200, accountResponse, {}];
            });
        }
    }

