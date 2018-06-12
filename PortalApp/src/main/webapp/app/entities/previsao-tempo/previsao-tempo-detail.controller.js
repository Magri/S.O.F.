(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('PrevisaoTempoDetailController', PrevisaoTempoDetailController);

    PrevisaoTempoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PrevisaoTempo', 'Produtor', 'Grupo'];

    function PrevisaoTempoDetailController($scope, $rootScope, $stateParams, previousState, entity, PrevisaoTempo, Produtor, Grupo) {
        var vm = this;

        vm.previsaoTempo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('portalApp:previsaoTempoUpdate', function(event, result) {
            vm.previsaoTempo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
