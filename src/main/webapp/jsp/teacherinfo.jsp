<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 18.10.2016
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Информация о ${teacher.FIO}</title>
    <link href="<c:url value="/resources/css/Base.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/bootstrap/js/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
</head>
<body>

<ul>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li><a  href="<c:url value="/admin"/>">Админская панель</a></li>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
        <li><a href="<c:url value="/admin/schedules"/>">Расписание</a></li>
        <li><a href="<c:url value="/work/tlessons"/>">Лекции</a></li>
        <li><a href="<c:url value="/work/classinfo"/>">Класс</a></li>
        <li><a href="<c:url value="/work/workpage"/>">Учительская</a></li>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_USER')">
        <li><a href="<c:url value="/childreninfo/${childrenID}"/>">Общая информация</a></li>
        <li><a href="<c:url value="/childreninfo/childrenratings/${childrenID}"/>">Оценки</a></li>
        <li><a class="active" href="<c:url value="/teacherinfo/${teacher.t_id}"/>">Классный руководитель</a></li>
        <li><a href="<c:url value="/childreninfo/${childrenID}#notices"/>">Сообщения классу</a></li>
        <li><a  href="<c:url value="/childreninfo/timetable/${teacher.schoolClass.class_id}"/>">Расписание уроков</a></li>
    </sec:authorize>
    <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
    <sec:authorize access="isAuthenticated()">
        <li style="float:right"><a href="<c:url value="/logout" />">Выйти из системы</a></li>
    </sec:authorize>
</ul>
<hr/>
<c:if test="${!empty teacher}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-6 g5" style="background-color:#4CAF50; font-size: 20px">Информация об учителе</div>
        </div>
        <div class="row">
            <div><img class="img-responsive" alt="Foto" width="100" src="${pageContext.request.contextPath}/image/${teacher.image.id}"></div>
        </div>
        <div class="row g5">
            <div class="col-sm-3">ФАМИЛИЯ</div>
            <div class="col-sm-3">ИМЯ</div>
            <div class="col-sm-3">ОТЧЕСТВО</div>
            <div class="col-sm-3">Телефон</div>

        </div>
        <div class="row g6">
            <div class="col-sm-3">${teacher.t_surname}</div>
            <div class="col-sm-3">${teacher.t_name}</div>
            <div class="col-sm-3">${teacher.t_pname}</div>
            <div class="col-sm-3">${teacher.teacherPhone}</div>
        </div>
    </div>
</c:if>
</body>
</html>
