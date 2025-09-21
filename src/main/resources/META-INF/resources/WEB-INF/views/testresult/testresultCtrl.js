angular.module('mainApp').directive('exportToCsv', ExportToCsv).controller("TestresultCtrl", TestresultCtrl);

function ExportToCsv() {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            function stringify(str) {
                return '"' + str.replace(/^\s\s*/, '').replace(/\s*\s$/, '').replace(/"/g, '""') + '"';
            }

            var el = element[0];
            element.bind('click', function (e) {
                var table = e.target.nextElementSibling;
                var csvString = '';
                for (var i = 3; i < table.rows.length; i++) {
                    var rowData = table.rows[i].cells;
                    for (var j = 0; j < rowData.length; j++) {
                        if (rowData[j].innerHTML.indexOf('<a href') > -1) {
                            var ele = angular.element(rowData[j]);
                            for (var k = 0; k < ele[0].childNodes.length; k++) {
                                if (ele[0].childNodes[k].tagName == 'A') {
                                    csvString = csvString + stringify(ele[0].childNodes[k].innerText) + ",";
                                }
                            }
                        } else if (rowData[j].innerHTML.indexOf('<em') > -1 || rowData[j].innerHTML.indexOf('<strong>') > -1) {
                            var ele = angular.element(rowData[j]);
                            csvString = csvString + stringify(ele[0].textContent) + ",";
                        } else {
                            csvString = csvString + stringify(rowData[j].innerHTML) + ",";
                        }
                    }
                    csvString = csvString.substring(0, csvString.length - 1);
                    csvString = csvString + "\n";
                }
                console.log(csvString);
                csvString = csvString.substring(0, csvString.length - 1);
                // alert(csvString);
                var a = $('<a/>', {
                    style: 'display:none',
                    href: 'data:application/csv;base64,' + btoa(csvString),
                    download: 'emailStatistics.csv'
                }).appendTo('body');
                a[0].click();
                a.remove();
            });
        }
    }
}

