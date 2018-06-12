(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('LoginAppDialogController', LoginAppDialogController);

    LoginAppDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'LoginApp', 'Produtor'];

    function LoginAppDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, LoginApp, Produtor) {
        var vm = this;

        vm.loginApp = entity;
        vm.clear = clear;
        vm.save = save;
        vm.produtors = Produtor.query({filter: 'loginapp-is-null'});
        $q.all([vm.loginApp.$promise, vm.produtors.$promise]).then(function() {
            if (!vm.loginApp.produtorId) {
                return $q.reject();
            }
            return Produtor.get({id : vm.loginApp.produtorId}).$promise;
        }).then(function(produtor) {
            vm.produtors.push(produtor);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.loginApp.id !== null) {
                LoginApp.update(vm.loginApp, onSaveSuccess, onSaveError);
            } else {
                LoginApp.save(vm.loginApp, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('portalApp:loginAppUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
