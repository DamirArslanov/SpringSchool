<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 19.09.2016
  Time: 22:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--<%@ page session="false" %>--%>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Список учителей</title>

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
        <li><a class="active"  href="<c:url value="/admin/teachers"/>">Учителя</a></li>
        <li><a href="<c:url value="/admin/schoolclasses"/>">Классы</a></li>
        <li><a  href="<c:url value="/admin/subjects"/>">Предметы</a></li>
        <li><a href="<c:url value="/admin/childrens"/>">Список учеников</a></li>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
        <li><a href="<c:url value="/admin/schedules"/>">Расписание</a></li>
        <li><a href="<c:url value="/work/tlessons"/>">Лекции</a></li>
        <li style="float:right"><a href="<c:url value="/"/>">Главная страница</a></li>
    </sec:authorize>
</ul>
<hr/>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <button type="button" class="btn btn-block btn-success" data-toggle="modal" data-target="#myModal">Добавить учителя</button>
    <c:url var="addAction" value="/admin/teachers/add"/>
    <form:form action="${addAction}" commandName="teacher"  enctype="multipart/form-data" class="form-horizontal">
        <!-- Modal -->
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog  modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <%--<h4 class="modal-title">Добавить сообщение</h4>--%>
                    </div>

                    <div class="modal-body">
                        <div class="form-group">
                            <form:label path="image" class="control-label col-sm-2">
                                Фотография
                            </form:label>
                            <div class="col-sm-6">
                                <input type="file" name="file" id="file">
                            </div>
                        </div>

                        <div class="form-group">
                            <form:label path="t_name" class="control-label col-sm-2">
                                Имя
                            </form:label>
                            <div class="col-sm-6">
                                <form:input path="t_name"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <form:label path="t_surname" class="control-label col-sm-2">
                                Фамилия
                            </form:label>
                            <div class="col-sm-6">
                                <form:input path="t_surname"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <form:label path="t_pname" class="control-label col-sm-2">
                                Отчество
                            </form:label>
                            <div class="col-sm-6">
                                <form:input path="t_pname"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <form:label path="teacherPhone" class="control-label col-sm-2">
                                Телефон
                            </form:label>
                            <div class="col-sm-6">
                                <form:input path="teacherPhone"  placeholder="формат 81234567890"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <form:label path="username" class="control-label col-sm-2">
                                Username
                            </form:label>
                            <div class="col-sm-6">
                                <form:input path="username"  placeholder="ENG"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <form:label path="password" class="control-label col-sm-2">
                                Password
                            </form:label>
                            <div class="col-sm-6">
                                <form:input path="password"  placeholder="ENG or/and Number"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <form:label path="password" class="control-label col-sm-2">
                                ROLES
                            </form:label>
                            <div class="col-sm-6">
                                <form:select multiple="true" path="roles">
                                    <form:options items="${roles}" itemLabel="role" itemValue="TRId" />
                                </form:select>
                            </div>
                        </div>

                        <c:if test="${!empty teacher.schoolClass.class_id}">
                            <div class="form-group">
                                <form:label path="schoolClass.class_name" class="control-label col-sm-2">
                                    Класс
                                </form:label>
                                <div class="col-sm-6">
                                    <form:input path="schoolClass.class_name" readonly="true"  disabled="true"/>
                                    <form:hidden path="schoolClass.class_id"/>
                                </div>
                            </div>
                        </c:if>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <input type="hidden" name="imageID" value="${imageID}"/>
                        <c:if test="${!empty teacher.t_name}">
                            <form:hidden path="t_id"/>
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
</sec:authorize>
<hr/>
<h1>Список учителей</h1>
<c:if test="${!empty listTeachers}">
    <div class="table-responsive">

    <table class="table table-hover table-condensed" id="myTable">
        <thead>
            <tr>
                <%--<th width = "70">Фото</th>--%>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Отчество</th>
                <th>Класс</th>
                <th>Username</th>
                <th>Password</th>
                <th>Roles</th>
                <th>ФИО</th>
                <th></th>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <th></th>
                    <th></th>
                </sec:authorize>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${listTeachers}" var="teacher">

                <tr>
                    <%--<td><img class="img-rounded"  alt="Foto" width="70" height="70" src="${pageContext.request.contextPath}/image/${teacher.image.id}"></td>--%>
                    <td>${teacher.t_name}</td>
                    <td>${teacher.t_surname}</td>
                    <td>${teacher.t_pname}</td>
                    <td>${teacher.schoolClass.getClass_name()}</td>
                    <td>${teacher.username}</td>
                    <td>${teacher.password}</td>
                    <td>
                        <c:forEach items="${teacher.roles}" var="role">
                                ${role.role}
                                <br>
                        </c:forEach>
                    </td>
                    <td>${teacher.FIO}</td>
                    <td ><a href="<c:url value="/admin/teachers/schedule/${teacher.t_id}"/>">Занятия</a></td>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <td width = "120"><a href="<c:url value="/admin/teachers/edit/${teacher.t_id}"/>">Редактировать</a></td>
                    <td><a href="<c:url value="/admin/teachers/delete/${teacher.t_id}"/>">Удалить</a></td>
                    </sec:authorize>
                </tr>

        </c:forEach>
        </tbody>
    </table>
