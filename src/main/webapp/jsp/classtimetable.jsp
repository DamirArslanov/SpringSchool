<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 29.10.2016
  Time: 18:06
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
        <link href="<c:url value="/resources/css/ul.css" />" rel="stylesheet">
    <title>Расписание</title>
</head>
<body>
<ul>
    <sec:authorize access="hasRole('ROLE_USER')">
        <li><a  href="<c:url value="/childreninfo/${children.ch_id}"/>">Общая информация</a></li>
        <li><a  href="<c:url value="/childreninfo/childrenratings/${children.ch_id}"/>">Оценки</a></li>
        <li><a  href="<c:url value="/teacherinfo/${children.schoolClass.teacher.t_id}"/>">Классный руководитель</a></li>
        <li><a  href="<c:url value="/childreninfo/${children.ch_id}#notices"/>">Сообщения классу</a></li>
        <li><a   class="active"  href="<c:url value="/childreninfo/timetable/${children.schoolClass.class_id}"/>">Расписание уроков</a></li>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li><a href="<c:url value="/admin"/>">Админская панель</a></li>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
        <li><a  class="active" href="<c:url value="/admin/schedules"/>">Расписание</a></li>
        <li><a href="<c:url value="/work/tlessons"/>">Лекции</a></li>
        <li><a href="<c:url value="/work/classinfo/${schedule.schoolClass.class_id}"/>">Класс</a></li>
        <li><a href="<c:url value="/work/workpage"/>">Учительская</a></li>
        <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <li style="float:right"><a href="<c:url value="/logout" />">Выйти из системы</a></li>
    </sec:authorize>
</ul>
    <c:if test="${!empty schedules}">
        <table class="equal-width-cols">
            <tr  valign="top">
                <th style="background: #fdfdff" align="center">
                    <table>
                        <c:if test="${!empty schedules}">
                            <tr>
                                <td class="my" colspan="6">Понедельник</td>
                            </tr>
                            <tr>
                                <th>Время</th>
                                <th>Класс</th>
                                <th>Предмет</th>
                                <th>Учитель</th>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <th>&nbsp</th>
                                    <th>&nbsp</th>
                                </sec:authorize>
                            </tr>
                            <c:forEach items="${schedules}" var="schedule">
                                <c:if test="${schedule.weekday.week_id == 1}">
                                    <tr>
                                        <td>${schedule.time}</td>
                                        <td>${schedule.schoolClass.class_name}</td>
                                        <td>${schedule.subject.sub_name}</td>
                                        <td>${schedule.teacher.FIO}</td>
                                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                                            <td><a href="<c:url value="/work/timetable/edit/${schedule.shed_id}"/>">Ред.</a></td>
                                            <td><a href="<c:url value="/work/timetable/delete/${schedule.shed_id}"/>">Удл.</a></td>
                                        </sec:authorize>
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
                                <td class="my" colspan="6">Вторник</td>
                            </tr>
                            <tr>
                                <th>Время</th>
                                <th>Класс</th>
                                <th>Предмет</th>
                                <th>Учитель</th>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <th>&nbsp</th>
                                    <th>&nbsp</th>
                                </sec:authorize>
                            </tr>
                            <c:forEach items="${schedules}" var="schedule">
                                <c:if test="${schedule.weekday.week_id == 2}">
                                    <tr>
                                        <td>${schedule.time}</td>
                                        <td>${schedule.schoolClass.class_name}</td>
                                        <td>${schedule.subject.sub_name}</td>
                                        <td>${schedule.teacher.FIO}</td>
                                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                                            <td><a href="<c:url value="/work/timetable/edit/${schedule.shed_id}"/>">Ред.</a></td>
                                            <td><a href="<c:url value="/work/timetable/delete/${schedule.shed_id}"/>">Удл.</a></td>
                                        </sec:authorize>
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
                                <td class="my"  colspan="6">Среда</td>
                            </tr>
                            <tr>
                                <th>Время</th>
                                <th>Класс</th>
                                <th>Предмет</th>
                                <th>Учитель</th>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <th>&nbsp</th>
                                    <th>&nbsp</th>
                                </sec:authorize>
                            </tr>
                            <c:forEach items="${schedules}" var="schedule">
                                <c:if test="${schedule.weekday.week_id == 3}">
                                    <tr>
                                        <td>${schedule.time}</td>
                                        <td>${schedule.schoolClass.class_name}</td>
                                        <td>${schedule.subject.sub_name}</td>
                                        <td>${schedule.teacher.FIO}</td>
                                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                                            <td><a href="<c:url value="/work/timetable/edit/${schedule.shed_id}"/>">Ред.</a></td>
                                            <td><a href="<c:url value="/work/timetable/delete/${schedule.shed_id}"/>">Удл.</a></td>
                                        </sec:authorize>
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
                                <td  class="my" colspan="6">Четверг</td>
                            </tr>
                            <tr>
                                <th>Время</th>
                                <th>Класс</th>
                                <th>Предмет</th>
                                <th>Учитель</th>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <th>&nbsp</th>
                                    <th>&nbsp</th>
                                </sec:authorize>
                            </tr>
                            <c:forEach items="${schedules}" var="schedule">
                                <c:if test="${schedule.weekday.week_id == 4}">
                                    <tr>
                                        <td>${schedule.time}</td>
                                        <td>${schedule.schoolClass.class_name}</td>
                                        <td>${schedule.subject.sub_name}</td>
                                        <td>${schedule.teacher.FIO}</td>
                                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                                            <td><a href="<c:url value="/work/timetable/edit/${schedule.shed_id}"/>">Ред.</a></td>
                                            <td><a href="<c:url value="/work/timetable/delete/${schedule.shed_id}"/>">Удл.</a></td>
                                        </sec:authorize>
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
                                <td  class="my" colspan="6">Пятница</td>
                            </tr>
                            <tr>
                                <th>Время</th>
                                <th>Класс</th>
                                <th>Предмет</th>
                                <th>Учитель</th>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <th>&nbsp</th>
                                    <th>&nbsp</th>
                                </sec:authorize>
                            </tr>
                            <c:forEach items="${schedules}" var="schedule">
                                <c:if test="${schedule.weekday.week_id == 5}">
                                    <tr>
                                        <td>${schedule.time}</td>
                                        <td>${schedule.schoolClass.class_name}</td>
                                        <td>${schedule.subject.sub_name}</td>
                                        <td>${schedule.teacher.FIO}</td>
                                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                                            <td><a href="<c:url value="/work/timetable/edit/${schedule.shed_id}"/>">Ред.</a></td>
                                            <td><a href="<c:url value="/work/timetable/delete/${schedule.shed_id}"/>">Удл.</a></td>
                                        </sec:authorize>
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
                                <td class="my"  colspan="6">Суббота</td>
                            </tr>
                            <tr>
                                <th>Время</th>
                                <th>Класс</th>
                                <th>Предмет</th>
                                <th>Учитель</th>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <th>&nbsp</th>
                                    <th>&nbsp</th>
                                </sec:authorize>
                            </tr>
                            <c:forEach items="${schedules}" var="schedule">
                                <c:if test="${schedule.weekday.week_id == 6}">
                                    <tr>
                                        <td>${schedule.time}</td>
                                        <td>${schedule.schoolClass.class_name}</td>
                                        <td>${schedule.subject.sub_name}</td>
                                        <td>${schedule.teacher.FIO}</td>
                                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                                            <td><a href="<c:url value="/work/timetable/edit/${schedule.shed_id}"/>">Ред.</a></td>
                                            <td><a href="<c:url value="/work/timetable/delete/${schedule.shed_id}"/>">Удл.</a></td>
                                        </sec:authorize>
                                    </tr>
                                </c:if>
                            </c:forEach>

                        </c:if>
                    </table>
                </th>
            </tr>
        </table>
    </c:if>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <hr>
    <p style="font-size: large; font-family: 'Roboto', sans-serif;">${schedule.schoolClass.class_name} КЛАСС
    <a class="btn btn-info" style="background-color: #a09e96" role="button" href="<c:url value="/timetable/update/${classID}"/>">Обновить расписание на эту неделю</a>
    <a class="btn btn-info" style="background-color: #a09e96" role="button" href="<c:url value="/timetable/create/${classID}"/>">Создать расписание на следующую неделю</a>
    </p>
    <hr/>
