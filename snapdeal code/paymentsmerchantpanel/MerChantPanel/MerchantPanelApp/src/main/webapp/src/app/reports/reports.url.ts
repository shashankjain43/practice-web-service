export class APP_REPORT_URL {
    static get getReportsUrl() {
        return {
            SETTLEMENT_REPORT: {
                METHOD: 'GET',
                URL: '/data/v2/get/settlementreport'
            },
            INVOICE_REPORT: {
                METHOD: 'GET',
                URL: '/data/v2/get/invoice'
            }
        }
    }
}
