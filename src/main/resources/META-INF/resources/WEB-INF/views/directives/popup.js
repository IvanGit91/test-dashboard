angular.module('mainApp').directive('popup', function ($window, $timeout) {
    return {
        restrict: 'E',
        replace: true,
        translude: true,
        scope: {datapopup: '@', position: '@', inverse: '@'},
        controller: function ($scope) {
            $(function () {
                $('[data-toggle="popover"]').popover();

            });
            if ($scope.position === null || $scope.position === undefined)
                $scope.position = "top";
            $scope.class = ($scope.inverse !== undefined) ? 'dash-info-inverse' : 'dash-info';
        },
        template: [
            '<a style="font-size: 11pt;padding: 4px" class="glyphicon glyphicon-info-sign" ng-class="(inverse !== undefined) ? \'dash-info-inverse\' : \'dash-info\'" data-trigger="hover" data-html="true" role="button" tabindex="0" data-container="body" data-toggle="popover" data-placement={{position}} data-content={{datapopup}}> </a>'
        ].join(''),
        link: function (scope, element, attrs) {
        }
    };
});