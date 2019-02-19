/*
 * 작성자: 이동훈
 * 작성일: 190219
 * 기능: 게시판에 대한 기본 스크립트 모음
 */

/* list page */
function categoryTogle(){ //수정요망
	var val = $('#btnToggle').val();
	console.log(val);
	var frm = document.frm;
	if(val == '삽니다'){
		location.href='./clone_board_list.jsp?category=1';
	}else if(val == '팝니다'){
		location.href='./clone_board_list.jsp?category=0';
	}
}
function movePage(nowPage){ //수정요망
	var frm = document.frm;
	frm.nowPage.value = nowPage;
	frm.submit();
}
function bdSearch() { //수정요망
	var frm = document.frm;
	location.href='index.jsp?content=./clone_board_list.jsp?search='+frm.tf.value;
}

/* view page */
function backOrNext(param){ //수정요망
	var serial = $('#cd').val();
	if(param == 1){
		if(serial == 1 ){
			alert('처음 글입니다.');
			return;
		}
		location.href='./clone_board_view.jsp?serial='+(--serial);
	}else if(param == 2){
		location.href='./clone_board_view.jsp?serial='+(++serial);
	}
}