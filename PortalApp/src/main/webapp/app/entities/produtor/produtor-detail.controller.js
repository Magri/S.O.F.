(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('ProdutorDetailController', ProdutorDetailController);

    ProdutorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Produtor', 'Grupo', 'Noticia', 'PrevisaoTempo', 'Cotacao'];

    function ProdutorDetailController($scope, $rootScope, $stateParams, previousState, entity, Produtor, Grupo, Noticia, PrevisaoTempo, Cotacao) {
        var vm = this;

        vm.produtor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('portalApp:produtorUpdate', function(event, result) {
            vm.produtor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
