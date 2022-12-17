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
        <th>pid</th>
        <th>user name</th>
        <th>users</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${profiles}" var="p">
        <tr>
            <td>${p.pid}</td>
            <td> ${p.firstname} ${p.lastname}</td>
            <td>
                <ul>
                    <c:forEach items="${p.usersByPid}" var="u">
                        <li>${u.username}</li>
                    </c:forEach>
                </ul>
            </td>
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
