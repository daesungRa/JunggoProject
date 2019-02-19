<%@page import="junggo.board.BrdVo"%>
<%@page import="junggo.board.BrdDao"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<style>
a {color: black;}
#brd_title {padding:20px;box-sizing: border-box;font-family: 'Helvetica';font-size: 48px;font-weight: bold;}
#thumbnail {width:100%;height:200px;}
.row .page-item {display:inline-block;list-style:none;font-size:20px;}
.col-md-4:hover {background-color: #dfdfdf;}
</style>
<script>
function categoryTogle(){
	var val = $('#btnToggle').val();
	console.log(val);
	var frm = document.frm;
	if(val == '삽니다'){
		location.href='./clone_board_list.jsp?category=1';
	}else if(val == '팝니다'){
		location.href='./clone_board_list.jsp?category=0';
	}
}
function movePage(nowPage){
	var frm = document.frm;
	frm.nowPage.value = nowPage;
	frm.submit();
}
function bdSearch() {
	var frm = document.frm;
	location.href='index.jsp?content=./clone_board_list.jsp?search='+frm.tf.value;
}
</script>
</head>
<body>
	<%
	BrdDao dao = new BrdDao();
	String search = "";
	if(request.getParameter("search") != null) search = request.getParameter("search");
	int nowPage = 1;
	if(request.getParameter("nowPage") != null) nowPage = Integer.parseInt(request.getParameter("nowPage"));
	int category = 0;
	if(request.getParameter("category") != null) category = Integer.parseInt(request.getParameter("category"));
	List<BrdVo> list = dao.list(search, nowPage, category);
	%>
	<div class='container'>
		<div id='brd_title'>
			<p class="text-center">게시판</p>
		</div>
		<div class="text-right">
			<form class="form-inline" name='frm'>
	  			<input class="form-control" type="text" id='tf' value="${(param.search==null)? '': param.search }" name='search' autocomplete='off'/>
  				<input class="btn btn-default" type="button" id='btnSearch' value="검색" onclick='bdSearch();'/>
  				<input type='hidden' id='np' name='nowPage' value='${(param.nowPage==null)? 1: param.nowPage }'/>
  				<input type='hidden' id='cg' value='${(param.category==null)? 0 : param.category}'/>
			</form>
		</div>
		<div id='category'>
			<span class='text-right'>
				<input class="btn btn-default" type="button"  id='btnToggle' value="삽니다" onclick='categoryTogle()'b>
				<input class="btn btn-default" type="button"  value="글쓰기" onclick=''>
			</span>
		</div>
		<hr/>
		<div class='row'>
			<%for(int i=0;i<list.size();i++){ %>
			<div class='col-md-4 text-center'>
				<h1><a href='./clone_board_view.jsp?serial=<%=list.get(i).getSerial()%>&size=<%=dao.totSize%>'><%=list.get(i).getSubject() %></a></h1>
				<img src='./<%=list.get(i).getPhoto() %>' id='thumbnail'>
				<p><%=list.get(i).getStringPrice() %></p>
			</div>
			<%} %>
		</div>
		
		<div class='row text-center'>
			<ul>
				<%if(dao.nowBlock > 1){  %>
				<li class="page-item">
					<a class="page-link" href="#" aria-label="Previous" onclick='movePage(1)'>
						<span aria-hidden="true">처음</span>
					</a>
				</li>
				<li class="page-item">
					<a class="page-link" href="#" aria-label="Previous" onclick='movePage(<%=dao.startPage-1%>)'>
						<span aria-hidden="true">이전</span>
					</a>
				</li>
				<%} %>
					<%for(int j=dao.startPage; j<=dao.endPage;j++){%>
					<li class="page-item">
						<a class="page-link" href="#" onclick='movePage(<%=j%>)'><%=j %></a>
					</li>
					<%} %>
				<%if(dao.nowBlock < dao.totBlock){ %>
				<li class="page-item">
					<a class="page-link" href="#" aria-label="Next" onclick='movePage(<%=dao.endPage+1%>)'>
						<span aria-hidden="true">다음</span>
					</a>
				</li>
				
				<li class="page-item">
					<a class="page-link" href="#" aria-label="Next" onclick='movePage(<%=dao.totPage%>)'>
						<span aria-hidden="true">마지막</span>
					</a>
				</li>
				<%} %>
			</ul>
		</div>
	</div>
	<script>
	$(function(){
		var category = $('#cg').val();
		if(category == 0){
			$('#btnToggle').attr('value', '삽니다');
		}else if(category == 1){
			$('#btnToggle').attr('value', '팝니다');
		}
	});
	</script>
</body>
</html>