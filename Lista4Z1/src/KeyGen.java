import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class KeyGen {

	private static BigInteger ONE = BigInteger.ONE;
	private static SecureRandom random;

	public static void generateKey(int bits, int quanity) {

		BigInteger n, phi, e, d;
		n = ONE;
		phi = ONE;
		BigInteger p[] = new BigInteger[quanity];
		random = new SecureRandom();
		for (int i = 0; i < quanity; i++) {
			p[i] = new BigInteger(bits, 100, random);
			n = n.multiply(p[i]);
			phi = p[i].subtract(ONE).multiply(phi);
		}
		e = coprime(phi);
		d = e.modInverse(phi);

		PrintWriter savePublicKey;
		PrintWriter savePrivateKey;

		try {
			savePublicKey = new PrintWriter("public.key");
			savePublicKey.println(e.toString());
			savePublicKey.println(n.toString());

			savePrivateKey = new PrintWriter("private.key");
			savePrivateKey.println(d.toString());
			savePrivateKey.println(n.toString());

			savePublicKey.close();
			savePrivateKey.close();
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}
		;

	}

	private static BigInteger coprime(BigInteger phi) {
		random = new SecureRandom();
		int length = phi.bitLength() - 1;
		BigInteger e = BigInteger.probablePrime(length, random);
		while (!(phi.gcd(e)).equals(BigInteger.ONE)) {
			e = BigInteger.probablePrime(length, random);
		}
		return e;
	}
}
