<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <title>게시판</title>
 <c:url var ="replyInsert" value = "/replyInsert"/>
 <c:url var = "replyDelete" value = "/replyDelete"/>
 <link href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  
 </head>
 <body>
 	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script type = "text/javascript" src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js"></script>
<script>
$(document).ready(function() {
		 
	 $("#btnReply").click(function() {  
		   var obj = { //json형태로 값 주기
			   "id" : "${userSession.user.userId}",
		   	   "boardNo" : "${boardInfo.b_no}",
		   	   "comment" : $("textarea[name=comment]").val()
		   }
			var json_data = JSON.stringify(obj);
		    
			$.ajax({	//ajax 표현식 (jquery를 이용한) //이 메서드 내부에서 XMLHttpRequest객체를 만들어 Ajax를 수행한다.
				url:"${replyInsert}", //대상 url지정
				type:"post", //메소드 방식 지정 
				data:json_data, //요청 매개변수 지정
				dataType:"json", //컨트롤러에서 @RequestBody로 받기 때문에 이에 맞게 json타입이어야 한다.
				contentType: "application/json; charset=UTF-8",
				success:function(data){ //통신이 성공적으로 일어났을 때 자동으로 success 이벤트를 실행한다.
					//첫번째 매개변수 data는 Ajax가 성공했을 때 받은 데이터이다.
					var output = "";
					output += data.id + " " + data.date + " ";
					output += "<a href=\"#\" onclick=\"replyDelete(" + data.no + ")\">삭제</a>";
      				output += "<a href=\"#\" onclick=\"replyUpdate(" + data.no + ")\">수정</a>";
					output += "</br>" + data.comment;
			
					$("#replyList3").append("<div id =\"replyList" + data.no + "\"" + "style=\"border: 1px solid gray; padding: 5px; width: 600px; margin-top: 5px;\">"
											 +output + "</div>");	 // name이 listReply 인 태그 안에 html를 넣는다.	
				},
				error:function(xhr, status, error){
					alert(xhr); //xhr : XMLHttpRequest객체 - JS가 Ajax를 사용할 때 사용하는 객체
					alert(status);
					alert(error);
				}
			});
			
			//댓글 내용 초기화
			$('.com').val('');
	 	});
	});
	
	function replyDelete(no){ //삭제 이것도 jQeury로 바꿔서 할 수 있다.
		var r_no = {"no" : no}
		var json_data = JSON.stringify(r_no);
		
		$.ajax({
			url: "${replyDelete}",
			type: "post",
			data: json_data, //no:...
			dataType: "json",
			contentType: "application/json; charset=UTF-8",
			success: function(data){
				if(data == "OK"){
					$("#replyList"+no).remove();
	                alert("삭제되었습니다.");
				}
				
			}
		});
	}
	
	
</script>
<center>
	<c:url var = "boardList" value ="/board"><c:param name="pageBlock" value="1"/></c:url>
	<c:url var = "board" value = "/b_write"><c:param name="b_no" value="${boardInfo.b_no}"/></c:url>
 	<c:url var ="boardDelete" value = "/delete"><c:param name="b_no" value="${boardInfo.b_no}"/></c:url>
 	<c:choose>
      		<c:when test = "${userSession.user.userId eq boardInfo.b_id}"> <!-- 내 글 수정할 때 -->
      			 <c:set var="method" value="put"/>
      			 <h2>Update</h2>
      		</c:when>
      		<c:otherwise><!-- 글 신규 insert-->
      		     <c:set var="method" value="post"/>
      		     <h2>Write</h2>
      		</c:otherwise>
      		
      	</c:choose>
  
 <form:form commandName="boardInfo" method ="${method}" action="${board}">
 <div class="table-responsive">
