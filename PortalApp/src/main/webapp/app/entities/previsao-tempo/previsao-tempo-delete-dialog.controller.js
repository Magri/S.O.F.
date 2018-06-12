(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('PrevisaoTempoDeleteController',PrevisaoTempoDeleteController);

    PrevisaoTempoDeleteController.$inject = ['$uibModalInstance', 'entity', 'PrevisaoTempo'];

    function PrevisaoTempoDeleteController($uibModalInstance, entity, PrevisaoTempo) {
        var vm = this;

        vm.previsaoTempo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PrevisaoTempo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