<c:url var="addAction" value="/work/timetable/add"/>
<div class="container">

    <form:form action="${addAction}" commandName="schedule"  class="form-horizontal">

        <div class="form-group">
            <form:label path="weekday" class="control-label col-sm-2">
                <b>День недели</b>
            </form:label>
            <div class="col-sm-6">
                <form:select path="weekday.week_id" items="${weekdays}" selected="true"
                             itemLabel="week_name"
                             itemValue="week_id"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="time" class="control-label col-sm-2">
                <b>Время</b>
            </form:label>
            <div class="col-sm-6">
                <form:select path="time" items="${lessonTimeList}" selected="true"
                             itemLabel="time"
                             itemValue="time"/>
            </div>
        </div>

        <div class="form-group">
            <form:label path="subject" class="control-label col-sm-2">
                <b>Предмет</b>
            </form:label>
            <div class="col-sm-6">
                <form:select path="subject.sub_id" items="${subjects}" selected="true"
                             itemLabel="sub_name"
                             itemValue="sub_id"/>
            </div>
        </div>

        <div class="form-group">
            <form:label path="schoolClass" class="control-label col-sm-2">
                <b>Класс</b>
            </form:label>
            <div class="col-sm-6">
                <form:select path="schoolClass.class_id" items="${schoolclasses}" selected="true"
                             itemLabel="class_name"
                             itemValue="class_id"/>
            </div>
        </div>

        <div class="form-group">
            <form:label path="teacher" class="control-label col-sm-2">
                <b>Учитель</b>
            </form:label>
            <div class="col-sm-6">
                <form:select path="teacher.t_id" items="${teachers}" selected="true"
                             itemLabel="FIO"
                             itemValue="t_id"/>
            </div>
        </div>

        <c:if test="${!empty schedule.shed_id}">
            <form:hidden path="shed_id"/>
        </c:if>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="submit" class="btn btn-default" value="<spring:message text="Сохранить"/>"/>
            </div>
        </div>

    </form:form>
</div>
</sec:authorize>
</body>
</html>