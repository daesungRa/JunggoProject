package junggo.jmember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import junggo.component.DBConnect;

public class JMemberDao {

	// db 커넥션 자원
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	// 파일업로드를 위한 환경설정
	private int size = 1024 * 1024 * 10; // 10mb
	private String encode = "utf-8";
	private String saveDir = "D:/1806_Ra/git/JunggoProject/WebContent/img/jmember/"; // 실제 저장 경로
	private String oriFileName = "";
	private String sysFileName = "";
	
	// 열려있으나 사용하지 않는 모든 자원 해제
	public void closeSet () {
		try {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
			if (conn != null) conn.close();
		} catch (Exception ex) { ex.printStackTrace(); }
	}
	
	public JMemberVo login(JMemberVo vo) {  //로그인
		JMemberVo v = null;
		String sql = "select * from jmember where mid =? and pwd = ? " ;
		
	    try{
	    	conn = new DBConnect().getConn();
			ps = conn.prepareStatement(sql);  //import해줘야함
			ps.setString(1, vo.getMid());
			ps.setString(2, vo.getPwd());
			rs = ps.executeQuery();
			
			if(rs.next()){
				v = new JMemberVo();
				v.setIrum(rs.getString("irum"));
				v.setMid(rs.getString("mid"));
			}

	     }catch(Exception ex){
	    	 ex.printStackTrace();
	     }finally{
	    	 closeSet();
	     }
	    
	    return v;	    
	}
	
	// 입력된 아이디로 아이디 중복체크
	public boolean idCheck (String mid) {
		boolean result = false;
		String sql = "select mid, irum from jmember where mid = ?";
		
		try {
			this.conn = new DBConnect().getConn();	
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, mid);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				result = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeSet();
		}
		
		return result;
	}
	
	// 회원가입
	public boolean insert (HttpServletRequest request) {
		boolean result = false;
		String sql = " insert into jmember (mid, irum, pwd, phone, email, postal, address, addressAdd, photo, photoOri, mDate)"
						+ "	values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate) ";
		
		try {
			// 파일업로드
			MultipartRequest multi = new MultipartRequest(request, saveDir, size, encode, new DefaultFileRenamePolicy());
			Enumeration<String> e = multi.getFileNames();
			while (e.hasMoreElements()) {
				String tagName = (String) e.nextElement();
				oriFileName = multi.getOriginalFileName(tagName);
				sysFileName = multi.getFilesystemName(tagName);
				System.out.println("ori: " + oriFileName);
				System.out.println("sys: " + sysFileName);
			}
			
			// db 로직 처리
			this.conn = new DBConnect().getConn();
			this.conn.setAutoCommit(false);
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, multi.getParameter("mid"));
			ps.setString(2, multi.getParameter("irum"));
			ps.setString(3, multi.getParameter("pwd"));
			ps.setString(4, multi.getParameter("phone"));
			ps.setString(5, multi.getParameter("email"));
			if (multi.getParameter("postal") != null && !multi.getParameter("postal").equals("")) {
				ps.setString(6, multi.getParameter("postal"));
				ps.setString(7, multi.getParameter("address"));
			} else {
				ps.setString(6, multi.getParameter(""));
				ps.setString(7, multi.getParameter(""));
			}
			if (multi.getParameter("addressAdd") != null && !multi.getParameter("addressAdd").equals("")) {
				ps.setString(8, multi.getParameter("addressAdd"));
			} else {
				ps.setString(8, multi.getParameter(""));
			}
			if (sysFileName != null && !sysFileName.equals("")) {
				ps.setString(9, "/junggo/img/jmember/" + sysFileName);
				ps.setString(10, oriFileName);
			} else {
				ps.setString(9, multi.getParameter(""));
				ps.setString(10, multi.getParameter(""));
			}
			int i = ps.executeUpdate();
			
			if (i > 0) {
				result = true;
				this.conn.commit();
			}
		} catch (Exception ex) {
			try {
				this.conn.rollback();
			} catch (Exception ex2) { }
			ex.printStackTrace();
		} finally {
			closeSet();
		}
		
		return result;
	}
	
	// 아이디 찾기
	  public JMemberVo findId(String irum, String receiver) {
		  JMemberVo vo = null;
		 
		  conn = new DBConnect().getConn(); 
		  PreparedStatement ps = null;
		  ResultSet rs = null;

		  try {
			  String sql = "select * from jmember where irum=? and email=? ";

			  ps = conn.prepareStatement(sql);

			  ps.setString(1, irum);
			  ps.setString(2, receiver); 

			  rs = ps.executeQuery();
			  
			  if(rs.next()) {
				vo = new JMemberVo();
	    		   vo.setMid(rs.getString("mid"));
	    		  
			  }
		  
		  } catch (Exception ex) {
			  ex.printStackTrace();
		  } finally {
			  closeSet();
		  }
		  
		  return vo;
	}
	
	// 비번 찾기
	public JMemberVo findPwd(String mid, String irum, String receiver) {
		  JMemberVo vo = null;
		 
		  conn = new DBConnect().getConn(); 
		  PreparedStatement ps = null;
		  ResultSet rs = null;

		  try {
			  String sql = "select * from jmember where mid=? and irum=? and email=? ";

			  ps = conn.prepareStatement(sql);
			  ps.setString(1, mid);
			  ps.setString(2, irum);
			  ps.setString(3, receiver); 

			  rs = ps.executeQuery();
			  
			  if(rs.next()) {
				vo = new JMemberVo();
	    		   vo.setPwd(rs.getString("pwd"));

			  }
		  
		  } catch (Exception ex) {
			  ex.printStackTrace();
		  } finally {
			  closeSet();
		  }
		  
		  return vo;
	}
}
