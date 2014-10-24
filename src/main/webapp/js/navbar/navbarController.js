(function () {
    angular.module("navbar").controller("navbarController", ['$scope', '$http', '$window', 'stravaProfileResource', 'authorizationResource',
        function ($scope, $http, $window, stravaProfileResource, authorizationResource) {
            stravaProfileResource.get().$promise.then(setProfile);
            authorizationResource.get().$promise.then(setAdmin);

            $scope.signout = function () {
                $http.post('/signout', {}).then(redirectToHome);
            };

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