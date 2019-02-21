package junggo.jmember;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;



@WebServlet(urlPatterns ="*.emailId")
public class EmailServletId extends HttpServlet{
	MultipartRequest multi;  
	String upload = "c:/Temp/email/"; //파일 위치
	int size = 1024*1024*100; //받을 파일 사이즈
	String encode = "utf-8";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   doPost(req, resp);
	
	  }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		// 경로가 없으면 새로 만든다
		File path = new File(upload);
		if (!path.exists()) {
			path.mkdirs();
		}
		
		//메일전송에 필요한 변수들
		String sender = "";
		String receiver = "";
		String doc = "";
		String subject = "";
		String attFile = null;
		String mid = "";
		String irum = "";

		multi = new MultipartRequest(req, upload, size, encode, new DefaultFileRenamePolicy());
		
		 resp.setContentType("text/html;charset=utf-8");
		 

		    irum     = multi.getParameter("irum");
		    sender   = multi.getParameter("sender");
			receiver = multi.getParameter("receiver");
			subject  = multi.getParameter("subject");
			doc      = multi.getParameter("doc");

            JMemberDao dao = new JMemberDao();
            JMemberVo vo = dao.findId(irum, receiver);
		    
		    if(vo != null){ 
		    	  Enumeration<String> files = multi.getFileNames();
		          
		          while(files.hasMoreElements()) {
		          	String tag = files.nextElement();
		          	attFile = multi.getFilesystemName(tag);    	
		          }
		          //-----------------------------------
		          //이메일전송을 위한 프로퍼티설정(내용거의안바뀜-외우기)

		          String mailServer = "smtp.naver.com";
		          Properties prop = new Properties();
		          try {
		          	prop.put("mail.smtp.stattls.enable", "true");
		          	prop.put("mail.transport", "smtp");
		          	prop.put("mail.smtp.port", "465");
		          	prop.put("mail.smtp.auth", "true");
		              prop.put("mail.smtp.user", receiver);
		              prop.put("mail.smtp.host", mailServer);
		              prop.put("mail.smtp.ssl.enable", "true"); //ssl보안연결 활성화
		          
		              //기본메시지 설정
		              Session mailSession = Session.getInstance(prop, new MyAuth());
		              Message message = new MimeMessage(mailSession);
		              message.setFrom(new InternetAddress(sender));
		              message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
		              message.setSubject(subject);
		              message.setSentDate(new Date());
		              
		              //문자와 첨부파일을 동시에 전송하기 위한 객체
		              Multipart part = new MimeMultipart();
		              
		              MimeBodyPart text = new MimeBodyPart();
		              text.setContent(vo.getMid(), "text/html;charset=utf-8");
		              part.addBodyPart(text);
		              
		              System.out.println("ser");
		   		   System.out.println(vo.getMid());
		              
		              //첨부파일 처리
		              if(attFile != null) {
		              	MimeBodyPart img = new MimeBodyPart();
		              	FileDataSource fds = new FileDataSource(upload + attFile);
		              	img.setDataHandler(new DataHandler(fds));
		              	img.setFileName(MimeUtility.encodeText(fds.getName()));
		              	part.addBodyPart(img);
		              }
		              message.setContent(part);
		              Transport.send(message);

		          }catch(Exception ex) {
		          	ex.printStackTrace();
		          }finally {
		          	File delFile = new File(upload + attFile);
		          	if(delFile.exists()) delFile.delete();
		          	
		          	String search = "";
		          	String nowPage = "1";
		          	if(multi.getParameter("search")!=null) search = multi.getParameter("search");
		          	if(multi.getParameter("nowPage") != null) nowPage=multi.getParameter("nowPage");
      	
		          	//resp.setStatus(307);
		          	//resp.addHeader("Location", url);

		          	req.setAttribute("search", search);
		          	req.setAttribute("nowPage", nowPage);
		          	HttpSession session = req.getSession();
		          	session.setAttribute("search", search);
		          	session.setAttribute("nowPage", nowPage);
		          }
		          // resp.getWriter().print("<script>alert('회원님의 아이디를 이메일로 전송하였습니다.')</script>");
		          System.out.println("이메일 전송 성공");
		          resp.getWriter().print("1");
		    }else{
		    	// resp.getWriter().print("<script>alert('가입정보가 없습니다.')</script>");
		    	System.out.println("이메일 전송 실패. 가입정보가 없습니다.");
		          resp.getWriter().print("0");
		    }		    
		    // resp.getWriter().print("<script>location.href ='/junggo/index.jsp'</script>");
	}
	
}
