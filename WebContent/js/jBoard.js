/*
 * 작성자: 이동훈
 * 작성일: 190219
 * 기능: 게시판에 대한 기본 스크립트 모음
 */

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

/* list page */
function movePage(page,category){ //수정 02-20
	$.post('list.bd',
		{
            nowPage : page,
            category: category
		},
		function (data, status){
			$('#content').html(data);
		});
}
function moveInsertPage(){
	$.post('/junggoProject/views/jboard/jboard_insert.jsp',
	function (data, status){
		$('#content').html(data);
	});
}
function bdSearch(category) { //수정 02-20
    var search = $('#tf').val();
	$.post('list.bd',
	{
         category: category, 
         search  : search
	},
	function (data, status){
		$('#content').html(data);
	});
}
function categoryToggle(){
    var val = $('#btnToggle').val();
    if(val == '삽니다'){
        $.post('list.bd',
		{
			category: '1'
		},
		function (data, status){
			$('#content').html(data);
		});
    }else if(val == '팝니다'){
        $.post('list.bd',
		{
			category: '0'
		},
		function (data, status){
			$('#content').html(data);
		});
    }
}

/* view page */
function moveViewPage(serial){
    $.post('view.bd',
	{
          serial  : serial
	},
	function (data, status){
       $('#content').html(data);
           modalContent.setAttribute('style', 'height: 60%; margin: 14% auto;');
		    innerModalContent.setAttribute('style', 'position: absolute; width: 96%; height: 90%; top: -10px;');
				
		    innerModalContent.innerHTML = data;
		    modalWindow.style.display = 'block';
	});
}

/*function backOrNext(param){ //수정요망
	var serial = $('#cd').val();
	if(param == 1){
		if(serial == 1 ){
			alert('처음 글입니다.');
			return;
		}
		location.href='./clone_board_view.jsp?serial='+(--serial);
	}else if(param == 2){
		location.href='./clone_board_view.jsp?serial='+(++serial);
	}
}*/

/* insert page */ 
function check() {
      var frm = document.frm;
      
      var obj = inputData(frm);
      
      if (obj != null) {
         
         obj.focus();
         
         swal({
            title : "실패!",
            text : "제목과 금액을 입력하세요!",
            icon : "warning",
         });         
      } else {
         swal({
              title: "게시판 등록",
              text: "OK를 누르면 등록됩니다.",
              buttons: true,
              dangerMode: true,
              icon : "success",
            })
            .then((willDelete) => {
              if (willDelete) {
                 
                    frm.submit();
              } else {
                 
                   swal("게시물 등록 취소!",{
                   icon: "warning",
                   
                });
              }
            });
      }
   }
    function inputData(ff){
      
      var obj = null;
      
      if(ff.insertSubject.value == '') obj = ff.insertSubject;
      else if(ff.insertPrice.value == '') obj = ff.insertPrice; 
      
      return obj;   
   }
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