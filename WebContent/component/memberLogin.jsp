<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src = "/junggo/lib/jquery-3.3.1.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>

<!------ Include the above in your HEAD tag ---------->
<style>
.form-signin
{
    max-width: 330px;
    padding: 15px;
    margin: 0 auto;
}
.form-signin .form-signin-heading, .form-signin .checkbox
{
    margin-bottom: 10px;
}
.form-signin .checkbox
{
    font-weight: normal;
}
.form-signin .form-control
{
    position: relative;
    font-size: 16px;
    height: auto;
    padding: 10px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
.form-signin .form-control:focus
{
    z-index: 2;
}
.form-signin input[type="text"]
{
    margin-bottom: -1px;
    border-bottom-left-radius: 0;
    border-bottom-right-radius: 0;
}
.form-signin input[type="password"]
{
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
}
.account-wall
{
    margin-top: 20px;
    padding: 40px 0px 20px 0px;
    background-color: #f7f7f7;
    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}
.login-title
{
    color: #555;
    font-size: 18px;
    font-weight: 400;
    display: block;
}
.profile-img
{
    width: 96px;
    height: 96px;
    margin: 0 auto 10px;
    display: block;
    -moz-border-radius: 50%;
    -webkit-border-radius: 50%;
    border-radius: 50%;
}
.need-help
{
    margin-top: 10px;
}
.new-account
{
    display: block;
    margin-top: 10px;
}

</style>

<script>
function mbLogin () {
	 function funcLogin(){
		 var frm = document.frm;
		 var mid = frm.mid;
		 var pwd = frm.pwd;
		 
		 if(mid.value =='') {
			 alert("아이디를 입력해주세요");
			 mid.focus();
		 }
		 else if(pwd.value == ''){
			 alert("암호를 입력해주세요");
			 pwd.focus();
		 }
		 else {
			 frm.submit();
		 }
	  
	 }
	 document.frm.pwd.onkeyup = function(e){
		 if(e.keyCode == 13) funcLogin();
	 }
	 document.frm.btn.onclick = function () {
		 funcLogin();
	 }
	}
</script>
</head>
<body>
  <%-- <%
   if(request.getMethod().equals("POST")) { 
    String mid = request.getParameter("mid");
    String pwd = request.getParameter("pwd");
   
    jmemberVo vo = new jmemberVo();
    vo.setMid(mid);
    vo.setPwd(pwd);

    jmemberDao dao = new jmemberDao();
    jmemberVo v = dao.login(vo);
    
    	 if(v != null){
      		 String irum = v.getIrum();
      		 String admin = v.getAdmin();
      		 
    		 session.setAttribute("irum", irum);  
    		 session.setAttribute("admin", admin); 
    		 out.print("<script>location.href ='/junggo/index.jsp'</script>");
    		 
    	 }else{
    		 out.print("<script>alert('아이디나 암호를 확인해주세요.')</script>");
    	 }     
   }
  %> --%>

<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">LOGIN</h1>
            <div class="account-wall">
                <img class="profile-img" src='/junggo/img/logo/cat_foot_empty.svg' alt="">
                <form class="form-signin"  name='frm' method='post' >
		               <input type="text" class="form-control" name ='mid' placeholder="ID" required autofocus autocomplete='off'>
		               <input type="password" class="form-control" name ='pwd' placeholder="Password" required>
		               <input type="button" class="btn btn-lg btn-primary btn-block" id='btn' value='Sign in'>
		 
		               <a href="/junggo/jmember/memberForget.jsp" class="pull-right forget" >아이디 찾기</a><span class="clearfix"></span>
		               <a href="/junggo/jmember/memberForget.jsp" class="pull-right forget" >Forget Password?</a><span class="clearfix"></span>
                </form>
            </div>
            <a href="#" class="text-center new-account">Create an account </a>
        </div>
    </div>
</div>

<script>mbLogin(); </script>
</body>
</html>