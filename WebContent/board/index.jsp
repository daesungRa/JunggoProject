<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Welcome to Junggo-heaven!</title>
<!-- style -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" />
<link rel="stylesheet" href="/junggo/css/index.css" />

<!-- script -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="/junggo/js/index.js"></script>
</head>
<body onresize="getWindowSize()">

	
	<!-- 브라우저 크기 확인 (임시) -->
	<!-- <div id='windowSize' style='width: 180px; border: 1px solid black;'>
		window size : 
	</div> -->

	<!-- top -->
	<div class='container-fluid' id='top'>
		<div class='row'>
			<div class='col-md-1'></div>
			<div class='col-md-3'>
				<img src='/junggo/img/logo/cat_foot_empty.svg' id='topLogo' />&nbsp;
				<div id='title'>&nbsp;&nbsp;중고천국</div>
			</div>
			<div class='col-md-5'>
			</div>
			<div class='col-md-3'>
				<form>
					<div class='form-group' id='searchForm'>
						<input type='search' class='my-search-bar' />
						<input type='button' class='btn btn-primary btn-sm' value='검색' />
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- navbar -->
	<div class='navbar navbar-expand-md bg-light navbar-light sticky-top'>
		<div class='container'>
			<button class='navbar-toggler collapsed' type='button' data-toggle='collapse' data-target='#navbarResponsive'
				aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
				<span class='navbar-toggler-icon'></span>
			</button>
			<div class='collapse navbar-collapse' id='navbarResponsive'>
				<ul class='navbar-nav' style='color: #343434'>
					<li class='nav-item'>
						<a class='nav-link' href='#home' onclick='funcMovePage("top")'>Home</a>
					</li>
					<li class='nav-item'>
						<a class='nav-link' href='#buy' onclick='funcMovePage("content")'>삽니다</a>
					</li>
					<li class='nav-item'>
						<a class='nav-link' href='#sell' onclick='funcMovePage("content")'>팝니다</a>
					</li>
				</ul>
			</div>
			<div class='navbar navbar-nav navbar-right'>
				<div class='dropdown'>
					
					<% if(session.getAttribute("irum") == null){ %>
					<a class='dropdown-toggle' data-toggle='dropdown' role='button' href='#' style='color: #343434;'>
						접속<span class='caret'></span>
					</a>
					<ul class='dropdown-menu dropdown-menu-right animate slideIn'>
						<li><a class='dropdown-item' href='/junggo/jmember/memberLogin.jsp'>로그인</a></li>    <!-- 가현 -->
						<li><a class='dropdown-item' href='#join'>회원가입</a></li>
					</ul>
					<%}else{ %> 
					  <b><%= session.getAttribute("irum") %></b>님, 환영합니다.
					<a class='dropdown-toggle' data-toggle='dropdown' role='button' href='#' style='color: #343434;'>
						접속<span class='caret'></span>
					</a>
					<ul class='dropdown-menu dropdown-menu-right animate slideIn'>
						<li><a class='dropdown-item' onclick='funcLogout()'>로그아웃</a></li>                   <!-- 가현 -->
						<li><a class='dropdown-item' href='/junggo/jmember/memberView.jsp'>회원정보</a></li>
						
						<% if(session.getAttribute("admin").equals("0")) { %>                   <!-- 가현 관리자 회원목록조회 -->
						<li><a class='dropdown-item' href='/junggo/jmember/memberList.jsp'>회원정보목록</a></li>
					  <% } %> 
					</ul>
					
					<% } %>
				</div>
			</div>
		</div>
	</div>
	<!-- header -->
	<header id='header'>
		<div class='container-fluid' id='headerContainer'>
			<div class='container' id='headerContent'>
				<div class="fadeIn">
					<h1>중 고 천 국</h1><br/>
					<p>
						없는 것 빼고 모두 다 있는 천국같은 곳!<br/>
						이 사이트는 부트스트랩으로 디자인 하였고, java Servlet 을 이용해 서버환경을 구성했습니다.<br/>데이터베이스는 Oracle 11g xe 입니다
					</p>
				</div>
				<div class="fadeIn">
					<a class='btn btn-primary btn-lg my-btn' href='#buy' onclick='funcMovePage("content")'>삽니다</a>
					<a class='btn btn-primary btn-lg my-btn' href='#sell' onclick='funcMovePage("content")'>팝니다</a>
				</div>
			</div>
		</div>
	</header>
	
	<!-- 본문 -->
	<article>
		<!-- 사이트 소개 -->
		<div class='container my-intro' id='content'>
			<!-- 이하는 예시임 -->
			<div class='row my-intro-row'>
				<div class='col-lg-2 my-intro-grid'></div>
				<div class='col-lg-3 my-intro-grid align-items-center'>안녕하세요</div>
				<div class='col-lg-2 my-intro-grid'></div>
				<div class='col-lg-3 my-intro-grid align-items-center'>소개글입니다</div>
				<div class='col-lg-2 my-intro-grid'></div>
			</div>
		</div>
		<hr />
		<!-- 게시판 로드 -->
		<div class='container my-board' id='content'>
			<h3>게시판 페이지</h3>
			<!-- 이하는 예시임 -->
			<div class='row my-board-row'>
				<div class='col-lg-3 my-board-grid'>게시글 1</div>
				<div class='col-lg-3 my-board-grid'>게시글 2</div>
				<div class='col-lg-3 my-board-grid'>게시글 3</div>
				<div class='col-lg-3 my-board-grid'>게시글 4</div>
			</div>
			<div class='row my-board-row'>
				<div class='col-lg-3 my-board-grid'>게시글 5</div>
				<div class='col-lg-3 my-board-grid'>게시글 6</div>
				<div class='col-lg-3 my-board-grid'>게시글 7</div>
				<div class='col-lg-3 my-board-grid'>게시글 8</div>
			</div>
		</div>
		<hr />
		<!-- 개발자 소개 -->
		<div class='container my-intro' id='content'>
			<!-- 이하는 예시임 -->
			<div class='row my-intro-row'>
				<div class='col-lg-2 my-intro-grid'></div>
				<div class='col-lg-3 my-intro-grid'>개발자</div>
				<div class='col-lg-2 my-intro-grid'></div>
				<div class='col-lg-3 my-intro-grid'>소개소개</div>
				<div class='col-lg-2 my-intro-grid'></div>
			</div>
		</div>
	</article>
	
	<div class='d-flex flex-column'>
		<footer id='sticky-footer' class='page-footer py-4 bg-light text-black-50 my-footer'>
			<div class='container text-center'>
				<small>Copyright &copy; My Webpage</small><br/>
				<small>Copyright &copy; My Webpage</small><br/>
				<small>Copyright &copy; My Webpage</small><br/>
				<small>Copyright &copy; My Webpage</small><br/>
			</div>
		</footer>
	</div>
	
	<a class='btn btn-info' id='toTop' href='#top' onclick='funcMovePage("top")'>
		<span class='glyphicon glyphicon-chevron-up' aria-hidden="true"></span>top
	</a>

</body>
</html>