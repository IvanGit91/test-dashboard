angular.module("mainApp").config(function ($routeProvider) {
    $routeProvider
        .when("/home", {
            templateUrl: "home"
        })
        .when("/environments", {
            templateUrl: "environments"
        })
        .when("/testresult", {
            templateUrl: "testresult"
        })
        .when("/statistics", {
            templateUrl: "statistics"
        })
        .when("/benchmark", {
            templateUrl: "benchmark"
        })
        .when("/reportDetails", {
            templateUrl: "reportDetails"
        })
});