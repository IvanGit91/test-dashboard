<link rel="stylesheet" href="views/testresult/testresult.css">

<div class="container subContainer" ng-controller="TestresultCtrl as demo">
    <div class="loader-overlay" ng-show="!(semaforo == 0)">
        <div class="custom-loader" loader-css="ball-clip-rotate-multiple"></div>
    </div>
    <div class="row" ng-init="page.selected='tre'">
        <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2" style="font-size: 15pt">
            <span>{{title}}</span>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-10 col-lg-10" style="font-size: 13pt;padding-top: 0px!important">
            <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 center">
                <div ng-if="projectName">
                    Selected project:
                </div>
                <div ng-if="projectName">{{projectName}}</div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 center">
                <div ng-if="targetName">
                    Selected target:
                </div>
                <div ng-if="targetName">{{targetName}}</div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 center">
                <span><span>Reload data</span><br><i ng-click="reload()"
                                                     class="glyphicon glyphicon-repeat reloadBtn"></i></span>
            </div>
        </div>
    </div>

    <div class="alert alert-danger newfade in" role="alert" ng-show="errors">
        <a class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
        <span class="sr-only">Error:</span>
        {{errors}}
    </div>

    <script type="text/ng-template" id="customHeader.html">
        <ng-table-group-row></ng-table-group-row>
        <tr>
            <th title="{{$column.headerTitle(this)}}"
                ng-repeat="$column in $columns"
                ng-class="{
                    'sortable': $column.sortable(this),
                    'sort-asc': params.sorting()[$column.sortable(this)]=='asc',
                    'sort-desc': params.sorting()[$column.sortable(this)]=='desc'
                  }"
                ng-click="sortBy($column, $event)"
                ng-if="$column.show(this)"
                ng-init="template = $column.headerTemplateURL(this)"
                class="header {{$column.class(this)}}"
                style="pointer-events: none;"
            >
                <div ng-if="!template" class="ng-table-header"
                     ng-class="{'sort-indicator': params.settings().sortingIndicator == 'div'}">
                    <span ng-bind="$column.title(this)"
                          ng-class="{'sort-indicator': params.settings().sortingIndicator == 'span'}"> </span>
                    <span style="pointer-events: auto" ng-if="$column.title(this) === 'DUT Name'"> <popup position="top"
                                                                                                          datapopup="Device Under Test, it identifies the name of the device under test"
                                                                                                          inverse="true"></popup> </span>
                    <span style="pointer-events: auto" ng-if="$column.title(this) === 'Test Name'"> <popup
                            position="top" datapopup="Test title" inverse="true"></popup> </span>
                    <span style="pointer-events: auto" ng-if="$column.title(this) === 'Test Type'"> <popup
                            position="top"
                            datapopup="It specifies the software release type, if it is an EVOLUTIVE or a CORRECTIVE one"
                            inverse="true"></popup> </span>
                    <span style="pointer-events: auto" ng-if="$column.title(this) === 'Test Status'"> <popup
                            position="top" datapopup="This term shows the tests results (PASSED or FAILED)"
                            inverse="true"></popup> </span>
                    <span style="pointer-events: auto" ng-if="$column.title(this) === 'Actions'"> <popup position="top"
                                                                                                         datapopup="It allows the user to download a test executed report and to display its details"
                                                                                                         inverse="true"></popup> </span>
                </div>
                <div ng-if="template" ng-include="template"></div>
            </th>
        </tr>
        <ng-table-filter-row></ng-table-filter-row>
    </script>

    <div class="row fast-fade" ng-show="isRendered">
        <div class="col-*-12">
            <div style="display: flex;">
                <div class="dash-div">
                    <button class="btn dash-btn" type="button"
                            ng-click='downloadCsv(csv, $event, "all-test-result.csv")' href=''>Download all test
                    </button>
                    <popup position="top"
                           datapopup="This button allows the user to download a csv file containing a list of all performed test results"
                           inverse="true"></popup>
                </div>
                <div class="dash-div">
                    <button class="btn dash-btn" type="button" ng-disabled="checkSelected()" ng-click="downloadButton()"
                            href=''>Download selected
                    </button>
                    <popup position="top"
                           datapopup="This button allows the user to download a zip file containing the details of the selected tests"
                           inverse="true"></popup>
                </div>
                <div class="dash-div">
                    <button class="btn dash-btn" ng-disabled="!tableParams3.hasFilter()"
                            ng-click="tableParams3.filter({})">Clear filters
                    </button>
                    <popup position="top" datapopup="This button clears all the filters set up" inverse="true"></popup>
                </div>
            </div>
            <div class="table-dashboard-container">
                <table ng-table-dynamic="tableParams3 with cols" class="table table-dashboard" show-filter="true"
                       template-pagination="custom/pager" template-header="customHeader.html" export-csv="csv"
                       separator=";" export-csv-ignore="ignore">
                    <tr ng-repeat="row in $data">
                        <td ng-repeat="col in $columns">
                            <div ng-if="col.field=='selected' && row.status!='RUNNING'" width="30"
                                 style="text-align: center"><input type="checkbox" ng-model="row.selected"
                                                                   ng-click="setVar(tableParams3)"></div>
                            <div ng-if="col.field!='selected'"
                                 ng-class="{'test-status center {{row.status | lowercase}}' : col.field=='status'}">
                                {{row[col.field]}}
                            </div>
                            <div ng-if="col.field=='actions'" style="text-align: center">
                                <script>
                                    $(function () {
                                        $('[data-toggle="tooltip"]').tooltip()
                                    })
                                </script>
                                <img data-toggle="tooltip" data-placement="top" data-html="true"
                                     title="Download<br>single report" class="img-custom-dimension"
                                     src="img/dashboard/icon_10.png" alt="none"
                                     style="margin-right: 5px;" ng-click="reportToPdf(row)"
                                     ng-show="row.status!='RUNNING'">
                                <img data-toggle="tooltip" data-placement="top" title="Report details"
                                     class="img-custom-dimension" ng-click="goToReportDetails(row)"
                                     src="img/dashboard/icon_9.png" alt="none">
                            </div>
                        </td>
                    </tr>
                </table>
                <script type="text/ng-template" id="ng-table/headers/checkbox.html">
                    <input id="selectAllCheckbox" type="checkbox" ng-model="selectVar"
                           ng-click="selectAll(tableParams3)"/>
                </script>
            </div>
        </div>
    </div>
</div>

<script type="text/ng-template" id="custom/pager">
    <div class="table-dashboard-showing center">
        <span>Showing {{params.page()}} to {{(params.total() / params.count()) | roundup}} of {{params.total()}} entries</span>
    </div>
    <div class="ng-cloak ng-table-pager" ng-if="params.data.length" style="display: flow-root;">
        <div ng-if="params.settings().counts.length" class="ng-table-counts btn-group pull-right"
             style="margin-bottom: 24px">
            <button ng-repeat="count in params.settings().counts" type="button"
                    ng-class="{'active':params.count() == count}"
                    ng-click="params.count(count)" class="btn btn-default">
                <span ng-bind="count"></span>
            </button>
        </div>
        <ul ng-if="pages.length" class="pagination ng-table-pagination">
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

<script>
    $(function () {
        $('[data-toggle="popover"]').popover();
    })
</script>