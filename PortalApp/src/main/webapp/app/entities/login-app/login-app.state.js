(function() {
    'use strict';

    angular
        .module('portalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('login-app', {
            parent: 'entity',
            url: '/login-app',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LoginApps'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/login-app/login-apps.html',
                    controller: 'LoginAppController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('login-app-detail', {
            parent: 'login-app',
            url: '/login-app/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LoginApp'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/login-app/login-app-detail.html',
                    controller: 'LoginAppDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LoginApp', function($stateParams, LoginApp) {
                    return LoginApp.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'login-app',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('login-app-detail.edit', {
            parent: 'login-app-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/login-app/login-app-dialog.html',
                    controller: 'LoginAppDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LoginApp', function(LoginApp) {
                            return LoginApp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('login-app.new', {
            parent: 'login-app',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/login-app/login-app-dialog.html',
                    controller: 'LoginAppDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                usuario: null,
                                senha: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('login-app', null, { reload: 'login-app' });
                }, function() {
                    $state.go('login-app');
                });
            }]
        })
        .state('login-app.edit', {
            parent: 'login-app',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/login-app/login-app-dialog.html',
                    controller: 'LoginAppDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LoginApp', function(LoginApp) {
                            return LoginApp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('login-app', null, { reload: 'login-app' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('login-app.delete', {
            parent: 'login-app',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/login-app/login-app-delete-dialog.html',
                    controller: 'LoginAppDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LoginApp', function(LoginApp) {
                            return LoginApp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('login-app', null, { reload: 'login-app' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
