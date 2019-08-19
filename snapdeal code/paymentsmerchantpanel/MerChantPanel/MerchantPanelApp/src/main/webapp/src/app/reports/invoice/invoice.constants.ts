
'use strict';

export class INVOICE_CONSTANT {

    static get getInvoiceConstant() {
        return {
            'PAGINATION':[
                {
                    'KEY':'page',
                    'DISPLAY_VALUE':1,
                    'VALUE':1
                },{
                    'KEY':'limit',
                    'DISPLAY_VALUE':'limit',
                    'VALUE':10
                }],
            'DATE_FILTER':[
                {
                    'KEY':'startDate',
                    'DISPLAY_VALUE':'From Date',
                    'VALUE':'From Date'
                },
                {
                    'KEY':'endDate',
                    'DISPLAY_VALUE':'To Date',
                    'VALUE':'To Date'
                }],
            'INVOICE_HEADER':[
                {
                    'KEY':'fileDownloadUrl',
                    'DISPLAY_VALUE':'Download',
                    'IS_SHOWN':true,
                    'PRIORITY':6
                },
                {
                    'KEY':'reportName',
                    'DISPLAY_VALUE':'Report Name',
                    'IS_SHOWN':true,
                    'PRIORITY':2
                },
                {
                    'KEY':'startTime',
                    'DISPLAY_VALUE':'Start Time',
                    'IS_SHOWN':true,
                    'PRIORITY':3
                },
                {
                    'KEY':'isScheduled',
                    'DISPLAY_VALUE':'Scheduled',
                    'IS_SHOWN':false,
                    'PRIORITY':4
                },
                {
                    'KEY': 'endTime',
                    'DISPLAY_VALUE': 'End Time',
                    'IS_SHOWN': true,
                    'PRIORITY': 5
                }]
        }
    }
}
