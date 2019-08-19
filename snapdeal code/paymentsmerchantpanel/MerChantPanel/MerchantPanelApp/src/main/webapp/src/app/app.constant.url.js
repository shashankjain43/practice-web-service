(function() {
    'use strict';

    angular.module('app').constant('APP_URL', APP_URL());

    function APP_URL(){
        var url ={
            dummyUrl:{
                method:'POST',
                url : 'api/v1/dummy'
            }
        };
        return url;
    }
})();
