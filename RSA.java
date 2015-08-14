import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;

public class RSA {
	private BigInteger primeOne;
	private BigInteger primeTwo;
	static public BigInteger N;
	static private BigInteger Z;
	static private BigInteger D;
	static public BigInteger E = new BigInteger("65537");
	
	//Default Constructor
	RSA(){
		primeOne = genPrime();
		primeTwo = genPrime();
		boolean sentinel = true;
		while (sentinel){
			if((primeOne.compareTo(primeTwo))== 0){
				primeTwo = genPrime();
			}
			else{
				sentinel = false;
			}
		}
		N = primeOne.multiply(primeTwo);
		BigInteger One = new BigInteger("1");
		BigInteger primeOneMinus = primeOne.subtract(One);
		BigInteger primeTwoMinus = primeTwo.subtract(One);
		Z = primeOneMinus.multiply(primeTwoMinus);
		D = E.modInverse(Z);
		primeOne = null;
		primeTwo = null;
		System.gc();
		System.out.println("Keys Generated");
		//Set the prime numbers to null after the keys are generated
		//Clear D after displayed
	}
	
	//Constructor for when the keys have already been generated
	RSA(String publicKeys, String privateKey) throws FileNotFoundException{
		File publicFile = new File(publicKeys);
		File privateFile = new File(privateKey);
		Scanner file = new Scanner(publicFile);
		N = new BigInteger(file.nextLine()); 
		Z = new BigInteger(file.nextLine()); 
		file.close();
		file = new Scanner(privateFile);
		D = new BigInteger(file.nextLine()); 
		file.close();
	}
	
	static void saveKeys(String publicKeys, String privateKey) throws FileNotFoundException{
		
		//Create the file
		File publicFile = new File(publicKeys);
		File privateFile = new File(privateKey);
		PrintWriter output = new PrintWriter(publicFile);
		output.println(N);
		output.println(Z);
		output.close();
		
		output = new PrintWriter(privateFile);
		output.println(D);
		output.close();
		System.out.println("Keys have been saved");
	}
	
	static void deleteKeys(){
		N = Z = D = E = null;
		System.out.println("Keys Have Been Deleted");
	}
	
	private BigInteger genPrime(){
		BigInteger prime;
		SecureRandom rand = new SecureRandom();
		boolean isPrime = false;
		do{
			prime = BigInteger.probablePrime(64, rand);
			isPrime = prime.isProbablePrime(40);
		}while(!isPrime);
		return prime;
	}
	
	ArrayList<BigInteger> encrypt(String message){
		BigInteger [] encryptionTable = new BigInteger[8483];
		BigInteger BigIntAtI;
		ArrayList<BigInteger> encryptedMessage = new ArrayList<>();
		for (int i = 0; i < message.length(); i++){
			if( encryptionTable[(int)message.charAt(i)] == null){
				BigIntAtI = BigInteger.valueOf((long)message.charAt(i));
				encryptionTable[(int)message.charAt(i)] = BigIntAtI.modPow(E , N);
				encryptedMessage.add(encryptionTable[(int)message.charAt(i)]);				
			}
			else{
				encryptedMessage.add(encryptionTable[(int)message.charAt(i)]);
			}
		}
		encryptionTable = null;
		return encryptedMessage;		
	}
	
	String decrypt(ArrayList<BigInteger> encryptedMsg){
		String message = "";
		BigInteger BigIntAtI;
		char decryptedChar;
		for(int i = 0; i < encryptedMsg.size(); i++){
			BigIntAtI = encryptedMsg.get(i);
			BigInteger postMod = BigIntAtI.modPow(D, N);
			decryptedChar = (char)(postMod.intValue());
			message = message + decryptedChar;
		}
		return message;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// Debugging, seeing if all values are correct
		//RSA object = new RSA();
		
	}

}
