/**
 * Created by user on 6/11/15.
 */

(function () {
    'use strict';
    angular
        .module('app.authentication')
        .service('SetFirstPasswordService', SetFirstPasswordService);

    /* @ngInject */
    function SetFirstPasswordService(Auth_URL,HttpService) {

        this.setPassword = setPassword;
        function setPassword(password,identifier) {
            var requestObj={
                'password': password,
                'userIdentifier': identifier
            };
            return HttpService.request(Auth_URL.SET_PASSWORD.METHOD, Auth_URL.SET_PASSWORD.URL, requestObj);
        }
    }
})();
