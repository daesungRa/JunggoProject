<%@page import="junggo.jmember.JMemberVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:if test="${not empty requestScope.vo }">
		<c:set var="vo" value="${requestScope.vo }" scope='page'></c:set>
	</c:if>

	<div class='container' style='height: 720px;'>
		<div class='container' id='joinForm' style='background-color: #cdcdcd; border-radius: 10px; padding-right: 70px; margin-top: 50px;'>
			<div style='margin: 20px 0 10px 0;'>
				<img class="profile-img" src='/junggo/img/logo/cat_foot_empty.svg' alt="">&nbsp;&nbsp;중고천국<br/>
			</div>
			<h3 id='infoTitle'>회원정보 조회</h3><br/>
			<form class='form' name='joinFrm' id='joinFrm' action='#joinAction' method='post' enctype='multipart/form-data'>
				<div class='form-group'>
					<div class='form-inline'>
						<input class='form-control' type='text' id='mid' name='mid' maxlength='30' value='${vo.mid }' placeholder='아이디 입력 (필수)' readonly="readonly" style='width: 48%; margin-right: 10px;'/>
						<input class='btn btn-primary' type='button' id='btnIdChk' name='btnIdChk' value='중복확인' style='margin-right: 10px; display: none;'></input>
						<input type='hidden' id='midChk' value='unChecked' readonly/>
						<div id='midChkResult' style='color: #0000ff; width: 40%;'></div>
					</div>
				</div>
				<div class='form-group'>
					<input class='form-control' type='password' id='pwd01' name='pwd' maxlength='30' value='' placeholder='비밀번호 입력 (필수)' style='display: none; width: 60%;' />
					<input class='form-control' type='password' id='pwd02' name='pwdChk' maxlength='30' placeholder='비밀번호 확인 (필수)' style='display: none; width: 60%;' />
					<input type='hidden' id='pwdChk' value='unChecked' readonly/>
					<div id='pwdChkResult' style='color: #ff0000;'></div>
				</div>
				<div class='form-group'>
					<input class='form-control' type='text' id='irum' name='irum' value='${vo.irum }' placeholder='이름 입력' readonly="readonly" />
					<input type='hidden' id='irumChk' value='unChecked' readonly/>
					<div id='irumChkResult' style='color: #ff0000;'></div>
				</div>
				<div class='form-group'>
					<input class='form-control' type='email' id='email' name='email' value='${vo.email }' placeholder='이메일 입력' readonly="readonly" />
					<input type='hidden' id='emailChk' value='unChecked' readonly/>
					<div id='emailChkResult' style='color: #ff0000;'></div>
				</div>
				<div class='form-group'>
					<input class='form-control' type='text' id='phone' name='phone' value='${vo.phone }' placeholder="연락처 입력 ('-' 포함, 필수)" readonly="readonly" />
					<input type='hidden' id='phoneChk' value='unChecked' readonly/>
					<div id='phoneChkResult' style='color: #ff0000;'></div>
				</div>
				<div class='form-group'>
					<div class='form-inline'>
						<input class='form-control' type='text' id='postal' name='postal' value='${vo.postal }' placeholder='우편번호' style='width: 30%; margin-right: 10px;' readonly="readonly"/>
						<input class='btn btn-primary' type='button' id='btnPostal' name='btnPostal' value='주소 찾기' style='display: none;'></input>
					</div>
						<input class='form-control' type='text' id='address' name='address' value='${vo.address }' placeholder='주소 입력' readonly="readonly"/>
						<input class='form-control' type='text' id='addressAdd' name='addressAdd' value='${vo.addressAdd }' placeholder='추가 주소 입력' readonly="readonly" />
				</div>
				<div class='form-group'>
					<%
						String dir = "";
						String fileName = "";
						if (((JMemberVo)request.getAttribute("vo")).getPhoto() != null) { // 전달된 vo 객체에 photo 가 존재한다면(없다면 빈 문자열 세팅)
							dir = "D://git/JunggoProject/WebContent/img/jmember/"; // 파일이 저장된 디렉토리
							fileName = ((JMemberVo) request.getAttribute("vo")).getPhoto();
							fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length()); // 파일명만 따오기
						}
					%>
					<script>alert('<%=dir + fileName %>');</script>
					<input type='file' id='photo' name='photo' value='<%=dir + fileName %>' required="required" style='display: none;' /><br/>
				</div>	
				<div class='form-group'>
					<input class='btn btn-primary' type='button' id='btnModifySubmit' name='btnJoinSubmit' value='제 출' style='display: none;' />
					<input class='btn btn-primary' type='button' id='btnModifyCancel' name='btnCancel' value='취 소' style='display: none;' />
				</div>
				<div class='form-group'>
					<input class='btn btn-primary' type='button' id='showModifyPage' name='btnJoinSubmit' value='정보 수정' />
					<input class='btn btn-primary' type='button' id='btnMemberDelete' name='btnCancel' value='회원 탈퇴' />
				</div>
				<%-- <c:choose>
					<c:when test="${not empty requestScope.isJoin and requestScope.isJoin == '1' }">
						<div class='form-group'>
							<input type='file' id='photo' name='photo' value='photo' multiple="multiple" required="required" style='display: none;'/><br/>
						</div>	
						<div class='form-group'>
							<input class='btn btn-primary' type='button' id='btnJoinSubmit' name='btnJoinSubmit' value='제 출' />
							<input class='btn btn-primary' type='button' id='btnCancel' name='btnCancel' value='초기화' />
						</div>
					</c:when>
					<c:otherwise>
						<div class='form-group'>
							<input class='btn btn-primary' type='button' id='showModifyPage' name='btnJoinSubmit' value='정보 수정' />
							<input class='btn btn-primary' type='button' id='btnMemberDelete' name='btnCancel' value='회원 탈퇴' />
						</div>
					</c:otherwise>
				</c:choose> --%>
			</form>
			<p/>
		</div>
		<div class='container' id='memberImg'>
			<c:choose>
				<c:when test="${not empty vo.photo }">
					<img id='image' src='${vo.photo }' width='150px' height='200px' /><br/>
					${vo.photo }<br/>
					<p>이미지 미리보기(${vo.photoOri })</p>
				</c:when>
				<c:otherwise>
					<img id='image' src='https://via.placeholder.com/150x200?text=Your Imgs here' width='150px' height='200px' /><br/>
					<p>등록된 사진이 없습니다</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

</body>
</html>