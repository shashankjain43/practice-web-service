/**
 * Created by chitra.parihar on 17-03-2016.
 */

export function ACCESS_CONSTANT() {
    return {
      PERMISSIONS: {
        'MANAGE_USER': {
          'KEY': 'MANAGE_USER',
          'SERVER_KEY': 'MANAGE_USER',
          'IS_CATEGORY': false
        },
        'ACCOUNTS': {
          'KEY': 'Account',
          'SERVER_KEY': 'Account',
          'IS_CATEGORY': true
        },
        'VIEW_MERCHANT_PROFILE': {
          'KEY': 'VIEW_MERCHANT_PROFILE',
          'SERVER_KEY': 'VIEW_MERCHANT_PROFILE',
          'IS_CATEGORY': false
        },
        'PAYMENTS': {
          'KEY': 'Payments',
          'SERVER_KEY': 'Payments',
          'IS_CATEGORY': true
        },
        'VIEW_TRANSACTION': {
          'KEY': 'VIEW_TRANSACTION',
          'SERVER_KEY': 'VIEW_TRANSACTION',
          'IS_CATEGORY': false
        },
        'INITIATE_REFUND': {
          'KEY': 'MCNT_INIT_REFUND',
          'SERVER_KEY': 'MCNT_INIT_REFUND',
          'IS_CATEGORY': false
        },
        'DOWNLOAD_HISTORY': {
          'KEY': 'DOWNLOAD_HISTORY',
          'SERVER_KEY': 'VIEW_TRANSACTION',
          'IS_CATEGORY': false
        },
        'INTEGRATION': {
          'KEY': 'INTEGRATION',
          'SERVER_KEY': 'INTEGRATION',
          'IS_CATEGORY': true
        },
        'SANDBOX': {
          'KEY': 'SANDBOX',
          'SERVER_KEY': 'SANDBOX',
          'IS_CATEGORY': false
        },
        'PRODUCTION': {
          'KEY': 'PRODUCTION',
          'SERVER_KEY': 'PRODUCTION',
          'IS_CATEGORY': false
        },
        'BULK_REFUND': {
          'KEY': 'BULK_REFUND',
          'SERVER_KEY': 'MCNT_INIT_REFUND',
          'IS_CATEGORY': false
        },
        'REPORT': {
          'KEY': 'REPORT',
          'SERVER_KEY': 'VIEW_TRANSACTION',
          'IS_CATEGORY': true
        },
        'SETTLEMENT_REPORT': {
          'KEY': 'VIEW_TRANSACTION',
          'SERVER_KEY': 'VIEW_TRANSACTION',
          'IS_CATEGORY': false
        },
        'INVOICE_REPORT':{
         'KEY':'VIEW_TRANSACTION',
         'SERVER_KEY':'VIEW_TRANSACTION',
         'IS_CATEGORY': false
         }
      }
    }
  }
