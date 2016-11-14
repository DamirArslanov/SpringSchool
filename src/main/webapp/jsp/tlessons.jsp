<%--
  Created by IntelliJ IDEA.
  User: ArslanovDamir
  Date: 13.10.2016
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <title>Список уроков</title>
    <link href="<c:url value="/resources/css/Base.css" />" rel="stylesheet">
</head>
<body>
    <ul>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li><a href="<c:url value="/admin"/>">Админская панель</a></li>
            <li><a href="<c:url value="/admin/lessontime"/>">Часы занятий</a></li>
            <li><a href="<c:url value="/admin/teachers"/>">Учителя</a></li>
            <li><a href="<c:url value="/admin/schoolclasses"/>">Классы</a></li>
            <li><a href="<c:url value="/admin/subjects"/>">Предметы</a></li>
            <li><a href="<c:url value="/admin/childrens"/>">Список учеников</a></li>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
            <li><a href="<c:url value="/admin/schedules"/>">Расписание</a></li>
            <li><a class="active" href="<c:url value="/work/tlessons"/>">Лекции</a></li>
        <c:if test="${classCheck}">
            <li><a href="<c:url value="/work/classinfo"/>">Класс</a></li>
        </c:if>
            <li><a href="<c:url value="/work/workpage"/>">Учительская</a></li>
        </sec:authorize>
            <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
    </ul>
<hr/>
<sec:authorize access="hasRole('ROLE_ADMIN')">
<c:url var="give" value="/work/tlessons/"/>
    <div>
        <form:form modelAttribute="LessonSearchForm" method="POST" action="${give}" commandName="LessonSearchForm">
            <table>
                <tr>
                    <td>
                        <form:select path="teacher.t_id" items="${teachers}" itemLabel="FIO" itemValue="t_id"/>
                    </td>
                    <td><form:input path="startDate"  placeholder="День/Месяц/Год"/></td>
                    <td><form:input path="endDate" placeholder="День/Месяц/Год"/></td>
                    <td><input type="submit" value="Вывести"/></td>
                </tr>
            </table>
        </form:form>
    </div>
</sec:authorize>
<h1>Список лекции: ${teacher.FIO} ${teacherSearch.t_name}</h1>
<table class="equal-width-cols">
    <tr  valign="top">
        <th style="background: #fdfdff" align="center">
            <table>
                <c:if test="${!empty lessons}">
                    <tr>
                        <td class="my" colspan="4">Понедельник</td>
                    </tr>
                    <tr>
                        <th>Дата</th>
                        <th>Класс</th>
                        <th>Предмет</th>
                        <th>Перейти</th>
                    </tr>
                    <c:forEach items="${lessons}" var="lesson">
                        <c:if test="${lesson.schedule.weekday.week_id == 1}">
                            <tr>
                                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${lesson.less_Date}" /></td>
                                <td>${lesson.schedule.schoolClass.class_name}</td>
                                <td>${lesson.schedule.subject.sub_name}</td>
                                <td><a href="<c:url value="/lesson/update/${lesson.less_id}"/>">Урок</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:if>
            </table>
        </th>
        <th style="background: #fdfdff"  align="center">
            <table>
                <c:if test="${!empty lessons}">
                    <tr>
                        <td class="my" colspan="4">Вторник</td>
                    </tr>
                    <tr>
                        <th>Дата</th>
                        <th>Класс</th>
                        <th>Предмет</th>
                        <th>Перейти</th>
                    </tr>
                    <c:forEach items="${lessons}" var="lesson">
                        <c:if test="${lesson.schedule.weekday.week_id == 2}">
                            <tr>
                                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${lesson.less_Date}" /></td>
                                <td>${lesson.schedule.schoolClass.class_name}</td>
                                <td>${lesson.schedule.subject.sub_name}</td>

                                <td><a href="<c:url value="/lesson/update/${lesson.less_id}"/>">Урок</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:if>
            </table>
        </th>
        <th style="background: #fdfdff"  align="center">
            <table>
                <c:if test="${!empty lessons}">
                    <tr>
                        <td class="my" colspan="4">Среда</td>
                    </tr>
                    <tr>
                        <th>Дата</th>
                        <th>Класс</th>
                        <th>Предмет</th>
                        <th>Перейти</th>
                    </tr>
                    <c:forEach items="${lessons}" var="lesson">
                        <c:if test="${lesson.schedule.weekday.week_id == 3}">
                            <tr>
                                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${lesson.less_Date}" /></td>
                                <td>${lesson.schedule.schoolClass.class_name}</td>
                                <td>${lesson.schedule.subject.sub_name}</td>

                                <td><a href="<c:url value="/lesson/update/${lesson.less_id}"/>">Урок</a></td>
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
                <c:if test="${!empty lessons}">
                    <tr>
                        <td class="my" colspan="4">Четверг</td>
                    </tr>
                    <tr>
                        <th>Дата</th>
                        <th>Класс</th>
                        <th>Предмет</th>
                        <th>Перейти</th>
                    </tr>
                    <c:forEach items="${lessons}" var="lesson">
                        <c:if test="${lesson.schedule.weekday.week_id == 4}">
                            <tr>
                                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${lesson.less_Date}" /></td>
                                <td>${lesson.schedule.schoolClass.class_name}</td>
                                <td>${lesson.schedule.subject.sub_name}</td>
                                <td><a href="<c:url value="/lesson/update/${lesson.less_id}"/>">Урок</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:if>
            </table>
        </th>
        <th  style="background: #fdfdff" align="center">
            <table>
                <c:if test="${!empty lessons}">
                    <tr>
                        <td class="my" colspan="4">Пятница</td>
                    </tr>
                    <tr>
                        <th>Дата</th>
                        <th>Класс</th>
                        <th>Предмет</th>
                        <th>Перейти</th>
                    </tr>
                    <c:forEach items="${lessons}" var="lesson">
                        <c:if test="${lesson.schedule.weekday.week_id == 5}">
                            <tr>
                                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${lesson.less_Date}" /></td>
                                <td>${lesson.schedule.schoolClass.class_name}</td>
                                <td>${lesson.schedule.subject.sub_name}</td>

                                <td><a href="<c:url value="/lesson/update/${lesson.less_id}"/>">Урок</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:if>
            </table>
        </th>
        <th style="background: #fdfdff"  align="center">
            <table>
                <c:if test="${!empty lessons}">
                    <tr>
                        <td class="my" colspan="4">Суббота</td>
                    </tr>
                    <tr>
                        <th>Дата</th>
                        <th>Класс</th>
                        <th>Предмет</th>
                        <th>Перейти</th>
                    </tr>
                    <c:forEach items="${lessons}" var="lesson">
                        <c:if test="${lesson.schedule.weekday.week_id == 6}">
                            <tr>

                                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${lesson.less_Date}" /></td>
                                <td>${lesson.schedule.schoolClass.class_name}</td>
                                <td>${lesson.schedule.subject.sub_name}</td>

                                <td><a href="<c:url value="/lesson/update/${lesson.less_id}"/>">Урок</a></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:if>
            </table>
        </th>
    </tr>
</table>
<hr/>
</body>
</html>
