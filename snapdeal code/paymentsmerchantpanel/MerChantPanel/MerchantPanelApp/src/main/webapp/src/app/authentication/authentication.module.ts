import {moduleConfig} from "./authentication.config";
import {LoginController} from "./login/login.controller";
import {LoginService} from "./login/login.service";
import {Constants} from "../app.constant.config";
import AuthConstants from "./authentication.url";
import authenticationMockRun from "./authentication.mockRun";
import {ForgotPasswordController} from "./forgotPassword/forgotPassword.controller";
import {SetPasswordController} from "./forgotPassword/setPassword.controller";
import {ForgotPasswordService} from "./forgotPassword/forgotPassword.service";
import {CompareTo} from "../shared/directives/compareto.directive";


'use strict';
export default angular.module('app.authentication', ['app.shared'])
    .constant('Auth_URL', AuthConstants.Auth_URL)
    .config(moduleConfig)
    .run(authenticationMockRun)
    .directive(CompareTo)
    .service('LoginService', LoginService)
    .service('ForgotPasswordService', ForgotPasswordService)
    .controller('LoginController', LoginController)
    .controller('ForgotPasswordController', ForgotPasswordController)
    .controller('SetPasswordController', SetPasswordController)
    .name;

