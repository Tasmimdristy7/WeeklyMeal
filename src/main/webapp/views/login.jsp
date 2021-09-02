<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><spring:message code="login.page.title"/></title>
    <link rel="stylesheet" href="https://bootswatch.com/4/flatly/bootstrap.min.css">


    <link rel="stylesheet" href="<c:url value="/assets/styles/bootstrap-overrides.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/styles/global.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/styles/login.css"/>">
</head>
<body>
<div class="container mt-4">
    <header class="d-flex justify-content-center">
        <a href="<c:url value="/"/>">
            <img src="<c:url value="/assets/images/logo.png"/>" class="logo">
        </a>
    </header>

    <h1 class="mt-5 display-4 text-center main-title">
        <spring:message code="login.page.header"/>
    </h1>

    <div class="login-form mt-5">
        <form:form action="/" method="post" modelAttribute="loginFormData">
            <div class="form-group">
                <form:label path="username">
                    <spring:message code="login.page.prompt.username"/>
                </form:label>
                <form:input path="username" cssClass="form-control" cssErrorClass="form-control is-invalid"/>

            </div>
            <div class="form-group">
                <form:label path="password">
                    <spring:message code="login.page.prompt.password"/>
                </form:label>
                <form:password path="password" cssClass="form-control" cssErrorClass="form-control is-invalid"/>
            </div>

            <form:errors path="*" cssClass="mt-3 mb-4 d-block alert alert-dismissible alert-danger"/>

            <button type="submit" class="btn btn-primary mb-4 btn-block">
                <spring:message code="login.button.login"/>
            </button>
        </form:form>
    </div>
</div>
</body>
</html>


