/*
 * 작성자: 라대성
 * 작성일: 190216
 * 기능: 화면을 구성하는 top, navBar, loginForm, joinForm, header, footer, modal 에 대한 기본 스크립트 모음
 */

		/* 로그인, 아이디 체크, 조인 함수 내 서블릿 요청경로 수정 필요(190219) */

// 페이지 내 링크 이동
function funcMovePage (page) {
	var offset = $("#" + page).offset();
    $('html, body').animate({scrollTop : offset.top}, 400);
    
    switch (page) {
    case 'top':
    	$('.fadeIn > h1').fadeOut(0);
    	$('.fadeIn > p').fadeOut(0);
    	$('.fadeIn > .my-btn').fadeOut(0);
    	
    	$('.fadeIn > h1').fadeIn(1000);
    	$('.fadeIn > p').fadeIn(1000);
    	$('.fadeIn > .my-btn').delay('slow').fadeIn(1000);
    	break;
    case 'header':
    	
    	break;
    case 'content':
    	
    	break;
    }
}

// 브라우저 사이즈 표시
function getWindowSize () {
	var width = window.outerWidth;
	$('#windowSize').text('window size : ' + width);
}

// 입력 내용 체크 후 로그인 실행 함수
function funcLoginAction () {
	var loginFrm = $('#loginFrm');
	var mid = $('#loginFrm #mid');
	var pwd = $('#loginFrm #pwd');
	var btnSubmit = $('#loginFrm #btnSubmit');
	
	mid.focus();
	mid.keyup(function (ev) {
		if (ev.keyCode == '13') {
			pwd.focus();
		}
	});
	pwd.keyup(function (ev) {
		if (ev.keyCode == '13') {
			btnSubmit.trigger('click');
		}
	});
	
	btnSubmit.click(function () {
		if (mid.val() == '') {
			alert('아이디를 입력하세요.');
			mid.focus();
		} else if (pwd.val() == '') {
			alert('비밀번호를 입력하세요.');
			pwd.focus();
		} else {
			loginFrm.submit();
		}
	});
}

//입력 내용 체크 후 조인 실행 함수
function juncJoinAction () {
	var joinFrm = document.joinFrm;
	
	joinFrm.mid.focus();
	joinFrm.mid.select();
	
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
	joinFrm.btnSubmit.onclick = function () {
		funcSubmit(joinFrm);
	}
	// cancel
	joinFrm.btnCancel.onclick = function () {
		window.location.reload();
		$('#joinAnc').trigger('click');
	}
}
// 아이디 체크 함수
function funcIdChk (xhr) {
	var mid = document.getElementById('mid');
	var btnIdChk = document.getElementById('btnIdChk');
	var midChk = document.getElementById('midChk');
	var midChkResult = document.getElementById('midChkResult');
	
	if (mid.value == '') {
		alert('아이디를 입력하세요');
		mid.focus();
		return;
	}

	if (btnIdChk.value == '중복확인') {
		xhr.open('post', '/desktop/member/idChk');
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.send('mid=' + mid.value);
		xhr.onreadystatechange = function () {
			if(xhr.readyState == 4 && xhr.status == 200) {
				var result = xhr.responseText;
				if (result != '1') {
					alert('아이디 중복확인 완료');
					mid.setAttribute("readonly", true);
					midChk.value = 'checked';
					btnIdChk.value = '재설정';
					midChkResult.innerHTML = '중복확인 완료';
				} else {
					alert('이미 존재하는 아이디입니다');
					midChk.value = 'unChecked';
					mid.focus();
					mid.select();
					midChkResult.innerHTML = '';
				}
			}
		}
	} else if (btnIdChk.value == '재설정') {
		mid.removeAttribute("readonly");
		midChk.value = 'unChecked';
		btnIdChk.value = '중복확인';
		mid.focus();
		mid.select();
		midChkResult.innerHTML = '';
	}
}
	// 비번 체크 함수
function funcPwdChk (pwd01, pwd02) {
	var pwdChk = document.getElementById('pwdChk');
	var pwdChkResult = document.getElementById('pwdChkResult');
	
	if (pwd01.value != pwd02.value || pwd01.value == '') {
		pwdChk.value = 'unChecked';
		pwdChkResult.innerHTML = '비밀번호가 일치하지 않습니다';
	} else if (pwd01.value == pwd02.value || pwd01.value != '') {
		pwdChk.value = 'checked';
		pwdChkResult.innerHTML = '';
	}
}
	// 이름 체크 함수
function funcNameChk () {
	var irumExp = /^[가-힣a-zA-Z]+$/;
	var irum = document.getElementById('irum');
	var irumChk = document.getElementById('irumChk');
	var irumChkResult = document.getElementById('irumChkResult');
	
	if (irumExp.test(irum.value)) {
		irumChk.value = 'checked';
		irumChkResult.innerHTML = '';
	} else {
		irumChk.value = 'unChecked';
		irumChkResult.innerHTML = '한글 혹은 영문으로만 입력하십시오';
	}
}
	// 이메일 체크
function funcEmailChk () {
	var emailExp = /^\w+@\w+.\w(.\w){1,2}/;
	var email = document.getElementById('email');
	var emailChk = document.getElementById('emailChk');
	var emailChkResult = document.getElementById('emailChkResult');
	
	if (emailExp.test(email.value)) {
		emailChk.value = 'checked';
		emailChkResult.innerHTML = '';
	} else {
		emailChk.value = 'unChecked';
		emailChkResult.innerHTML = '이메일 형식에 맞게 입력하십시오';
	}
}
	// 전화번호 체크
function funcPhoneChk () {
	var phoneExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
	var phone = document.getElementById('phone');
	var phoneChk = document.getElementById('phoneChk');
	var phoneChkResult = document.getElementById('phoneChkResult');
	
	if (phoneExp.test(phone.value)) {
		phoneChk.value = 'checked';
		phoneChkResult.innerHTML = '';
	} else {
		phoneChk.value = 'unChecked';
		phoneChkResult.innerHTML = '연락처 형식에 맞게 입력하십시오';
	}
}
// 우편번호 찾기 API (daum kakao)
function searchPostal () {
    new daum.Postcode({
        oncomplete: function(data) {
            var frm = document.joinFrm;
            frm.postal.value = data.zonecode;
            frm.address.value = data.address;
        }
    }).open();
}
// 이미지 미리보기 함수
function imagePreView (e) {
    var profile = document.getElementById('image');
    var url = e.srcElement;
    var file = url.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function (e2) {
        var img = new Image();
        img.src = e2.target.result;
        profile.src = img.src;
    }
}
// 최종 제출 함수
function funcSubmit (frm) {
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
	
	frm.submit();
} // end of join function