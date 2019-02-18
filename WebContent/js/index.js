/*
 * 작성자: 라대성
 * 작성일: 190216
 * 기능: index 화면을 구성하는 navBar, header, content, footer 에 대한 기본 스크립트 모음
 */

$(function () {	
	$('.fadeIn > h1').fadeIn(1000);
	$('.fadeIn > p').fadeIn(1000);
	$('.fadeIn > .my-btn').delay('slow').fadeIn(1000);
	
	$('#content').load(function () {
		alert('focus');
	});
});
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