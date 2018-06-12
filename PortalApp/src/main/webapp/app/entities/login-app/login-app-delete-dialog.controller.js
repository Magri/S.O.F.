(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('LoginAppDeleteController',LoginAppDeleteController);

    LoginAppDeleteController.$inject = ['$uibModalInstance', 'entity', 'LoginApp'];

    function LoginAppDeleteController($uibModalInstance, entity, LoginApp) {
        var vm = this;

        vm.loginApp = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LoginApp.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
