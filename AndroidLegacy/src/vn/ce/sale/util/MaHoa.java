package vn.ce.sale.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class MaHoa {
	// Hàm chuyển đổi key là 1 chuỗi ký tự về 1 mảng byte làm làm chìa khóa mã
	// hóa
	public static byte[] generateKey(String password) throws Exception {
		byte[] keyStart = password.getBytes();
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(keyStart);
		kgen.init(128, sr);
		SecretKey skey = kgen.generateKey();
		return skey.getEncoded();
	}

	// Hàm thực hiện việc mã hóa dữ liệu từ 1 key
	public static byte[] encodeFile(byte[] key, byte[] fileData) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(fileData);
		return encrypted;
	}

	// Hàm thực hiện việc giải mã dữ liệu từ 1 key
	public static byte[] decodeFile(byte[] key, byte[] fileData) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(fileData);
		return decrypted;
	}

	private static byte[] getKey() {
		byte[] seed = "here_is_your_aes_key".getBytes();
		KeyGenerator kg;
		try {
			kg = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		SecureRandom sr;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		sr.setSeed(seed);

		kg.init(128, sr);

		SecretKey sk = kg.generateKey();

		byte[] key = sk.getEncoded();
		return key;
	}

	public static String encrypt(String clearText) {
		byte[] encryptedText = null;
		try {
//			byte[] keyData = seed.getBytes();
			SecretKey ks = new SecretKeySpec(getKey(), "AES");
			Cipher c = Cipher.getInstance("AES");
			c.init(128, ks);
			encryptedText = c.doFinal(clearText.getBytes("UTF-8"));
			return Base64.encodeToString(encryptedText, Base64.DEFAULT);
		} catch (Exception e) {
			return null;
		}
	}

	public static String decrypt(String encryptedText) {
		byte[] clearText = null;
		try {
//			byte[] keyData = seed.getBytes();
			SecretKey ks = new SecretKeySpec(getKey(), "AES");
			Cipher c = Cipher.getInstance("AES");
			 int blockSize = c.getBlockSize();
			c.init(Cipher.DECRYPT_MODE, ks,new IvParameterSpec(new byte[blockSize]));
			clearText = c.doFinal(Base64.decode(encryptedText, Base64.DEFAULT));
			return new String(clearText, "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}
}
