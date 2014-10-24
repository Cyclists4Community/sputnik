(function () {
    angular.module("security").factory('authorizationResource', ['$resource', function ($resource) {
        return $resource('/authorization');
    }]);
})();