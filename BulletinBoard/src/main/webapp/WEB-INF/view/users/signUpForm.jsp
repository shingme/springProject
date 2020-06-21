<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
<link href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script type = "text/javascript" src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js"></script>
<c:url var="loginForm" value="/users/form"/>

<c:choose>
	<c:when test="${!empty userSession.user.userId }"> <!-- user객체의 userId필드가 null이 아닐 때  -->
		<c:set var="method" value="put"/> <!--개인정보 수정  -->
	</c:when>	
	<c:otherwise>
		<c:set var="method" value="post"/> <!-- 회원가입  -->
	</c:otherwise>
</c:choose>
<center>
<form:form modelAttribute="user" method ="${method}"  action="${loginForm}" class="form-horizontal">
	<!-- user 클래스에 있는 setter 와 getter와 자동으로 매핑을 해준다. 밑에 path와  -->
	<div class="form-group">
   	 <label for="inputEmail3" class="col-sm-2 control-label">ID</label>
   	 <div class="col-sm-10">
 		<c:choose>
 			<c:when test="${empty userSession.user.userId }">
 				<form:input class="form-control" path="userId"/>
 				<form:errors path="userId" />  <!--id와 name이 자동으로 userId로 들어가게 된다. -->
 			</c:when>
 			<c:otherwise>
 				<label for="inputEmail3">${userSession.user.userId}</label>
 				<form:hidden path="userId"/> <!-- hidden으로 값을 전달  -->
 			</c:otherwise>
 		</c:choose>
 		</div>
 	</div>
 		<p/>
 	<div class="form-group">
 		<label for="inputPassword3" class="col-sm-2 control-label">PASSWORD</label>
 		<div class="col-sm-10">
 			<form:password class="form-control" path="password"/>
 			<form:errors path="password" /><!-- form태그를 활용해  자동으로 name에 password가 적용 -->
 		</div>
 	</div>
 		
 	<div class="form-group">
 		<label for="inputNickName3" class="col-sm-2 control-label">NICKNAME</label>
 		<div class="col-sm-10">
 			<form:input class="form-control" path="nickname"/>
 			<form:errors path="nickname"/>
 		</div>
 	</div>
 		
 	<div class="form-group">
 		<label for="inputName" class="col-sm-2 control-label">NAME</label>
 		<div class="col-sm-10">
 			<form:input class="form-control" path="name"/>
 			<form:errors path="name" />
 		</div>
 	</div>
 	
 	<div class="form-group">
 		<label for="inputEmail3" class="col-sm-2 control-label">E-MAIL</label>
 		<div class="col-sm-10">
 			<form:input class="form-control" path="email"/>
 			<form:errors path="email" />
 		</div>
 	</div>
 		<p/>
 		<p/>
 		
 	<div class="form-group">
    	<div class="col-sm-offset-2 col-sm-10">
 	<c:choose>
	<c:when test="${!empty userSession.user.userId }">
		<input type="submit" class="btn btn-default" value="Update"/> <!--개인정보 수정  -->
	</c:when>	
	<c:otherwise>
		<input type="submit" class="btn btn-default" value="Sign up"/> <!-- 회원가입  -->
	</c:otherwise>
</c:choose>
 		</div>
 	</div>
</form:form>
</center>
</body>
</html>