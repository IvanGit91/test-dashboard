angular.module('mainApp').controller("ChartCtrl", ChartCtrl);

function ChartCtrl($scope, $window, $location, $http) {

    $scope.run = {
        "data": [2, 4, 5, 50, 10],
        "colors": ["#33cc33", "#ff0000", "#808080", "#0033cc", "#767b00"],
        "labels": ["A", "B", "C", "D", "E"],
        "options": {
            legend: {
                display: true,
                position: "right",
                labels: {
                    // fontSize: 12
                }
            },
            title: {
                display: true,
                text: 'RUN',
                fontSize: 16
            }
        }
    };

    $scope.passed = {
        "data": [2, 4, 5, 50, 10],
        "colors": ["#33cc33", "#ff0000", "#808080", "#0033cc", "#767b00"],
        "labels": ["A", "B", "C", "D", "E"],
        "options": {
            legend: {
                display: true,
                position: "right",
                labels: {
                    // fontSize: 12
                }
            },
            title: {
                display: true,
                text: 'PASSED',
                fontSize: 16
            }
        }
    };

    $scope.blocked = {
        "data": [28, 23, 3, 55, 12],
        "colors": ["#33cc33", "#ff0000", "#808080", "#0033cc", "#767b00"],
        "labels": ["A", "B", "C", "D", "E"],
        "options": {
            legend: {
                display: true,
                position: "right",
                labels: {
                    // fontSize: 12
                }
            },
            title: {
                display: true,
                text: 'BLOCKED',
                fontSize: 16
            }
        }
    };

    $scope.defect = {
        "data": [28, 23, 3, 55, 12],
        "colors": ["#33cc33", "#ff0000", "#808080", "#0033cc", "#767b00"],
        "labels": ["A", "B", "C", "D", "E"],
        "options": {
            legend: {
                display: true,
                position: "right",
                labels: {
                    // fontSize: 12
                }
            },
            title: {
                display: true,
                text: 'DEFECT',
                fontSize: 16
            }
        }
    };
}