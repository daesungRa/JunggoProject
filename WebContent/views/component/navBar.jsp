<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>navbar component</title>
</head>
<body>

	<div class='navbar navbar-expand-md bg-light navbar-light sticky-top my-nav'>
		<div class='container'>
			<button class='navbar-toggler collapsed' type='button' data-toggle='collapse' data-target='#navbarResponsive'
				aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
				<span class='navbar-toggler-icon'></span>
			</button>
			<div class='collapse navbar-collapse' id='navbarResponsive'>
				<ul class='navbar-nav' style='color: #343434'>
					<li class='nav-item'>
						<a class='nav-link' href='#home' onclick='location.href = "/junggo/index.jsp"'>Home</a>
					</li>
					<li class='nav-item'>
						<a class='nav-link' href='#buy' id='btnLoadJBoardBuy' onclick='funcMovePage("content")'>삽니다</a>
					</li>
					<li class='nav-item'>
						<a class='nav-link' href='#sell' id='btnLoadJBoardSell' onclick='funcMovePage("content")'>팝니다</a>
					</li>
				</ul>
			</div>
			<div class='navbar navbar-nav navbar-right'>
				<div class='dropdown'>
					<% if(session.getAttribute("mid") == null){ %>
					<a class='dropdown-toggle' data-toggle='dropdown' role='button' href='#' style='color: #343434; text-decoration: none;'>
						접속<span class='caret'></span>
					</a>
					<ul class='dropdown-menu dropdown-menu-right animate slideIn'>
						<li><a class='dropdown-item' id='loginAnc' href='#login'>로그인</a></li>
						<li><a class='dropdown-item' id='joinAnc' href='#join'>회원가입</a></li>
					</ul>
					
					<% }else{ %> 
					<a class='dropdown-toggle' data-toggle='dropdown' role='button' href='#' style='color: #343434; text-decoration: none;'>
						<b><%= session.getAttribute("mid") %></b>님, 환영합니다.<span class='caret'></span>
					</a>
					<ul class='dropdown-menu dropdown-menu-right animate slideIn'>
						<li><a class='dropdown-item' id='logoutAnc' href='#logout'>로그아웃</a></li>                   <!-- 가현 -->

					 <% } %> 
					</ul>
				</div>
			</div>
		</div>
	</div>

</body>
</html>