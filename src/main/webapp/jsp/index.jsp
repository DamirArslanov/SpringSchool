<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 10.10.2016
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
  <head>
    <link href="<c:url value="/resources/css/Base.css" />" rel="stylesheet">
    <title>Главная страница</title>
  </head>
<body>

<ul>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
        <li><a  href="<c:url value="/admin"/>">Админская панель</a></li>
    </sec:authorize>
    <li style="float:right" class="active"><a href="<c:url value="/"/>">Главная страница</a></li>
    <sec:authorize access="isAuthenticated()">
        <li style="float:right"><a href="<c:url value="/logout" />">Выйти из системы</a></li>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')">
        <li style="float:right"><a href="<c:url value="/work/workpage"/>">Учительская</a></li>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_USER')">
        <li style="float:right"><a href="<c:url value="/childreninfo/"/>">Родительская</a></li>
    </sec:authorize>
    <sec:authorize access="isAnonymous()">
        <li style="float:right"><a href="<c:url value="/login"/>">Войти</a></li>
    </sec:authorize>
</ul>
<hr/>
  <table>
      <tr>
          <th class="my">teacher</th>
          <th class="my">---------</th>
          <th class="my">parent</th>
      </tr>
      <tr>
          <th>username</th>
          <th>password</th>
          <th>username</th>
          <th>password</th>
      </tr>
      <tr>
          <td>fdg5gf</td>
          <td>l32g45</td>
          <td>userTaPHd</td>
          <td>LsKF9cFN</td>
      </tr>
  </table>
  http://www.edu.ru/news/feed.rss
  </body>
</html>
