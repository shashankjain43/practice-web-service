
import {IStorageService} from "../storage/storage.service";
import {StorageEnum} from "../storage/storage.enum";
import {UserResponseModel} from "../../authentication/httpModels/UserResponseModel";
import {LoginResponseModel} from "../../authentication/httpModels/UserResponseModel";
import {IAccessibleState} from "./accessibleState.interface";
export interface IPermissionService {
  isAllowed(string):boolean;
  generateAccessibleStates(): Object[];
  initialize(Object): void;
  accessibleStates:Object[];
  modulesVM:Object[]
  user: LoginResponseModel;
}
'use strict';
/* @ngInject */
export class PermissionService implements IPermissionService {
  public user:LoginResponseModel;
  public modulesVM:Object[];
  public accessibleStates:Object[];
  private StorageService:IStorageService;
  private MENU_CONSTANTS;
  private ACCESS_CONSTANTS;
  private $filter;
  private userLoggedInPermissions;

  constructor(StorageService:IStorageService, ACCESS_CONSTANT, MENU_CONSTANTS, $filter) {
    this.StorageService = StorageService;
    this.MENU_CONSTANTS = MENU_CONSTANTS();
    this.$filter = $filter;
    this.ACCESS_CONSTANTS = ACCESS_CONSTANT;

    if(this.StorageService.getKey(StorageEnum.user)){
          this.user = this.StorageService.getKey(StorageEnum.user);
          this.modulesVM =  this.StorageService.getKey(StorageEnum.modulesVM);
          this.accessibleStates = this.StorageService.getKey(StorageEnum.accessibleStates);
          this.userLoggedInPermissions = this.StorageService.getKey(StorageEnum.user).userDTO.roleList;
    }
  }

  public initialize(loginResponse){
    this.user = loginResponse;
    this.userLoggedInPermissions = loginResponse.userDTO.roleList;
    this.StorageService.setKey(StorageEnum.accessibleStates, this.generateAccessibleStates());
  }

  public generateAccessibleStates(): Object[] {
    var accessibleStates = [];
    var categories = this.MENU_CONSTANTS;
    for (var i = 0; i < categories.length; i++) {
      var permissions = categories[i].permissions;
      var subCategory = permissions.concat(categories[i].defaultTabs);
      for (var j = 0; j < subCategory.length; j++) {
        if (this.permissionIsAllowed(subCategory[j].serverKey, false)) {
          var accessibleState: IAccessibleState = {};
            accessibleState.priority = subCategory[j].priority;
            accessibleState.state = subCategory[j].state;
            accessibleState.category = categories[i].name;
            accessibleState.categoryPriority = categories[i].priority;

          accessibleStates.push(accessibleState);
        }
      }
    }
    this.accessibleStates = accessibleStates;
    //this.StorageService.setKey(StorageEnum.accessibleStates, accessibleStates);
    return accessibleStates;
  }
  public isAllowed(permissionName) {
    var key = this.ACCESS_CONSTANTS().PERMISSIONS[permissionName].SERVER_KEY;
    var isCategory = this.ACCESS_CONSTANTS().PERMISSIONS[permissionName].IS_CATEGORY;
    return this.permissionIsAllowed(key, isCategory);
  }
  permissionIsAllowed(permissionName, isCategory) {
    var isExist = this.$filter('filter')(this.userLoggedInPermissions, permissionName);
    if (isExist.length == 0) {
      return false;
    }
    if (isCategory) {
      return isExist[0].permissions.length > 0;
    }
    return isExist.length > 0;
  }
}

