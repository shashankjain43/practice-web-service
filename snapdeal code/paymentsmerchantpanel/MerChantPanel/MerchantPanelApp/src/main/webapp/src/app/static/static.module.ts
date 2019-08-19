import {moduleConfig} from "./static.config";

import {StaticController} from "./static.controller";

import {StaticService} from "./static.service";

import {STATIC_URL} from "./static.url";

import {FAQ} from "./faq.constant";



'use strict';
export default angular.module('app.static', [])
  .config(moduleConfig)
  .controller('StaticController', StaticController)
  .constant('STATIC_URL', STATIC_URL)
  .constant('FAQ', FAQ)
  .service('StaticService', StaticService)
  .name;

