import {GlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {IGlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {IManageService} from "./manage.interface";
import {ICssStyleService} from "../../shared/services/cssStyle.service";
import {APP_MESSAGE_CONSTANT} from "../../shared/constants/message.constant";
import {APP_KEY} from "../../app.constant.key";


"use strict";

/* @ngInject */
export class ManageController {
    private GlobalOmnitureService:IGlobalOmnitureService;
    public ManageService:IManageService;
    public CssStyleService:ICssStyleService;
    public APP_MESSAGE:any;
    public isLoading:Boolean = false;
    public $scope:angular.IScope;
    public errorMessage;
    public allUsers:any;
    public APP_KEY:any;
    public permissionUserJson:any;
    public permissionString:string;
    public usersObj:any;
    public userSelect:any;
    public successMessage;
    public $rootScope:angular.IRootScopeService;
    public userCount:any;

    constructor(GlobalOmnitureService:IGlobalOmnitureService, ManageService:IManageService, CssStyleService:ICssStyleService, APP_KEY:any,
                allUsers:any, $scope:angular.IScope, $rootScope:angular.IRootScopeService) {
        this.GlobalOmnitureService = GlobalOmnitureService;
        this.ManageService = ManageService;
        this.CssStyleService = CssStyleService;
        this.APP_MESSAGE = APP_MESSAGE_CONSTANT.getMessageConstants;
        this.errorMessage = '';
        this.successMessage = '';
        this.$scope = $scope;
        this.allUsers = allUsers.data;
        this.APP_KEY = APP_KEY.getKeys;
        this.permissionUserJson = [];
        this.permissionString = '';
        this.usersObj = null;
        this.$rootScope = $rootScope;
        this.userSelect = undefined;
        this.userCount = 0;

        this.init();
    }

    public init() {
        var vm=this;
        vm.$scope.$on('keydown', function (event, keyEvent) {
            if (keyEvent.keyCode == vm.APP_KEY.ESC) {
                vm.CssStyleService.removeCssClass('.add-user', 'show');
                vm.CssStyleService.removeDisplayElement('.adduser-bg');
            }
        });

        vm.$scope.$on('user-add', function () {
            vm.CssStyleService.removeDisplayElement('.adduser-bg');
            vm.ManageService.getUsersOfMerchant().then(function (response) {
                var responseData = response.data;
                vm.usersObj = responseData.data.users;
                vm.getUserPermissions();
                vm.userSelect = undefined;
            });
        });
        vm.$scope.$watch(() => vm.userSelect,
            (oldValue:string, newValue:string) => {
                if (oldValue !== newValue) {
                    if (vm.userSelect != '')
                        vm.CssStyleService.removeCssClass('.adduser', 'disabled');
                }
            });

      if(this.allUsers.data != null){
        this.usersObj = this.allUsers.data.users;
      }
      if (this.usersObj != null) {
          this.GlobalOmnitureService.manageHome(this.usersObj.length);
          this.getUserPermissions();
          this.userSelect = undefined;
      }
      if (this.allUsers.error !== null) {
          this.errorMessage = this.allUsers.error.errorMessage;
      }
    }

    public addUserClick() {
        this.CssStyleService.addDisplayElement('.adduser-bg');
        var vm = this;
        this.$rootScope.$broadcast('user-action',
            {
                data: {
                    'action': 'add',
                    userCount: vm.usersObj == null ? 0 : vm.usersObj.length
                }
            });

    }


    public editUserClick() {
        if (this.userSelect != undefined) {
            this.CssStyleService.addDisplayElement('.adduser-bg');
            this.CssStyleService.addCssClass('#editButton', 'adduser');
            this.$rootScope.$broadcast('user-action', {
                data: {
                    'userInfo': angular.fromJson(this.userSelect),
                    'action': 'edit',
                    'userCount': this.usersObj.length
                }
            });
        } else {
            this.CssStyleService.removeCssClass('#editButton', 'adduser');
        }
    }

    public getUserPermissions() {
        this.permissionUserJson = [];
        if (this.usersObj != null) {
            for (var i = 0; i < this.usersObj.length; i++) {
                this.getPermissions(this.usersObj[i]);
            }
            this.userSelect = angular.toJson(this.permissionUserJson[0]);
        }
        else
            this.CssStyleService.removeCssClass('#editButton', 'adduser');
    }

    public getPermissions(users) {
        var roleLists = users.roleList;
        this.permissionString = '';
        var vm = this;
        angular.forEach(roleLists, function (roleValue) {
            //get permissions string and object
            angular.forEach(roleValue.permissions, function (permissionValue) {
                vm.permissionString += permissionValue.displayName + ', ';

            });
        });
        this.permissionUserJson.push(
            {
                'userName': users.loginName,
                'userObj': users,
                'permissionString': this.permissionString.trim().slice(0, -1)
            });
    }

}
