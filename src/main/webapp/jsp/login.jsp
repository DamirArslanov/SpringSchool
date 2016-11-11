<%--
  Created by IntelliJ IDEA.
  User: Cheshire
  Date: 19.10.2016
  Time: 0:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Войдите в систему</title>
    <link href="<c:url value="/resources/css/colorLogin.css" />" rel="stylesheet">
</head>
<body>
<div class="login">
    <h1>Войдите в систему</h1>
    <form method="post">
        <div class="form-group ${error != null ? 'has-error' : ''}">
            <div style="text-align: center" class="success">${message}</div>
            <input type="text"  name="username"  placeholder="Введите Ваш логин" required="required" AUTOFOCUS/>
            <input type="password"  name="password" placeholder="Введите пароль" required="required" />
            <div style="text-align: center"><span class="error">${error}</span></div>
            <button type="submit" class="btn btn-primary btn-block btn-large">Войти</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </div>
    </form>
</div>
</body>
</html>
