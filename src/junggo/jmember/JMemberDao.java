package junggo.jmember;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import junggo.component.DBConnect;
import junggo.component.GetHash;

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
		String hashedPwd = ""; // 해시처리되면 그것을, 아니면 원래 비번 저장됨
		
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
			
			// 비밀번호 해싱 to 문자열
			try {
				GetHash getHash = new GetHash();
				hashedPwd = getHash.getHash(multi.getParameter("pwd"));
			} catch (NoSuchAlgorithmException nae) {
				nae.printStackTrace();
			}
			
			// db 로직 처리
			this.conn = new DBConnect().getConn();
			this.conn.setAutoCommit(false);
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, multi.getParameter("mid"));
			ps.setString(2, multi.getParameter("irum"));
			if (!hashedPwd.equals("")) {
				ps.setString(3, hashedPwd);				
			} else {
				ps.setString(3, multi.getParameter("pwd")); // 해시처리가 안됐다면, 그냥 비번 투입
			}
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
	
	// 회원정보 수정
	public boolean modify (HttpServletRequest request) {
		boolean result = false;
		String sql = " update jmember set irum = ?, phone = ?, email = ?, postal = ?, "
						+ "	address = ?, addressadd = ?, photo = ?, photoOri = ? "
						+ "	where mid = ? and pwd = ? ";
		
		try {
			this.conn = new DBConnect().getConn();
			String mid = (String) request.getSession().getAttribute("mid"); // 디비 로직 처리를 위한 세션 아이디 get
			
			// 파일업로드
			MultipartRequest multi = new MultipartRequest(request, saveDir, size, encode, new DefaultFileRenamePolicy());
			Enumeration<String> e = multi.getFileNames();
			while (e.hasMoreElements()) {
				// 새로 업로드된 파일이 있다면,
				// db 로부터 기존 파일명을 가져와 실제 디렉토리에서 삭제한 후 새로운 것으로 다시 저장한다
				String sqlGetSysFileName = " select photo from jmember where mid = ? ";
				String delFileName = ""; // 실제 디렉토리에서 삭제할 파일경로
				ps = this.conn.prepareStatement(sqlGetSysFileName);
				System.out.println("세션아이디: " + mid);
				ps.setString(1, mid);
				rs = ps.executeQuery();
				if (rs.next()) {
					// photo 경로를 얻어와 실제 드라이브 저장경로 조립
					delFileName = rs.getString("photo");
					delFileName = saveDir + delFileName.substring(delFileName.lastIndexOf("/") + 1, delFileName.length());
					// 해당 경로에 존재하는 파일 삭제
					File delFilePath = new File(delFileName);
					if (delFilePath.exists()) { // 존재한다면 삭제
						delFilePath.delete();
					} else {
						System.out.println("해당 파일이 존재하지 않습니다.");
					}
				}
				// 사용 후 자원해제
				rs.close();
				ps.close();
				
				// 새롭게 요청받은 파일정보를 얻어온다
				String tagName = (String) e.nextElement();
				oriFileName = multi.getOriginalFileName(tagName);
				sysFileName = multi.getFilesystemName(tagName);
				System.out.println("[수정]ori: " + oriFileName);
				System.out.println("[수정]sys: " + sysFileName);
			}
			
			// db 로직 처리
			this.conn.setAutoCommit(false);
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, multi.getParameter("irum"));
			ps.setString(2, multi.getParameter("phone"));
			ps.setString(3, multi.getParameter("email"));
			if (multi.getParameter("postal") != null && !multi.getParameter("postal").equals("")) {
				ps.setString(4, multi.getParameter("postal"));
				ps.setString(5, multi.getParameter("address"));
			} else {
				ps.setString(4, multi.getParameter(""));
				ps.setString(5, multi.getParameter(""));
			}
			if (multi.getParameter("addressAdd") != null && !multi.getParameter("addressAdd").equals("")) {
				ps.setString(6, multi.getParameter("addressAdd"));
			} else {
				ps.setString(6, multi.getParameter(""));
			}
			if (sysFileName != null && !sysFileName.equals("")) {
				System.out.println("사진 저장!");
				ps.setString(7, multi.getParameter("photo"));
				ps.setString(8, multi.getParameter("photoOri"));
			} else {
				ps.setString(7, multi.getParameter(""));
				ps.setString(8, multi.getParameter(""));
			}
			ps.setString(9, mid);
			ps.setString(10, multi.getParameter("pwd"));
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
	
	// 회원정보 조회
	public JMemberVo select (String mid) {
		JMemberVo vo = null;
		String sql = "select mid, irum, phone, email, postal, address, addressAdd, photo, photoOri, to_char(mDate, 'YY.MM.DD/HH24:MI:SS') mDate from jmember where mid = ?";
		
		try {
			this.conn = new DBConnect().getConn();	
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, mid);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				vo = new JMemberVo();
				vo.setMid(rs.getString("mid"));
				vo.setIrum(rs.getString("irum"));
				vo.setPhone(rs.getString("phone"));
				vo.setEmail(rs.getString("email"));
				vo.setPostal(rs.getString("postal"));
				vo.setAddress(rs.getString("address"));
				vo.setAddressAdd(rs.getString("addressAdd"));
				vo.setPhoto(rs.getString("photo"));
				vo.setPhotoOri(rs.getString("photoOri"));
				vo.setmDate(rs.getString("mDate"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeSet();
		}
		
		return vo;
	}
	
	// 회원정보 삭제
	public boolean delete (HttpServletRequest request) {
		boolean result = false;
		String sql = " delete from jmember where mid = ? and pwd = ? ";
		String sessionMid = (String) request.getSession().getAttribute("mid");
		String inputPwd = (String) request.getParameter("pwd");
		System.out.println("[삭제]요청아이디, 비번: " + sessionMid + ", " + inputPwd);
		
		try {
			this.conn = new DBConnect().getConn();
			conn.setAutoCommit(false);
			
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, sessionMid);
			ps.setString(2, inputPwd);
			int i = ps.executeUpdate();
			
			if (i > 0) {
				result = true;
				conn.commit();
			}
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (Exception ex2) { ex2.printStackTrace(); }
			ex.printStackTrace();
		} finally {
			closeSet();
		}
		
		return result;
	}
}
