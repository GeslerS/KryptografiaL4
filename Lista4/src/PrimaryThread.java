import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class PrimaryThread implements Runnable {
	private static final BigInteger ZERO = BigInteger.ZERO;
	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger TWO = new BigInteger("2");
	private static final BigInteger THREE = new BigInteger("3");
	int k, id;
	byte bytes[];
	SecureRandom random;
	BigInteger randomPrime;
	Random normalRandom = new Random();

	boolean prime = false;

	public PrimaryThread(int k, int id, SecureRandom random) {
		this.k = k / 8;
		this.id = id;
		this.random = random;
		bytes = new byte[k / 8];
	}

	@Override
	public void run() {
		while (true) {

			random.nextBytes(bytes);

			// randomPrime = new BigInteger(k*8,1,random);
			//	randomPrime = new BigInteger(bytes);
			BigInteger randomPrime = new BigInteger(k*8 - 2, random).add((TWO.pow(k*8-2))).shiftLeft(1).add(ONE);
			
			randomPrime = randomPrime.abs();

			// System.out.println(id + ") " + randomPrime.toString());
			if (checkPrimary(randomPrime)) {
				System.out.println(id + ": " + randomPrime.toString());
				System.out.println(id + ": " + randomPrime.bitLength());
				break;
			}

		}

	}

	public boolean checkPrimary(BigInteger randomPrime) {

		if ((randomPrime.compareTo(THREE) == 1) && (randomPrime.mod(TWO).equals(ONE))) {
			// System.out.println("PRZECHODZI " + randomPrime.toString());
			return rabinMiller(randomPrime);

		}
		// System.out.println("PRZECHODZI " + randomPrime.toString() + " " +
		// (randomPrime.mod(TWO) == ONE) + " " +
		// randomPrime.mod(TWO).toString());
		return false;
	}

	public boolean rabinMiller(BigInteger randomPrime) {
		int k = 0;

		BigInteger a, v, i;
		BigInteger s = new BigInteger(randomPrime.subtract(ONE).toString());
		BigInteger t = ZERO;

		while ((s.mod(TWO).compareTo(ZERO) == 0)) {
			s = s.divide(TWO);
			t = t.add(ONE);
		}

		while (k < 128) {
			do {
				a = new BigInteger(Integer.toString(random.nextInt()));
			} while ((a.compareTo(TWO) != 1) && (a.compareTo(randomPrime.subtract(ONE)) != -1));

			v = a.modPow(s, randomPrime);

			if (!v.equals(ONE)) {
				i = ZERO;
				while (!v.equals(randomPrime.subtract(ONE))) {
					if (i.equals(t.subtract(ONE))) {
						return false;
					} else {
						v = v.modPow(TWO, randomPrime);
						i = i.add(ONE);
					}

				}

			}
			k = k + 2;

		}
		return true;
	}

}