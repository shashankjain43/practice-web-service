export function TRANSACTION_CONSTANT() {
    return {
      TRANSACTION_CONSTANT: {
        'PAGINATION':[
          {
            'key':'page',
            'displayValue':1,
            'value':1
          },{
            'key':'limit',
            'displayValue':'limit',
            'value':10
          }],
        'AMOUNT_FILTER': [
          {
          'key':'fromAmount',
          'displayValue': 'From Amount',
          'value':''
          },
          {
            'key':'toAmount',
            'displayValue': 'To Amount',
            'value':''
          }],
        'DATE_FILTER':[
          {
            'key':'startDate',
            'displayValue':'From Date',
            'value':'From Date'
          },
          {
            'key':'endDate',
            'displayValue':'To Date',
            'value':'To Date'
          }],
        'TRANSACTION_TYPE_FILTER':[
          {
            'key': 'txnTypeList',
            'displayValue': 'PAYMENT',
            'value': 'PAYMENT',
            'selected': false
          },
          {
            'key': 'txnTypeList',
            'displayValue':'REFUND',
            'value': 'REFUND',
            'selected': false
          }
        ],
        'TRANSACTION_STATUS_FILTER':[
          {
            'key':'txnStatusList',
            'displayValue':'PENDING',
            'value':'PENDING',
            'selected': false
          },
          {
            'key':'txnStatusList',
            'displayValue':'SUCCESS',
            'value':'SUCCESS',
            'selected': false
          },
          {
            'key':'txnStatusList',
            'displayValue':'FAILED',
            'value':'FAILED',
            'selected': false
          },
          {
            'key':'txnStatusList',
            'displayValue':'SETTLED',
            'value':'SETTLED',
            'selected': false
          }
        ],
        'DROPDOWN_FILTER': [
          {
            'key':'',
            'displayValue':'ALL',
            'value':'ALL'
          },
          {
            'key':'txnStatusList',
            'displayValue':'SETTLED',
            'value':'SETTLED'
          },
          {
            'key':'txnTypeList',
            'displayValue':'REFUND',
            'value':'REFUND'
          }
        ],
        'TRANSACTION_SEARCH':[

          {
            'key':'transactionId',
            'displayValue':'Transaction Id'
          },
          {
            'key':'merchantTxnId',
            'displayValue':'Merchant Transaction Id'

          },

          {
            'key':'settlementId',
            'displayValue':'Settlement Id'

          }
          ,
          {
            'key':'customerId',
            'displayValue':'Customer Id'

          },
          {
            'key':'orderId',
            'displayValue':'Order Id'

          },
          {
            'key':'productId',
            'displayValue':'Product Id'

          },
          {
            'key':'storeId',
            'displayValue':'Store Id'

          },
          {
            'key':'terminalId',
            'displayValue':'Terminal Id'

          }

        ],
        'TRANSACTION_HEADER': [
          {
            'key': 'txnDate',
            'displayValue': 'Date',
            'isShown': true,
            'priority': 2
          },
          {
            'key': 'fcTxnId',
            'displayValue': 'Transaction Id',
            'isShown': false,
            'priority': 57,
            'isInChooseField': true
          },
          {
            'key': 'merchantTxnId',
            'displayValue': 'Merchant Transaction Id',
            'isShown': false,
            'priority': 100,
            'isInChooseField': true
          },
          {
            'key': 'orderId',
            'displayValue': 'Order Id',
            'isShown': true,
            'priority': 1
          },
          {
            'key': 'txnType',
            'displayValue': 'Transaction<br>Type',
            'isShown': true,
            'priority': 4.1,
            'isInChooseField': false
          },
          {
            'key': 'txnStatus',
            'displayValue': 'Transaction<br>Status',
            'isShown': true,
            'priority': 4,
            'isInChooseField': false
          },
          {
            'key': 'merchantFee',
            'displayValue': 'Merchant<br>Fee',
            'isShown': true,
            'priority': 5,
            'isInChooseField': false
          },
          {
            'key': 'serviceTax',
            'displayValue': 'Service Tax',
            'isShown': false,
            'priority': 8,
            'isInChooseField': true
          },
          {
            'key': 'swachBharatCess',
            'displayValue': 'Swach Bharat Cess',
            'isShown': false,
            'priority': 7,
            'isInChooseField': true
          },
          {
            'key': 'krishiKalyanCess',
            'displayValue': 'Krishi Kalyan Cess',
            'isShown': false,
            'priority': 7,
            'isInChooseField': true
          },
          {
            'key': 'dealerId',
            'displayValue': 'Dealer ID',
            'isShown': false,
            'priority': 8,
            'isInChooseField': true
          },
          {
            'key': 'patnerId',
            'displayValue': 'Partner ID',
            'isShown': false,
            'priority': 8,
            'isInChooseField': true
          },
          {
            'key': 'platformId',
            'displayValue': 'Platform ID',
            'isShown': false,
            'priority': 8,
            'isInChooseField': true
          },
          {
            'key': 'txnType',
            'displayValue': 'Transaction<br>Type',
            'isShown': false,
            'priority': 100,
            'isInChooseField': false
          },
          {
            'key': 'totalTxnAmount',
            'displayValue': 'Total Transaction<br>Amount',
            'isShown': true,
            'priority': 100,
            'isInChooseField': false
          },
          {
            'key': 'netDeduction',
            'displayValue': 'Net<br>deduction',
            'isShown': true,
            'priority': 6,
            'isInChooseField': false
          },
          {
            'key': 'payableAmount',
            'displayValue': 'Payable<br>amount',
            'isShown': true,
            'priority': 8,
            'isInChooseField': false
          },
          {
            'key': 'merchantId',
            'displayValue': 'Merchant Id',
            'isShown': false,
            'priority': 100,
            'isInChooseField': true
          },
          {
            'key': 'merchantName',
            'displayValue': 'Merchant Name',
            'isShown': false,
            'priority': 100,
            'isInChooseField': true
          },

          {
            'key': 'storeId',
            'displayValue': 'Store Id',
            'isShown': false,
            'priority': 100,
            'isInChooseField': true
          },
          {
            'key': 'storeName',
            'displayValue': 'Store Name',
            'isShown': false,
            'priority': 100,
            'isInChooseField': true
          },
          {
            'key': 'terminalId',
            'displayValue': 'Terminal Id',
            'isShown': false,
            'priority': 100,
            'isInChooseField': true
          },
          {
            'key': 'custId',
            'displayValue': 'Customer Id',
            'isShown': false,
            'priority': 54,
            'isInChooseField': true

          },
          {
            'key': 'custName',
            'displayValue': 'Customer Name',
            'isShown': false,
            'priority': 52,
            'isInChooseField': true
          },
          {
            'key': 'custIP',
            'displayValue': 'Customer IP',
            'isShown': false,
            'priority': 100,
            'isInChooseField': true

          },
          {
            'key': 'productId',
            'displayValue': 'Product Id',
            'isShown': false,
            'priority': 53,
            'isInChooseField': true
          },

          {
            'key': 'location',
            'displayValue': 'Location',
            'isShown': false,
            'priority': 55,
            'isInChooseField': true
          },
          {
            'key': 'shippingCity',
            'displayValue': 'Shipping City',
            'isShown': false,
            'priority': 56,
            'isInChooseField': true

          },
          {
            'key': 'settlementId',
            'displayValue': 'Settlement Id',
            'isShown': true,
            'priority': 100,
            'isInChooseField': false

          },
          {
            'key': 'email',
            'displayValue': 'Email Id',
            'isShown': true,
            'priority': 11,
            'isInChooseField': false,
          }, {
            'key': 'mobile',
            'displayValue': 'Mobile No',
            'isShown': true,
            'priority': 12,
            'isInChooseField': false,
          }
        ]
      }
    }
}
