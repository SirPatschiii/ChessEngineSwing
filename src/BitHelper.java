/**
 * Utility class providing helper methods for manipulating and inspecting bits in a long bitstream.
 *
 * <p>
 * The {@code BitHelper} class includes static methods for:
 * <ul>
 *     <li>Setting a bit at a specified position in a bitstream.</li>
 *     <li>Clearing a bit at a specified position in a bitstream.</li>
 *     <li>Checking if a bit is set at a specified position in a bitstream.</li>
 *     <li>Counting the number of bits set to {@code 1} in a bitstream.</li>
 *     <li>Printing the binary representation of a bitstream in a single line.</li>
 *     <li>Printing the binary representation of a bitstream in a matrix format (8x8).</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class does not support instantiation as all methods are static.
 * </p>
 *
 * @author SirPatschiii
 * @version 2024-06-26
 */
public class BitHelper {
    /**
     * Private constructor to prevent instantiation of the utility class.
     */
    private BitHelper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Sets a bit at a specified position in the given bitstream.
     *
     * @param bitstream The original bitstream.
     * @param position  The position (0 to 63) where the bit should be set.
     * @return The bitstream with the specified bit set.
     */
    public static long setBit(long bitstream, int position) {
        return bitstream | (1L << position);
    }

    /**
     * Clears a bit at a specified position in the given bitstream.
     *
     * @param bitstream The original bitstream.
     * @param position  The position (0 to 63) where the bit should be cleared.
     * @return The bitstream with the specified bit cleared.
     */
    public static long clearBit(long bitstream, int position) {
        return bitstream & ~(1L << position);
    }

    /**
     * Checks if a bit is set at a specified position in the given bitstream.
     *
     * @param bitstream The bitstream to check.
     * @param position  The position (0 to 63) of the bit to check.
     * @return {@code true} if the bit is set (1), {@code false} otherwise (0).
     */
    public static boolean isBitSet(long bitstream, int position) {
        return (bitstream & (1L << position)) != 0;
    }

    /**
     * Counts the number of bits set to {@code 1} in the given bitstream.
     *
     * @param bitstream The bitstream to count bits from.
     * @return The number of bits set to {@code 1} in the bitstream.
     */
    public static int countBits(long bitstream) {
        return Long.bitCount(bitstream);
    }

    /**
     * Prints the binary representation of the given bitstream in a single line.
     *
     * @param bitstream The bitstream to print.
     */
    public static void printBitstreamInLine(long bitstream) {
        for (int i = 0; i < 64; i++) {
            System.out.print(isBitSet(bitstream, i) ? "1" : "0");
        }
    }

    /**
     * Prints the binary representation of the given bitstream in a matrix format (8x8).
     *
     * @param bitstream The bitstream to print.
     */
    public static void printBitstreamInMatrix(long bitstream) {
        for (int i = 0; i < 64; i++) {
            System.out.print(isBitSet(bitstream, i) ? "1" : "0");
            System.out.print(" ");
            if ((i + 1) % 8 == 0) {
                System.out.println();
            }
        }
    }
}
