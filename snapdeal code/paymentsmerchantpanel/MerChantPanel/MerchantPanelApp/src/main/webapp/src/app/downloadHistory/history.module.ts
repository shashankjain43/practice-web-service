import {moduleConfig} from "./history.config";
import {HistoryController} from "./history.controller";
import {HistoryService} from "./history.service";
import {HISTORY_URL} from "./history.url";
import {HISTORY_CONSTANT} from "./history.constant";
import historyMockRun from "./history.mockRun";
import {capitalize} from "./history.filters";

/**
 * Created by chitra.parihar on 08-03-2016.
 */

'use strict';
export default angular.module('app.history', ['app.shared'])
  .constant('History_URL', HISTORY_URL)
  .config(moduleConfig)
  .run(historyMockRun)
  .filter('capitalize',capitalize)
  .constant('History_Constant', HISTORY_CONSTANT)
  .controller('historyController', HistoryController)
  .service('HistoryService', HistoryService)
  .name;


