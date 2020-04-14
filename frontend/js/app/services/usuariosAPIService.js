(function() {

    'use strict';

    angular.module('app-cadastral').factory('usuariosAPI', usuariosAPI);

    function usuariosAPI($http, configAPI) {

        var _getUsuarios = function() {
            return $http.get(configAPI.resourceUsuarios);
        };
    
        var _saveUsuario = function(usuario) {
            if(!!usuario.senha) {
                usuario.senha = btoa(usuario.senha);
            }
            if (!!usuario._links && !!usuario._links.usuario.href) {
                usuario.id = usuario._links.usuario.href.replace(/^.*\//g, '');
                return $http.put(usuario._links.usuario.href, usuario);
            }
            return $http.post(configAPI.resourceUsuarios, usuario);
        };
    
        var _removeUsuario = function(usuarioParaRemover) {
            return $http.delete(usuarioParaRemover._links.usuario.href);
        }
    
        return {
            getUsuarios: _getUsuarios,
            saveUsuario: _saveUsuario,
            removeUsuario: _removeUsuario
        };
    }

})();
