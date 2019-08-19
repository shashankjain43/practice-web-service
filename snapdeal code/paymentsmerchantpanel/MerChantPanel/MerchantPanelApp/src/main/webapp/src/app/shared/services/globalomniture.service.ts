import {OMNITURE_CONSTANT} from "../constants/omniture.constant";
import ILocationProvider = angular.ILocationProvider;
import {IPermissionService} from "../permission/permission.service";
'use strict';

export  interface IGlobalOmnitureService {
  setOmniture :(omniParams:Object)=>void;
  setLogin :(error:string)=>void;
  manageHome :(length:string)=>void;
  changePassword :(error:string)=>void;
  contactInfo :(email:string)=>void;
  bankInfo :(bankName:string)=>void;
  exportToExcel:(error:string)=>void;
  bulkRefundHome:(error?:string)=>void;
  bulkRefundUplaod:(error:string)=>void;
  bulkRefundDownload:(error:string)=>void;
  bulkRefundView:(error:string)=>void;
  downloadHistory:(error:string)=>void;
  sandbox:(url:string, key:string, error:string)=>void;
  production:(url:string, key:string, error:string)=>void;
  submitRefund:(amount:string, reason:string, cstId:string, prodId:string)=>void;
  refundHome:()=>void;
  forgotPassword:(email:string)=>void;
  editUser:(error:string, userCount:string, userName:string, emailId:string, permissionString:string)=>void;
  addUser:(error:string, userCount:string, userName:string, emailId:string, permissionString:string)=>void;
  viewTransaction:(filter:string, header:string, date:string)=>void;
  searchRefundTransaction:(selectedDrodownItem:string, searchValue:string, dateVal:string)=>void;
  searchTransaction:(selectedDrodownItem:string, searchValue:string, dateVal:string)=>void;
  businessInfo:(businessType:string, businessCategory:string, subCategory:string)=>void;
  contactUs :(contactInfo:Object)=>void;
  support :(selectedQuery:string)=>void;
  settlementReport :(dateRange:string)=>void;
  invoiceReport :(dateRange:string)=>void;
  onHome:()=>void;
  clickRegister:()=>void;
  clickRegisterSubmit:(error:string)=>void;
  clickOfflineRegister:()=>void;
  clickOnlineRegister:()=>void;
  clickProducts:()=>void;
  clickPhysicalProducts:()=>;
  clickOnlineProducts:()=>;
  loadOfflineProducts:()=>void;
  loadOnlineProducts:()=>void;
  clickPricing:()=>void;
  clickLearnMore:()=>void;
  loadPricing:()=>void;
  loadHelp:()=>void;
  clickHelp:()=>void;
  clickGetStarted:()=>void;
  banksubmit:(error:string)=>void;
  bankload:()=>void;
  basicInfoLoaded:()=>void;
  loadIntegrationKit:()=>void;
  clickIntegrationKit:()=>void;
  kycload:()=>void;
  kycSubmit:()=>void;
  clickLogin:()=>void;
  loginSuccess:()=>void;
  businessInfoSubmit:(error:string, secondEmail:string, businessType:string, businessCategory:string, subCategory:string)=>void;
}

/* @ngInject */
export class GlobalOmnitureService implements IGlobalOmnitureService {
  private $location:ng.ILocationService;
  private PermissionService:IPermissionService;
  public OMNITURE_CONSTANT;

  constructor($location:ng.ILocationService, PermissionService:IPermissionService) {
    this.$location = $location;
    this.PermissionService = PermissionService;
    this.OMNITURE_CONSTANT = OMNITURE_CONSTANT.getOmnitureConstant;
  }

  setOmniture(omniParams:Object) {
    if(this.$location.host().indexOf('merchant.freecharge.in') === -1){
      return;
    }
    var user = this.PermissionService.user;
    if (user != null) {
      var globalOmniture = {
        'eVar1': user.userDTO.userName,
        'eVar2': user.userDTO.emailId
      }
    }
    else {
      var globalOmniture = {
        'eVar1': 'nonloggedin',
        'eVar2': 'nonloggedin'
      }
    }
    try {
        var params = angular.extend(globalOmniture, omniParams);
        s.t(params);
      }catch(ex) {}
  };


/* before login omniture - no user authentication needed -- STARTS*/

