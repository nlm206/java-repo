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
SecretKey key
com.sun.crypto.provider.DESedeKey@b069a8ba
INPUT string: (9)
72,111,112,101,102,117,108,108,121,

Encrypted string: (16)
196,58,185,71,126,212,254,250,194,0,228,171,65,105,235,187,

CIPHER_TEXT (24)
xDq5R37U/vrCAOSrQWnruw==
Decrypted string: 
72,111,112,101,102,117,108,108,121,

Hopefully            
     */
	@Test
	public void encryptionWorksOkUsingJCE() throws Exception {

		System.out.println("\n ------------------------------ ");
		System.out.println(" >> EncryptionWorksOkUsingJCE Testing(). \n");
		
		
        String algorithm = "DESede"; // DES-EDE Triple-DES
        String transformation = "DESede/CBC/PKCS5Padding";

        
        byte[] keyValue = SHARED_KEY.getBytes("UTF-8");

        DESedeKeySpec keySpec = new DESedeKeySpec(keyValue);

        /* Initialization Vector of 8 bytes set to zero. */
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);

        SecretKey key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
        
        /**
		 *  Step 1. Generate a DES key using KeyGenerator 
		 * 
		 */
		//KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
		//SecretKey key = keyGen.generateKey();
		
		System.out.println("SecretKey key");
		System.out.println(key);
		
        Cipher encrypter = Cipher.getInstance(transformation);
        encrypter.init(Cipher.ENCRYPT_MODE, key, iv);

        byte[] input = PLAIN_TEXT.getBytes("UTF-8");

        System.out.println("INPUT string: (" + input.length + ")");
        for (int i = 0; i< input.length; i++) {
        	System.out.print((int)(input[i] & 0xFF));
        	System.out.print(",");
        }
        System.out.println("\n");
        
        byte[] encrypted = encrypter.doFinal(input);

        System.out.println("Encrypted string: (" + encrypted.length + ")");
        for (int i = 0; i< encrypted.length; i++) {
        	System.out.print((int)(encrypted[i] & 0xFF));
        	System.out.print(",");
        }
        System.out.println("\n");
        
        CIPHER_TEXT = new BASE64Encoder().encode(encrypted);
        System.out.println("CIPHER_TEXT (" + CIPHER_TEXT.length() + ")");
        System.out.println(CIPHER_TEXT);
        
        /*assertEquals("Ensure that we have encrypted the token correctly", CIPHER_TEXT, new String(Hex
                .encodeHex(encrypted)).toUpperCase());
*/
        Cipher decrypter = Cipher.getInstance(transformation);
        decrypter.init(Cipher.DECRYPT_MODE, key, iv);

        //byte[] decrypted = decrypter.doFinal(Hex.decodeHex(CIPHER_TEXT.toCharArray()));
        byte[] decrypted = decrypter.doFinal((encrypted));
        
        System.out.println("Decrypted string: ");
        for (int i = 0; i< decrypted.length; i++) {
        	System.out.print((int)(decrypted[i] & 0xFF));
        	System.out.print(",");
        }
        System.out.println("\n");
        System.out.println(new String(decrypted, "UTF-8"));
        
        assertEquals(PLAIN_TEXT, new String(decrypted, "UTF-8"));
    }

}