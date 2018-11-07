<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Список игр</title>
</head>
<body>
<form id="form" action="${pageContext.request.contextPath}/games-filter" method="post">
    <table>
        <caption><b>Фильтры</b></caption>
        <col width="200"><col width="300"><col width="300">
        <tr>
            <td>
                <input type="checkbox" name="filters" value="price"><b>Цена(ниже чем)</b>
                <input type="text" name="price">
            </td>
            <td>
                <input type="checkbox" name="filters" value="age_limit"><b>Возрастное ограничение</b>
                <select name="age_limit">
                        <c:forEach var="ageLimit" items="${requestScope.ageLimits}">
                            <option value="ageLimit">${ageLimit.name}</option>
                        </c:forEach>
                </select>
            </td>
            <td>
                <input type="checkbox" name="filters" value="issue_year"><b>Год выпуска(начиная от)</b>
                <input type="text" name="issue_year">
            </td>
        </tr>
    </table>
    <br>
    <label>Показывать на странице по
        <input type="text" name="items_on_page" size="2">
    </label><br>
    <input type="submit" value="Применить">
</form>

<table width="1200" border="1">
    <col width="200"><col width="950"><col width="50">
    <tr>
        <th align="left">Название</th>
        <th align="left">Описание</th>
        <th align="left">Год выпуска</th>
        <th align="left">Возрастное ограничение</th>
    </tr>
    <c:if test="${not empty requestScope.games}">
        <c:forEach var="game" items="${requestScope.games}">
            <tr>
                <td>${game.name}</td>
                <td>${game.description}</td>
                <td>${game.issueYear}</td>
                <td>${game.ageLimit.name}</td>
            </tr>
        </c:forEach>
    </c:if>
</table>
</body>
</html>
