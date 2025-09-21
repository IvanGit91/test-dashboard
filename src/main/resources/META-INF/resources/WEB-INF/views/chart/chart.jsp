<link rel="stylesheet" href="views/chart/chart.css">

<div class="container subContainer">
    <div class="row">
        <div class="col-xs-12 col-md-6 chartContainer">
            <canvas class="chart chart-pie" chart-data="run.data" chart-labels="run.labels" chart-colors="run.colors"
                    chart-options="run.options"
                    chart-series="" chart-click=""></canvas>
        </div>
        <div class="col-xs-12 col-md-6 chartContainer">
            <canvas class="chart chart-doughnut" chart-data="passed.data" chart-labels="passed.labels"
                    chart-colors="passed.colors" chart-options="passed.options"
                    chart-series="" chart-click=""></canvas>
        </div>
        <div class="col-xs-12 col-md-6 chartContainer">
            <canvas class="chart chart-pie" chart-data="blocked.data" chart-labels="blocked.labels"
                    chart-colors="blocked.colors" chart-options="blocked.options"
                    chart-series="" chart-click=""></canvas>
        </div>
        <div class="col-xs-12 col-md-6 chartContainer">
            <canvas class="chart chart-doughnut" chart-data="defect.data" chart-labels="defect.labels"
                    chart-colors="defect.colors" chart-options="defect.options"
                    chart-series="" chart-click=""></canvas>
        </div>
    </div>
</div>