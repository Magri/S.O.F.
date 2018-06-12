(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('GrupoDetailController', GrupoDetailController);

    GrupoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Grupo', 'Empresa', 'Produtor', 'Noticia', 'PrevisaoTempo', 'Cotacao'];

    function GrupoDetailController($scope, $rootScope, $stateParams, previousState, entity, Grupo, Empresa, Produtor, Noticia, PrevisaoTempo, Cotacao) {
        var vm = this;

        vm.grupo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('portalApp:grupoUpdate', function(event, result) {
            vm.grupo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
