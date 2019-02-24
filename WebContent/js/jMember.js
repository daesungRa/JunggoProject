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

// 회원정보 조회 페이지 함수
// 정보 수정 버튼 클릭시 수정 페이지로 스위칭됨
function funcMemberView () {
	$('#showModifyPage').click(function () { // 회원정보 수정 페이지 로드
		alert('회원정보 수정 페이지입니다.');
		$('#infoTitle').text('회원정보 수정');
		// $('#mid').removeAttr('readonly');
		$('#midChkResult').text('아이디는 수정 불가합니다.');
		// $('#btnIdChk').css('display', 'block');
		$('#midChk').val('checked'); // 아이디는 세션 아이디 사용
		$('#pwdChk').val('checked'); // 비번은 사용자 입력 비번 사용
		$('#irum').removeAttr('readonly');
		$('#email').removeAttr('readonly');
		$('#phone').removeAttr('readonly');
		$('#btnPostal').css('display', 'block');
		$('#addressAdd').removeAttr('readonly');
		$('#photo').css('display', 'block');
		$('#btnModifySubmit').css('display', 'inline-block');
		$('#btnModifyCancel').css('display', 'inline-block');
		$('#showModifyPage').css('display', 'none');
		$('#btnMemberDelete').css('display', 'none');
		
		funcModifyAction();
	});
	$('#btnMemberDelete').click(function () { // 회원탈퇴. 비번 확인 필수
		var inputPwd = prompt('회원탈퇴를 위해 가입 시 등록한 비밀번호를 입력하십시오.');
		if (inputPwd != null && inputPwd != '') {
			var result = confirm('정말 탈퇴하시겠습니까? 관련된 모든 정보는 삭제됩니다.');
			if (result) {
				$.post('delete.mb', {pwd: inputPwd}, 
				function (data, status){
					if (data == '1') {
						alert('회원탈퇴가 완료되었습니다.');
						location.href = '/junggo/index.jsp'; // 탈퇴시 관련 파일도 삭제되도록
					} else if (data == '0') {
						alert('회원탈퇴에 실패했습니다. 입력 정보를 다시 확인해주세요.');
						// location.href = '/junggo/index.jsp';
					}
					return;
				});
			} else {
				alert('회원탈퇴가 취소되었습니다.');	
			}
		} else {
			alert('회원탈퇴가 취소되었습니다.');	
		}
	});
}
function funcModifyAction () {
	var xhr = new XMLHttpRequest();
	var joinFrm = document.joinFrm;
	
	joinFrm.irum.focus();
	joinFrm.irum.select();
	
	// mid
	joinFrm.btnIdChk.onclick = function () {
		funcIdChk(xhr);
	}
	// pwd
	joinFrm.pwd01.onkeyup = function () {
		funcPwdChk(pwd01, pwd02);
	}
	joinFrm.pwd02.onkeyup = function () {
		funcPwdChk(pwd01, pwd02);
	}
	// irum
	joinFrm.irum.onkeyup = function () {
		funcNameChk();
	}
	// email
	joinFrm.email.onkeyup = function () {
		funcEmailChk();
	}
	// phone
	joinFrm.phone.onkeyup = function () {
		funcPhoneChk();
	}
	// postal, address
	joinFrm.btnPostal.onclick = function () {
        searchPostal();
    }
	// photo file
	joinFrm.photo.onchange = imagePreView;
	// btn submit
	joinFrm.btnModifySubmit.onclick = function () {
		var inputPwd = prompt('회원정보 수정을 위해 가입 시 등록한 비밀번호를 입력하십시오.');
		if (inputPwd != null && inputPwd != '') {
			$('#pwd01').val(inputPwd); // 입력받은 비밀번호를 폼 태그 내에 세팅
			funcModifySubmit(joinFrm);
		} else {
			alert('회원정보 수정 취소');
		}
	}
	// cancel
	joinFrm.btnModifyCancel.onclick = function () {
		// window.location.reload(); // 수정 요망
		$('#loadMemberView').trigger('click');
	}
}
//회원정보 수정 최종 제출 함수
function funcModifySubmit (frm) {
	// 폼 내부 모든 input 중 타입이 hidden 인 태그만 검증
	var inputs = frm.getElementsByTagName('input');
	for (var i = 0; i < inputs.length; i++) {
		var tag = inputs[i];
		if (tag.type.toLowerCase() == 'hidden') {
			var chkState = tag.value;
			// 현재 태그가 unChecked 상태라면 메시지 발생 후 포커싱
			if (chkState == 'unChecked') {
				if (tag.id == 'midChk') {
					alert('아이디 중복확인이 필요합니다');
					frm.mid.focus();
					frm.mid.select();
					return;
				} else if (tag.id == 'pwdChk') {
					alert('비밀번호는 영문, 숫자, 특수문자 조합만 가능합니다');
					frm.pwd.focus();
					frm.pwd.select();
					return;
				} else if (tag.id == 'irumChk') {
					alert('이름은 한글 혹은 영문으로만 입력하십시오');
					frm.irum.focus();
					frm.irum.select();
					return;
				} else if (tag.id == 'emailChk') {
					alert('이메일 형식에 맞게 입력하십시오');
					frm.email.focus();
					frm.email.select();
					return;
				} else if (tag.id == 'phoneChk') {
					alert("연락처 형식에 맞게 입력하십시오 ( '-' 포함)");
					frm.phone.focus();
					frm.phone.select();
					return;
				}
			}
		}
	} // 입력 데이터 검증 로직 끝
	
	// 제출
	// frm.submit();
	var formData = new FormData(frm);
	$.ajax({
		url: 'modify.mb',
		data: formData,
		contentType: false,
		processData: false,
		type: 'post',
		success: function (data) {
			var result = data;
			alert("회원정보 수정 결과: " + result);
			if (result == '1') { // 회원정보 수정 성공, 뷰 페이지로 이동
				alert('회원정보 수정에 성공했습니다.');
				
				$('#loadMemberView').trigger('click');
			} else if (data == '0') { // 회원정보 수정 실패, 페이지 이동 없음
				alert('회원정보 수정에 실패했습니다. 입력 정보를 다시 확인하세요. 혹은 비밀번호가 일치하지 않았을 수 있습니다.');
				frm.irum.focus();
				frm.irum.select();
			}
		}
	})
} // end of modify function