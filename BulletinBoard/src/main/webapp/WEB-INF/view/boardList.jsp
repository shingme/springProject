<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <title>게시판</title>
 <link href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
 </head>
 <c:url var = "board_write" value ="/b_write"/>
 <body>
 	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script type = "text/javascript" src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js"></script>  
 <center>
 <form  action = "${board_write}">
 
 <div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">Board about Nature</div>
   <div class="table-responsive">
<table class="table table-hover" width="600" cellpadding="0" cellspacing="3" >

 <tr>
   <td width="58">번호</td>
   <td width="259">제목</td>
   <td width="73">작성자</td>
   <td width="204">작성날짜</td>
   <td width="60">조회수</td>
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
 </div>
</div>

	<nav>
 	 <ul class="pagination">
 <c:if test="${boardPager.prev}">
 	<li>
 	<a href="<c:url value='/board'>
 	<c:param name='pageBlock' value='${boardPager.tempStart-1}'/>
 	</c:url>" ><span aria-hidden="true">&laquo;</span></a>
 </li>
 </c:if>
 
 
 <c:forEach varStatus="status" begin="${boardPager.tempStart}" end="${boardPager.tempEnd}">
 <li><a href ="<c:url value='/board'>
 						<c:param name='pageBlock' value='${boardPager.tempStart + (status.count-1)}'/>
 						</c:url>">${boardPager.tempStart + (status.count-1)}</a></li>
 </c:forEach>

 <c:if test="${boardPager.next}">
 	<li>
 	<a href="<c:url value='/board'>
 		<c:param name='pageBlock' value='${boardPager.tempEnd+1}'/>
 		</c:url>" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
 </c:if>
  	</ul>
	</nav>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr><td colspan="4" height="5"></td></tr>
  <tr align="center">
   <td><input class="btn btn-default" type=submit value="Write"></td>
  </tr>
</table>

</form>
</center>
</body> 
</html>