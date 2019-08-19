 import {IRefundActionMessage} from "./refund-action-message.interface";
 import {ICssStyleService} from "../../../shared/services/cssStyle.service";
 import {IRefundService} from "../refund.interface";
 import {IGlobalOmnitureService} from "../../../shared/services/globalomniture.service";
 import ITimeoutService = angular.ITimeoutService;

 'use strict';
 /* @ngInject */
 export class RefundActionController{
    requiredMessage: string;
    $scope: ng.IScope;
    $timeout: ng.ITimeoutService;
    CssStyleService: ICssStyleService;
    RefundService: IRefundService;
    isLoading: boolean;
    message:IRefundActionMessage;
    GlobalOmnitureService: IGlobalOmnitureService;
    transactionInfo: any;
    REFUND_ACTION_CONSTANT: any;
    successError: string;
    totalRefundAmount: string;
    maxRefund: number;
    APP_MESSAGE: any;

      constructor($scope: ng.IScope, $timeout: ITimeoutService, REFUND_ACTION_CONSTANT, APP_MESSAGE, CssStyleService, RefundService, GlobalOmnitureService: IGlobalOmnitureService) {
        this.RefundService = RefundService;
        this.REFUND_ACTION_CONSTANT = REFUND_ACTION_CONSTANT();
        this.GlobalOmnitureService = GlobalOmnitureService;
        this.APP_MESSAGE = APP_MESSAGE.getMessageConstants;
        this.message = {};
        this.message.requiredMessage = this.APP_MESSAGE.REQUIRED_FIELD;
        this.message.patternMessage = this.APP_MESSAGE.CORRECT_FIELD;
        this.message.amountGreater = this.APP_MESSAGE.AMOUNT_GREATER;
        this.message.nonZeroValue = this.APP_MESSAGE.NO_ZERO_VALUE;
        this.message.submitSuccess = this.APP_MESSAGE.SUBMIT_SUCCESS;
        this.message.tryAgain = this.APP_MESSAGE.TRY_AGAIN;
        this.message.successError= '';
        this.CssStyleService = CssStyleService;
        this.$scope = $scope;
        this.$timeout = $timeout;
        this.init();
      }

      init(){
        this.transactionActionListener();
      }

      transactionActionListener(){
        var vm = this;
        this.$scope.$on('transaction-action', function (event, args) {
          try {
            vm.resetForm();
          } catch (ex) {
            ex.toString();
          }
          vm.CssStyleService.addDisplayElement('.adduser-bg');
          vm.transactionInfo = args.data.transactionInfo;
          vm.totalRefundAmount = args.data.refundedAmount;
          vm.maxRefund = parseFloat(vm.transactionInfo.totalTxnAmount - vm.totalRefundAmount).toFixed(2);
          vm.transactionInfo.amount = vm.maxRefund;
        });
      }

    refund() {
           var vm = this;
            this.message.errorMessage = undefined;
            this.message.successMessage = undefined;
            this.isLoading = true;
            var reqObjVal = [];
            var requestObject = {};
            reqObjVal.push(this.transactionInfo.orderId);
            reqObjVal.push(this.transactionInfo.amount);
            reqObjVal.push(this.transactionInfo.reason);
            for (var i = 0; i < this.REFUND_ACTION_CONSTANT.submitRefund.length; i++) {
                requestObject[this.REFUND_ACTION_CONSTANT.submitRefund[i]] = reqObjVal[i];
            }

            this.GlobalOmnitureService.submitRefund(this.transactionInfo.amount, this.transactionInfo.reason, this.transactionInfo.custId, this.transactionInfo.productId);

            this.RefundService.initiateRefund(requestObject)
                .then(function (response) {
                        vm.message.successMessage = response.status ? vm.message.submitSuccess : vm.message.tryAgain;
                        vm.actionOnSuccess();
                    })
                .catch(function(response){
                  vm.message.errorMessage = response.errorMessage;
                        vm.showError();
                    })
                .finally(function(){
                  vm.isLoading = false;
                })
        }

         showError() {
            this.message.successError = 'failure';
            this.CssStyleService.focusElement('#erorDiv');
        }

         actionOnSuccess() {
            var _this = this;
            this.message.successError = 'success';
            this.$timeout(function () {
                _this.cancel();
            }, 1000);
            this.CssStyleService.focusElement('#successDiv');

        }

         resetForm() {
            this.message.errorMessage = undefined;
            this.message.successMessage = undefined;
            this.successError = '';
            this.transactionInfo = angular.copy(null);
            this.CssStyleService.clearValFromElement('#reason');
            this.$scope.refundForm.$setPristine();
        }

         cancel() {
            this.CssStyleService.removeCssClass('.initiate-refund-popup', 'show');
            this.CssStyleService.removeDisplayElement('.adduser-bg');
        }
    }
