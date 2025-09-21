angular.module('mainApp').controller("DataboardHomeCtrl", DataboardHomeCtrl);

function DataboardHomeCtrl($scope, $window, $location, $http, homeToProjectsBoardService) {

    $scope.goToProjectBoard = function (container, projects) {
        homeToProjectsBoardService.setProjects(projects);
        homeToProjectsBoardService.setTitleRoute($scope.title + " > " + container.name);
        $location.path("projects_board");
    };

    $scope.calculateStatus = function () {
        var status = true;
        angular.forEach($scope.data, function (value, key) {
            if (!value.status)
                status = false;
        });
        return status;
    };

    $(function () {
        console.log($scope);
    });
}