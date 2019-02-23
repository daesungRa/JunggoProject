package junggo.jboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class JBoardVo {
	/*
	 * 기존 작성한 dao, vo 그대로 뒀음. 수정이 필요하다면 수정
	 * JCOMMENT 댓글 테이블에 대한 처리를 어떻게 할지 결정 필요
	 */
	
	String serial;
	String subject;
	String content;
	String mdate;
	String id;
	String pwd;
	int hit;
	int rep;
	int price;
	String photo;
	int category;
	//0: 판매중, 1: 판매완료, 2:구매중, 3: 구매완료
	int status;
	List<String> attFiles = new ArrayList<>();
	List<String> delFiles;
	
	//comment
	String rserial;
	String mid;
	String comment;
	String cdate;
	String pserial;
	
	public JBoardVo() {}
	
	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getSubject2() {
		return this.subject;
	}
	public String getSubject() {
		String result = "";
		if(this.status == 0) {
			result += "[판매중]";
		} else if(this.status == 1) {
			result += "[판매완료]";
		} else if(this.status == 2 ) {
			result += "[구매중]";
		} else if(this.status == 3) {
			result += "[구매완료]";
		}
		return result+this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMdate() {
		return mdate;
	}

	public void setMdate(String mdate) {
		this.mdate = mdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getRep() {
		return rep;
	}

	public void setRep(int rep) {
		this.rep = rep;
	}

	public List<String> getAttFiles() {
		return attFiles;
	}

	public void setAttFiles(List<String> attFiles) {
		this.attFiles = attFiles;
	}

	public List<String> getDelFiles() {
		return delFiles;
	}

	public void setDelFiles(List<String> delFiles) {
		this.delFiles = delFiles;
	}
	public int getPrice2() {
		return this.price;
	}
	public String getPrice() {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(this.price)+"원";
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public String getPhoto() {
		if(this.status == 1) {
			this.photo = "판매완료.jpg";
		}else if(this.status == 3) {
			this.photo = "거래완료.jpg";
		}
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getStatus2() {
		return this.status;
	}
	public String getStatus() {
		String result = "";
		switch(this.status) {
		case 0:
			result = "판매중";
			break;
		case 1:
			result = "판매완료";
			break;
		case 2:
			result = "구매중";
			break;
		case 3:
			result = "구매완료";
			break;
		default:
			break;
		}
		return result;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getRserial() {
		return rserial;
	}

	public void setRserial(String rserial) {
		this.rserial = rserial;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getPserial() {
		return pserial;
	}

	public void setPserial(String pserial) {
		this.pserial = pserial;
	}
	
	
	
}
