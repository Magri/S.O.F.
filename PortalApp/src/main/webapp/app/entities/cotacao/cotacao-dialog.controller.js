(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('CotacaoDialogController', CotacaoDialogController);

    CotacaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Cotacao', 'Produto', 'Produtor', 'Grupo'];

    function CotacaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Cotacao, Produto, Produtor, Grupo) {
        var vm = this;

        vm.cotacao = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.produtos = Produto.query({filter: 'cotacao-is-null'});
        $q.all([vm.cotacao.$promise, vm.produtos.$promise]).then(function() {
            if (!vm.cotacao.produtoId) {
                return $q.reject();
            }
            return Produto.get({id : vm.cotacao.produtoId}).$promise;
        }).then(function(produto) {
            vm.produtos.push(produto);
        });
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
            if (vm.cotacao.id !== null) {
                Cotacao.update(vm.cotacao, onSaveSuccess, onSaveError);
            } else {
                Cotacao.save(vm.cotacao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('portalApp:cotacaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataCotacao = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
