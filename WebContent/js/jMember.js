/*
 * 작성자: 김가현
 * 작성일: 190220
 * 기능: 회원관리에 대한 기본 스크립트 모음
 */

// 아이디 찾기 함수
function funcFindId () {
	 document.frm.receiver.onkeyup = function(e){
		 if(e.keyCode == 13) funcForgetId();
	 }
	 document.frm.btnFindId.onclick = function () {
		 funcForgetId();
	 }
}
function funcForgetId(){
	 var frm = document.frm;
	 var irum = frm.irum;
	 var receiver = frm.receiver;

	 if(irum.value == ''){
		 alert("이름을 입력해주세요");
		 irum.focus();
	 }
	 else if(receiver.value == ''){
		 alert("이메일을 입력해주세요");
		 receiver.focus();
	 }
	 else {
		 // frm.submit();
		 var formData = new FormData(frm);
		$.ajax({
			url: 'email.emailId',
			data: formData,
			contentType: false,
			processData: false,
			type: 'post',
			success: function (data) {
				var result = data;
				alert("아이디 찾기 결과: " + result);
				if (result == '1') { // 아이디 찾기 성공, 로그인 페이지로 이동
					alert('회원님의 아이디를 이메일로 전송하였습니다. 로그인해 주십시오.');
					
					// 로그인 모달 열기
					var modalWindow = document.getElementById('modalWindow');
					var modalContent = document.getElementById('modalContent');
					var innerModalContent = document.getElementById('innerModalContent');
					
					$.ajax({
						type: 'get',
						url: '/junggo/views/jmember/jmember_login.jsp',
						dataType: 'html',
						success: function (html, status) {
							modalContent.setAttribute('style', 'height: 60%; margin: 14% auto;');
							innerModalContent.setAttribute('style', 'position: absolute; width: 96%; height: 90%; top: -10px;');
							
							innerModalContent.innerHTML = html;
							modalWindow.style.display = 'block';
							
							funcLoginAction();
						}
					})
				} else if (data == '0') { // 아이디 찾기 실패, 페이지 이동 없음
					alert('가입정보가 없습니다. 입력 정보를 다시 확인하세요.');
					frm.irum.focus();
					frm.irum.select();
				}
			}
		})
	 }
}

// 비밀번호 찾기 함수
function funcFindPwd () {
	 document.frm.receiver.onkeyup = function(e){
		 if(e.keyCode == 13) funcForgetPwd();
	 }
	 document.frm.btnFindPwd.onclick = function () {
		 funcForgetPwd();
	 }
}
function funcForgetPwd(){
	 var frm = document.frm;
	 var mid = frm.mid;
	 var irum = frm.irum;
	 var receiver = frm.receiver;
	 
	 if(mid.value =='') {
		 alert("아이디를 입력해주세요");
		 mid.focus();
	 }
	 else if(irum.value == ''){
		 alert("이름을 입력해주세요");
		 irum.focus();
	 }
	 else if(receiver.value == ''){
		 alert("이메일을 입력해주세요");
		 receiver.focus();
	 }
	 else {
		 // frm.submit();
		 var formData = new FormData(frm);
		$.ajax({
			url: 'email.emailId',
			data: formData,
			contentType: false,
			processData: false,
			type: 'post',
			success: function (data) {
				var result = data;
				alert("비밀번호 찾기 결과: " + result);
				if (result == '1') { // 비밀번호 찾기 성공, 로그인 페이지로 이동
					alert('회원님의 암호를 이메일로 전송하였습니다. 로그인해 주십시오.');
					
					// 로그인 모달 열기
					var modalWindow = document.getElementById('modalWindow');
					var modalContent = document.getElementById('modalContent');
					var innerModalContent = document.getElementById('innerModalContent');
					
					$.ajax({
						type: 'get',
						url: '/junggo/views/jmember/jmember_login.jsp',
						dataType: 'html',
						success: function (html, status) {
							modalContent.setAttribute('style', 'height: 60%; margin: 14% auto;');
							innerModalContent.setAttribute('style', 'position: absolute; width: 96%; height: 90%; top: -10px;');
							
							innerModalContent.innerHTML = html;
							modalWindow.style.display = 'block';
							
							funcLoginAction();
						}
					})
				} else if (data == '0') { // 비밀번호 찾기 실패, 페이지 이동 없음
					alert('가입정보가 없습니다. 입력 정보를 다시 확인하세요.');
					frm.irum.focus();
					frm.irum.select();
				}
			}
		})
	 }
}