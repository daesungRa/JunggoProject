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
import junggo.component.GetHashedData;

public class JMemberDao {

	// db 커넥션 자원
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	// 파일업로드를 위한 환경설정
	private int size = 1024 * 1024 * 10; // 10mb
	private String encode = "utf-8";
	private String saveDir = "D://git/JunggoProject/WebContent/img/jmember/"; // 실제 저장 경로
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
		JMemberVo v = null; // 반환할 vo 객체
		String saltData = "";
		String hashedPwd = "";
		String sql = " select saltData from jmember_salt where mid = ? " ;
		
	    try{
	    	conn = new DBConnect().getConn();
	    	
	    	// 소금값 얻어온 후 비번 조립
	    	ps = conn.prepareStatement(sql);
	    	ps.setString(1, vo.getMid());
	    	rs = ps.executeQuery();
	    	
	    	if (rs.next()) {
	    		saltData = rs.getString("saltData"); // 소금값 얻어오기
	    		if (!saltData.equals("")) { // 소금값 있다면 해시 비밀번호 생성
	    			hashedPwd = GetHashedData.generateHashedString(saltData + vo.getPwd());
	    			System.out.println("[login] 생성된 소금값, 해시 비번 : " + saltData + ", " + hashedPwd);
	    		}
	    	}
	    	
	    	// 사용완료자원 close 처리
	    	rs.close();
	    	ps.close();
	    	
	    	// 아이디, 비번으로 회원정보 조회
	    	sql = " select * from jmember where mid =? and pwd = ? " ;
			ps = conn.prepareStatement(sql);  //import해줘야함
			ps.setString(1, vo.getMid());
			if (!hashedPwd.equals("")) { // 해시 비밀번호가 존재한다면 그것을, 아니라면 입력받은 비밀번호 투입
				ps.setString(2, hashedPwd);
			} else {
				ps.setString(2, vo.getPwd());
			}			
			rs = ps.executeQuery();
			
			if(rs.next()) { // 회원정보가 존재한다면, 회원명과 아이디가 세팅된 새로운 vo 객체 만들어 그것을 반환한다
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
		String saltData = "";
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
			
			/*
			 * 비밀번호 암호화 처리
			 */
			// 소금코드 생성 후
			// 소금 + 비밀번호 결과를 해싱
			try {
				saltData = GetHashedData.generateRandomString(); // 소금 코드 생성
				hashedPwd = GetHashedData.generateHashedString(saltData + multi.getParameter("pwd")); // 비번에 소금치기
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			// db 로직 처리
			this.conn = new DBConnect().getConn();
			this.conn.setAutoCommit(false);
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, multi.getParameter("mid"));
			ps.setString(2, multi.getParameter("irum"));
			if (!hashedPwd.equals("")) { // 해시코드문자열이 정상적으로 생성되었다면
				System.out.println("[join] saltData, hashedPwd : " + saltData + ", " + hashedPwd);
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
				this.conn.commit(); // jmember 에 저장 성공 시 커밋
				
				ps.close(); // 기존 ps close 하고
				sql = " insert into jmember_salt (mid, saltData)"
						+ "	values (?, ?) ";
				ps = conn.prepareStatement(sql); // 소금값을 해당 아이디와 함께 jmember_salt 테이블에 저장. 추후 로그인시 활용
				ps.setString(1, multi.getParameter("mid"));
				ps.setString(2, saltData);
				i = ps.executeUpdate();
				
				if (i > 0) {
					conn.commit();
				}
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
		String sql = " select saltData from jmember_salt where mid = ? "; // 소금값 얻어오기 쿼리
		String saltData = "";
		String hashedPwd = ""; // 해시처리되면 그것을, 아니면 원래 비번 저장됨
		
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
			
			// db로부터 소금 코드 가져와서 검증할 비번 생성
			ps = conn.prepareStatement(sql);
			ps.setString(1, mid); // 소금코드 얻는 쿼리에 세션으로부터 얻어온 mid 세팅
			rs = ps.executeQuery();
			
			if (rs.next()) {
				saltData = rs.getString("saltData"); // saltData 얻어옴
				hashedPwd = GetHashedData.generateHashedString(saltData + multi.getParameter("pwd")); // 비번에 소금치기
			}
			
			// close 처리
			rs.close();
			ps.close();
			
			// 업데이트를 위한 새로운 쿼리 작성
			sql = " update jmember set irum = ?, phone = ?, email = ?, postal = ?, "
					+ "	address = ?, addressadd = ?, photo = ?, photoOri = ? "
					+ "	where mid = ? and pwd = ? ";
			
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
				ps.setString(7, "/junggo/img/jmember/" + sysFileName);
				ps.setString(8, oriFileName);
			} else {
				ps.setString(7, multi.getParameter(""));
				ps.setString(8, multi.getParameter(""));
			}
			ps.setString(9, mid);
			if (!hashedPwd.equals("")) {
				System.out.println("[modify] saltData, hashedPwd : " + saltData + ", " + hashedPwd);
				ps.setString(10, hashedPwd);
			} else {
				ps.setString(10, multi.getParameter("pwd")); // 해시처리가 안됐다면, 그냥 비번 투입
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
		String sessionMid = (String) request.getSession().getAttribute("mid");
		String inputPwd = (String) request.getParameter("pwd"); // 기본 비번으로 설정, 해싱되면 그것을 투입
		String deleteFileName = "";
		String saltData = "";
		String hashedPwd = "";
		String sql = "";
		
		try {
			this.conn = new DBConnect().getConn();
			
			// 디비에서 파일정보 가져온 후, 계정삭제가 성공하면 실제 경로에서 파일도 삭제한다
			sql = " select photo from jmember where mid = ? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, sessionMid);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				deleteFileName = rs.getString("photo"); // 일단 저장만, 계정삭제가 완료된 후에 파일도 삭제
			}
			
			// close 처리
			rs.close();
			ps.close();
			
			// db로부터 소금 코드 가져와서 검증할 비번 생성
			sql = " select saltData from jmember_salt where mid = ? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, sessionMid); // 소금코드 얻는 쿼리에 세션으로부터 얻어온 sessinMid 세팅
			rs = ps.executeQuery();
			
			if (rs.next()) {
				saltData = rs.getString("saltData"); // saltData 얻어옴
				hashedPwd = GetHashedData.generateHashedString(saltData + inputPwd); // 비번에 소금치기
			}
			
			// close 처리
			rs.close();
			ps.close();
			
			// 삭제를 위해 새로운 쿼리 작성
			sql = " delete from jmember where mid = ? and pwd = ? ";
			conn.setAutoCommit(false);
			
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, sessionMid);
			if (!hashedPwd.equals("")) {
				System.out.println("[삭제]요청아이디, 소금값, 비번: " + sessionMid + ", " + saltData + ", " + hashedPwd);
				ps.setString(2, hashedPwd);
			} else {
				System.out.println("[삭제]요청아이디, 비번: " + sessionMid + ", 비번은 보호, 소금값 없음");
				ps.setString(2, inputPwd);
			}
			int i = ps.executeUpdate();
			
			if (i > 0) {
				result = true;
				conn.commit();
				
				// 삭제 성공 시 파일도 삭제
				// 삭제할 파일경로 조립
				deleteFileName = "D://git/JunggoProject/WebContent/img/jmember/" + deleteFileName.substring(deleteFileName.lastIndexOf("/") + 1, deleteFileName.length());
				System.out.println("삭제할 파일경로: " + deleteFileName);
				File delFile = new File(deleteFileName);
				if (delFile.exists()) { // 존재한다면 삭제
					delFile.delete();
					System.out.println(deleteFileName + " 삭제완료!");
				}
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
