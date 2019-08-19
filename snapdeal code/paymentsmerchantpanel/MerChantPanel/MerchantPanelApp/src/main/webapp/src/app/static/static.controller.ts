import {IStaticService} from "./static.interface";
import {FAQ} from "./faq.constant"
import {ISmoothScrollService} from "../shared/services/scroll.service";
import {IStorageService} from "../shared/storage/storage.service";
import {IPermissionService} from "../shared/permission/permission.service";
import {APP_MESSAGE_CONSTANT} from "../shared/constants/message.constant"
import {IGlobalOmnitureService} from "../shared/services/globalomniture.service";
import {GlobalOmnitureService} from "../shared/services/globalomniture.service";

"use strict";
/* @ngInject */
export class StaticController {
	public StaticService:IStaticService;
	public FAQ:any;
	public $scope:angular.IScope;
    public $location:angular.ILocationService;
	public $sce:angular.ISCEService;
	public SmoothScrollService:ISmoothScrollService;
	public contactIssue:string;
	public contactDesc:string;
	public contactEmail:string;
	public $state:ng.ui.IStateService;
    public isLoading:boolean;
    private GlobalOmnitureService: IGlobalOmnitureService;
    public PermissionService:IPermissionService;
    public StorageService:IStorageService;
    public user:any;
    public errorMessage:string;
    public successMessage:string;
    public APP_MESSAGE:any;
    public loggedInCheck;

	constructor(StaticService:IStaticService,
		FAQ:any,
		$scope:angular.IScope,
		$sce:angular.ISCEService ,
		$state:ng.ui.IStateService,
        StorageService:IStorageService,
        PermissionService:IPermissionService,
		SmoothScrollService:ISmoothScrollService,
        GlobalOmnitureService: IGlobalOmnitureService,
        $location:angular.ILocationService
		){
		this.FAQ = FAQ.getFaq;
		this.StaticService = StaticService;
		this.SmoothScrollService = SmoothScrollService;
        this.APP_MESSAGE = APP_MESSAGE_CONSTANT.getMessageConstants;
        this.GlobalOmnitureService = GlobalOmnitureService;
		this.$scope = $scope;
		this.$sce = $sce;
		this.contactIssue='';
		this.contactDesc='';
		this.$state = $state;
        this.isLoading;
        this.$location = $location;
        if(PermissionService.user != undefined){
            this.user = PermissionService.user;
            this.contactEmail = this.user.userDTO.emailId;
            this.loggedInCheck = true
        }
        else {
            this.loggedInCheck = false;
        }
        this.errorMessage = '';
        this.successMessage = '';
        var vm = this;
	}
    public highlight(text, search) {
    	var vm = this;
        if (!search) {
            return vm.$sce.trustAsHtml(text);
        }
        return vm.$sce.trustAsHtml(text.replace(new RegExp(search, 'gi'), '<span class="highlightedText">$&</span>'));
    }
    public contactMail() {
    	var vm = this;
        if(vm.contactIssue==''||vm.contactDesc==''|| !vm.contactIssue||!vm.contactDesc){
            return;
        }
        vm.isLoading = true;
        var contactData = {
            "emailId":vm.contactEmail,
            "emailContent":vm.contactDesc,
            "issueType":vm.contactIssue
        }
        if(vm.loggedInCheck) {
            var mailSent = this.StaticService.sendContactMail(contactData).then(function (response) {
                if (response.data.error!= null) {
                    vm.errorMessage = this.APP_MESSAGE.CONTACT_US_FAIL;
                    vm.isLoading = false;
                    return;
                }
                else {
                    vm.successMessage = response.data.data.message;
                    vm.isLoading = false;
                    return;
                }
            })
            .finally(function () {
                vm.isLoading = false;
            });
        }
        else {
            var mailSent = this.StaticService.sendContactMailNonLoggedIn(contactData).then(function (response) {
                if (response.data.error!= null) {
                    vm.errorMessage = this.APP_MESSAGE.CONTACT_US_FAIL;
                    vm.isLoading = false;
                    return;
                }
                else {
                    vm.successMessage = response.data.data.message;
                    vm.isLoading = false;
                    return;
                }
            })
            .finally(function () {
                vm.isLoading = false;
            });
        }
        
    }
    public showMobMenu() {
        angular.element(document.getElementById('mobOverlayMenu')).toggleClass('show');
    }
    public getActiveTab (taburl) {
    if(this.$location.url() == taburl)
      return true;
  }
    public loadHelp() {
    this.GlobalOmnitureService.loadHelp();
  }

}
