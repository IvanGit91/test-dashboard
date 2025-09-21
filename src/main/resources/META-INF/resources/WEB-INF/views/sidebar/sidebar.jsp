<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="views/sidebar/sidebar.css">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,800" rel="stylesheet">

</head>

<body>

<div class="sidebar collapse navbar-collapse" id="navbarCollapse" style="
    overflow-y: auto!important;overflow-x: hidden!important">
    <img class="img-dashboard" src="/img/dashboard/logo_dash.png"/>
    <a href="https://ivanbattimiello.dev/" target="_blank">
        <img src="/img/dashboard/poweredby.png"
             class="img-dash"
             alt="Responsive image">
    </a>
    <ul class="sidebar-ul" ng-init="page.selected='zero'">
        <li class="dash-element" ng-class="{'currentPage' : page.selected == 'uno'}">
            <img class="img-arrow" src="/img/dashboard/icon_11.png"/>
            <a href="#home"><span class='iconHome'></span>Monitoring & Test Centre</a>
        </li>
        <li class="dash-element" ng-show="true" ng-class="{'currentPage' : page.selected == 'due'}">
            <img class="img-arrow" src="/img/dashboard/icon_11.png"/>
            <a href="#environments"><span class='iconEnvironments'></span>Environments</a>
        </li>
        <li class="dash-element" ng-show="true" ng-class="{'currentPage' : page.selected == 'tre'}">
            <img class="img-arrow" src="/img/dashboard/icon_11.png"/>
            <a href="#testresult"><span class='iconResult'></span>Test Results</a>
        </li>
        <li class="dash-element" ng-show="true" ng-class="{'currentPage' : page.selected == 'quattro'}">
            <img class="img-arrow" src="/img/dashboard/icon_11.png"/>
            <a href="#statistics"><span class='iconStatistics'></span>Statistics</a>
        </li>
        <li class="dash-element" ng-show="true" ng-class="{'currentPage' : page.selected == 'cinque'}">
            <img class="img-arrow" src="/img/dashboard/icon_11.png"/>
            <a href="#benchmark"><span class='iconBenchmark'></span>Benchmark</a>
        </li>
    </ul>

</div>
<div class="navigation-bar">
    <button type="button" class="menu-button" data-target="#navbarCollapse" data-toggle="collapse">
        <i class="glyphicon glyphicon-menu-hamburger"
           style="color:#005CB9;text-shadow:1px 1px 1px #000000;font-size: 40px;"></i>
    </button>
    <img class="img-m3" src="/img/dash.png"/>
    <button type="button" class="logout-button">
        <a href="/logout" class="logout"><span class='iconLogout'></span>Logout</a>
    </button>
</div>
<div class="pageView">
    <div ng-view></div>
</div>
</body>
</html>