/**
 * Created by chitra.parihar on 03-12-2015.
 */

export class APP_PAYMENT_URL {
    static get getPaymentURL() {
        return {
            TRANSACTION_VIEW: {
                METHOD: 'GET',
                URL: '/txn/v1/gettxns'
            },
            TRANSACTION_SEARCH: {
                METHOD: 'GET',
                URL: '/txn/v1/search'
            },
            SUBMIT_AMOUNT: {
                METHOD: 'POST',
                URL: '/txn/v1/refund'
            },
            INITIATE_GENERATE_REPORT: {
                METHOD: 'GET',
                URL: '/txn/v1/export'
            },
            FILE__UPLOAD: {
                METHOD: 'POST',
                URL: '/txn/v1/bulkrefund'
            },
            BULK_REFUND_HISTORY: {
                METHOD: 'GET',
                URL: '/data/v1/get/bulkrefund/listdownloads'
            },
            DOWNLOAD_BULK_FILE: {
                METHOD: 'GET',
                URL: '/data/v1/download/refundtmpl'
            },
            DOWNLOAD_BULK_REFUND_FILE: {
                METHOD: 'GET',
                URL: '/data/v1/bulkrefund/download'
            },
            SHOW_REFUND_AMOUNT: {
                METHOD: 'GET',
                URL: '/txn/v1/refundamount'
            }
        };
    }

}

