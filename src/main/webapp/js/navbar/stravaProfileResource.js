(function () {
    angular.module("navbar").factory('stravaProfileResource', ['$resource', function ($resource) {
        return $resource('/strava/profile');
    }]);
})();
