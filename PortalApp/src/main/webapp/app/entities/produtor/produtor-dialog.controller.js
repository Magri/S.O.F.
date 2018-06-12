(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('ProdutorDialogController', ProdutorDialogController);

    ProdutorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Produtor', 'Grupo', 'Noticia', 'PrevisaoTempo', 'Cotacao'];

    function ProdutorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Produtor, Grupo, Noticia, PrevisaoTempo, Cotacao) {
        var vm = this;

        vm.produtor = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.grupos = Grupo.query();
        vm.noticias = Noticia.query();
        vm.previsaotempos = PrevisaoTempo.query();
        vm.cotacaos = Cotacao.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.produtor.id !== null) {
                Produtor.update(vm.produtor, onSaveSuccess, onSaveError);
            } else {
                Produtor.save(vm.produtor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('portalApp:produtorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataNascimento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
