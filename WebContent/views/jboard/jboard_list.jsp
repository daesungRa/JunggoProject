<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix='c' %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
function moveInsertPage(){
	$.post('/junggo/views/jboard/jboard_insert.jsp',
	function (data, status){
		$('#content').html(data);
	});
}
function moveModifyPage(){
	$.post('/junggo/views/jboard/jboard_modify.jsp',{
		serial : "41",
	},
	function (data, status){
		$('#content').html(data);
	});
}
</script>
<body>
	<c:if test="${not empty requestScope.list }">
		<c:set var="list" 	   value="${requestScope.list }"></c:set>
		<c:set var="category"  value="${requestScope.category }"></c:set>
		<c:set var="nowBlock"  value="${requestScope.nowBlock }"></c:set>
		<c:set var="startPage" value="${requestScope.startPage }"></c:set>
		<c:set var="endPage"   value="${requestScope.endPage }"></c:set>
		<c:set var="totBlock"  value="${requestScope.totBlock }"></c:set>
		<c:set var="totPage"   value="${requestScope.totPage }"></c:set>
	</c:if>
	<div class='container'>
		<div id='brd_title'>
			<p class="text-center">게시판</p>
		</div>
		<div id="search-row">
			<div id="row-right">
				<form class="form-inline" name='frm'>
		  			<input class="form-control" type="text" id='tf' value="${(param.search==null)? '': param.search }" name='search' autocomplete='off' style='border-radius: 20px 0 0 20px'/>
	  				<input class="btn btn-default" type="button" id='btnSearch' value="검색" onclick='bdSearch(${category});' style='border-radius: 0 20px 20px 0'/>
	  				<input type='hidden' id='np' name='nowPage' value='${(param.nowPage==null)? 1: param.nowPage }'/>
	  				<input type='hidden' id='cg' value='${(param.category==null)? 0 : param.category}'/>
				</form>
			</div>
		</div>
		<div id='category'>
			<span class='text-right'>
				<input class="btn btn-default" type="button"  id='btnToggle' value="삽니다" onclick='categoryToggle()'>
				<input class="btn btn-default" type="button"  id='btnInsert' value="글쓰기" onclick='moveInsertPage()'>
				<input class="btn btn-default" type="button"  id='btnInsert' value="수정" onclick='moveModifyPage()'>
			</span>
		</div>
		<hr/>
		<div class='row'>
			<c:forEach var='i' items='${list}'>
				<div class='col-md-4 text-center' >
					<img src='./img/jboard/${i.photo}' id='thumbnail' onerror="this.src='http://placehold.it/200x200'">
					<h2><a href='#' onclick='moveViewPage(${i.serial})'>${i.serial}.${i.subject} <label style='color:green'>[${i.rep}]</label></a></h2>
					<p>${i.price}</p>
				</div>
			</c:forEach>
		</div>
		
		<div id='page-center'>
			<ul>
				<c:if test='${nowBlock > 1}'>
					<li class="page-item">
						<a href="#" onclick='movePage(1,${category})'>
							<span>처음</span>
						</a>
					</li>
					<li class="page-item">
						<a href="#" onclick='movePage(${startPage-1},${category})'>
							<span>이전</span>
						</a>
					</li>
				</c:if>
				<c:forEach var='j' begin='${startPage}' end='${endPage }'>
					<li class="page-item">
						<a href="#" onclick='movePage(${j},${category})'>
							<span>${j}</span>
						</a>
					</li>
				</c:forEach>
				<c:if test='${nowBlock < totBlock}'>
					<li class="page-item">
						<a href="#" onclick='movePage(${endPage+1},${category})'>
							<span>다음</span>
						</a>
					</li>
					<li class="page-item">
						<a href="#" onclick='movePage(${totPage},${category})'>
							<span>마지막</span>
						</a>
					</li>
				</c:if>
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