(function() {
    'use strict';

    angular
        .module('app.pictures')
        .factory('PictureThumbnailsFactory', PictureThumbnailsFactory);

    PictureThumbnailsFactory
        .$inject = [
            '$resource',
            'constants'
        ];

    function PictureThumbnailsFactory($resource, constants) {
        return $resource(constants.apiUrl + '/picturesthumb/:id', {}, {
            'query': {
                method: 'GET',
                isArray: false
            }
        });
    }

})();
