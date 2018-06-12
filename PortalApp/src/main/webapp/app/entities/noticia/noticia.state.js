(function() {
    'use strict';

    angular
        .module('portalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('noticia', {
            parent: 'entity',
            url: '/noticia',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Noticias'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/noticia/noticias.html',
                    controller: 'NoticiaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('noticia-detail', {
            parent: 'noticia',
            url: '/noticia/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Noticia'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/noticia/noticia-detail.html',
                    controller: 'NoticiaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Noticia', function($stateParams, Noticia) {
                    return Noticia.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'noticia',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('noticia-detail.edit', {
            parent: 'noticia-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/noticia/noticia-dialog.html',
                    controller: 'NoticiaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Noticia', function(Noticia) {
                            return Noticia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('noticia.new', {
            parent: 'noticia',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/noticia/noticia-dialog.html',
                    controller: 'NoticiaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                link: null,
                                texto: null,
                                dataPublicacao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('noticia', null, { reload: 'noticia' });
                }, function() {
                    $state.go('noticia');
                });
            }]
        })
        .state('noticia.edit', {
            parent: 'noticia',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/noticia/noticia-dialog.html',
                    controller: 'NoticiaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Noticia', function(Noticia) {
                            return Noticia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('noticia', null, { reload: 'noticia' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('noticia.delete', {
            parent: 'noticia',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/noticia/noticia-delete-dialog.html',
                    controller: 'NoticiaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Noticia', function(Noticia) {
                            return Noticia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('noticia', null, { reload: 'noticia' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
