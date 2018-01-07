<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Edit</title>
</head>
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<body>
<form action="<c:out value="AdmUsers" />" method="post">
	<label>Wprowadź/zmień nazwę: <input type="text" name="username" value="${user.username}"/></label><br>
	<label>Wprowadź/zmień email: <input type="text" name="email" value="${user.email}"/></label><br>
	<label>Wprowadź/zmień haslo: <input type="text" name="password" value="${user.password}"/></label><br>
	<label>Wprowadź/zmień numer grupy: <br>
		<c:forEach items="${groups}" var="group">
			<input type="radio" name="gId" value="${group.id}">${group.name}<br>
		</c:forEach>
	</label>
	<input type="submit" value="Zatwierdź" />
</form>
</body>
</html>