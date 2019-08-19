import {baseConfig} from "./base.config";
import {BASE_CONSTANTS} from "./base.constant";
import {LayoutController} from "./layouts/layout.controller.ts";
import {SubHeaderController} from "./layoutComponent/subHeader/subHeader.controller";
import {HeaderController} from "./layoutComponent/header/header.controller";
import {MenuController} from "./layoutComponent/sidebarLeft/sidebarLeft.controller";
/**
 * Created by chitra.parihar on 14-03-2016.
 */

'use strict';

export default angular.module('app.base', ['app.shared'])
  .config(baseConfig)
  .constant('BASE_CONSTANT', BASE_CONSTANTS)
  .controller('LayoutController', LayoutController)
  .controller('SubHeaderController', SubHeaderController)
  .controller('HeaderController',HeaderController)
  .controller('MenuController',MenuController)
  .name;
