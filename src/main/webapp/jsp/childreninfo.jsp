<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 18.10.2016
  Time: 18:35
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
    <title>${children.FIO}</title>
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
        <li><a  href="<c:url value="/admin"/>">Админская панель</a></li>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
        <li><a href="<c:url value="/admin/schedules"/>">Расписание</a></li>
        <li><a href="<c:url value="/work/tlessons"/>">Лекции</a></li>
        <li><a href="<c:url value="/work/classinfo"/>">Класс</a></li>
        <li><a class="active" href="<c:url value="/childreninfo/${children.ch_id}"/>">Информация о ${children.FIO}</a></li>
        <li><a href="<c:url value="/work/workpage"/>">Учительская</a></li>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_USER')">
        <li><a  class="active"  href="<c:url value="/childreninfo/${children.ch_id}"/>">Общая информация</a></li>
        <li><a href="<c:url value="/childreninfo/childrenratings/${children.ch_id}"/>">Оценки</a></li>
        <li><a  href="<c:url value="/teacherinfo/${children.schoolClass.teacher.t_id}"/>">Классный руководитель</a></li>
        <li><a  href="<c:url value="#notices"/>">Сообщения классу</a></li>
        <li><a  href="<c:url value="/childreninfo/timetable/${children.schoolClass.class_id}"/>">Расписание уроков</a></li>
    </sec:authorize>
    <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
    <sec:authorize access="isAuthenticated()">
        <li style="float:right"><a href="<c:url value="/logout" />">Выйти из системы</a></li>
    </sec:authorize>
</ul>
<hr/>
<c:if test="${!empty children}">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-6 g5" style="background-color:#4CAF50; font-size: 20px">Информация об ученике ${children.FIO}</div>
        </div>
        <div class="row g5">
            <div class="col-sm-2">ФАМИЛИЯ</div>
            <div class="col-sm-2">ИМЯ</div>
            <div class="col-sm-2">ОТЧЕСТВО</div>
            <div class="col-sm-2">ДАТА РОЖДЕНИЯ</div>
            <div class="col-sm-2">АДРЕС ПРОЖИВАНИЯ</div>
            <div class="col-sm-2">ТЕЛЕФОН</div>
        </div>

        <div class="row g6">
            <div class="col-sm-2">${children.ch_surname}</div>
            <div class="col-sm-2">${children.ch_name}</div>
            <div class="col-sm-2">${children.ch_Pname}</div>
            <div class="col-sm-2"><fmt:formatDate pattern="dd/MM/yyyy" value="${children.birthDate}" /></div>
            <div class="col-sm-2">${children.address}</div>
            <div class="col-sm-2">${children.phone}</div>
        </div>
        <div class="row g5">
            <div class="col-sm-2">КЛАСС</div>
            <div class="col-sm-3">КЛАССНЫЙ РУКОВОДИТЕЛЬ</div>
            <div class="col-sm-2">ОЦЕНКИ</div>
        </div>
        <div class="row g6">
            <div class="col-sm-2">${children.schoolClass.class_name}</div>
            <div class="col-sm-3"><a href="<c:url value="/teacherinfo/${children.schoolClass.teacher.t_id}"/>"> ${children.schoolClass.teacher.FIO}</a></div>
            <div class="col-sm-2"><a href="<c:url value="/childreninfo/childrenratings/${children.ch_id}"/>">Перейти к оценкам</a></div>
        </div>
        <div>
            <div class="col-sm-20"></div>
        </div>
    </div>
    <div class="container-fluid" style="margin-top: 0.8cm;">
        <c:if test="${!empty children.parent}">
            <div class="row">
                <div class="col-sm-6 g5" style="background-color:#54a879; font-size: 20px">Информация о родителях</div>
            </div>
            <div class="row g5" style="background-color: #9F9BB5">
                <div class="col-sm-2">ФАМИЛИЯ</div>
                <div class="col-sm-2">ИМЯ</div>
                <div class="col-sm-2">ОТЧЕСТВО</div>
                <div class="col-sm-2">ТЕЛЕФОН</div>
            </div>
            <div class="row g6">
                <div class="col-sm-2">${children.parent.parentSurname}</div>
                <div class="col-sm-2">${children.parent.parentName}</div>
                <div class="col-sm-2">${children.parent.parenPname}</div>
                <div class="col-sm-2">${children.parent.parentPhone}</div>
            </div>
        </c:if>


    </div>
</c:if>

<c:if test="${!empty children.parent}">
    <h3><a class="btn btn-info" role="button" href="<c:url value="/childreninfo/parent/${children.parent.parentId}"/>">Редактировать данные родителя</a></h3>
</c:if>

<div class="container-fluid" style="margin-top: 2cm;">
    <a name="notices"> <h2>Сообщения от учителя</h2></a>
    <div class="row g5" style="background-color:#4CAF50;">
        <div class="col-sm-2">Дата</div>
        <div class="col-sm-10">Сообщение</div>
    </div>
</div>
<div id="example">
<c:if test="${!empty children.schoolClass.notices}">
    <div class="table-responsive">
        <table class="table table-hover table-striped" id="myTable">
            <thead>
                <tr>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${children.schoolClass.notices}" var="notice">
                    <tr>
                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${notice.noticeDate}" /></td>
                        <td>${notice.message}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
</div>
</body>
</html>