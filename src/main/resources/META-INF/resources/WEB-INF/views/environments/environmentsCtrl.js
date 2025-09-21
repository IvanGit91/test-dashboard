angular.module('mainApp').controller("EnvironmentsCtrl", EnvironmentsCtrl);
//     .filter('unique',function () {
//     return function (collection,keyname) {
//         var output = [],
//             keys = [];
//         angular.forEach(collection,function(item){
//             var key = item[keyname];
//             if (keys.indexOf(key) === -1){
//                 keys.push(key);
//                 output.push(item);
//             }
//         });
//         return output;
//     };
// });

function EnvironmentsCtrl($scope, $window, $location, $http, $filter, NgTableParams, $q) {

    $scope.title = "Environments";
    $scope.semaforo = 0;
    $scope.isRendered = false;
    $(function () {
        $scope.getAvailableSuts();
    });

    // $scope.testdata = function (done,total) {
    //     if (done == 0){
    //         return [
    //             {
    //                 "num": [total]
    //             }
    //         ];
    //     } else {
    //         return [
    //             {
    //                 "num": [done,total]
    //             }
    //         ];
    //     }
    // }

    $scope.getAvailableSuts = function () {
        $scope.semaforo++;
        $http({
            method: 'GET',
            url: '/availablesuts/'
        }).then(function (response) {
            console.log(response);
            // $scope.planningList = [];
            $scope.s1 = [];
            $scope.s2 = [];
            $scope.s3 = [];
            $scope.s4 = [];
            $scope.s5 = [];
            $scope.s6 = [];
            $scope.s7 = [];
            $scope.s9 = [];
            for (var i = 0; i < response.data.length; i++) {
                if (response.data[i].componentType === "mi" || response.data[i].componentType === "tau") {

                    addUnique($scope.s1, response.data[i].sutName);
                    if (response.data[i].componentType === "tau") {
                        response.data[i].componentType = "STB";
                    } else if (response.data[i].componentType === "mi") {
                        response.data[i].componentType = "Mobile";
                    }
                    addUnique($scope.s2, response.data[i].componentType);
                    response.data[i].details = JSON.parse(response.data[i].details);
                    if (response.data[i].details != null) {
                        response.data[i].firmwareVersion = response.data[i].details.firmwareVersion;
                        response.data[i].connectionType = response.data[i].details.connectionType;
                        response.data[i].backendEnvironment = response.data[i].details.backendEnvironment;
                        if (response.data[i].backendEnvironment == "Prod") {
                            response.data[i].backendEnvironment = "Prod "
                        }
                        response.data[i].provisioning = response.data[i].details.provisioning;
                        response.data[i].site = response.data[i].details.site;
                        response.data[i].idSerialNumber = response.data[i].details.idSerialNumber;
                    } else {
                        response.data[i].firmwareVersion = '-';
                        response.data[i].connectionType = '-';
                        response.data[i].backendEnvironment = '-';
                        response.data[i].provisioning = '-';
                        response.data[i].site = '-';
                        response.data[i].idSerialNumber = '-';
                    }
                    addUnique($scope.s3, response.data[i].firmwareVersion);
                    addUnique($scope.s4, response.data[i].connectionType);
                    addUnique($scope.s5, response.data[i].backendEnvironment);
                    addUnique($scope.s6, response.data[i].provisioning);
                    addUnique($scope.s7, response.data[i].site);
                    addUnique($scope.s9, response.data[i].idSerialNumber);
                } else {
                    response.data.splice(i, 1);
                    i--;
                }
            }

            $scope.sutName = $scope.s1;
            $scope.componentType = $scope.s2;
            $scope.firmwareVersion = $scope.s3;
            $scope.connectionType = $scope.s4;
            $scope.backendEnvironment = $scope.s5;
            $scope.provisioning = $scope.s6;
            $scope.site = $scope.s7;
            $scope.idSerialNumber = $scope.s9;

            $scope.newData = response.data;
            $scope.cols = [
                {field: "sutName", title: "DUT Name", filter: {sutName: "select"}, filterData: getSutName, show: true},
                {
                    field: "idSerialNumber",
                    title: "Device ID / Serial Number",
                    filter: {idSerialNumber: "select"},
                    filterData: getSerialNumber,
                    show: true
                },
                {
                    field: "componentType",
                    title: "Component Type",
                    filter: {componentType: "select"},
                    filterData: getComponentType,
                    show: true
                },
                {
                    field: "firmwareVersion",
                    title: "Firmware Version",
                    filter: {firmwareVersion: "select"},
                    filterData: getFirmwareVersion,
                    show: true
                },
                {
                    field: "connectionType",
                    title: "Connection Type",
                    filter: {connectionType: "select"},
                    filterData: getConnectionType,
                    show: true
                },
                {
                    field: "backendEnvironment",
                    title: "Backend Environment",
                    filter: {backendEnvironment: "select"},
                    filterData: getBackendEnvironment,
                    show: true
                },
                {
                    field: "provisioning",
                    title: "Provisioning",
                    filter: {provisioning: "select"},
                    filterData: getProvisioning,
                    show: true
                },
                {field: "site", title: "Site", filter: {site: "select"}, filterData: getSite, show: true}
            ];
            $scope.tableParams2 = new NgTableParams({
                page: 1,
                count: 10,
            }, {
                counts: [10, 25, 50, 100],
                dataset: response.data
            });

            // availableTests($scope.planningList);
            $scope.isRendered = true;
            $scope.semaforo--;
        }, function (err) {
            console.log('Oh no! An error in suts!');
            $scope.isRendered = true;
            $scope.semaforo--;
        });
    }

    function addUnique(arr, str) {
        if (str != undefined) {
            var found = false;
            for (var n = 0; n < arr.length; n++) {
                if (arr[n].id == str.toUpperCase()) {
                    found = true;
                }
            }

            if (found == false) {
                var obj = new Object();
                obj.id = str.toUpperCase();
                obj.title = str;
                arr[arr.length] = obj;
            }
        }
    }

    function getSutName() {
        return $q.when($scope.sutName);
    }

    function getSerialNumber() {
        return $q.when($scope.idSerialNumber);
    }

    function getComponentType() {
        return $q.when($scope.componentType);
    }

    function getFirmwareVersion() {
        return $q.when($scope.firmwareVersion);
    }

    function getConnectionType() {
        return $q.when($scope.connectionType);
    }

    function getBackendEnvironment() {
        return $q.when($scope.backendEnvironment);
    }

    function getProvisioning() {
        return $q.when($scope.provisioning);
    }

    function getSite() {
        return $q.when($scope.site);
    }
}