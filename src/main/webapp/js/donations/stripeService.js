(function () {
    angular.module('donationEvents').service('stripeService', ['$q', 'stripePublicKey', 'stripe', function ($q, stripePublicKey, stripe) {
        stripe.setPublishableKey(stripePublicKey);

        this.getToken = function (cardData) {
            var deferred = $q.defer();

            stripe.card.createToken(cardData, function (status, response) {
                if (response.error) {
                    deferred.reject(response.error.message);
                } else {
                    deferred.resolve(response.id);
                }
            });

            return deferred.promise;
        };
    }]);
})();