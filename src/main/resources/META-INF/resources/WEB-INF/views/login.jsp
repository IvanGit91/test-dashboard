<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="views/login.css"/>
    <link rel="stylesheet" href="lib/bootstrap/css/bootstrap.min.css">
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon"/>
</head>
<body class="blueBg">
<div class="bg-img">
    <div class="container login-container">
        <div class="col-md-4 col-md-offset-4 col-sm-8 col-sm-offset-2 col-xs-8 col-xs-offset-2 centerDiv">
            <div class="login-background">
                <div class="m3-logo-container">
                    <img src="/img/dash.png" class="img-responsive login-m3-logo centerE" alt="Responsive image">
                </div>
                <h3 class="login-header" style="color: #484c4d;text-align: center">Sign in to Platform</h3>
                <form action="/login" method="post" style="padding-bottom: 30px;">
                    <div class="form-group">
                        <input type="text" placeholder="Username" name="user" class="login-input centerE" id="user">
                    </div>
                    <div class="form-group">
                        <input type="password" name="pass" placeholder="Password" class="login-input centerE" id="pass">
                    </div>
                    <button type="submit" name="submit" class="login-button centerE">Sign in</button>
                    <c:if test="${param.error ne null}">
                        <div class="alert alert-danger center" style="margin-top: 15px; margin-bottom:0;">Invalid
                            username and/or password
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
    <footer class="login-footer">
        <div class='container'>
            <div class='row'>
                <div class='col-md-12'>
                    <a href="https://ivanbattimiello.dev/" target="_blank">
                        <img src="/img/dashboard/poweredby.png"
                             class="img-responsive login-m3-logo centerE"
                             alt="Responsive image">
                    </a>
                </div>
            </div>
        </div>
    </footer>
</div>

</body>
</html>