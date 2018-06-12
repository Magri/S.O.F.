(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('NoticiaDialogController', NoticiaDialogController);

    NoticiaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Noticia', 'Produtor', 'Grupo'];

    function NoticiaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Noticia, Produtor, Grupo) {
        var vm = this;

        vm.noticia = entity;
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
            if (vm.noticia.id !== null) {
                Noticia.update(vm.noticia, onSaveSuccess, onSaveError);
            } else {
                Noticia.save(vm.noticia, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('portalApp:noticiaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataPublicacao = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
