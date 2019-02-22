package junggo.component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GetHash {
	
	public String getHash (String txt) throws NoSuchAlgorithmException {
		return bytesToHex(sha256(txt));
	}

	public byte[] sha256 (String txt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256"); // sha-256 해시함수를 세팅
		md.update(txt.getBytes()); // 입력받은 텍스트를 바이트 배열로 변환하여 해시함수에 update
		System.out.println("sha256 입력값: " + txt);
		System.out.println("sha256 결과: " + md.digest());
		
		return md.digest(); // 해싱의 결과를 반환( byte[] ). 저장 시 String 으로 변환 필요
	}
	
	public String bytesToHex (byte[] bytes) {
		StringBuilder builder = new StringBuilder(); // 빌더 선언
		for (byte b: bytes) { // 입력받은 바이트 배열의 각 인덱스에 저장된 바이트 값을 가져와
			builder.append(String.format("%02x", b)); // 헥사 형식의 문자열로 변환하여 빌더에 append
		}
		System.out.println("bytesToHex 결과: " + builder.toString());
		
		return builder.toString(); // 바이트 배열을 변환한 최종 문자열 반환
	}
	
}
