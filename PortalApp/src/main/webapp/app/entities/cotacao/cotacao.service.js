(function() {
    'use strict';
    angular
        .module('portalApp')
        .factory('Cotacao', Cotacao);

    Cotacao.$inject = ['$resource', 'DateUtils'];

    function Cotacao ($resource, DateUtils) {
        var resourceUrl =  'api/cotacaos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataCotacao = DateUtils.convertDateTimeFromServer(data.dataCotacao);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
