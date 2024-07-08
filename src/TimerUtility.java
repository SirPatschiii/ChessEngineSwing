/**
 * <p>
 *  Utility class for managing and measuring time intervals. This class provides methods to start and stop a timer,
 *  and prints the elapsed time in seconds and milliseconds.
 *  It is implemented using the singleton pattern to prevent instantiation.
 * </p>
 *
 * @author SirPatschiii
 * @version 08.07.2024
 */
public class TimerUtility {
    private static long startTime;
    private static long endTime;
    private static boolean isRunning;

    private TimerUtility() {
        // Private constructor to prevent instantiation
    }

    /**
     * <p>
     *  Starts the timer by recording the current time in nanoseconds.
     * </p>
     *
     * @throws IllegalStateException if the timer is already running
     */
    public static void startTimer() {
        if (isRunning) {
            throw new IllegalStateException("Timer is already running.");
        }
        startTime = System.nanoTime();
        isRunning = true;
        System.out.println("Timer started...");
    }

    /**
     * <p>
     *  Stops the timer by recording the current time in nanoseconds and calculating the elapsed time.
     *  The elapsed time is then printed in seconds and milliseconds.
     * </p>
     *
     * @throws IllegalStateException if the timer is not running
     */
    public static void stopTimer() {
        if (!isRunning) {
            throw new IllegalStateException("Timer is not running. Please start the timer first.");
        }
        endTime = System.nanoTime();
        isRunning = false;
        long elapsedTime = endTime - startTime;
        printElapsedTime(elapsedTime);
    }

    /**
     * <p>
     *  Prints the elapsed time in seconds and milliseconds.
     * </p>
     *
     * @param elapsedTime the elapsed time in nanoseconds
     */
    private static void printElapsedTime(long elapsedTime) {
        double seconds = elapsedTime / 1_000_000_000.0;
        double milliseconds = (elapsedTime / 1_000_000.0) % 1_000.0;
        System.out.printf("Elapsed time: %.3f seconds (%.3f milliseconds)%n", seconds, milliseconds);
    }
}
