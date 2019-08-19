import ICookiesService = angular.cookies.ICookieStoreService;
import {IPermissionService} from "../../../shared/permission/permission.service";
import {LoginResponseModel} from "../../../authentication/httpModels/UserResponseModel";
import {IGlobalOmnitureService} from "../../../shared/services/globalomniture.service";

/* @ngInject */
export class MenuController {
  MENU_CONSTANTS: any;
  $filter: ng.IFilterService;
  localStorageService: angular.local.storage.ILocalStorageService;
  $state: ng.ui.IStateService;
  PermissionService: IPermissionService;
  GlobalOmnitureService:IGlobalOmnitureService;
  username: string;
  emailId: string;
  constructor(PermissionService: IPermissionService, $state: ng.ui.IStateService, $filter: ng.IFilterService, localStorageService: angular.local.storage.ILocalStorageService,
              MENU_CONSTANTS: any,GlobalOmnitureService:IGlobalOmnitureService) {
    this.MENU_CONSTANTS = $filter('orderBy')(angular.copy(MENU_CONSTANTS()),'priority');
    this.$filter = $filter;
    this.localStorageService = localStorageService;
    this.$state = $state;
    this.GlobalOmnitureService=GlobalOmnitureService;
    this.PermissionService = PermissionService;
    var user = this.PermissionService.user;
    this.username = user.userDTO.userName;
    this.emailId = user.userDTO.emailId;
  }

  navigate(value)
  {
    if(value === "base.default.payment.transaction"){
      this.$state.go(value, {searchParam: null}, {reload: true});
      return;
    }
    if (value == 'base.default.static.content.contactus')
      this.GlobalOmnitureService.contactUs('menu');

      this.$state.go(value, {}, {reload: true});
  }

  accountClick() {

    var blockElement = angular.element('.login-main-box');
    blockElement.attr('display', 'none');
    blockElement.css('display', 'none');
    blockElement.css('overflow', 'hidden');
    var accountTabs =  this.$filter('filter')(this.PermissionService.accessibleStates,{category:'ACCOUNTS'});
    var defaultState = accountTabs[0].state;
    this.$state.go(defaultState, {}, {reload: true});
  }

  signOutClick() {
    this.PermissionService.user = <LoginResponseModel>[];
    this.PermissionService.modulesVM = [];
    this.PermissionService.accessibleStates = [];
    this.localStorageService.remove('user');
    this.localStorageService.remove('MODULES_VM');
    this.localStorageService.remove('accessibleStates');
    this.$state.go('authentication.login', {}, {reload: true});
    this.localStorageService.set('popUpShown',true);
  }
}
