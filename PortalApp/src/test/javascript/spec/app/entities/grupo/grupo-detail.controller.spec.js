'use strict';

describe('Controller Tests', function() {

    describe('Grupo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGrupo, MockEmpresa, MockProdutor, MockNoticia, MockPrevisaoTempo, MockCotacao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGrupo = jasmine.createSpy('MockGrupo');
            MockEmpresa = jasmine.createSpy('MockEmpresa');
            MockProdutor = jasmine.createSpy('MockProdutor');
            MockNoticia = jasmine.createSpy('MockNoticia');
            MockPrevisaoTempo = jasmine.createSpy('MockPrevisaoTempo');
            MockCotacao = jasmine.createSpy('MockCotacao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Grupo': MockGrupo,
                'Empresa': MockEmpresa,
                'Produtor': MockProdutor,
                'Noticia': MockNoticia,
                'PrevisaoTempo': MockPrevisaoTempo,
                'Cotacao': MockCotacao
            };
            createController = function() {
                $injector.get('$controller')("GrupoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'portalApp:grupoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
