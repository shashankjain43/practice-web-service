'use strict';

export class SETTLEMENT_CONSTANT {

  static get getSettlementConstant() {
      return {
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
        'SETTLEMENT_HEADER':[
            {
                'key':'fileDownloadUrl',
                'displayValue':'Download',
                'isShown':true,
                'priority':6
            },
            {
                'key':'reportName',
                'displayValue':'Report Name',
                'isShown':true,
                'priority':2
            },
            {
                'key':'startTime',
                'displayValue':'Start Time',
                'isShown':true,
                'priority':3
            },
            {
                'key':'scheduled',
                'displayValue':'Scheduled',
                'isShown':false,
                'priority':4
            },
            {
                'key': 'endTime',
                'displayValue': 'End Time',
                'isShown': true,
                'priority': 5
            }]
      }
  }
}
