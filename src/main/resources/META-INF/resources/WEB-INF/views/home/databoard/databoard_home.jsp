<link rel="stylesheet" href="views/home/databoard/databoard_home.css">

<div class="componentWrapper">
    <div>
        <div class="componentTitle" title="{{title}}"><span
                ng-class="{'glyphicon-ok': calculateStatus(), 'glyphicon-remove': !calculateStatus()}"
                class="glyphicon"></span>
            {{title}}
        </div>
    </div>
</div>
<div class="componentContent">
    <div class="col-sx-12 col-md-3 card-group" ng-repeat="element in data" title="{{element.name}}">
        <div class="card bg-primary">
            <div ng-class="{'noPointerEvent': (!element.projects.length > 0)}" class="card-body text-center"
                 ng-click="goToProjectBoard(element, element.projects)">
                <p class="card-text"><span
                        ng-class="{'glyphicon-remove': !element.status, 'glyphicon-ok': element.status}"
                        class="glyphicon"></span></p>
                <p class="card-text" ng-class="{'colorRed': !element.status, 'colorGreen': element.status}">
                    {{element.name}}</p>
                <p class="card-text" ng-class="{'colorRed': !element.status, 'colorGreen': element.status}">
                    {{element.date}}</p>
            </div>
        </div>
    </div>
</div>
