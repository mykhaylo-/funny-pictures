(function() {
    'use strict';

    angular
        .module('app.funnies')
        .factory('FunnyThumbnailsByPicture', FunnyThumbnailsByPicture);

    FunnyThumbnailsByPicture
        .$inject = [
            '$resource',
            'constants'
        ];

    function FunnyThumbnailsByPicture($resource, constants) {
        return $resource(constants.apiUrl + '/funnies/:id/funniesThumb', {}, {
            'query': {
                method: 'GET',
                isArray: false
            }
        });
    }

})();
