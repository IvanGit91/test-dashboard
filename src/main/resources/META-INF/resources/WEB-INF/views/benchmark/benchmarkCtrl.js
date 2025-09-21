angular.module('mainApp').controller("BenchmarkCtrl", BenchmarkCtrl)
    .filter('myFormat', function () {
        return function (x) {
            console.log("DATA: " + x);
            var i, c, txt = "";
            for (i = 0; i < x.length; i++) {
                c = x[i];
                if (i % 2 == 0) {
                    c = c.toUpperCase();
                }
                txt += c;
            }
            return txt;
        };
    });

function BenchmarkCtrl($rootScope, $scope, $window, $location, $http, NgTableParams, $filter, $q, $timeout) {
    var scope = $scope;
    $scope.title = "Benchmark";
    scope.isResult = false;
    $scope.isRendered = false;
    scope.semaforo = 0;
    $(function () {
        scope.reload();
    });

    scope.reload = function () {
    };

    function getTargets() {
        $scope.semaforo++;
        $http({
            method: 'GET',
            url: '/targets/' + $rootScope.project.id
        }).then(function (response) {
            console.log(response.data);
            var targets = response.data;
            scope.numTargets = response.data.length;
            for (var j in targets) {
                getTestsPerformance(targets[j].id);
            }
            $scope.finishRender = true;
            $scope.semaforo--;
        }, function (err) {
            console.log('Oh no! An error in targets!');
            $scope.finishRender = true;
            $scope.semaforo--;
        });
    }

    function getTestsPerformance(targetId) {
        if ($rootScope.project == undefined || $rootScope.project.id == null) {
            console.log("No Target")
            $scope.errors = "No target selected";
        } else {
            $scope.semaforo++;
            $http({
                method: 'GET',
                url: '/testsPerformance/' + $rootScope.project.id + "/" + targetId
            }).then(function (response) {
                scope.semaforo--;
                scope.iterTargets++;
                if (response.data.length === 0) {
                    $scope.errors = "No statistics found";
                    $scope.isRendered = false;
                    $scope.isResult = true;
                } else if (response.data.length > 0) {
                    $scope.errors = undefined;
                    $scope.modelJson = response;
                    $scope.statList = [];
                    $scope.s1 = [];
                    for (var i = 0, n = 0; n < $scope.modelJson.data.length; i++, n++) {

                        $scope.statList[i] = {};
                        $scope.statList[i].testName = $scope.modelJson.data[n].testName;
                        $scope.statList[i].attempt = $scope.modelJson.data[n].testsRepeat.length;
                        $scope.arrayAverage = 0;
                        $scope.arrayCountPerformance = 0;
                        $scope.arrayStatus = 0;
                        $scope.arrayCountStatus = 0;
                        $scope.arrayMin = undefined;
                        $scope.arrayMax = undefined;
                        for (var j = 0; j < $scope.modelJson.data[n].testsRepeat.length; j++) {
                            if ($scope.modelJson.data[n].testsRepeat[j].reportStatus != null) {
                                if ($scope.modelJson.data[n].testsRepeat[j].reportStatus == "PASS") {
                                    $scope.arrayStatus = $scope.arrayStatus + 100;
                                }
                                $scope.arrayCountStatus = $scope.arrayCountStatus + 1;
                            }

                            key = 'performance';
                            if ($scope.modelJson.data[n].testsRepeat[j].statistics != null && $scope.modelJson.data[n].testsRepeat[j].statistics[key] != null) {
                                $scope.arrayMin1 = undefined;
                                for (var k = 0; k < $scope.modelJson.data[n].testsRepeat[j].statistics[key].length; k++) {
                                    if ($scope.arrayMin1 == undefined || $scope.arrayMin1 > $scope.modelJson.data[n].testsRepeat[j].statistics[key][k]) {
                                        $scope.arrayMin1 = $scope.modelJson.data[n].testsRepeat[j].statistics[key][k];
                                    }
                                    if ($scope.arrayMin == undefined || $scope.arrayMin > $scope.arrayMin1) {
                                        $scope.arrayMin = $scope.arrayMin1;
                                    }
                                }
                                $scope.arrayMax1 = undefined;
                                for (var k = 0; k < $scope.modelJson.data[n].testsRepeat[j].statistics[key].length; k++) {
                                    if ($scope.arrayMax1 == undefined || $scope.arrayMax1 < $scope.modelJson.data[n].testsRepeat[j].statistics[key][k]) {
                                        $scope.arrayMax1 = $scope.modelJson.data[n].testsRepeat[j].statistics[key][k];
                                    }
                                    if ($scope.arrayMax == undefined || $scope.arrayMax < $scope.arrayMax1) {
                                        $scope.arrayMax = $scope.arrayMax1;
                                    }
                                }
                                $scope.arrayAverage1 = 0;
                                for (var k = 0; k < $scope.modelJson.data[n].testsRepeat[j].statistics[key].length; k++) {
                                    $scope.arrayAverage1 = $scope.arrayAverage1 + $scope.modelJson.data[n].testsRepeat[j].statistics[key][k]
                                }
                                $scope.arrayAverage1 = $scope.arrayAverage1 / scope.modelJson.data[n].testsRepeat[j].statistics[key].length;
                                $scope.arrayAverage = $scope.arrayAverage + $scope.arrayAverage1;
                                $scope.arrayCountPerformance = $scope.arrayCountPerformance + 1;
                            }
                        }
                        $scope.statList[i].min = $scope.arrayMin;
                        if ($scope.statList[i].min == null) $scope.statList[i].min = '-';
                        $scope.statList[i].max = $scope.arrayMax;
                        if ($scope.statList[i].max == null) $scope.statList[i].max = '-';
                        if ($scope.arrayCountStatus != 0) {
                            $scope.statList[i].successRate = Math.round($scope.arrayStatus / $scope.arrayCountStatus) + '%';
                        }
                        if ($scope.statList[i].successRate == null) $scope.statList[i].successRate = '-';
                        if ($scope.arrayCountPerformance != 0) {
                            $scope.statList[i].average = Math.round(($scope.arrayAverage / $scope.arrayCountPerformance) * 100) / 100;
                        }
                        if ($scope.statList[i].average == null) $scope.statList[i].average = '-';
                        addUnique($scope.s1, response.data[n].testName);
                        if ($scope.statList[i].average != '-') {
                            i++;
                            $scope.statList[i] = {};
                            $scope.statList[i].testName = $scope.statList[i - 1].testName + ' - (TIM)';
                            $scope.statList[i].attempt = '100';
                            var random = Math.random();
                            $scope.statList[i].average = $scope.statList[i - 1].average + Math.floor(random * $scope.statList[i - 1].average);
                            $scope.statList[i].min = $scope.statList[i - 1].min + Math.floor(random * $scope.statList[i - 1].min);
                            $scope.statList[i].max = $scope.statList[i - 1].max + Math.floor(random * $scope.statList[i - 1].max);
                            i++;
                            $scope.statList[i] = {};
                            $scope.statList[i].testName = $scope.statList[i - 2].testName + ' - (WIND)';
                            $scope.statList[i].attempt = '100';
                            var random2 = Math.random();
                            $scope.statList[i].average = $scope.statList[i - 2].average + Math.floor(random2 * $scope.statList[i - 2].average);
                            $scope.statList[i].min = $scope.statList[i - 2].min + Math.floor(random2 * $scope.statList[i - 2].min);
                            $scope.statList[i].max = $scope.statList[i - 2].max + Math.floor(random2 * $scope.statList[i - 2].max);
                        }
                    }
                    $scope.isResult = true;
                    $scope.isRendered = true;
                    $scope.testName = $scope.s1;
                    $scope.cols = [
                        {
                            field: "testName",
                            title: "Test Name",
                            filter: {testName: "select"},
                            filterData: getTestName,
                            show: true
                        },
                        {field: "attempt", title: "Attempts", show: true},
                        {field: "successRate", title: "Success Rate", show: true},
                        {field: "average", title: "Performance (ms)", show: true},
                        {field: "min", title: "Min (ms)", show: true},
                        {field: "max", title: "Max (ms)", show: true}
                    ];
                    $scope.tableParams = new NgTableParams({
                        page: 1,            // show first page
                        count: 10           // count per page
                    }, {
                        // dataset: $scope.modelJson.data
                        dataset: $scope.statList
                    });
                }
            }, function (err) {
                console.log('Oh no! An error in suts!');
                $scope.finishRender = true;
                $scope.semaforo--;
            });
        }
    }

    function addUnique(arr, str) {

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

    function getTestName() {
        return $q.when($scope.testName);
    }

    $scope.downloadCsv = function (csv, $event, name) {
        $scope.semaforo++;
        $(".pageView").css('overflow', 'hidden');
        $timeout(function () {
            csv.generate($event, name, $scope.tableParams, $scope);
        }, 100, false);
    }
}