  onHome() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.HOME
    };
    this.setOmniture(omniParams);
  };
  clickRegister() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.REGISTER
    };
    this.setOmniture(omniParams);
  };
  clickRegisterSubmit(error:string) {
    var omniParams =
    {
      eVar3: error,
      pageName: this.OMNITURE_CONSTANT.REGISTER_SUBMIT
    };
    this.setOmniture(omniParams);
  };
  clickOfflineRegister() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.OFFLINEPOP
    };
    this.setOmniture(omniParams);
  };
  clickOnlineRegister() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.ONLINEPOP
    };
    this.setOmniture(omniParams);
  };
  clickProducts() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.PRODUCTS
    };
    this.setOmniture(omniParams);
  };
  clickPhysicalProducts() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.OFFLINE_PRODUCTS_CLICK
    };
    this.setOmniture(omniParams);
  };
  clickOnlineProducts() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.ONLINE_PRODUCTS_CLICK
    };
    this.setOmniture(omniParams);
  };
  loadOnlineProducts() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.ONLINE_PRODUCTS
    };
    this.setOmniture(omniParams);
  };
  loadOfflineProducts() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.OFFLINE_PRODUCTS
    };
    this.setOmniture(omniParams);
  };
  clickPricing() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.PRICING
    };
    this.setOmniture(omniParams);
  };
  loadPricing() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.PRICING_LOAD
    };
    this.setOmniture(omniParams);
  }
  clickLearnMore(){
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.LEARN_MORE
    };
    this.setOmniture(omniParams);
  };
  loadHelp() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.HELP_LOAD
    };
    this.setOmniture(omniParams);
  }
  clickGetStarted(){
     var omniParams =
      {
        pageName: this.OMNITURE_CONSTANT.GET_STARTED
      };
      this.setOmniture(omniParams);
  };
  clickHelp() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.HELP
    };
    this.setOmniture(omniParams);
  };
  contactUs(contactInfo:Object){
    var omniParams =
    {
      eVar25: contactInfo,
      pageName: this.OMNITURE_CONSTANT.CONTACT_US
    };
    this.setOmniture(omniParams);
  };
/* before login omniture - no user authentication needed -- ENDS*/


/* Merchant onboarding omniture -- STARTS*/

  businessInfo(businessType:string, businessCategory:string, subCategory:string) {
    var omniParams =
    {
      eVar9: businessType,
      eVar10: businessCategory,
      eVar11: subCategory,
      pageName: this.OMNITURE_CONSTANT.BUSINESS_INFO
    };
    this.setOmniture(omniParams);
  };
  banksubmit(error:string) {
    var omniParams =
    {
      eVar3: error,
      pageName: this.OMNITURE_CONSTANT.BANK_DETAIL_SUBMIT
    };
    this.setOmniture(omniParams);
  };
  bankload() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.BANK_DETAIL_LOAD
    };
    this.setOmniture(omniParams);
  };
  kycload() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.KYC_LOAD
    };
    this.setOmniture(omniParams);
  };
  kycSubmit() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.KYC_SUBMIT
    };
    this.setOmniture(omniParams);
  };
  basicInfoLoaded() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.BASIC_INFO_LOAD
    };
    this.setOmniture(omniParams);
  };
  loadIntegrationKit () {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.INTEGRATIONKIT_LOAD
    };
    this.setOmniture(omniParams);
  };
  clickIntegrationKit () {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.INTEGRATIONKIT_CLICK
    };
    this.setOmniture(omniParams);
  };
  businessInfoSubmit(error:string, secondEmail:string, businessType:string, businessCategory:string, subCategory:string) {
    var omniParams =
    {
      eVar3: error,
      eVar8: secondEmail,
      eVar9: businessType,
      eVar10: businessCategory,
      eVar11: subCategory,
      pageName: this.OMNITURE_CONSTANT.BUSINESSINFO_SUBMIT
    };
  };
/* Merchant onboarding omniture -- ENDS*/


/*LOGIN omniture -- STARTS*/

  clickLogin() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.CLICK_LOGIN
    };
    this.setOmniture(omniParams);
  };
  loginSuccess() {
     var omniParams =
      {
        pageName: this.OMNITURE_CONSTANT.LOGIN_SUCCESS
      };
      this.setOmniture(omniParams);
  };
  setLogin(error:string) {
    var omniParams =
    {
      eVar3: error,
      pageName: this.OMNITURE_CONSTANT.LOGIN
    };
    this.setOmniture(omniParams);
  };
/*LOGIN omniture -- ENDS*/


