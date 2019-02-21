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
	$('#closeModal').click(function () {
		modalWindow.style.display = 'none';
	});
	
	// 헤더 페이드인/아웃
	$('.fadeIn > h1').fadeIn(1000);
	$('.fadeIn > p').fadeIn(1000);
	$('.fadeIn > .my-btn').delay('slow').fadeIn(1000);
	
	// 로그인 모달 창 불러오기
	// 이후 funcLoginAction 함수 실행
	$('#loginAnc').click(function () {
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
	});
	
	// 조인 모달 창 불러오기
	// 이후 funcJoinAction 함수 실행
	$('#joinAnc').click(function () {
		$.ajax({
			type: 'get',
			url: '/junggo/views/jmember/jmember_join.jsp',
			dataType: 'html',
			success: function (html, status) {
				modalContent.setAttribute('style', 'height: 82%; margin: 7% auto;');
				innerModalContent.setAttribute('style', 'position: absolute; width: 97%; height: 94%; top: 0;');
				
				innerModalContent.innerHTML = html;
				modalWindow.style.display = 'block';
				
				funcJoinAction();
			}
		})
	});
	
	// 로그아웃
	$('#logoutAnc').click(function(){
		alert("로그아웃되었습니다.");
		location.href ='logout.mb';
	});
	
	// 회원정보 조회
	$('#loadMemberView').click(function(){
		$.ajax({
			type: 'get',
			url: 'view.mb',
			dataType: 'html',
			success: function (html, status) {
				// 에러코드 0 이면 세션아이디 없음, 에러코드 1 이면 조회된 결과 없음
				if (html == "0") { // 세션아이디 없음
					alert('접속 정보가 존재하지 않습니다. 로그인 후 이용해주세요.');
					location.href = '/junggo/index.jsp';
				} else if (html == "1") { // 조회결과 없음 (vo == null)
					alert('현재 접속정보로 조회된 회원정보가 존재하지 않습니다. 확인 후 이용해주세요.');
					location.href = '/junggo/index.jsp';
				} else {
					modalContent.setAttribute('style', 'height: 82%; margin: 7% auto;');
					innerModalContent.setAttribute('style', 'position: absolute; width: 97%; height: 94%; top: 0;');
					
					innerModalContent.innerHTML = html;
					modalWindow.style.display = 'block';
					
					funcMemberView();
				}
			}
		});
	});
	
	// post 방식으로 서블릿 컨테이너에 파라미터와 함께 요청해야 함
	// 네비게이션의 삽니다 버튼
	$('#btnLoadJBoardBuy').click(function () {
		$.post('list.bd',
		{
			category: '1'
		},
		function (data, status){
			$('#content').html(data);
		});
	});
	// 네비게이션의 팝니다 버튼
	$('#btnLoadJBoardSell').click(function () {
		$.post('list.bd',
		{
			category: '0'
		},
		function (data, status){
			$('#content').html(data);
		});
	});
	// 헤더의 삽니다 버튼
	$('#btnBuy').click(function () {
		$.post('list.bd',
		{
			category: '1'
		},
		function (data, status){
			$('#content').html(data);
		});
	});
	// 헤더의 팝니다 버튼
	$('#btnSell').click(function () {
		$.post('list.bd',
		{
			category: '0'
		},
		function (data, status){
			$('#content').html(data);
		});
	});
});