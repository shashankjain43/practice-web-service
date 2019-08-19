import {GlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {IGlobalOmnitureService} from "../../shared/services/globalomniture.service";

"use strict";

/* @ngInject */
export class ProfileController {
  private GlobalOmnitureService:IGlobalOmnitureService;
  public  isLoading:Boolean = false;
  public errorMessage;
  public profileInfo:any;
  public bankInfo:any;
  public businessInfo:any;

  constructor(GlobalOmnitureService:IGlobalOmnitureService, profileInfo:any) {
    this.GlobalOmnitureService = GlobalOmnitureService;
    this.profileInfo = profileInfo;
    this.bankInfo;
    this.businessInfo;
    var vm = this;
    this.init();
  }

  public init() {
    if (this.profileInfo.value.data == null) {
      this.errorMessage = this.profileInfo.value.error.errorMessage;
      return;
    }
    this.bankInfo = this.profileInfo.value.data.bankInfo;
    this.businessInfo = this.profileInfo.value.data.businessInfo;
    this.contactTab();
  }

  public contactTab() {
    if (this.businessInfo)
      this.GlobalOmnitureService.contactInfo(this.businessInfo.secondaryEmail);
  }

  public bankInfoTab() {
    if (this.bankInfo)
      this.GlobalOmnitureService.bankInfo(this.bankInfo.bankName);

  }

  public businessTab() {
    if (this.businessInfo)
      this.GlobalOmnitureService.businessInfo(this.businessInfo.businessType, this.businessInfo.businessCategory, this.businessInfo.subCategory);
  }
}

