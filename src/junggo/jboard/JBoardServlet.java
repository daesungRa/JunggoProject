package junggo.jboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		// 각 페이지 요청에 대한 처리
		// jsp 화면 단에 보낼 데이터가 있다면 request 나 session 객체에 setAttribute 해서 처리하면 됨
			// 예시) request.setAttribute("msg", "환영합니다!");
		switch (page) {
		case "list":
			
			break;
		case "insert":
			
			break;
		case "view":
	
			break;
		case "modify":
	
			break;
		case "delete": // 별도처리
			
			break;
		}
		
		// 최종 페이지 조립하고 forward
		RequestDispatcher dispatcher = request.getRequestDispatcher(url + page + ".jsp");
		dispatcher.forward(request, response);
	}

}
