<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 19.10.2016
  Time: 5:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Домашняя страница</title>
    <link href="<c:url value="/resources/css/Base.css" />" rel="stylesheet">
</head>
<body>
<ul>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li><a  href="<c:url value="/admin"/>">Админская панель</a></li>
        <li><a href="<c:url value="/admin/lessontime"/>">Часы занятий</a></li>
        <li><a href="<c:url value="/admin/teachers"/>">Учителя</a></li>
        <li><a href="<c:url value="/admin/schoolclasses"/>">Классы</a></li>
        <li><a href="<c:url value="/admin/subjects"/>">Предметы</a></li>
        <li><a href="<c:url value="/admin/childrens"/>">Список учеников</a></li>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
        <li><a href="<c:url value="/admin/schedules"/>">Расписание</a></li>
        <li><a href="<c:url value="/work/tlessons"/>">Лекции</a></li>
    <c:if test="${classCheck}">
        <li><a href="<c:url value="/work/classinfo"/>">Класс</a></li>
    </c:if>
        <li><a class="active" href="<c:url value="/work/workpage"/>">Учительская</a></li>
    </sec:authorize>
        <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
</ul>
<hr/>
<sec:authorize access="hasRole('ROLE_ADMIN')">
<p style="margin-left: auto;
    margin-right: auto">
    <h1><a  href="<c:url value="/lesson/create"/>">ОБНОВИТЬ ОБЩЕЕ РАСПИСАНИЕ</a></h1>
</p>
</sec:authorize>
</body>
</html>
