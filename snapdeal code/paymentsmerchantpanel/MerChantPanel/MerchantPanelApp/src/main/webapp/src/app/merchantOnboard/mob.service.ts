import {HttpService} from "../shared/services/http.service";
import {MOB_URL} from "./mob.url";
import {IHttpService} from "../shared/services/http.interface";

"use strict";
/* @ngInject */
export class MobService implements IMobService {

  public HttpService:IHttpService;
  public MOB_URL:MOB_URL;

  constructor(HttpService:IHttpService, MOB_URL:MOB_URL) {
    this.HttpService = HttpService;
    this.MOB_URL = MOB_URL.getMobUrl;
  }

  public getBusinessTypes(mode) {
    var url = this.MOB_URL.GET_BSNSSDATA.URL + "?type=businesstype&integrationMode="+mode+"&parent=all";
    return this.HttpService.request(this.MOB_URL.GET_BSNSSDATA.METHOD, url);
  }

  public getBusinessCategories(mode) {
    var url = this.MOB_URL.GET_BSNSSDATA.URL + "?type=category&integrationMode="+mode+"&parent=all";
    return this.HttpService.request(this.MOB_URL.GET_BSNSSDATA.METHOD, url);
  }

  public getBusinessSubCategories(e,mode) {
    var url = this.MOB_URL.GET_BSNSSDATA.URL + "?type=subcategory&integrationMode="+mode+"&parent="+e;
    return this.HttpService.request(this.MOB_URL.GET_BSNSSDATA.METHOD, url);
  }

  public mobSignUp(mobdata) {
    var data = {
      'businessInformationDTO':mobdata
    };
    var url = this.MOB_URL.MOB_SIGNUP.URL;
    return this.HttpService.request(this.MOB_URL.MOB_SIGNUP.METHOD, url, mobdata);
  }

  public getKycDocs(businessType) {
    var url = this.MOB_URL.GET_KYC_DOCS.URL + "?businessType="+businessType;
    return this.HttpService.request(this.MOB_URL.GET_KYC_DOCS.METHOD, url);
  }

  public getUploadedKycDocs() {
    var url = this.MOB_URL.GET_UPLOADED_KYC_DOCS.URL;
    return this.HttpService.request(this.MOB_URL.GET_UPLOADED_KYC_DOCS.METHOD, url);
  }
}
