<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert a new name of the Group</title>
</head>
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<body>
<form action="<c:out value="AdmUsersGroups" />" method="post">
	<label>Wprowadź/zmień nazwę grupy:<input type="text" name="name" value="${group.name}"/></label>
	<input type="submit" value="Zatwierdź" />
</form>
</body>
</html>