/*
 * 작성자: 라대성
 * 작성일: 190216
 * 기능: 화면을 구성하는 top, navBar, loginForm, joinForm, header, footer, modal 에 대한 기본 스크립트 모음
 */

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
function loginlogin () {
	var loginFrm = $('#loginFrm');
	var userId = $('#loginFrm #userId');
	var userPwd = $('#loginFrm #userPwd');
	var btnSubmit = $('#loginFrm #btnSubmit');
	
	userId.focus();
	userId.keyup(function (ev) {
		if (ev.keyCode == '13') {
			userPwd.focus();
		}
	});
	userPwd.keyup(function (ev) {
		if (ev.keyCode == '13') {
			btnSubmit.trigger('click');
		}
	});
	
	btnSubmit.click(function () {
		if (userId.val() == '') {
			alert('아이디를 입력하세요.');
			userId.focus();
		} else if (userPwd.val() == '') {
			alert('비밀번호를 입력하세요.');
			userPwd.focus();
		} else {
			loginFrm.submit();
		}
	});
}

//입력 내용 체크 후 조인 실행 함수
function joinjoin () {
	
}