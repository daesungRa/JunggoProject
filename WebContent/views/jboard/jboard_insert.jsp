<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script>
// �̹��� �̸�����
  $(document).ready(function() {
	 /*
	  	document.getElementById("pro-image-1").addEventListener('change', readImage, false);
	 */		
	    $( ".preview-images-zone" ).sortable();
	    
	    /* ���� X��ư Ŭ���� �̺�Ʈ ó�� */
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
			<h2>�Խ��� �Է� ��</h2>
			<hr/>
			<div class = "form-group">
				<label>�Խù� �����</label>
				<output>��<%=new Date().toLocaleString()%>��</output>
		  	</div>
		  	<p>
		  	<label>�Խ��� ī�װ�</label>
	  		<select class = "form-control" name = "insertCategory">
	  			<option value = '1'>�Խ��� (��ϴ�.)</option>
	  			<option value = '0' selected>�Խ��� (�˴ϴ�.)</option>
	  		</select>
		  	<p>
		  	<label>�Ǹ� ����</label>
	  		<select class = "form-control" name = "insertStatus">
	  			<option value = '0' selected>�Ǹ���..!</option>
	  			<option value = '1'>�ǸſϷ�..!</option>
	  			<option value = '2' selected>������..!</option>
	  			<option value = '3'>���ſϷ�..!</option>
	  		</select>
	  		
		  	<p>
		  	<div class="form-group">
		    	<label for="inputId">���̵�</label>
		   		<input type="text" class="form-control" id="insertId" name = "insertId" value = "${sessionScope.mid}" readonly="readonly"> <!-- session.Scopeid -->
		 	</div>
		 	
		  	<div class="form-group">
		    	<label for="insertSubject">�Խù� ����</label>
		    	<input type="text" class="form-control" id="insertSubject" name = "insertSubject" placeholder="������ �Է��ϼ���." required="required">
		  	</div>
		  	
		  	<div class="form-group">
		    	<label for="insertPrice">�Ǹ� �ݾ�</label>
		  		<input type="text" class="form-control" id="insertPrice" name = "insertPrice" onkeyup="insertNumber(this)" required="required">
		  	</div>
		  	
		  	<label>�Խñ� �ۼ�</label>
		  	<textarea id = "insertContent" name = "insertContent" class="form-control" rows="5" placeholder = "������ �Է��ϼ���."></textarea>  
		  	<!-- �ش� �̹��� ���� �̸����� -->
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
				<button type="button" class="btn btn-default" onclick='check()' >����ϱ�</button>
			</div>
		</form>
	</div>
</body>
</html>