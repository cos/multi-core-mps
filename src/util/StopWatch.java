package util;

public class StopWatch {
	private static long startTime;
	private static long endTime;
	public static void start() {
		startTime = System.nanoTime();
	}
	public static void stop() {
		endTime = System.nanoTime();
	}
	public static void log(String message) {
		System.out.println(message+getRuntime()+"ms");
	}
	public static long getRuntime() {
		return (endTime - startTime)/1000000;
	}
}