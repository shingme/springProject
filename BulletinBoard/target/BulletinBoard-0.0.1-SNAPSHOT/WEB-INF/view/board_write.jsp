<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <title>게시판</title>
 </head>
 <body>
 <%@include file="commons/_top.jspf" %>
<c:url var = "boardList" value ="/board"><c:param name="pageBlock" value="1"/></c:url>
<c:url var = "board" value = "/b_write"><c:param name="b_no" value="${boardInfo.b_no}"/></c:url>
 <c:url var ="boardDelete" value = "/delete"><c:param name="b_no" value="${boardInfo.b_no}"/></c:url>
 <c:choose>
      		<c:when test = "${userSession.user.userId eq boardInfo.b_id}"> <!-- 내 글 수정할 때 -->
      			 <c:set var="method" value="put"/>
      		</c:when>
      		<c:otherwise><!-- 글 insert-->
      		     <c:set var="method" value="post"/>
      		</c:otherwise>
      		
      	</c:choose>
<center>
 <form:form commandName="boardInfo" method ="${method}" action="${board}">
 <h2>게시판</h2>
<table>
  <tr>
   <td>
   <table width="500" cellpadding="7" cellspacing="0" border="1">
    <tr>
      <td align="center" width="50">작성자</td>
      <td>
       	<c:choose>
      		<c:when test = "${!empty get}">
      			 <form:input path="b_id" value="${userSession.user.userId}"/>
      		</c:when>
      		<c:otherwise>
      			${boardInfo.b_id}
      		</c:otherwise>
      	</c:choose>
      </td>
     </tr>
     <tr>
      <td align="center">제목</td>
      <td>
      	<c:choose>
      		<c:when test = "${userSession.user.userId eq boardInfo.b_id}">
      			 <form:input path="b_title" />
      		</c:when>
      		<c:when test ="${empty get }">
      			${boardInfo.b_title}
      		</c:when>
      		<c:otherwise>
      			<form:input path="b_title" />
      		</c:otherwise>
      	</c:choose>
      </td>
     </tr>
     <tr>
      <td align="center" height="100" >내용</td>
      <td>
      <c:choose>
      		<c:when test = "${userSession.user.userId eq boardInfo.b_id}">
      			 <form:textarea path="b_text" rows="3" cols="50"/>
      		</c:when>
      		<c:when test ="${empty get }">
      			${boardInfo.b_text}
      		</c:when>
      		<c:otherwise>
      			<form:textarea path="b_text" rows="3" cols="50"/>
      		</c:otherwise>
      	</c:choose>
      </td>
     </tr>
     
    </table>
   </td>
  </tr>
 </table>
  <c:choose>
      		<c:when test = "${userSession.user.userId eq boardInfo.b_id}"> <!-- 내 글 수정할 때 -->
      			 <input type=submit value="수정">
      			 <input type=button value="취소" onclick="location.href='${boardList}' ">
      			 <input type=button value="삭제" onclick="location.href='${boardDelete}' ">
      		</c:when>
      		<c:when test ="${empty get }"> <!-- 제목 클릭시 (내 글이 아닐 때) -->
      			 <input type=button value="목록" onclick="location.href='${boardList}' ">
      		</c:when>
      		<c:otherwise><!-- 새 글 쓸 때-->
      			<input type=submit value="등록">
      			<input type=button value="취소" onclick="location.href='${boardList}' ">
      		</c:otherwise>
      	</c:choose>
 </form:form>
	<!--form 태그 밖에다 빼줘야 include의 버튼 타입이 실행된다. submit는 폼폼태그 안에 있어야하므로 위에다가! -->
	<c:if test="${userSession.user.userId != null}"> 
      <c:choose>
      		<c:when test = "${userSession.user.userId eq boardInfo.b_id}"> <!-- 내 글 수정할 때 --> 
    			<%@include file="reply.jsp" %>
      		</c:when>
      		<c:when test ="${empty get }"> <!-- 제목 클릭시 (내 글이 아닐 때) -->		 
      			<%@include file="reply.jsp" %>
      		</c:when>
      </c:choose>
     </c:if>
     </center>
</body> 
</html>
