angular.module('mainApp').controller("ClusterDetailCtrl", ClusterDetailCtrl);

function ClusterDetailCtrl($rootScope, $scope, $window, $location, $http, NgTableParams) {
    console.log("ciao");
    $scope.title = "Cluster Detail";

    $scope.data = [
        {uid: 'User 11', name: 'Name 11', area: 'Area 1'},
        {uid: 'User 12', name: 'Name 12', area: 'Area 1'},
        {uid: 'User 21', name: 'Name 21', area: 'Area 2'},
        {uid: 'User 22', name: 'Name 22', area: 'Area 2'}
    ];
    $scope.tableParams = new NgTableParams({
        page: 1,
        count: 10,
    }, {
        dataset: $scope.data
    });

}