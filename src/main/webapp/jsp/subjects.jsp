<%--
  Created by IntelliJ IDEA.
  User: ArslanovDamir
  Date: 26.09.2016
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
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/bootstrap/js/jquery.min.js" />"></script>
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
        <li><a href="<c:url value="/admin/schoolclasses"/>">Классы</a></li>
        <li><a  class="active"  href="<c:url value="/admin/subjects"/>">Предметы</a></li>
        <li><a href="<c:url value="/admin/childrens"/>">Список учеников</a></li>
        <li><a href="<c:url value="/admin/schedules"/>">Расписание</a></li>
        <li><a href="<c:url value="/work/tlessons"/>">Лекции</a></li>
    </sec:authorize>
        <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
</ul>
<%--<sec:authorize access="hasRole('ROLE_ADMIN')">--%>
    <%--<div class="container">--%>
        <%--<h1>Добавить предмет</h1>--%>
        <%--<c:url var="addAction" value="/admin/subjects/add"/>--%>
        <%--<form:form action="${addAction}" commandName="subject" class="form-inline">--%>
            <%--<div class="form-group">--%>
                <%--<form:label path="sub_name">--%>
                    <%--Название предмета:--%>
                <%--</form:label>--%>
                    <%--<form:input path="sub_name"/>--%>
            <%--</div>--%>
            <%--<c:if test="${!empty subject.sub_name}">--%>
                <%--<form:hidden path="sub_id"/>--%>
            <%--</c:if>--%>
        <%--<button type="submit" class="btn btn-default">Сохранить</button>--%>
        <%--</form:form>--%>
        <%--<form:errors path="sub_name" cssclass="error"/>--%>
    <%--</div>--%>
<%--</sec:authorize>--%>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <c:url var="addAction" value="/admin/subjects/add"/>
    <form:form action="${addAction}" commandName="subject" class="form-inline">
        <table>
            <tr>
                <th>Название предмета:  </th>
                <td><form:input path="sub_name"/></td>
                <td><button type="submit" class="btn btn-default">Сохранить</button></td>
                <c:if test="${!empty subject.sub_name}">
                    <form:hidden path="sub_id"/>
                </c:if>
            </tr>
            <tr>
                <th>&nbsp;</th>
                <td>
                    <form:errors path="sub_name" cssclass="error"/>
                </td>
            </tr>
        </table>
    </form:form>
</sec:authorize>
<h1>Предметы</h1>
<c:if test="${!empty listSubjects}">
    <div class="table-responsive">
        <table class="table table-hover table-striped" id="myTable">
            <thead>
                <tr>
                    <th width = "100">Название предмета</th>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <th></th>
                        <th></th>
                    </sec:authorize>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listSubjects}" var="subject">
                    <tr>
                        <td>${subject.sub_name}</td>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <td width = "120"><a href="<c:url value="/admin/subjects/edit/${subject.sub_id}"/>">Редактировать</a></td>
                            <td><a href="<c:url value="/admin/subjects/delete/${subject.sub_id}"/>">Удалить</a></td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
</body>
</html>