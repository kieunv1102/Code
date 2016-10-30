package vn.ce.sale.util;

import javax.crypto.spec.SecretKeySpec;

import android.provider.SyncStateContract.Constants;
import android.util.Base64;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;

public class AES {
	static String IV = "AAAAAAAAAAAAAAAA";
	static String plaintext = "test text 123\0\0\0"; /* Note null padding */

	public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		return cipher.doFinal(plainText.getBytes("UTF-8"));
	}

	public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		return new String(cipher.doFinal(cipherText), "UTF-8");
	}

	public static String encryptText(String plainText) throws Exception {
		// ---- Use specified 3DES key and IV from other source --------------
		byte[] plaintext = plainText.getBytes();// input
		byte[] tdesKeyData = Constants.getKey().getBytes();// your encryption
															// key

		byte[] myIV = Constants.getInitializationVector().getBytes();// initialization
																		// vector

		Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		SecretKeySpec myKey = new SecretKeySpec(tdesKeyData, "DESede");
		IvParameterSpec ivspec = new IvParameterSpec(myIV);

		c3des.init(Cipher.ENCRYPT_MODE, myKey, ivspec);
		byte[] cipherText = c3des.doFinal(plaintext);
		String encryptedString = Base64.encodeToString(cipherText, Base64.DEFAULT);
		// return Base64Coder.encodeString(new String(cipherText));
		return encryptedString;
	}

	private static class Constants {
		private static final String KEY = "QsdPasd45FaSdnLjf";
		private static final String INITIALIZATION_VECTOR = "l9yhTaWY";

		public static String getKey() {
			return KEY;
		}

		public static String getInitializationVector() {
			return INITIALIZATION_VECTOR;
		}
	}
}