</div>

</c:if>
<hr/>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <c:url var="addAction" value="/admin/teachers/add"/>
        <div class="container">
            <h2>Добавить учителя</h2>
            <form:form action="${addAction}" commandName="teacher"  enctype="multipart/form-data" class="form-horizontal">

            <div class="form-group">
                <form:label path="image" class="control-label col-sm-2">
                    Фотография
                </form:label>
                <div class="col-sm-6">
                <input type="file" name="file" id="file">
                </div>
            </div>

                <div class="form-group">
                    <form:label path="t_name" class="control-label col-sm-2">
                        Имя
                    </form:label>
                    <div class="col-sm-6">
                        <form:input path="t_name"/>
                    </div>
                </div>


                <div class="form-group">
                    <form:label path="t_surname" class="control-label col-sm-2">
                        Фамилия
                    </form:label>
                    <div class="col-sm-6">
                        <form:input path="t_surname"/>
                    </div>
                </div>


                <div class="form-group">
                    <form:label path="t_pname" class="control-label col-sm-2">
                        Отчество
                    </form:label>
                    <div class="col-sm-6">
                        <form:input path="t_pname"/>
                    </div>
                </div>


                <div class="form-group">
                    <form:label path="teacherPhone" class="control-label col-sm-2">
                        Телефон
                    </form:label>
                    <div class="col-sm-6">
                        <form:input path="teacherPhone"  placeholder="формат 81234567890"/>
                    </div>
                </div>

                <div class="form-group">
                    <form:label path="username" class="control-label col-sm-2">
                        Username
                    </form:label>
                    <div class="col-sm-6">
                        <form:input path="username"  placeholder="ENG"/>
                    </div>
                </div>

                <div class="form-group">
                    <form:label path="password" class="control-label col-sm-2">
                        Password
                    </form:label>
                    <div class="col-sm-6">
                        <form:input path="password"  placeholder="ENG or/and Number"/>
                    </div>
                </div>

                <div class="form-group">
                    <form:label path="password" class="control-label col-sm-2">
                        ROLES
                    </form:label>
                    <div class="col-sm-6">
                        <form:select multiple="true" path="roles">
                            <form:options items="${roles}" itemLabel="role" itemValue="TRId" />
                        </form:select>
                    </div>
                </div>

                <c:if test="${!empty teacher.schoolClass.class_id}">
                    <div class="form-group">
                        <form:label path="schoolClass.class_name" class="control-label col-sm-2">
                            Класс
                        </form:label>
                        <div class="col-sm-6">
                            <form:input path="schoolClass.class_name" readonly="true"  disabled="true"/>
                            <form:hidden path="schoolClass.class_id"/>
                        </div>
                    </div>
                </c:if>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input type="hidden" name="imageID" value="${imageID}"/>
                <c:if test="${!empty teacher.t_name}">
                    <form:hidden path="t_id"/>
                </c:if>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="submit" class="btn btn-default" value="<spring:message text="Сохранить"/>"/>
                </div>
            </div>

            </form:form>
        </div>
</sec:authorize>
<hr/>
</body>
</html>