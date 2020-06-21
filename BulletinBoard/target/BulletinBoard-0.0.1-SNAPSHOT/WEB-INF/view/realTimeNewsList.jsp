<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta charset="utf-8">
		<title>Real Time News</title>
	</head> 
	<body>
	 <%@include file="commons/_top.jspf" %>
		<c:forEach var="newsList" items="${newsList}">
			<h2><a href = "${newsList.originalLink}" target="_blank">${newsList.title}</a></h2>
			${newsList.description}
			<p>
			${newsList.date}
			<hr>					
		</c:forEach>
		
		<c:if test="${newsPager.prev}">
 			<a href="<c:url value='/newsList'>
 				<c:param name='pageBlock' value='${newsPager.tempStart-1}'/>
 			</c:url>">이전</a>
 		</c:if>
		
		<c:forEach varStatus="status" begin="${newsPager.tempStart}" end="${newsPager.tempEnd}">
			<a href ="<c:url value ='/newsList'>
				<c:param name='pageBlock' value='${newsPager.tempStart + (status.count-1)}'/></c:url>">
		${newsPager.tempStart + (status.count-1)}
		</a></c:forEach>
		
		<c:if test="${newsPager.next}">
 			<a href="<c:url value='/newsList'>
 				<c:param name='pageBlock' value='${newsPager.tempEnd+1}'/>
 			</c:url>">다음</a>
 		</c:if>
			

		
	</body>
</html>
