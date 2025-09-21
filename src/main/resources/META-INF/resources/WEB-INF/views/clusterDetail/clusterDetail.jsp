<link rel="stylesheet" href="views/clusterDetail/clusterDetail.css">

<div class="customContainer" ng-controller="ClusterDetailCtrl" ng-app="mainApp">
    <table ng-table="tableParams">
        <tr ng-repeat="row in $data">
            <td data-title="'Sut Name'" filter="{area: 'text'}" sortable="'area'">
                {{row.area}}
            </td>
            <td data-title="'Component Type'" filter="{uid: 'text'}" sortable="'uid'">
                {{row.uid}}
            </td>
        </tr>
    </table>
</div>