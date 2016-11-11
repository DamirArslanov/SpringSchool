<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 07.11.2016
  Time: 5:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="<c:url value="/resources/css/Base.css" />" rel="stylesheet">

    <title>Задайте часы уроков</title>
</head>
<body>
<ul>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li><a href="<c:url value="/admin"/>">Админская панель</a></li>
        <li><a class="active" href="<c:url value="/admin/lessontime"/>">Часы занятий</a></li>
        <li><a href="<c:url value="/admin/teachers"/>">Учителя</a></li>
        <li><a href="<c:url value="/admin/schoolclasses"/>">Классы</a></li>
        <li><a href="<c:url value="/admin/subjects"/>">Предметы</a></li>
        <li><a href="<c:url value="/admin/childrens"/>">Список учеников</a></li>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
        <li><a href="<c:url value="/admin/schedules"/>">Расписание</a></li>
        <li><a href="<c:url value="/work/tlessons"/>">Лекции</a></li>
    </sec:authorize>
        <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
</ul>
<h1>Задайте часы уроков</h1>
<c:if test="${!empty lessonTimeList}">
    <table>
        <tr>
            <th>№</th>
            <th>Время</th>
            <th>Удалить</th>
        </tr>
        <c:forEach items="${lessonTimeList}" var="lessonTime" varStatus="count">
            <tr>
                <td>${count.count}</td>
                <td>${lessonTime.time}</td>
                <td><a href="<c:url value="/admin/lessontime/${lessonTime.timeID}"/>">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:url var="addAction" value="/admin/lessontime/add"/>
<form:form action="${addAction}" commandName="lessonTime">
    <table>
        <c:if test="${!empty lessonTime.timeID}">
            <tr>
                <td>
                    <form:hidden path="timeID" />
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <form:label path="time">
                    <spring:message text="Время"/>
                </form:label>
            </td>
            <td>
                <form:input path="time"  placeholder="формат ЧАС:МИНУТЫ Пример: 8:45" pattern="([01]?[0-9]|2[0-3]):[0-5][0-9]"/>
            </td>
        </tr>

        <tr>
            <td colspan="2"> <input type="submit" value="<spring:message text="Добавить время"/>"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>
