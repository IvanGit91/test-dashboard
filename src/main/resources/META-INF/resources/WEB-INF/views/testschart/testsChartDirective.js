angular.module('mainApp').directive("testsChart", testsChart);

function testsChart() {
    return {
        restrict: 'E',
        controller: 'TestsChartCtrl',
        scope: {
            title: "=",
            data: "="
        },
        templateUrl: 'tests_chart'
    };
}