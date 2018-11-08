<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Список игр</title>
</head>
<body>
<table width="1200" border="1">
    <col width="200"><col width="950"><col width="50">
    <tr>
        <th align="left">Название</th>
        <th align="left">Описание</th>
        <th align="left">Год выпуска</th>
        <th align="left">Возрастное ограничение</th>
    </tr>
    <c:if test="${not empty requestScope.filteredGames}">
        <c:forEach var="game" items="${requestScope.filteredGames}">
            <tr>
                <td>${game.name}</td>
                <td>${game.description}</td>
                <td>${game.issueYear}</td>
                <td>${game.ageLimit.name}</td>
            </tr>
        </c:forEach>
    </c:if>
</table>
<table cellspacing="7">
    <tr>
    <c:if test="${requestScope.pagesNumber ne 1}">
        <c:forEach begin="1" end="${requestScope.pagesNumber}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="${pageContext.request.contextPath}/games-filter?page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:if>
    </tr>
</table>
</body>
</html>
