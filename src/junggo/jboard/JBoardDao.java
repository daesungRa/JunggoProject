package junggo.jboard;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import junggo.component.DBConnect;

public class JBoardDao {
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;
	
	MultipartRequest multi;
	String folder = "C://Users/JHTA/eclipse-workspace/junggo/WebContent/img/jboard/";
	
	int size = 1024*1024*50;
	String encode = "utf-8";
	String sql = "";
	JBoardVo vo = null;
	
	public int totSize;			
	public int totPage;			
	public int totBlock;		
	public int nowBlock;		
	public int startNo;			
	public int startPage;		
	public int endNo;			
	public int endPage;			
	
	public int listSize = 3;	
	public int blockSize = 2;   
	public int nowPage; 
	
	/*
	 * 기존 작성한 dao, vo 그대로 뒀음. 수정이 필요하다면 수정
	 */
	public JBoardDao() {
		conn = new DBConnect().getConn();
		ps = null;
		rs = null;
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public JBoardVo find(String serial) {
		if(serial.equals("")) {
			System.out.println("serial 전달에러");
			return null;
		}
		JBoardVo result = new JBoardVo();
		try {
			sql = "select * from jboard where jboard_serial = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(serial));
			rs = ps.executeQuery();	
			if(rs.next()) {
				result.setSubject(rs.getString("jboard_subject"));
				result.setContent(rs.getString("jboard_content"));
				result.setPrice(rs.getInt("jboard_price"));
			}
			sql = "select * from jboardatt where jboardatt_pserial = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(serial));
			rs = ps.executeQuery();
			while(rs.next()) {
				result.getAttFiles().add(rs.getString("jboardatt_sysfilename"));
			}
			System.out.println("로드완료");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public List<String> modifyImage(int Serial) {

	      List<String> img = new ArrayList<String>();

	      sql = "select * from JBoardAtt where JBoardAtt_pSerial = ? ";

	      try {

	         ps = conn.prepareStatement(sql);
	         ps.setInt(1, Serial);

	         rs = ps.executeQuery();

	         while (rs.next()) {
	            img.add(rs.getString("JBoardAtt_sysFileName"));
	         }

	      } catch (Exception ex) {
	         ex.printStackTrace();
	      }

	      return img;

	}
	public String modify(HttpServletRequest req) throws SQLException {
	      String msg = "";
	      try {
	         JBoardAtt attVo = setVo(req);

	         JBoardVo vo = new JBoardVo();

	         String sql = "update JBoard set JBoard_Subject = ?, JBoard_Content = ?, JBoard_Price = ?, JBoard_Category = ?, JBoard_Status = ? where JBoard_Serial = ? ";

	         ps = conn.prepareStatement(sql);
	         ps.setString(1, multi.getParameter("modifySubject"));
	         ps.setString(2, multi.getParameter("modifyContent"));
	         ps.setInt(3, Integer.parseInt(multi.getParameter("modifyPrice")));
	         ps.setInt(4,Integer.parseInt(multi.getParameter("modifyCategory")));
	         ps.setInt(5, Integer.parseInt(multi.getParameter("modifyStatus")));
	         ps.setInt(6, Integer.parseInt("41"));

	         int cnt = ps.executeUpdate();

	         if (cnt > 0) {
	            boolean b = modifyFile(attVo);

	            if (b) {

	               // delFiles 리스트에 추가된 이미지파일 삭제

	               if (attVo.getDelFiles() != null) {
	                  for (String d : attVo.getDelFiles()) {
	                     sql = "delete from JBoardAtt where JBoardAtt_sysfilename = ?";
	                     ps = conn.prepareStatement(sql);
	                     
	                     ps.setString(1, d);
	                     System.out.println(d + " : delFiles에 체크된 파일이름");
	                     ps.executeQuery();
	                     
	                     File f = new File(folder + d);
	                     
	                     if (f.exists())
	                        
	                     System.out.println(f + "경로상의 삭제 될 파일 이름");
	                        f.delete();
	                  }
	               }
	               msg = "delFile 삭제 OK (경로상의 파일) ";
	               conn.commit();
	            } else {
	               msg = "delFile 삭제 오류 발생";
	               throw new Exception();
	            }
	         }
	      } catch (Exception ex) {
	         conn.rollback();
	         ex.printStackTrace();
	      }
	      return msg;
	   }
	public boolean modifyFile(JBoardAtt attVo) throws SQLException {
	      boolean b = true;
	      int cnt = 0;
	      for (int i = 0; i < attVo.getSysFiles().size(); i++) {

	         String sql = "insert into JBoardAtt (JBoardAtt_Serial, JBoardAtt_pSerial ,JBoardAtt_SYSfileName, JBoardAtt_ORIfileName) "
	               + "   values (seq_JboardAtt.nextVal ,?, ?, ?) ";

	         ps = conn.prepareStatement(sql);

	         ps.setInt(1, Integer.parseInt("41"));
	         ps.setString(2, attVo.getSysFiles().get(i));
	         ps.setString(3, attVo.getOriFiles().get(i));
	         

	         cnt = ps.executeUpdate();

	         if (cnt < 1) {
	            b = false;
	            System.out.println("modifyFile 수정 오류 ");
	            break;
	         }
	      }
	      return b;
	}
	public boolean insert(HttpServletRequest req) throws IOException, SQLException {
	      boolean b = true;
	      JBoardAtt attVo = new JBoardAtt();

	      try {

	         attVo = setVo(req);

	         String sql = "insert into JBoard (JBoard_Serial, JBoard_subject, Jboard_Content, JBoard_Price, JBoard_Category, mId , JBoard_Hit, JBoard_Rep, JBoard_Status, JBoard_bDate ) "
	               + " values (seq_JBoard.nextVal , ? , ?, ?, ? ,? , 0, 0, ?, sysdate) ";

	         ps = conn.prepareStatement(sql);

	         ps.setString(1, multi.getParameter("insertSubject"));
	         ps.setString(2, multi.getParameter("insertContent"));
	         ps.setInt(3, Integer.parseInt(multi.getParameter("insertPrice")));
	         ps.setInt(4, Integer.parseInt(multi.getParameter("insertCategory")));
	         ps.setString(5, multi.getParameter("insertId"));
	         ps.setInt(6, Integer.parseInt(multi.getParameter("insertStatus")));

	         int cnt = ps.executeUpdate();

	         if (cnt < 0) {
	            System.out.println("정보 등록 중 오류 발생");
	            b = false;
	            conn.rollback();

	         } else {
	            System.out.println("정보 등록 까지 진행");
	            b = insertFile(attVo);

	            if (b) {
	               System.out.println("insertFile에서 넘어온 B값 = true;");
	               conn.commit();
	            } else {
	               throw new Exception();
	            }
	         }

	         System.out.println("게시물 정보 등록 OK ");
	         conn.commit();

	      } catch (Exception ex) {
	         b = false;
	         System.out.println("insert 마지막 부분 오류");
	         ex.printStackTrace();
	         for(String s : attVo.getSysFiles()) {
	            File f = new File(folder + s);
	            if(f.exists()) f.delete();
	            
	         }
	         System.out.println(ex.toString());
	      }
	      return b;
	}
	   public boolean insertFile(JBoardAtt attVo) throws SQLException {
		      boolean b = true;
		      int cnt = 0;

		      for (int i = 0; i < attVo.getSysFiles().size(); i++) {
		         String sql = "insert into JBoardAtt (JBoardAtt_Serial, JBoardAtt_pSerial,JBoardAtt_SYSfileName, JBoardAtt_ORIfileName) "
		               + "   values (seq_JboardAtt.nextVal ,seq_Jboard.currval, ?, ?) ";

		         ps = conn.prepareStatement(sql);
		         ps.setString(1, attVo.getSysFiles().get(i));
		         ps.setString(2, attVo.getOriFiles().get(i));
		         
		         cnt = ps.executeUpdate();

		         if (cnt < 1) {
		            b = false;
		            System.out.println("insertFile false");
		            break;
		         } else {
		            System.out.println("insertFile 까지 데이터 넘어감");
		         }
		      }
		      return b;
		   }
	public boolean delete(String serial) {
		boolean result = false;
		try {
			sql = "delete from jboard where jboard_serial = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(serial));
			int cnt = ps.executeUpdate();
			if(cnt > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
			sql = "select jboardatt_sysfilename from jboardatt where jboardatt_pserial= ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(serial));
			rs = ps.executeQuery();
			while(rs.next()) {
				File file = new File(folder+rs.getString("jboardatt_sysfilename"));
				if(file.exists()) {
					file.delete();
				}
			}
			sql = "delete from jboardatt where jboard_pserial = ?";
			ps.setInt(1, Integer.parseInt(serial));
			cnt = ps.executeUpdate();
			if(cnt > 0) {
				result = true;
				conn.commit();
			} else {
				conn.rollback();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return result;
	}
	public JBoardVo view(String serial) {
		if(serial.equals("")) return null;
		
		JBoardVo result = new JBoardVo();
		try {
			sql = "update jboard set jboard_hit = jboard_hit + 1 where jboard_serial = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(serial));
			int cnt = ps.executeUpdate();
			if(cnt > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
			sql = "select * from jboard where jboard_serial = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(serial));
			rs = ps.executeQuery();
			if(rs.next()) {
				result.setSerial(serial);
				result.setSubject(rs.getString("jboard_subject"));
				result.setContent(rs.getString("jboard_content"));
				result.setPrice(rs.getInt("jboard_price"));
				result.setMdate(rs.getString("jboard_mdate"));
				result.setId(rs.getString("mid"));
				result.setPwd(rs.getString("jboard_pwd"));
				result.setHit(rs.getInt("jboard_hit"));
				result.setRep(rs.getInt("jboard_rep"));
				result.setStatus(rs.getInt("jboard_status"));
				getAttfile(serial, result);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public void pageCompute(String findStr, int category) {
		sql = "select count(*) cnt from jboard "
			+ "where jboard_category = ? and (jboard_subject like ? or jboard_content like ?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, category);
			ps.setString(2, "%"+findStr+"%");
			ps.setString(3, "%"+findStr+"%");
			rs = ps.executeQuery();
			if(rs.next()) {
				totSize = rs.getInt("cnt");
				totPage = (int)Math.ceil(totSize / (double)listSize);
				totBlock = (int)Math.ceil(totPage / (double)blockSize);
				nowBlock = (int)Math.ceil(nowPage / (double)blockSize);
				endNo = nowPage * listSize;
				startNo = endNo - listSize + 1;
				if(endPage > totPage) endNo = totSize;
				
				endPage = nowBlock * blockSize;
				startPage = endPage - blockSize + 1;
				if(endPage > totPage) endPage = totPage;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public List<JBoardVo> list(String findStr, int nowPage, int category){
		this.nowPage = nowPage;
		pageCompute(findStr, category);
		
		List<JBoardVo> list = new ArrayList<>();
		
		sql = "select * from ( "
				+ "select rownum rno, s.* from ( "
				+ "		select jboard_serial, jboard_subject, jboard_content, jboard_price, jboard_bdate, mid, jboard_pwd, jboard_hit, jboard_rep, jboard_status, att.jboardatt_sysfilename "
				+ "		from jboard join jboardatt att "
				+ "		on jboard_serial = att.jboardatt_pserial "
				+ "		where jboard_category = ? and (jboard_subject like ? or jboard_content like ?)"
				+ "		order by jboard_serial desc)s "
				+ ") where rno between ? and ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, category);
			ps.setString(2, "%"+findStr+"%");
			ps.setString(3, "%"+findStr+"%"); 
			ps.setInt(4, startNo); //1
			ps.setInt(5, endNo);   //9
			rs = ps.executeQuery();
			while(rs.next()) { //?
				JBoardVo vo = new JBoardVo();
				vo.setSerial(rs.getInt("jboard_serial")+"");
				vo.setSubject(rs.getString("jboard_subject"));
				vo.setContent(rs.getString("jboard_content"));
				vo.setPrice(rs.getInt("jboard_price"));
				vo.setMdate(rs.getString("jboard_bdate"));
				vo.setId(rs.getString("mid"));
				vo.setPwd(rs.getString("jboard_pwd"));
				vo.setHit(rs.getInt("jboard_hit"));
				vo.setRep(rs.getInt("jboard_rep"));
				vo.setPhoto(rs.getString("jboardatt_sysfilename"));
				vo.setStatus(rs.getInt("jboard_status"));
				list.add(vo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public boolean getAttfile(String serial, JBoardVo vo) {
		boolean result = true;
		
		try {
			sql = "select jboardatt_systemfilename from jboardatt where jboardatt_pserial= ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(serial));
			rs = ps.executeQuery();
			while(rs.next()) {
				vo.setPhoto(rs.getString("jboardatt_systemfilename"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	public JBoardAtt setVo(HttpServletRequest req) throws Exception {

	      JBoardAtt attVo = new JBoardAtt();

	      List<String> attFiles = new ArrayList<>();
	      List<String> oriFiles = new ArrayList<>();
	      List<String> delFiles = new ArrayList<>();

	      multi = new MultipartRequest(req, folder, size, encode, new DefaultFileRenamePolicy());

	      Enumeration<String> e = multi.getFileNames();
	      while (e.hasMoreElements()) {
	         String tagName = (String) e.nextElement();
	         String att = multi.getFilesystemName(tagName);
	         String ori = multi.getOriginalFileName(tagName);
	         if (att == null || att.equals(""))
	            continue;
	         attFiles.add(att);
	         oriFiles.add(ori);
	      }
	      attVo.setSysFiles(attFiles);
	      attVo.setOriFiles(oriFiles);

	      if (multi.getParameterValues("delFiles") != null) {
	         String[] temp = multi.getParameterValues("delFiles");
	         for (String s : temp) {
	            delFiles.add(s);
	         }
	         attVo.setDelFiles(delFiles);
	      }
	      return attVo;
	   }
	public List<JBoardVo>read(String jno) {

		List<JBoardVo>list=new ArrayList<>();
		JBoardVo vo=null;
		try {
			sql=" select * from jcomment "
					+ " where jcomment_pserial = ? order by jcomment_cdate desc ";
					
			ps=conn.prepareStatement(sql);
			ps.setString(1, jno);
			rs=ps.executeQuery();

			while(rs.next()) {
				vo=new JBoardVo();
				vo.setRserial(rs.getString("jcomment_serial"));
				vo.setMid(rs.getString("jcomment_mid"));
				vo.setComment(rs.getString("jcomment_comment"));
				vo.setCdate(rs.getString("jcomment_cdate"));
				vo.setPserial(jno);
				
				list.add(vo);
			//	System.out.println(list.get(0).comment);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			return list;
		}
	}
	public boolean insertRep(HttpServletRequest req) {
		boolean b=true;
		JBoardVo vo=new JBoardVo();
		
		vo.setMid(req.getParameter("mid"));
		vo.setComment(req.getParameter("comment"));
		vo.setPserial(req.getParameter("jserial"));
		
		System.out.println(vo.getMid());
		sql=" insert into jcomment (jcomment_serial, jcomment_mid, jcomment_comment,"
				+ " jcomment_cdate, jcomment_pserial ) "
				+ " values(seq_jcomment.nextval, ?, ?, sysdate, ?) ";
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getMid());
			ps.setString(2, vo.getComment());
			ps.setInt(3, Integer.parseInt(vo.getPserial()));
			
			
			int cnt=ps.executeUpdate();
			if(cnt<1) {
				b=false;
				return b;
			}else {
			System.out.println("insertRep ok");
			conn.commit();
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}
}
