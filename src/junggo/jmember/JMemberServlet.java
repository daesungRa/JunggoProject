package junggo.jmember;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junggo.component.GetHash;

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
		RequestDispatcher dispatcher = null;
		
		// 각 페이지 요청에 대한 처리
		// jsp 화면 단에 보낼 데이터가 있다면 request 나 session 객체에 setAttribute 해서 처리하면 됨
			// 예시) request.setAttribute("msg", "환영합니다!");
			// 아니면 response 객체로부터 out.print 사용
		switch (page) {
		case "login":
			//request 객체 << form 정보들이 담겨
			String mid = request.getParameter("mid");
		    String hashedPwd = request.getParameter("pwd"); // 그냥 비번 투입했다가 해시처리 후 해시결과 투입
		    
		    // 비밀번호 해싱 to 문자열
 			try {
 				hashedPwd = GetHash.getHash(request.getParameter("pwd"));
 			} catch (NoSuchAlgorithmException nae) {
 				nae.printStackTrace();
 			}
 			
 			// 결과 출력
		    System.out.println("id: " + mid);
		    System.out.println("[login] hashedPwd : " + hashedPwd);

		    vo = new JMemberVo();
		    vo.setMid(mid);
		    vo.setPwd(hashedPwd);
		    
		    JMemberVo v = dao.login(vo); // 뷰로 반환할 vo 객체
		    
		    if (v != null) {
		    	session.setAttribute("mid", v.getMid());
		    	session.setAttribute("irum", v.getIrum());
		    	out.print("1");
		    } else {
		    	// 1. index 페이지에서 로그인이 실패되었다는 사실을 인지할 수 있도록 처리
		    	//		> 인지가 되었다면, 로그인 모달 창을 자동으로 띄우도록 처리
		    	// 2. 서블릿 요청을 두번한다
		    	//		> 첫번째로 아이디체크
		    	request.setAttribute("message", "아이디나 암호를 확인해주세요.");
		    	out.print("0");
		    }
			return;
		case "logout":
			session.setAttribute("mid", null);
			session.setAttribute("irum", null);
			session.invalidate();
			
			page = "index";
			dispatcher = request.getRequestDispatcher(page + ".jsp");
			dispatcher.forward(request, response);
			return;
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
		case "view":
			// 조회성공시 페이지 로드, 세션아이디가 없으면 에러코드 0, vo 가 null 이면 에러코드 1
			System.out.println("세션 등록 아이디: " + (String)session.getAttribute("mid"));
			if ((String)session.getAttribute("mid") != null && !((String)session.getAttribute("mid")).equals("")) {
				vo = dao.select((String)session.getAttribute("mid"));
				if (vo != null) { // 조회된 정보가 있다면 request 객체에 set 한다
					request.setAttribute("vo", vo);
				} else {
					out.print("1");
					return;
				}
			} else {
				out.print("0");
				return;
			}
			
			break;
		case "modify":
			result = dao.modify(request);
			
			if (result) {
				System.out.println("회원정보 수정 성공");
				out.print("1");
			} else {
				System.out.println("회원정보 수정 실패");
				out.print("0");
			}
			
			return; // 페이지 이동은 회원가입 결과에 따라 스크립트에서 처리
		case "delete": // 별도처리
			result = dao.delete(request);
			
			if (result) {
				System.out.println("회원정보 삭제 성공");
				session.invalidate();
				out.print("1");
			} else {
				System.out.println("회원정보 삭제 실패");
				out.print("0");
			}
			
			return;
		}
		
		// 최종 페이지 조립하고 forward
		System.out.println("서블릿에서 최종 요청 페이지: " + url01 + page + ".jsp");
		dispatcher = request.getRequestDispatcher(url01 + page + ".jsp");
		dispatcher.forward(request, response);
	}

}