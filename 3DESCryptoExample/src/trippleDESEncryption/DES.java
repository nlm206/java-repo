package trippleDESEncryption;

import org.junit.jupiter.api.Test;



import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import sun.misc.BASE64Encoder;

/**
 * 
 * https://www.owasp.org/index.php/Using_the_Java_Cryptographic_Extensions
 * https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html
 * 
 * @author Joe Prasanna Kumar
 * This program provides the following cryptographic functionalities
 * 1. Encryption using DES
 * 2. Decryption using DES
 * 
 * The following modes of DES encryption are supported by SUNJce provider 
 * 1. ECB (Electronic code Book) - Every plaintext block is encrypted separately 
 * 2. CBC (Cipher Block Chaining) - Every plaintext block is XORed with the previous ciphertext block
 * 3. PCBC (Propogating Cipher Block Chaining) - 
 * 4. CFB (Cipher Feedback Mode) - The previous ciphertext block is encrypted and this enciphered block is XORed with the plaintext block to produce the corresponding ciphertext block 
 * 5. OFB (Output Feedback Mode) - 
 *
 *	High Level Algorithm :
 * 1. Generate a DES key
 * 2. Create the Cipher (Specify the Mode and Padding)
 * 3. To Encrypt : Initialize the Cipher for Encryption
 * 4. To Decrypt : Initialize the Cipher for Decryption
 * 
 * Need for Padding :
 * Block ciphers operates on data blocks on fixed size n. 
 * Since the data to be encrypted might not always be a multiple of n, the remainder of the bits are padded.
 * PKCS#5 Padding is what will be used in this program 
 * 
 */

@SuppressWarnings("restriction")
public class DES {
	
	@Test
	public void DES_Test() {
		
		String strDataToEncrypt = new String();
		String strCipherText = new String();
		String strDecryptedText = new String();
		
		System.out.println("\n ------------------------------ ");
		System.out.println(" >> DES_Test Testing(). \n");
		
		try{
		/**
		 *  Step 1. Generate a DES key using KeyGenerator 
		 * 
		 */
		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		SecretKey secretKey = keyGen.generateKey();
		
		/**
		 *  Step2. Create a Cipher by specifying the following parameters
		 * 			a. Algorithm name - here it is DES
		 * 			b. Mode - here it is CBC
		 * 			c. Padding - PKCS5Padding
		 */
		
		Cipher desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); /* Must specify the mode explicitly as most JCE providers default to ECB mode!! */
		
		/**
		 *  Step 3. Initialize the Cipher for Encryption 
		 */
		
		desCipher.init(Cipher.ENCRYPT_MODE,secretKey);
		
		/**
		 *  Step 4. Encrypt the Data
		 *  		1. Declare / Initialize the Data. Here the data is of type String
		 *  		2. Convert the Input Text to Bytes
		 *  		3. Encrypt the bytes using doFinal method 
		 */
		strDataToEncrypt = "Hello World of Encryption using DES 123";
		byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
		byte[] byteCipherText = desCipher.doFinal(byteDataToEncrypt); 
		strCipherText = new BASE64Encoder().encode(byteCipherText);
		System.out.println("Cipher Text generated using DES with CBC mode and PKCS5 Padding is " +strCipherText);
		
		System.out.println("INPUT string: (" + byteDataToEncrypt.length + ")");
        for (int i = 0; i< byteDataToEncrypt.length; i++) {
        	System.out.print((int)(byteDataToEncrypt[i] & 0xFF));
        	System.out.print(",");
        }
        System.out.println("\n");
        
		System.out.println("Encrypted string: (" + byteCipherText.length + ")");
        for (int i = 0; i< byteCipherText.length; i++) {
        	System.out.print((int)(byteCipherText[i] & 0xFF));
        	System.out.print(",");
        }
        System.out.println("\n");
        
		/**
		 *  Step 5. Decrypt the Data
		 *  		1. Initialize the Cipher for Decryption 
		 *  		2. Decrypt the cipher bytes using doFinal method 
		 */
		desCipher.init(Cipher.DECRYPT_MODE,secretKey,desCipher.getParameters());
		 //desCipher.init(Cipher.DECRYPT_MODE,secretKey);
		byte[] byteDecryptedText = desCipher.doFinal(byteCipherText);
		strDecryptedText = new String(byteDecryptedText);
		System.out.println(" Decrypted Text message is " +strDecryptedText);
		}
		
		catch (NoSuchAlgorithmException noSuchAlgo)
		{
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		}
		
			catch (NoSuchPaddingException noSuchPad)
			{
				System.out.println(" No Such Padding exists " + noSuchPad);
			}
		
				catch (InvalidKeyException invalidKey)
				{
					System.out.println(" Invalid Key " + invalidKey);
				}
				
				catch (BadPaddingException badPadding)
				{
					System.out.println(" Bad Padding " + badPadding);
				}
				
				catch (IllegalBlockSizeException illegalBlockSize)
				{
					System.out.println(" Illegal Block Size " + illegalBlockSize);
				}
				
				catch (InvalidAlgorithmParameterException invalidParam)
				{
					System.out.println(" Invalid Parameter " + invalidParam);
				}
	}

}