angular.module('mainApp').directive("chart", chart);

function chart() {
    return {
        restrict: 'E',
        controller: 'ChartCtrl',
        scope: {},
        templateUrl: 'chart'
    };
}