package trippleDESEncryption;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * @author Joe Prasanna Kumar
 * This program provides the functionality for Generating a Secure Random Number.
 *  
 * There are 2 ways to generate a  Random number through SecureRandom.
 * 1. By calling nextBytes method to generate Random Bytes
 * 2. Using setSeed(byte[]) to reseed a Random object
 * 
 */

public class SecureRandomGen {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
	        // Initialize a secure random number generator
	        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
	    
	        // Method 1 - Calling nextBytes method to generate Random Bytes
	        byte[] bytes = new byte[512];
	        secureRandom.nextBytes(bytes); 
	        
	        // Printing the SecureRandom number by calling secureRandom.nextDouble()
	        System.out.println(" Secure Random # generated by calling nextBytes() is " + secureRandom.nextDouble());
	    
	        // Method 2 - Using setSeed(byte[]) to reseed a Random object
	        int seedByteCount = 10;
	        byte[] seed = secureRandom.generateSeed(seedByteCount);   
	        
	        // TBR System.out.println(" Seed value is " + new BASE64Encoder().encode(seed));
	    
	        secureRandom.setSeed(seed);
	        
	        System.out.println(" Secure Random # generated using setSeed(byte[]) is  " + secureRandom.nextDouble());
	        
	    } catch (NoSuchAlgorithmException noSuchAlgo)
		{
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		}
	}

}
