angular.module('mainApp').controller("MainCtrl", MainCtrl);

function MainCtrl($rootScope, $scope, $window, $location, $http) {
    $(function () {

    });

    $scope.check = function (arr) {
        console.log(arr);
    }
    $scope.results = ['PASSED', 'FAILED', 'RunOrNotExec'];
}
