import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class RSAReg {
	private static BigInteger e, n, d;

	public static void encrypt(String plaintext, String destinityFile) {

		try {
			/*
			 * Pobieranie kluczy
			 */
			File key = new File("public.key");
			Scanner reader;

			reader = new Scanner(key);
			e = new BigInteger(reader.nextLine());
			n = new BigInteger(reader.nextLine());
			/*
			 * Szyfrowanie
			 */
			BufferedWriter writer;

			FileInputStream fileInput;

			fileInput = new FileInputStream(plaintext);

			int r;
			BigInteger message = new BigInteger("1");
			String dataChar = "";

			writer = new BufferedWriter(new FileWriter(destinityFile));

			while ((r = fileInput.read()) != -1) {
				char c = (char) r;
				dataChar += (char) c;
				message = new BigInteger(dataChar.getBytes());
				if (message.compareTo(n) > 0) {
					dataChar = dataChar.substring(0, dataChar.length() - 1);
					message = new BigInteger(dataChar.getBytes());
					BigInteger wynik = message.modPow(e, n);

					writer.write(new String(wynik.toString()));
					writer.write(" ");
					dataChar = "" + c;
				}
			}
			BigInteger result = message.modPow(e, n);
			writer.write(new String(result.toString()));
			writer.close();
			fileInput.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("Szyfrowanie zakończone!");

	}

	public static void decrypt(String ciphertext, String resultFile) {
		/*
		 * Pobieranie klucza
		 */
		
		try {
			File key = new File("private.key");
			Scanner reader;
			reader = new Scanner(key);
			d = new BigInteger(reader.nextLine());
			n = new BigInteger(reader.nextLine());

			BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile));
			FileInputStream fileInput = new FileInputStream(ciphertext);
			int r;

			/*
			 * Deszyfrowanie
			 */
			String content = new String(Files.readAllBytes(Paths.get(ciphertext)));
			String[] checked = content.split(" ");
			for (String o : checked) {
				BigInteger result = new BigInteger(o).modPow(d, n);
				writer.write(new String(result.toByteArray()));
			}
			writer.close();
			fileInput.close();
			System.out.println("Deszyfrowanie zakończone!");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}
