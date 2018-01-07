<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit exercise data</title>
</head>
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<body>
<form action="<c:out value="AdmExercises" />" method="post">
	<label>Wprowadź/zmień tytuł zadania:<input type="text" name="title" value="${exercise.title}"/></label><br>
	<label>Wprowadź/zmień opis zadania: <input type="text" name="desc" value="${exercise.description}"/></label>
	<input type="submit" value="Zatwierdź" />
</form>
</body>
</html>