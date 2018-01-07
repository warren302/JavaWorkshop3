<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Groups of users admin panel</title>
</head>
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<body>
<table>
<thead><tr><th>Nazwa grupy</th><th>Akcja</th></tr></thead>
<tbody>
<c:forEach items="${groups}" var="group">
	<tr>
		<td>${group.name}</td>
		<td><a href='<c:url value='/GroupEdit?gId=${group.id}'/>'>Edytuj</a></td>
	</tr>
</c:forEach>
</tbody>
</table>
<a href='<c:url value='/GroupEdit?gId=0'/>'>Dodaj</a>
</body>
</html>