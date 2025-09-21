<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Dashboard</title>

    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- jQuery -->
    <script src="lib/jquery/jquery-3.1.0.min.js"></script>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="lib/bootstrap/css/bootstrap.min.css">
    <script src="lib/bootstrap/js/bootstrap.min.js"></script>

    <!-- Angular -->
    <script src="lib/angularjs/angular.js"></script>
    <script src="lib/angularjs/angular-route.js"></script>

    <!-- Chartjs -->
    <script src="lib/chartjs/Chart.js"></script>
    <script src="lib/chartjs/angular-chart.min.js"></script>

    <!-- module -->
    <script src="views/appModule.js"></script>

    <!-- configuration -->
    <script src="views/routeProvider.js"></script>

    <!-- DashboardMain -->
    <script src="views/mainCtrl.js"></script>

    <!-- Sidebar -->
    <script src="views/sidebar/sidebarDirective.js"></script>
    <script src="views/sidebar/sidebarCtrl.js"></script>

    <!-- Home -->
    <script src="views/home/homeCtrl.js"></script>
    <script src="views/home/databoard/databoardHomeCtrl.js"></script>
    <script src="views/home/databoard/databoardHomeDirective.js"></script>


    <!-- Testresult -->
    <script src="views/testresult/testresultCtrl.js"></script>

    <!-- Statistics -->
    <script src="views/statistics/statisticsCtrl.js"></script>

    <!-- Environments -->
    <script src="views/environments/environmentsCtrl.js"></script>
    <script src="views/availableTest/availableTestCtrl.js"></script>
    <script src="views/availableTest/availableTestDirective.js"></script>

    <%--Report Details--%>
    <script src="views/reportDetails/reportDetailsCtrl.js"></script>

    <%--Benchmark--%>
    <script src="views/benchmark/benchmarkCtrl.js"></script>

    <!-- Charts -->
    <script src="views/chart/chartCtrl.js"></script>
    <script src="views/chart/chartsDirective.js"></script>

    <script src="views/dashchart/dashChartCtrl.js"></script>
    <script src="views/dashchart/dashChartDirective.js"></script>

    <script src="views/testschart/testsChartCtrl.js"></script>
    <script src="views/testschart/testsChartDirective.js"></script>


    <!-- css global-->
    <link rel="stylesheet" href="views/template.css"/>

    <!-- material -->
    <script src="lib/angularjs/angular-animate.js"></script>
    <script src="lib/angularjs/angular-aria.js"></script>
    <script src="lib/angularjs/angular-messages.js"></script>
    <script src="lib/angularjs/angular-sanitize.js"></script>
    <script src="lib/angularjs/table/ng-table.min.js"></script>
    <script src="lib/util/angular-material.min.js"></script>
    <script src="lib/util/smart-table.min.js"></script>
    <script src="lib/util/ui-bootstrap-tpls-2.5.0.min.js"></script>
    <link rel="stylesheet" href="lib/util/angular-material.min.css"/>
    <link rel="stylesheet" href="lib/angularjs/table/ng-table.min.css"/>

    <script src="lib/angularjs/angular-resource.js"></script>

    <%--Utility--%>
    <script src="lib/util/ui-carousel.min.js"></script>
    <link rel="stylesheet" href="lib/util/ui-carousel.min.css"/>

    <%--jszip--%>
    <script src="lib/util/jszip.js"></script>

    <%--Carousel--%>
    <script src="views/home/angularCarouselModule.js"></script>

    <script src="lib/util/dirPagination.js"></script>

    <link rel="stylesheet" src="lib/util/chosen.min.css"/>
    <link rel="image_src" href="lib/util/chosen-sprite.png">
    <script src="lib/util/chosen.jquery.min.js"></script>
    <script src="lib/util/chosen.proto.min.js"></script>

    <script src="lib/util/angular-chosen.js"></script>

    <script src="views/directives/popup.js"></script>

    <%--Loaders--%>
    <script src="lib/util/angular-loaders.min.js"></script>
    <link rel="stylesheet" href="lib/util/loaders.min.css">

    <%--Table to csv--%>
    <script src="lib/angularjs/table/ng-table-export.js"></script>

    <%--File Saver--%>
    <script src="lib/util/filesaver/Blob.js"></script>
    <script src="lib/util/filesaver/FileSaver.min.js"></script>
    <script src="lib/util/filesaver/angular-file-saver.bundle.min.js"></script>

    <script src="lib/angularjs/angular-cookies.js"></script>
    <script src="lib/angularjs/angular-idle.min.js"></script>
    <script src="views/utilFunction.js"></script>

