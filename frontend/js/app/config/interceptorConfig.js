(function(){

    'use strict';

    angular.module('app-cadastral').config(interceptorsApp);

    interceptorsApp.$inject = ['$httpProvider'];

    function interceptorsApp ($httpProvider) {
        $httpProvider.interceptors.push('authorizationInterceptor');
    }

})();
