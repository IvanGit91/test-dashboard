var app = angular.module('mainApp', ["chart.js", "ngRoute", "ngMaterial", "ngTable", "ngResource",
    "angularUtils.directives.dirPagination", "localytics.directives", "ui.bootstrap", "ngAnimate", "alexjoffroy.angular-loaders", "ngTableExport", "ngFileSaver", "ngCookies", "ngIdle"]);


app.config(['$httpProvider',
    function ($httpProvider) {
        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    }
]);

app.filter('roundup', roundUpFilter);

function roundUpFilter() {
    return function (value) {
        return Math.ceil(value);
    };
}


app.directive("modalWindow", function () {
    return {
        restrict: "E",
        template: "<button ng-click='open()' class='btn btn-info'>Open Modal</button>" +
            "<div ng-hide='hidden' class='trans-layer'></div>" +
            "<div class='modal-container' ng-class='{modalactive: !hidden}' ng-transclude>" +
            "</div>",
        scope: true,
        transclude: true,
        controller: function ($scope) {
            $scope.hidden = true;
            $scope.open = function () {
                $scope.hidden = false;
            };
            $scope.hide = function () {
                $scope.hidden = true;
            };
        },
        link: function (scope, ele, attrs) {
            $(ele).find('.trans-layer').on('click', function (event) {
                scope.hidden = true;
                scope.$apply();
            })
        }
    }
});


app.directive("modalWindowOpen", function () {
    return {
        restrict: "E",
        template: "<div>" +
            "<button ng-click='open()' class='btn btn-info'>Open Modal</button>" +
            "<div ng-hide='hidden' class='trans-layer'></div>" +
            "<div class='modal-container' ng-class='{modalactive: !hidden}' ng-transclude>" +
            "<button ng-click='hide()' class='btn btn-info'>Close Modal</button>" +
            "</div>" +
            "</div>",
        scope: {
            modalOpen: "="
        },
        transclude: true,
        controller: function ($scope) {
            console.log("open " + $scope.modalOpen);
            $scope.hidden = true;
            $scope.open = function () {
                $scope.hidden = false;
            };
            $scope.hide = function () {
                $scope.hidden = true;
            };
        },
        link: function (scope, ele, attrs) {
            $(ele).find('.trans-layer').on('click', function (event) {
                scope.hidden = true;
                scope.$apply();
            });

            scope.$watch('modalOpen', function () {
                console.log("VAI");
            }, true);
        }
    }
});


app.config(['$compileProvider', function ($compileProvider) {
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|sms|tel):/);
}]);

app.config(['$compileProvider', function ($compileProvider) {
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(|blob|):/);
}]);

app.config(function (IdleProvider, KeepaliveProvider) {
    var timeoutInSeconds = 29 * 60;
    IdleProvider.idle(60);
    IdleProvider.timeout(timeoutInSeconds);
});

app.config(['TitleProvider', function (TitleProvider) {
    TitleProvider.enabled(false); // it is enabled by default
}]);


app.run(function ($rootScope, $http, $window, $location, $interval, $cookies, Idle, $timeout) {
    var initialSeconds = (new Date()).getTime() / 1000;  //In seconds
    var totalSecondsElapsed = 0;
    var showModalTime = 30;

    Idle.watch();
    $rootScope.$on('IdleStart', function () {
        console.log("START");
        totalSecondsElapsed = ((new Date()).getTime() / 1000) - initialSeconds;
        console.log("TIME ELAPSED: " + totalSecondsElapsed + " FROM THE RUN OF APPLICATION: " + initialSeconds);
    });

    $rootScope.$on('IdleEnd', function () {
        hideModalOnly("Info");
    });

    // countdown in seconds
    $rootScope.$on('IdleWarn', function (e, countdown) {
        if (countdown === showModalTime) {
            showModalOnly("Info", null);
        }
    });

    $rootScope.$on('IdleTimeout', function () {
        hideModalOnly("Info");
        showModalOnly("Logout", null);
        console.log("TIMEOUT");
        $http.post('logout', {})
            .finally(function () {
                // $window.location.href = "http://" + window.location.host + "/login";
            });
    });

    $('#modalLogout').on('hide.bs.modal', function () {
        window.location.href = "http://" + window.location.host + "/login";
    });
});

