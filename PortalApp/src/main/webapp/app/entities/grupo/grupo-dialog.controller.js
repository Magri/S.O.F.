(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('GrupoDialogController', GrupoDialogController);

    GrupoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Grupo', 'Empresa', 'Produtor', 'Noticia', 'PrevisaoTempo', 'Cotacao'];

    function GrupoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Grupo, Empresa, Produtor, Noticia, PrevisaoTempo, Cotacao) {
        var vm = this;

        vm.grupo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.empresas = Empresa.query({filter: 'grupo-is-null'});
        $q.all([vm.grupo.$promise, vm.empresas.$promise]).then(function() {
            if (!vm.grupo.empresaId) {
                return $q.reject();
            }
            return Empresa.get({id : vm.grupo.empresaId}).$promise;
        }).then(function(empresa) {
            vm.empresas.push(empresa);
        });
        vm.produtors = Produtor.query();
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
            if (vm.grupo.id !== null) {
                Grupo.update(vm.grupo, onSaveSuccess, onSaveError);
            } else {
                Grupo.save(vm.grupo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('portalApp:grupoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
