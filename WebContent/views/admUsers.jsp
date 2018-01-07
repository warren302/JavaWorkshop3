<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of Users</title>
</head>
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<body>
<table>
<thead><tr><th>Imię i nazwisko</th><th>E-mail</th><th>Grupa</th><th>Akcja</th></tr></thead>
<tbody>
<c:forEach items="${users}" var="user">
	<tr>
		<td>${user.username}</td>
		<td>${user.email}</td>
		<td>${user.group.name}</td>
		<td><a href='<c:url value='/UserEdit?uId=${user.id}'/>'>Edytuj</a></td>
	</tr>
</c:forEach>
</tbody>
</table>
<a href='<c:url value='/UserEdit?uId=0'/>'>Dodaj</a>
</body>
</html>