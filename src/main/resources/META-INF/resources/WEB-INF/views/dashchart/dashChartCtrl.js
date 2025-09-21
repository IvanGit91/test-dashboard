angular.module('mainApp').controller("DashChartCtrl", DashChartCtrl);

function DashChartCtrl($scope, $rootScope, $window, $location, $http, NgTableParams) {
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

    $scope.rundata = [
        2, 2, 3,
        ["passato1", "passato2", "passato3", "passato4", "passato5"], ["fallito1", "fallito2", "fallito3", "fallito4"], ["running1", "not-exec1", "not-exec2"]
    ];

    $(function () {
    });

    $scope.vaiModale = function (clData, name) {
        $scope.dataModal = [];
        for (var i = 0; i < clData[0].length; i++) {
            var obj = new Object();
            obj.name = clData[0][i].name;
            obj.result = 'PASSED';
            $scope.dataModal[$scope.dataModal.length] = obj;
        }
        for (var i = 0; i < clData[1].length; i++) {
            var obj = new Object();
            obj.name = clData[1][i].name;
            obj.result = 'FAILED';
            $scope.dataModal[$scope.dataModal.length] = obj;
        }
        for (var i = 0; i < clData[2].length; i++) {
            var obj = new Object();
            obj.name = clData[2][i].name;
            obj.result = 'RunOrNotExec';
            $scope.dataModal[$scope.dataModal.length] = obj;
        }

        $rootScope.tableParamsModal = new NgTableParams({
            page: 1,
            count: 10,
            filter: {
                result: ""
            }
        }, {
            dataset: $scope.dataModal
        });
        showModalOnly("User", name);
    }
}