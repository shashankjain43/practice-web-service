import {IMobService} from "./mob.interface";
import {IGlobalOmnitureService} from "../shared/services/globalomniture.service";
import {ISandboxService} from "../integrationkey/sandbox/sandbox.interface";
import {SandboxService} from "../integrationkey/sandbox/sandbox.service";
import {APP_MESSAGE_CONSTANT} from "../shared/constants/message.constant";
import {IPermissionService} from "../shared/permission/permission.service";
import {StorageEnum} from "../shared/storage/storage.enum";
import {StorageService} from "../shared/storage/storage.service";
import {IStorageService} from "../shared/storage/storage.service";
import {IfileUpload} from "../shared/services/fileuploader.interface";
import {fileUpload} from "../shared/services/fileuploader.service";
import {MOB_URL} from "./mob.url";


"use strict";
/* @ngInject */
export class mobController {
    public $scope: angular.IScope;
    public $window: angular.IWindowService;
    public $mdDialog: angular.material.IDialogService;
    public MobService: IMobService;
    public MOB_URL:MOB_URL;
    public bTypes:any;
    public bCats:any;
    public bSubCats:any;
    public mobForm:any;
    private GlobalOmnitureService:IGlobalOmnitureService;
    public SandboxService:ISandboxService;
    public PermissionService:IPermissionService;
    public fileUpload:IfileUpload;
    public APP_MESSAGES;
    public errorMessage;
    public sandData:any;
    public sandboxData:any;
    public profileInfo: any;
    private StorageService:IStorageService;
    public merchantStatus:any;
    public basicInfo:any;
    public kycInfo:any;
    public bankInfo:any;
    public bankSubmitInfo:any;
    public isLoading:boolean;
    public errorbank:any;
    public successbank:any;
    public errormob:any;
    public successmob:any;
    public successMessage:any;
    public user:any;
    public merchantEmail:any;
    public selectedIndex;
    public max;
    public bank;
    public testDate;
    public kycDocList;
    public $filter:angular.IFilterService;
    public userKycDocList;
    public integrationMode;
    public errorKyc;
    public errorKycMsg;
    public $location:angular.ILocationService;

    constructor($scope: angular.IScope,$filter:angular.IFilterService, $mdDialog: angular.material.IDialogService, MobService:IMobService, $window: angular.IWindowService,
      GlobalOmnitureService:IGlobalOmnitureService,StorageService:IStorageService, APP_MESSAGE:APP_MESSAGE_CONSTANT,fileUpload:IfileUpload,
      SandboxService: ISandboxService,PermissionService:IPermissionService,$location:angular.ILocationService, profileInfo:any, sandboxData:any) {
        this.$scope = $scope;
        this.$mdDialog = $mdDialog;
        this.$window = $window;
        this.$filter = $filter;
        this.$location = $location;
        this.MobService = MobService;
        this.bTypes;
        this.bCats;
        this.bSubCats;
        this.profileInfo = profileInfo;
        this.fileUpload = fileUpload;
        this.MOB_URL=MOB_URL.getMobUrl;
        if(this.profileInfo.data.data.businessInfo) {
          this.mobForm = profileInfo.data.data.businessInfo;
        }
        else {
          this.mobForm = {};
        }
        if(this.profileInfo.data.data.bankInfo) {
          this.bank = profileInfo.data.data.bankInfo;
          this.bankSubmitInfo = true;
        }
        else {
          this.bank = {};
          this.bankSubmitInfo = false;
        }

        this.StorageService = StorageService;
        this.isLoading = false;
        this.errorbank = false;
        this.errormob = false;
        this.successbank = false;
        this.successmob = false;
        this.errorKyc = false;
        this.errorKycMsg;
        this.successMessage;
        this.businessTypes();
        this.businessCateogory();
        this.GlobalOmnitureService = GlobalOmnitureService;
        this.APP_MESSAGES = APP_MESSAGE_CONSTANT.getMessageConstants;
        this.merchantStatus = this.StorageService.getKey(StorageEnum.userStatus);
        this.basicInfo = this.StorageService.getKey(StorageEnum.userStatus)['BASIC_INFO'];
        this.bankInfo = this.StorageService.getKey(StorageEnum.userStatus)['BANK_INFO'];
        this.kycInfo = this.StorageService.getKey(StorageEnum.userStatus)['KYC'];
        this.integrationMode = this.$filter('lowercase')(this.StorageService.getKey(StorageEnum.user)['userDTO']['integrationMode']);
        this.user = PermissionService.user;
        this.merchantEmail = this.user.userDTO.emailId;
        
        this.max = 3;
        this.testDate = null;
        this.kycDocList;
        this.userKycDocList;
        
        var vm = this;
        vm.errorMessage=vm.APP_MESSAGES.EMPTY_INTEGRATION_KEY+'sandbox';
          function init()
          {
              var sandData = sandboxData.data;
              vm.sandboxData = sandData;
              if(sandData.data !== null) {
                  var url = sandData.data.url;
                  var key = sandData.data.key;
              }
              if(sandData.error !== null)
                  vm.errorMessage = sandData.error.errorMessage;
              GlobalOmnitureService.sandbox(url, key, sandData.error);

              /* set current tab */
              if(vm.integrationMode == 'online') {
                if(!vm.basicInfo) 
                  vm.selectedIndex = 0;
                if(vm.basicInfo && !vm.bankSubmitInfo)
                  vm.selectedIndex = 2;
                if(vm.basicInfo && vm.bankSubmitInfo) 
                  {
                    vm.selectedIndex = 3;
                    vm.getKycDocs(vm.mobForm.businessType);
                  }
              }
              if(vm.integrationMode == 'offline') {
                if(!vm.basicInfo) 
                  vm.selectedIndex = 0;
                if(vm.basicInfo && vm.bankSubmitInfo) 
                  {
                    vm.selectedIndex = 2;
                    vm.getKycDocs(vm.mobForm.businessType);
                  }
              }

              if(vm.selectedIndex == 0)
                vm.GlobalOmnitureService.basicInfoLoaded();


          }
          init();
    }

