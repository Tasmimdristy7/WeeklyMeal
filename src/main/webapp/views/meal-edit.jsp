<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><spring:message code="mealEdit.page.title"/></title>

    <link rel="stylesheet" href="https://bootswatch.com/4/flatly/bootstrap.min.css">

    <link rel="stylesheet" href="<c:url value="/assets/styles/bootstrap-overrides.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/styles/global.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/styles/meal-edit.css"/>">
</head>
<body>
<div class="container mt-4">
    <header class="d-flex justify-content-center">
        <a href="<c:url value="/"/>">
            <img src="<c:url value="/assets/images/logo.png"/>" class="logo">
        </a>
    </header>

    <h1 class="mt-5 display-4 text-center main-title">
        <c:out value="${actionMode}"/> Menu
    </h1>

    <c:if test="${not empty errors}">
        <div class="mt-3 w-50 item-container mx-auto">
            <div class="alert alert-danger">
                <c:forEach var="error" items="${errors}">
                    <span style="font-size: 0.85rem">
                        <c:out value="${error.getDefaultMessage()}"/>
                    </span>
                </c:forEach>
            </div>
        </div>
    </c:if>

    <form:form action="/weekly-meal/menu/edit" method="post" modelAttribute="mealData">
        <div class="mt-1 w-50 mx-auto edit-container">
            <h5><spring:message code="mealEdit.title"/></h5>
            <h6 class="mt-3">
                <spring:message code="mealEdit.chosenDay"/> <span class="text-info"><c:out
                    value="${mealData.getDay().toString()}"/></span>
            </h6>
            <h6>
                <spring:message code="mealEdit.chosenSlot"/> <span class="text-info"><c:out
                    value="${mealData.getSlot().toString()}"/></span>
            </h6>

            <h5 class="mt-5 pt-2"><spring:message code="mealEdit.items.title"/></h5>
            <div class="mt-3">
                <form:hidden path="day"/>
                <form:hidden path="slot"/>

                <c:choose>
                    <c:when test="${!items.isEmpty()}">
                        <div class="form-checkbox-container">
                            <form:checkboxes path="itemIds" items="${items}"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <span class="text-danger"><spring:message code="mealEdit.noItemWarning"/></span>
                    </c:otherwise>
                </c:choose>

                <div class="mt-5">
                    <button type="submit"
                            <c:if test="${items.isEmpty()}">disabled</c:if> class="btn btn-primary"><c:out
                            value="${actionMode}"/> Meal
                    </button>
                </div>
            </div>
        </div>
    </form:form>


    <div class="d-flex justify-content-center my-5">
        <a href="<c:url value="/weekly-meal/menu"/>" class="no-decoration">
            <button type="button" class="btn btn-sm btn-outline-primary mr-3">
                <spring:message code="nav.menu"/>
            </button>
        </a>

        <a href="<c:url value="/weekly-meal/items"/>" class="no-decoration">
            <button type="button" class="btn btn-sm btn-outline-primary mr-5">
                <spring:message code="nav.items"/>
            </button>
        </a>

        <a href="<c:url value="/logout"/>" class="no-decoration">
            <button type="button" class="btn btn-sm btn-primary"><spring:message code="nav.logout"/></button>
        </a>
    </div>
</div>

<!-- Bootstrap Files -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>
</body>
</html>
