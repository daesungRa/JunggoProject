package junggo.jmember;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class memberServlet
 */
@WebServlet("*.mb")
public class JMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public JMemberServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * [회원관리에 대한 요청 처리]
		 * - dao 객체를 생성해 vo 를 이용하여 디비 로직 처리 (CRUD)
		 */
		
		// 인코딩
		response.setContentType("text/html;charset-utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		// 응답 페이지 요소
		String url01 = "/views/jmember/jmember_";
		String url02 = "/views/jmember/";
		String page = request.getRequestURI();
		page = page.substring(page.lastIndexOf("/") + 1, page.lastIndexOf("."));
		System.out.println("page: " + page); // 요청 페이지 확인
		
		// 사용할 dao, vo, result, list 필요에 따라 사용
		JMemberDao dao = new JMemberDao();
		JMemberVo vo = null;
		boolean result = false;
		List<JMemberVo> list = null; // new ArrayList<JBoardVo>();
		
		// 세션, PrintWriter 객체 생성
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		// 각 페이지 요청에 대한 처리
		// jsp 화면 단에 보낼 데이터가 있다면 request 나 session 객체에 setAttribute 해서 처리하면 됨
			// 예시) request.setAttribute("msg", "환영합니다!");
			// 아니면 response 객체로부터 out.print 사용
		switch (page) {
		case "login":
			
			break;
		case "logout":
			
			break;
		case "join":
			result = dao.insert(request);
			
			if (result) {
				System.out.println("회원가입 성공");
				out.print("1");
			} else {
				System.out.println("회원가입 실패");
				out.print("0");
			}
			
			return; // 페이지 이동은 회원가입 결과에 따라 스크립트에서 처리
		case "idCheck": // 별도처리
			System.out.println(request.getParameter("mid"));
			result = dao.idCheck(request.getParameter("mid"));
			
			if (result) {
				out.print("1");
			} else {
				out.print("0");
			}
	
			return;
		case "findId": // url02 로 별도처리
	
			break;
		case "findPwd": // url02 로 별도처리
			
			break;
		case "view":
			
			break;
		case "modify":
			
			break;
		case "delete": // 별도처리
			
			break;
		}
		
		// 최종 페이지 조립하고 forward
		RequestDispatcher dispatcher = request.getRequestDispatcher(url01 + page + ".jsp");
		dispatcher.forward(request, response);
	}

}
