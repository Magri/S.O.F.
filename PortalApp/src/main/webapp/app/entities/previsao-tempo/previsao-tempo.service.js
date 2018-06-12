(function() {
    'use strict';
    angular
        .module('portalApp')
        .factory('PrevisaoTempo', PrevisaoTempo);

    PrevisaoTempo.$inject = ['$resource', 'DateUtils'];

    function PrevisaoTempo ($resource, DateUtils) {
        var resourceUrl =  'api/previsao-tempos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataPrevisao = DateUtils.convertDateTimeFromServer(data.dataPrevisao);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
