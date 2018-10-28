<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Info</title>
</head>
<body>
    <p><b>Login: </b>${requestScope.user.getLogin()}</p>
    <p><b>First Name: </b>${requestScope.user.getFirstName()}</p>
    <p><b>Last Name: </b>${requestScope.user.getLastName()}</p>
    <p><b>E-mail: </b>${requestScope.user.getEmail()}</p>
    <p><b>Address: </b>${requestScope.user.getAddress()}</p>
    <p><b>Phone Number: </b>${requestScope.user.getPhoneNumber()}</p>
    <p><b>Role: </b>${requestScope.user.getRole().name}</p>
    <c:if test="${requestScope.user.getRole() eq 'ADMIN'}">
        <p><b>Salary: </b>${requestScope.user.salary}</p>
    </c:if>
    <c:if test="${requestScope.user.getRole() eq 'USER'}">
        <p><b>Last Visit Date: </b>${requestScope.user.lastVisitDate}</p>
    </c:if>
</body>
</html>
