<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
   
   <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
 	<head>
 	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	
 	<title>게시판</title>
 	<link href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
 	</head>
	 <body>
	 	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	 	<script type = "text/javascript" src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js"></script>
    <div class="navbar navbar-default navbar-fixed-top">
        <div class="container">
           
         <center><h2>NATURE FORUM</h2></center>
         <div>
            <ul class="nav nav-tabs" role="tablist">
            <li><a href="<c:url value ='/'/>">HOME</a></li>
              <c:choose>
               <c:when test="${empty userSession.user}">
              <li><a href="<c:url value ='/user/loginForm'/>">LOGIN</a></li>
              <li><a href="<c:url value ='/user/signUp'/>">SING UP</a></li>         
              </c:when>
              <c:otherwise>  
              <li><a href="<c:url value='/board'><c:param name='pageBlock' value='1'/></c:url>">BOARD</a></li>
              <li><a href="<c:url value='/newsList'><c:param name='pageBlock' value='1'/></c:url>">ARTICLE</a></li>
              <li><a href="<c:url value='/user/logout'/>">LOGIN OUT</a></li>
              <li><a href="<c:url value='/user/${userSession.user.userId }/signUp'/>">MY PAGE</a></li>  
              <c:choose>
				<c:when test="${ userSession.user.userId == 'shingme'}">
					<p class="text-right"><small>안녕하세요. 관리자님</small></p>
				</c:when>
				<c:otherwise>
					<p class="text-right"><small>안녕하세요. ${userSession.user.userId } 님</small></p>
				</c:otherwise>
				</c:choose>
              </c:otherwise>
              </c:choose>     
            </ul>
            
        
          </div>
        </div>
        
       
        
   </div>
      </body>
      </html>
      
