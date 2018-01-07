<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>admin panel</title>
</head>
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<body>
<h3>Panel Administratora </h3>
<hr>
<a href='<c:url value='/AdmUsersGroups'/>'>Adm. grupami użytkowników</a>
<a href='<c:url value='/AdmUsers'/>'>Adm. użytkownikami</a>
<a href='<c:url value='/AdmExercises'/>'>Adm. zadaniami</a>
</body>
</html>