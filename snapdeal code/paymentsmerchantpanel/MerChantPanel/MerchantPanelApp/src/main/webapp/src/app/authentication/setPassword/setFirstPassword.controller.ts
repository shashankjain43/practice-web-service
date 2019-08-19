/**
 * Created by user on 2/12/15.
 */
(function () {
    'use strict';

    angular
        .module('app.authentication')
        .controller('SetFirstPasswordController', SetFirstPasswordController);

    /* @ngInject */
      function SetFirstPasswordController($stateParams, $timeout, $state, SetFirstPasswordService, APP_MESSAGE) {
        var vm = this;
        vm.successMessage = '';
        vm.errorMessage = '';
        vm.submitPassword = submitPassword;
        vm.requiredMessage = APP_MESSAGE.getMessageConstants.REQUIRED_FIELD;
        vm.pwdLengthRange = APP_MESSAGE.getMessageConstants.PASSWSORD_RANGE;
        vm.nomatchPassword = APP_MESSAGE.getMessageConstants.NO_MATCH_PASSWORD;

        function submitPassword()
        {
            vm.successMessage = '';
            vm.errorMessage = '';
            vm.isLoading = true;
            SetFirstPasswordService.setPassword(vm.newPwd,$stateParams.hasval)
                .then(function (response) {
                    if(response.data.error != null){
                      vm.errorMessage = response.data.error.errorMessage;
                    }else {
                        vm.successMessage = APP_MESSAGE.getMessageConstants.PASSWORD_SET;
                        $timeout(function() {
                            $state.go('authentication.login');
                        }, 1500);
                    }
                })
                .finally(function(){
                  vm.isLoading = false;
                });
        }
    }
})();
