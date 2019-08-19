import {ILoginService} from "./login.service";
import {StorageEnum} from "../../shared/storage/storage.enum";
import {StorageService} from "../../shared/storage/storage.service";
import {IStorageService} from "../../shared/storage/storage.service";
import {IPermissionService} from "../../shared/permission/permission.service";
import {PermissionService} from "../../shared/permission/permission.service";
import {FAQ} from "../../static/faq.constant"
import {ISmoothScrollService} from "../../shared/services/scroll.service";
import {IGlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {GlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {IMobService} from "../../merchantOnboard/mob.interface";


/** @ngInject **/
export class LoginController {
    public  isLoading:boolean;
    public errorMessage:string;
    public error:boolean;
    private LoginService:ILoginService;
    private StorageService:IStorageService;
    private $state:ng.ui.IStateService;
    private $filter: ng.IFilterService;
    private PermissionService: IPermissionService;
    public MobService: IMobService;
    public $scope: angular.IScope;
    public status:any;
    public FAQ:any;
    public bCats:any;
    public $sce:angular.ISCEService;
    public SmoothScrollService:ISmoothScrollService;
    public $mdDialog: angular.material.IDialogService;
    private GlobalOmnitureService: IGlobalOmnitureService;
    public success: any;
    public successMessage: any;
    public offline:any;
    public $location:angular.ILocationService;
    public faqTab;

    public prodBanner;

    constructor(LoginService:ILoginService, FAQ:any,$sce:angular.ISCEService ,MobService:IMobService,
    SmoothScrollService:ISmoothScrollService, $scope: angular.IScope, PermissionService: IPermissionService,
    StorageService:IStorageService, $state:ng.ui.IStateService, $mdDialog: angular.material.IDialogService,
    GlobalOmnitureService: IGlobalOmnitureService,  $filter: ng.IFilterService, $location:angular.ILocationService) {

        this.isLoading = false;
        this.LoginService = LoginService;
        this.PermissionService = PermissionService;
        this.StorageService = StorageService;
        this.MobService = MobService;
        this.bCats;
        this.$state = $state;
        this.$scope = $scope;
        this.status = null;
        this.$mdDialog = $mdDialog;
        this.$location = $location;
        this.FAQ = FAQ.getFaq;
        this.SmoothScrollService = SmoothScrollService;
        this.$sce = $sce;
        this.$filter = $filter;
        this.GlobalOmnitureService = GlobalOmnitureService;
        this.success;
        this.successMessage;
        this.offline = {};
        this.businessCateogory();
        this.faqTab = 0;
        this.prodBanner = "offline";
    }

    public forgotPwd() {
        this.hide();
        this.$state.go('authentication.forgotPassword');
    }

    public loginClick(loginName, password) {

        this.isLoading = true;
        this.errorMessage = '';
        var vm = this;
        vm.StorageService.setKey(StorageEnum.userStatus, null);
        this.LoginService.login(loginName, password)
            .then(function (successData) {
                vm.PermissionService.initialize(successData);
                vm.GlobalOmnitureService.loginSuccess();
                vm.StorageService.setKey(StorageEnum.user, successData);
                if(successData.merchantState!=null) {
                    var merchantStatus = successData.merchantState;
                    var merchantStates = {
                      "BASIC_INFO":false,
                      "BANK_INFO":false,
                      "KYC":false
                    };
                    angular.forEach(merchantStatus, function(m) {
                       merchantStates[m.state] = m.completed;
                    });
                    vm.StorageService.setKey(StorageEnum.userStatus, merchantStates);
                    if(merchantStates['BASIC_INFO'] == true && merchantStates['BANK_INFO'] == true && merchantStates['KYC'] == true)
                        {
                          var accessibleStates = vm.StorageService.getKey(StorageEnum.accessibleStates);
                          accessibleStates = vm.$filter('orderBy')(accessibleStates, 'priority');
                          vm.$state.go(accessibleStates[0].state);
                        }
                    else
                        {
                          vm.$state.go('base.default.merchant-onboard');
                        }
                }
                else {
                      var accessibleStates = vm.StorageService.getKey(StorageEnum.accessibleStates);
                      accessibleStates = vm.$filter('orderBy')(accessibleStates, 'priority');
                      vm.$state.go(accessibleStates[0].state);
                }
                vm.hide();
            })
            .catch(function (errorData) {
                vm.error = true;
                vm.errorMessage = errorData.errorMessage;
                vm.GlobalOmnitureService.setLogin(errorData.errorMessage);
            })

            .finally(function () {
                vm.isLoading = false;
            })
    }
    public createNewMerchant(loginName, password, mode) {

        this.isLoading = true;
        this.errorMessage = '';
        var vm = this;

        this.LoginService.createMerchant(loginName, password, mode)
            .then(function (successData) {
                vm.PermissionService.initialize(successData);
                var merchantStatus = successData.merchantState;
                var merchantStates = {
                  "BASIC_INFO":false,
                  "BANK_INFO":false,
                  "KYC":false
                };
                angular.forEach(merchantStatus, function(m) {
                   merchantStates[m.state] = m.completed;
                });
                vm.StorageService.setKey(StorageEnum.user, successData);
                vm.StorageService.setKey(StorageEnum.userStatus, merchantStates);
                //vm.$state.go('base.default.download-history');
                vm.GlobalOmnitureService.clickRegisterSubmit(null);
                vm.$state.go('base.default.merchant-onboard');
                vm.hide();
            })
            .catch(function (errorData) {
                vm.error = true;
                vm.errorMessage = errorData.errorMessage;
                vm.GlobalOmnitureService.clickRegisterSubmit(errorData.errorMessage);
            })

            .finally(function () {
                vm.isLoading = false;
               /* vm.$state.go('base.default.download-history');*/
            })
    }
    public offlineMerchant(offline) {
      this.isLoading = true;
        this.errorMessage = '';
        var vm = this;
        vm.success = false;
        vm.error = false;

        this.LoginService.createOfflineMerchant(offline)
            .then(function (successData) {
//              vm.offline = {};
                  vm.success = true;
                  vm.successMessage = "Successfully submitted! Our Alliances team will shortly reach out to you.";
            })
            .catch(function (errorData) {
                vm.error = true;
                vm.errorMessage = errorData.errorMessage;
            })

            .finally(function () {
                vm.isLoading = false;
               /* vm.$state.go('base.default.download-history');*/
            })
    }

     public businessCateogory () {
      var vm = this;
      this.MobService.getBusinessCategories().then(function(response){
        vm.bCats = response.data.data.merchantUIData;
      });
    }

    public signupbox (ev) {
        var vm = this;
        vm.GlobalOmnitureService.clickRegister();
        this.$mdDialog.show({
          targetEvent: ev,
          templateUrl: 'app/authentication/login/onlineoffline.tmpl.html',
          clickOutsideToClose:true
        })
        .then(function(answer) {
        }, function() {
        });
  }

  public loginbox (ev) {
        this.GlobalOmnitureService.clickLogin();
        var vm = this;
        this.$mdDialog.show({
          targetEvent: ev,
          templateUrl: 'app/authentication/login/loginbox.tmpl.html',
          clickOutsideToClose:true
        })
        .then(function(answer) {
        }, function() {
        });
  }

  public showVideo(ev) {
    var vm = this;
        this.$mdDialog.show({
          targetEvent: ev,
          templateUrl: 'app/authentication/products/offlinevideo.tmpl.html',
          clickOutsideToClose:true
        })
        .then(function(answer) {
        }, function() {
        });
  }
  public offlinedialog (ev) {
        var vm = this;
        vm.GlobalOmnitureService.clickOfflineRegister();
        this.$mdDialog.show({
          targetEvent: ev,
          templateUrl: 'app/authentication/login/offline.tmpl.html',
          clickOutsideToClose:true
        })
        .then(function(answer) {
        }, function() {
        });
  }
  public onlinedialog (ev) {
        var vm = this;
        vm.GlobalOmnitureService.clickOnlineRegister();
        this.$mdDialog.show({
          targetEvent: ev,
          templateUrl: 'app/authentication/login/online.tmpl.html',
          clickOutsideToClose:true
        })
        .then(function(answer) {
        }, function() {
        });
  }
  public getActiveTab (taburl) {
    if(this.$location.url() == taburl)
      return true;
  }
  public getActiveFaqTab(t) {
      if(this.faqTab == t)
          return true;
  }
  public hide () {
    this.$mdDialog.hide();
  }
  public cancel () {
    this.$mdDialog.cancel();
  }
  public highlight(text, search) {
      var vm = this;
        if (!search) {
            return vm.$sce.trustAsHtml(text);
        }
        return vm.$sce.trustAsHtml(text.replace(new RegExp(search, 'gi'), '<span class="highlightedText">$&</span>'));
    }
  public goToElement(ID) {
        this.SmoothScrollService.scrollTo(ID,0,50);
        this.faqTab = ID;
    }
  public productsPage() {
    var vm = this;
     vm.GlobalOmnitureService.clickProducts();
  }
  public offlineproductsPage() {
    var vm = this;
     vm.GlobalOmnitureService.loadOfflineProducts();
  }
  public onlineproductsPage() {
    var vm = this;
     vm.GlobalOmnitureService.loadOnlineProducts();
  }
  public pricingPage() {
    var vm = this;
     vm.GlobalOmnitureService.clickPricing();
  }
  public loadPricingPage() {
    var vm = this;
     vm.GlobalOmnitureService.loadPricing();
  }
  public homePage() {
    var vm = this;
     vm.GlobalOmnitureService.onHome();
  }
  public showMobMenu() {
    angular.element(document.getElementById('mobOverlayMenu')).toggleClass('show');
  }
  public setProdBanner(e,url) {
    this.prodBanner = e;
    this.$location.url(url);
    if(this.$location.url() == '/offlineproducts')
      this.offlineproductsPage();
    else if(this.$location.url() == '/onlineproducts')
      this.onlineproductsPage();
  }
  public clickLearnMore() {
    this.GlobalOmnitureService.clickLearnMore();
  }
  public clickHelp() {
    this.GlobalOmnitureService.clickHelp();
  }
  public loadHelp() {
    this.GlobalOmnitureService.loadHelp();
  }
  public clickGetStarted(){
    this.GlobalOmnitureService.clickGetStarted();
  }
  public clickOfflineProd() {
    this.GlobalOmnitureService.clickOfflineRegister();
  }
  public clickOnlineProd() {
    this.GlobalOmnitureService.clickOnlineProducts();
  }
}


