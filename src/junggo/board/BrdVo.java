package junggo.board;

import java.text.DecimalFormat;
import java.util.List;

public class BrdVo {
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
	//0: 판매중, 1: 판매완료, 2:구매중, 3: 구매완료
	int status;
	List<String> attFiles;
	List<String> delFiles;
	
	public String toJSON() {
		return String.format("{"
				+ "'brd_serial'  : '%s', "
				+ "'brd_subject' : '%s', "
				+ "'brd_content' : '%s', "
				+ "'brd_mdate'   : '%s', "
				+ "'brd_id       : '%s', "
				+ "'brd_pwd      : '%s', "
				+ "'brd_hit      : '%s', "
				+ "'brd_rep      : '%s', "
				+ "'brd_price    : '%s', "
				+ "'brd_photo    : '%s'}", 
				serial, subject, content, mdate, id, pwd, hit, rep, price, photo).replaceAll("\'", "\"");
	}
	
	public BrdVo() {}
	
	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
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
		return content.substring(0, content.indexOf("다")+1);
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

	public int getPrice() {
		return price;
	}
	public String getStringPrice() {
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

	public int getStatus() {
		
		return status;
	}
	public String getStringStatus() {
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
	
}
