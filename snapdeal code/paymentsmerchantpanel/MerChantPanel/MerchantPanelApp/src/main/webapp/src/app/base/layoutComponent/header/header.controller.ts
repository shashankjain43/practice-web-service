import {APP_MESSAGE_CONSTANT} from "../../../shared/constants/message.constant";

/* @ngInject */
export class HeaderController {
    selectedDrodownItem:any;
    searchValue:string;
    isShowError:boolean;
    dropdown:any;
    requiredMessage:string;
    APP_KEY:any;
    APP_MESSAGE:any;

    $state:ng.ui.IStateService;
    $scope:ng.IScope;

    constructor($state:ng.ui.IStateService, $scope:ng.IScope, APP_KEY:any, TRANSACTION_CONSTANT) {
        this.$state = $state;
        this.$scope = $scope;
        this.searchValue = '';
        this.isShowError = false;
        this.APP_KEY = APP_KEY.getKeys;
        this.APP_MESSAGE = APP_MESSAGE_CONSTANT.getMessageConstants;
        this.dropdown = angular.copy(TRANSACTION_CONSTANT().TRANSACTION_CONSTANT.TRANSACTION_SEARCH);
        this.requiredMessage = this.APP_MESSAGE.REQUIRED_FIELD + this.dropdown[0].displayValue;
        this.selectedDrodownItem = angular.toJson({
            'key': 'Select',
            'displayValue': 'Search criteria'
        });
        this.attachScopeEvents();
    }

    attachScopeEvents() {

        var vm = this;
        vm.$scope.$on('keydown', function (event, keyEvent) {
            if (keyEvent.keyCode == vm.APP_KEY.ENTER) {
                vm.search();
            }
        });
        vm.$scope.$watch(() => vm.selectedDrodownItem,
            (oldValue:string, newValue:string) => {
                if (oldValue !== newValue) {
                    vm.requiredMessage = vm.APP_MESSAGE.REQUIRED_FIELD + angular.fromJson(vm.selectedDrodownItem).displayValue;
                }
            });
    }

    search() {
        if (this.selectedDrodownItem != '') {
            var keyParam = angular.fromJson(this.selectedDrodownItem).key;
            if (this.searchValue != '' && keyParam != 'Select') {
                this.$state.go('base.default.payment.transaction', {
                    searchParam: {
                        key: keyParam,
                        value: this.searchValue
                    }
                })
            }
            else {
                this.isShowError = keyParam == 'Select' ? false : true;
            }
        }
        else
            this.isShowError = false;

    }
}
