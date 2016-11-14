<%--
  Created by IntelliJ IDEA.
  User: ArslanovDamir
  Date: 10.10.2016
  Time: 16:08
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
    <title>Администрирование</title>
    <link href="<c:url value="/resources/css/Base.css" />" rel="stylesheet">

</head>
<body>
<ul>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li><a class="active" href="<c:url value="/admin"/>">Админская панель</a></li>
        <li><a href="<c:url value="/admin/lessontime"/>">Часы занятий</a></li>
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
    <sec:authorize access="isAnonymous()">
        <li style="float:right"><a href="<c:url value="/login"/>">Войти</a></li>
    </sec:authorize>
</ul>
</body>
</html>
