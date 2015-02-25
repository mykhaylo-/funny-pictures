(function() {
    'use strict';

    angular
        .module('app.funnies')
        .factory('FunnyThumbnailsFactory', FunnyThumbnailsFactory);

    FunnyThumbnailsFactory
        .$inject = [
            '$resource',
            'constants'
        ];

    function FunnyThumbnailsFactory($resource, constants) {
        return $resource(constants.apiUrl + '/funniesthumb/:id', {}, {
            'query': {
                method: 'GET',
                isArray: false
            }
        });
    }

})();
