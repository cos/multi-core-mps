package primes;

public abstract class PrimesComputation {
	/**
	 * Finds all prime numbers smaller than n. The returned array tells whether
	 * the index is prime or not.
	 * 
	 * @param upto
	 *            - the number of primes to compute
	 * @return an array of boolean, a[x] == true iff x is prime
	 */
	public abstract Boolean[] computePrimes(int upto) throws Exception;

	/**
	 * Very basic prime test.
	 * 
	 * The algorithm is naive, checking for each number whether it divides with
	 * 2 or odd numbers smaller then it.
	 * 
	 * @param x
	 * @return whether x is prime
	 */
	public static boolean isPrime(int x) {
		if (x == 2)
			return true;
		if (x % 2 == 0 || x < 2)
			return false;
		for (long i = 3; i <= Math.sqrt(x); i += 2)
			if (x % i == 0)
				return false;
		return true;
	}
}