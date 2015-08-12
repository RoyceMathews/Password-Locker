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
	private BigInteger N;
	private BigInteger Z;
	private BigInteger D;
	private BigInteger E = new BigInteger("65537");
	
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
		//Set the prime numbers to null after the keys are generated
		//Clear D after displayed
	}
	
	//Constructor for when the keys have already been generated
	RSA(File publicKeys, File privateKey) throws FileNotFoundException{
		Scanner file = new Scanner(publicKeys);
		N = new BigInteger(file.nextLine()); 
		Z = new BigInteger(file.nextLine()); 
		file.close();
		file = new Scanner(privateKey);
		D = new BigInteger(file.nextLine()); 
		file.close();
	}
	
	private void saveKeys(Scanner inputText) throws FileNotFoundException{
		//Scanner inputText = new Scanner(System.in);
		System.out.println("Enter desired file name for the public keys: ");
		String filename = inputText.nextLine();
		System.out.println("Enter desired file name for the private key: ");
		String privateFilename = inputText.nextLine();
		//inputText.close();
		//Create the file
		PrintWriter output = new PrintWriter(filename);
		output.println(N);
		output.println(Z);
		output.close();
		output = new PrintWriter(privateFilename);
		output.println(D);
		output.close();
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
		
		/*System.out.println(object.primeTwo + " " + object.primeOne);
		System.out.println(object.Z);
		System.out.println(object.N);
		System.out.println(object.D);
		BigInteger temp = (object.D).multiply(object.E);
		System.out.println(temp.mod(object.Z));
		*/
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a Message: ");
		String message = input.nextLine();
		System.out.println("Enter a public Key file name: ");
		String filename = input.nextLine();
		System.out.println("Enter a private Key file name: ");
		String privateFilename = input.nextLine();
		RSA object = new RSA(new File(filename), new File(privateFilename));
		//Scanner file = new Scanner(new File(filename));
		
		//String text = "";	// empty string is created
		/*while(file.hasNext()){	// as long as there is another character, the loop keep running
			text = text + file.nextLine();	// the next character is added to the string
		}
		file.close();*/
		
		System.out.println(object.Z);
		System.out.println(object.N);
		System.out.println(object.D);
		ArrayList<BigInteger> encryptedMessage = object.encrypt(message);
		System.out.println(encryptedMessage);
		System.out.println(object.decrypt(encryptedMessage));
		object.saveKeys(input);
		input.close();
	}

}
