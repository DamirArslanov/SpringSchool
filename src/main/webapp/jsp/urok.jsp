<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 15.10.2016
  Time: 1:29
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
    <title>Урок</title>
    <link href="<c:url value="/resources/css/Base.css" />" rel="stylesheet">
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
        <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
    <sec:authorize access="isAuthenticated()">
        <li style="float:right"><a href="<c:url value="/logout" />">Выйти из системы</a></li>
    </sec:authorize>
</ul>

<c:url var="saveLesson" value="/lesson/update/${lesson.less_id}"/>
<form:form action="${saveLesson}" commandName="lesson">
    <table>
        <tr>
            <td>
                <form:hidden path="less_id"/>
            </td>
            <td>
                <form:hidden path="schedule.shed_id"/>
            </td>
        </tr>
        <tr>
            <th>Дата</th>
            <th>Предмет</th>
            <th>Класс</th>
            <th>Учитель</th>
        </tr>
        <tr>
            <td>
                <fmt:formatDate pattern="dd/MM/yyyy" value="${lesson.less_Date}" /></div>
                <form:hidden path="less_Date"/>
            </td>
            <td>${lesson.schedule.subject.sub_name}</td>
            <td>${lesson.schedule.schoolClass.class_name}</td>
            <td>${lesson.schedule.teacher.FIO}</td>
        </tr>
        <tr>
            <td colspan="4"><form:input path="description"  placeholder="Введите описание (тему) урока."/></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <th>ФИО</th>
            <th colspan="2">Оценка</th>
        </tr>
        <c:forEach items="${lesson.schedule.schoolClass.childrenList}" var="children">
            <tr>
                <td>${children.ch_surname} &nbsp;${children.ch_name}</td>
                <c:choose>
                    <c:when test="${lesson.ratingList.isEmpty()}">
                        <td><input name="ratingMap['${children.ch_id}']" value="${ratingMap.value}" placeholder="Присутствовал (по умолчанию)"/></td>
                    </c:when>
                    <c:when test="${lesson.ratingList != null}">
                        <c:forEach items="${lesson.ratingList}" var="rating">
                           <c:if test="${rating.children.ch_id == children.ch_id}">
                               <c:choose>
                                   <c:when test="${rating.evaluation == 0}">
                                       <td>Отсутствовал</td>
                                   </c:when>
                                   <c:when test="${rating.evaluation == 1}">
                                       <td>Присутствовал</td>
                                   </c:when>
                                   <c:otherwise><td>${rating.evaluation}</td></c:otherwise>
                               </c:choose>
                               <td><a href="<c:url value="/lesson/editrating/${rating.rt_id}"/>">Редактировать</a></td>
                           </c:if>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </tr>
        </c:forEach>
         <tr>
            <td colspan="3"><input type="submit" value="Сохранить"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>
