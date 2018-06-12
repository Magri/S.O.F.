(function() {
    'use strict';
    angular
        .module('portalApp')
        .factory('LoginApp', LoginApp);

    LoginApp.$inject = ['$resource'];

    function LoginApp ($resource) {
        var resourceUrl =  'api/login-apps/:id';

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
