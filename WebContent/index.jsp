<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Welcome to Junggo-heaven!</title>
<!-- style -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" />
<link rel="stylesheet" href="/junggo/css/index.css" />
<link rel="stylesheet" href="/junggo/css/component.css" />

<!-- script -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="/junggo/js/index.js"></script>
<script src="/junggo/js/component.js"></script>
</head>
<body onresize="getWindowSize()">
	
	<!-- 브라우저 크기 확인 (임시) -->
	<div id='windowSize' style='width: 180px; border: 1px solid black;'>
		window size : 
	</div>

	<!-- top -->
	<jsp:include page="/component/top.html"></jsp:include>
	
	<!-- navbar -->
	<jsp:include page="/component/navBar.html"></jsp:include>
	
	<!-- header -->
	<jsp:include page="/component/header.html"></jsp:include>
	
	<!-- article -->
	<article>
		<!-- 사이트 소개 -->
		<div class='container my-intro' id='intro'>
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
		<div class='container my-intro' id='developers'>
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
	
	<!-- footer -->
	<jsp:include page="/component/footer.html"></jsp:include>
	
	<a class='btn btn-info' id='toTop' href='#top' onclick='funcMovePage("top")'>
		<span class='glyphicon glyphicon-chevron-up' aria-hidden="true"></span>top
	</a>
	
	<!-- Modal -->
	<jsp:include page="/component/modal.html"></jsp:include>

</body>
</html>