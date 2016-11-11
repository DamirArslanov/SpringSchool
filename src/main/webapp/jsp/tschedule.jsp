<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 26.09.2016
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page session="false" %>
<html>
<head>
    <link href="<c:url value="/resources/css/Base.css" />" rel="stylesheet">
    <title>Список уроков</title>
</head>
<body>
<ul>
    <li><a href="<c:url value="/admin"/>">Админская панель</a></li>
    <li><a href="<c:url value="/admin/lessontime"/>">Часы занятий</a></li>
    <li><a  href="<c:url value="/admin/teachers"/>">Учителя</a></li>
    <li><a href="<c:url value="/admin/schoolclasses"/>">Классы</a></li>
    <li><a  href="<c:url value="/admin/subjects"/>">Предметы</a></li>
    <li><a href="<c:url value="/admin/childrens"/>">Список учеников</a></li>
    <li><a class="active" href="<c:url value="/admin/schedules"/>">Уроки</a></li>
    <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
</ul>
<hr/>
<c:url var="give" value="/admin/schedules/"/>
<%--<div>--%>
    <%--<form:form modelAttribute="ScheduleForm" method="POST" action="${give}" commandName="ScheduleForm">--%>
        <%--<table>--%>
            <%--<tr>--%>

                <%--<td>--%>
                    <%--<form:select path="schoolClass"  >--%>
                        <%--<form:option label="Выбрать класс" value="null"/>--%>
                        <%--<form:options items="${schoolclasses}" itemLabel="class_name"  itemValue="class_id"/>--%>
                    <%--</form:select>--%>
                <%--</td>--%>



                <%--<td>--%>
                    <%--<form:select path="teacher"  >--%>
                        <%--<form:option label="Выбрать учителя" value="null"/>--%>
                        <%--<form:options items="${teachers}" itemLabel="t_name"  itemValue="t_id"/>--%>
                    <%--</form:select>--%>

                <%--</td>--%>

                <%--<td><input type="submit" value="Вывести"/></td>--%>
            <%--</tr>--%>
        <%--</table>--%>
    <%--</form:form>--%>
<%--</div>--%>
<h1>Список уроков</h1>
<table class="equal-width-cols">
    <tr  valign="top">
        <th style="background: #fdfdff" align="center">
            <table>
                <c:if test="${!empty schedules}">
                    <tr>
                        <td class="my" colspan="4">Понедельник</td>
                    </tr>
                    <tr>
                        <th>Класс</th>
                        <th>Предмет</th>
                        <th>Учитель</th>
                        <th>Перейти к уроку</th>
                    </tr>
                    <c:forEach items="${schedules}" var="schedule">
                        <c:if test="${schedule.weekday.week_id == 1}">
                            <tr>
                                <td>${schedule.schoolClass.class_name}</td>
                                <td>${schedule.subject.sub_name}</td>
                                <td>${schedule.teacher.FIO}</td>
                                <td><a href="<c:url value="/lesson/${schedule.shed_id}"/>">Урок</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:if>
            </table>
        </th>
        <th style="background: #fdfdff"  align="center">
            <table>
                <c:if test="${!empty schedules}">
                    <tr>
                        <td class="my" colspan="4">Вторник</td>
                    </tr>
                    <tr>
                        <th>Класс</th>
                        <th>Предмет</th>
                        <th>Учитель</th>
                        <th>Перейти к уроку</th>
                    </tr>
                    <c:forEach items="${schedules}" var="schedule">
                        <c:if test="${schedule.weekday.week_id == 2}">
                            <tr>
                                <td>${schedule.schoolClass.class_name}</td>
                                <td>${schedule.subject.sub_name}</td>
                                <td>${schedule.teacher.FIO}</td>
                                <td><a href="<c:url value="/lesson/${schedule.shed_id}"/>">Урок</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:if>
            </table>
        </th>
        <th style="background: #fdfdff"  align="center">
            <table>
                <c:if test="${!empty schedules}">

                    <tr>
                        <td class="my"  colspan="4">Среда</td>


                    </tr>
                    <tr>
                        <th>Класс</th>
                        <th>Предмет</th>
                        <th>Учитель</th>
                        <th>Перейти к уроку</th>
                    </tr>
                    <c:forEach items="${schedules}" var="schedule">
                        <c:if test="${schedule.weekday.week_id == 3}">
                            <tr>
                                <td>${schedule.schoolClass.class_name}</td>
                                <td>${schedule.subject.sub_name}</td>
                                <td>${schedule.teacher.FIO}</td>
                                <td><a href="<c:url value="/lesson/${schedule.shed_id}"/>">Урок</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>

                </c:if>
            </table>
        </th>
    </tr>
