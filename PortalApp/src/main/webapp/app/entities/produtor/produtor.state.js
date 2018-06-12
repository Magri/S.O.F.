(function() {
    'use strict';

    angular
        .module('portalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('produtor', {
            parent: 'entity',
            url: '/produtor',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Produtors'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/produtor/produtors.html',
                    controller: 'ProdutorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('produtor-detail', {
            parent: 'produtor',
            url: '/produtor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Produtor'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/produtor/produtor-detail.html',
                    controller: 'ProdutorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Produtor', function($stateParams, Produtor) {
                    return Produtor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'produtor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('produtor-detail.edit', {
            parent: 'produtor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produtor/produtor-dialog.html',
                    controller: 'ProdutorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Produtor', function(Produtor) {
                            return Produtor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('produtor.new', {
            parent: 'produtor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produtor/produtor-dialog.html',
                    controller: 'ProdutorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                email: null,
                                dataNascimento: null,
                                cpf: null,
                                cadpro: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('produtor', null, { reload: 'produtor' });
                }, function() {
                    $state.go('produtor');
                });
            }]
        })
        .state('produtor.edit', {
            parent: 'produtor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produtor/produtor-dialog.html',
                    controller: 'ProdutorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Produtor', function(Produtor) {
                            return Produtor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('produtor', null, { reload: 'produtor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('produtor.delete', {
            parent: 'produtor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produtor/produtor-delete-dialog.html',
                    controller: 'ProdutorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Produtor', function(Produtor) {
                            return Produtor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('produtor', null, { reload: 'produtor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
