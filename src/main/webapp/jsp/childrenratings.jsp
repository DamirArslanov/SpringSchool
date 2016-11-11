
<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 17.10.2016
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Оценки ${children.FIO}</title>
    <link href="<c:url value="/resources/css/rating.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/w3.css" />" rel="stylesheet">
</head>
<body>
<ul style="font-size: 14px;">
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <li><a  href="<c:url value="/admin"/>">Админская панель</a></li>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
    <li><a href="<c:url value="/admin/schedules"/>">Расписание</a></li>
    <li><a href="<c:url value="/work/tlessons"/>">Лекции</a></li>
    <li><a href="<c:url value="/work/classinfo/${children.schoolClass.class_id}"/>">Класс</a></li>
    <li><a class="active" href="<c:url value="/childreninfo/${children.ch_id}"/>">Информация о ${children.FIO}</a></li>
    <li><a href="<c:url value="/work/workpage"/>">Учительская</a></li>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_USER')">
    <li><a  href="<c:url value="/childreninfo/${children.ch_id}"/>">Общая информация</a></li>
    <li><a  class="active" href="<c:url value="/childreninfo/childrenratings/${children.ch_id}"/>">Оценки</a></li>
    <li><a  href="<c:url value="/teacherinfo/${children.schoolClass.teacher.t_id}"/>">Классный руководитель</a></li>
    <li><a  href="<c:url value="/childreninfo/${children.ch_id}#notices"/>">Сообщения классу</a></li>
    <li><a  href="<c:url value="/childreninfo/timetable/${children.schoolClass.class_id}"/>">Расписание уроков</a></li>
</sec:authorize>
    <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
    <sec:authorize access="isAuthenticated()">
        <li style="float:right"><a href="<c:url value="/logout" />">Выйти из системы</a></li>
    </sec:authorize>
</ul>
<hr/>
<nav class="w3-sidenav w3-light-grey w3-card-2" style="width:160px; background: #F8E391;">
        <c:if test="${!empty ratings}">
                <c:forEach items="${subjectSet}" var="subject">
                   <td style="background: #F8E391;"><a href="#${subject.sub_name}">${subject.sub_name}</a></td>
                </c:forEach>
        </c:if>
        <c:if test="${empty ratings}">
            <div align="center"><h2>Оценок нет.</h2></div>
        </c:if>
</nav>

<c:if test="${!empty ratings}">
    <div class="w3-container" style="margin-left:160px">
    <c:forEach items="${subjectSet}" var="subject" varStatus="index">
        <table>
            <tr>
                <th class="my"><a name="${subject.sub_name}">${subject.sub_name}</a></th>
            </tr>

            <tr>
                <c:forEach items="${ratings}" var="rating">
                    <c:if test="${rating.subject.sub_id == subject.sub_id}">
                        <th><fmt:formatDate pattern="dd/MM/yy" value="${rating.rt_Date}" /></th>
                    </c:if>
                </c:forEach>
            </tr>
            <tr>
                <c:forEach items="${ratings}" var="rating">
                    <c:if test="${rating.subject.sub_id == subject.sub_id}">
                        <c:choose>
                            <c:when test="${rating.evaluation == 0}">
                                <td>Н</td>
                            </c:when>
                            <c:when test="${rating.evaluation == 1}">
                                <td>П</td>
                            </c:when>
                            <c:otherwise>
                                <td>${rating.evaluation}</td>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:forEach>
            </tr>
        </table>
    </c:forEach>
</div>
</c:if>
<c:if test="${empty ratings}">
    <div class="w3-container" style="margin-left:160px">
        <div align="center"><h2>Оценок нет.</h2></div>
    </div>
</c:if>
</body>
</html>
