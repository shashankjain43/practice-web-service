/**
 * Created by chitra.parihar on 17-03-2016.
 */

export function MENU_CONSTANTS() {
    return [
        {
            'name': 'ACCOUNTS',
            'displayName': 'My Account',
            'isDropdown': true,
            'serverKey': 'Account',
            'state': 'base.default.account',
            'checkPermission': 'false',
            'priority': 10,
            'defaultTabs': [{
                'checkPermission': false,
                'priority': 7,
                'displayName': 'Change password',
                'state': 'base.default.account.content.password'
            }],
            'permissions': [
                {
                    'checkPermission': true,
                    'name': 'VIEW_MERCHANT_PROFILE',
                    'serverKey': 'VIEW_MERCHANT_PROFILE',
                    'displayName': 'View Merchant profile',
                    'priority': 6,
                    'state': 'base.default.account.content.profile'
                },
                {
                    'checkPermission': true,
                    'name': 'MANAGE_USER',
                    'serverKey': 'MANAGE_USER',
                    'displayName': 'Manage User',
                    'priority': 8,
                    'state': 'base.default.account.content.manage'
                }]
        },
        {
            'name': 'PAYMENTS',
            'serverKey': 'Payments',
            'displayName': 'Payments',
            'isDropdown': true,
            'defaultTabs': [],
            'priority': 1,
            'checkPermission': true,
            'permissions': [
                {
                    'name': 'VIEW_TRANSACTION',
                    'serverKey': 'VIEW_TRANSACTION',
                    'displayName': 'View Transaction',
                    'checkPermission': true,
                    'priority': 1,
                    'state': 'base.default.payment.transaction'
                },
                {
                    'name': 'INITIATE_REFUND',
                    'serverKey': 'MCNT_INIT_REFUND',
                    'displayName': 'Initiate Refund',
                    'checkPermission': true,
                    'priority': 2,
                    'state': 'base.default.payment.base-refund.refund'
                },
                {
                    'name': 'BULK_REFUND',
                    'serverKey': 'MCNT_INIT_REFUND',
                    'displayName': 'Bulk Refund',
                    'checkPermission': true,
                    'priority': 3,
                    'state': 'base.default.payment.bulkRefund'
                }/*,
                {
                    'name': 'ACCEPT_PAYMENTS',
                    'serverKey': 'ACCEPT_PAYMENTS',
                    'displayName': 'Accept Payments',
                    'checkPermission': true,
                    'priority': 4,
                    'state': 'base.default.payment.acceptPayments'
                }*/

            ]
        },
        {

            'name': 'REPORT',
            'displayName': 'Reports',
            'checkPermission': true,
            'isDropdown': true,
            'serverKey': 'VIEW_TRANSACTION',
            'priority': 2,
            'defaultTabs': [],
            'permissions': [
                {
                    'name': 'SETTLEMENT_REPORT',
                    'serverKey': 'VIEW_TRANSACTION',
                    'displayName': 'Settlement Report',
                    'checkPermission': true,
                    'priority': 1,
                    'state': 'base.default.reports.settlement'
                },
                {
                    'name': 'INVOICE_REPORT',
                    'serverKey': 'VIEW_TRANSACTION',
                    'checkPermission': true,
                    'displayName': 'Invoice Report',
                    'priority': 2,
                    'state': 'base.default.reports.invoice'
                }]
        },
        {
            'name': 'DOWNLOAD_HISTORY',
            'serverKey': 'VIEW_TRANSACTION',
            'defaultTabs': [],
            'priority': 3,
            'displayName': 'Download History',
            'checkPermission': true,
            'isDropdown': false,
            'permissions': [
                {
                    'name': 'DOWNLOAD_HISTORY',
                    'serverKey': 'VIEW_TRANSACTION',
                    'displayName': 'Download History',
                    'checkPermission': true,
                    'priority': 4,
                    'state': 'base.default.download-history'
                }],
            'state': 'base.default.download-history'
        },
        {
            'name': 'INTEGRATION',
            'displayName': 'Integration',
            'checkPermission': true,
            'isDropdown': true,
            'serverKey': 'INTEGRATION',
            'defaultTabs': [],
            'priority': 4,
            'permissions': [
                {
                    'name': 'SANDBOX',
                    'serverKey': 'SANDBOX',
                    'checkPermission': true,
                    'displayName': 'Sandbox',
                    'priority': 5,
                    'state': 'base.default.integration.sandbox'
                },
                {
                    'name': 'PRODUCTION',
                    'serverKey': 'PRODUCTION',
                    'displayName': 'Production',
                    'checkPermission': true,
                    'priority': 4,
                    'state': 'base.default.integration.production'
                }]

        }
    ];
}
