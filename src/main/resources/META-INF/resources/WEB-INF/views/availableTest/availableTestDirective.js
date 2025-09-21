angular.module('mainApp').directive("availableTest", availableTest);

function availableTest() {
    return {
        restrict: 'E',
        controller: 'AvailableTestCtrl',
        scope: {
            title: "=",
            type: "=",
            data: "="
        },
        templateUrl: 'availableTest'
    };
}