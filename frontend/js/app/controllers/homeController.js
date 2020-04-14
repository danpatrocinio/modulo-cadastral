(function(){
    
    'use strict';
    
    angular.module('app-cadastral').controller('homeController', homeController);

    homeController.$inject = ['$scope', '$localStorage'];

    function homeController($scope, $localStorage) {

        var vm = $scope;
        vm.tituloApp = "App Cadastral";
        vm.currentUser = currentUser;
        vm.anoAtual = new Date().getFullYear();

        function currentUser() {
            return $localStorage.currentUser;
        }

    }

})();
