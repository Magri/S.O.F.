'use strict';

describe('Controller Tests', function() {

    describe('PrevisaoTempo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPrevisaoTempo, MockProdutor, MockGrupo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPrevisaoTempo = jasmine.createSpy('MockPrevisaoTempo');
            MockProdutor = jasmine.createSpy('MockProdutor');
            MockGrupo = jasmine.createSpy('MockGrupo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PrevisaoTempo': MockPrevisaoTempo,
                'Produtor': MockProdutor,
                'Grupo': MockGrupo
            };
            createController = function() {
                $injector.get('$controller')("PrevisaoTempoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'portalApp:previsaoTempoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
