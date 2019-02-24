package junggo.component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GetHashedData {
	
	/*
	 * 해시코드 문자열 반환 메서드
	 */
	// 1. 입력받은 문자열을 64 자리 바이트 배열로 변환
	// 2. 바이트 배열의 각 인자들을 문자열로 변환해 StringBuffer 로 덧붙인 후 toString 으로 반환
	public static String generateHashedString (String txt){
		String hashedResult = "";
		
		hashedResult = getHashedStringFromBytes(getHashed64Bytes(txt));
		
		return hashedResult;
	}
	// 1. 64 자리의 난수 바이트 배열 생성
	// 2. 바이트 배열의 각 인자들을 문자열로 변환해 StringBuffer 로 덧붙인 후 toString 으로 반환
	public static String generateRandomString () {
		String randomResult = "";
		
		randomResult = getHashedStringFromBytes(getRandom64Bytes());
		
		return randomResult;
	}

	/*
	 * 해시문자열 및 난수 생성기
	 */
	// 입력받은 문자열을  sha512 알고리즘에 의해 64 자리 바이트 배열로 변환 후 반환
	private static byte[] getHashed64Bytes (String str) {
	    MessageDigest md = null;
	    byte[] bytes = null;
	    
	    try {
	    	md = MessageDigest.getInstance("SHA-512"); // 알고리즘 적용
			md.update(str.getBytes("UTF-8")); // 인코딩 설정 후 입력받은 문자열을 바이트 배열로 변환하여 적용
			bytes = md.digest();
	    } catch (NoSuchAlgorithmException nae) { // 알고리즘 없을 시
	    	nae.printStackTrace();
		} catch (UnsupportedEncodingException uee) { // 인코딩 에러
			uee.printStackTrace();
		}

	    return bytes;
	}
	
	// SecureRandom 난수 생성기를 이용해 128 자리의 랜덤 바이트 배열 생성 후 반환
	// 알고리즘은 SHA1PRNG
	private static byte[] getRandom64Bytes () {
		byte[] bytes = null;
		
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			bytes = random.generateSeed(64);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return bytes;
	}
	
	// convert the bytes to hexadecimal format
	// 입력받은 바이트 배열의 인수들을 16진수 형식의 문자열로 변환 후 stringbuffer 에 append 후 tostring 하여 반환
	private static String getHashedStringFromBytes (byte[] bytes) {
	    StringBuffer hashCodeBuffer = new StringBuffer();
	    
	    if (bytes != null) {
	    	try {
			    for (int i = 0; i < bytes.length; i++) {
			        hashCodeBuffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			    }
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    	}
	    }
	    
	    return hashCodeBuffer.toString();
	}
	
}
