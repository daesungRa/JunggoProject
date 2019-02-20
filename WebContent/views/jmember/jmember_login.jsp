<!-- 완료(190219) -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div class='container' id='loginForm'>
		<div class='container' id='innerLoginForm' style=' height: 500px; padding: 20px;'>
			<div class='container' style='width: 70%; height: 380px; margin: 70px auto; padding-top: 30px; background-color: #cdcdcd; border-radius: 10px;'>
				<p/>
				<img class="profile-img" src='/junggo/img/logo/cat_foot_empty.svg' alt="">&nbsp;&nbsp;중고천국<br/>
				<h3>로그인</h3><br/>
				<div class="container">
					<form class='form-signin my-login-form' name='loginFrm' id='loginFrm' action='#loginlogin' method='post' style='width: 70%; margin: 10px auto;'>
						<input class='form-control' type='text' name='mid' id='mid' placeholder='아이디 입력' /><br/>
						<input class='form-control' type='password' name='pwd' id='pwd' placeholder='비밀번호 입력' /><br/>
						<input class='btn btn-lg btn-info btn-block' type='button' id='btnLoginSubmit' value='제 출' style='margin-top: 20px;' /><br/>
					</form>
				</div>
			</div>
			<a href='#'>아이디 찾기</a>&nbsp;&nbsp;
			<a href='#'>비밀번호 찾기</a>&nbsp;&nbsp;
			<a href='#'>회원가입</a>
			<p/>
		</div>
	</div>

</body>
</html>