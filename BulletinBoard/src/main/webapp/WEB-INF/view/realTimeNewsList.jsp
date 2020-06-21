<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta charset="utf-8">
		<title>Real Time News</title>
	</head> 
	<body>
	<h2><p class="text-center">Article about Environment</p></h2>
		<c:forEach var="newsList" items="${newsList}">
			<h4><a href = "${newsList.originalLink}" target="_blank">${newsList.title}</a></h4>
			<h5>${newsList.description}</h5>
			<p>
			<h6><p class="text-right">${newsList.date}</p></h6>
			<hr>					
		</c:forEach>
		<nav>
 		<ul class="pagination">
		<c:if test="${newsPager.prev}">
			<li>
 			<a href="<c:url value='/newsList'>
 				<c:param name='pageBlock' value='${newsPager.tempStart-1}'/>
 			</c:url>"><span aria-hidden="true">&laquo;</span></a>
 			</li>
 		</c:if>
		
		<c:forEach varStatus="status" begin="${newsPager.tempStart}" end="${newsPager.tempEnd}">
			<li><a href ="<c:url value ='/newsList'>
				<c:param name='pageBlock' value='${newsPager.tempStart + (status.count-1)}'/></c:url>">
		${newsPager.tempStart + (status.count-1)}
		</a></li></c:forEach>
		
		<c:if test="${newsPager.next}">
			<li>
 			<a href="<c:url value='/newsList'>
 				<c:param name='pageBlock' value='${newsPager.tempEnd+1}'/>
 			</c:url>"><span aria-hidden="true">&raquo;</span></a></li>
 		</c:if>
 		</ul>
 		</nav>	
	</body>
</html>
