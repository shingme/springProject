<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
</head>
<body>
<%@include file="../commons/_top.jspf" %>

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
<form:form modelAttribute="user" method ="${method}"  action="${loginForm}">
	<!-- user 클래스에 있는 setter 와 getter와 자동으로 매핑을 해준다. 밑에 path와  -->
	<table >
 		<tr>
 			<td>아이디</td>
 			<c:choose>
 			<c:when test="${empty userSession.user.userId }">
 			<td><form:input path="userId"/></td>
 			<td><form:errors path="userId" /></td>  <!--id와 name이 자동으로 userId로 들어가게 된다. -->
 			</c:when>
 			<c:otherwise>
 			<td>	${userSession.user.userId} </td>
 				<form:hidden path="userId"/> <!-- hidden으로 값을 전달  -->
 			</c:otherwise>
 			</c:choose>
 			
 		</tr>
 		<tr>
 			<td>비밀번호</td>
 			<td><form:password path="password"/></td>
 			<td><form:errors path="password" /></td> <!-- form태그를 활용해  자동으로 name에 password가 적용 -->
 		</tr>
 		<tr>
 			<td>닉네임</td>
 			<td><form:input path="nickname"/></td>
 			<td><form:errors path="nickname"/></td>
 		</tr>
 		<tr>
 			<td>이름</td>
 			<td><form:input path="name"/></td>
 			<td><form:errors path="name" /></td>
 		</tr>
 		<tr>
 			<td>이메일</td>
 			<td><form:input path="email"/></td>
 			<td><form:errors path="email" /></td>
 		</tr>
 	</table>
 	<c:choose>
	<c:when test="${!empty userSession.user.userId }">
		<input type="submit" value="수정하기"/> <!--개인정보 수정  -->
	</c:when>	
	<c:otherwise>
		<input type="submit" value="회원가입"/> <!-- 회원가입  -->
	</c:otherwise>
</c:choose>
 	
</form:form>
</center>
</body>
</html>