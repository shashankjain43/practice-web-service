import ICookiesService = angular.cookies.ICookieStoreService;

/* @ngInject */
export class LayoutController {
  private  $scope:ng.IScope;
  private $state:ng.ui.IStateProvider;
  private $rootScope:ng.IRootScopeService;
  private $cookieStore:ICookiesService;
  private vm:any;

  constructor($scope:ng.IScope, $rootScope:ng.IRootScopeService, $state:ng.ui.IStateProvider, $cookieStore:ICookiesService) {
    this.$scope = $scope;
    this.$state = $state;
    this.$cookieStore = $cookieStore;
    this.$rootScope = $rootScope;
    this.vm = this;
  }

  public closePopup() {
    this.$cookieStore.put('popUpShown', true);
  }

  public checkPopup() {
    return this.$cookieStore.get('popUpShown');
  }

  public  navigate(value:string) {
    if (value === "base.default.payment.transaction") {
      this.$state.go(value, {searchParam: null}, {reload: true});
      return;
    }
    this.$state.go(value, {}, {reload: true});
    this.closePopup();
  }

}

