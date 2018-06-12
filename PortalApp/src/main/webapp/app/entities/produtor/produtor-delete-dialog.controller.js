(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('ProdutorDeleteController',ProdutorDeleteController);

    ProdutorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Produtor'];

    function ProdutorDeleteController($uibModalInstance, entity, Produtor) {
        var vm = this;

        vm.produtor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Produtor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
