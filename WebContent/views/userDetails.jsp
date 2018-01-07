<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User's details</title>
</head>
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<body>
Imię i nazwisko: <c:out  value="${user.username}" default="brak danych"/>
e-mail: <c:out  value="${user.email}" default="brak danych"/>
<hr>
<table>
<thead><tr><th>Zadania</th><th>Rozwiązanie</th><th>Data utworzenia</th><th>Szczegóły</th></tr></thead>
<tbody>
<c:forEach items="${solutions}" var="solution">
	<tr>
		<td>${solution.exercise.title}</td>
		<td>${solution.description}</td>
		<td>${solution.created}</td>
		<td><a href='<c:url value='/SolutionDetailsServlet?sId=${solution.id}'/>'>Link</a></td>
	</tr>
</c:forEach>
</tbody>
</table>
</body>
</html>