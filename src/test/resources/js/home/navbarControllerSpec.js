describe('navbarController', function () {
    var $scope, stravaProfileResource, profileResource, authorizationResource, stravaProfileDeferred, profileDeferred, authorizationDeferred, signoutDeferred, $http, $window;

    beforeEach(module('navbar'));

    beforeEach(inject(function ($rootScope, $q, _$http_, $controller, _stravaProfileResource_, _profileResource_, _authorizationResource_) {
        $scope = $rootScope.$new();
        $http = _$http_;
        $window = {location: {}};
        stravaProfileResource = _stravaProfileResource_;
        authorizationResource = _authorizationResource_;
        profileResource = _profileResource_;

        stravaProfileDeferred = $q.defer();
        signoutDeferred = $q.defer();
        authorizationDeferred = $q.defer();
        profileDeferred = $q.defer();

        spyOn(profileResource, "get").and.returnValue({$promise: profileDeferred.promise});
        spyOn(stravaProfileResource, "get").and.returnValue({$promise: stravaProfileDeferred.promise});
        spyOn(authorizationResource, "get").and.returnValue({$promise: authorizationDeferred.promise});
        spyOn($http, "post").and.returnValue(signoutDeferred.promise);

        $controller('navbarController', {
            $scope: $scope,
            $http: $http,
            $window: $window,
            stravaProfileResource: stravaProfileResource,
            profileResource: profileResource,
            authorizationResource: authorizationResource
        });
    }));

    it('sets stravaProfile', function () {
        stravaProfileDeferred.resolve("stravaProfile");

        $scope.$apply();
        expect($scope.stravaProfile).toEqual("stravaProfile");
    });

    it('sets profile', function () {
        profileDeferred.resolve("profile");

        $scope.$apply();
        expect($scope.profile).toEqual("profile");
    });

    it('sets admin', function () {
        authorizationDeferred.resolve({admin: true});

        $scope.$apply();
        expect($scope.admin).toEqual(true);
    });

    describe('signout', function () {
        it('signs the user out and redirects the user', function () {
            $scope.signout();

            expect($http.post).toHaveBeenCalledWith('/signout', {});

            signoutDeferred.resolve();
            $scope.$apply();

            expect($window.location.href).toEqual('/');
        });
    });
});