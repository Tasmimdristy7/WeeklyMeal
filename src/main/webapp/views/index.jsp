<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><spring:message code="menu.page.title"/></title>

    <link rel="stylesheet" href="https://bootswatch.com/4/flatly/bootstrap.min.css">

    <link rel="shortcut icon" href="<c:url value="/assets/images/favicon.png"/>" type="image/x-icon"/>
    <link rel="stylesheet" href="<c:url value="/assets/styles/bootstrap-overrides.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/styles/global.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/styles/index.css"/>">
</head>
<body>
<div class="container mt-4">
    <header class="d-flex justify-content-center">
        <a href="<c:url value="/"/>">
            <img src="<c:url value="/assets/images/logo.png"/>" class="logo">
        </a>
    </header>

    <h1 class="mt-5 display-4 text-center main-title">
        <spring:message code="menu.title"/>
    </h1>

    <c:if test="${not empty meal_error}">
        <div class="my-3 item-container mx-auto">
            <div class="alert alert-danger">
                <span style="font-size: 0.85rem"><c:out value="${meal_error}"/></span>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty meal_info}">
        <div class="my-3 item-container mx-auto">
            <div class="alert alert-dismissible alert-success">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <span style="font-size: 0.85rem"><c:out value="${meal_info}"/></span>
            </div>
        </div>
    </c:if>

    <div class="my-4">
        <table>
            <thead>
            <tr>
                <td>
                    <spring:message code="menu.table.day.title"/>
                </td>

                <td>
                    <spring:message code="menu.table.slot.title"/>
                </td>

                <td>
                    <spring:message code="menu.table.items.title"/>
                </td>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="dayIndex" begin="0" end="4" varStatus="loop">
                <c:set var="currentDay" value="${days[dayIndex]}"/>

                <c:forEach var="slotIndex" begin="0" end="1">
                    <tr class="slot-item-cell">

                        <c:if test="${slotIndex == 0}">
                            <td class="day-cell <c:if test="${dayIndex % 2 == 0}">day-col-bg</c:if>"
                                rowspan="2">
                                <c:out value="${currentDay}"/>
                            </td>
                        </c:if>


                        <td>
                            <c:out value="${slots[slotIndex]}"/>
                        </td>

                        <td style="position:relative;">
                            <c:set var="key" value='${currentDay.toString()}/${slots[slotIndex]}'/>
                            <c:set var="mealId" value="${currentDay.toString()}${slots[slotIndex]}"/>

                            <c:choose>
                                <c:when test="${meals.containsKey(key)}">
                                <span>
                                    <c:out value="${meals.get(key).getItemsNames()}"/>
                                </span>

                                    <div class="button-container">
                                        <a class="no-decoration"
                                           href="<c:url value="/weekly-meal/menu/edit/${key.toLowerCase()}"/>">
                                            <button type="button" class="btn btn-sm btn-info delete-button"
                                                    style="margin-right: -8px">
                                                <spring:message code="menu.button.edit"/>
                                            </button>
                                        </a>

                                        <button type="button"
                                                class="btn btn-sm btn-danger delete-button"
                                                data-toggle="modal"
                                                data-target="#deletionConfirmModal<c:out value="${mealId}"/>">
                                            <spring:message code="menu.button.meal.delete"/>
                                        </button>

                                        <!-- Modal -->
                                        <div class="modal fade"
                                             id="deletionConfirmModal<c:out value="${mealId}"/>"
                                             tabindex="-1"
                                             aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="modalLabelBreakfast">
                                                            <spring:message code="menu.title.deletionConfirm"/>
                                                        </h5>
                                                        <button type="button" class="close" data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <spring:message code="menu.deletionModal.info"
                                                                        arguments="${meals.get(key).getDescription()}"
                                                                        htmlEscape="false"/>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-primary"
                                                                data-dismiss="modal">
                                                            <spring:message code="menu.button.cancel"/>
                                                        </button>

                                                        <form action="<c:url value="/weekly-meal/menu/delete"/>"
                                                              method="post">
                                                            <input hidden name="dayOfWeek"
                                                                   value="<c:out value="${meals.get(key).getDay()}"/>">
                                                            <input hidden name="slot"
                                                                   value="<c:out value="${meals.get(key).getSlot()}"/>">
                                                            <button type="submit" class="btn btn-danger">
                                                                <spring:message code="menu.button.delete"/>
                                                            </button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted">
                                        <spring:message code="menu.table.itemsNotFoundTitle"/>
                                    </span>

                                    <div class="button-container">
                                        <a class="no-decoration"
                                           href="<c:url value='/weekly-meal/menu/edit/${key.toLowerCase()}'/>">
                                            <button type="button" class="btn btn-sm btn-info delete-button">
                                                <spring:message code="menu.button.add"/>
                                            </button>
                                        </a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="d-flex justify-content-center my-5">
        <button disabled type="button" class="btn btn-sm btn-outline-primary mr-3">
            <spring:message code="nav.menu"/>
        </button>

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
