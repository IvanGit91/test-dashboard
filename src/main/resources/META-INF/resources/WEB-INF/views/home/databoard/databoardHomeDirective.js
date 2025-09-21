angular.module('mainApp').directive("databoardHome", databoardHome);

function databoardHome() {
    return {
        restrict: 'E',
        controller: 'DataboardHomeCtrl',
        scope: {
            title: "=",
            data: "="
        },
        templateUrl: 'databoard_home'
    };
}