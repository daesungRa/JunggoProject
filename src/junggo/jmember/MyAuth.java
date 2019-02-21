package junggo.jmember;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

//메일전송에 필요한 계정정보
public class MyAuth extends Authenticator{
	PasswordAuthentication passwordAuthentication;
	
	public PasswordAuthentication getPasswordAuthentication() {
		String user = "dfdf0608@naver.com";
		String pwd = "RKGUS@9419";
		
		passwordAuthentication = new PasswordAuthentication(user, pwd);
		return passwordAuthentication;
		}
	
	

}
