<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <title>댓글</title>
 <c:url var ="replyInsert" value = "/replyInsert"/>
 <c:url var ="replyList" value = "/replyList"/>
  <!-- Ajax 사용을 위한 jquery -->
 <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
 
 <script>
 $(document).ready(function() {
	 
	 listReply();
	 
	 $("#btnReply").click(function() {  
		   var obj = { //json형태로 값 주기
			   "id" : "${userSession.user.userId}",
		   	   "boardNo" : "${boardInfo.b_no}",
		   	   "comment" : $("textarea[name=comment]").val()
		   }
		
			var json_data = JSON.stringify(obj);
			
			$.ajax({	//ajax 표현식 (jquery를 이용한)
				url:"${replyInsert}",
				type:"post",
				data:json_data,
				dataType:"json", //컨트롤러에서 @RequestBody로 받기 때문에 이에 맞게 json타입이어야 한다.
				contentType: "application/json; charset=UTF-8",
				success:function(data){ //통신이 성공적으로 일어났을 때 
					alert("댓글이 등록되었습니다.");
					$("#listReply").append("<tr><td>" + data.id
										 + "(" + data.date + ") </br>"
										 + data.comment + "</td></tr>");
				},
				error:function(xhr, status, error){
					alert(xhr);
					alert(status);
					alert(error);
				}
			});
			
			//댓글 내용 초기화
			$("#form").each(function(){ //각각(jquery객체) 함수를 적용한다. -> 함수에는 리셋함수가 들어있다. 
				this.reset();
			});
	 	});
	});
 function listReply(){
	 	$.ajax({	//ajax 표현식 (jquery를 이용한)
				url:"${replyList}",
				type:"get",
				data: {"boardNo": $("input[name=boardNo]").val()},
				contentType: "application/json; charset=UTF-8",
				success:function(data){ //통신이 성공적으로 일어났을 때 
					var output = "";
					for(var i in data){
						output += "<tr>";
						output += "<td>" + data[i].id;
						output += "(" + data[i].date + ")</br>";
						output += data[i].comment + "</td></tr>";
					}
					
					$("#listReply").html(output);	 // name이 listReply 인 태그 안에 html를 넣는다.
				}
			});
		 }
 
 </script>
 </head>
 <body>
 <center>
 <form id="form" method="post">
 	<input type="hidden" name="id" value="${userSession.user.userId}"/>
	<input type="hidden" name="boardNo" value="${boardInfo.b_no}"/>
	
	<table width="600" cellpadding="0" cellspacing="3" border="1">
		<tr>
			<td> 
			<textarea name="comment" rows="2" style="width:95%;resize:none;"></textarea>
			</td>
			<td>	
			<input type="button" id="btnReply" value="등록">
			</td>
		</tr>
	</table>
	<table id="listReply" border="1">
	</table>
</form>
</center>
</body> 
</html>