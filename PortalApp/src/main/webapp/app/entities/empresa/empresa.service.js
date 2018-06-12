(function() {
    'use strict';
    angular
        .module('portalApp')
        .factory('Empresa', Empresa);

    Empresa.$inject = ['$resource'];

    function Empresa ($resource) {
        var resourceUrl =  'api/empresas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
