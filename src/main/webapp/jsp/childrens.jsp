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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>Список учеников</title>
</head>
<body>
<ul>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li><a href="<c:url value="/admin"/>">Админская панель</a></li>
        <li><a href="<c:url value="/admin/lessontime"/>">Часы занятий</a></li>
        <li><a href="<c:url value="/admin/teachers"/>">Учителя</a></li>
        <li><a href="<c:url value="/admin/schoolclasses"/>">Классы</a></li>
        <li><a href="<c:url value="/admin/subjects"/>">Предметы</a></li>
        <li><a  class="active"  href="<c:url value="/admin/childrens"/>">Список учеников</a></li>
        <li><a href="<c:url value="/admin/schedules"/>">Расписание</a></li>
        <li><a href="<c:url value="/work/tlessons"/>">Лекции</a></li>
    </sec:authorize>
        <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
</ul>
<h1>Список учеников</h1>
<c:if test="${!empty listChildrens}">
    <div class="table-responsive">
        <table class="table table-hover table-striped" id="myTable">
            <thead>
                <tr>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Отчество</th>
                    <th>Дата рождения</th>
                    <th>Класс</th>
                    <th>Классный руководитель</th>
                    <th></th>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <th></th>
                        <th></th>
                    </sec:authorize>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listChildrens}" var="children">
                        <tr>
                            <td>${children.ch_name}</td>
                            <td>${children.ch_surname}</td>
                            <td>${children.ch_Pname}</td>
                            <td><fmt:formatDate pattern="dd/MM/yyyy" value="${children.birthDate}" /></td>
                            <td>${children.schoolClass.getClass_name()}</td>
                            <td>${children.schoolClass.teacher.t_name} ${children.schoolClass.teacher.t_pname}</td>
                            <td><a href="<c:url value="/childreninfo/${children.ch_id}"/>">Полная информация</a></td>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <td width = "120"><a href="<c:url value="/admin/childrens/edit/${children.ch_id}"/>">Редактировать</a></td>
                                <td><a href="<c:url value="/admin/childrens/delete/${children.ch_id}"/>">Удалить</a></td>
                            </sec:authorize>
                        </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>

<sec:authorize access="hasRole('ROLE_ADMIN')">
    <c:url var="addAction" value="/admin/childrens/add"/>
    <div class="container">
        <h1>Добавить ученика</h1>
        <form:form action="${addAction}" commandName="children" class="form-horizontal">
            <div class="form-group">
                <form:label path="ch_name" class="control-label col-sm-2">
                    Имя
                </form:label>
                <div class="col-sm-6">
                    <form:input path="ch_name"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="ch_surname" class="control-label col-sm-2">
                    Фамилия
                </form:label>
                <div class="col-sm-6">
                    <form:input path="ch_surname"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="ch_Pname" class="control-label col-sm-2">
                    Отчество
                </form:label>
                <div class="col-sm-6">
                    <form:input path="ch_Pname"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="birthDate" class="control-label col-sm-2">
                    Дата рождения
                </form:label>
                <div class="col-sm-6">
                    <form:input path="birthDate" placeholder="День/Месяц/Год"/>
                </div>
            </div>

            <div class="form-group">
                <form:label path="address" class="control-label col-sm-2">
                    Адрес
                </form:label>
                <div class="col-sm-6">
                    <form:input path="address"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="phone" class="control-label col-sm-2">
                    Телефон
                </form:label>
                <div class="col-sm-6">
                    <form:input path="phone" placeholder="формат 81234567890"/>
                </div>
            </div>

            <div class="form-group">
                <form:label path="schoolClass" class="control-label col-sm-2">
                    Класс
                </form:label>
                <div class="col-sm-6">
                    <form:select path="schoolClass">
                        <form:option label="Выбрать класс?" value="null"/>
                        <form:options items="${schoolClasses}" itemLabel="class_name" itemValue="class_id"/>
                    </form:select>
                </div>
            </div>
            <c:if test="${!empty children.ch_name}">
                <form:hidden path="ch_id"/>
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