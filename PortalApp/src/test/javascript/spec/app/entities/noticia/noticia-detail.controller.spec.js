'use strict';

describe('Controller Tests', function() {

    describe('Noticia Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockNoticia, MockProdutor, MockGrupo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockNoticia = jasmine.createSpy('MockNoticia');
            MockProdutor = jasmine.createSpy('MockProdutor');
            MockGrupo = jasmine.createSpy('MockGrupo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Noticia': MockNoticia,
                'Produtor': MockProdutor,
                'Grupo': MockGrupo
            };
            createController = function() {
                $injector.get('$controller')("NoticiaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'portalApp:noticiaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
