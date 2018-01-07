<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of groups</title>
</head>
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<body>
<table>
<thead><tr><th>Nazwa grupy</th><th>Szczegóły</th></tr></thead>
<tbody>
<c:forEach items="${groups}" var="group">
	<tr>
		<td>${group.name}</td>
		<td><a href='<c:url value='/GroupMembersList?gId=${group.id}'/>'>Link</a></td>
	</tr>
</c:forEach>
</tbody>
</table>
</body>
</html>