<link rel="stylesheet" href="views/benchmark/benchmark.css">

<div class="container subContainer" ng-controller="BenchmarkCtrl as vm">
    <div class="loader-overlay" ng-show="!(semaforo == 0)">
        <div class="custom-loader" loader-css="ball-clip-rotate-multiple"></div>
    </div>
    <div class="row" ng-init="page.selected='cinque'">
        <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2" style="font-size: 15pt">
            <span>{{title}}</span>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-10 col-lg-10" style="font-size: 13pt;padding-top: 0px!important">
            <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 center">
                <div ng-if="projectName">
                    <span> Selected project: </span>
                </div>
                <div ng-if="projectName">{{projectName}}</div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 center">
                <div ng-if="targetName">
                    <span> Selected target: </span>
                </div>
                <div ng-if="targetName">{{targetName}}</div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 center">
                <span><span>Reload data</span><br><i ng-click="reload()"
                                                     class="glyphicon glyphicon-repeat reloadBtn"></i></span>
            </div>
        </div>
    </div>

    <div class="alert alert-danger newfade in" role="alert" ng-show="true">
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
        <span class="sr-only">Error:</span>
        THIS SECTION IS
        UNDER CONSTRUCTION<br>
        ...please come back later...
    </div>

</div>