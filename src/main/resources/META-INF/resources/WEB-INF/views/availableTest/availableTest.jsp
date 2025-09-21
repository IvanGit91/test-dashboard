<link rel="stylesheet" href="views/availableTest/availableTest.css">

<div class="container availableContainer" ng-controller="AvailableTestCtrl">

    <div class="row">
        <div class="col-xs-8 col-sm-8 col-md-8">
            <div class="componentTitle">
                {{title}}<br><i>{{type}}</i>
            </div>
        </div>
        <div class="col-xs-4 col-sm-4 col-md-4 outsideBox">
            <div ng-repeat="element in data">
                <div ng-app
                     ng-init="element.num.length==1 ? [itemStatus='Planned',itemColor='#0071bd'] : [itemStatus='Ongoing',itemColor='#e65100']">
                    <div ng-app
                         ng-init="itemStatus=='Ongoing'&&element.num[0]==element.num[1] ? [itemStatus='Completed',itemColor='#009345'] : itemStatus=itemStatus">
                        <div ng-app
                             ng-init="itemStatus=='Planned'&&element.num[0]!=0 ? itemText=itemStatus+' ('+element.num[0]+')' : itemText=itemStatus">
                            <div ng-app
                                 ng-init="itemStatus!='Planned' ? itemText=itemStatus+' ('+element.num[0]+'/'+element.num[1]+')' : itemText=itemText">
                                <div class="insideBox" ng-style="{'background-color':itemColor}">
                                    {{itemText}}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>