(function() {
    'use strict';
    angular
        .module('portalApp')
        .factory('Produtor', Produtor);

    Produtor.$inject = ['$resource', 'DateUtils'];

    function Produtor ($resource, DateUtils) {
        var resourceUrl =  'api/produtors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataNascimento = DateUtils.convertDateTimeFromServer(data.dataNascimento);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
