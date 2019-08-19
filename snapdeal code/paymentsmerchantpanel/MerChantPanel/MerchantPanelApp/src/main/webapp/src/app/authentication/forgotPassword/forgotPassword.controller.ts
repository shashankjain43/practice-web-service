import {IForgotPasswordService} from "./forgotPassword.service";
import {APP_MESSAGE_CONSTANT} from "../../shared/constants/message.constant";
"use strict";
export class ForgotPasswordController {
    private ForgotPasswordService:IForgotPasswordService;
    private APP_MESSAGE;
    public errorMessage:string;
    public requiredMessage:string;
    public correctMessage:string;
    public isLoading:Boolean;
    public email:string;
    public otpId:string;

  /* @ngInject */
  constructor(ForgotPasswordService:IForgotPasswordService, $location) {
        this.ForgotPasswordService = ForgotPasswordService;
        this.APP_MESSAGE = APP_MESSAGE_CONSTANT.getMessageConstants;
        var vm = this;
        vm.errorMessage = '';
        vm.otpId = '';
        vm.requiredMessage = this.APP_MESSAGE.REQUIRED_FIELD;
        vm.correctMessage = this.APP_MESSAGE.CORRECT_FIELD;
    }

    public forgotPassword() {
        var vm = this;
        vm.errorMessage = '';
        vm.isLoading = true;
        var requestObject = {
            'loginName': vm.email
        };
        this.ForgotPasswordService.forgotPassword(requestObject)
            .then(function (response) {
                if (response.data.data != null)
                    vm.otpId = response.data.data.otpId;
                else
                    vm.errorMessage = response.data.error.errorMessage;
                vm.isLoading = false;
            });
    }

}
