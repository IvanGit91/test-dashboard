<link rel="stylesheet" href="views/testschart/tests_chart.css">

<div class="container barContainer">

    <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 chartContainer">
        <div class="componentWrapper">
            <div class="componentTitle" ng-repeat="element in title">
                {{element.numero}}
                <br>
                {{element.title}}
            </div>
        </div>
    </div>
    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8 chartContainer">
        <div class="componentContent">
            <div ng-repeat="run in data">
                <canvas id="bar" class="chart chart-bar" chart-data="run.data" chart-labels="run.labels"
                        chart-series="run.series" chart-colors="run.colors" chart-options="run.options"
                        chart-dataset-override="run.dataset">
                </canvas>
            </div>
        </div>
    </div>
</div>