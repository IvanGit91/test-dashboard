angular.module('mainApp').controller("HomeCtrl", HomeCtrl);

function HomeCtrl($rootScope, $scope, $window, $location, $http, $timeout) {
    // Register a plugin that apply before to draw each chart
    Chart.plugins.register({
        beforeDraw: function (chart) {
            // Ensure that the element is in options with the centered text
            if (chart.config.options.elements.center) {
                //Get ctx from string
                var ctx = chart.chart.ctx;
                //Get options from the center object in options
                var centerConfig = chart.config.options.elements.center;
                var fontStyle = centerConfig.fontStyle || 'Arial';
                var txt = centerConfig.text;
                var color = centerConfig.color || '#000';
                var sidePadding = centerConfig.sidePadding || 20;
                var sidePaddingCalculated = (sidePadding / 100) * (chart.innerRadius * 2)
                //Start with a base font of 30px
                ctx.font = "40px " + fontStyle;

                //Get the width of the string and also the width of the element minus 10 to give it 5px side padding
                var stringWidth = ctx.measureText(txt).width;
                var elementWidth = (chart.innerRadius * 2) - sidePaddingCalculated;

                // Find out how much the font can grow in width.
                var widthRatio = elementWidth / stringWidth;
                var newFontSize = Math.floor(30 * widthRatio);
                var elementHeight = (chart.innerRadius * 2);

                // Pick a new font size so it will not be larger than the height of label.
                var fontSizeToUse = Math.min(newFontSize, elementHeight);

                //Set font settings to draw it correctly.
                ctx.textAlign = 'center';
                ctx.textBaseline = 'middle';
                var centerX = ((chart.chartArea.left + chart.chartArea.right) / 2);
                var centerY = ((chart.chartArea.top + chart.chartArea.bottom) / 2);
                ctx.font = fontSizeToUse + "px " + fontStyle;
                ctx.fillStyle = color;

                //Draw text in center
                ctx.fillText(txt, centerX, centerY);
            }
        }
    });

    $scope.title = "Monitoring & Test Centre";
    $scope.date = new Date();
    $scope.finishRender = false;
    $scope.semaforo = 0;

    // $rootScope.project = null;
    // $rootScope.target = null;
    $scope.selectedProject = undefined;
    $scope.selectedTarget = undefined;
    if ($rootScope.project !== undefined && $rootScope.project !== null) {
        $scope.selectedProject = $rootScope.project;
        if ($rootScope.target !== undefined && $rootScope.target !== null) {
            $scope.selectedTarget = $rootScope.target;
        }
    }

    $(function () {
        if ($scope.selectedProject !== undefined && $scope.selectedProject.id !== null) {
            getProjects();
            if ($scope.selectedTarget !== undefined && $scope.selectedTarget.id !== null) {
                getTargets(false);
                getDashboardStatistics($scope.selectedTarget.id, false);
            } else {
                getTargets(true);
            }
        } else {
            getProjects();
        }
    });
    $scope.getSomething = function () {
        if ($scope.selectedTarget != undefined) {
            $scope.getSelectedTarget();
        } else if ($scope.selectedProject != undefined) {
            $scope.getSelectedProject();
        } else {
            getProjects();
        }
    }
    // ComboBoxes
    $scope.getSelectedProject = function () {
        $scope.finishRender = false;
        if ($scope.selectedProject !== undefined) {
            // It makes available also to the other controllers
            $rootScope.project = $scope.selectedProject;
            getTargets(true);
        } else {
            console.log("SELECTED NO PROJECT");
            resetVars();
            $scope.targets = null;
            $scope.selectedTarget = undefined;
            $rootScope.project = null;
            $scope.finishRender = true;
        }
        //RECALCULATE DOT IN ANGULARCAROUSEL
    };

    $scope.getSelectedTarget = function () {
        $scope.finishRender = false;
        if ($scope.selectedTarget !== undefined) {
            resetVars();
            $rootScope.target = $scope.selectedTarget;
            getDashboardStatistics($scope.selectedTarget.id, false);
        } else {
            console.log("SELECTED NO TARGET");
            resetVars();
            $rootScope.target = null;
            $scope.finishRender = true;
            getTargets(true);
        }
        //RECALCULATE DOT IN ANGULARCAROUSEL
    };

    function getProjects() {
        $scope.semaforo++;
        $http({
            method: 'GET',
            url: '/projects/'
        }).then(function (response) {
            console.log(response.data);

            if (response.data.length == 0) {
                console.log("NO PROJECT FOUND");
                $scope.errors = "Connection Error";
            } else {
                $scope.errors = null;
                $scope.projects = response.data;
            }
            $scope.finishRender = true;
            $scope.semaforo--;
        }, function (err) {
            console.log('Oh no! An error in projects!');
            $scope.finishRender = true;
            $scope.semaforo--;
        });
    }

    function getTargets(flag) {
        $scope.semaforo++;
        $scope.noCluster = false;
        $scope.noBars = false;
        resetVars();
        $http({
            method: 'GET',
            url: '/targets/' + $scope.selectedProject.id
        }).then(function (response) {
            console.log(response.data);
            $scope.targets = response.data;
            if ($scope.targets.length === 0) {
                console.log("No targets");
                $scope.noBars = true;
                $scope.noCluster = true;
            } else {
                for (var i = 0; i < response.data.length; i++) {
                    $scope.targets[i].name = $scope.targets[i].name + ' v.' + $scope.targets[i].release;
                }
                $scope.finishRender = true;
                if (flag) {
                    summarizeAllTargets();
                }
            }
            $scope.semaforo--;
        }, function (err) {
            console.log('Oh no! An error in targets!');
            $scope.finishRender = true;
            $scope.semaforo--;
        });
    }

    function summarizeAllTargets() {
        var targets = $scope.targets;
        $scope.cardIter = 0;
        $scope.cardList = [];
        $scope.testFailed = 0;
        $scope.testPassed = 0;
        $scope.testTotal = 0;
        $scope.testRunning = 0;
        $scope.testNames = [];
        $scope.targetsProcessed = 0;
        $scope.targetsLength = $scope.targets.length;
        Object.keys(targets).forEach(function (k) {
            getDashboardStatistics(targets[k].id, true);
        });

    }

    function findTestName(tNames, target) {
        for (var i = 0; i < tNames.length; i++) {
            if (tNames[i] === target) {
                return i;
            }
        }
        return -1;
    }

    function getDashboardStatistics(targetId, flag) {
        $scope.semaforo++;
        $scope.noCluster = false;
        $scope.noBars = false;
        var level = 1;
        $http({
            method: 'GET',
            url: '/statistics/' + $scope.selectedProject.id + '/' + targetId + '/' + level
        }).then(function (response) {
            console.log(response.data);
            var statistics = response.data.clusters;
            if (statistics === undefined) {
                resetVars();
                console.log("NO DATA STATISTICS");
            } else {
                $scope.targetsProcessed++;
                if (flag) {
                    if (statistics.length == 0) {
                        // $scope.cardList2 = null;
                    } else {
                        Object.keys(statistics).forEach(function (k) {
                            var name = statistics[k].name,
                                percentualTest = 0;

                            if (name.length > 19) {
                                name = name.substr(0, 17) + '...';
                            }

                            var result = findTestName($scope.testNames, name);
                            if (result === -1) {

                                var tot = statistics[k].total;
                                percentualTest = Math.round((statistics[k].pass.length / tot) * 100);
                                if (tot != 0) {
                                    $scope.testNames[$scope.cardIter] = name;
                                    $scope.cardList[$scope.cardIter] = donutChartElement(statistics[k].pass, statistics[k].fail, statistics[k].noRun, statistics[k].total, name, "Success", "Failed", percentualTest, statistics[k].name);
                                    $scope.cardIter++;
                                }

                            } else {
                                var pass = $scope.cardList[result].data[0].dataC[0].concat(statistics[k].pass); //Passati
                                var fall = $scope.cardList[result].data[0].dataC[1].concat(statistics[k].fail); //Falliti
                                var tot = $scope.cardList[result].data[0].total + statistics[k].total;  //Totali
                                var rone = $scope.cardList[result].data[0].dataC[2].concat(statistics[k].noRun);
                                percentualTest = Math.round((pass.length / tot) * 100);
                                $scope.cardList[result] = donutChartElement(pass, fall, rone, tot, name, "Success", "Failed", percentualTest, statistics[k].name);

                            }

                        });

                        if ($scope.targetsProcessed === $scope.targetsLength) {
                            if ($scope.cardIter > 0) {
                                $scope.cardList2 = {};
                                $scope.cardList2["cardList"] = $scope.cardList;
                                $scope.update = 1 - $scope.update;
                            }
                        }
                    }

                    // Bar charts
                    $scope.testFailed += (response.data.reportGroup.FAIL == undefined) ? 0 : response.data.reportGroup.FAIL;
                    $scope.testPassed += (response.data.reportGroup.PASS == undefined) ? 0 : response.data.reportGroup.PASS;
                    $scope.testTotal += (response.data.totalTest == undefined) ? 0 : response.data.totalTest;
                    $scope.testRunning = $scope.testTotal - ($scope.testFailed + $scope.testPassed);

                    // Chart data bar
                    if ($scope.targetsProcessed === $scope.targetsLength) {
                        $scope.dataStatistic = [];
                        $scope.titleStatistic = [];
                        $scope.dataStatistic[0] = dataBarChartElement($scope.testPassed, $scope.testTotal, "Test passed", "Success", "Total");
                        $scope.titleStatistic[0] = titleBarChartElement($scope.testPassed, "test passed");
                        $scope.dataStatistic[1] = dataBarChartElement($scope.testFailed, $scope.testTotal, "Test failed", "Failed", "Total");
                        $scope.titleStatistic[1] = titleBarChartElement($scope.testFailed, "test failed");
                        $scope.dataStatistic[2] = dataBarChartElement($scope.testRunning, $scope.testTotal, "Test running or not executed", "Running", "Total");
                        $scope.titleStatistic[2] = titleBarChartElement($scope.testRunning, "Test running or not executed");


                        if ($scope.cardList.length === 0) {
                            $scope.noCluster = true;
                        }
                    }


                } else {
                    $scope.cardList = [];
                    if (statistics.length == 0) {
                        $scope.cardList2 = null;
                    } else {
                        var i = 0,
                            percentualTest = 0;
                        Object.keys(statistics).forEach(function (k) {
                            var tot = statistics[k].total;
                            // percentualTest = Math.round((statistics[k].pass / tot) * 10000) / 100;
                            percentualTest = Math.round((statistics[k].pass.length / tot) * 100);
                            var name = statistics[k].name;

                            if (name.length > 17) {
                                name = name.substr(0, 17) + '...';
                            }
                            if (tot != 0) {
                                $scope.cardList[i] = donutChartElement(statistics[k].pass, statistics[k].fail, statistics[k].noRun, statistics[k].total, name, "Success", "Failed", percentualTest, statistics[k].name);
                                i++;
                            }
                        });
                        if (i > 0) {
                            $scope.cardList2 = {};
                            $scope.cardList2["cardList"] = $scope.cardList;
                            $scope.update = 1 - $scope.update;
                        }
                    }

                    // Bar charts
                    $scope.dataStatistic = [];
                    $scope.titleStatistic = [];
                    var testFailed = (response.data.reportGroup.FAIL == undefined) ? 0 : response.data.reportGroup.FAIL,
                        testPassed = (response.data.reportGroup.PASS == undefined) ? 0 : response.data.reportGroup.PASS,
                        testTotal = (response.data.totalTest == undefined) ? 0 : response.data.totalTest,
                        testRunning = testTotal - (testFailed + testPassed);

                    // Chart data bar4
                    $scope.dataStatistic[0] = dataBarChartElement(testPassed, testTotal, "Test passed", "Success", "Total");
                    $scope.titleStatistic[0] = titleBarChartElement(testPassed, "Test passed");
                    $scope.dataStatistic[1] = dataBarChartElement(testFailed, testTotal, "Test failed", "Failed", "Total");
                    $scope.titleStatistic[1] = titleBarChartElement(testFailed, "Test failed");
                    $scope.dataStatistic[2] = dataBarChartElement(testRunning, testTotal, "Test running or not executed", "Running", "Total");
                    $scope.titleStatistic[2] = titleBarChartElement(testRunning, "Test running or not executed");
                }
            }
            $scope.finishRender = true;
            $scope.semaforo--;
        }, function (err) {
            console.log('Oh no! An error in statistics!');
            $scope.finishRender = true;
            $scope.semaforo--;
        });
    }

    function dataBarChartElement(nTest, nTestTotali, nomeTest, label1, label2) {
        return JSON.parse(JSON.stringify([
            {
                "labels": [label1, label2],
                "series": [label1, label2],
                "data": [nTest, nTestTotali],
                "colors": ["#005CB9", "#e6e6e6"],
                "dataset":
                    {
                        // label: '# of Votes',
                        // data: [3, 5],
                        backgroundColor: [
                            'rgba(230, 230, 230, 0.6)',
                            'rgba(230, 230, 230, 0.6)'
                        ],
                        hoverBackgroundColor: [
                            'rgba(230, 230, 230, 0.3)',
                            'rgba(230, 230, 230, 0.3)'
                        ],
                        borderColor: [
                            '#e6e6e6',
                            '#e6e6e6'
                        ],
                        borderWidth: 2
                    }
                ,
                "options": {
                    responsive: true,
                    maintainAspectRatio: false,
                    legend: {
                        display: false
                    },
                    scales: {
                        yAxes: [{
                            id: 'y-axis-1', type: 'linear', position: 'left', ticks: {
                                beginAtZero: true,
                                fontColor: "white",
                                // max: nTestTotali,
                                display: true
                                // suggestedMin: 0,
                                // suggestedMax: nTestTotali
                                // min: 0,
                                // max: 5
                                // callback: function(value, index, values) {
                                //     return '$' + value;
                                // }
                            },
                            // axisLabel: '1 / 2 / 3',
                            // ticks: 4,
                            gridLines: {
                                display: true,
                                color: "#ffffff",
                                borderDash: [6, 3],
                                zeroLineColor: "#ffffff",
                                zeroLineWidth: 2,
                                zeroLineBorderDash: 2
                            }
                        }],
                        xAxes: [{
                            ticks: {
                                fontColor: "#ffffff",
                                maxRotation: 0,
                                display: true,
                                fontFamily: "sans-serif",
                                fontSize: 14
                            }
                        }]
                    }
                }
            }
        ]));
    }

    function dataSingleBarChartElement(nTestTotali, nomeTest, label1) {
        return JSON.parse(JSON.stringify([
            {
                "labels": [label1],
                "series": [label1],
                "data": [nTestTotali],
                // "colors": ["#005CB9", "#e6e6e6"],
                "dataset": {
                    // label: '# of Votes',
                    // data: [3, 5],
                    backgroundColor: [
                        'rgba(230, 230, 230, 0.6)'
                    ],
                    hoverBackgroundColor: [
                        'rgba(230, 230, 230, 0.3)'
                    ],
                    borderColor: [
                        '#e6e6e6'
                    ],
                    borderWidth: 2
                },
                "options": {
                    responsive: true,
                    maintainAspectRatio: false,
                    legend: {
                        display: false
                    },
                    scales: {
                        yAxes: [{
                            id: 'y-axis-1', type: 'linear', position: 'left', ticks: {
                                beginAtZero: true,
                                fontColor: "white",
                                max: nTestTotali,
                                display: true
                                // callback: function(value, index, values) {
                                //     return '$' + value;
                                // }
                            },
                            gridLines: {
                                display: true,
                                color: "#ffffff",
                                borderDash: [6, 3],
                                zeroLineColor: "#ffffff",
                                zeroLineWidth: 2,
                                zeroLineBorderDash: 2
                            }
                        }],
                        xAxes: [{
                            ticks: {
                                fontColor: "#ffffff",
                                maxRotation: 0,
                                display: true
                            }
                        }]
                    }
                }
            }
        ]));
    }

    function titleBarChartElement(nTest, nomeTest) {
        return JSON.parse(JSON.stringify([
            {
                "numero": nTest,
                "title": nomeTest
            }
        ]));
    }

    function donutChartElement(nTestPassati, nTestFalliti, nTestRONE, total, nomeTest, label1, label2, percentual, longText) {
        return JSON.parse(JSON.stringify(
            {
                data: [{
                    "labels": [label1, label2, 'Running'],
                    "total": total,
                    "data": [nTestPassati.length, nTestFalliti.length, nTestRONE.length],
                    "dataC": [nTestPassati, nTestFalliti, nTestRONE],
                    "colors": ["#005CB9", "#484c4d", "#e6e6e6"],
                    "tooltip": longText,
                    "options": {
                        cutoutPercentage: 65,
                        responsive: true,
                        maintainAspectRatio: false,
                        title: {
                            display: true,
                            text: nomeTest,
                            position: "bottom",
                            fontColor: '#484c4d',
                            fontFamily: "sans-serif",
                            fontSize: 18,
                            fontWeight: "bold"
                        },
                        elements: {
                            center: {
                                text: percentual + '%',
                                color: '#005CB9', // Default is #000000
                                fontStyle: 'sans-serif', // Default is Arial
                                sidePadding: 20 // Default is 20 (as a percentage)
                            }
                        }
                    }
                }],
                title: {
                    "numero": 1230,
                    "title": "Test executed"
                }
            }
        ));
    }

    function resetVars() {
        $scope.cardList2 = null;
        $scope.dataStatistic = null;
        $scope.titleStatistic = null;
        $scope.targetsProcessed = 0;
    }

    $scope.chartBarTimer = function () {
        $timeout(function () {
            return (!$scope.dataStatistic && $scope.selectedTarget);
        }, 2000);
    };

    $scope.chartDonutTimer = function () {
        $timeout(function () {
            return (!$scope.cardList2 && $scope.selectedTarget);
        }, 2000);
    };
}