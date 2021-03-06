package pi;

import java.util.Timer;
import java.util.TimerTask;

import junit.framework.Assert;

import org.junit.Test;

import util.StopWatch;
import util.UnimplementedExercise;

/**
 * Tests for PiApproximation implementations.
 * 
 * The tests run all implementations and report, for each of them, the speedup
 * and the approximation. The reported values are obtained after executing two
 * {@link #warmup(PiApproximation)} runs.
 */

public class PiTest {
	private static final double PI = 3.141592653589793238462643383279D;
	private static final int ITERATIONS = 20000000;
	private static long seqRuntime = 0;

	@Test
	public void testSequential() throws Exception {
		test("Sequential version", new PiSequential());
	}

	@Test
	public void testThreads() throws Exception {
		test("Threads version", new PiThreads());
	}

	@Test
	public void testLive() throws Exception {
		test("Live version", new PiLive());
	}

	@Test
	public void testLiveSync() throws Exception {
		test("Live with sync version", new PiLiveSync());
	}

	@Test
	public void testLiveAtomic() throws Exception {
		test("Live with atomic version", new PiLiveAtomic());
	}

	protected void test(String version, final PiApproximation piApproximation)
			throws Exception {

		System.out.println(version);
		System.out.println("-----------------------------");

		if (piApproximation instanceof UnimplementedExercise) {
			System.out.println("UNIMPLEMENTED - when finished "
					+ "implementing this exercise, "
					+ "remove UnimplementedExercise from "
					+ "the list of implemented interfaces.\n");
			return;
		}

		// warm-up
		warmup(piApproximation);
		// double the warmup for the sequential version - just to make sure all
		// JIT is done
		if (seqRuntime == 0)
			warmup(piApproximation);

		StopWatch.start();
		Timer timer = new Timer("Print live value");
		if (piApproximation instanceof LiveValue) {
			int processorCount = Runtime.getRuntime().availableProcessors();
			long delay = (seqRuntime == 0 ? 400 : seqRuntime) / processorCount
					/ 2;
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					double livePi = ((LiveValue) piApproximation).liveValue();
					System.out.println("Live value: " + livePi);
				}
			}, delay);
		}
		double pi = piApproximation.computePi(ITERATIONS);
		StopWatch.stop();

		StopWatch.log("Time: ");
		if (seqRuntime == 0)
			seqRuntime = StopWatch.getRuntime();
		else
			System.out.printf("Speed-up: %.2f\n",
					seqRuntime / 1.0 / StopWatch.getRuntime());

		System.out.println("     Real Pi: " + PI);
		System.out.println("Estimated Pi: " + pi);
		System.out.println("Delta (times 1,000,000): " + ((pi - PI) * 1000000));
		Assert.assertEquals(PI, pi, 0.001);
		System.out.println();
		timer.cancel();
	}

	private static void warmup(PiApproximation piApproximation)
			throws Exception {
		piApproximation.computePi(ITERATIONS);
		piApproximation.computePi(ITERATIONS);
	}
}
