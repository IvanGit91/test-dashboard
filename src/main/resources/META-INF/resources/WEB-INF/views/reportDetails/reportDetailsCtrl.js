angular.module('mainApp').controller("ReportDetailsCtrl", ReportDetailsCtrl);

function ReportDetailsCtrl($rootScope, $scope, $window, $location, $http) {

    $scope.title = "Report Details";

    $(function () {
        $scope.testInfo = $rootScope.report;
        $http({
            method: 'GET',
            url: '/reportDetails/' + $rootScope.report.id
        }).then(function (response) {
            console.log(response.data);
            if (response.data.length == 0) {
            } else {
                $scope.testDetails = response.data;
                console.log($scope.reportDetails);
            }
        }, function (err) {
            console.log('Oh no! An error in get report details!');
        });

    });

    $scope.goPrevious = function () {
        $location.path('/testresult');
    };
}
