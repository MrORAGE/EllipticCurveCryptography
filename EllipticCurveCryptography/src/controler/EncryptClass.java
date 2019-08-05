package controler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;


public class EncryptClass {
	public static byte[] encryption(SecretKey seceretKey, String plainText) {
		
		byte[] b = null;	
		try {
				Cipher cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.ENCRYPT_MODE, seceretKey);
				b =cipher.doFinal(plainText.getBytes());
			} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException  e) {
				System.out.println(e);
			} 
			return b ;
	}
}
