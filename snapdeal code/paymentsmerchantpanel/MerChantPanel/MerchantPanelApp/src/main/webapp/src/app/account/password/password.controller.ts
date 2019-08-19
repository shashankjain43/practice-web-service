import {GlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {IGlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {IPasswordService} from "./password.interface";
import {ICssStyleService} from "../../shared/services/cssStyle.service"
import {ACCOUNT_REQOBJECT} from "../account.requestObject"
import {APP_MESSAGE_CONSTANT} from "../../shared/constants/message.constant"
import {OMNITURE_CONSTANT} from "../../shared/constants/omniture.constant"


"use strict";

/* @ngInject */
export class PasswordController {
    private GlobalOmnitureService:IGlobalOmnitureService;
    public PasswordService:IPasswordService;
    public CssStyleService:ICssStyleService;
    public ACCOUNT_REQUESTOBJECT:any;
    public APP_MESSAGE:any;
    public isLoading:Boolean = false;
    public $scope:angular.IScope;
    public errorMessage;
    public requiredMessage;
    public correctMessage;
    public pwdLengthRange;
    public nomatchPassword;
    public oldPassword;
    public newPassword;
    public confirmPassword;
    public successMessage;
    public passwordChanged;
    public ACCOUNT_REQOBJECT:any;
    public passwordForm:any;

    constructor(GlobalOmnitureService:IGlobalOmnitureService,
                PasswordService:IPasswordService,
                CssStyleService:ICssStyleService,
                ACCOUNT_REQOBJECT:any,
                $scope:angular.IScope) {
        this.GlobalOmnitureService = GlobalOmnitureService;
        this.PasswordService = PasswordService;
        this.CssStyleService = CssStyleService;
        this.ACCOUNT_REQUESTOBJECT = ACCOUNT_REQOBJECT.getAccountRequest;
        this.APP_MESSAGE = APP_MESSAGE_CONSTANT.getMessageConstants;
        this.requiredMessage = this.APP_MESSAGE.REQUIRED_FIELD;
        this.correctMessage = this.APP_MESSAGE.CORRECT_FIELD;
        this.pwdLengthRange = this.APP_MESSAGE.PASSWSORD_RANGE;
        this.nomatchPassword = this.APP_MESSAGE.NO_MATCH_PASSWORD;
        this.passwordChanged = this.APP_MESSAGE.PASSWORD_CHANGED;
        this.errorMessage = '';
        this.oldPassword = '';
        this.newPassword = '';
        this.confirmPassword = '';
        this.successMessage = '';
        this.$scope = $scope;
        var vm = this;
    }

    public submit(form) {
        this.errorMessage = '';
        this.successMessage = '';
        this.isLoading = true;
        var reqObjVal = [];
        var requestObject = {};
        reqObjVal.push(this.oldPassword);
        reqObjVal.push(this.newPassword);
        for (var i = 0; i < this.ACCOUNT_REQUESTOBJECT.PASSWORD_OBJECT.length; i++) {
            requestObject[this.ACCOUNT_REQUESTOBJECT.PASSWORD_OBJECT[i]] = reqObjVal[i];
        }
        var vm = this;
        var css = this.CssStyleService;
        this.PasswordService.changePassword(requestObject)
            .then(function (response) {
                var responseData = response.success;
                vm.successMessage = vm.passwordChanged;
                vm.successMessage = vm.passwordChanged;
                /*resetform*/
                css.clearValFromElement('#oldPassword');
                css.clearValFromElement('#newPassword');
                css.clearValFromElement('#confirmPassword');
                form.$setPristine();
            }).catch(function (responseData) {
            vm.errorMessage = responseData.errorMessage;
        }).finally(function () {
            vm.isLoading = false;
        });
        this.GlobalOmnitureService.changePassword(this.errorMessage);
    }
}
