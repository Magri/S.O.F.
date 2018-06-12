(function() {
    'use strict';
    angular
        .module('portalApp')
        .factory('Noticia', Noticia);

    Noticia.$inject = ['$resource', 'DateUtils'];

    function Noticia ($resource, DateUtils) {
        var resourceUrl =  'api/noticias/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataPublicacao = DateUtils.convertDateTimeFromServer(data.dataPublicacao);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
