(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('CotacaoDetailController', CotacaoDetailController);

    CotacaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cotacao', 'Produto', 'Produtor', 'Grupo'];

    function CotacaoDetailController($scope, $rootScope, $stateParams, previousState, entity, Cotacao, Produto, Produtor, Grupo) {
        var vm = this;

        vm.cotacao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('portalApp:cotacaoUpdate', function(event, result) {
            vm.cotacao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
