(function() {
    'use strict';

    angular
        .module('app.funnies')
        .factory('FunniesFactory', FunniesFactory);

    FunniesFactory
        .$inject = [
            '$resource',
            'constants'
        ];

    function FunniesFactory($resource, constants) {
        return $resource(constants.apiUrl + '/funnies/:id', {}, {
            'query': {
                method: 'GET',
                isArray: false
            }
        });
    }

})();