</table>
<hr/>
<table class="equal-width-cols">
    <tr  valign="top">
        <th style="background: #fdfdff"    align="center">
            <table>
                <c:if test="${!empty schedules}">
                    <tr>
                        <td  class="my" colspan="4">Четверг</td>
                    </tr>
                    <tr>
                        <th>Класс</th>
                        <th>Предмет</th>
                        <th>Учитель</th>
                        <th>Перейти к уроку</th>
                    </tr>
                    <c:forEach items="${schedules}" var="schedule">
                        <c:if test="${schedule.weekday.week_id == 4}">
                            <tr>
                                <td>${schedule.schoolClass.class_name}</td>
                                <td>${schedule.subject.sub_name}</td>
                                <td>${schedule.teacher.FIO}</td>
                                <td><a href="<c:url value="/lesson/${schedule.shed_id}"/>">Урок</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:if>
            </table>
        </th>
        <th  style="background: #fdfdff" align="center">
            <table>
                <c:if test="${!empty schedules}">
                    <tr>
                        <td  class="my" colspan="4">Пятница</td>
                    </tr>
                    <tr>
                        <th>Класс</th>
                        <th>Предмет</th>
                        <th>Учитель</th>
                        <th>Перейти к уроку</th>
                    </tr>
                    <c:forEach items="${schedules}" var="schedule">
                        <c:if test="${schedule.weekday.week_id == 5}">
                            <tr>
                                <td>${schedule.schoolClass.class_name}</td>
                                <td>${schedule.subject.sub_name}</td>
                                <td>${schedule.teacher.FIO}</td>
                                <td><a href="<c:url value="/lesson/${schedule.shed_id}"/>">Урок</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:if>
            </table>
        </th>
        <th style="background: #fdfdff"  align="center">
            <table>
                <c:if test="${!empty schedules}">

                    <tr>
                        <td class="my"  colspan="4">Суббота</td>
                    </tr>
                    <tr>
                        <th>Класс</th>
                        <th>Предмет</th>
                        <th>Учитель</th>
                        <th>Перейти к уроку</th>
                    </tr>
                    <c:forEach items="${schedules}" var="schedule">
                        <c:if test="${schedule.weekday.week_id == 6}">
                            <tr>
                                <td>${schedule.schoolClass.class_name}</td>
                                <td>${schedule.subject.sub_name}</td>
                                <td>${schedule.teacher.FIO}</td>
                                <td><a href="<c:url value="/lesson/${schedule.shed_id}"/>">Урок</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>

                </c:if>
            </table>
        </th>
    </tr>
</table>
<hr/>
<%--<c:url var="addAction" value="/admin/schedules/add"/>--%>
<%--<form:form action="${addAction}" commandName="schedule">--%>
    <%--<table>--%>
        <%--<c:if test="${!empty schedule.shed_id}">--%>
            <%--<tr>--%>
                <%--<td>--%>
                    <%--<form:label path="shed_id">--%>
                        <%--<spring:message text="ID"/>--%>
                    <%--</form:label>--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<form:input path="shed_id" readonly="true"  disabled="true"/>--%>
                    <%--<form:hidden path="shed_id"/>--%>
                <%--</td>--%>
            <%--</tr>--%>
        <%--</c:if>--%>
        <%--<tr>--%>
            <%--<td>День недели</td>--%>
            <%--<td><form:select path="weekday.week_id" items="${weekdays}" selected="true"--%>
                             <%--itemLabel="week_name"--%>
                             <%--itemValue="week_id"/></td>--%>
            <%--<td><form:errors path="weekday"/></td>--%>
        <%--</tr>--%>



        <%--<tr>--%>
            <%--<td>Предмет</td>--%>
            <%--<td><form:select path="subject.sub_id" items="${subjects}" selected="true"--%>
                             <%--itemLabel="sub_name"--%>
                             <%--itemValue="sub_id"/></td>--%>
            <%--<td><form:errors path="subject"/></td>--%>
        <%--</tr>--%>


        <%--<tr>--%>
            <%--<td>Класс</td>--%>
            <%--<td><form:select path="schoolClass.class_id" items="${schoolclasses}" itemLabel="class_name" itemValue="class_id"/></td>--%>
            <%--<td><form:errors path="schoolClass"/></td>--%>
        <%--</tr>--%>

        <%--<tr>--%>
            <%--<td>Учитель</td>--%>
            <%--<td><form:select path="teacher.t_id" items="${teachers}" itemLabel="t_name" itemValue="t_id"/></td>--%>
            <%--<td><form:errors path="teacher"/></td>--%>

        <%--</tr>--%>



        <%--<tr>--%>
            <%--<td colspan="2">--%>
                <%--<c:if test="${!empty schedule.schoolClass}">--%>
                    <%--<input type="submit"--%>
                           <%--value="<spring:message text="Edit Schedule"/>"/>--%>
                <%--</c:if>--%>
                <%--<c:if test="${empty schedule.schoolClass}">--%>
                    <%--<input type="submit"--%>
                           <%--value="<spring:message text="Add Schedule"/>"/>--%>
                <%--</c:if>--%>
            <%--</td>--%>
        <%--</tr>--%>
    <%--</table>--%>
<%--</form:form>--%>


</body>
</html>