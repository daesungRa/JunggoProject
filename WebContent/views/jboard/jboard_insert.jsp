<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
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


  	var num = 2;
	var ber = 1;
	
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
	                            	'<div class="image-cancel" data-no="' + ber + '">X</div>' +
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
</head>
<body>

	<div class = "container">
		<form id = "frm" name = "frm" method = "post" enctype = "multipart/form-data" action = "/junggo/insert.bd">
			<hr>
			<h2>게시판 입력 폼</h2>
			<hr/>
			<div class = "form-group">
				<label>게시물 등록일</label>
				<output>◆<%=new Date().toLocaleString()%>◆</output>
		  	</div>
		  	<p>
		  	<label>게시판 카테고리</label>
	  		<select class = "form-control" name = "insertCategory">
	  			<option value = '1'>게시판 (삽니다.)</option>
	  			<option value = '0' selected>게시판 (팝니다.)</option>
	  		</select>
		  	<p>
		  	<label>판매 상태</label>
	  		<select class = "form-control" name = "insertStatus">
	  			<option value = '0' selected>판매중..!</option>
	  			<option value = '1'>판매완료..!</option>
	  			<option value = '2' selected>구매중..!</option>
	  			<option value = '3'>구매완료..!</option>
	  		</select>
	  		
		  	<p>
		  	<div class="form-group">
		    	<label for="inputId">아이디</label>
		   		<input type="text" class="form-control" id="insertId" name = "insertId" value = "${sessionScope.mid}" readonly="readonly"> <!-- session.Scopeid -->
		 	</div>
		 	
		  	<div class="form-group">
		    	<label for="insertSubject">게시물 제목</label>
		    	<input type="text" class="form-control" id="insertSubject" name = "insertSubject" placeholder="제목을 입력하세요." required="required">
		  	</div>
		  	
		  	<div class="form-group">
		    	<label for="insertPrice">판매 금액</label>
		  		<input type="text" class="form-control" id="insertPrice" name = "insertPrice" onkeyup="insertNumber(this)" required="required">
		  	</div>
		  	
		  	<label>게시글 작성</label>
		  	<textarea id = "insertContent" name = "insertContent" class="form-control" rows="5" placeholder = "내용을 입력하세요."></textarea>  
		  	<!-- 해당 이미지 파일 미리보기 -->
			<p>
			
			<div class="form-group">
				<div class = "file-zone">
					<div class = "file-list-1">
						<input type= 'file' onchange = "readImage()" name = "pro-image-1" id = 'pro-image-1' data-no = '1' style = "color : #fff">
					</div>
				</div>	
			</div>
			<p>	
			<div class="preview-images-zone">	
			</div>
			<p>
			<div class = "form-group">
				<button type="button" class="btn btn-default" onclick='check()' >등록하기</button>
			</div>
		</form>
	</div>
</body>
</html>