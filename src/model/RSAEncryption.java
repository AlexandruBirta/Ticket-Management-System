package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
 
// Example for RSA encryption/decryption.
// Uses strong encryption with 2048 key size.
public class RSAEncryption {
	
    private static final String privateKeyFile = "D:\\Facultate\\Licenta\\Proiect de licenta\\private.key";
	private static final String publicKeyFile = "D:\\Facultate\\Licenta\\Proiect de licenta\\public.key";
	
	public static void generateKeys() throws Exception {
		Map<String, Object> keys = getRSAKeys();
		
		PrivateKey privateKey = (PrivateKey) keys.get("private");
		PublicKey publicKey = (PublicKey) keys.get("public");

		// Serialization  
        try
        {    
            FileOutputStream file = new FileOutputStream(privateKeyFile); 
            ObjectOutputStream out = new ObjectOutputStream(file); 
            
            out.writeObject(privateKey); 
              
            out.close(); 
            file.close();
            
            file = new FileOutputStream(publicKeyFile); 
            out = new ObjectOutputStream(file); 
            
            out.writeObject(publicKey); 
              
            out.close(); 
            file.close(); 
        } 
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        }
	}
	
	
	public static PrivateKey getPrivateKey() {
		PrivateKey privateKey = null; 
		  
        // Deserialization 
        try
        {    
            // Reading the object from a file 
            FileInputStream file = new FileInputStream(privateKeyFile); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            privateKey = (PrivateKey)in.readObject(); 
              
            in.close(); 
            file.close();
        } 
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
          
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } 
        
        return privateKey;
	}
	
	public static PublicKey getPublicKey() {
		PublicKey publicKey = null; 
		  
        // Deserialization 
        try
        {    
            // Reading the object from a file 
            FileInputStream file = new FileInputStream(publicKeyFile); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            publicKey = (PublicKey) in.readObject(); 
              
            in.close(); 
            file.close();
        } 
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
          
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } 
        
        return publicKey;
	}
	

	// Get RSA keys. Uses key size of 2048.
    public static Map<String,Object> getRSAKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
 
        Map<String, Object> keys = new HashMap<String,Object>();
        keys.put("private", privateKey);
        keys.put("public", publicKey);
        return keys;
    }
 
    // Decrypt using RSA public key
    public static String decryptMessage(String encryptedText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
    }
 
    // Encrypt using RSA private key
    public static String encryptMessage(String plainText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }
 
}