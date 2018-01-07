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
<thead><tr><th>Imie i Nazwisko</th><th>E-mail</th><th>Grupa</th><th>Szczegóły</th></tr></thead>
<tbody>
<c:forEach items="${users}" var="user">
	<tr>
		<td>${user.username}</td>
		<td>${user.email}</td>
		<td>${user.group.name}</td>
		<td><a href='<c:url value='/UserDetails?uId=${user.id}'/>'>Link</a></td>
	</tr>
</c:forEach>
</tbody>
</table>
</body>
</html>