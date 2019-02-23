<%@page import="junggo.jboard.JBoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
JBoardDao dao = new JBoardDao();
boolean test = dao.delete("1");
if(test){
	out.println("<script>");
	out.println("alert('삭제 되었습니다.')");
	out.println("location.href='/junggo/index.jsp'");
	out.println("</script>");
} else {
	out.println("<script>");
	out.println("alert('삭제중 에러.')");
	out.println("location.href='/junggo/index.jsp'");
	out.println("</script>");
}

%>