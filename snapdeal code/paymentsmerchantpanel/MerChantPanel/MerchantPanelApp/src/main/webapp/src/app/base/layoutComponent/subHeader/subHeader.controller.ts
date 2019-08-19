/**
 * Created by chitra.parihar on 16-03-2016.
 */
"use strict"
/* @ngInject */
export class SubHeaderController {
  subHeaderVM: any;
  $state: ng.ui.IStateService;
  constructor(SubHeaderFor, MENU_CONSTANTS, $state: ng.ui.IStateService, $filter) {
    var vm = this;
    this.$state = $state;
    var tabs = [];

    var SubHeaderVM = $filter('filter')(MENU_CONSTANTS(), {name: SubHeaderFor.KEY})[0];
    if(SubHeaderVM){
      tabs = SubHeaderVM.permissions;
      if(SubHeaderVM.defaultTabs) {
        tabs = tabs.concat(SubHeaderVM.defaultTabs);
      }
    }
    tabs = $filter('orderBy')(tabs, 'priority');
    vm.subHeaderVM = {
      'heading': SubHeaderVM.displayName,
      'tabs': tabs
    };
  }
  tabNavClick(stateName){
    if(stateName == 'base.default.payment.transaction'){
      this.$state.go(stateName, {searchParam: null}, {reload: true});
    } else {
      this.$state.go(stateName, {}, {reload: true});
    }
  }
}
