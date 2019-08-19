import ILocalStorageService = angular.local.storage.ILocalStorageService;
import {StorageEnum} from "./storage.enum";

export interface IStorageService {
  setKey(key: StorageEnum, value: any): void;
  getKey(key: StorageEnum): any;
}
'use strict';
/* @ngInject */
export class StorageService implements IStorageService{
  private localStorageService: ILocalStorageService;
  constructor(localStorageService:ILocalStorageService){
    this.localStorageService = localStorageService;
  }

  public setKey(key: StorageEnum, value: any) {
    var keyString = StorageEnum[key];
    this.localStorageService.set(keyString, value);
  }

  public getKey(key: StorageEnum):any {
    var keyString = StorageEnum[key];
    return this.localStorageService.get(keyString);
  }

}
