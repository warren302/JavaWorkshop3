<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Solution Details</title>
</head>
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<body>
<h3>Tytuł zadania: <c:out  value="${solution.exercise.title}" default="brak tytulu"/></h3><br>
<p>Treść rozwiązania:</p>
<c:out  value="${solution.description}" default="brak treści"/><br>
<hr>
Autor: <c:out  value="${solution.user.username}" default="brak autora"/>
  Dodano: <c:out  value="${solution.created}" default="brak danych"/><br>
</body>
</html>