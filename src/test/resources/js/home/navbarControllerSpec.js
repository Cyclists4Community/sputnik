describe('navbarController', function () {
    var $scope, profileRepository, authorizationResource, profileDeferred, authorizationDeferred, signoutDeferred, $http, $window;

    beforeEach(module('navbar'));

    beforeEach(inject(function ($rootScope, $q, _$http_, $controller, _profileRepository_, _authorizationResource_) {
        $scope = $rootScope.$new();
        $http = _$http_;
        $window = {location: {}};
        profileRepository = _profileRepository_;
        authorizationResource = _authorizationResource_;

        profileDeferred = $q.defer();
        signoutDeferred = $q.defer();
        authorizationDeferred = $q.defer();

        spyOn(profileRepository, "get").and.returnValue({$promise: profileDeferred.promise});
        spyOn(authorizationResource, "get").and.returnValue({$promise: authorizationDeferred.promise});
        spyOn($http, "post").and.returnValue(signoutDeferred.promise);

        $controller('navbarController', {
            $scope: $scope,
            $http: $http,
            $window: $window,
            profileRepository: profileRepository,
            authorizationResource: authorizationResource
        });
    }));

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