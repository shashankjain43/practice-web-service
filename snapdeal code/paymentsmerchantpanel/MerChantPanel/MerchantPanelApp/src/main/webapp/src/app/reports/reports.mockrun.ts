import {APP_REPORT_URL} from "./reports.url";
'use strict';

/* @ngInject */
export default function reportsMockRun($httpBackend:angular.IHttpBackendService, API_CONFIG) {
  if (API_CONFIG.MOCK_RUN) {
    this.REPORTS_URL = APP_REPORT_URL.getReportsUrl;
    $httpBackend.whenGET(new RegExp(this.REPORTS_URL.SETTLEMENT_REPORT.url)).respond(function () {
      var settlementReportDataResponse ={
        "error" : null,
        "data" : {
            "merchantReportDetails": [
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report",
                    "startTime": "946665000000",
                   "endTime": "1455042600000",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report2",
                    "startTime": "946665000000",
                    "endTime": "1455042600000",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report3",
                      "startTime": "946665000000",
                   "endTime": "1455042600000",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report4",
                    "startTime": "946665000000",
                    "endTime": "1455042600000",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report5",
                    "startTime": "946665000000",
                    "endTime": "1455042600000",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report6",
                    "startTime": "946665000000",
                    "endTime": "1455042600000",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report7",
                    "startTime": "946665000000",
                    "endTime": "1455042600000",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report8",
                    "startTime": "2000-01-01 00:00:00.000",
                    "endTime": "2016-02-12 15:32:36.489",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report9",
                    "startTime": "2000-01-01 00:00:00.000",
                   "endTime": "1455042600000",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report10",
                    "startTime": "2000-01-01 00:00:00.000",
                    "endTime": "2016-02-12 15:32:36.489",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report11",
                    "startTime": "2000-01-01 00:00:00.000",
                    "endTime": "2016-02-12 15:32:36.489",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report12",
                    "startTime": "2000-01-01 00:00:00.000",
                    "endTime": "2016-02-12 15:32:36.489",
                    "scheduled": false
                },{
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report13",
                    "startTime": "2000-01-01 00:00:00.000",
                   "endTime": "1455042600000",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report14",
                    "startTime": "2000-01-01 00:00:00.000",
                    "endTime": "2016-02-12 15:32:36.489",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report15",
                    "startTime": "2000-01-01 00:00:00.000",
                    "endTime": "2016-02-12 15:32:36.489",
                    "scheduled": false
                },
                {
                    "fileDownloadUrl": "url",
                    "reportName": "Merchant Settlement Report16",
                    "startTime": "946665000000",
                    "endTime": "1455042600000",
                    "scheduled": false
                }
            ]
        }
      };
      return [200, settlementReportDataResponse, {}];
    });


      $httpBackend.whenGET(new RegExp(this.REPORTS_URL.INVOICE_REPORT.url)).respond(function () {
          var merchantReporResponse ={
              "error": null,
              "data": {
                  "merchantInvoiceDetails": [
                      {
                          "fileDownloadUrl": "url",
                          "reportName": "Merchant Settlement Report",
                          "startTime": "946665000000",
                          "endTime": "1467311400000",
                          "isScheduled": false
                      },
                      {
                          "fileDownloadUrl": "url",
                          "reportName": "Merchant Settlement Report2",
                          "startTime": "946665000000",
                          "endTime": "1455042600000",
                          "isScheduled": false
                      },
                      {
                          "fileDownloadUrl": "url",
                          "reportName": "Merchant Settlement Report3",
                          "startTime": "946665000000",
                          "endTime": "1455042600000",
                          "isScheduled": false
                      },
                      {
                          "fileDownloadUrl": "url",
                          "reportName": "Merchant Settlement Report4",
                          "startTime": "946665000000",
                          "endTime": "1455042600000",
                          "isScheduled": false
                      },
                      {
                          "fileDownloadUrl": "url",
                          "reportName": "Merchant Settlement Report5",
                          "startTime": "946665000000",
                          "endTime": "1455042600000",
                          "isScheduled": false
                      },
                      {
                          "fileDownloadUrl": "url",
                          "reportName": "Merchant Settlement Report6",
                          "startTime": "946665000000",
                          "endTime": "1455042600000",
                          "isScheduled": false
                      },
                      {
                          "fileDownloadUrl": "url",
                          "reportName": "Merchant Settlement Report16",
                          "startTime": "1464719400000",
                          "endTime": "946665000000",
                          "isScheduled": false
                      }
                  ]
              }
          };
          return [200, merchantReporResponse, {}];
      });
 }
}
