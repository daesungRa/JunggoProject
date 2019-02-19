package junggo.board;

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

public class BrdDao {
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;
	
	MultipartRequest multi;
	String folder = "C:/Users/JHTA/eclipse-workspace/board/WebContent/";
	int size = 1024*1024*50;
	String encode = "utf-8";
	String sql = "";
	
	public int totSize;			
	public int totPage;			
	public int totBlock;		
	public int nowBlock;		
	public int startNo;			
	public int startPage;		
	public int endNo;			
	public int endPage;			
	
	public int listSize = 9;	
	public int blockSize = 3;   
	public int nowPage; 
	
	public BrdDao() {
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
	public BrdVo view(String serial) {
		if(serial.equals("")) return null;
		
		BrdVo result = new BrdVo();
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
	public List<BrdVo> list(String findStr, int nowPage, int category){
		this.nowPage = nowPage;
		pageCompute(findStr, category);
		
		List<BrdVo> list = new ArrayList<>();
		
		sql = "select * from ( "
				+ "select rownum rno, s.* from ( "
				+ "		select jboard_serial, jboard_subject, jboard_content, jboard_price, jboard_mdate, mid, jboard_pwd, jboard_hit, jboard_rep, jboard_status, att.attfile "
				+ "		from jboard join boardatt att "
				+ "		on jboard_serial = att.pserial "
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
				BrdVo vo = new BrdVo();
				vo.setSerial(rs.getInt("jboard_serial")+"");
				vo.setSubject(rs.getString("jboard_subject"));
				vo.setContent(rs.getString("jboard_content"));
				vo.setPrice(rs.getInt("jboard_price"));
				vo.setMdate(rs.getString("jboard_mdate"));
				vo.setId(rs.getString("mid"));
				vo.setPwd(rs.getString("jboard_pwd"));
				vo.setHit(rs.getInt("jboard_hit"));
				vo.setRep(rs.getInt("jboard_rep"));
				vo.setPhoto(rs.getString("attfile"));
				vo.setStatus(rs.getInt("jboard_status"));
				list.add(vo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public boolean getAttfile(String serial, BrdVo vo) {
		boolean result = true;
		
		try {
			sql = "select attfile from boardatt where pserial= ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(serial));
			rs = ps.executeQuery();
			while(rs.next()) {
				vo.setPhoto(rs.getString("attfile"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	public BrdVo setVo(HttpServletRequest req) {
		BrdVo vo = new BrdVo();
		//첨부 파일 리스트
		List<String> attFiles = new ArrayList<>();
		//삭제 파일 리스트
		List<String> delFiles = new ArrayList<>();
		
		try {
			multi = new MultipartRequest(req, folder, size, encode, new DefaultFileRenamePolicy());
			Enumeration<String> e = multi.getFileNames();
			while(e.hasMoreElements()) {
				String tagName = (String)e.nextElement();
				String att	   = multi.getFilesystemName(tagName);
				if(att == null || att.equals("")) continue;
				attFiles.add(att);
			}
			vo.setAttFiles(attFiles);
			
			if(multi.getParameterValues("delFiles") != null) {
				String[] temp = multi.getParameterValues("delFiles");
				for(String s: temp) {
					delFiles.add(s);
				}
				vo.setDelFiles(delFiles);
			}
			vo.setSerial(multi.getParameter("serial"));
			vo.setSubject(multi.getParameter("subject"));
			vo.setContent(multi.getParameter("content"));
			vo.setMdate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
			vo.setId(multi.getParameter("id"));
			vo.setPwd(multi.getParameter("pwd"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
}
