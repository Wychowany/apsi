package com.apsi.global;


import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


public class HashHelper {

	static String hashString(String stringToHash) {
		String hash = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(stringToHash.getBytes());
			byte[] digest = messageDigest.digest();
			hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}

	public static String generateEmailToken() {
		String uuid = UUID.randomUUID().toString();
		return hashString(uuid).toLowerCase();
	}

	public static String generateHashForFile() {
		String uuid = UUID.randomUUID().toString();
		return hashString(uuid).toLowerCase();
	}

	public static String generateRandomPassword() {
		String uuid = UUID.randomUUID().toString();
		return hashString(uuid).toLowerCase();
	}

	/**
	 * Generate 10-digit token for volunteer users
	 *
	 * @return 10-digit token
	 */
	public static String generateVolunteerCode() {
		String uuid = UUID.randomUUID().toString();
		return hashString(uuid).substring(0, 10);
	}
}
