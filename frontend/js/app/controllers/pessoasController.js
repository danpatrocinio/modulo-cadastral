(function() {
    
    'use strict';

    angular.module('app-cadastral').controller('pessoasController', pessoasController);

    pessoasController.$inject = ['$scope', 'pessoasAPI'];

    function pessoasController($scope, pessoasAPI) {
    
        var vm = $scope;

        vm.tituloApp = "Listagem de pessoas";
        vm.pessoas = [];
        var carregarPessoas = function() {
            pessoasAPI.getPessoas()
            .then(function(response) {
                vm.pessoas = response.data._embedded.pessoas;
            })
            .catch(function(response) {
                var mensagem = "Deu erro: " + response.status + " - " + response.statusText;
                vm.mensagemDeErro = !!response.data.mensagem ? response.data.mensagem : mensagem;
            });
        };
    
        vm.adicionarPessoa = function(pessoa) {
            var novaPessoa = angular.copy(pessoa);
            pessoasAPI.savePessoa(novaPessoa)
            .then(function(response) {
                delete vm.pessoa;
                vm.pessoaForm.$setPristine();
                carregarPessoas();
            })
            .catch(function(response) {
                var mensagem = "Deu erro: " + response.status + " - " + response.statusText;
                vm.mensagemDeErro = !!response.data.mensagem ? response.data.mensagem : mensagem;
            });
        };
    
        vm.removerPessoa = function(pessoaParaRemover) {
            if(!confirm('Deseja realmente excluir?')) { 
                return; 
            };
            pessoasAPI.removePessoa(pessoaParaRemover)
            .then(function(response) {
                carregarPessoas();
            })
            .catch(function(response) {
                var mensagem = "Deu erro: " + response.status + " - " + response.statusText;
                vm.mensagemDeErro = !!response.data.mensagem ? response.data.mensagem : mensagem;
            });
        };
        vm.editarPessoa = function(pessoaParaEditar) {
            vm.pessoa = angular.copy(pessoaParaEditar);
            vm.pessoa.dataNascimento = convertData(pessoaParaEditar.dataNascimento);
        };
        var convertData = function(data) {
            if (!data) {
                return;
            }
            var ano = data.substring(0, 4);
            var mes = data.substring(5, 7);
            var dia = data.substring(8, 10);
            var dt = new Date(ano,mes-1,dia);
            return dt;
        };
        vm.ordenarPor = function(nomeDoCampo) {
            vm.campoParaOrdenacao = nomeDoCampo;
            vm.direcaoDaOrdenacao = !vm.direcaoDaOrdenacao;
        };
        carregarPessoas();
    };
})();