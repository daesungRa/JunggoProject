<%@page import="junggo.jboard.JBoardAtt"%>
<%@page import="javax.sound.midi.SysexMessage"%>
<%@page import="junggo.jboard.JBoardDao"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="junggo.jboard.JBoardVo"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	
	String serial = "";
	if(request.getParameter("serial") != null) serial = request.getParameter("serial");
	
	JBoardDao dao = new JBoardDao();
	JBoardVo vo = dao.find(serial);
	JBoardAtt att = new JBoardAtt();
	request.setAttribute("vo", vo);
	int c = 0;
	int s = 0;

	c = vo.getCategory();
	s = vo.getStatus2();
%>
<script>
// 이미지 미리보기

  $(document).ready(function() {
	 /*
	  	document.getElementById("pro-image-1").addEventListener('change', readImage, false);
	 */		
	    $( ".preview-images-zone" ).sortable();
	    
	    /* 사진 X버튼 클릭시 이벤트 처리 */
	    $(document).on('click', '.image-cancel', function() {
	        let no = $(this).data('no');
	        
	        $(".preview-image.preview-show-"+no).remove();
	       
	        $(".file-list-" + no).remove();
	        
	    });
	});
	
  	function insertNumber(obj) { 
	    obj.value = unNum(obj.value); 
	} 

	function unNum(str) { 
	    str = String(str); 
	    return str.replace(/[^\d]+/g, ''); 
	}
	

 	var num = 101;
	var ber = 100;
	
	function readImage() {
	  
		if (window.File && window.FileList && window.FileReader) {
	    	
	    	var files = event.target.files; //FileList object
	        var output = $(".preview-images-zone");

	        var fileLoad = $(".file-zone");
	        
	        var dis = $(".file-list-" + (num-1));
        	dis.attr("style" ,"display:none;");
	    
	        for (let i = 0; i < files.length; i++) {
	       
	           	var file = files[i];
	           	
	     
	           	if (!file.type.match('image')) continue;
	            
	            var picReader = new window.FileReader();
	            
	            picReader.addEventListener('load', function (event) {
	            	
	            	var picFile = event.target;
	               
	                
	        	    var fileAppend = 
	                	'<div class = "file-list-'+ num +'">' +
							'<input type="file" name = "pro-image-' + num + '" id = "pro-image-'+ num +'" onchange="readImage()" data-no=' + num + ' style = "color : #fff">' +
						'</div>';
	             
					fileLoad.append(fileAppend);
				
	                var html =  '<div class="preview-image preview-show-' + ber + '">' +
	                            	'<div class="image-cancel" data-no="' + ber + '" >X</div>' +
	                            	'<div class="image-zone"><img id="pro-img-' + ber + '" src="' + picFile.result + '"></div>' +
	                            '</div>';

	                output.append(html);
	               
	                num = num + 1;
	                ber = ber + 1;
	                
	            
	            });
	            picReader.readAsDataURL(file);
	        }
	       
	        // $("#pro-image").val('');
	    } else {
	        console.log('Browser not support');
	    }
	}

</script>
<style>

.form-group a{
	border : 1px solid rgba(150, 100, 200 , 0.5);
	padding : 2px;	
	border-radius: 2px 2px 2px 2px;
	text-decoration: none;
	
}
.preview-images-zone {
	
    border: 1px solid aqua;
    
    height: 100%;
    box-shadow: 3px 3px 3px aqua;
    padding: 10px 10px 5px 10px;
    overflow: auto;
    text-shadow: 3px 3px 3px purple;
}
.preview-images-zone > .preview-image {
    height: 170px;
    width: 160px;
    position: relative;
    margin-right: 5px;
    margin-bottom: 50px;
    float: left;
}
.preview-images-zone > .preview-image > .image-zone {
    width: 100%;
    height: 100%;
}
.preview-images-zone > .preview-image > .image-zone > img {
    width: 100%;
    height: 100%;
}

