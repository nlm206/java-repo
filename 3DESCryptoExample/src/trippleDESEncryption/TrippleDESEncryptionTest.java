package trippleDESEncryption;

import static org.junit.Assert.assertEquals;

import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Encoder;

import org.junit.jupiter.api.Test;

@SuppressWarnings("restriction")
class TripleDESEncryptionTest {
	/* 
     * Hopefully the documentation that you're using can give you some suitable test data to fill in 
     * these constants.
     */
    private static final String PLAIN_TEXT = "Hopefully1234567Hopefully1234567";

    private static String CIPHER_TEXT = "someCipherText";

    private static final String SHARED_KEY = "A1234567A1234567A1234567"; // 24 bytes, K1, K2, K3

    /**
     * Test that we are correctly encrypting the content as per their spec.
     * 
     * @throws Exception
     *             if there was a problem
     */
	@Test
	public void encryptionWorksOkUsingJCE() throws Exception {

		System.out.println("\n ------------------------------------------------------------");
		System.out.println(" >> Encryption Works Ok Using JCE Testing().");
		System.out.println(" ------------------------------------------------------------");
		
        String algorithm = "DESede"; // DES-EDE Triple-DES
        String transformation = "DESede/CBC/NoPadding"; // NoPadding/ PKCS1Padding/ PKCS5Padding
        
        /* Initialization Vector of 8 bytes set to zero. */
        byte[] iv8 = {(byte) 0xE7, 0x59, 0x3B, 0x75, (byte) 0xA8, 0x02, 0x03, 0x2B};
        IvParameterSpec iv = new IvParameterSpec(iv8);
        
        /**
		 *  Step 1. Generate a DES key using KeyGenerator 
		 * 
		 */
		//KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
		//SecretKey key = keyGen.generateKey();
        
        //byte[] keyValue = SHARED_KEY.getBytes("UTF-8");
        byte[] keyValue = {
        		0x68, 0x4B, 0x10, 0x08, (byte) 0xBB, 0x28, (byte) 0xE3, (byte) 0xF8, 
        		(byte) 0xBA, 0x38, 0x2C, (byte) 0xC9, (byte) 0xDC, 0x6A, 0x44, (byte) 0xD3,
        		(byte) 0x89, (byte) 0xE6, 0x26, (byte) 0xEC, (byte) 0x96, 0x1D, 0x32, 0x6E};
        
        DESedeKeySpec keySpec = new DESedeKeySpec(keyValue);
        SecretKey key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
        // com.sun.crypto.provider.DESedeKey@b0698b32
        
		System.out.println(">>SecretKey key:");
		System.out.println(key);
		System.out.println();
		
		/**
		 *  Step2. Create a Cipher by specifying the following parameters
		 * 			a. Algorithm name - here it is DES
		 * 			b. Mode - here it is CBC
		 * 			c. Padding - PKCS5Padding
		 */
        Cipher encrypter = Cipher.getInstance(transformation);
        encrypter.init(Cipher.ENCRYPT_MODE, key, iv);

        
        byte[] input = PLAIN_TEXT.getBytes("UTF-8");

        byte[] encrypted = encrypter.doFinal(input);

        CIPHER_TEXT = new BASE64Encoder().encode(encrypted);
        
        //assertEquals("Ensure that we have encrypted the token correctly", CIPHER_TEXT, 
        //		new String(Hex.encodeHex(encrypted)).toUpperCase());
        
        Cipher decrypter = Cipher.getInstance(transformation);
        decrypter.init(Cipher.DECRYPT_MODE, key, iv);

        //byte[] decrypted = decrypter.doFinal(Hex.decodeHex(CIPHER_TEXT.toCharArray()));
        byte[] decrypted = decrypter.doFinal((encrypted));
        
        assertEquals(PLAIN_TEXT, new String(decrypted, "UTF-8"));
        
        //-----------------------------------------------------------------------//
        System.out.println(">>INPUT string: (" + input.length + ")");
        for (int i = 0; i< input.length; i++) {
        	System.out.printf("%02X ", (int)(input[i] & 0xFF));

        	if ((1 + i) % 16 == 0)
        		System.out.print("\r\n");
        }
        System.out.println("\n");
        
       //-----------------------------------------------------------------------//
        System.out.println(">>Encrypted string: (" + encrypted.length + ")");
        for (int i = 0; i< encrypted.length; i++) {
        	System.out.printf("%02X ", (int)(encrypted[i] & 0xFF));

        	if ((1 + i) % 16 == 0)
        		System.out.print("\r\n");
        }
        System.out.println("\n");
        
       //-----------------------------------------------------------------------//
        System.out.println(">>CIPHER_TEXT (" + CIPHER_TEXT.length() + ")");
        System.out.println(CIPHER_TEXT);
        //System.out.println(new String(Hex.encodeHex(encrypted)).toUpperCase());
        System.out.println();
        
       //-----------------------------------------------------------------------//
        System.out.println(">>Decrypted string (" + decrypted.length + ")");
        for (int i = 0; i < decrypted.length; i++) {
        	System.out.printf("%02X ", (int)(decrypted[i] & 0xFF));
        	
        	if ((1 + i) % 16 == 0)
        		System.out.print("\r\n");
        }
        System.out.println("\n");
        System.out.println(new String(decrypted, "UTF-8"));
       //-----------------------------------------------------------------------//
        
    }

}