(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('EmpresaDetailController', EmpresaDetailController);

    EmpresaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Empresa'];

    function EmpresaDetailController($scope, $rootScope, $stateParams, previousState, entity, Empresa) {
        var vm = this;

        vm.empresa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('portalApp:empresaUpdate', function(event, result) {
            vm.empresa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