/*Panel post login and onboard omniture -- STARTS*/

  manageHome(length:string) {
    var omniParams =
    {
      eVar13: length,
      pageName: this.OMNITURE_CONSTANT.MANAGE_HOME
    };
    this.setOmniture(omniParams);
  };

  changePassword(error:string) {
    var omniParams =
    {
      eVar3: error,
      pageName: this.OMNITURE_CONSTANT.CHANGE_PWD
    };
    this.setOmniture(omniParams);
  };

  contactInfo(email:string) {
    var omniParams =
    {
      eVar8: email,
      pageName: this.OMNITURE_CONSTANT.CONTACT_INFO
    };
    this.setOmniture(omniParams);
  };

  bankInfo(bankName:string) {
    var omniParams =
    {
      eVar12: bankName,
      pageName: this.OMNITURE_CONSTANT.BANK_INFO
    };
    this.setOmniture(omniParams);
  };
  exportToExcel(error:string) {
    var omniParams =
    {
      eVar3: error,
      pageName: this.OMNITURE_CONSTANT.EXPORT
    };
    this.setOmniture(omniParams);
  };

  bulkRefundHome(error?:string) {
    var omniParams =
    {
      eVar3: error,
      pageName: this.OMNITURE_CONSTANT.BULK_REFUND_HOME
    };
    this.setOmniture(omniParams);
  };

  bulkRefundUplaod(error:string) {
    var omniParams =
    {
      eVar3: error,
      pageName: this.OMNITURE_CONSTANT.BULK_REFUND_UPLOAD
    };
    this.setOmniture(omniParams);
  };

  bulkRefundDownload(error:string) {
    var omniParams =
    {
      eVar3: error,
      pageName: this.OMNITURE_CONSTANT.BULK_REFUND_DOWNLOAD
    };
    this.setOmniture(omniParams);
  };

  bulkRefundView(error:string) {
    var omniParams =
    {
      eVar3: error,
      pageName: this.OMNITURE_CONSTANT.BULK_REFUND_VIEW
    };
    this.setOmniture(omniParams);
  };

  downloadHistory(error:string) {
    var omniParams =
    {
      eVar3: error,
      pageName: this.OMNITURE_CONSTANT.DOWNLOAD_HISTORY
    };
    this.setOmniture(omniParams);
  };

  sandbox(url:string, key:string, error:string) {
    var omniParams =
    {
      eVar3: error,
      eVar25: url,
      eVar26: key,
      pageName: this.OMNITURE_CONSTANT.SANDBOX
    };
    this.setOmniture(omniParams);
  };

  production(url:string, key:string, error:string) {
    var omniParams =
    {
      eVar3: error,
      eVar25: url,
      eVar26: key,
      pageName: this.OMNITURE_CONSTANT.PRODUCTION
    };
    this.setOmniture(omniParams);
  };

  submitRefund(amount:string, reason:string, cstId:string, prodId:string) {
    var omniParams =
    {
      eVar20: amount,
      eVar21: reason,
      eVar22: cstId,
      eVar23: prodId,
      pageName: this.OMNITURE_CONSTANT.REFUND_SUBMIT
    };
    this.setOmniture(omniParams);
  };

  refundHome() {
    var omniParams =
    {
      pageName: this.OMNITURE_CONSTANT.REFUND_HOME
    };
    this.setOmniture(omniParams);
  };

  forgotPassword(email:string) {
    var omniParams =
    {
      eVar24: email,
      pageName: this.OMNITURE_CONSTANT.FORGOT_PASSWORD
    };
    this.setOmniture(omniParams);
  };

  editUser(error:string, userCount:string, userName:string, emailId:string, permissionString:string) {
    var omniParams =
    {
      eVar3: error,
      eVar13: userCount,
      eVar14: userName,
      eVar15: emailId,
      eVar16: permissionString,
      pageName: this.OMNITURE_CONSTANT.MANAGE_EDIT
    };
    this.setOmniture(omniParams);
  };

  addUser(error:string, userCount:string, userName:string, emailId:string, permissionString:string) {
    var omniParams =
    {
      eVar3: error,
      eVar13: userCount,
      eVar14: userName,
      eVar15: emailId,
      eVar16: permissionString,
      pageName: this.OMNITURE_CONSTANT.MANAGE_ADD
    };
    this.setOmniture(omniParams);
  };

  viewTransaction(filter:string, header:string, date:string) {
    var omniParams =
    {
      eVar4: filter,
      eVar5: date,
      eVar6: header,
      pageName: this.OMNITURE_CONSTANT.VIEW_TRANSACTION
    };
    this.setOmniture(omniParams);
  };

  searchRefundTransaction(selectedDrodownItem:string, searchValue:string, dateVal:string) {
    var omniParams =
    {
      eVar18: selectedDrodownItem,
      eVar19: searchValue,
      eVar5: dateVal,
      pageName: this.OMNITURE_CONSTANT.SEARCH_REFUND_TRANSACTION
    };
    this.setOmniture(omniParams);
  };

  searchTransaction(selectedDrodownItem:string, searchValue:string, dateVal:string) {
    var omniParams =
    {
      eVar18: selectedDrodownItem,
      eVar19: searchValue,
      eVar5: dateVal,
      pageName: this.OMNITURE_CONSTANT.SEARCH_TRANSACTION
    };
    this.setOmniture(omniParams);
  };

  support(selectedQuery:string){
    var omniParams =
    {
      eVar26: selectedQuery,
      pageName: this.OMNITURE_CONSTANT.HELP_SUPPORT
    };
    this.setOmniture(omniParams);
  };

  settlementReport(dateRange:string){
    var omniParams =
    {
      eVar5: dateRange,
      pageName: this.OMNITURE_CONSTANT.SETTLEMENT_REPORT
    };
    this.setOmniture(omniParams);
  };

  invoiceReport(dateRange:string){
    var omniParams =
    {
      eVar5: dateRange,
      pageName: this.OMNITURE_CONSTANT.INVOICE_REPORT
    };
    this.setOmniture(omniParams);
  };
/*Panel post login and onboard omniture -- ENDS*/


}
