import {APP_INTEGRATION_URL} from "./integrationkey.url";

'use strict';

/* @ngInject */
export default function integrationMockRun($httpBackend:angular.IHttpBackendService, API_CONFIG) {
  if (API_CONFIG.MOCK_RUN) {
    this.INTEGRATION_URL = APP_INTEGRATION_URL.getIntegrationKeyUrl;
    $httpBackend.whenGET(new RegExp(this.INTEGRATION_URL.sandbox.url)).respond(function () {
      var sandboxDataResponse ={
        "error" : null,
        "data" : {
          "key": "4642fb40-7782-4583-98da-4f6380567dba",
          "url":"https://checkout-sandbox.freecharge.in/"
        }
      };
      return [200, sandboxDataResponse, {}];
    });
    $httpBackend.whenGET(new RegExp(this.INTEGRATION_URL.production.url)).respond(function () {
      var productionDataResponse = {
        "error" : null,
        "data" : {
          "key": "4642fb40-7782-4583-98da-4f6380567dba",
          "url":"https://checkout-sandbox.freecharge.in/"
        }
      };
      return [200, productionDataResponse, {}];
    });
  }
}
