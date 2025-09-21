<link rel="stylesheet" href="views/home/home.css">

<div class="container subContainer" ng-controller="HomeCtrl">
    <div class="loader-overlay newfade-loader in" ng-show="!(semaforo == 0)">
        <div class="custom-loader" loader-css="ball-clip-rotate-multiple"></div>
    </div>
    <div class="alert alert-danger newfade in" role="alert" ng-show="errors">
        <a class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
        <span class="sr-only">Error:</span>
        {{errors}}
    </div>

    <div class="row" ng-init="page.selected='uno'">
        <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2" style="font-size: 15pt">
            {{title}}
        </div>
        <div class="monitoring-background col-xs-12 col-sm-12 col-md-10 col-lg-10" style="font-size: 13pt">
            <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 center">
                <span><i class="glyphicon glyphicon-calendar"></i> <span>{{date | date:'dd MMMM yyyy'}}</span></span>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 center">
                <label style="font-weight: 600">Projects:
                    <popup position="top"
                           datapopup="This term identifies all the used projects to create tests on STBs and on mobile devices"></popup>
                </label>
                <select ng-model="selectedProject"
                        ng-options="x.name for x in projects track by x.id"
                        class="form-control comboClass"
                        ng-required="true"
                        ng-change="getSelectedProject()">
                    <option value="">-- Select a Project --</option>
                </select>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 center">
                <label style="font-weight: 600">Targets:
                    <popup position="top"
                           datapopup="A target is a project folder. Each target identifies a test campaign connected to a new software release"></popup>
                </label>
                <select ng-model="selectedTarget"
                        ng-options="x.name for x in targets track by x.id"
                        class="form-control comboClass"
                        ng-required="true"
                        ng-change="getSelectedTarget()">
                    <option value="">-- Select a Target --</option>
                </select>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 center">
                <span><span>Reload data</span><br><i ng-click="getSomething()"
                                                     class="glyphicon glyphicon-repeat reloadBtn"></i></span>
            </div>
        </div>
    </div>

    <div class="row" ng-show="dataStatistic">
        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
            <tests-chart title="titleStatistic[0]" data="dataStatistic[0]"></tests-chart>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
            <tests-chart title="titleStatistic[1]" data="dataStatistic[1]"></tests-chart>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
            <tests-chart title="titleStatistic[2]" data="dataStatistic[2]"></tests-chart>
        </div>
    </div>

    <div class="center custom-text-shadow custom-height"
         ng-show="!dataStatistic && (selectedTarget || noBars) && semaforo == 0">
        <div>No test avalaible to display</div>
    </div>

    <div class="row" ng-class="cardList2 ? 'dash-visible' : 'dash-invisible'">
        <div class="col-*-12">
            <fieldset class="dash-chart-container">
                <legend class="dash-chart-header">Clusters
                    <popup position="top"
                           datapopup="This term identifies requirements. They relate to the sections belonging to the test campaigns. Each cluster has been renamed referring to the sections based on which the requirements are divided"></popup>
                </legend>

                <div class="row" id="card-carousel">
                    <angular-carousel cards="cardList2" update="update"></angular-carousel>
                </div>
            </fieldset>
        </div>
    </div>

    <div class="center custom-text-shadow custom-height"
         ng-show="!cardList2 && (selectedTarget || noCluster) && semaforo == 0">
        <div>No cluster available to display</div>
    </div>
</div>

<script>
    $(function () {
        $('[data-toggle="popover"]').popover();
    })
</script>