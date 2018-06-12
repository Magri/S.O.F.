(function() {
    'use strict';

    angular
        .module('portalApp')
        .controller('LoginAppDetailController', LoginAppDetailController);

    LoginAppDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LoginApp', 'Produtor'];

    function LoginAppDetailController($scope, $rootScope, $stateParams, previousState, entity, LoginApp, Produtor) {
        var vm = this;

        vm.loginApp = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('portalApp:loginAppUpdate', function(event, result) {
            vm.loginApp = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
