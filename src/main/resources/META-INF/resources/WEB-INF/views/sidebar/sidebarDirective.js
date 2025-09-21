angular.module('mainApp').directive("sidebar", sidebar);

function sidebar() {
    return {
        restrict: 'E',
        controller: 'SidebarCtrl',
        scope: {},
        templateUrl: 'sidebar'
    };
}