import {moduleConfig} from "./mob.config";
import {mobController} from "./mob.controller";
import {MobService} from "./mob.service";
import {MOB_URL} from "./mob.url";

'use strict';
export default angular.module('app.mob', ['app.shared'])
  .config(moduleConfig)
  .controller('mobController', mobController)
  .service('MobService', MobService)
  .constant('MOB_URL',MOB_URL)
  .name;


