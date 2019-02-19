<%@page import="junggo.board.BrdVo"%>
<%@page import="junggo.board.BrdDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<title>Insert title here</title>
<style>
.container{border: 1px solid black;}
#precautions{border: 1px dotted gray; background-color:#fdfdfd;}
</style>
<script>
function backOrNext(param){
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
</script>
</head>
<body>
	<%
	String serial = "";
	String searchSize = request.getParameter("size");
	
	if(request.getParameter("serial") != null) {
		serial = request.getParameter("serial");
	} else {
		response.sendRedirect("error_page.jsp");
	}
	BrdDao dao = new BrdDao();
	BrdVo vo = dao.view(serial);
	%>
	<div class='container'>
		<div class='row'>
			 <div class='col-md-4'>
			 	<input class='btn btn-default' type='button' value='이전글' onclick='backOrNext(1)'>
			 	<input class='btn btn-default' type='button' value='다음글' disabled onclick='backOrNext(2)'>
			 </div>
			 <div class='col-md-8'>
			 	<div class='text-right'>
			 		<input class='btn btn-default' type='button' value='목록' onclick="location.href='./clone_board_list.jsp'">
			 	</div>
			 </div>
		</div>
		<div class='row'>
			<div class='col-md-8'>
			 	<div class='text-left'>
			 		<label><%=vo.getSubject() %></label>
			 		<label><%=vo.getStringStatus() %></label>
			 	</div>
			</div>
			<div class='col-md-4'>
				<div class='text-right'>
			 		<label><%=vo.getMdate() %></label>
			 	</div>
			</div>
		</div>
		<div class='row'>
			<div class='col-md-6'>
				<img src='http://placehold.it/20x20' alt=''>
				<label><%=vo.getId() %></label>
			</div>
		</div>
		<div class='row'>
			<div class='col-md-4'>
				<div class='text-right'>
					<img src='http://placehold.it/300x200' alt=''>
				</div>
			</div>
			<div class='col-md-8'>
				<h3>제품명: <%=vo.getSubject() %></h3>
				<h2>가격: <%=vo.getStringPrice() %></h2>
			</div>
		</div>
		<div id='precautions' class='text-center'>
			<p>* 거래전 필독! 주의하세요!</p>
			<p>* 연락처가 없이 외부링크, 카카오톡, 댓글로만 거래할 때</p>
			<p>* 연락처 및 계좌번호를 사이버캅과 더치트로 꼭 조회해보기</p> 
			<p>* 업체인 척 위장하여 신분증과 사업자등록증을 보내는 경우</p> 
			<p>* 고가의 물품(휴대폰,전자기기)등만 판매하고 최근(1주일 내) 게시글만 있을 때</p> 
			<p>* 해외직구로 면세받은 물품을 판매하는 행위는 불법입니다.</p>
		</div>
		<div class='row'>
			<div class='col-md-4'>
				<label>댓글 수(<%=vo.getRep() %>)</label>
				<label>조회 수(<%=vo.getHit() %>)</label>
			</div>
			<div class='col-md-8'>
				<div class='text-right'>
					<input class='btn btn-default' type='button' value='수정'>
					<input class='btn btn-default' type='button' value='판매완료' onclick=''>
				</div>
			</div>
			
		</div>
		<div class='row'>
			<div class='col-md-3'>
				<img src='http://placehold.it/30x30'/><label>작성자</label><label>[작성일]</label><br>
			</div>
			<div class='col-md-9'>
				<label>댓글 내용</label>
			</div>
		</div>
		<div class='row'>
			<div class='col-md-10'>
				<input class="form-control input-lg" type="text" placeholder="댓글 쓰기">
			</div>
			<div class='col-md-2'>
				<button type="button" class="btn btn-default btn-lg btn-block">댓글쓰기</button>
			</div>
		</div>
	</div>
	<input type='hidden' id='cd' value='<%=vo.getSerial()%>'/>
</body>
</html>