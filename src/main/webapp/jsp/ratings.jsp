<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 28.09.2016
  Time: 22:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@ page session="false" %>
<html>
    <title>Редактирование оценки</title>
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
        <li><a class="active" href="<c:url value="/lesson/update/${rating.lesson.less_id}"/>">Вернуться к лекции</a></li>
        <li><a href="<c:url value="/work/workpage"/>">Учительская</a></li>
    </sec:authorize>
</ul>
<h1>Редактирование оценки ${rating.children.FIO}</h1>
<c:url var="addAction" value="/ratings/add"/>
<form:form action="${addAction}" commandName="rating">
    <table>
        <tr>
            <td>
                <form:label path="rt_Date">
                    <spring:message text="Дата оценки"/>
                </form:label>
            </td>
            <td>
                <form:input path="rt_Date"  placeholder="День/Месяц/Год"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="children.ch_id">
                    <spring:message text="Учащийся"/>
                </form:label>
            </td>
            <td>
                <form:input path="children.FIO" readonly="true"  disabled="true"/>
                <form:hidden path="children.ch_id"/>
            </td>
            <td><form:hidden path="children.ch_id"/></td>
        </tr>
        <tr>
            <td>
                <form:label path="subject.sub_id">
                    <spring:message text="Предмет"/>
                </form:label>
            </td>
            <td>
                <form:input path="subject.sub_name" readonly="true"  disabled="true"/>
                <form:hidden path="subject.sub_id"/>
            </td>
            <td><form:hidden path="subject.sub_id"/></td>
        </tr>
        <tr>
            <td>
                <form:label path="evaluation">
                <spring:message text="Оценка &nbsp; : "/>
                    <c:choose>
                        <c:when test="${rating.evaluation == 0}">Отсутствовал</c:when>
                        <c:when test="${rating.evaluation == 1}">Присутствовал</c:when>
                        <c:otherwise>${rating.evaluation}</c:otherwise>
                    </c:choose>
                </form:label>
            </td>
            <td>
                <form:select path="evaluation" >
                    <form:options items="${selectMap}" />
                </form:select>
            </td>
        </tr>
        <tr>
            <c:if test="${!empty rating.rt_id}">
                <form:hidden path="lesson.less_id"/>
                <form:hidden path="rt_id"/>

            </c:if>
            <td colspan="2">
                    <input type="submit"
                           value="<spring:message text="Редактировать"/>"/>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>