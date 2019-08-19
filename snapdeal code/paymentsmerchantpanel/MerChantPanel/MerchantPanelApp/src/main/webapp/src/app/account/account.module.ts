import {moduleConfig} from "./account.config";

import {AccountController} from "./account.controller";
import {ProfileController} from "./profile/profile.controller";
import {PasswordController} from "./password/password.controller";
import {ManageController} from "./manage/manage.controller";
import {UserController} from "./manage/user.controller";

import {PasswordService} from "./password/password.service";
import {AccountService} from "./account.service";
import {ManageService} from "./manage/manage.service";
import {AccountUtilService} from "./account.util"

import {APP_ACCOUNT_URL} from "./account.url";
import {ACCOUNT_REQOBJECT} from "./account.requestObject";
import {OMNITURE_CONSTANT} from "../shared/constants/omniture.constant";
import {APP_MESSAGE_CONSTANT} from "../shared/constants/message.constant";
import {accountMockRun} from "./account.mockRun";



'use strict';
export default angular.module('app.account', [])
  .config(moduleConfig)
  .controller('AccountController', AccountController)
  .controller('PasswordController', PasswordController)
  .controller('ManageController', ManageController)
  .controller('UserController', UserController)
  .controller('ProfileController', ProfileController)
  .constant('APP_ACCOUNT_URL', APP_ACCOUNT_URL)
  .constant('ACCOUNT_REQOBJECT',ACCOUNT_REQOBJECT)
  .constant('OMNITURE_CONSTANT', OMNITURE_CONSTANT)
  .constant('APP_MESSAGE_CONSTANT', APP_MESSAGE_CONSTANT)
  .service('AccountService', AccountService)
  .service('PasswordService',PasswordService)
  .service('ManageService', ManageService)
  .service('AccountUtilService', AccountUtilService)
  .run(accountMockRun)
  .name;

