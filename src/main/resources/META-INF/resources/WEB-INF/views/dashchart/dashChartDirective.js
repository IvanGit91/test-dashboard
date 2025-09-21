angular.module('mainApp').directive("dashChart", dashChart);

function dashChart() {
    return {
        restrict: 'E',
        controller: 'DashChartCtrl',
        scope: {
            title: "=",
            data: "=",
            widthreference: '='
        },
        templateUrl: 'dash_chart'
    };
}