<table>
  <tr>
   <td>
   <table class="table table-bordered" width="500" cellpadding="7" cellspacing="0" >
    <tr>
      <td align="center" width="120">작성자</td>
       	<c:choose>
      		<c:when test = "${!empty get}"><td  colspan="3" width="150"> <!--글쓰기 폼으로 들어왔을 때 글 신규 -->
      			 <form:input path="b_id" value="${userSession.user.userId}" />
      		</td></c:when>
      		<c:otherwise><td width="150"> <!-- 내 글이면 수정 다른 사람 글이면 그냥 보기 -->
      			${boardInfo.b_id}
      			</td>
      			<td width="50">날짜</td>
      			<td> ${boardInfo.b_date}</td>
      		</c:otherwise>
      	</c:choose>
     </tr>
     <tr>
      <td align="center">제목</td>
      	<c:choose>
      		<c:when test = "${userSession.user.userId eq boardInfo.b_id}">
      		<td >
      			 <form:input path="b_title" />
      		</td>
      		<td>조회수</td>
      		<td>${boardInfo.b_see}</td>
      		</c:when>
      		<c:when test ="${empty get }"><td>
      			${boardInfo.b_title}
      		</td>
      		<td>조회수</td>
      		<td>${boardInfo.b_see}</td>
      		</c:when>
      		<c:otherwise><td colspan="3">
      			<form:input path="b_title" />
      		</td></c:otherwise>
      	</c:choose>
     </tr>
     <tr>
      <td align="center" height="100" >내용</td>
      <td height="150" colspan="3">
      <c:choose>
      		<c:when test = "${userSession.user.userId eq boardInfo.b_id}">
      			 <form:textarea path="b_text" rows="3" cols="50"/>
      		</c:when>
      		<c:when test ="${empty get }">
      			${boardInfo.b_text}
      		</c:when>
      		<c:otherwise>
      			<form:textarea path="b_text" rows="3" cols="0"/>
      		</c:otherwise>
      	</c:choose>
      </td>
     </tr>
     
    </table>
   </td>
  </tr>
 </table>
 </div>
  <c:choose>
      		<c:when test = "${userSession.user.userId eq boardInfo.b_id}"> <!-- 내 글 수정할 때 -->
      			 <input class="btn btn-default" type=submit value="update">
      			 <input class="btn btn-default" type=button value="cancel" onclick="location.href='${boardList}' ">
      			 <input class="btn btn-default" type=button value="delete" onclick="location.href='${boardDelete}' ">
      		</c:when>
      		<c:when test ="${empty get }"> <!-- 제목 클릭시 (내 글이 아닐 때) -->
      			 <input class="btn btn-default" type=button value="Back to List" onclick="location.href='${boardList}' ">
      		</c:when>
      		<c:otherwise><!-- 새 글 쓸 때-->
      			<input class="btn btn-default" type=submit value="Register">
      			<input class="btn btn-default" type=button value="Cancel" onclick="location.href='${boardList}' ">
      		</c:otherwise>
      	</c:choose>
 </form:form>
	<!--form 태그 밖에다 빼줘야 include의 버튼 타입이 실행된다. submit는 폼폼태그 안에 있어야하므로 위에다가! -->
	<c:if test="${userSession.user.userId != null}"> 
      <c:choose>
      		<c:when test = "${userSession.user.userId eq boardInfo.b_id}"> <!-- 내 글 수정할 때  // 아마 이거 수정해야할껄!! 삭제 가능하게!--> 
    			<table width="600" cellpadding="0" cellspacing="3" border="0">
		<tr>
			<td> 
			<textarea name="comment" id="com" rows="2" style="width:95%;resize:none;"></textarea>
			</td>
			<td>	
			<input type="button" class="btn btn-default" id="btnReply" value="comment">
			</td>
		</tr>
	</table>
	<br/>
	<c:forEach var="replyList" items="${replyList}" varStatus="status">
	<div style="border: 1px solid gray; width: 600px; padding: 5px; margin-top: 5px;"> 
	<c:out value="${replyList.id}"/> <c:out value="${replyList.date}"/>
	<c:out value="${replyList.comment}"/>
	</div>
	</c:forEach>
	
	
      		</c:when><!-- 입력시 댓글 DB에 저장후 가져오기 -->
      		<c:when test ="${empty get }"> <!-- 제목 클릭시 (내 글이 아닐 때)//이건 삭제 완료! -->	
      			<table width="600" cellpadding="0" cellspacing="3" border="0">
					<tr><td> 
						<textarea name="comment" class="com" rows="2" style="width:95%;resize:none;"></textarea></td>
					<td><input type="button" class="btn btn-default" id="btnReply" value="comment"></td>
					</tr>
				</table><br/>
		<div id="replyList3">
			<c:forEach var="replyList" items="${replyList}" varStatus="status">
			<div id="replyList<c:out value='${replyList.no}'/>" style="border: 1px solid gray; width: 600px; padding: 5px; margin-top: 5px;"> 
				<c:out value="${replyList.id}"/> <c:out value="${replyList.date}"/>
				<c:if test="${userSession.user.userId eq replyList.id}">
					<a href="#" onclick="replyDelete('<c:out value="${replyList.no}"/>')">삭제</a>
      				<a href="#" onclick="replyUpdate('<c:out value="${replyList.no}"/>')">수정</a>
				</c:if>
				<br/><c:out value="${replyList.comment}"/>
		</div>
			</c:forEach>
			
		</div>
		 
      		</c:when>
      </c:choose>
     </c:if>
     </center>
</body> 

</html>
