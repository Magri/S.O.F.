(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('CotacaoDeleteController',CotacaoDeleteController);

    CotacaoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cotacao'];

    function CotacaoDeleteController($uibModalInstance, entity, Cotacao) {
        var vm = this;

        vm.cotacao = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cotacao.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
