package testing;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import model.RSAEncryption;

public class RSAEncryptionTest {

	private static final String TEST_PASSWORD = "exemplu_de_parola";
	private PublicKey publicKey = RSAEncryption.getPublicKey();
	private PrivateKey privateKey = RSAEncryption.getPrivateKey();

	@Test
	void cryptographyTest() {
		boolean successfulEncryption = false;

		try {
			String encryptedPassword = RSAEncryption.encryptMessage(TEST_PASSWORD, privateKey);
			String decryptedPassword = RSAEncryption.decryptMessage(encryptedPassword, publicKey);
			
			if (decryptedPassword.equals(TEST_PASSWORD))
				successfulEncryption = true;

			System.out.println("Password to be encrypted: " + TEST_PASSWORD + "\n");
			System.out.println("Encrypted passoword: " + encryptedPassword + "\n");
			System.out.println("Decrypted passoword: " + decryptedPassword + "\n");
			System.out.println("Encryption worked: " + successfulEncryption);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Assert.assertTrue(successfulEncryption);
	}
}
