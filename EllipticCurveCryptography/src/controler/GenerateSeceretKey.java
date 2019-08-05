package controler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

public class GenerateSeceretKey {
	public static SecretKey getSeceretKey(PrivateKey privatekey, PublicKey publickey) {
		SecretKey key = null;
		try {
			KeyAgreement agreement = KeyAgreement.getInstance("ECDH", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			agreement.init(privatekey);
			agreement.doPhase(publickey, true);
			key = agreement.generateSecret("AES");
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			System.out.println(e);
		}
		return key;
	}
}
