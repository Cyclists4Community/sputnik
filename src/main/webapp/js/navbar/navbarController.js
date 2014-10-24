(function () {
    angular.module("navbar").controller("navbarController", ['$scope', '$http', '$window', 'stravaProfileResource', 'profileResource', 'authorizationResource',
        function ($scope, $http, $window, stravaProfileResource, profileResource, authorizationResource) {
            stravaProfileResource.get().$promise.then(setStravaProfile);
            profileResource.get().$promise.then(setProfile);
            authorizationResource.get().$promise.then(setAdmin);

            $scope.signout = function () {
                $http.post('/signout', {}).then(redirectToHome);
            };

            function setStravaProfile(result) {
                $scope.stravaProfile = result;
            }

            function setProfile(result) {
                $scope.profile = result;
            }

            function setAdmin(authorities) {
                $scope.admin = authorities.admin;
            }

            function redirectToHome() {
                $window.location.href = '/';
            }
        }]);
})();