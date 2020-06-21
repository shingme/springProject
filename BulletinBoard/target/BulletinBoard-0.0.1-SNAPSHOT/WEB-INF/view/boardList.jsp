<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <title>게시판</title>
 </head>
 <c:url var = "board_write" value ="/b_write"/>

 <body>
 <%@include file="commons/_top.jspf" %>
 <center>
 <form  action = "${board_write}">
 <h2>게시판</h2>
<table width="600" cellpadding="0" cellspacing="3" border="1">

 <tr>
   <td width="73">번호</td>
   <td width="379">제목</td>
   <td width="73">작성자</td>
   <td width="164">작성날짜</td>
   <td width="58">조회수</td>
  </tr>
 	<c:forEach  var="boardList" items="${boardList}" varStatus="status">
 		<tr>
 			<td>${boardPager.start + status.count}</td>
 			<td><a href ="<c:url value="/boardComment">
 						<c:param name="b_no" value="${boardList.b_no}"/>
 						</c:url>">${boardList.b_title}</a></td>
 			<td>${boardList.b_id}</td>
 			<td>${boardList.b_date}</td>
 			<td>${boardList.b_see}</td>
 		</tr>
 	</c:forEach>
 </table>
 
 <c:if test="${boardPager.prev}">
 	<a href="<c:url value='/board'>
 	<c:param name='pageBlock' value='${boardPager.tempStart-1}'/>
 	</c:url>">이전</a>
 </c:if>
 
 <c:forEach varStatus="status" begin="${boardPager.tempStart}" end="${boardPager.tempEnd}">
 <a href ="<c:url value='/board'>
 						<c:param name='pageBlock' value='${boardPager.tempStart + (status.count-1)}'/>
 						</c:url>">${boardPager.tempStart + (status.count-1)}</a>
 </c:forEach>
 
 <c:if test="${boardPager.next}">
 	<a href="<c:url value='/board'>
 		<c:param name='pageBlock' value='${boardPager.tempEnd+1}'/>
 		</c:url>">다음</a>
 </c:if>
 
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr><td colspan="4" height="5"></td></tr>
  <tr align="center">
   <td><input type=submit value="글쓰기"></td>
  </tr>
</table>
</form>
</center>
</body> 
</html>