<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><spring:message code="items.page.title"/></title>

    <link rel="stylesheet" href="https://bootswatch.com/4/flatly/bootstrap.min.css">


    <link rel="stylesheet" href="<c:url value="/assets/styles/bootstrap-overrides.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/styles/global.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/styles/items.css"/>">
</head>
<body>
<div class="container mt-4">
    <header class="d-flex justify-content-center">
        <a href="<c:url value="/"/>">
            <img src="<c:url value="/assets/images/logo.png"/>" class="logo">
        </a>
    </header>

    <h1 class="mt-5 display-4 text-center main-title">
        <spring:message code="items.title"/>
    </h1>

    <c:if test="${not empty item_error}">
        <div class="my-3 item-container mx-auto">

            <div class="alert alert-danger">
                <span style="font-size: 0.85rem"><c:out value="${item_error}"/></span>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty item_info}">
        <div class="my-3 item-container mx-auto">
            <div class="alert alert-dismissible alert-success">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <span style="font-size: 0.85rem"><c:out value="${item_info}"/></span>
            </div>
        </div>
    </c:if>

    <div class="my-4">
        <table class="mx-auto item-container">
            <thead>
            <tr>
                <td>
                    <spring:message code="items.table.item.title"/>
                </td>
            </tr>
            </thead>

            <tbody>
            <c:choose>
                <c:when test="${!items.isEmpty()}">
                    <c:forEach var="item" items="${items}" varStatus="loop">
                        <tr>
                            <td>
                                <span class="mr-2"><c:out value="${item.getName()}"/></span>
                                <button type="button" class="btn btn-sm btn-danger delete-button" data-toggle="modal"
                                        data-target="#deletionConfirmModal<c:out value="${item.getId()}"/>">
                                    <spring:message code="items.button.delete"/>
                                </button>

                                <!-- Modal -->
                                <div class="modal fade" id="deletionConfirmModal<c:out value="${item.getId()}"/>"
                                     tabindex="-1"
                                     aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">
                                                    <spring:message code="items.title.deletionConfirm"/>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <spring:message code="items.deletion.info"
                                                                arguments="${item.getName()}"
                                                                htmlEscape="false"/>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-primary" data-dismiss="modal">
                                                    Cancel
                                                </button>

                                                <form action="<c:url value="/weekly-meal/items/delete"/>" method="post">
                                                    <input hidden name="itemId"
                                                           value="<c:out value="${item.getId()}"/>">
                                                    <button type="submit" class="btn btn-danger">
                                                        <spring:message code="items.button.delete"/>
                                                    </button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td>
                            <spring:message code="items.noItems.title"/>
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>

    <div class="mt-5 d-flex justify-content-center">
        <div class="card item-container">
            <div class="card-body">
                <h5 class="card-title"><spring:message code="items.add.title"/></h5>
                <p class="card-text"><spring:message code="items.add.info"/></p>

                <c:url value="/weekly-meal/items" var="formPath"/>

                <form:form cssClass="form-inline" action="${formPath}" method="post" modelAttribute="itemData">
                    <spring:message code="items.add.name" var="itemsName"/>

                    <form:errors path="*" cssClass="mb-4 d-block alert alert-dismissible alert-danger"/>
                    <form:input path="itemName" cssClass="form-control form-control-sm mr-3"
                                placeholder="${itemsName}" required="required" type="text"/>

                    <button type="submit" class="btn btn-sm btn-info">
                        <spring:message code="items.button.add"/>
                    </button>
                </form:form>
            </div>
        </div>
    </div>

    <div class="d-flex justify-content-center my-5">
        <a href="<c:url value="/weekly-meal/menu"/>" class="no-decoration">
            <button type="button" class="btn btn-sm btn-outline-primary mr-3">
                <spring:message code="nav.menu"/>
            </button>
        </a>

        <button disabled type="button" class="btn btn-sm btn-outline-primary mr-5">
            <spring:message code="nav.items"/>
        </button>

        <a href="<c:url value="/logout"/>" class="no-decoration">
            <button type="button" class="btn btn-sm btn-primary">
                <spring:message code="nav.logout"/>
            </button>
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