    public businessTypes () {
      var vm = this;
      this.MobService.getBusinessTypes().then(function(response){
        vm.bTypes = response.data.data.merchantUIData;
      });
    }

    public businessCateogory () {
      var vm = this;
      this.MobService.getBusinessCategories().then(function(response){
        vm.bCats = response.data.data.merchantUIData;
      });
    }

    public businessSubCateogory (e) {
      var vm = this;

      this.MobService.getBusinessSubCategories(e).then(function(response){
        vm.bSubCats = response.data.data.merchantUIData;
        vm.mobForm.subCategory = '';
      });
    }

    public submitBankForm() {
      var vm =this;
      vm.successbank = false;
      vm.errorbank = false;
      this.isLoading = true;
      this.errorMessage = '';
      var data = {
        'businessInformationDTO' : this.mobForm,
        'bankAccountDetailsDTO' : this.bank
      }
      this.MobService.mobSignUp(data).then(function(response){
        if(response.data.error==null)
        {
            vm.successbank = true;
            vm.successMessage = "Success!";
            vm.bankSubmitInfo = true;
            vm.GlobalOmnitureService.banksubmit(null);
        }
        else {
          vm.errorbank = true;
          vm.errorMessage = response.data.error.errorMessage;
          vm.GlobalOmnitureService.banksubmit(response.error);
        }
      })
      .catch(function (errorData) {
          vm.errorbank = true;
          vm.errorMessage = "Cannot process your request right now, please try again later.";
          vm.GlobalOmnitureService.banksubmit(errorData.error);
      })

      .finally(function () {
          vm.isLoading = false;
      });

    }

    public submitMobForm() {
      var vm =this;
      this.isLoading = true;
      this.errorMessage = '';
      vm.successmob = false;
      vm.errormob = false;
      this.mobForm.dateOfFormation =vm.$filter('date')(vm.testDate, 'dd-MM-yyyy');
      //this.mobForm.tdrDetailsDTO = {"fixedFeeValue":0,"validFrom":"abc","merchantFeeValue":2};
      var data = {
        'businessInformationDTO' : this.mobForm
      }
      this.MobService.mobSignUp(data).then(function(response){
        if(!response.data.error) {
            vm.successmob = true;
            var userStatus = {
              'BASIC_INFO':true,
              'BANK_INFO':vm.bankInfo,
              'KYC': vm.kycInfo
            }
            vm.merchantStatus.BASIC_INFO = true;
            vm.StorageService.setKey(StorageEnum.userStatus, userStatus);
            vm.GlobalOmnitureService.businessInfoSubmit(null, vm.merchantEmail, vm.mobForm.businessType, vm.mobForm.businessCategory, vm.mobForm.subCategory);
            vm.successMessage = "Success! Please take a printout of this form and e-mail it's self attested copy (along with your KYC documents) to complete the registration.";
        }
        if(response.data.error) {
            vm.errormob = true;
            vm.errorMessage = response.data.error.errorMessage;
            vm.GlobalOmnitureService.businessInfoSubmit(response.error, vm.merchantEmail, vm.mobForm.businessType, vm.mobForm.businessCategory, vm.mobForm.subCategory);
        }
      })
      .catch(function (errorData) {
          vm.errormob = true;
          vm.errorMessage = "Unable to process your request. Please try later";
          vm.GlobalOmnitureService.businessInfoSubmit(errorData.error, vm.merchantEmail, vm.mobForm.businessType, vm.mobForm.businessCategory, vm.mobForm.subCategory);
      })

      .finally(function () {
          vm.isLoading = false;
      })
    }

