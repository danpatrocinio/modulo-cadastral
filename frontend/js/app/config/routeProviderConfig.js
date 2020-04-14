(function() {

    'use strict';

    angular.module('app-cadastral').config(AppConfig).run(run);

    AppConfig.$inject = ['$routeProvider'];

    function AppConfig($routeProvider) {

        $routeProvider
            .when('/', {
                templateUrl: 'views/home.html',
                controller: 'homeController'
            })
            .when('/login', {
                templateUrl: 'views/login.html',
                controller: 'loginController',
                controllerAs: 'vm'
            })
            .when('/usuarios', {
                templateUrl: 'views/usuarios.html',
                controller: 'usuariosController'
            })
            .when('/pessoas', {
                templateUrl: 'views/pessoas.html',
                controller: 'pessoasController'
            })
            .otherwise({redirectTo: "/"});
    }
    
    function run($rootScope, $http, $location, $localStorage) {

        if ($localStorage.currentUser) {
          $http.defaults.headers.common.Authorization = 'Basic ' + $localStorage.currentUser.token;
        }
    
        $rootScope.$on('$locationChangeStart', function(event, next, current) {
          var paginasPublicas = ['/login'];
          var paginaRestrita = paginasPublicas.indexOf($location.path()) === -1;
    
          if (paginaRestrita && !$localStorage.currentUser) {
            $location.path('/login');
          }
        });
      }

})();