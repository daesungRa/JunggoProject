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

	<div class='container' style='height: 720px;'>
		<div class='container' id='joinForm' style='background-color: #cdcdcd; border-radius: 10px; padding-right: 70px; margin-top: 50px;'>
			<div style='margin: 20px 0 10px 0;'>
				<img class="profile-img" src='/junggo/img/logo/cat_foot_empty.svg' alt="">&nbsp;&nbsp;중고천국<br/>
			</div>
			<h3>회원가입</h3><br/>
			<form class='form' name='joinFrm' id='joinFrm' action='#joinAction' method='post' enctype='multipart/form-data'>
				<div class='form-group'>
					<div class='form-inline'>
						<input class='form-control' type='text' id='mid' name='mid' maxlength='30' placeholder='아이디 입력 (필수)' style='width: 48%; margin-right: 10px;'/>
						<input class='btn btn-primary' type='button' id='btnIdChk' name='btnIdChk' value='중복확인' style='margin-right: 10px;'></input>
						<input type='hidden' id='midChk' value='unChecked' readonly/>
						<div id='midChkResult' style='color: #0000ff; width: 40%;'></div>
					</div>
				</div>
				<div class='form-group'>
					<input class='form-control' type='password' id='pwd01' name='pwd' maxlength='30' placeholder='비밀번호 입력 (필수)' style='width: 60%;' />
					<input class='form-control' type='password' id='pwd02' name='pwdChk' maxlength='30' placeholder='비밀번호 확인 (필수)' style='width: 60%;' />
					<input type='hidden' id='pwdChk' value='unChecked' readonly/>
					<div id='pwdChkResult' style='color: #ff0000;'></div>
				</div>
				<div class='form-group'>
					<input class='form-control' type='text' id='irum' name='irum' placeholder='이름 입력 (필수)' />
					<input type='hidden' id='irumChk' value='unChecked' readonly/>
					<div id='irumChkResult' style='color: #ff0000;'></div>
				</div>
				<div class='form-group'>
					<input class='form-control' type='email' id='email' name='email' placeholder='이메일 입력 (필수)' />
					<input type='hidden' id='emailChk' value='unChecked' readonly/>
					<div id='emailChkResult' style='color: #ff0000;'></div>
				</div>
				<div class='form-group'>
					<input class='form-control' type='text' id='phone' name='phone' placeholder="연락처 입력 ('-' 포함, 필수)" />
					<input type='hidden' id='phoneChk' value='unChecked' readonly/>
					<div id='phoneChkResult' style='color: #ff0000;'></div>
				</div>
				<div class='form-group'>
					<div class='form-inline'>
						<input class='form-control' type='text' id='postal' name='postal' placeholder='우편번호' style='width: 30%; margin-right: 10px;' readonly/>
						<input class='btn btn-primary' type='button' id='btnPostal' name='btnPostal' value='주소 찾기'></input>
					</div>
						<input class='form-control' type='text' id='address' name='address' placeholder='주소 입력' readonly/>
						<input class='form-control' type='text' id='addressAdd' name='addressAdd' value='' placeholder='추가 주소 입력' />
				</div>
				<div class='form-group'>
					<input type='file' id='photo' name='photo' value='photo' required="required"/><br/>
				</div>
				<div class='form-group'>
					<input class='btn btn-primary' type='button' id='btnJoinSubmit' name='btnJoinSubmit' value='제 출' />
					<input class='btn btn-primary' type='button' id='btnCancel' name='btnCancel' value='초기화' />
				</div>
			</form>
			<p/>
		</div>
		<div class='container' id='memberImg'>
			<img id='image' src='https://via.placeholder.com/150x200?text=Your Imgs here' width='150px' height='200px' /><br/>
			<p>이미지 미리보기</p>
		</div>
	</div>

</body>
</html>