function TestresultCtrl($rootScope, $scope, $window, $location, $http, $filter, NgTableParams, ngTableEventsChannel, $q, $timeout, FileSaver, Blob) {
    var scope = $scope;
    scope.title = "Test Result";
    scope.isResult = false;
    $scope.isRendered = false;
    scope.semaforo = 0;
    $scope.rows = [];
    $scope.selectVar = false;

    $(function () {
        scope.reload();
    });

    scope.reload = function () {
        $scope.selectVar = false;
        $scope.iterTargets = 0;
        $scope.reports = [];
        $scope.selected = [];
        $scope.s1 = [];
        $scope.s2 = [];
        $scope.s3 = [];
        $scope.s4 = [];
        $scope.s5 = [];
        $scope.isResult = false;
        if ($rootScope.project === undefined || $rootScope.project === null) {
            $scope.errors = "No project and/or target selected";
        } else if ($rootScope.project !== undefined && $rootScope.target === undefined || $rootScope.target === null) {
            $scope.projectName = $rootScope.project.name;
            getTargets();
        } else {
            $scope.projectName = $rootScope.project.name;
            $scope.targetName = $rootScope.target.name;
            scope.numTargets = 1;
            getReports($rootScope.target.id);
        }
    };

    function getReports(targetId) {
        if ($rootScope.project == undefined || $rootScope.project.id == null) {
            console.log("No Target or project")
            $scope.errors = "No project and/or target selected";
        } else {
            scope.semaforo++;
            $http({
                method: 'GET',
                url: '/reports/' + $rootScope.project.id + "/" + targetId
            }).then(function (response) {
                scope.semaforo--;
                scope.iterTargets++;
                console.log(response.data);
                if (scope.iterTargets === scope.numTargets && response.data.length === 0 && $scope.reports.length === 0) {
                    $scope.errors = "No reports found";
                } else if (response.data.length > 0) {


                    for (var i = 0; i < response.data.length; i++) {
                        response.data[i].selected = false;

                        if (response.data[i].status + '' === "FAIL") {
                            response.data[i].status = "FAILED"
                        }
                        if (response.data[i].status + '' === "PASS") {
                            response.data[i].status = "PASSED"
                        }
                        if (response.data[i].status + '' === "RUNNING") {
                            response.data.splice(i, 1);
                            i--;
                        }
                        addUnique($scope.s1, response.data[i].sut);
                        addUnique($scope.s2, response.data[i].name);
                        addUnique($scope.s3, response.data[i].typeTarget);
                        addUnique($scope.s4, response.data[i].status);

                    }


                    if ($scope.reports === undefined) {
                        $scope.reports = response.data;
                    } else {
                        $scope.reports = $scope.reports.concat(response.data);
                    }
                    $scope.isResult = true;
                    if (scope.iterTargets === scope.numTargets) {
                        $scope.sut = $scope.s1;
                        $scope.name = $scope.s2;
                        $scope.typeTarget = $scope.s3;
                        $scope.status = $scope.s4;
                        $scope.cluster = $scope.s5;
                        $scope.isRendered = true;
                        $scope.rows = $scope.reports;
                        $scope.cols = [
                            {field: "selected", title: "Select", filter: {}, show: true},
                            {field: "sut", title: "DUT Name", filter: {sut: "select"}, filterData: getSut, show: true},
                            {
                                field: "name",
                                title: "Test Name",
                                filter: {name: "select"},
                                filterData: getName,
                                show: true
                            },
                            {
                                field: "typeTarget",
                                title: "Test Type",
                                filter: {typeTarget: "select"},
                                filterData: getTypeTarget,
                                show: true
                            },
                            {
                                field: "status",
                                title: "Test Status",
                                filter: {status: "select"},
                                filterData: getStatus,
                                show: true
                            },
                            // { field: "cluster", title: "Cluster", filter: { cluster: "select" }, filterData: getCluster, show: true },
                            {field: "actions", title: "Actions", filter: {}, show: true}
                        ];
                        $scope.cols[0].filter.Select = "ng-table/headers/checkbox.html";
                        // $scope.cols[5].filter.Actions = "ng-table/headers/button.html";

                        $scope.tableParams3 = new NgTableParams({
                            page: 1,
                            count: 10
                        }, {
                            dataset: $scope.reports
                        });
                        ngTableEventsChannel.onAfterReloadData(function () {
                            $scope.setVar($scope.tableParams3);
                        });
                        // ngTableEventsChannel.onAfterDataFiltered(function($scope.tableParams3, filteredData){
                        //     //DO SOMETHING
                        //     console.log("ciao!!");
                        // });

                    }
                }
            }, function (err) {
                console.log('Oh no! An error in get Reports!');
                scope.iterTargets++;
                $scope.semaforo--;
            });
        }
    }

    $scope.setVar = function (tableparams) {
        var count = 0;
        for (var i = 0; i < tableparams.data.length; i++) {
            if (tableparams.data[i].selected == true) {
                count++;
            }
        }
        if (count == tableparams.data.length) {
            $scope.selectVar = true;
            $('#selectAllCheckbox').prop('checked', true);
        } else {
            $scope.selectVar = false;
            $('#selectAllCheckbox').prop('checked', false);
        }
    };

    $scope.selectAll = function (tableparams) {
        $scope.selectVar = !$scope.selectVar;
        console.log("selectAll");
        for (var i = 0; i < tableparams.data.length; i++) {
            if (tableparams.data[i].status != 'RUNNING') {
                tableparams.data[i].selected = $scope.selectVar;
            }
        }
    };
    var zip;
    var toDownload;
    var added;
    $scope.checkSelected = function () {
        var count = 0;
        for (var i = 0; i < $scope.rows.length; i++) {
            if ($scope.rows[i].selected == true) {
                count++;
            }
        }
        return (count === 0);
    };
    $scope.downloadButton = function () {
        console.log("downloadButton");
        zip = new JSZip();
        toDownload = [];
        added = 0;
        for (var i = 0; i < $scope.rows.length; i++) {
            if ($scope.rows[i].selected == true) {
                toDownload[toDownload.length] = $scope.rows[i];
            }
        }
        if (toDownload.length != 0) {
            $scope.addToZip(toDownload[0]);
        }
    };
    $scope.resetChecked = function () {
        for (var i = 0; i < $scope.rows.length; i++) {
            $scope.rows[i].selected = false;
        }
        $scope.selectVar = false;
        $('#selectAllCheckbox').prop('checked', false);
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

    function getSut() {
        return $q.when($scope.sut);
    }

    function getName() {
        return $q.when($scope.name);
    }

    function getTypeTarget() {
        return $q.when($scope.typeTarget);
    }

    function getStatus() {
        return $q.when($scope.status);
    }

    function getCluster() {
        return $q.when($scope.cluster);
    }

    $scope.addToZip = function (row) {
        row.projectName = $rootScope.project.name;
        scope.semaforo++;
        $http({
            method: 'POST',
            url: '/reportDetailToPdf',
            data: JSON.stringify(row),
            dataType: 'json',
            responseType: 'arraybuffer',
            headers: {
                "content-type": 'application/json'
            }
        }).then(function (response) {
            if (response.data.length == 0) {
                console.log("REPORT DETAILS NO ELEMENTS");
            } else {
                $scope.reportDetails = response.data;
                var file = new Blob([response.data], {type: 'application/pdf'});
                zip.file($rootScope.project.name + "_" + row.vfName + "_" + row.name + ".pdf", file);
                added += 1;
                if (added == toDownload.length) {
                    $scope.resetChecked();
                    zip.generateAsync({type: "blob"})
                        .then(function (blob) {
                            FileSaver.saveAs(blob, "Reports.zip");
                            // var a = document.createElement("a");
                            // a.href="data:application/zip;base64," + content;
                            // a.download = "Reports.zip";
                            // a.click();
                        });
                } else {
                    $scope.addToZip(toDownload[added]);
                }
                scope.semaforo--;
            }
        }, function (err) {
            console.log('Oh no! An error in get download zip!');
            $scope.semaforo--;
        });
    };
    $scope.reportToPdf = function (row) {
        row.projectName = $rootScope.project.name;
        scope.semaforo++;
        $http({
            method: 'POST',
            url: '/reportDetailToPdf',
            data: JSON.stringify(row),
            dataType: 'json',
            responseType: 'arraybuffer',
            headers: {
                "content-type": 'application/json'
            }
        }).then(function (response) {
            if (response.data.length == 0) {
                console.log("REPORT DETAILS NO ELEMENTS");
            } else {
                $scope.reportDetails = response.data;
                var file = new Blob([response.data], {type: 'application/pdf'});
                FileSaver.saveAs(file, $rootScope.project.name + "_" + row.vfName + "_" + row.name + ".pdf");
                // var fileURL = URL.createObjectURL(file);
                // var a = document.createElement("a");
                // a.href = fileURL;
                // a.download = $rootScope.project.name + "_" + row.vfName + "_" + row.name+".pdf";
                // a.click();
                scope.semaforo--;
            }
        }, function (err) {
            console.log('Oh no! An error in get download pdf!');
            $scope.semaforo--;
        });
    };

    $scope.goToReportDetails = function (row) {
        console.log("ID REPORT " + row.idReport);
        $rootScope.report = row;
        $location.path('/reportDetails');
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
                getReports(targets[j].id);
            }
            $scope.finishRender = true;
            $scope.semaforo--;
        }, function (err) {
            console.log('Oh no! An error in targets!');
            $scope.finishRender = true;
            $scope.semaforo--;
        });
    }

    $scope.downloadCsv = function (csv, $event, name) {
        $scope.semaforo++;
        $(".pageView").css('overflow', 'hidden');
        $timeout(function () {
            csv.generate($event, name, $scope.tableParams3, $scope);
        }, 100, false);
    }
}