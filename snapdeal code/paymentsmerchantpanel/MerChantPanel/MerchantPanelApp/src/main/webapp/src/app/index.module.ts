/// <reference path="../../.tmp/typings/tsd.d.ts" />

import { config } from './index.config';
import { mdThemingProvider } from './theme.config'
import { routerConfig } from './index.route';
import { runBlock } from './index.run';
import { stateRunBlock } from './state.run';
import { GithubContributor } from '../app/components/githubContributor/githubContributor.service';
import { acmeNavbar } from '../app/components/navbar/navbar.directive';
import { acmeMalarkey } from '../app/components/malarkey/malarkey.directive';

import '../app/shared/shared.module' ;
import '../app/base/base.module';
import '../app/authentication/authentication.module';
import '../app/downloadHistory/history.module';
import '../app/payment/payment.module.ts';
import '../app/integrationkey/integrationkey.module';
import '../app/reports/reports.module';
import '../app/static/static.module';
import '../app/account/account.module';
import '../app/merchantOnboard/mob.module';
import '../app/qrcode/qrcode.module';
import '../app/authentication/setPassword/setFirstPasswordService.ts';
import '../app/authentication/setPassword/setFirstPassword.controller.ts';

import {Constants} from "./app.constant.config";
import {APP_KEY} from "./app.constant.key";
import {httpConfig} from "./config.http";
declare var moment:moment.MomentStatic;

module app {
  'use strict';
  var url = window.location.href;
  var dev_environment = url.indexOf('localhost') > -1 ? true : false;
  var mockRun = dev_environment && false;
  if (dev_environment && mockRun) {
    angular.module('app',
      ['ngMockE2E', 'ui.router','ui.router.metatags', 'ngclipboard', 'ngFileUpload', 'LocalStorageModule', 'ngMessages', 'ngCookies', 'ngMaterial', 'material.svgAssetsCache', 'JDatePicker',
        'app.authentication', 'app.shared','app.base','app.history', 'app.payment','app.integrationkey' , 'app.reports' , 'app.account' , 'app.static', 'app.mob',
        'app.qrcode'
      ]);
  } else {
    angular
      .module('app', [
        'ui.router','ui.router.metatags', 'ngclipboard', 'ngFileUpload', 'LocalStorageModule', 'ngMessages', 'ngCookies', 'ngMaterial', 'material.svgAssetsCache', 'JDatePicker',
        'app.authentication', 'app.shared','app.base','app.history', 'app.payment','app.integrationkey' , 'app.reports' , 'app.account' , 'app.static', 'app.mob',
        'app.qrcode'
      ]);
  }
function runBlock($rootScope, MetaTags) {
    $rootScope.MetaTags = MetaTags;
}

function configure(UIRouterMetatagsProvider) {
    UIRouterMetatagsProvider
        .setTitlePrefix('')
        .setTitleSuffix('')
        .setDefaultTitle('Merchant Panel')
        .setDefaultDescription('Freecharge Merchant Panel')
        .setDefaultKeywords('Freecharge Merchant Panel')
        .setStaticProperties({
                'og:site_name': 'https://merchant.paywithfreecharge.com/'
            })
        .setOGURL(true);
}

  angular.module('app')
    .config(routerConfig)
    .config(httpConfig)
    .config(mdThemingProvider)
    .config(['UIRouterMetatagsProvider', configure])
    .run(runBlock)
    .run(stateRunBlock)
    .run(['$rootScope', 'MetaTags', runBlock])
    .constant('API_CONFIG', Constants.API_CONFIG)
    .constant('APP_KEY', APP_KEY);

}
