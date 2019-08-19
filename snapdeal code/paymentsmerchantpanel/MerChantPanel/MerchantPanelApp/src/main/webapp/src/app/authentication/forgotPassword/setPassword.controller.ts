import {IForgotPasswordService} from "./forgotPassword.service";
import {APP_MESSAGE_CONSTANT} from "../../shared/constants/message.constant";
export class SetPasswordController {
    private $scope:ng.IScope;
    private $timeout:ng.ITimeoutService;
    private $state:ng.ui.IStateService;
    private  ForgotPasswordService:IForgotPasswordService;
    public  APP_MESSAGE;
    public confirmPwd:string;
    public newPwd:string;
    public successMessage:string;
    public errorMessage:string;
    public requiredMessage:string;
    public pwdLengthRange:string;
    public nomatchPassword:string;
    public otpId:string;
    public  otpCode:string;
    public isLoading:Boolean;

  /* @ngInject */
  constructor($scope:ng.IScope, $timeout:ng.ITimeoutService, $state:ng.ui.IStateService, ForgotPasswordService:IForgotPasswordService) {
        this.$scope = $scope;
        this.$timeout = $timeout;
        this.$state = $state;
        this.ForgotPasswordService = ForgotPasswordService;
        this.APP_MESSAGE = APP_MESSAGE_CONSTANT.getMessageConstants;
        var vm = this;
        vm.otpId = '';
        vm.confirmPwd = '';
        vm.newPwd = '';
        vm.successMessage = '';
        vm.errorMessage = '';
        vm.requiredMessage = this.APP_MESSAGE.REQUIRED_FIELD;
        vm.pwdLengthRange = this.APP_MESSAGE.PASSWSORD_RANGE;
        vm.nomatchPassword = this.APP_MESSAGE.NO_MATCH_PASSWORD;
    }

    public setPassword() {
        var vm = this;
        vm.successMessage = '';
        vm.errorMessage = '';
        this.$scope.vm.isLoading = true;
        vm.otpId = this.$scope.vm.otpId == null ? this.otpId : this.$scope.vm.otpId;
        var requestObject = {
            "otpId": vm.otpId,
            "otp": vm.otpCode,
            "password": vm.confirmPwd
        };
        this.ForgotPasswordService.verifyOTP(requestObject)
            .then(function (response) {
                if (response.data.data != null) {
                    vm.successMessage = response.data.data.message;
                    vm.$timeout(function () {
                        vm.$state.go('authentication.login');
                    }, 1500);
                }
                else
                    vm.errorMessage = response.data.error.errorMessage;
                //show message
                vm.$scope.vm.isLoading = false;
            });
    }


    public resendOtpClick() {


        this.successMessage = '';
        this.errorMessage = '';
        var _this = this;
        _this.$scope.vm.isLoading = true;
        _this.otpId = _this.$scope.vm.otpId == null ? _this.otpId : _this.$scope.vm.otpId;
        var requestObject = {
            "otpId": _this.otpId
        };
        _this.ForgotPasswordService.resendOTP(requestObject)
            .then(function (response) {
                if (response.data.data != null) {
                    _this.otpId = response.data.data.otpId;
                    _this.$scope.vm.otpId = null;
                }
                else
                    _this.errorMessage = response.data.error.errorMessage;
                //show message
                _this.$scope.vm.isLoading = false;
            });
    }
}
