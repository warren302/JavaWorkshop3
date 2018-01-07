<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home Page</title>
</head>
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<body>
<table>
<thead><tr><th>Zadanie</th><th>Użytkownik</th><th>Szczegóły</th></tr></thead>
<tbody>
<c:forEach items="${solutions}" var="solution">
	<tr>
		<td>${solution.exercise.title}</td>
		<td>${solution.user.username}</td>
		<td><a href='<c:url value='/SolutionDetailsServlet?sId=${solution.id}'/>'>Link</a></td>
	</tr>
</c:forEach>
</tbody>
</table>
</body>
<%@ include file="/WEB-INF/fragments/footer.jspf" %>
</html>