<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 27.10.2016
  Time: 20:00
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
    <link href="<c:url value="/resources/css/ul.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/bootstrap/js/jquery.min.js" />"></script>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/jquery.dataTables.js" />"></script>
    <link href="<c:url value="/resources/bootstrap/css/jquery.dataTables.css" />" rel="stylesheet">
    <script>
        $(document).ready( function () {
            $('#childrenTable').DataTable( {
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

    <title>Список класса</title>
</head>
<body>


<ul>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li><a  href="<c:url value="/admin"/>">Админская панель</a></li>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
        <li><a href="<c:url value="/admin/schedules"/>">Расписание</a></li>
        <li><a href="<c:url value="/work/tlessons"/>">Лекции</a></li>
        <li><a class="active" href="<c:url value="/work/classinfo/${children.schoolClass.class_id}"/>">Класс</a></li>
        <li><a href="<c:url value="/work/workpage"/>">Учительская</a></li>
        <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
    </sec:authorize>
</ul>
<hr/>
<c:if test="${!empty listChildrens}">
    <div class="table-responsive">
        <table class="table table-hover table-striped" id="childrenTable">
            <thead>
            <tr>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Отчество</th>
                <th>Дата рождения</th>
                <th>Адрес</th>
                <th>Телефон</th>
                <th>Класс</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listChildrens}" var="children">
                <tr>
                    <td>${children.ch_name}</td>
                    <td>${children.ch_surname}</td>
                    <td>${children.ch_Pname}</td>
                    <td><fmt:formatDate pattern="dd/MM/yyyy" value="${children.birthDate}" /></td>
                    <td>${children.address}</td>
                    <td>${children.phone}</td>
                    <td>${children.schoolClass.class_name}</td>
                    <td><a href="<c:url value="/work/classfill/${children.schoolClass.class_id}/get/${children.ch_id}"/>">Ред.</a></td>
                    <td><a href="<c:url value="/work/classfill/${children.schoolClass.class_id}/del/${children.ch_id}"/>">Удл.</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
<c:url var="addAction" value="/work/classfill/add"/>
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
                    <form:select path="schoolClass.class_id" items="${schoolClasses}" selected="true"
                                 itemLabel="class_name"
                                 itemValue="class_id"/>
            </div>
        </div>

        <c:if test="${!empty children.ch_name}">
            <form:hidden path="ch_id"/>
        </c:if>
        <%--<form:errors path="schoolClass" cssClass="error"/></td>--%>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="submit" class="btn btn-default" value="<spring:message text="Сохранить"/>"/>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>
