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
<table>
    <thead>
        <tr>
            <th>uid</th>
            <th>username</th>
            <th>user name</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.uid}</td>
            <td>${user.username}</td>
            <td> ${user.profilesByPid.firstname} ${user.profilesByPid.lastname}</td>
        </tr>
    </c:forEach>
    </tbody>

</table>
<ul>

</ul>

</body>
<footer>
    <span>${session.getId()}</span>
</footer>
</html>
