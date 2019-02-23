package junggo.jboard;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junggo.jboard.JBoardDao;
import junggo.jboard.JBoardVo;
import junggo.jmember.JMemberVo;

/**
 * Servlet implementation class boardServlet
 */
@WebServlet("*.bd")
public class JBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public JBoardServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * [게시판에 대한 요청 처리]
		 * - dao 객체를 생성해 vo 를 이용하여 디비 로직 처리 (CRUD)
		 * - JCOMMENT(댓글) 테이블에 대한 로직이나 쿼리도 함께 작성할 것
		 * 		>> 댓글 테이블을 구성하는 컬럼들을 JBoardVo 클래스에 포함시킬지 별도의 JCommentVo 객체를 만들지 결정 필요!
		 * 		>> 서블릿이나 DAO 는 통합해서 처리할 것
		 */
		// 인코딩
		response.setContentType("text/html;charset-utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		// 응답 페이지 요소
		String url = "/views/jboard/jboard_";
		String page = request.getRequestURI();
		page = page.substring(page.lastIndexOf("/") + 1, page.lastIndexOf("."));
		System.out.println("page: " + page); // 요청 페이지 확인
		
		// 사용할 dao, vo, list. 필요에 따라 사용
		JBoardDao dao = new JBoardDao();
		JBoardVo vo = null;
		List<JBoardVo> list = null; // new ArrayList<JBoardVo>();
		PrintWriter out = response.getWriter();
		Boolean b = true;
		
		// 각 페이지 요청에 대한 처리
		// jsp 화면 단에 보낼 데이터가 있다면 request 나 session 객체에 setAttribute 해서 처리하면 됨
			// 예시) request.setAttribute("msg", "환영합니다!");
		switch (page) {
		case "list":
			System.out.println("Search:"+request.getParameter("search"));
			String search = "";
			if(request.getParameter("search") != null) search = request.getParameter("search");
			int nowPage = 1;
			if(request.getParameter("nowPage") != null) nowPage = Integer.parseInt(request.getParameter("nowPage"));
			int category = 0;
			if(request.getParameter("category") != null) category = Integer.parseInt(request.getParameter("category"));
			list = dao.list(search, nowPage, category);
			
			request.setAttribute("list", list);
			request.setAttribute("category", category);
			request.setAttribute("nowBlock", dao.nowBlock);
			request.setAttribute("startPage", dao.startPage);
			request.setAttribute("endPage", dao.endPage);
			request.setAttribute("totBlock", dao.totBlock);
			request.setAttribute("totPage", dao.totPage);
			break;
		case "insert" :
	         dao = new JBoardDao();
	         
	         b = true;
	         try {
	            b = dao.insert(request);
	         } catch (SQLException e) {
	            b = false;
	            e.printStackTrace();
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/junggo/views/jboard/err_page500.jsp");
	    		dispatcher.forward(request, response);
	    		return;
	         }
	         
	         if(b) {
	            out.println("<script>alert('정보가 정상적으로 등록되었습니다.');");
	            out.flush();
	            out.println("location.href='index.jsp'</script>");
	            
	         }else {
	        	 	RequestDispatcher dispatcher = request.getRequestDispatcher("/junggo/views/jboard/err_page500.jsp");
		    		dispatcher.forward(request, response);
		    		return;
	         }
	         
	         return;
		case "view":
			String serials = "";
			JBoardVo vos = null;
			if(request.getParameter("serial")!=null) {
				serials=request.getParameter("serial");
			}
		
			vos=dao.view(serials);
			request.setAttribute("vos", vos);

			list=dao.read(serials);
			request.setAttribute("list", list);
        
		
			break;
		case "insertRep" :
			b=dao.insertRep(request);
			 String result=String.format(
						"	        			<span>%s</span><br/>" + 
						"	        			<textarea>%s</textarea>" + 
						"	        			<output>%s</output><hr/>" , 
						request.getParameter("mid"), request.getParameter("comment"), 
						request.getParameter("cdate"));
			if(b) {
				out.print(result);
				return;
			} 
		
			break;
		case "modify":
			  dao  = new JBoardDao();
		         b = true;
		         try {
		            dao.modify(request);
		         }catch(Exception ee) {
		            b = false;
		            System.out.println(ee.toString());
		            RequestDispatcher dispatcher = request.getRequestDispatcher("/junggo/views/jboard/err_page500.jsp");
		    		dispatcher.forward(request, response);
		    		return;
		         }
		         if(b) {
		            out.print("<script>alert('정보가 수정되었습니다.')</script>");
		            out.flush();
		            
		            System.out.println("Modify OK");
		         }else {
		        	 RequestDispatcher dispatcher = request.getRequestDispatcher("/junggo/views/jboard/err_page500.jsp");
			    	dispatcher.forward(request, response);
			    	return;
		         }
		         return;
		case "delete": 
			String serial = "";
			if(request.getParameter("serial") != null) serial = request.getParameter("serial");
			boolean test = dao.delete(serial);
			if(!test) {
				return;
			}
			break;
		}
		
		// 최종 페이지 조립하고 forward
		RequestDispatcher dispatcher = request.getRequestDispatcher(url + page + ".jsp");
		dispatcher.forward(request, response);
	}

}
