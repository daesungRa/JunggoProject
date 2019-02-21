<!--
	[비밀번호 찾기]
	아이디, 이름, 이메일로 찾기
 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div class="container">
	    <div class="row">
	        <div class="col-sm-6 col-md-4 col-md-offset-4">
	            <div class="account-wall">
	                <label class="profile">암호 찾기</label>
	                <p class="profile_p" >회원가입 시 입력한 회원정보를 입력해주세요.</p>
	                <form class="form-signin"  name='frm' method='post' action ='#email' enctype = 'multipart/form-data'>
	                <input type="text" class="form-control" name ='mid' placeholder="아이디" value='' required autofocus autocomplete='off'>
	                <input type="text" class="form-control" name ='irum' placeholder="이름" value='' required autofocus autocomplete='off'>
	                <input type="text" class="form-control" name ='receiver' placeholder="이메일" value='' required autofocus autocomplete='off'>
	                
	                
	                <input type ='hidden' name='sender' value='dfdf0608@naver.com'>
	                <input type= 'hidden' name ='subject' value ='중고천국입니다.'>
	
	                <input type= 'hidden' name='attFile' >
	                
	                <br/>
	                <input type="button" class="btn btn-md btn-info btn-block" id='btnFindPwd' value='비밀번호 찾기' onclick = 'funcFindPwd()'>
	              
	                </form>
	            </div>
	        </div>
	    </div>
	</div>

</body>
</html>