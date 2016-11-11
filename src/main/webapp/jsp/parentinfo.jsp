<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 19.10.2016
  Time: 5:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Редактирование родителя ${user.children.FIO}</title>
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
        <li><a class="active" href="<c:url value="/childreninfo/${children.ch_id}"/>">Информация о ${children.FIO}</a></li>
        <li><a href="<c:url value="/work/workpage"/>">Учительская</a></li>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_USER')">
        <li><a  class="active"  href="<c:url value="/childreninfo/${user.children.ch_id}"/>">Общая информация</a></li>
        <li><a href="<c:url value="/childreninfo/childrenratings/${user.children.ch_id}"/>">Оценки</a></li>
        <li><a  href="<c:url value="/teacherinfo/${user.children.teacher.t_id}"/>">Классный руководитель</a></li>
        <li><a  href="<c:url value="/childreninfo/${user.children.ch_id}#notices"/>">Сообщения классу</a></li>
        <li><a  href="<c:url value="/childreninfo/timetable/${user.children.schoolClass.class_id}"/>">Расписание уроков</a></li>
    </sec:authorize>
    <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
    <sec:authorize access="isAuthenticated()">
        <li style="float:right"><a href="<c:url value="/logout" />">Выйти из системы</a></li>
    </sec:authorize>
</ul>
<c:url var="addAction" value="/childreninfo/parent"/>
<form:form action="${addAction}" commandName="user">
    <table>
        <c:if test="${!empty user.parentId}">
            <tr>
                <td>
                    <form:hidden path="children.ch_id"/>
                </td>
                <td>
                    <form:hidden path="username"/>
                </td>
                <td>
                    <form:hidden path="password"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="parentId">
                        <spring:message text="ID"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="parentId" readonly="true"  disabled="true"/>
                    <form:hidden path="parentId"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <form:label path="parentSurname">
                    <spring:message text="Фамилия"/>
                </form:label>
            </td>
            <td>
                <form:input path="parentSurname"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="parentName">
                    <spring:message text="Имя"/>
                </form:label>
            </td>
            <td>
                <form:input path="parentName"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="parenPname">
                    <spring:message text="Отчество"/>
                </form:label>
            </td>
            <td>
                <form:input path="parenPname"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="parentPhone">
                    <spring:message text="Телефон"/>
                </form:label>
            </td>
            <td>
                <form:input path="parentPhone"  placeholder="формат 81234567890"/>
            </td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="<spring:message text="Сохранить"/>"/>

            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
