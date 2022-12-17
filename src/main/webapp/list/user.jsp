<%--
  Created by IntelliJ IDEA.
  User: shlomo
  Date: 16/12/2022
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<ul>
    <c:forEach items="${user}" var="user">
        <li>${user.id}. ${user.name} ${user.lastName}</li>
    </c:forEach>
</ul>

</body>
<footer>
    <span>${session.getId()}</span>
</footer>
</html>
