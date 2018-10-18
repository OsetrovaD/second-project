<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Info</title>
</head>
<body>
    <p><b>Login: </b>${requestScope.user.login}</p>
    <p><b>First Name: </b>${requestScope.user.firstName}</p>
    <p><b>Last Name: </b>${requestScope.user.lastName}</p>
    <p><b>E-mail: </b>${requestScope.user.email}</p>
    <p><b>Address: </b>${requestScope.user.address}</p>
    <p><b>Phone Number: </b>${requestScope.user.phoneNumber}</p>
</body>
</html>
