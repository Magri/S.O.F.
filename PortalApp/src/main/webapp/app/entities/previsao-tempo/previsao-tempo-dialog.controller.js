(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('PrevisaoTempoDialogController', PrevisaoTempoDialogController);

    PrevisaoTempoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PrevisaoTempo', 'Produtor', 'Grupo'];

    function PrevisaoTempoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PrevisaoTempo, Produtor, Grupo) {
        var vm = this;

        vm.previsaoTempo = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.produtors = Produtor.query();
        vm.grupos = Grupo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.previsaoTempo.id !== null) {
                PrevisaoTempo.update(vm.previsaoTempo, onSaveSuccess, onSaveError);
            } else {
                PrevisaoTempo.save(vm.previsaoTempo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('portalApp:previsaoTempoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataPrevisao = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