    public printDiv (divName) {

        var printContents = document.getElementById(divName).style.display = "inline";
        var printContents = document.getElementById(divName).innerHTML;
        var originalContents = document.body.innerHTML;      

        if (navigator.userAgent.toLowerCase().indexOf('chrome') > -1) {
            var popupWin = window.open('', '_blank', 'width=600,height=600,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
            popupWin.window.focus();
            popupWin.document.write('<!DOCTYPE html><html><head>' +
                '</head><body onload="window.print()"><div style="width:100%; clear:both; float:left; text-align:center"><img src="assets/images/print-Header.PNG" alt="Freecharge" class="pull-left"></div>' + printContents +
                '<div style="width:100%; float:left; text-align:center"><img src="assets/images/print-Footer.PNG" alt="Freecharge" class="pull-left"></div></html>');
            popupWin.onbeforeunload = function (event) {
                popupWin.close();
                return '.\n';
            };
            popupWin.onabort = function (event) {
                popupWin.document.close();
                popupWin.close();
            }
        } else {
            var popupWin = window.open('', '_blank', 'width=800,height=600');
            popupWin.document.open();
            popupWin.document.write('<html><head></head><body onload="window.print()"><div style="width:100%; clear:both; float:left; text-align:center"><img src="assets/images/print-Header.PNG" alt="Freecharge" class="pull-left"></div>' + printContents +
                '<div style="width:100%; float:left; text-align:center"><img src="assets/images/print-Footer.PNG" alt="Freecharge" class="pull-left"></div></html>');
            popupWin.document.close();
        }
        popupWin.document.close();
        document.getElementById(divName).style.display = "none";
        return true;
    }

    public bankDialog (ev) {
        var vm = this;
        this.$mdDialog.show({
          targetEvent: ev,
          templateUrl: 'app/merchantOnboard/bank.tmpl.html',
          clickOutsideToClose:true
        })
        .then(function(answer) {
        }, function() {
      });
  }
  public nextTab() {
    var vm = this;
    var index = (vm.selectedIndex == vm.max) ? 0 : vm.selectedIndex + 1;
    vm.selectedIndex = index;

  }

  public selectTab(tab, id) {
    var vm = this;
    vm.selectedIndex = tab;
    angular.element(document.getElementsByClassName('mobTab')).removeClass('activeMobTab');
    angular.element(document.getElementById(id)).addClass('activeMobTab');
  }

  public getKycDocs(bt) {
    var vm = this;
    vm.isLoading = true;
    vm.MobService.getKycDocs(bt).then(function(response){
        vm.kycDocList = response.data.data.merchantKycDocList;
        vm.MobService.getUploadedKycDocs().then(function(response){
          vm.userKycDocList = response.data.data.uploadedDocDTO;
          var userlist = {};
          angular.forEach(vm.userKycDocList, function(value, key) {
              userlist[value.documentCategory] = value;
          });
          angular.forEach(vm.kycDocList, function(value, key) {
              if(userlist[value.docCategory]) {
                value.uploadedstatus = true;
                value.uploadedKyc = userlist[value.docCategory];
              }
          });
        }).finally(function(){
           vm.isLoading = false;
        });
    });
  }

  public uploadKycDoc(file, name, docCat, docType, doc) {
    var vm = this;
    vm.errorKyc = false;
    if(!file || !docType || !docCat) 
      return;
    var uploadUrl = vm.MOB_URL.UPLOAD_KYC_DOC.URL;
    var uploadData = {
      'name':file.name,
      'docCategory':docCat,
      'docType':docType,
      'approvalStatus':false
    }
    vm.isLoading=true;
    vm.fileUpload.withDataUpload(file, uploadUrl, uploadData, 'file').then(function(response){
      if(response.error==null) {
        doc.uploadedstatus = true;
        doc.uploadedKyc = {
          "name": file.name,
          "approvalStatus": false,
          "docStatus":"Awaiting approval",
          "documentType": docType,
          "documentCategory": docCat,
        }
        vm.GlobalOmnitureService.kycSubmit();
        vm.isLoading=false;
      }
      else {
        vm.isLoading=false;
        vm.errorKyc = true;
        vm.errorKycMsg = response.error.errorMessage;
      }
    });
  }

  public loadSandboxTab() {
    this.GlobalOmnitureService.loadIntegrationKit();
  }
  public clickIntegrationkit() {
    this.GlobalOmnitureService.clickIntegrationKit();
  }

  public loadBank() {
    this.GlobalOmnitureService.bankload();
  }

  public loadKyc() {
    this.GlobalOmnitureService.kycload();
  }

}


