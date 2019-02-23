<!-- 
	[게시글 뷰 페이지]
	- jboard.css
	- jboard.js
	- index 페이지에 로드될 것이므로, 관련 스크립트는 index.js 에 작성할 것
 -->

<%@page import="java.util.Date"%>
<%@page import="junggo.jboard.JBoardVo"%>
<%@page import="junggo.jboard.JBoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" 
content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Insert title here</title>
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
<script>

$(document).ready(function(){
	$('#btnComment').click(function(){
		//var frm=$('form[name=commentForm]').serialize();
		var mid=$('#mid').val();
		var cdate=$('#cdate').val();
		var comment=$('#comment').val();
		var jserial=$('#jserial').val();
		$.post('insertRep.bd', 
			{
			mid : mid,
			cdate : cdate,
			comment : comment,
			jserial : jserial
			}, function(data){
			$("#commentList").append(data);
			} 
		);
	});	
});
</script>
</head>
<body>
<c:set var='vos' value='${requestScope.vos }'></c:set>
<div class='container' id='view'>

	<div class='row' >
		<div class='col'>
			<span id='subject'>${vos.subject }</span>
			<span id='kategorie'>${vos.status }</span>
		</div>
		<div class='col.mr-3'>
			<span><a href='#'>목록으로</a></span>
		</div>
	</div>
	<hr/>
	
	<div class='row'>
		<div class='col'>
			<span>${vos.id }</span>
		</div>
		<div class='col-2.mr-3'>
			<label id='mdate'>${vos.mdate }</label>
			<output>[ ${vos.hit } ]</output>
			<input type='hidden' value='${vos.serial }' id='jboard_serial'/>
		</div>
	</div>
	<hr/>
	
	<div class='container'>
		<div class='row'>
			<c:forEach var='imgs' items='${vos.attFiles }'>
				<img src='./img/jboard/${imgs }' width=200px height=300px />
			</c:forEach>
			<div class='col'>
				<ul class='list-group'>
					<li class='list-group-item'>${vos.status }</li>
					<li class='list-group-item'>${vos.subject }</li>
					<li class='list-group-item'>${vos.price }</li>
				</ul>
			</div>	
		</div>
	</div>
	
<!--게시글에 저장된 댓글 목록  -->	
	<div class="container">
        <div class="commentList" id='commentList'>
			<c:set var='rlist' value='${requestScope.rlist }'></c:set>
    		 	<c:forEach var='rep'  items='${rlist }'>
    				<c:if test="${rep !='null' }">
    					<input type='hidden' id='rserial' value='${rep.rserial }' />
	        			<span id='mid'>${rep.mid }</span><br/>
	        			<textarea name='comment' 
	        			<c:if test='${sessionScope.mid == null }'>readonly</c:if>>${rep.comment }
	        			</textarea> 
	        			<output id='cdate'>[${rep.cdate }]</output>
	        			<input type='hidden' id='pserial' value='${rep.pserial }'>
       					<hr/>
      				</c:if> 	
       			</c:forEach> 
        	</div>
    </div>
    
    
 <!-- 상세페이지에서 댓글 작성 폼 -->   
    <c:if test="${sessionScope.mid != null }">
	    <div class="container">
	        <label for="comment">comment</label>
	            <div class="input-group">
	               <input type="hidden"  id='jserial' value="${vos.serial }"/>
	               <input type='hidden'  value='${sessionScope.id }' id='mid'/>
	               <output id='cdate'>[ <%=new Date().toLocaleString() %> ]</output>
	               <input type="text" class="form-control" id="comment" name="comment" 
	              				placeholder="내용을 입력하세요." >
	               <span class="input-group-btn">
	                 	<input class="btn btn-default" type="button" 
	                    	name="btnComment"  id="btnComment"  value='등록' />
	               </span>
	            </div>
	    </div>
	</c:if>

</div>

</body>
</html>