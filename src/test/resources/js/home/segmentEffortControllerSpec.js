describe('segmentEffortController', function () {
    var segmentEffortsRepository, segmentEffortsDeferred;

    beforeEach(module('sputnikControllers'));

    beforeEach(inject(function ($rootScope, $q, $routeParams, $controller, _segmentEffortsRepository_) {
        $scope = $rootScope.$new();
        segmentEffortsRepository = _segmentEffortsRepository_;

        $routeParams.segmentEffortId = '17';

        segmentEffortsDeferred = $q.defer();
        spyOn(segmentEffortsRepository, "get").and.returnValue({$promise: segmentEffortsDeferred.promise});

        $controller('segmentEffortController', {$scope: $scope, $routeParams: $routeParams});
    }));

    it('sets segmentEffort', function () {
        segmentEffortsDeferred.resolve("segmentEffort");

        $scope.$apply();
        expect($scope.segmentEffort).toEqual("segmentEffort");
        expect(segmentEffortsRepository.get).toHaveBeenCalledWith({segmentEffortId: '17'});
    });
});