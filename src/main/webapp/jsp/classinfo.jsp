<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 19.10.2016
  Time: 9:46
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
    <title>Страница ${schoolClass}</title>

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
    <style>
        #example {overflow:scroll; height:300px; border:#999 solid 1px; }
    </style>
    <script>
        $(document).ready( function () {
            $('#myTable5').DataTable( {
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
        <li><a href="<c:url value="/admin/subjects"/>">Предметы</a></li>
        <li><a href="<c:url value="/admin/childrens"/>">Список учеников</a></li>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
        <li><a href="<c:url value="/admin/schedules"/>">Расписание</a></li>
        <li><a href="<c:url value="/work/tlessons"/>">Лекции</a></li>
        <li><a class="active" href="<c:url value="/work/classinfo"/>">Класс</a></li>
        <li><a href="<c:url value="/work/workpage"/>">Учительская</a></li>
        <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
    </sec:authorize>
</ul>
<hr/>
<h1>${schoolClass.class_name} КЛАСС</h1>
<hr/>
<h3><a href="/work/classfill/${schoolClass.class_id}">Управление списком учеников</a></h3>
<h3><a href="/work/timetable/${schoolClass.class_id}">Управление расписанием класса</a></h3>
<h3><a href="/work/parents/${schoolClass.class_id}" target="_blank">Получить список ИМЯ/ПАРОЛЬ родителей</a></h3>
<hr/>
<c:if test="${!empty schoolClass}">
    <div class="table-responsive">
        <table class="table table-hover table-striped" id="myTable">
            <thead>
                <tr>
                    <th>ФИО</th>
                    <th>ДАТА РОЖДЕНИЯ</th>
                    <th>АДРЕС</th>
                    <th>ТЕЛЕФОН</th>
                    <th>ДОП. ИНФО</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${schoolClass.childrenList}" var="children">
                    <tr>
                        <td><a href="<c:url value="/childreninfo/${children.ch_id}"/>">${children.FIO}</a></td>
                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${children.birthDate}" /></td>
                        <td>${children.address}</td>
                        <td>${children.phone}</td>
                        <td><a href="<c:url value="/childreninfo/${children.ch_id}"/>">Перейти</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
<div  style="margin-top: 1cm;">
    <!-- Trigger the modal with a button -->
    <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Добавить сообщение классу</button>
</div>


<c:url var="addAction" value="/work/notice/add/${schoolClass.class_id}"/>
<form:form action="${addAction}" commandName="notice" class="form-horizontal">
<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog  modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Добавить сообщение</h4>
            </div>

            <div class="modal-body">
                    <div class="form-group">
                        <form:label path="noticeDate" class="control-label col-sm-2">
                            Дата
                        </form:label>
                        <div class="col-sm-6">
                            <form:input path="noticeDate"  placeholder="День/Месяц/Год" />
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="message" class="control-label col-sm-2">
                            Сообщение
                        </form:label>
                        <div class="col-sm-6">
                            <form:input path="message"/>
                        </div>
                    </div>
                    <c:if test="${!empty notice.message}">
                        <form:hidden path="noticeID"/>
                    </c:if>
                    <div class="form-group">

                        <div class="col-sm-offset-2 col-sm-10">
                            <input type="submit" class="btn btn-default" value="<spring:message text="Сохранить"/>"/>
                        </div>
                    </div>
            </div>



            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
</form:form>

<div id="example">
<c:if test="${!empty schoolClass.notices}">
    <div class="table-responsive">
        <table class="table table-hover table-striped" id="myTable5">
            <thead>
        <tr>
            <th>Сообщение классу</th>
            <th></th>
            <th></th>
        </tr>
            </thead>
            <tbody>
        <c:forEach items="${schoolClass.notices}" var="notice">
            <tr>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${notice.noticeDate}" /></td>
                <td>${notice.message}</td>
                <td><a href="<c:url value="/work/class/notice/delete/${notice.noticeID}"/>">Удалить</a></td>
            </tr>
        </c:forEach>
            </tbody>
    </table>
    </div>
</c:if>

</div>
<c:if test="${!empty message}">
    <hr/>
    <h1>${message}</h1>
    <hr/>
</c:if>
</body>
</html>
