<link rel="stylesheet" href="views/dashchart/dash_chart.css">
<div class="outer" style="{{widthreference}}">

    <div ng-repeat="run in data" style="height: 180px">
        <div class="legend">
            Test total = {{run.data[0] + run.data[1] + run.data[2]}}<br>
            Test passed = {{run.data[0]}}<br>
            Test failed = {{run.data[1]}}<br>
            Test run or not-exec = {{run.data[2]}}<br><br>
            <a style="background-color: #ffffff;color: #005CB9;padding: 1px"
               ng-click="vaiModale(run.dataC, run.tooltip)">Show details</a>

        </div>
        <canvas id="doughnut"
                class="chart chart-doughnut"
                chart-data="run.data"
                chart-labels="run.labels"
                chart-colors="run.colors"
                chart-options="run.options"
                chart-series=""
                chart-click="">
        </canvas>
    </div>
</div>