(function() {

    'use strict';

    angular.module('app-cadastral').factory('pessoasAPI', pessoasAPI);

    function pessoasAPI($http, configAPI) {

        var _getPessoas = function() {
            return $http.get(configAPI.resourcePessoas);
        };
    
        var _savePessoa = function(pessoa) {
            if (!!pessoa.cpf) {
                pessoa.cpf = pessoa.cpf.replace(/\D/g,'');
            }
            if (!!pessoa._links && !!pessoa._links.pessoa.href) {
                pessoa.id = pessoa._links.pessoa.href.replace(/^.*\//g, '');
                return $http.put(pessoa._links.pessoa.href, pessoa);
            }
            return $http.post(configAPI.resourcePessoas, pessoa);
        };
    
        var _removePessoa = function(pessoaParaRemover) {
            return $http.delete(pessoaParaRemover._links.pessoa.href);
        }
    
        return {
            getPessoas: _getPessoas,
            savePessoa: _savePessoa,
            removePessoa: _removePessoa
        };
    }

})();
