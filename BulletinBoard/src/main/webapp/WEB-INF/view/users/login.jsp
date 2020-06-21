<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
     <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>로그인</title>

 	<link href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">

	<c:url var="loginCheck" value="/loginCheck"/>
	<c:url var="main" value="/"/>
 	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script type = "text/javascript" src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.min.js"></script>
	
	<!-- RSA 자바스크립트 라이브러리 -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsbn.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/rsa.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/prng4.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/rng.js"></script>
	
	<!-- RSA 암호화 처리 스크립트 -->
	<script>
	$(function(){
		$("#ms_login").click(function() {
			//사용자 계정정보 암호화전 평문
			var uid = $("#userId").val();
			var pwd = $("#password").val();
			
			if(!uid || !pwd){
				alert("아이디와 비밀번호를 입력해주세요.");
				return false;
			}
			
			var rsaPublicKeyModulus = $("#RSAModulus").val();
			var rsaPublicKeyExponent = $("#RSAExponent").val();
			
			//RSA 암호화 생성
			var rsa = new RSAKey();
			rsa.setPublic(rsaPublicKeyModulus, rsaPublicKeyExponent);
			
			//사용자 계정정보를 암호화 처리
			uid = rsa.encrypt(uid);
			pwd = rsa.encrypt(pwd);
			
			var obj = {
					"userId" : uid,
					"password" : pwd
			}
			
			var json_data = JSON.stringify(obj);
			
			$.ajax({
				url:"${loginCheck}",
				type:"post",
				data:json_data,
				dataType:"json",
				contentType:"application/json; charset=UTF-8",
				success:function(msg){
					if(msg.state == null && msg.matchPwd == null)
						location.href = "${main}";
					else if(msg.state == "false")
						alert("로그인에 실패하였습니다. <br/> 아이디 비밀번호를 확인해주세요.");
					else if(msg.matchPwd == "false")
						alert("비밀번호가 맞지 않습니다. 다시 확인해주세요.");
					else
						alert("잘못된 경로로 접근하였습니다.");
				}
			});	
		});
	});
	</script>
	</head> 
	<body>
	<center>
	<form id="loginFrm" method="post" class="form-inline">
		<input type="hidden" id="RSAModulus" value="${publicKeyModulus}"/>
		<input type="hidden" id="RSAExponent" value="${publicKeyExponent}"/>
			<div class="form-group">
			<label for="exampleInputName2">ID</label>&nbsp;&nbsp;&nbsp;
			 <input type="text" class="form-control" placeholder="Enter ID" id="userId" name="userId"/>
			</div>
			<p/>
			<div class="form-group">
			<label for="exampleInputName2">PASSWORD</label>&nbsp;&nbsp;
			<input type="password" class="form-control" placeholder="Password" id="password" name="password"/></p>
			</div>
			<br/>
		<input type="button" class="btn btn-default" id="ms_login" value="login">
		
	</form>
	</center>
</body>
</html>