(function () {
    angular.module("navbar").factory('profileResource', ['$resource', function ($resource) {
        return $resource('/profile');
    }]);
})();
