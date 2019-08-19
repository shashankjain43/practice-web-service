import {GlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {IGlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {APP_MESSAGE_CONSTANT} from "../../shared/constants/message.constant";
import {ISandboxService} from "./sandbox.interface";
import {SandboxService} from "./sandbox.service";

"use strict";
export class SandboxController {

  private GlobalOmnitureService:IGlobalOmnitureService;
  SandboxService:ISandboxService;
  public APP_MESSAGES;
  public errorMessage;
  public sandData:any;
  public sandboxData:any;

  /* @ngInject */
  constructor(GlobalOmnitureService:IGlobalOmnitureService,
  APP_MESSAGE:APP_MESSAGE_CONSTANT,
  SandboxService: ISandboxService,
  sandboxData:any)
  {

    this.GlobalOmnitureService = GlobalOmnitureService;
    this.APP_MESSAGES = APP_MESSAGE_CONSTANT.getMessageConstants;

    var vm = this;
    vm.errorMessage=vm.APP_MESSAGES.EMPTY_INTEGRATION_KEY+'sandbox';
      function init()
      {
          var sandData = sandboxData.data;
          vm.sandboxData = sandData;
          if(sandData.data !== null) {
              var url = sandData.data.url;
              var key = sandData.data.key;
          }
          if(sandData.error !== null)
              vm.errorMessage = sandData.error.errorMessage;
          GlobalOmnitureService.sandbox(url, key, sandData.error);
      }
      init();
  }
}
