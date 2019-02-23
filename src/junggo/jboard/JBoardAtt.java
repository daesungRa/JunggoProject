package junggo.jboard;

import java.util.List;

public class JBoardAtt {
	
	List<String> sysFiles;
	List<String> oriFiles;
	List<String> delFiles;
	
	int JBoard_Serial;
	int JBoard_pSerial;

	public List<String> getSysFiles() {
		return sysFiles;
	}

	public void setSysFiles(List<String> sysFiles) {
		this.sysFiles = sysFiles;
	}

	public List<String> getOriFiles() {
		return oriFiles;
	}

	public void setOriFiles(List<String> oriFiles) {
		this.oriFiles = oriFiles;
	}

	public List<String> getDelFiles() {
		return delFiles;
	}

	public void setDelFiles(List<String> delFiles) {
		this.delFiles = delFiles;
	}

	public int getJBoard_Serial() {
		return JBoard_Serial;
	}

	public void setJBoard_Serial(int jBoard_Serial) {
		JBoard_Serial = jBoard_Serial;
	}

	public int getJBoard_pSerial() {
		return JBoard_pSerial;
	}

	public void setJBoard_pSerial(int jBoard_pSerial) {
		JBoard_pSerial = jBoard_pSerial;
	}

	
}
