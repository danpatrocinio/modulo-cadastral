(function() {

    'use strict';

    angular.module('app-cadastral').value('configAPI', {
        resourceLogin: "http://localhost:8080/api-cadastral/logar",
        resourceUsuarios: "http://localhost:8080/api-cadastral/usuarios",
        resourcePessoas: "http://localhost:8080/api-cadastral/pessoas"
    });

})();
