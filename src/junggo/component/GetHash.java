package junggo.component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GetHash {
	
	public static String getHash (String txt) throws NoSuchAlgorithmException {
		return getHashCodeFromString(txt);
	}

	// 16진수 해시코드 암호화 > 128자리 스트링 출력
	private static String getHashCodeFromString(String str) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("SHA-512");
	    try {
			md.update(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    byte byteData[] = md.digest();

	    //convert the byte to hex format method 1
	    StringBuffer hashCodeBuffer = new StringBuffer();
	    for (int i = 0; i < byteData.length; i++) {
	        hashCodeBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    return hashCodeBuffer.toString();
	}
	
}
