<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 19.10.2016
  Time: 12:22
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
    <title>Добавить сообщение</title>
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
        <c:if test="${classCheck}">
            <li><a href="<c:url value="/work/classinfo"/>">Класс</a></li>
        </c:if>
        <li><a class="active" href="<c:url value="/work/workpage"/>">Учительская</a></li>
        <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
        <sec:authorize access="isAuthenticated()">
            <li style="float:right"><a href="<c:url value="/logout" />">Выйти из системы</a></li>
        </sec:authorize>
    </sec:authorize>
</ul>
<c:if test="${!empty schoolClass}">
    <c:url var="addAction" value="/work/notice/add/${schoolClass.class_id}"/>
</c:if>

<form:form action="${addAction}" commandName="notice">
    <table>
        <c:if test="${!empty notice.noticeID}">
            <tr>
                <td>
                    <form:hidden path="noticeID" />
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <form:label path="noticeDate">
                    <spring:message text="Дата сообщения"/>
                </form:label>
            </td>
            <td>
                <form:input path="noticeDate"  placeholder="День/Месяц/Год"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="message">
                    <spring:message text="Сообщение"/>
                </form:label>
            </td>
            <td>
                <form:input path="message"/>
            </td>
        </tr>

        <tr>
            <td colspan="2"> <input type="submit" value="<spring:message text="Добавить сообщение"/>"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>
