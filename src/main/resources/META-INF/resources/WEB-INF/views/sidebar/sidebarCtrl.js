angular.module('mainApp').controller("SidebarCtrl", SidebarCtrl);

function SidebarCtrl($scope, $window, $location, $http) {


    $(function () {
        $scope.initSidebar();   //init sidebar
        $window.location.href = "#home";
    });

    $scope.initSidebar = function () {
        $(document).ready(function () {
            var trigger = $('.hamburger'),
                overlay = $('.overlay'),
                isClosed = true;

            trigger.click(function () {
                hamburger_cross();
            });

            function hamburger_cross() {

                if (isClosed === true) {
                    overlay.hide();
                    trigger.removeClass('is-open');
                    trigger.addClass('is-closed');
                    isClosed = false;
                } else {
                    overlay.show();
                    trigger.removeClass('is-closed');
                    trigger.addClass('is-open');
                    isClosed = true;
                }
            }

            $('[data-toggle="offcanvas"]').click(function () {
                $('#wrapper').toggleClass('toggled');
            });
        });
    };
}