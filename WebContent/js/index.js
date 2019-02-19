/*
 * 작성자: 라대성
 * 작성일: 190216
 * 기능: index 화면에 대한 기본 스크립트 모음
 */

$(function () {
	// 모달
	var modalWindow = document.getElementById('modalWindow');
	var modalContent = document.getElementById('modalContent');
	var innerModalContent = document.getElementById('innerModalContent');
	
	$(window).click(function (ev) { // 모달 외 화면 클릭 시
		if (ev.target == modalWindow) {
			modalWindow.style.display = 'none';
		}
	});
	$(window).keydown(function (ev) { // esc 버튼 입력 시
		if (ev.keyCode == '27') {
			modalWindow.style.display = 'none';
		}
	});
	
	// 헤더 페이드인/아웃
	$('.fadeIn > h1').fadeIn(1000);
	$('.fadeIn > p').fadeIn(1000);
	$('.fadeIn > .my-btn').delay('slow').fadeIn(1000);
	
	$('#loginAnc').click(function () {
		$.ajax({
			type: 'get',
			url: '/junggo/component/loginForm.html',
			dataType: 'html',
			success: function (html, status) {
				modalContent.setAttribute('style', 'height: 60%; margin: 14% auto;');
				innerModalContent.setAttribute('style', 'position: absolute; width: 96%; height: 90%; top: -10px;');
				
				innerModalContent.innerHTML = html;
				modalWindow.style.display = 'block';
				
				loginlogin();
			}
		})
	});
	$('#joinAnc').click(function () {
		$.ajax({
			type: 'get',
			url: '/junggo/component/joinForm.html',
			dataType: 'html',
			success: function (html, status) {
				modalContent.setAttribute('style', 'height: 78%; margin: 7% auto;');
				innerModalContent.setAttribute('style', 'position: absolute; width: 97%; height: 90%; top: 0;');
				
				innerModalContent.innerHTML = html;
				modalWindow.style.display = 'block';
				
				joinjoin();
			}
		})
	});
});