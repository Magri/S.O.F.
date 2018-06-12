(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('NoticiaDetailController', NoticiaDetailController);

    NoticiaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Noticia', 'Produtor', 'Grupo'];

    function NoticiaDetailController($scope, $rootScope, $stateParams, previousState, entity, Noticia, Produtor, Grupo) {
        var vm = this;

        vm.noticia = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('portalApp:noticiaUpdate', function(event, result) {
            vm.noticia = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
