<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>메인화면</title>
	</head> 
	<body>
	<%@include file="commons/_top.jspf" %>
		<!-- <c:url value="/h" var="messageUrl" />  value가 바로 jps OR html로 가면 안됨. 컨트롤러를 지나쳐야 하므로 /h로 줘야한다. 매핑을 /로 해줬기 때문  -->
		<!-- <a href="<c:url value='/login'  />">Click to enter</a> 매핑을 /login으로 하겠다. -->
	</body>
</html>
