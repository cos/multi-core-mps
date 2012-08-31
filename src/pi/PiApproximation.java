package pi;

/**
 * Implementation of this interface use an approximation algorithm to compute
 * pi.<br/>
 * 
 * See {@link PiSequential} for an implementation of this method.
 * 
 */

public interface PiApproximation {

	/**
	 * Compute pi by approximation using <code>iterantions</code> iterations.
	 * 
	 * @param iterations
	 * @return the approximated pi
	 * @throws Exception
	 */
	public double computePi(long iterations) throws Exception;
}