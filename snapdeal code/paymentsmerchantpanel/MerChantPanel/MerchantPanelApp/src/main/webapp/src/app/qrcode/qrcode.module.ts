import {QrcodeController} from "./qrcode.controller";
import {QrcodeService} from "./qrcode.service";
import {qrcodeConfig} from "./qrcode.config";

'use strict';
export default angular.module('app.qrcode', ['monospaced.qrcode'])
  .controller('QrcodeController', QrcodeController)
  .service('QrcodeService', QrcodeService)
  .config(qrcodeConfig)
  .name;