/*사진 취소버튼*/
.preview-images-zone > .preview-image > .image-cancel {
    font-size: 18px;
    position: absolute;
    top: 0;
    right: 0;
    margin-right: 10px;
    cursor: pointer;
    display: none;
    z-index: 100;
}
.preview-image:hover > .image-zone {
    cursor: move;
    opacity: 0.5;
}

.preview-image:hover > .image-cancel {
    display: block;
}

</style>
</head>
<body>

	<div class = "container">
		<form id = "frm_modify" name = "frm_modify" method = "post" enctype = "multipart/form-data" action = "modify.bd">
			<hr>
			<h2>게시판 수정 폼</h2>
			<hr/>
			<div class = "form-group">
				<label>게시물 수정일</label>
				<output>◆<%=new Date().toLocaleString() %> ◆</output>
		  	</div>
		  	<p>
		  	<label>게시판 카테고리</label>
	  		<select class = "form-control" name = "modifyCategory">
	  			<option value = '0' <%= (c==0) ? "selected" : "" %> >게시판 (삽니다.)</option>
	  			<option value = '1' <%= (c==1) ? "selected" : "" %> >게시판 (팝니다.)</option>
	  		</select>
		  	<p>
		  	<label>판매 상태</label>
	  		<select class = "form-control" name = "modifyStatus">
	  			<option value = '0' <%= (s==0) ? "selected" : "" %> >판매중..!</option>
	  			<option value = '1' <%= (s==1) ? "selected" : "" %> >판매완료..!</option>
	  		</select>
		  	<p>
		  	<div class="form-group">
		    	<label for="modifyId">아이디</label>
		   		<input type="text" class="form-control" id="modifyId" name = "modifyId" value = "${sessionScope.id }"> <!-- session.Scopeid -->
		 	</div>
		 	
		  	<div class="form-group">
		    	<label for="modifySubject">게시물 제목</label>
		    	<input type="text" class="form-control" id="modifySubject" name = "modifySubject" value = <%=vo.getSubject2() %> required="required">
		  	</div>
		  	
		  	<div class="form-group">
		    	<label for="modifyPrice">판매 금액</label>
		  		<input type="text" class="form-control" id="modifyPrice" name = "modifyPrice" value = <%=vo.getPrice2() %> onkeyup="insertNumber(this)" required="required">
		  	</div>
		  	
		  	<label>게시글 작성</label>
		  	<textarea id = "modifyContent" name = "modifyContent" class="form-control" rows="5"><%=vo.getContent() %></textarea>  
		  	<!-- 해당 이미지 파일 미리보기 -->
			<p>
			
			<div class="form-group">
				<div class = "file-zone">
					<div class = "file-list-100">
						<input type= 'file' onchange = "readImage()" name = "pro-image-100" id = 'pro-image-100' data-no = '100' style = "color : #fff">
					</div>
				</div>	
			</div>
			<p>	
			<div class="preview-images-zone">
							
				<!-- 삭제할 파일들을  -->
				<c:set var = "y" value = '1' /> <!-- 비교값 생성 -->
				<c:forEach var='x' items="<%=vo.getAttFiles() %>">
					<div class = "file-list-${y }">
						
						<input type="file" name = "pro-image-${y }" id = "pro-image-${y }" onchange="readImage()" data-no="${y }" style = "display:none" />
						
					</div>
					<div class="preview-image preview-show-${y }">
	             			<div class="image-zone">	
	             				
	             				<img id="pro-img-${y }" src="/junggo/img/jboard/${x }">
	             				<label><input type='checkbox' name='delFiles' value ='${x }'>삭제 체크</label>
	             			</div>
	        		</div>
	        	<c:set var = "y" value = "${y + 1 }" />
				</c:forEach>
			</div>
			<p>
			<div class = "form-group">
				<input type = "hidden" value = "<%=vo.getSerial()%>" name = "modifySerial">
				<button type="submit" id='btnModify' class="btn btn-default" >게시글 등록하기</button>
				<button type="button" class="btn btn-default" name = "btnDel" onclick = "">게시글 삭제하기</button> <!-- 삭제기능 서블릿으로 가야함 -->
			</div>
		</form>
	</div>
</body>
</html>