(function() {
    
    'use strict';
  
    angular.module('app-cadastral').controller('loginController', loginController);
  
    function loginController($location, loginService) {
      
      var vm = this;
  
      vm.login = login;
  
      init();
  
      function init() {
        loginService.sair();
      };
  
      function login() {
        vm.loading = true;
        var auth = {"username" : vm.username , "senha" : vm.senha};
        loginService.logar(auth, function(result) {
          if (result === true) {
            $location.path('/');
          } else {
              vm.loading = false;
              vm.mensagemDeErro = 'Usuário e senha não cadastrados!';
          }
        });
      };
    }
  
  })();