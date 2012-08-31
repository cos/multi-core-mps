package primes;

/**
 * This application computes a set of all primes smaller than a given n.
 * 
 * The algorithm is naive, checking for each number whether it divides with 2 or
 * odd numbers smaller then it.
 * 
 */

public class PrimesSequential extends PrimesComputation {
	@Override
	public Boolean[] computePrimes(int upto) {
		Boolean[] results = new Boolean[upto];
		for (int x = 0; x < results.length; x++)
			results[x] = isPrime(x);
		return results;
	}
}