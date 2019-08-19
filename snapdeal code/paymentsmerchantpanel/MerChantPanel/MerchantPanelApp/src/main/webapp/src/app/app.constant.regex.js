(function() {
    'use strict';

    angular.module('app').constant('APP_REGEX', APP_REGEX());
    // across application regex
    function APP_REGEX(){
        var regex ={
            //valid general email :  a@b.c
            email:'^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$'
        };
        return regex;
    }
})();
