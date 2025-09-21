<link rel="stylesheet" href="views/environments/environments.css">

<div class="container subContainer" ng-controller="EnvironmentsCtrl">
    <div class="loader-overlay" ng-show="!(semaforo == 0)">
        <div class="custom-loader" loader-css="ball-clip-rotate-multiple"></div>
    </div>
    <div class="row" ng-init="page.selected='due'">
        <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2" style="font-size: 15pt">
            {{title}}
        </div>
        <div class="col-xs-12 col-sm-12 col-md-10 col-lg-10" style="font-size: 13pt;padding-top: 0px!important">
            <div class="col-xs-12 col-sm-12 col-md-10 col-lg-10 center"></div>
            <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 center"><span><span>Reload data</span><br><i
                    ng-click="getAvailableSuts()" class="glyphicon glyphicon-repeat reloadBtn"></i></span></div>
        </div>
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
                    <span style="pointer-events: auto" ng-if="$column.title(this) === 'Device ID / Serial Number'"> <popup
                            position="top"
                            datapopup="This column identifies the Device ID returned from the application or the STB Serial Number"
                            inverse="true"></popup> </span>
                    <span style="pointer-events: auto" ng-if="$column.title(this) === 'Component Type'"> <popup
                            position="top"
                            datapopup="It identifies the type of  devices under test (Set-Top-Box, Smartphone or Tablet)"
                            inverse="true"></popup> </span>
                    <span style="pointer-events: auto" ng-if="$column.title(this) === 'Firmware Version'"> <popup
                            position="top"
                            datapopup="It specifies the release software installed on different DUTs"
                            inverse="true"></popup> </span>
                    <span style="pointer-events: auto" ng-if="$column.title(this) === 'Connection Type'"> <popup
                            position="top"
                            datapopup="It specifies the connection type by which the tester performed tests (Fiber-To-The-Home, Fiber-To-The-Cabinet Make/Vula, ADSL ULL o Wholesale, Wi-Fi, 4G/3G)"
                            inverse="true"></popup> </span>
                    <span style="pointer-events: auto" ng-if="$column.title(this) === 'Backend Environment'"> <popup
                            position="top"
                            datapopup="Test environment, it should should be PRODUCTION or PRE-PRODUCTION"
                            inverse="true"></popup> </span>
                    <span style="pointer-events: auto" ng-if="$column.title(this) === 'Provisioning'"> <popup
                            position="top" datapopup="TVE or 190" inverse="true"></popup> </span>
                    <span style="pointer-events: auto" ng-if="$column.title(this) === 'Site'"> <popup position="top"
                                                                                                      datapopup="Site where the devices are set up to perform tests"
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
                    <button class="btn dash-btn" ng-disabled="!tableParams2.hasFilter()"
                            ng-click="tableParams2.filter({})">Clear filters
                    </button>
                    <popup position="top" datapopup="This button clears all the filters set up" inverse="true"></popup>
                </div>
            </div>
            <div class="table-dashboard-container">
                <table ng-table-dynamic="tableParams2 with cols" class="table table-dashboard" show-filter="true"
                       template-pagination="custom/pager" template-header="customHeader.html">
                    <tr ng-repeat="row in $data">
                        <td ng-repeat="col in $columns">{{row[col.field]}}</td>
                    </tr>
                </table>
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
                        <a class="page-link" ng-switch-when="prev" ng-click="params.page(page.number)"
                           href="">&laquo;</a>
                        <a class="page-link" ng-switch-when="first" ng-click="params.page(page.number)" href=""><span
                                ng-bind="page.number"></span></a>
                        <a class="page-link" ng-switch-when="page" ng-click="params.page(page.number)" href=""><span
                                ng-bind="page.number"></span></a>
                        <a class="page-link" ng-switch-when="more" ng-click="params.page(page.number)"
                           href="">&#8230;</a>
                        <a class="page-link" ng-switch-when="last" ng-click="params.page(page.number)" href=""><span
                                ng-bind="page.number"></span></a>
                        <a class="page-link" ng-switch-when="next" ng-click="params.page(page.number)"
                           href="">&raquo;</a>
                    </li>
                </ul>
            </div>
        </script>
    </div>
</div>
<script>
    $(function () {
        $('[data-toggle="popover"]').popover();
    })
</script>