<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 19.09.2016
  Time: 22:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@ page session="false" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="/resources/css/ul.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/bootstrap/js/jquery.min.js" />"></script>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/jquery.dataTables.js" />"></script>
    <link href="<c:url value="/resources/bootstrap/css/jquery.dataTables.css" />" rel="stylesheet">
    <script>
        $(document).ready( function () {
            $('#myTable').DataTable( {
                language: {
                    "processing": "Подождите...",
                    "search": "Поиск:",
                    "lengthMenu": "Показать _MENU_ записей",
                    "info": "Записи с _START_ до _END_ из _TOTAL_ записей",
                    "infoEmpty": "Записи с 0 до 0 из 0 записей",
                    "infoFiltered": "(отфильтровано из _MAX_ записей)",
                    "infoPostFix": "",
                    "loadingRecords": "Загрузка записей...",
                    "zeroRecords": "Записи отсутствуют.",
                    "emptyTable": "В таблице отсутствуют данные",
                    "paginate": {
                        "first": "Первая",
                        "previous": "Предыдущая",
                        "next": "Следующая",
                        "last": "Последняя"
                    },
                    "aria": {
                        "sortAscending": ": активировать для сортировки столбца по возрастанию",
                        "sortDescending": ": активировать для сортировки столбца по убыванию"
                    }
                }
            } );
        } );
    </script>
</head>
<body>
<ul>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li><a href="<c:url value="/admin"/>">Админская панель</a></li>
        <li><a href="<c:url value="/admin/lessontime"/>">Часы занятий</a></li>
        <li><a href="<c:url value="/admin/teachers"/>">Учителя</a></li>
        <li><a class="active"  href="<c:url value="/admin/schoolclasses"/>">Классы</a></li>
        <li><a href="<c:url value="/admin/subjects"/>">Предметы</a></li>
        <li><a href="<c:url value="/admin/childrens"/>">Список учеников</a></li>
        <li><a href="<c:url value="/admin/schedules"/>">Расписание</a></li>
        <li><a href="<c:url value="/work/tlessons"/>">Лекции</a></li>
    </sec:authorize>
        <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
</ul>
<h1>Общий список классов</h1>
<c:if test="${!empty listschoolClasses}">
    <div class="table-responsive">
        <table class="table table-hover table-striped" id="myTable">
            <thead>
                <tr>
                    <th>Имя класса</th>
                    <th>Учитель</th>
                    <th>Количество детей</th>
                    <th>Расписание</th>
                    <th>Доп. инф.</th>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <th></th>
                        <th></th>
                    </sec:authorize>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listschoolClasses}" var="schoolClass">
                    <tr>
                        <td>${schoolClass.class_name}</td>
                        <td>${schoolClass.teacher.FIO}</td>
                        <td>${schoolClass.childrenList.size()}</td>
                        <td><a href="<c:url value="/work/timetable/${schoolClass.class_id}"/>">Перейти к расписанию</a></td>
                        <td><a href="<c:url value="/work/classinfo/${schoolClass.class_id}"/>">Перейти к классу</a></td>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <td><a href="<c:url value="/admin/schoolclasses/edit/${schoolClass.class_id}"/>">Редактировать</a></td>
                            <td><a href="<c:url value="/admin/schoolclasses/delete/${schoolClass.class_id}"/>">Удалить</a></td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <div class="container">
    <h1>Добавить класс</h1>
    <c:url var="addAction" value="/admin/schoolclasses/add"/>
        <form:form action="${addAction}" commandName="schoolClass" class="form-horizontal">
            <div class="form-group">
                <form:label path="class_name" class="control-label col-sm-2">
                    Имя класса
                </form:label>
                <div class="col-sm-6">
                    <form:input path="class_name"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="teacher" class="control-label col-sm-2">
                    Класс
                </form:label>
                <div class="col-sm-6">
                    <form:select path="teacher">
                        <form:option label="Выбрать учителя?" value="null"/>
                        <form:options items="${listFTeachers}" itemLabel="FIO" itemValue="t_id"/>
                    </form:select>
                </div>
            </div>
            <c:if test="${!empty schoolClass.class_name}">
                <form:hidden path="class_id"/>
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