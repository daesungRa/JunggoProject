/*
 * 작성자: 라대성, 김가현
 * 작성일: 190220
 * 기능: 회원관리에 대한 기본 스크립트 모음
 */
function funcFindPwd () {
	 function funcForget(){
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
			 frm.submit(); 
		 }
	 }
	 document.frm.receiver.onkeyup = function(e){
		 if(e.keyCode == 13) funcForget();
	 }
	 document.frm.btn.onclick = function () {
		 funcForget();
	 }
	 	 
}
function funcFindId () {
	 function funcForget(){
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
			 frm.submit(); 
		 }
	 }
	 document.frm.receiver.onkeyup = function(e){
		 if(e.keyCode == 13) funcForget();
	 }
	 document.frm.btn.onclick = function () {
		 funcForget();
	 }
	 	 
}