</head>
<body ng-app="mainApp" ng-controller="MainCtrl">
<div class="bg-img thin">
    <sidebar></sidebar>

    <!-- Table Modal -->
    <div class="modal fade" id="modalUser" tabindex="-1" role="dialog" aria-labelledby="modalUserTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header dash-modal-style center">
                    <h5 class="modal-title app-color" id="modalUserTitle"></h5>
                </div>
                <script type="text/ng-template" id="ng-table/filters/top-result.html">
                    <label class="radio-inline">
                        <input type="radio" ng-disabled="$filterRow.disabled" ng-model="params.filter()[name]"
                               value=""/> <strong>Total</strong>
                    </label>
                    <br/>
                    <label class="radio-inline" ng-repeat="result in $column.data">
                        <input type="radio" ng-disabled="$filterRow.disabled" ng-model="params.filter()[name]"
                               value="{{result}}"/> {{result}}
                    </label>
                </script>
                <div id="modalUserBody" class="modal-body">
                    <div class="dash-modal">
                        <div class="table-dashboard-container">
                            <table style="width: 100%;font-family: 'Open Sans'" ng-table="tableParamsModal"
                                   template-pagination="custom/pagermodal">
                                <colgroup>
                                    <col width="55%"/>
                                    <col width="45%"/>
                                </colgroup>
                                <tr ng-repeat="row in $data">
                                    <td data-title="'Test Name'">
                                        {{row.name}}
                                    </td>
                                    <td data-title="'Result'" filter="{ result: 'ng-table/filters/top-result.html'}"
                                        filter-data="results">
                                        {{row.result}}
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer center dash-modal-style" style="display: block;">
                    <button id="modalUserCloseButton" type="button" class="btn btn-dash" data-dismiss="modal">Close
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/ng-template" id="custom/pagermodal">
        <div class="table-dashboard-showing center"
             style="border-top: 1px solid #005CB9; margin-top: 5px; margin-bottom: 5px;">
            <span style="position: relative; top: 5px;font-family: 'Open Sans'">Showing {{params.page()}} to {{(params.total() / params.count()) | roundup}} of {{params.total()}} entries</span>
        </div>
        <div class="ng-cloak ng-table-pager" ng-if="params.data.length" style="display: flow-root;">
            <div ng-if="params.settings().counts.length" class="ng-table-counts btn-group pull-right">
                <button ng-repeat="count in params.settings().counts" type="button"
                        ng-class="{'active':params.count() == count}"
                        ng-click="params.count(count)" class="btn btn-default">
                    <span ng-bind="count"></span>
                </button>
            </div>
            <ul ng-if="pages.length" class="pagination ng-table-pagination" style="display: block;">
                <li class="page-item" ng-class="{'disabled': !page.active && !page.current, 'active': page.current}"
                    ng-repeat="page in pages" ng-switch="page.type">
                    <a class="page-link" ng-switch-when="prev" ng-click="params.page(page.number)" href="">&laquo;</a>
                    <a class="page-link" ng-switch-when="first" ng-click="params.page(page.number)" href=""><span
                            ng-bind="page.number"></span></a>
                    <a class="page-link" ng-switch-when="page" ng-click="params.page(page.number)" href=""><span
                            ng-bind="page.number"></span></a>
                    <a class="page-link" ng-switch-when="more" ng-click="params.page(page.number)" href="">&#8230;</a>
                    <a class="page-link" ng-switch-when="last" ng-click="params.page(page.number)" href=""><span
                            ng-bind="page.number"></span></a>
                    <a class="page-link" ng-switch-when="next" ng-click="params.page(page.number)" href="">&raquo;</a>
                </li>
            </ul>
        </div>
    </script>


    <!-- Timeout modal -->
    <div class="modal fade" id="modalInfo" tabindex="-1" role="dialog" aria-labelledby="modalInfoTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header alert-warning">
                    <h3>Session is expiring for inactivity</h3>
                </div>
                <div id="modalInfoBody" class="modal-body app-color" idle-countdown="countdown"
                     ng-init="max = countdown">
                    <p>You will be logged out in <span class="label label-warning">{{countdown}}</span> <span
                            ng-pluralize="" count="countdown" when="{'one': 'second', 'other': 'seconds' }"></span></p>
                    <uib-progressbar max="30" value="countdown" animate="true" class="progress-striped active"
                                     type="warning"></uib-progressbar>
                </div>
            </div>
        </div>
    </div>

    <!-- Logout modal -->
    <div class="modal fade" id="modalLogout" tabindex="-1" role="dialog" aria-labelledby="modalLogoutTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header alert-danger">
                    <h3>Session Expired!</h3>
                </div>
                <div id="modalLogoutBody" class="modal-body app-color" idle-countdown="countdown">
                    <p>
                        Logout for inactivity!
                    </p>
                </div>
                <div class="modal-footer center" style="display: block;">
                    <button id="modalLogoutCloseButton" type="button" class="btn btn-danger" data-dismiss="modal">
                        Close
                    </button>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>