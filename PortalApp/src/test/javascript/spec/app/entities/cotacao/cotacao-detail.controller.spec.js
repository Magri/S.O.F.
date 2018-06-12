'use strict';

describe('Controller Tests', function() {

    describe('Cotacao Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCotacao, MockProduto, MockProdutor, MockGrupo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCotacao = jasmine.createSpy('MockCotacao');
            MockProduto = jasmine.createSpy('MockProduto');
            MockProdutor = jasmine.createSpy('MockProdutor');
            MockGrupo = jasmine.createSpy('MockGrupo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Cotacao': MockCotacao,
                'Produto': MockProduto,
                'Produtor': MockProdutor,
                'Grupo': MockGrupo
            };
            createController = function() {
                $injector.get('$controller')("CotacaoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'portalApp:cotacaoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
