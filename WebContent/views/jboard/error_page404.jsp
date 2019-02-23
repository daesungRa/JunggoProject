<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" />
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
<style>
	.container.error-page {border:1px dotted black;}
	.text-center {margin:0 auto;}
	.error-title, .error-helpful {font-family:'Helvetica', sans-serif; font-weight: bold;}
	.error-title {font-size:48px;  margin-top:50px;}
	.error-helpful {font-size:24px;}
	#state-code {font-size:300px;color:lightgray;opacity:0.5;}
</style>
</head>
<body>
	<div class='container error-page'>
		<div class='row'>
			<div class='text-center error-title'>페이지를 찾지 못했습니다.</div>
		</div>
		<div class='row'>
			<div class='text-center'>
				<div id='state-code'>404</div>
			</div>
		</div>
		<div class='row'>
			<div class='text-center'>
				<label class='error-helpful'>Helpful Links</label>
				<ul>
					<li><a href='/junggo/index.jsp'>Home</a></li>
					<li><a href='#'>로그인</a></li>
					<li><a href='#'>회원가입</a></li>
					<li><a href='#'>공지사항</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>