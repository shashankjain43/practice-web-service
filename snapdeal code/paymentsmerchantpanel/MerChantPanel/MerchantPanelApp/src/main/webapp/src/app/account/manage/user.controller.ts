import {IGlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {ICssStyleService} from "../../shared/services/cssStyle.service"
import {ACCOUNT_REQOBJECT} from "../account.requestObject"
import {APP_MESSAGE_CONSTANT} from "../../shared/constants/message.constant"
import {IManageService} from "./manage.interface";
import {APP_KEY} from "../../app.constant.key";
import {IPermissionService} from "../../shared/permission/permission.service";
import {IStorageService} from "../../shared/storage/storage.service";

'use strict';

/* @ngInject */
export class UserController {
    private GlobalOmnitureService:IGlobalOmnitureService;
    public ManageService:IManageService;
    public CssStyleService:ICssStyleService;
    public ACCOUNT_REQOBJECT:any;
    public APP_MESSAGE:any;
    public APP_KEY:any;
    public isLoading:Boolean = false;
    public $scope:angular.IScope;
    public errorMessage;
    public $rootScope:angular.IRootScopeService;
    public $filter:angular.IFilterService;
    public StorageService:IStorageService;
    public $timeout:angular.ITimeoutService;
    public PermissionService:IPermissionService;
    public permissionString:string;
    public user:any;
    public currentUserLoginName:any;
    public userInfo:any;
    public userCount:any;
    public oldLoginName:any;
    public successMessage:any;
    public successError:any;
    public action:any;
    public userObject:any;
    public allRoles:any;
    public allRole:any;
    public heading:any;
    public requiredMessage:string;
    public correctMessage:string;
    public emailPattern:any;
    public message:any;

    constructor(GlobalOmnitureService:IGlobalOmnitureService,
                ManageService:IManageService,
                CssStyleService:ICssStyleService,
                APP_KEY:any,
                $scope:angular.IScope,
                $filter:angular.IFilterService,
                StorageService:IStorageService,
                $rootScope:angular.IRootScopeService,
                PermissionService:IPermissionService,
                $timeout:angular.ITimeoutService,
                allRole:any,
                ACCOUNT_REQOBJECT:any) {
        this.GlobalOmnitureService = GlobalOmnitureService;
        this.ManageService = ManageService;
        this.PermissionService = PermissionService;
        this.CssStyleService = CssStyleService;
        this.APP_MESSAGE = APP_MESSAGE_CONSTANT.getMessageConstants;
        this.APP_KEY = APP_KEY.key;
        this.StorageService = StorageService;
        this.ACCOUNT_REQOBJECT = ACCOUNT_REQOBJECT.getAccountRequest;
        this.$scope = $scope;
        this.$timeout = $timeout;
        this.$filter = $filter;
        this.$rootScope = $rootScope;
        this.errorMessage = '';
        this.successMessage = '';
        this.permissionString = '';
        this.user = PermissionService.user;
        this.currentUserLoginName = this.user.userDTO.loginName;
        this.oldLoginName = '';
        this.permissionString = '';
        this.successError = undefined;
        this.allRole = allRole;
        this.heading = '';
        this.requiredMessage = '';
        this.correctMessage = '';
        this.emailPattern = '';
        this.message = '';
        this.init();
        var vm = this.initializeScope();
        this.$scope.$on('user-action', function (event, args) {
            vm.successError = undefined;
            vm.userInfo = args.data.userInfo;
            vm.userCount = args.data.userCount;
            vm.action = args.data.action;
            vm.userObject = vm.userInfo == undefined ? null : vm.userInfo.userObj;
            vm.allRoles = angular.copy(vm.allRole.data.data.roles);
            vm.heading = (vm.action == 'edit') ? 'Edit User' : 'Add User';
            if (vm.action == 'edit') {
                vm.removeCssClass();
                vm.mergePermissions();
                vm.successError = undefined;
            } else {
                vm.resetForm();
                vm.addcssClass();
            }
        });
    }

    public initializeScope() {
        return this;
    }

    public onBlur() {
        this.verifyUser();
    }

    public verifyUser() {
        var vm = this.initializeScope();
        if (vm.userObject.loginName != '' && vm.oldLoginName != vm.userObject.loginName) {
            vm.successError = undefined;
            vm.isLoading = true;
            var requestObject = '/' + vm.userObject.loginName;
            vm.ManageService.verifyUser(requestObject)
                .then(function (response) {
                    var responseData = response.data;
                    if (responseData.error == null) {
                        if (responseData.data.userPresent) {
                            vm.successError = 'failure';
                            vm.message = vm.APP_MESSAGE.USER_EXIST;
                        }
                    } else {
                        vm.successError = 'failure';
                        vm.message = responseData.error.errorMessage;
                    }
                    vm.isLoading = false;
                })
                .catch(function () {
                    vm.isLoading = false;
                });
            vm.oldLoginName = vm.userObject.loginName;
        }
    }

    public resetForm() {

        var vm = this.initializeScope();
        vm.successError = undefined;
        vm.userObject = angular.copy(null);
        vm.CssStyleService.clearValFromElement('#emailID');
        vm.CssStyleService.clearValFromElement('#userName');
        /*CssStyleService.clearValFromElement('#password');
         CssStyleService.clearValFromElement('#confirmPassowrd');
         vm.password = undefined;
         vm.confirmPassword = undefined;*/
        // vm.$scope.userForm.$setPristine();
    }


    public addcssClass() {
        var vm = this.initializeScope();
        vm.CssStyleService.addCssClass('#userName', 'empty');
        vm.CssStyleService.addCssClass('#emailID', 'empty');
    }

    public removeCssClass() {
        var vm = this.initializeScope();
        vm.CssStyleService.removeCssClass('#userName', 'empty');
        vm.CssStyleService.removeCssClass('#emailID', 'empty');
    }

    public mergePermissions() {
        var vm = this.initializeScope();
        var userRoles = vm.userObject.roleList;
        var vm = vm;
        angular.forEach(userRoles, function (roleValue) {
            angular.forEach(roleValue.permissions, function (permissionValue) {
                vm.setTrueForUser(roleValue.name, permissionValue.name);
            });
        });
    }

    public setTrueForUser(roleName, permissionName) {
        var vm = this.initializeScope();
        var roleObject = vm.$filter('filter')(vm.allRoles, {'name': roleName});
        if (roleObject.length < 1) {
            return;
        }
        var permissionObject = vm.$filter('filter')(roleObject[0].permissions, {'name': permissionName});
        if (permissionObject.length == 1) {
            permissionObject[0].enabled = true;
        }
    }

    public checkedClick(value) {
        var vm = this.initializeScope();
        if (value.name == 'MCNT_INIT_REFUND' && value.enabled) {
            vm.setValue('VIEW_TRANSACTION', value.enabled);
        }
        if (value.name == 'VIEW_TRANSACTION' && !value.enabled) {
            vm.setValue('MCNT_INIT_REFUND', value.enabled);
        }
    }

    public setValue(valueName, isenable) {
        var vm = this.initializeScope();
        angular.forEach(vm.allRoles, function (roleVal) {
            angular.forEach(roleVal.permissions, function (permissionValue) {
                if (permissionValue.name == valueName)
                    permissionValue.enabled = isenable;
                return;
            });

        });
    }

    public cancel() {
        var vm = this.initializeScope();
        vm.CssStyleService.removeCssClass('.add-user', 'show');
        vm.CssStyleService.removeDisplayElement('.adduser-bg');
    }

    public init() {
        var vm = this.initializeScope();
        vm.requiredMessage = vm.APP_MESSAGE.REQUIRED_FIELD;
        vm.correctMessage = vm.APP_MESSAGE.CORRECT_FIELD;
        vm.emailPattern = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    }

    public submit() {
        var vm = this.initializeScope();
        var requestObject = {};
        var error = '';
        var reqObjVal = [];
        vm.permissionString = '';
        reqObjVal.push(vm.userObject.userName);
        reqObjVal.push(vm.userObject.loginName);
        reqObjVal.push(vm.userObject.emailId);
        if (this.action == 'edit')
            reqObjVal.push(vm.userObject.userId);
        var roleList = angular.copy(vm.allRoles);
        angular.forEach(roleList, function (roleVal) {
            roleVal.permissions = vm.$filter('filter')(roleVal.permissions, {enabled: true});
            angular.forEach(roleVal.permissions, function (permissionValue) {
                if (permissionValue.enabled)
                    vm.permissionString += permissionValue.displayName + ', ';
            });
        });

        reqObjVal.push(roleList);

        if (vm.permissionString == '') {
            vm.successError = 'failure';
            vm.message = this.APP_MESSAGE.PERMISSION_REQUIRED;
        } else {
            vm.isLoading = true;
            if (vm.action == 'add') {
                for (var i = 0; i < vm.ACCOUNT_REQOBJECT.ADD_USER_OBJECT.length; i++) {
                    requestObject[vm.ACCOUNT_REQOBJECT.ADD_USER_OBJECT[i]] = reqObjVal[i];
                }
                vm.ManageService.addUsers(requestObject)
                    .then(function (responseData) {
                        var response = responseData.data;
                        if (response.error == null) {
                            vm.actionOnSuccess(vm.APP_MESSAGE.ADD_SUCCESSFULLY);
                            vm.isLoading = false;
                            vm.init();
                        } else {
                            vm.showError(response);
                        }
                        vm .setAddEditOmniture('add', error);
                    })
                    .catch(function (response) {
                        vm.showError(response);
                        vm.setAddEditOmniture('add', error);
                    });
            } else {
                for (i = 0; i < vm.ACCOUNT_REQOBJECT.EDIT_USER_OBJECT.length; i++) {
                    requestObject[vm.ACCOUNT_REQOBJECT.EDIT_USER_OBJECT[i]] = reqObjVal[i];
                }
                vm.ManageService.editUsers(requestObject)
                    .then(function (responseData) {
                        var response = responseData.data;
                        if (response.error == null) {
                            vm.actionOnSuccess(vm.APP_MESSAGE.EDIT_SUCCESSFULLY);
                            vm.isLoading = false;
                        } else
                            vm.showError(response);
                        vm.setAddEditOmniture('edit', error);
                    })
                    .catch(function (response) {
                        vm.showError(response);
                        vm.setAddEditOmniture('edit', error);
                    });
            }
        }
    }


    public setAddEditOmniture(action, error) {
        var vm = this.initializeScope();
        vm.CssStyleService.focusElement('#successDiv');
        vm.CssStyleService.focusElement('#erorDiv');

        if (action == 'add')
            this.GlobalOmnitureService.addUser(error, vm.userCount, vm.userObject.userName, vm.userObject.emailId, vm.permissionString.trim().slice(0, -1));
        else
            this.GlobalOmnitureService.editUser(error, vm.userCount, vm.userObject.userName, vm.userObject.emailId, vm.permissionString.trim().slice(0, -1));

    }

    public showError(response) {
        var vm = this.initializeScope();
        vm.isLoading = false;
        vm.successError = 'failure';
        vm.message = response.error.errorCode == 'ER-5130' ? vm.APP_MESSAGE.tryAgain : response.error.errorMessage;
        vm.CssStyleService.focusElement('#erorDiv');
    }


    public actionOnSuccess(message) {
        var vm = this.initializeScope();
        vm.successError = 'success';
        vm.message = message;
        vm.$timeout(function () {
            vm.$rootScope.$broadcast('user-add');
            vm.CssStyleService.removeCssClass('.add-user', 'show');
        }, 1000);
        vm.CssStyleService.focusElement('#erorDiv');
    }


}
