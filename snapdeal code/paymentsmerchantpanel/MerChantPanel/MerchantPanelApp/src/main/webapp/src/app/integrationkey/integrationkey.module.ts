import {moduleConfig} from "./integrationkey.config";
import {SandboxController} from "./sandbox/sandbox.controller";
import {ProductionController} from "./production/production.controller";
import {SandboxService} from "./sandbox/sandbox.service";
import {ProductionService} from "./production/production.service";
import {APP_INTEGRATION_URL} from "./integrationkey.url";
import integrationMockRun from "./integration.mockRun";

'use strict';
export default angular.module('app.integrationkey', [])
  .controller('SandboxController', SandboxController)
  .controller('ProductionController', ProductionController)
  .constant('APP_INTEGRATION_URL', APP_INTEGRATION_URL)
  .run(integrationMockRun)
  .service('SandboxService', SandboxService)
  .service('ProductionService', ProductionService)
  .config(moduleConfig)
  .name;


