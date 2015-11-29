import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class RSA {

	public static void main(String[] args) {
		BigInteger e, n, d;
		
		if(args.length < 3){
			System.out.println("Błędne użycie programu");
			System.out.println("Aby wygenerować klucze: prog gen k d)");
			System.out.println("Aby zaszyfrowac plik: prog enc plikDoZaszyfrowania plikWynikowy");
			System.out.println("Aby zdeszyfrować plik: prog dec zaszyfrowanyPlik plikWynikowy");
			}
		if (args[0].equals("gen")) {
			KeyGen.generateKey(Integer.parseInt(args[2]), Integer.parseInt(args[1]));
			System.out.println("Wygenerowano klucze! ");
			return;
		} else if (args[0].equals("enc")) {
			RSAReg.encrypt(args[1], args[2]);
		} else if (args[0].equals("dec")) {
			RSAReg.decrypt(args[1], args[2]);
		}
	}

}
