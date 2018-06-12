(function() {
    'use strict';

    angular
        .module('portalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('previsao-tempo', {
            parent: 'entity',
            url: '/previsao-tempo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PrevisaoTempos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/previsao-tempo/previsao-tempos.html',
                    controller: 'PrevisaoTempoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('previsao-tempo-detail', {
            parent: 'previsao-tempo',
            url: '/previsao-tempo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PrevisaoTempo'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/previsao-tempo/previsao-tempo-detail.html',
                    controller: 'PrevisaoTempoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PrevisaoTempo', function($stateParams, PrevisaoTempo) {
                    return PrevisaoTempo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'previsao-tempo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('previsao-tempo-detail.edit', {
            parent: 'previsao-tempo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/previsao-tempo/previsao-tempo-dialog.html',
                    controller: 'PrevisaoTempoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PrevisaoTempo', function(PrevisaoTempo) {
                            return PrevisaoTempo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('previsao-tempo.new', {
            parent: 'previsao-tempo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/previsao-tempo/previsao-tempo-dialog.html',
                    controller: 'PrevisaoTempoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                link: null,
                                dataPrevisao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('previsao-tempo', null, { reload: 'previsao-tempo' });
                }, function() {
                    $state.go('previsao-tempo');
                });
            }]
        })
        .state('previsao-tempo.edit', {
            parent: 'previsao-tempo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/previsao-tempo/previsao-tempo-dialog.html',
                    controller: 'PrevisaoTempoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PrevisaoTempo', function(PrevisaoTempo) {
                            return PrevisaoTempo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('previsao-tempo', null, { reload: 'previsao-tempo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('previsao-tempo.delete', {
            parent: 'previsao-tempo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/previsao-tempo/previsao-tempo-delete-dialog.html',
                    controller: 'PrevisaoTempoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PrevisaoTempo', function(PrevisaoTempo) {
                            return PrevisaoTempo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('previsao-tempo', null, { reload: 'previsao-tempo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
