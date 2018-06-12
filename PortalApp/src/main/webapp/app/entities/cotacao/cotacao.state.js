(function() {
    'use strict';

    angular
        .module('portalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cotacao', {
            parent: 'entity',
            url: '/cotacao',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cotacaos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cotacao/cotacaos.html',
                    controller: 'CotacaoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('cotacao-detail', {
            parent: 'cotacao',
            url: '/cotacao/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cotacao'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cotacao/cotacao-detail.html',
                    controller: 'CotacaoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Cotacao', function($stateParams, Cotacao) {
                    return Cotacao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cotacao',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cotacao-detail.edit', {
            parent: 'cotacao-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cotacao/cotacao-dialog.html',
                    controller: 'CotacaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cotacao', function(Cotacao) {
                            return Cotacao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cotacao.new', {
            parent: 'cotacao',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cotacao/cotacao-dialog.html',
                    controller: 'CotacaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dataCotacao: null,
                                preco: null,
                                descricaoCustomizadaProduto: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cotacao', null, { reload: 'cotacao' });
                }, function() {
                    $state.go('cotacao');
                });
            }]
        })
        .state('cotacao.edit', {
            parent: 'cotacao',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cotacao/cotacao-dialog.html',
                    controller: 'CotacaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cotacao', function(Cotacao) {
                            return Cotacao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cotacao', null, { reload: 'cotacao' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cotacao.delete', {
            parent: 'cotacao',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cotacao/cotacao-delete-dialog.html',
                    controller: 'CotacaoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cotacao', function(Cotacao) {
                            return Cotacao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cotacao', null, { reload: 'cotacao' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
