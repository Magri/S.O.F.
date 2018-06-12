'use strict';

describe('Controller Tests', function() {

    describe('Produtor Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProdutor, MockGrupo, MockNoticia, MockPrevisaoTempo, MockCotacao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProdutor = jasmine.createSpy('MockProdutor');
            MockGrupo = jasmine.createSpy('MockGrupo');
            MockNoticia = jasmine.createSpy('MockNoticia');
            MockPrevisaoTempo = jasmine.createSpy('MockPrevisaoTempo');
            MockCotacao = jasmine.createSpy('MockCotacao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Produtor': MockProdutor,
                'Grupo': MockGrupo,
                'Noticia': MockNoticia,
                'PrevisaoTempo': MockPrevisaoTempo,
                'Cotacao': MockCotacao
            };
            createController = function() {
                $injector.get('$controller')("ProdutorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'portalApp:produtorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
