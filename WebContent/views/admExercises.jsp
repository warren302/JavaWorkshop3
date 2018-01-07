<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of exercises</title>
</head>
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<body>
<table>
<thead><tr><th>Tytuł</th><th>Treść</th><th>Akcja</th></tr></thead>
<tbody>
<c:forEach items="${exercises}" var="exercise">
	<tr>
		<td>${exercise.title}</td>
		<td>${exercise.description}</td>
		<td><a href='<c:url value='/ExerciseEdit?eId=${exercise.id}'/>'>Edytuj</a></td>
	</tr>
</c:forEach>
</tbody>
</table>
<a href='<c:url value='/ExerciseEdit?eId=0'/>'>Dodaj</a>
</body>
</html>